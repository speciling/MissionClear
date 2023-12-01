package server.db;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import server.group.Group;
import server.service.Request;
import server.user.User;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DBManager {
    protected static Connection conn = null;
    protected static Path path = null;

    protected static ResultType executeSQL(String sql) {
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

    protected static JSONObject executeQuery(String sql) {
        JSONObject result = new JSONObject();
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
                    colNames.forEach(cn -> {
                        try {
                            result.put(cn, resultSet.getObject(cn));
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    });
                }
                if (result.isEmpty())
                    result.put("resultType", ResultType.WARNING.getCode());
                else
                    result.put("resultType", ResultType.SUCCESS.getCode());
                return result;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            result.put("resultType", ResultType.FAILURE.getCode());
            return result;
        }
    }

    private static boolean checkTable(String tableName) throws SQLException {
        DatabaseMetaData metaData = conn.getMetaData();
        try (ResultSet tables = metaData.getTables(null, null, tableName, null);) {
            return (tables.next() && tables.getRow() != 0);
        }
    }

    // 성공시 SUCCESS, 해당 이름의 테이블이 존재하면 WARNING, 실패시 FAILURE 반환
    protected static ResultType createTable(String tableName, String SQL) {
        try {
            if (checkTable(tableName))
                return ResultType.WARNING;

            return executeSQL(SQL);
        } catch (SQLException e) {
            e.printStackTrace();
            return  ResultType.FAILURE;
        }
    }

    protected static ResultType dropTable(String tableName) {
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

    // 저장 성공시 채팅 번호 반환, 실패시 -1 반환
    public static int saveChatMessage(JSONObject data){
        Integer uid = Integer.parseInt(data.get("uid").toString());
        Integer gid = Integer.parseInt(data.get("gid").toString());
        Integer isPic = Integer.parseInt(data.get("isPic").toString());
        String msg = (String)data.get("message");

        if (data.get("chatId") == null) {
            String sql = String.format("""
                    INSERT INTO G%dCHAT (uid, message, isPic) VALUES (%d, '%s', %d)""", gid, uid, msg, isPic);
            executeSQL(sql);

            sql = "SELECT last_insert_rowid()";
            Integer chatId = (Integer) executeQuery(sql).get("last_insert_rowid()");
            return chatId;
        }

        Integer chatId = Integer.parseInt(data.get("chatId").toString());
        String sql = String.format("""
                INSERT INTO G%dCHAT (chatId, uid, message, isPic) VALUES (%d, %d, '%s', %d)""", gid, chatId, uid, msg, isPic);
        executeSQL(sql);
        return chatId;
    }

    // 저장 성공시 채팅 번호 반환, 실패시 -1 반환
    public static int saveCertifyPicture(Request request){
        int uid = Integer.parseInt(request.getData().get("uid").toString());
        int gid = Integer.parseInt(request.getData().get("gid").toString());

        String fileName = request.getData().get("fileName").toString();
        String extension = fileName.substring(fileName.lastIndexOf('.')+1);
        if (!extension.equals("jpg") && !extension.equals("jpeg") && !extension.equals("png")) {
            return -1;
        }

        Path filePath = Path.of(path.toString() + '\\' + "G" + gid + "U" + uid + LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE) + "certify." + extension);
        try {
            Files.write(filePath, request.file);
        } catch (IOException e ) {
            return -1;
        }
        String sql = String.format("INSERT INTO G%dPROGRESS (uid, date) VALUES (%d, date('now', 'localtime'))", gid, uid);
        executeSQL(sql);
        request.getData().put("isPic", 1);
        request.getData().put("message", filePath.toString());

        return saveChatMessage(request.getData());
    }

    public static ResultType changeNickname(int uid, String nickname){
        String sql = String.format("UPDATE USER SET nickname='%s' WHERE uid=%d", nickname, uid);
        ResultType resultType = executeSQL(sql);
        return resultType;
    }

    public static ResultType changePFP(JSONObject data, byte[] fileData){
        int uid = Integer.parseInt(data.get("uid").toString());

        String filePath = data.get("fileName").toString();
        String extension = filePath.substring(filePath.lastIndexOf('.')+1);
        if (!extension.equals("jpg") && !extension.equals("jpeg") && !extension.equals("png")) {
            return ResultType.WARNING;
        }

        Path pfpPath = Path.of(path.toString() + '\\' + uid + "pfp." + extension);
        try {
            Files.write(pfpPath, fileData);
        } catch (IOException e ) {
            return ResultType.WARNING;
        }

        String sql = String.format("UPDATE USER SET pfp='%s' WHERE uid=%d", pfpPath.toString(), uid);

        return executeSQL(sql);
    }

    public static ResultType createGroupDataTables(int gid) {

        // 그룹 채팅 테이블과 그룹 진행도 테이블 생성
        String tableName = "G"+gid+"Chat";
        String sql = String.format("""
                    CREATE TABLE IF NOT EXISTS %s 
                    (chatId integer primary key, uid integer not null, text string not null)""", tableName);
        createTable(tableName, sql);

        tableName = "G"+gid+"Progress";
        String[] users = ((String) executeQuery("SELECT users FROM GROUPS WHERE gid="+gid).get("users")).split(",");
        StringBuilder colums = new StringBuilder();
        for (String uid : users){
            colums.append(", ").append(uid).append(" integer default 0");
        }
        sql = String.format("""
                    CREATE TABLE IF NOT EXISTS %s 
                    (date string not null%s)""", tableName, colums.toString());
        createTable(tableName, sql);

        return ResultType.SUCCESS;
    }

}
