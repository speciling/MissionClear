package client.db;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import server.db.DBManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;

public class ClientDBManager extends DBManager {

    public static void init() {
        try {
            path = Path.of("./missioncleardata/client/pictures");
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
            path = Path.of("./missioncleardata/client/client.db");
            try{
                Files.deleteIfExists(path);
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

    public static void saveInitData(JSONObject data) {
        JSONArray userData = (JSONArray) data.get("userData");
        for (int i = 0; i < userData.size(); i++) {
            JSONObject user = (JSONObject) userData.get(i);
            Integer uid = Integer.parseInt(user.get("uid").toString());
            String nickname = (String) user.get("nickname");
            String pfp = (String) user.get("pfp");
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
            String title = (String) group.get("title");
            String description = (String) group.get("description");
            String mission = (String) group.get("mission");
            Integer capacity = Integer.parseInt(group.get("capacity").toString());
            Integer category = Integer.parseInt(group.get("category").toString());
            Integer usercnt = Integer.parseInt(group.get("usercnt").toString());
            String deadline = (String) group.get("deadline");
            String startDate = (String) group.get("startDate");
            String endDate = (String) group.get("endDate");
            String users = (String) group.get("users");
            String sql = String.format("""
                    INSERT OR REPLACE INTO GROUPS (gid, title, description, mission, capacity, category, usercnt, deadline, startDate, endDate, users) 
                    VALUES (%d, '%s', '%s', '%s', %d, %d, %d, '%s', '%s', '%s', '%s')""", gid, title, description, mission, capacity, category, usercnt, deadline, startDate, endDate, users);
            executeSQL(sql);

            JSONArray chattings = (JSONArray) chatData.get("G"+gid+"CHAT");
            sql = String.format("""
                    CREATE TABLE IF NOT EXISTS G%sCHAT (chatId integer primary key, uid integer not null, message chat not null)""", gid);
            createTable("G"+gid+"CHAT", sql);
            for (int j = 0; j < chattings.size(); j++) {
                JSONObject chatting = (JSONObject) chattings.get(j);
                Integer chatId = Integer.parseInt(chatting.get("chatId").toString());
                Integer uid = Integer.parseInt(chatting.get("uid").toString());
                String message = chatting.get("message").toString();
                sql = String.format("""
                        INSERT INTO G%dCHAT (chatId, uid, message) VALUES (%d, %d, '%s')""", gid, chatId, uid, message);
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

    public static void createNewGroup(JSONObject group) {}

    public static void enterGroup(JSONObject group) {}

    public static JSONObject getUserInfo(int uid) {
        String sql = String.format("""
                SELECT * FROM USER WHERE uid=%d""", uid);
        return executeQuery(sql);
    }

    public static JSONObject getMyInfo(int uid) { return null; }

    public static JSONObject getGroupInfo(int gid) {
        String sql = String.format("""
                SELECT * FROM GROUPS WHERE gid=%d""", gid);
        return executeQuery(sql);
    }

    public static Integer[] getMyGroupList() {
        return null;
    }

    public static Integer[] getMyEndedGroupList() {
        return null;
    }

    public static JSONObject getGroupProgress(int gid) {

        return null;
    }

    public static JSONArray getChatData(int gid) {
        JSONArray result = new JSONArray();
        String sql = String.format("""
                SELECT * FROM G%dCHAT ORDER BY chatId DESC LIMIT 10""");
        return result;
    }

}
