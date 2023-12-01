package client.db;

import client.Client;
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

public class ClientDBManager extends DBManager {
    public static int myUid;

    public static void init() {
        try {
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

    public static JSONObject getLastData() {
        return null;
    }

    public static void login(JSONObject data) {
        Integer uid = Integer.parseInt(data.get("uid").toString());
        String nickname = data.get("nickname").toString();
        String pfp = data.get("pfp").toString();
        String groups = data.get("groups").toString();
        myUid = uid;

        String sql = String.format("INSERT OR REPLACE INTO USER (uid, nickname, pfp, groups) VALUES (%d, '%s', '%s', '%s')", uid, nickname, pfp, groups);
        executeSQL(sql);
    }

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

                JSONObject object = new JSONObject();
                object.put("fileName", fileName);
                ClientSocket.send(new Request(RequestType.GETFILE, object));
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
                if (isPic == 1) {
                    JSONObject object = new JSONObject();
                    object.put("fileName", message);
                    message = path.toString() + "\\" + message;
                    ClientSocket.send(new Request(RequestType.GETFILE, object));
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

        String sql = String.format("""
                INSERT INTO GROUPS 
                (title, description, mission, capacity, category, deadline, startDate, endDate, users)
                VALUES ('%s', '%s', '%s', %d, %d, '%s', '%s', '%s', '%s')""",
                title, description, mission, capacity, category, deadline, startDate, endDate, uid+",");

        executeSQL(sql).getCode();

        Integer gid = Integer.parseInt(group.get("gid").toString());
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

    public static void enterGroup(JSONObject data) {
        int uid = Integer.parseInt(data.get("uid").toString());
        int gid = Integer.parseInt(data.get("gid").toString());

        String sql = String.format("SELECT users FROM GROUPS WHERE gid=%d", gid);
        String users = ((String)executeQuery(sql).get("users")) + uid + ",";

        sql = String.format("SELECT groups FROM USER WHERE uid=%d", uid);
        String groups = (executeQuery(sql).get("groups").toString()) + gid + ",";

        sql = String.format("""
                    UPDATE GROUPS SET users='%s' WHERE gid=%d""", users, gid);
        executeSQL(sql);

        sql = String.format("""
                    UPDATE GROUPS SET usercnt=usercnt+1 WHERE gid='%s'""", gid);
        executeSQL(sql);

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

    public static JSONObject getUserInfo(int uid) {
        String sql = String.format("""
                SELECT * FROM USER WHERE uid=%d""", uid);
        return executeQuery(sql);
    }

    public static JSONObject getMyInfo() {
        String sql = String.format("SELECT * FROM USER WHERE uid=%d", myUid);
        return executeQuery(sql);
    }

    public static JSONObject getGroupInfo(int gid) {
        String sql = String.format("""
                SELECT * FROM GROUPS WHERE gid=%d""", gid);
        return executeQuery(sql);
    }

    public static JSONArray getGroupUsers(int gid) {
        JSONArray result = new JSONArray();
        String sql = String.format("SELECT users FROM GROUPS WHERE gid=%d", gid);
        try{
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            String users = rs.getString("users");
            statement.close();
            rs.close();
            sql = String.format("SELECT * FROM USER WHERE uid in ('%s')", users.substring(0, users.length()-1));

            PreparedStatement statement1 = conn.prepareStatement(sql);
            ResultSet rs1 = statement1.executeQuery();
            while (rs1.next()) {
                JSONObject object = new JSONObject();
                object.put("uid", rs1.getInt("uid"));
                object.put("nickname", rs1.getInt("nickname"));
                object.put("pfp", rs1.getString("pfp"));
                result.add(object);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static Integer[] getMyGroupList() {
        return null;
    }

    public static Integer[] getMyEndedGroupList() {
        return null;
    }

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
