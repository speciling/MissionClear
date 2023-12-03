package server.service;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.nio.ByteBuffer;

/**
 * {@code Request} 클래스는 클라이언트와 서버 간 통신에서 사용되는 요청을 표현하는 클래스입니다.
 *
 * @author 지연우
 */
public class Request {
    public RequestType type;
    public String bodyString;
    public byte[] file = null;
    private JSONObject data;

    /**
     * {@code Request} 클래스의 생성자입니다.
     *
     * @param type 요청의 유형을 나타내는 바이트 값
     * @param body 요청의 본문 문자열(JSON형태)
     */
    public Request(byte type, String body) {
        this.type = RequestType.of(type);
        JSONParser parser = new JSONParser();
        try {
            data = (JSONObject) parser.parse(body);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * {@code Request} 클래스의 생성자입니다.
     *
     * @param requestType 요청의 유형을 나타내는 열거형 상수
     * @param data 요청에 포함된 데이터를 담는 {@code JSONObject} 객체
     */
    public Request(RequestType requestType, JSONObject data) {
        this.type = requestType;
        this.data = data;
    }

    /**
     * 요청에 포함된 데이터를 반환합니다.
     *
     * @return {@code JSONObject} 형태의 데이터
     */
    public JSONObject getData() { return data; }

    /**
     * 요청 유형과 데이터를 {@code ByteBuffer}로 변환하여 반환합니다.
     *
     * @param type 요청의 유형을 나타내는 열거형 상수
     * @param data 요청의 데이터를 담는 {@code JSONObject} 객체
     * @return 변환된 {@code ByteBuffer} 객체
     */
    public static ByteBuffer toByteBuffer(RequestType type, JSONObject data) {
        String jsonString = data.toJSONString();
        byte[] bytes = jsonString.getBytes();
        ByteBuffer bf = ByteBuffer.allocate(bytes.length+5);
        bf.put((byte)type.getCode());
        bf.putInt(bytes.length);
        bf.put(bytes);
        bf.flip();
        return bf;
    }

    /**
     * 요청을 {@code ByteBuffer}로 변환하여 반환합니다.
     *
     * @param request {@code Request} 객체
     * @return 변환된 {@code ByteBuffer} 객체
     */
    public static ByteBuffer toByteBuffer(Request request) {
        RequestType type = request.type;
        JSONObject data = request.getData();
        String jsonString = data.toJSONString();
        byte[] bytes = jsonString.getBytes();
        ByteBuffer bf = ByteBuffer.allocate(bytes.length+5 + (request.file!=null? request.file.length+4: 0));
        bf.put((byte)type.getCode());
        bf.putInt(bytes.length);
        bf.put(bytes);
        if (request.file != null) {
            bf.putInt(request.file.length);
            bf.put(request.file);
        }
        bf.flip();
        return bf;
    }

}
