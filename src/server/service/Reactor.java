package server.service;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Set;

/**
 * {@code Reactor} 클래스는 Reactor 패턴을 구현하여 클라이언트와의 통신을 처리하는 클래스입니다.
 *
 * @author 지연우
 */
public class Reactor {
    private final Selector selector;
    private final ServerSocketChannel serverSocketChannel;

    /**
     * {@code Reactor} 클래스의 생성자입니다.
     *
     * @param port 서버가 바인딩할 포트 번호
     * @throws IOException 입출력 예외가 발생할 경우
     */
    public Reactor(int port) throws IOException {
        selector = Selector.open();

        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(port));
        serverSocketChannel.configureBlocking(false);
        SelectionKey selectionKey = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        selectionKey.attach(new AcceptHandler(selector, serverSocketChannel));
    }

    /**
     * Reactor를 실행하고 네트워크 이벤트를 처리합니다.
     */
    public void run() {
        try {
            while (true) {
                selector.select();
                Set<SelectionKey> selected = selector.selectedKeys();
                for (SelectionKey selectionKey : selected) {
                    dispatch(selectionKey);
                }
                selected.clear();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 선택된 이벤트를 처리하기 위해 해당 이벤트의 핸들러를 호출합니다.
     *
     * @param selectionKey 선택된 이벤트를 나타내는 {@code SelectionKey} 객체
     */
    private void dispatch(SelectionKey selectionKey) {
        Handler handler = (Handler) selectionKey.attachment();
        handler.handle();
    }
}
