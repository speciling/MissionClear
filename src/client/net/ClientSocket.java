package client.net;

import client.db.ClientDBManager;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import server.db.ResultType;
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
    private static final BlockingQueue<JSONObject> readQueue = new LinkedBlockingQueue<>();



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
                    data.flip();
                    try {
                        this.socket.write(data);
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

        writeThread.run();

        while (true) {
            JSONObject response = getResponse();
            if (response == null)
                continue;
            switch (RequestType.of((byte)response.get("requestType"))){
                case LOGIN:
                case SIGNUP:
                case CHANGEPFP:
                case CHANGENICKNAME:
                case CREATENEWGROUP:
                case ENTERGROUP:
                    readQueue.add(response);
                    break;
                case SENDDATA:
                    ClientDBManager.saveInitData(response);
                    break;
                case CHAT:
                    ClientDBManager.saveChatMessage(response);
                    break;
                case CERTIFYMISSION:
                    ClientDBManager.saveCertifyPicture(response);
                    break;
            }
        }


    }

    private JSONObject getResponse() {
        JSONObject response;
        ByteBuffer headerBuffer = ByteBuffer.allocate(5);
        try {
            this.socket.read(headerBuffer);
            headerBuffer.flip();
            byte type = headerBuffer.get();
            int length = headerBuffer.getInt();

            ByteBuffer bodyBuffer = ByteBuffer.allocate(length);
            socket.read(bodyBuffer);
            bodyBuffer.flip();
            String body = new String(bodyBuffer.array()).trim();

            JSONParser parser = new JSONParser();
            response = (JSONObject) parser.parse(body);
            response.put("responseType", type);

            return response;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void send(RequestType requestType ,JSONObject request) {
        byte[] body = request.toJSONString().getBytes();

        ByteBuffer requset = ByteBuffer.allocate(body.length+5);
        requset.put((byte) RequestType.LOGIN.getCode());
        requset.putInt(body.length);
        requset.put(body);
        requset.flip();

        writeQueue.add(requset);
    }

    public static boolean login(String id, String pw) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("password", pw);

        send(RequestType.LOGIN, jsonObject);

        while (true) {
            if (!readQueue.isEmpty()) {
                Integer result = (Integer) readQueue.poll().get("resultType");
                return result == ResultType.SUCCESS.getCode();
            }
        }
    }

    public static boolean signUp(String id, String pw, String nickname) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("password", pw);
        jsonObject.put("nickname", nickname);

        send(RequestType.LOGIN, jsonObject);

        while (true) {
            if (!readQueue.isEmpty()) {
                Integer result = (Integer) readQueue.poll().get("resultType");
                return result == ResultType.SUCCESS.getCode();
            }
        }
    }

    public static void getInitData() {}

    public static boolean createNewGroup() {
        return false;
    }

    public static boolean enterGroup() {
        return false;
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


