package server.db;

import client.net.ClientSocket;
import server.service.Request;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    private static final Map<Integer, String> CODE_MAP = Collections.unmodifiableMap(
            Stream.of(values()).collect(Collectors.toMap(ResultType::getCode, ResultType::getName)));


    // 코드값 가져오기
    public int getCode() {
        return this.code;
    }

    public String getName() {
        return name();
    }

    public static ResultType of(int code) {
        return ResultType.valueOf(CODE_MAP.get(code));
    }
}