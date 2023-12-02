package server.service;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 요청의 종류를 나타내기 위한 Enum 클래스.
 *
 * @author 지연우
 */
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

    /**
     * RequestType enum 생성자
     *
     * @param code 요청의 타입에 대한 코드
     */
    RequestType(int code) {
        this.code = code;
    }

    /**
     * 요청 타입에 대한 코드를 얻는 메소드
     *
     * @return 요청 타입에 대한 코드
     */
    public int getCode() {
        return code;
    }

    /**
     * 요청 타입에 대한 이름을 얻는 메소드
     *
     * @return 요청 타입의 이름
     */
    public String getName() {
        return name();
    }

    /**
     * 주어진 코드에 해당하는 RequestType enum을 얻는 함수
     *
     * @param code 요청 유형과 연관된 정수 코드
     * @return 주어진 코드에 해당하는 RequestType enum
     */
    public static RequestType of(int code) {
        return RequestType.valueOf(CODE_MAP.get(code));
    }
}
