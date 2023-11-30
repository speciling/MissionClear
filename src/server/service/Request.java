package server.service;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Request {
    public RequestType type;
    public String bodyString;
    public byte[] file = null;
    private JSONObject data;

    public Request(byte type, String body) {
        this.type = RequestType.of(type);
        JSONParser parser = new JSONParser();
        try {
            data = (JSONObject) parser.parse(body);
        } catch (ParseException e) {
            System.out.println(this.type);
            System.out.println(data);
            e.printStackTrace();
        }
    }

    public Request(RequestType requestType, JSONObject data) {
        this.type = requestType;
        this.data = data;
    }

    public JSONObject getData() { return data; }

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
