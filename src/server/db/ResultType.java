package server.db;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * {@code ResultType} 열거형은 서버의 결과 코드를 정의합니다.
 *
 * @author 지연우
 */
public enum ResultType {
    SUCCESS(1),     // 성공
    WARNING(0),     // 경고
    FAILURE(-1);    // 실패

    private int code = 0;

    /**
     * 결과 타입의 코드 값을 설정하는 생성자입니다.
     *
     * @param code 결과 타입의 코드 값
     */
    ResultType(int code){
        this.code = code;
    }

    private static final Map<Integer, String> CODE_MAP = Collections.unmodifiableMap(
            Stream.of(values()).collect(Collectors.toMap(ResultType::getCode, ResultType::getName)));

    /**
     * 결과 타입의 코드 값을 반환합니다.
     *
     * @return 결과 타입의 코드 값
     */
    public int getCode() {
        return this.code;
    }

    /**
     * 결과 타입의 이름을 반환합니다.
     *
     * @return 결과 타입의 이름
     */
    public String getName() {
        return name();
    }

    /**
     * 주어진 코드에 해당하는 {@code ResultType}을 반환합니다.
     *
     * @param code 결과 코드의 정수 값
     * @return 주어진 코드에 해당하는 {@code ResultType}
     */
    public static ResultType of(int code) {
        return ResultType.valueOf(CODE_MAP.get(code));
    }
}