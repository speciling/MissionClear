package client.net;

import client.db.ClientDBManager;
import client.recruitpage.Group;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import server.db.DBManager;
import server.db.ResultType;
import server.service.Request;
import server.service.RequestType;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * {@code ClientSocket} 클래스는 클라이언트가 서버와 통신하는 소켓 관련 동작을 처리하는 클래스입니다.
 *
 * @author 지연우
 */
public class ClientSocket extends Thread{
    /** 서버의 IP 주소 */
    private final String IP;
    /** 서버의 포트 번호 */
    private final int PORT;
    /** SocketChannel 객체 */
    private SocketChannel socket;
    /** 서버로 전송할 데이터를 저장하는 큐 */
    private static final BlockingQueue<ByteBuffer> writeQueue = new LinkedBlockingQueue<>();
    /** 서버로부터 수신한 요청을 저장하는 큐 */
    private static final BlockingQueue<Request> readQueue = new LinkedBlockingQueue<>();


    /**
     * 서버의 IP를 따로 입력하지 않을 경우 {@code ClientSocket} 클래스의 생성자입니다.
     * IP를 localhost로 설정합니다.
     *
     * @param PORT 서버의 포트 번호
     */
    public ClientSocket(int PORT) {
        this.IP = "localhost";
        this.PORT = PORT;
    }

    /**
     * {@code ClientSocket} 클래스의 생성자입니다.
     *
     * @param IP   서버의 IP 주소
     * @param PORT 서버의 포트 번호
     */
    public ClientSocket(String IP, int PORT) {
        this.IP = IP;
        this.PORT = PORT;
    }

    /**
     * 클라이언트 소켓을 생성하고 쓰기 및 읽기 스레드를 시작합니다.
     */
    @Override
    public void run() {
        try {
            socket = SocketChannel.open(new InetSocketAddress(IP, PORT));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Thread writeThread = new Thread(() -> {
            while (true) {
                if (!writeQueue.isEmpty()) {
                    ByteBuffer data = writeQueue.poll();
                    try {
                        socket.write(data);
                    } catch (IOException e) {
                        try {
                            this.socket.close();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        });

        writeThread.start();

        while (true) {
            try {
                recieve();
            } catch (IOException e) {
                System.out.println("서버와 연결이 끊어졌습니다.");
            }
        }


    }

    /**
     * 서버에서 수신된 응답을 파싱하여 {@code Request} 객체로 반환합니다.
     *
     * @return 서버 응답에 해당하는 {@code Request} 객체
     * @throws IOException 입출력 예외가 발생한 경우
     */
    private Request getResponse() throws IOException{
        ByteBuffer headerBuffer = ByteBuffer.allocate(5);

        int readCount = socket.read(headerBuffer);
        if (readCount > 0) {

            headerBuffer.flip();

            byte type = headerBuffer.get();
            int length = headerBuffer.getInt();

            ByteBuffer bodyBuffer = ByteBuffer.allocate(length);
            while (bodyBuffer.remaining() > 0)
                socket.read(bodyBuffer);
            bodyBuffer.flip();
            String body = new String(bodyBuffer.array());
            Request request = new Request(type, body);

            if (type == RequestType.CERTIFYMISSION.getCode() || type == RequestType.CHANGEPFP.getCode() || type == RequestType.GETFILE.getCode()) {
                headerBuffer = ByteBuffer.allocate(4);
                socket.read(headerBuffer);
                headerBuffer.flip();
                length = headerBuffer.getInt();


                bodyBuffer = ByteBuffer.allocate(length);
                while (bodyBuffer.remaining() > 0)
                    socket.read(bodyBuffer);
                bodyBuffer.flip();
                request.file = bodyBuffer.array();
            }

            return request;
        }
        return null;
    }

    /**
     * 서버에서 수신된 응답을 처리합니다.
     *
     * @throws IOException 입출력 예외가 발생한 경우
     */
    private void recieve() throws IOException{
        Request response = getResponse();
        if (response != null) {
            switch (response.type){
                case LOGIN:
                    if (ResultType.of(Integer.parseInt(response.getData().get("resultType").toString())).equals(ResultType.SUCCESS))
                        ClientDBManager.login(response.getData());
                case SIGNUP:
                case GETRECRUITINGGROUPDATA:
                    readQueue.add(response);
                    break;
                case CREATENEWGROUP:
                    ClientDBManager.createNewGroup(response.getData());
                    readQueue.add(response);
                    break;
                case ENTERGROUP:
                    if (ResultType.of(Integer.parseInt(response.getData().get("resultType").toString())).equals(ResultType.SUCCESS))
                        ClientDBManager.enterGroup(response.getData());
                    readQueue.add(response);
                    break;
                case SENDDATA:
                    ClientDBManager.saveInitData(response.getData());
                    JSONObject result = new JSONObject();
                    result.put("resultType", ResultType.SUCCESS.getCode());
                    readQueue.add(new Request(RequestType.SENDDATA, result));
                    break;
                case CHAT:
                    ClientDBManager.saveChatMessage(response.getData());
                    break;
                case CERTIFYMISSION:
                    ClientDBManager.saveCertifyPicture(response);
                    break;
                case CHANGEPFP:
                    DBManager.changePFP(response.getData(), response.file);
                    break;
                case CHANGENICKNAME:
                    break;
                case GETFILE:
                    ClientDBManager.saveFile(response);
                    break;
            }
        }
    }

    /**
     * 서버로 전송할 요청을 큐에 추가합니다.
     *
     * @param request 전송할 요청 객체
     */
    public static void send(Request request) {
        if (request.type == RequestType.CERTIFYMISSION || request.type == RequestType.CHANGEPFP) {
            String filePath = request.getData().get("filePath").toString();
            Path path = Path.of(filePath);
            request.getData().put("fileName", path.getFileName().toString());

            try {
                request.file = Files.readAllBytes(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        writeQueue.add(Request.toByteBuffer(request));
    }

    /**
     * 서버로부터 수신한 결과를 반환합니다.
     *
     * @return 수신한 결과가 성공인 경우 true, 그렇지 않은 경우 false
     */
    public static boolean getResult() {
        while (true) {
            if (!readQueue.isEmpty()) {
                JSONObject data = readQueue.poll().getData();

                return Integer.parseInt(data.get("resultType").toString()) == ResultType.SUCCESS.getCode();
            }
        }
    }

    /**
     * 서버에서 모집 중인 그룹 데이터를 가져옵니다.
     *
     * @return 서버에서 가져온 모집 중인 그룹의 리스트
     */
    public static List<Group> getRecruitingGroupData() {
        send(new Request(RequestType.GETRECRUITINGGROUPDATA, new JSONObject()));
        Request result = null;
        while (true) {
            if (!readQueue.isEmpty()) {
                result = readQueue.poll();
                break;
            }
        }
        List<Group> recruitingGroupList = new ArrayList<>();
        JSONArray recruitingGroups = (JSONArray) result.getData().get("recruitingGroups");
        for (int i = 0; i < recruitingGroups.size(); i++) {
            JSONObject group = (JSONObject) recruitingGroups.get(i);
            recruitingGroupList.add(new Group(group));
        }

        return recruitingGroupList;
    }
}