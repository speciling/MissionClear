package server.service;

/**
 * {@code Handler} 인터페이스는 네트워크 이벤트를 처리하는 핸들러의 기본 동작을 정의합니다.
 *
 * @see AcceptHandler
 * @see RequestHandler
 * @author 지연우
 */
public interface Handler {

    /**
     * 네트워크 이벤트를 처리하는 메서드입니다.
     */
    void handle();
}
