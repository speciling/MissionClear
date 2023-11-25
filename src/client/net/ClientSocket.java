package client.net;

import client.db.ClientDBManager;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import server.db.DBManager;
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
            recieve();
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

                return new Request(type, body);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    private void recieve() {
        Request response = getResponse();
        if (response != null) {
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

    public static void send(Request request) {
        writeQueue.add(Request.toByteBuffer(request));
    }

    public static boolean login(String id, String pw) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("password", pw);

        send(new Request((byte)RequestType.LOGIN.getCode(), jsonObject.toJSONString()));
        while (true) {
            if (!readQueue.isEmpty()) {
                JSONObject data = readQueue.poll().getData();

                return Integer.parseInt(data.get("resultType").toString()) == ResultType.SUCCESS.getCode();
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
                Integer result = Integer.parseInt(readQueue.poll().getData().get("resultType").toString());
                return result == ResultType.SUCCESS.getCode();
            }
        }
    }
}