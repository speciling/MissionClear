package server.db;

import server.group.Group;
import server.user.User;

import java.io.File;
import java.sql.*;

public class DBManager {
    private static Connection conn = null;

    public static void init() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:server.db");
            createTable("USER", "CREATE TABLE IF NOT EXISTS USER (" +
                    "uid integer primary key autoincrement, id string not null unique, " +
                    "password string not null, nickname string not null)");
            createTable("GROUPS", "CREATE TABLE IF NOT EXISTS GROUPS (" +
                    "gid integer primary key autoincrement, title string not null, description string not null, " +
                    "mission string not null, capacity int not null, category int not null, deadline string not null, " +
                    "startDate string not null, endDate string not null, password int)");


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static ResultType executeSQL(String SQL) throws SQLException {
        Statement statement = null;
        ResultType result;
        try {
            statement = conn.createStatement();
            statement.execute(SQL);
            result = ResultType.SUCCESS;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            result = ResultType.FAILURE;
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    private static boolean checkTable(String tableName) throws SQLException {
        DatabaseMetaData metaData = conn.getMetaData();
        ResultSet tables = metaData.getTables(null, null, tableName, null);
        return (tables.next() && tables.getRow() != 0);
    }

    private static ResultType createTable(String tableName, String SQL) throws SQLException {
        if (checkTable(tableName))
            return ResultType.WARNING;

        return executeSQL(SQL);
    }

    private static ResultType dropTable(String tableName) throws SQLException {
        if( !checkTable(tableName) ) {
            return ResultType.WARNING;
        }

        return executeSQL("DROP TABLE IF EXISTS "+tableName);
    }

    public static ResultType addUser(User user) throws SQLException {
        return ResultType.WARNING;
    }

    public static int checkLogin(String id, String pw) throws SQLException{
        return -1;
    }

    public static String getData() throws SQLException{
        return "";
    }

    public static ResultType createGroupTable() throws SQLException {
        return ResultType.WARNING;
    }

    public static ResultType enterGroup(int uid, int gid) throws SQLException {
        return ResultType.WARNING;
    }

    public static int saveChatMessage(int uid, int gid, String chatMessage) throws SQLException {
        return 0;
    }

    public static int saveCertifyPicture(int uid, int gid, File picture) throws SQLException {
        return 0;
    }

    public static ResultType changeNickname(int uid, String nickname) throws SQLException {
        return ResultType.WARNING;
    }

    public static ResultType changePFP(int uid, File picture) throws SQLException {
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
