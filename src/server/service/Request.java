package server.service;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Request {
    public RequestType type;
    public String bodyString;
    private JSONObject data;

    public Request(byte type, String body) {
        this.type = RequestType.of(type);
        this.bodyString = body;
        JSONParser parser = new JSONParser();
        try {
            parser.parse(bodyString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public JSONObject getData() { return data; }

    public static ByteBuffer getPacket(RequestType type, JSONObject data) {
        return null;
    }

}
