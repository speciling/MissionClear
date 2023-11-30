package client.net;

import client.db.ClientDBManager;
import client.recruitpage.Group;
import org.json.simple.JSONArray;
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
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
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
            try {
                recieve();
            } catch (IOException e) {
                System.out.println("서버와 연결이 끊어졌습니다.");
            }
        }


    }

    private Request getResponse() throws IOException{
        JSONObject response;
        ByteBuffer headerBuffer = ByteBuffer.allocate(5);

        int readCount = socket.read(headerBuffer);
        if (readCount > 0) {

            headerBuffer.flip();

            byte type = headerBuffer.get();
            int length = headerBuffer.getInt();

            ByteBuffer bodyBuffer = ByteBuffer.allocate(length);
            socket.read(bodyBuffer);
            bodyBuffer.flip();
            String body = new String(bodyBuffer.array());
            Request request = new Request(type, body);

            if (type == RequestType.CERTIFYMISSION.getCode() || type == RequestType.CHANGEPFP.getCode() || type == RequestType.GETFILE.getCode()) {
                headerBuffer = ByteBuffer.allocate(4);
                socket.read(headerBuffer);
                length = headerBuffer.getInt();

                bodyBuffer = ByteBuffer.allocate(length);
                socket.read(bodyBuffer);
                bodyBuffer.flip();
                request.file = bodyBuffer.array();
            }

            return request;
        }
        return null;
    }

    private void recieve() throws IOException{
        Request response = getResponse();
        if (response != null) {
            switch (response.type){
                case LOGIN:
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
                    ClientDBManager.enterGroup(response.getData());
                    readQueue.add(response);
                    break;
                case SENDDATA:
                    ClientDBManager.saveInitData(response.getData());
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

    public static boolean getResult() {
        while (true) {
            if (!readQueue.isEmpty()) {
                JSONObject data = readQueue.poll().getData();

                return Integer.parseInt(data.get("resultType").toString()) == ResultType.SUCCESS.getCode();
            }
        }
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