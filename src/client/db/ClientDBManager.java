package client.db;

import client.net.ClientSocket;
import client.recruitpage.Group;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import server.db.DBManager;
import server.service.Request;
import server.service.RequestType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * {@code ClientDBManager} 클래스는 클라이언트 측 데이터베이스 관련 작업을 처리합니다.
 *
 * @author 지연우
 */
public class ClientDBManager extends DBManager {
    /** 현재 사용자의 UID */
    public static int myUid;

    /**
     * 클라이언트 데이터베이스를 초기화하고 사용자 및 그룹 테이블을 생성합니다.
     */
    public static void init() {
        try {
            if (conn != null)
                conn.close();
            path = Path.of("./missioncleardata/client/client.db");
            try{
                Files.deleteIfExists(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
            path = Path.of("./missioncleardata/client/pictures");
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
            conn = DriverManager.getConnection("jdbc:sqlite:missioncleardata/client/client.db");

            createTable("USER", """
                    CREATE TABLE IF NOT EXISTS USER 
                    (uid integer unique, nickname string not null, pfp string, groups string default '')""");

            createTable("GROUPS", """
                    CREATE TABLE IF NOT EXISTS GROUPS (gid integer unique, 
                    title string not null, description string not null, mission string not null, 
                    capacity integer not null, category integer not null, usercnt integer default 1, 
                    deadline string not null, startDate string not null, endDate string not null, 
                    users string not null)""");


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 데이터베이스에 로그인한 사용자 본인의 정보를 저장합니다.
     *
     * @param data 로그인한 사용자 본인 정보를 포함하는 JSON 객체
     */
    public static void login(JSONObject data) {
        Integer uid = Integer.parseInt(data.get("uid").toString());
        String nickname = data.get("nickname").toString();
        String pfp = data.get("pfp").toString();
        if (!pfp.isEmpty()) {
            String fileName = Path.of(pfp).getFileName().toString();
            pfp = path.toString() + "\\" + fileName;
        }
        String groups = data.get("groups").toString();
        myUid = uid;

        String sql = String.format("INSERT OR REPLACE INTO USER (uid, nickname, pfp, groups) VALUES (%d, '%s', '%s', '%s')", uid, nickname, pfp, groups);
        executeSQL(sql);

        if (!pfp.isEmpty() && !Files.exists(Path.of(pfp))) {
            JSONObject jsonObject = new JSONObject();
            String fileName = Path.of(pfp).getFileName().toString();
            jsonObject.put("fileName", fileName);
            ClientSocket.send(new Request(RequestType.GETFILE, jsonObject));
        }

    }

    /**
     * 초기 데이터를 저장합니다.
     *
     * @param data 초기 데이터를 포함하는 JSON 객체
     */
    public static void saveInitData(JSONObject data) {
        JSONArray userData = (JSONArray) data.get("userData");
        for (int i = 0; i < userData.size(); i++) {
            JSONObject user = (JSONObject) userData.get(i);
            Integer uid = Integer.parseInt(user.get("uid").toString());
            String nickname = (String) user.get("nickname");
            String pfp = (String) user.get("pfp");
            if (!pfp.isEmpty()){
                String fileName = Path.of(pfp).getFileName().toString();
                pfp = path.toString() + "\\" + fileName;
            }
            String groups = user.get("groups").toString();
            String sql = String.format("""
                    INSERT OR REPLACE INTO USER (uid, nickname, pfp, groups) 
                    VALUES (%d, '%s', '%s', '%s')""", uid, nickname, pfp, groups);
            executeSQL(sql);
        }

        JSONArray groupData = (JSONArray) data.get("groupData");
        JSONObject chatData = (JSONObject) data.get("chatData");
        JSONObject progressData = (JSONObject) data.get("progressData");
        for (int i = 0; i < groupData.size(); i++) {
            JSONObject group = (JSONObject) groupData.get(i);
            Integer gid = Integer.parseInt(group.get("gid").toString());
            String title = group.get("title").toString();
            String description = group.get("description").toString();
            String mission = group.get("mission").toString();
            Integer capacity = Integer.parseInt(group.get("capacity").toString());
            Integer category = Integer.parseInt(group.get("category").toString());
            Integer usercnt = Integer.parseInt(group.get("usercnt").toString());
            String deadline = group.get("deadline").toString();
            String startDate = group.get("startDate").toString();
            String endDate = group.get("endDate").toString();
            String users = group.get("users").toString();
            String sql = String.format("""
                    INSERT OR REPLACE INTO GROUPS (gid, title, description, mission, capacity, category, usercnt, deadline, startDate, endDate, users) 
                    VALUES (%d, '%s', '%s', '%s', %d, %d, %d, '%s', '%s', '%s', '%s')""", gid, title, description, mission, capacity, category, usercnt, deadline, startDate, endDate, users);
            executeSQL(sql);

            JSONArray chattings = (JSONArray) chatData.get("G"+gid+"CHAT");
            sql = String.format("""
                    CREATE TABLE IF NOT EXISTS G%sCHAT (chatId integer primary key, uid integer not null, message chat not null, isPic integer not null)""", gid);
            createTable("G"+gid+"CHAT", sql);
            for (int j = 0; j < chattings.size(); j++) {
                JSONObject chatting = (JSONObject) chattings.get(j);
                Integer chatId = Integer.parseInt(chatting.get("chatId").toString());
                Integer uid = Integer.parseInt(chatting.get("uid").toString());
                String message = chatting.get("message").toString();
                Integer isPic = Integer.parseInt(chatting.get("isPic").toString());
                if (isPic == 1){
                    Path filePath = Path.of(path.toString()  + "\\" + Path.of(message).getFileName());
                    if (!Files.exists(filePath)){
                        JSONObject object = new JSONObject();
                        object.put("fileName", message);
                        ClientSocket.send(new Request(RequestType.GETFILE, object));
                    }
                }
                sql = String.format("""
                        INSERT INTO G%dCHAT (chatId, uid, message, isPic) VALUES (%d, %d, '%s', %d)""", gid, chatId, uid, message, isPic);
                executeSQL(sql);
            }

            JSONArray progresses = (JSONArray) progressData.get("G"+gid+"PROGRESS");
            sql = String.format("""
                    CREATE TABLE IF NOT EXISTS G%sPROGRESS (uid integer not null, date string not null)""", gid);
            createTable("G"+gid+"PROGRESS", sql);
            for (int j = 0; j < progresses.size(); j++){
                JSONObject progress = (JSONObject) progresses.get(j);
                Integer uid = Integer.parseInt(progress.get("uid").toString());
                String date = progress.get("date").toString();
                sql = String.format("""
                        INSERT INTO G%dPROGRESS (uid, date) VALUES (%d, '%s')""", gid, uid, date);
                executeSQL(sql);
            }
        }
    }

    /**
     * 새로 생성된 그룹의 정보를 그룹 테이블에 저장하고, 채팅 테이블과 진행도 테이블을 생성합니다.
     *
     * @param group 생성할 그룹 정보를 포함하는 JSON 객체
     */
    public static void createNewGroup(JSONObject group) {
        String title = (String) group.get("title");
        String description = (String) group.get("description");
        String mission = (String) group.get("mission");
        Integer capacity = Integer.parseInt(group.get("capacity").toString());
        Integer category = Integer.parseInt(group.get("category").toString());
        String deadline = (String) group.get("deadline");
        String startDate = (String) group.get("startDate");
        String endDate = (String) group.get("endDate");
        Integer uid = Integer.parseInt(group.get("uid").toString());
        Integer gid = Integer.parseInt(group.get("gid").toString());

        String sql = String.format("""
                INSERT INTO GROUPS 
                (gid, title, description, mission, capacity, category, deadline, startDate, endDate, users)
                VALUES (%d, '%s', '%s', '%s', %d, %d, '%s', '%s', '%s', '%s')""",
                gid, title, description, mission, capacity, category, deadline, startDate, endDate, uid+",");

        executeSQL(sql).getCode();

        // user의 groups에 group 추가
        sql = String.format("""
                SELECT groups FROM USER WHERE (uid=%d)""", uid);
        String groups = (executeQuery(sql).get("groups").toString()) + gid + ",";
        sql = String.format("UPDATE USER SET groups='%s' WHERE uid=%d", groups, uid);
        executeSQL(sql);

        sql = String.format("""
                    CREATE TABLE IF NOT EXISTS G%sCHAT (chatId integer primary key, uid integer not null, message chat not null, isPic integer not null)""", gid);
        createTable("G"+gid+"CHAT", sql);

        sql = String.format("""
                    CREATE TABLE IF NOT EXISTS G%sPROGRESS (uid integer not null, date string not null)""", gid);
        createTable("G"+gid+"PROGRESS", sql);
    }

    /**
     * 입장한 그룹의 정보를 그룹 테이블에 저장하고, 채팅 테이블과 진행도 테이블을 생성합니다.
     * 또한 유저의 소속 그룹 정보를 수정합니다.
     *
     * @param data 입장한 그룹 정보를 포함하는 JSON 객체
     */
    public static void enterGroup(JSONObject data) {
        int uid = Integer.parseInt(data.get("uid").toString());
        int gid = Integer.parseInt(data.get("gid").toString());

        String title = data.get("title").toString();
        String description = data.get("description").toString();
        String mission = data.get("mission").toString();
        Integer capacity = Integer.parseInt(data.get("capacity").toString());
        Integer category = Integer.parseInt(data.get("category").toString());
        Integer usercnt = Integer.parseInt(data.get("usercnt").toString());
        String deadline = data.get("deadline").toString();
        String startDate = data.get("startDate").toString();
        String endDate = data.get("endDate").toString();
        String users = data.get("users").toString();
        JSONArray userData = (JSONArray) data.get("userData");

        for (Object o : userData) {
            JSONObject user = (JSONObject) o;
            int userId = Integer.parseInt(user.get("uid").toString());
            String nickname = user.get("nickname").toString();
            String pfp = user.get("pfp").toString();
            if (!pfp.isEmpty()){
                String fileName = Path.of(pfp).getFileName().toString();
                pfp = path.toString() + "\\" + fileName;
                if (!Files.exists(Path.of(pfp))){
                    JSONObject object = new JSONObject();
                    object.put("fileName", Path.of(pfp).getFileName().toString());
                    ClientSocket.send(new Request(RequestType.GETFILE, object));
                }
            }
            String groups = user.get("groups").toString();

            String sql = String.format("INSERT OR REPLACE INTO USER VALUES (%d, '%s', '%s', '%s')",userId,nickname,pfp,groups);
            executeSQL(sql);
        }

        String sql = String.format("""
                    INSERT OR REPLACE INTO GROUPS VALUES (%d, '%s', '%s', '%s', %d, %d, %d, '%s', '%s', '%s', '%s')""", gid, title, description, mission, capacity, category, usercnt, deadline, startDate, endDate, users);

        executeSQL(sql);

        sql = String.format("SELECT groups FROM USER WHERE uid=%d", uid);
        String groups = (executeQuery(sql).get("groups").toString()) + gid + ",";

        sql = String.format("""
                    UPDATE USER SET groups='%s' WHERE uid=%d""", groups, uid);
        executeSQL(sql);

        sql = String.format("""
                    CREATE TABLE IF NOT EXISTS G%sCHAT (chatId integer primary key, uid integer not null, message chat not null, isPic integer not null)""", gid);
        createTable("G"+gid+"CHAT", sql);

        sql = String.format("""
                    CREATE TABLE IF NOT EXISTS G%sPROGRESS (uid integer not null, date string not null)""", gid);
        createTable("G"+gid+"PROGRESS", sql);
    }

    /**
     * 특정 사용자의 정보를 조회합니다.
     *
     * @param uid 조회할 사용자의 UID
     * @return 사용자 정보를 포함하는 JSON 객체
     */
    public static JSONObject getUserInfo(int uid) {
        String sql = String.format("""
                SELECT * FROM USER WHERE uid=%d""", uid);
        return executeQuery(sql);
    }

    /**
     * 현재 로그인한 사용자의 정보를 조회합니다.
     *
     * @return 현재 사용자 정보를 포함하는 JSON 객체
     */
    public static JSONObject getMyInfo() {
        String sql = String.format("SELECT * FROM USER WHERE uid=%d", myUid);
        return executeQuery(sql);
    }

    /**
     * 특정 그룹의 사용자 목록을 조회합니다.
     *
     * @param gid 조회할 그룹의 GID
     * @return 사용자 정보를 포함하는 JSON 객체의 배열
     */
    public static JSONArray getGroupUsers(int gid) {
        JSONArray result = new JSONArray();
        String sql = String.format("SELECT users FROM GROUPS WHERE gid=%d", gid);
        try{
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            String users = rs.getString("users");
            statement.close();
            rs.close();
            sql = String.format("SELECT * FROM USER WHERE uid in (%s)", users.substring(0, users.length()-1));

            PreparedStatement statement1 = conn.prepareStatement(sql);
            ResultSet rs1 = statement1.executeQuery();
            while (rs1.next()) {
                JSONObject object = new JSONObject();
                object.put("uid", rs1.getInt("uid"));
                object.put("nickname", rs1.getString("nickname"));
                object.put("pfp", rs1.getString("pfp"));
                result.add(object);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 현재 로그인한 사용자의 그룹중 활동 기간이 끝나지 않은 그룹의 목록을 조회합니다.
     *
     * @return 활동 기간이 끝나지 않은 그룹 정보를 포함하는 Group 객체의 리스트
     */
    public static List<Group> getMyGroupList() {
        List<Group> groupList = new ArrayList<>();
        String sql = "SELECT * FROM GROUPS WHERE (endDate >= date('now', 'localtime'))";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                JSONObject group = new JSONObject();
                group.put("gid", rs.getInt("gid"));
                group.put("title", rs.getString("title"));
                group.put("description", rs.getString("description"));
                group.put("mission", rs.getString("mission"));
                group.put("capacity", rs.getInt("capacity"));
                group.put("category", rs.getInt("category"));
                group.put("usercnt", rs.getInt("usercnt"));
                group.put("deadline", rs.getString("deadline"));
                group.put("startDate", rs.getString("startDate"));
                group.put("endDate", rs.getString("endDate"));
                groupList.add(new Group(group));
            }
            rs.close();
        }catch (SQLException e) {
            e.printStackTrace();;
        }
        return groupList;
    }

    /**
     * 종료된 그룹 목록을 조회합니다.
     *
     * @return 종료된 그룹 목록을 포함하는 Group 객체의 리스트
     */
    public static List<Group> getMyEndedGroupList() {
        List<Group> groupList = new ArrayList<>();
        String sql = "SELECT * FROM GROUPS WHERE (endDate < date('now', 'localtime'))";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                JSONObject group = new JSONObject();
                group.put("gid", rs.getInt("gid"));
                group.put("title", rs.getString("title"));
                group.put("description", rs.getString("description"));
                group.put("mission", rs.getString("mission"));
                group.put("capacity", rs.getInt("capacity"));
                group.put("category", rs.getInt("category"));
                group.put("usercnt", rs.getInt("usercnt"));
                group.put("deadline", rs.getString("deadline"));
                group.put("startDate", rs.getString("startDate"));
                group.put("endDate", rs.getString("endDate"));
                groupList.add(new Group(group));
            }
            rs.close();
        }catch (SQLException e) {
            e.printStackTrace();;
        }
        return groupList;
    }

    /**
     * 특정 그룹의 진행도 정보를 조회합니다.
     *
     * @param gid 조회할 그룹의 GID
     * @return 진행도 정보를 포함하는 JSON 배열
     */
    public static JSONArray getGroupProgress(int gid) {
        JSONArray result = new JSONArray();
        String sql = String.format("SELECT * FROM G%dPROGRESS", gid);
        try{
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                JSONObject progress = new JSONObject();
                progress.put("uid", rs.getInt("uid"));
                progress.put("date", rs.getString("date"));
                result.add(progress);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 특정 그룹의 채팅 데이터를 조회합니다.
     *
     * @param gid 조회할 그룹의 GID
     * @return 채팅 데이터를 포함하는 JSON 배열
     */
    public static JSONArray getChatData(int gid) {
        JSONArray result = new JSONArray();
        String sql = String.format("SELECT * FROM G%dCHAT ORDER BY chatId DESC LIMIT 50", gid);
        try{
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                JSONObject chat = new JSONObject();
                chat.put("uid", rs.getInt("uid"));
                chat.put("isPic", rs.getInt("isPic"));
                chat.put("message", rs.getString("message"));
                chat.put("chatId", rs.getInt("chatId"));
                result.add(chat);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 서버로부터 전송된 파일을 저장합니다.
     *
     * @param request 파일 저장 요청 정보를 포함하는 Request 객체
     */
    public static void saveFile(Request request) {
        String fileName = request.getData().get("fileName").toString();
        fileName = path.toString() + "\\" + fileName;
        try {
            Files.write(Path.of(fileName), request.file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
