package client.net;

import client.db.ClientDBManager;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import server.db.ResultType;
import server.service.Request;
import server.service.RequestType;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ClientSocket extends Thread{
    private final String IP;
    private final int PORT;
    private SocketChannel socket;
    private static final BlockingQueue<ByteBuffer> writeQueue = new LinkedBlockingQueue<>();
    private static final BlockingQueue<Request> readQueue = new LinkedBlockingQueue<>();



    public ClientSocket(int PORT) {
        this.IP = "localhost";
        this.PORT = PORT;
    }

    public ClientSocket(String IP, int PORT) {
        this.IP = IP;
        this.PORT = PORT;
    }

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
            Request response = getResponse();
            if (response == null)
                continue;
            switch (response.type){
                case LOGIN:
                case SIGNUP:
                case CHANGEPFP:
                case CHANGENICKNAME:
                case CREATENEWGROUP:
                case ENTERGROUP:
                    readQueue.add(response);
                    break;
                case SENDDATA:
                    ClientDBManager.saveInitData(response.getData());
                    break;
                case CHAT:
                    ClientDBManager.saveChatMessage(response.getData());
                    break;
                case CERTIFYMISSION:
                    ClientDBManager.saveCertifyPicture(response.getData());
                    break;
            }
        }


    }

    private Request getResponse() {
        JSONObject response;
        ByteBuffer headerBuffer = ByteBuffer.allocate(5);
        try {
            int readCount = socket.read(headerBuffer);
            if (readCount > 0) {

                headerBuffer.flip();

                byte type = headerBuffer.get();
                int length = headerBuffer.getInt();

                ByteBuffer bodyBuffer = ByteBuffer.allocate(length);
                socket.read(bodyBuffer);
                bodyBuffer.flip();
                String body = new String(bodyBuffer.array());
                System.out.println(body);

                return new Request(type, body);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    private static void send(Request request) {
        writeQueue.add(Request.toByteBuffer(request));
    }

    public static boolean login(String id, String pw) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("password", pw);

        send(new Request((byte)RequestType.LOGIN.getCode(), jsonObject.toJSONString()));
        while (true) {
            if (!readQueue.isEmpty()) {
                return ResultType.of((Integer) readQueue.poll().getData().get("resultType")).equals(ResultType.SUCCESS);
            }
        }
    }

    public static boolean signUp(String id, String pw, String nickname) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("password", pw);
        jsonObject.put("nickname", nickname);

        send(new Request((byte) RequestType.LOGIN.getCode(), jsonObject.toJSONString()));

        while (true) {
            if (!readQueue.isEmpty()) {
                Integer result = (Integer) readQueue.poll().getData().get("resultType");
                return result == ResultType.SUCCESS.getCode();
            }
        }
    }

    public static JSONObject getInitData() {
        return null;
    }

    public static boolean createNewGroup() {
        return false;
    }

    public static JSONObject enterGroup() {
        return null;
    }

    public static void sendChat() {}

    public static void certifyMission() {}

    public static boolean changePFP() {
        return false;
    }

    public static boolean changeNickname() {
        return false;
    }
}


