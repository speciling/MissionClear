package server.db;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import server.service.Request;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.util.*;
import java.util.stream.IntStream;

public class ServerDBManager extends DBManager{

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

    // 성공시 SUCCESS, 이미 존재하는 아이디면 WARNING, 에러 발생시 FAILURE
    // return: {"resultType": ResultType.getCode(), "uid": uid (유저 생성 성공시) }
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

    // 성공시 SUCCESS, 아이디나 비밀번호가 잘못됐으면 WARNING, 에러 발생시 FAILURE
    public static JSONObject getUser(int uid) {
        String sql = String.format("""
                SELECT uid, groups, nickname, pfp FROM USER WHERE uid=%s""", uid);

        JSONObject result = executeQuery(sql);

        if (result.isEmpty())
            result.put("resultType", ResultType.WARNING.getCode());

        return result;
    }

    public static JSONObject getUser(String id, String pw) {
        String sql = String.format("""
                SELECT uid, groups, nickname, pfp FROM USER WHERE id='%s' AND password='%s'""", id, pw);

        JSONObject result = executeQuery(sql);

        if (result.isEmpty())
            result.put("resultType", ResultType.WARNING.getCode());

        return result;
    }

    public static JSONObject getGroup(int gid) {
        String sql = String.format("""
                SELECT gid, title, description, mission, capacity, category, usercnt, deadline, startDate, endDate, users FROM GROUPS WHERE gid=%s""", gid);

        JSONObject result = executeQuery(sql);

        if (result.isEmpty())
            result.put("resultType", ResultType.WARNING.getCode());

        return result;
    }

    public static JSONObject getInitData(List<Integer> groups) {
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
            user.replace("pfp", Path.of(pfpPath).getFileName().toString());
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

        JSONArray recruitingGroups = new JSONArray();
        try (Statement statement = conn.createStatement()){
            String sql = "SELECT * FROM GROUPS WHERE deadline >= date('now', 'localtime')";
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
                    group.put("isSecret", group.get("password") != null);
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

    public static JSONObject getRecruitingGroupData() {
        JSONObject result = new JSONObject();
        JSONArray recruitingGroups = new JSONArray();
        try (Statement statement = conn.createStatement()){
            String sql = "SELECT * FROM GROUPS WHERE deadline >= date('now', 'localtime')";
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
                    group.put("isSecret", group.get("password") != null);
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

    public static JSONObject getGroupsData(){
        return null;
    }

    public static JSONObject getChatData(JSONObject lastChatId){
        return null;
    }

    public static JSONObject getProfileData(JSONObject profiles){
        return null;
    }

    public static void main(String[] args) {
        ServerDBManager.init();
        JSONObject groupInfo = new JSONObject();
        addUser("t5", "tt", "");
        addUser("t4", "tt", "");
        groupInfo.put("title", "t1");
        groupInfo.put("description", "t1");
        groupInfo.put("mission", "t1");
        groupInfo.put("capacity", 3);
        groupInfo.put("category", 3);
        groupInfo.put("deadline", "2023-11-15");
        groupInfo.put("startDate", "");
        groupInfo.put("endDate", "");
        groupInfo.put("password", "");
        groupInfo.put("uid", 1);
        System.out.println(createGroup(groupInfo));
//        System.out.println(enterGroup(2, 1, ""));

        JSONObject chatData = new JSONObject();
        chatData.put("uid", 1);
        chatData.put("gid", 1);
        chatData.put("message", "hi");
        chatData.put("time", "2023-11-18");
        System.out.println(saveChatMessage(chatData));

//        System.out.println(executeQuery("SELECT * FROM USER WHERE uid=2"));
//        System.out.println(executeQuery("SELECT * FROM GROUPS WHERE gid=1"));
    }

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

    public static ResultType enterGroup(int uid, int gid, String pw){
        String sql = String.format("""
                SELECT users FROM GROUPS WHERE (gid=%d AND password='%s' AND usercnt<capacity)""", gid, pw);
        String users = ((String)executeQuery(sql).get("users"));

        if(users != null) {
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
                return ResultType.SUCCESS;
            }
        }

        return ResultType.WARNING;
    }

    public static void getFile(Request request) {
        String fileName = request.getData().get("fileName").toString();
        fileName = path.toString() + "\\" + fileName;
        try {
            byte[] file = Files.readAllBytes(Path.of(fileName));
            request.file = file;
        } catch (IOException e) {
            request.getData().put("resultType", ResultType.WARNING);
        }
    }
}
