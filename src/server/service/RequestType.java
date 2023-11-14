package server.service;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum RequestType {
    LOGIN(1),
    SIGNUP(2),
    SENDDATA(3),
    CREATENEWGROUP(4),
    ENTERGROUP(5),
    CHAT(6),
    CERTIFYMISSION(7),
    CHANGEPFP(8),
    CHANGENICKNAME(9);

    private static final Map<Integer, String> CODE_MAP = Collections.unmodifiableMap(
            Stream.of(values()).collect(Collectors.toMap(RequestType::getCode, RequestType::getName)));


    private final int code;

    RequestType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name();
    }

    public static RequestType of(int code) {
        return RequestType.valueOf(CODE_MAP.get(code));
    }
}
