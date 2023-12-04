package server.db;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import server.service.Request;
import server.service.RequestType;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.util.*;
import java.util.stream.IntStream;

/**
 * {@code ServerDBManager} 클래스는 서버의 데이터베이스 관리를 담당하는 클래스입니다.
 *
 * @see DBManager
 * @author 지연우
 */
public class ServerDBManager extends DBManager{

    /**
     * 데이터베이스 연결 등 초기화를 수행합니다.
     */
    public static void init() {
        try {
            path = Path.of("./missioncleardata/server/pictures");
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
            conn = DriverManager.getConnection("jdbc:sqlite:missioncleardata/server/server.db");

            createTable("USER", """
                    CREATE TABLE IF NOT EXISTS USER (uid integer primary key, id string not null unique, 
                    password string not null, nickname string not null, pfp string default '', groups string default '')""");

            createTable("GROUPS", """
                    CREATE TABLE IF NOT EXISTS GROUPS (gid integer primary key, 
                    title string not null, description string not null, mission string not null, 
                    capacity integer not null, category integer not null, usercnt integer default 1, 
                    deadline string not null, startDate string not null, endDate string not null, 
                    password string default '', users string not null)""");


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 사용자를 추가합니다.
     *
     * @param id       사용자 아이디
     * @param pw       사용자 비밀번호
     * @param nickname 사용자 닉네임
     * @return 결과를 담은 JSON 객체
     */
    public static JSONObject addUser(String id, String pw, String nickname) {
        JSONObject result = new JSONObject();
        String sql = String.format("""
                INSERT INTO USER (id, password, nickname)
                VALUES ('%s', '%s', '%s')""", id, pw, nickname);

        result.put("resultType", executeSQL(sql).getCode());
        if ((Integer) result.get("resultType") == ResultType.SUCCESS.getCode()) {
            sql = "SELECT last_insert_rowid()";
            result.put("uid", executeQuery(sql).get("last_insert_rowid()"));
        }
        return result;
    }

    /**
     * 사용자 정보를 조회합니다.
     *
     * @param uid 사용자 식별자
     * @return 사용자 정보를 담은 JSON 객체
     */
    public static JSONObject getUser(int uid) {
        String sql = String.format("""
                SELECT uid, groups, nickname, pfp FROM USER WHERE uid=%s""", uid);

        JSONObject result = executeQuery(sql);

        if (result.isEmpty())
            result.put("resultType", ResultType.WARNING.getCode());

        return result;
    }

    /**
     * 아이디와 비밀번호가 일치히나느 사용자 정보가 있는지 조회합니다.
     *
     * @param id 사용자 아이디
     * @param pw 사용자 비밀번호
     * @return 사용자 정보 존재 여부 및 해당 사용자 정보를 담은 JSON 객체
     */
    public static JSONObject getUser(String id, String pw) {
        String sql = String.format("""
                SELECT uid, groups, nickname, pfp FROM USER WHERE id='%s' AND password='%s'""", id, pw);

        JSONObject result = executeQuery(sql);

        if (result.isEmpty())
            result.put("resultType", ResultType.WARNING.getCode());

        return result;
    }

    /**
     * 그룹 정보를 조회합니다.
     *
     * @param gid 그룹 식별자
     * @return 그룹 정보를 담은 JSON 객체
     */
    public static JSONObject getGroup(int gid) {
        String sql = String.format("""
                SELECT gid, title, description, mission, capacity, category, usercnt, deadline, startDate, endDate, users FROM GROUPS WHERE gid=%s""", gid);

        JSONObject result = executeQuery(sql);

        if (result.isEmpty())
            result.put("resultType", ResultType.WARNING.getCode());

        return result;
    }

    /**
     * 초기 데이터를 조회합니다.
     * 초기 데이터는 해당 유저가 속한 그룹, 그 그룹들에 속한 유저의 정보, 채팅정보, 미션인증 정보를 의미합니다.
     * 사용자들의 프로필 사진 전송 요청 또한 이 함수에서 수행합니다.
     *
     * @param groups     사용자가 속한 그룹 리스트
     * @param writeQueue 데이터를 쓰기 위한 큐
     * @return 초기 데이터를 담은 JSON 객체
     */
    public static JSONObject getInitData(List<Integer> groups, Queue<ByteBuffer> writeQueue) {
        JSONObject result = new JSONObject();
        JSONArray myGroups = new JSONArray();
        Set<Integer> userSet = new HashSet<>();
        for (int gid: groups) {
            JSONObject group = getGroup(gid);
            myGroups.add(group);
            List<Integer> users = Arrays.stream(((String)group.get("users")).split(",")).map(Integer::parseInt).toList();
            userSet.addAll(users);
        }
        result.put("groupData", myGroups);

        List<Integer> userList = new ArrayList<>(userSet);
        Collections.sort(userList);
        JSONArray users = new JSONArray();
        for (int uid: userList) {
            JSONObject user = getUser(uid);
            String pfpPath = user.get("pfp").toString();
            String fileName = Path.of(pfpPath).getFileName().toString();
            user.replace("pfp", fileName);
            if (!fileName.equals("")){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("fileName", fileName);
                fileName =  path.toString() + "\\" + fileName;
                Request request = new Request(RequestType.GETFILE, jsonObject);
                try {
                    byte[] file = Files.readAllBytes(Path.of(fileName));
                    request.file = file;
                    request.getData().put("resultType", ResultType.SUCCESS.getCode());
                } catch (IOException e) {
                    request.getData().put("resultType", ResultType.WARNING.getCode());
                }
                writeQueue.add(Request.toByteBuffer(request));
            }
            users.add(user);
        }
        result.put("userData", users);

        JSONObject chatData = new JSONObject();
        try (Statement statement = conn.createStatement()){
            for (int gid : groups) {
                JSONArray jsonArray = new JSONArray();
                String sql = "SELECT * from G" + gid + "CHAT";
                ResultSet rs = statement.executeQuery(sql);

                while(rs.next()) {
                    JSONObject chat = new JSONObject();
                    chat.put("chatId", rs.getInt("chatId"));
                    chat.put("uid", rs.getInt("uid"));
                    chat.put("isPic", rs.getInt("isPic"));
                    if (rs.getInt("isPic") == 1) {
                        String filePath = rs.getString("message");
                        chat.put("message", Path.of(filePath).getFileName().toString());
                    } else {
                        chat.put("message", rs.getString("message"));
                    }
                    jsonArray.add(chat);
                }

                chatData.put("G"+gid+"CHAT", jsonArray);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        result.put("chatData", chatData);

        JSONObject progressData = new JSONObject();
        try (Statement statement = conn.createStatement()){
            for (int gid : groups) {
                JSONArray jsonArray = new JSONArray();
                String sql = "SELECT * from G" + gid + "PROGRESS";
                ResultSet rs = statement.executeQuery(sql);

                while(rs.next()) {
                    JSONObject chat = new JSONObject();
                    chat.put("uid", rs.getInt("uid"));
                    chat.put("date", rs.getString("date"));
                    jsonArray.add(chat);
                }

                progressData.put("G"+gid+"PROGRESS", jsonArray);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        result.put("progressData", progressData);

        return result;
    }

    /**
     * 모집 중인 그룹 데이터를 조회합니다.
     *
     * @return 모집 중인 그룹 데이터를 담은 JSON 객체
     */
    public static JSONObject getRecruitingGroupData() {
        JSONObject result = new JSONObject();
        JSONArray recruitingGroups = new JSONArray();
        try (Statement statement = conn.createStatement()){
            String sql = "SELECT * FROM GROUPS WHERE (deadline >= date('now', 'localtime') AND usercnt < capacity)";
            try (ResultSet resultSet = statement.executeQuery(sql)) {
                // 열 제목 리스트 생성
                ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
                int numCols = resultSetMetaData.getColumnCount();
                List<String> colNames = IntStream.range(0, numCols)
                        .mapToObj(i -> {
                            try {
                                return resultSetMetaData.getColumnName(i + 1);
                            } catch (SQLException e) {
                                e.printStackTrace();
                                return "?";
                            }
                        })
                        .toList();
                // 각 열을 JSON 객체로 만들어 result에 저장
                while (resultSet.next()) {
                    JSONObject group = new JSONObject();
                    colNames.forEach(cn -> {
                        try {
                            group.put(cn, resultSet.getObject(cn));
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    });
                    group.put("isSecret", !group.get("password").equals(""));
                    group.remove("password");
                    recruitingGroups.add(group);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        result.put("recruitingGroups", recruitingGroups);

        return result;
    }

    /**
     * 새 그룹 정보를 그룹 테이블에 저장하고, 그룹 채팅 테이블과 그룹 진행도 테이블을 생성합니다.
     *
     * @param groupInfo 그룹 정보를 담은 JSON 객체
     * @return 생성된 그룹 정보를 담은 JSON 객체
     */
    public static JSONObject createGroup(JSONObject groupInfo){
        // GROUPS 테이블에 그룹 row 추가
        String title = (String) groupInfo.get("title");
        String description = (String) groupInfo.get("description");
        String mission = (String) groupInfo.get("mission");
        Integer capacity = Integer.parseInt(groupInfo.get("capacity").toString());
        Integer category = Integer.parseInt(groupInfo.get("category").toString());
        String deadline = (String) groupInfo.get("deadline");
        String startDate = (String) groupInfo.get("startDate");
        String endDate = (String) groupInfo.get("endDate");
        String password = (String) groupInfo.get("password");
        Integer uid = Integer.parseInt(groupInfo.get("uid").toString());

        JSONObject result = groupInfo;
        String sql = String.format("""
                INSERT INTO GROUPS 
                (title, description, mission, capacity, category, deadline, startDate, endDate, password, users)
                VALUES ('%s', '%s', '%s', %d, %d, '%s', '%s', '%s', '%s', '%s')""",
                title, description, mission, capacity, category, deadline, startDate, endDate, password, uid+",");

        result.put("resultType", executeSQL(sql).getCode());
        if ((Integer) result.get("resultType") == ResultType.SUCCESS.getCode()) {
            //gid 확인
            sql = "SELECT last_insert_rowid()";
            Integer gid = (Integer) executeQuery(sql).get("last_insert_rowid()");
            result.put("gid", gid);

            // user의 groups에 group 추가
            sql = String.format("""
                SELECT groups FROM USER WHERE (uid=%d)""", uid);
            String groups = (executeQuery(sql).get("groups").toString()) + gid + ",";
            sql = String.format("UPDATE USER SET groups='%s' WHERE uid=%d", groups, uid);
            executeSQL(sql);

            // 채팅, 미션인증 진행도 테이블 생성
            sql = String.format("""
                    CREATE TABLE IF NOT EXISTS G%sCHAT (chatId integer primary key, uid integer not null, message string not null, isPic integer not null)""", gid);
            createTable("G"+gid+"CHAT", sql);
            sql = String.format("""
                    CREATE TABLE IF NOT EXISTS G%sPROGRESS (uid integer not null, date string not null)""", gid);
            createTable("G"+gid+"PROGRESS", sql);
        }
        return result;
    }

    /**
     * 그룹에 입장이 가능한지 조회하고, 가능하다면 그룹의 소속유저 정보, 유저수 정보, 유저의 소속그룹 정보를 수정합니다,.
     *
     * @param uid 사용자 식별자
     * @param gid 그룹 식별자
     * @param pw  그룹 비밀번호
     * @return 결과를 담은 JSON 객체
     */
    public static JSONObject enterGroup(int uid, int gid, String pw){
        String sql = String.format("""
                SELECT * FROM GROUPS WHERE (gid=%d AND password='%s' AND usercnt<capacity)""", gid, pw);
        JSONObject result = executeQuery(sql);
        String users = ((String)result.get("users"));

        if(users != null) {
            result.remove("password");
            result.put("uid", uid);
            List<Integer> userList = Arrays.stream(users.split(",")).map(Integer::parseInt).toList();
            if(!userList.contains(uid)){
                sql = String.format("""
                        SELECT groups FROM USER WHERE (uid=%d)""", uid);
                String groups = (executeQuery(sql).get("groups").toString()) + gid + ",";
                users += uid+",";
                sql = String.format("""
                        UPDATE GROUPS SET users='%s' WHERE gid=%d""", users, gid);
                executeSQL(sql);

                sql = String.format("""
                        UPDATE GROUPS SET usercnt=usercnt+1 WHERE gid='%s'""", gid);
                executeSQL(sql);

                sql = String.format("""
                        UPDATE USER SET groups='%s' WHERE uid=%d""", groups, uid);
                executeSQL(sql);

                JSONArray userData = new JSONArray();
                for (int user : Arrays.stream(users.split(",")).map(Integer::parseInt).toList()) {
                    JSONObject data = getUser(user);
                    userData.add(data);
                }
                result.put("userData", userData);
                return result;
            }
        }

        return result;
    }

    /**
     * 클라이언트가 요청한 파일을 읽어 {@code Request} 객체에 첨부합니다.
     *
     * @param request 클라이언트로부터 받은 {@code Request} 객체입니다.
     */
    public static void getFile(Request request) {
        String fileName = request.getData().get("fileName").toString();
        fileName = path.toString() + "\\" + fileName;
        try {
            byte[] file = Files.readAllBytes(Path.of(fileName));
            request.file = file;
        } catch (IOException e) {
            request.getData().put("resultType", ResultType.WARNING.getCode());
        }
    }
}
