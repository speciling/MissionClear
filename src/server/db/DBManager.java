package server.db;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import server.group.Group;
import server.user.User;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DBManager {

    private static Connection conn = null;

    public static void init() {
        try {
            Path path = Path.of("./missioncleardata/server/pictures");
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
            conn = DriverManager.getConnection("jdbc:sqlite:missioncleardata/server/server.db");

            createTable("USER", """
                    CREATE TABLE IF NOT EXISTS USER (uid integer primary key, id string not null unique,
                     password string not null, nickname string not null, groups string default '')""");

            createTable("GROUPS", """
                    CREATE TABLE IF NOT EXISTS GROUPS (gid integer primary key, 
                    title string not null, description string not null, mission string not null, 
                    capacity integer not null, category integer not null, usercnt integer not null, 
                    deadline string not null, startDate string not null, endDate string not null, 
                    password string not null, users string not null)""");


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static ResultType executeSQL(String sql) {
        try (Statement statement = conn.createStatement();) {
            statement.execute(sql);
            return ResultType.SUCCESS;
        } catch (SQLException e) {
            if (Arrays.stream(e.getMessage().split(" ")).toArray()[0].equals("[SQLITE_CONSTRAINT_UNIQUE]") )
                return ResultType.WARNING;
            else {
                e.printStackTrace();
                return ResultType.FAILURE;
            }
        }
    }

    private static ResultType executeQuery(JSONArray result, String sql) {
        try (Statement statement = conn.createStatement();) {
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
                    JSONObject row = new JSONObject();
                    colNames.forEach(cn -> {
                        try {
                            row.put(cn, resultSet.getObject(cn));
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    });
                    result.add(row);
                }
                return ResultType.SUCCESS;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return ResultType.FAILURE;
        }
    }

    private static boolean checkTable(String tableName) throws SQLException {
        DatabaseMetaData metaData = conn.getMetaData();
        try (ResultSet tables = metaData.getTables(null, null, tableName, null);) {
            return (tables.next() && tables.getRow() != 0);
        }
    }

    // 성공시 SUCCESS, 해당 이름의 테이블이 존재하면 WARNING, 실패시 FAILURE 반환
    private static ResultType createTable(String tableName, String SQL) {
        try {
            if (checkTable(tableName))
                return ResultType.WARNING;

            return executeSQL(SQL);
        } catch (SQLException e) {
            e.printStackTrace();
            return  ResultType.FAILURE;
        }
    }

    private static ResultType dropTable(String tableName) {
        try {
            if( !checkTable(tableName) ) {
                return ResultType.WARNING;
            }

            return executeSQL("DROP TABLE IF EXISTS "+tableName);
        } catch (SQLException e) {
            e.printStackTrace();
            return ResultType.FAILURE;
        }
    }

    // 성공시 SUCCESS, 이미 존재하는 아이디면 WARNING, 에러 발생시 FAILURE 반환
    public static ResultType addUser(String id, String pw, String nickname) {
        String sql = String.format("""
                INSERT INTO USER (id, password, nickname)
                VALUES ('%s', '%s', '%s')""", id, pw, nickname);
        return executeSQL(sql);
    }

    // 성공시 SUCCESS, 아이디나 비밀번호가 잘못됐으면 WARNING, 에러 발생시 FAILURE 반환
    public static ResultType checkLogin(JSONArray result,String id, String pw){
        String sql = String.format("""
                SELECT uid, groups FROM USER WHERE id='%s' AND password='%s'""", id, pw);

        ResultType resultType = executeQuery(result, sql);

        if (result.isEmpty())
            return ResultType.WARNING;

        return resultType;
    }

    public static ResultType getGroupsData(JSONArray result, Map<Integer, Integer> request){
        return ResultType.FAILURE;
    }

    public static ResultType getChatData(JSONArray result, Map<Integer, Integer> request){
        return ResultType.FAILURE;
    }

    public static ResultType getProfileData(JSONArray result, Map<Integer, Integer> request){
        return ResultType.FAILURE;
    }

    public static ResultType createGroupTable(){
        return ResultType.WARNING;
    }

    public static ResultType enterGroup(int uid, int gid){
        return ResultType.WARNING;
    }

    // 저장 성공시 채팅 번호 반환, 실패시 -1 반환
    public static int saveChatMessage(int uid, int gid, String chatMessage){
        return -1;
    }

    // 저장 성공시 채팅 번호 반환, 실패시 -1 반환
    public static int saveCertifyPicture(int uid, int gid, Path picture){
        return -1;
    }

    public static ResultType changeNickname(int uid, String nickname){
        return ResultType.WARNING;
    }

    public static ResultType changePFP(int uid, Path picture){
        return ResultType.WARNING;
    }




    // 결과 코드 정의
    public enum ResultType {
        SUCCESS(1),     // 성공
        WARNING(0),     // 경고
        FAILURE(-1);    // 실패

        // 코드 변수
        private int code = 0;

        // 코드값 설정
        private ResultType(int code){
            this.code = code;
        }

        // 코드값 가져오기
        public int getCode() {
            return this.code;
        }
    }
}
