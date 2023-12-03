package server.service;

import java.io.IOException;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * {@code AcceptHandler} 클래스는 서버의 {@code ServerSocketChannel}에서 연결 요청을 수락하는 핸들러 클래스입니다.
 *
 * @see Handler
 * @author 지연우
 */
public class AcceptHandler implements Handler {
    final Selector selector;
    final ServerSocketChannel serverSocketChannel;

    /**
     * {@code AcceptHandler} 클래스의 생성자입니다.
     *
     * @param selector             셀렉터 객체
     * @param serverSocketChannel 서버 소켓 채널
     */
    public AcceptHandler(Selector selector, ServerSocketChannel serverSocketChannel) {
        this.selector = selector;
        this.serverSocketChannel = serverSocketChannel;
    }

    /**
     * 서버의 {@code ServerSocketChannel}에서 연결 요청을 수락하고, 새로운 {@code RequestHandler}를 생성합니다.
     */
    @Override
    public void handle() {
        try {
            final SocketChannel socketChannel = serverSocketChannel.accept();
            if (socketChannel != null) {
                new RequestHandler(selector, socketChannel);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}