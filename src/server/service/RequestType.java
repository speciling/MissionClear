package server.service;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum RequestType {
    LOGIN(1),
    SIGNUP(2),
    SENDDATA(3),
    GETRECRUITINGGROUPDATA(4),
    CREATENEWGROUP(5),
    ENTERGROUP(6),
    CHAT(7),
    CERTIFYMISSION(8),
    CHANGEPFP(9),
    CHANGENICKNAME(10),
    GETFILE(11);

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
