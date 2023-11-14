package server.db;

import org.json.simple.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
                    password string not null, nickname string not null, pfp string, groups string default '')""");

            createTable("GROUPS", """
                    CREATE TABLE IF NOT EXISTS GROUPS (gid integer primary key, 
                    title string not null, description string not null, mission string not null, 
                    capacity integer not null, category integer not null, usercnt integer default 1, 
                    deadline string not null, startDate string not null, endDate string not null, 
                    password string, users string not null)""");


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
    public static  JSONObject getUser(int uid) {
        String sql = String.format("""
                SELECT uid, groups, nickname, pfp FROM USER WHERE uid=%s""", uid);

        JSONObject result = executeQuery(sql);

        if (result.isEmpty())
            result.put("resultType", ResultType.WARNING.getCode());

        return result;
    }

    public static JSONObject getUser(String id, String pw) {
        String sql = String.format("""
                SELECT uid, groups, nickname FROM USER WHERE id='%s' AND password='%s'""", id, pw);

        JSONObject result = executeQuery(sql);

        if (result.isEmpty())
            result.put("resultType", ResultType.WARNING.getCode());

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
        JSONObject groupInfo = new JSONObject();
        addUser("t5", "tt", "");
        groupInfo.put("title", "t1");
        groupInfo.put("title", "t1");
        groupInfo.put("description", "t1");
        groupInfo.put("mission", "t1");
        groupInfo.put("capacity", 3);
        groupInfo.put("category", 3);
        groupInfo.put("deadline", "2023-11-15");
        groupInfo.put("startDate", "");
        groupInfo.put("endDate", "");
        groupInfo.put("password", null);
        groupInfo.put("uid", 1);
        System.out.println(createGroup(groupInfo));
        System.out.println(executeQuery("SELECT * FROM USER WHERE uid=1"));
        System.out.println(executeQuery("SELECT * FROM GROUPS WHERE gid=3"));
    }

    public static JSONObject createGroup(JSONObject groupInfo){
        // GROUPS 테이블에 그룹 row 추가
        String title = (String) groupInfo.get("title");
        String description = (String) groupInfo.get("description");
        String mission = (String) groupInfo.get("mission");
        Integer capacity = (Integer) groupInfo.get("capacity");
        Integer category = (Integer) groupInfo.get("category");
        String deadline = (String) groupInfo.get("deadline");
        String startDate = (String) groupInfo.get("startDate");
        String endDate = (String) groupInfo.get("endDate");
        String password = (String) groupInfo.get("password");
        Integer uid = (Integer) groupInfo.get("uid");

        JSONObject result = new JSONObject();
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

            // user의 groups에 group 추가
            sql = "SELECT groups FROM USER WHERE uid=" + uid;
            String groups = (String) executeQuery(sql).get("groups");
            groups += gid+",";
            sql = String.format("UPDATE USER SET groups='%s' WHERE uid=%d", groups, uid);
            executeSQL(sql);
        }
        return result;
    }

    public static ResultType enterGroup(int uid, int gid){

        return ResultType.WARNING;
    }
}
