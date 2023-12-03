package server.db;

import org.json.simple.JSONObject;
import server.service.Request;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.IntStream;

/**
 * `DBManager` 클래스는 데이터베이스 작업을 처리하며 SQL 쿼리 실행, 테이블 생성 및 관리, 채팅 메시지 및 인증 사진 저장과 관련된 메서드를 제공합니다.
 *
 * @see ServerDBManager
 * @see client.db.ClientDBManager
 * @author 지연우
 */
public class DBManager {
    protected static Connection conn = null;
    protected static Path path = null;

    /**
     * 결과 집합을 반환하지 않는 SQL 쿼리를 실행합니다.
     *
     * @param sql 실행할 SQL 쿼리
     * @return 성공, 경고 또는 실패를 나타내는 결과 유형
     */
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

    /**
     * 결과 집합을 나타내는 JSON 객체를 반환하는 SQL 쿼리를 실행합니다.
     *
     * @param sql 실행할 SQL 쿼리
     * @return 쿼리 결과와 결과 유형을 포함하는 JSON 객체
     */
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

    /**
     * 테이블이 존재하는지 확인하는 메서드.
     */
    private static boolean checkTable(String tableName) throws SQLException {
        DatabaseMetaData metaData = conn.getMetaData();
        try (ResultSet tables = metaData.getTables(null, null, tableName, null);) {
            return (tables.next() && tables.getRow() != 0);
        }
    }

    /**
     * 지정된 이름과 SQL 정의로 데이터베이스에 테이블을 생성합니다.
     *
     * @param tableName 테이블을 생성할 이름
     * @param SQL       테이블의 SQL 정의
     * @return 성공, 경고 또는 실패를 나타내는 결과 유형
     */
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

    /**
     * 데이터베이스에 채팅 메시지를 저장합니다.
     *
     * @param data 채팅 메시지 세부 정보를 포함하는 JSON 객체
     * @return 성공한 경우 채팅 ID, 실패한 경우 -1
     */
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

    /**
     * 데이터베이스에 인증 사진 관련 정보를 저장합니다.
     *
     * @param request 인증 사진 세부 정보를 포함하는 요청 객체
     * @return 성공한 경우 채팅 ID, 실패한 경우 -1
     */
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

    /**
     * 데이터베이스에서 사용자의 닉네임을 변경합니다.
     *
     * @param uid      닉네임을 변경할 사용자 ID
     * @param nickname 새로운 닉네임
     * @return 성공여부를 나타내는 결과 유형
     */
    public static ResultType changeNickname(int uid, String nickname){
        String sql = String.format("UPDATE USER SET nickname='%s' WHERE uid=%d", nickname, uid);
        ResultType resultType = executeSQL(sql);
        return resultType;
    }

    /**
     * 데이터베이스에서 사용자의 프로필 사진(PFP)을 변경합니다.
     *
     * @param data     사용자 및 파일 세부 정보를 포함하는 JSON 객체
     * @param fileData PFP 이미지를 나타내는 바이트 배열
     * @return 성공여부를 나타내는 결과 유형
     */
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
}
