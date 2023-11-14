package server.service;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import server.db.DBManager;
import server.db.ServerDBManager;
import server.user.User;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class RequestHandler implements Handler{
    private static final int READING = 0, SENDING = 1;

    private final Queue<ByteBuffer> writeQueue = new LinkedList<>();
    private final SocketChannel socketChannel;
    private final SelectionKey selectionKey;
    private final ByteBuffer headerBuffer = ByteBuffer.allocate(5);
    private int state = READING;
    private ByteBuffer bodyBuffer;
    private User user;


    RequestHandler(Selector selector, SocketChannel socketChannel) throws IOException {
        this.socketChannel = socketChannel;
        this.socketChannel.configureBlocking(false);
        // Attach a handler to handle when an event occurs in SocketChannel.
        selectionKey = this.socketChannel.register(selector, SelectionKey.OP_READ);
        selectionKey.attach(this);
        selector.wakeup();
    }

    @Override
    public void handle() {
        if (state == SENDING) {
            send();
        } else {
            Request request = getRequest();
            if (request != null) {
                switch (request.type) {
                    case LOGIN:
                        login(request);
                        break;
                    case SIGNUP:
                        signUp(request);
                        break;
                    case SENDDATA:
                        sendData(request);
                        break;
                    case CREATENEWGROUP:
                        createNewGroup(request);
                        break;
                    case ENTERGROUP:
                        enterGroup(request);
                        break;
                    case CHAT:
                        chat(request);
                        break;
                    case CERTIFYMISSION:
                        certifyMission(request);
                        break;
                    case CHANGEPFP:
                        changePFP(request);
                        break;
                    case CHANGENICKNAME:
                        changeNickname(request);
                        break;
                }
            }
        }
    }

    public void addTask(ByteBuffer data) {
        writeQueue.add(data);
        selectionKey.interestOps(SelectionKey.OP_WRITE);
        state = SENDING;
    }

    private void send() {
        if (writeQueue.isEmpty()) {
            selectionKey.interestOps(SelectionKey.OP_READ);
            state = READING;
        }
        else {
            ByteBuffer data = writeQueue.poll();
            data.flip();
            try{
                socketChannel.write(data);
            } catch (IOException e) {
                closeSocket();
            }
        }
    }

    private void closeSocket() {
        this.user.disconnect();
        this.selectionKey.cancel();
        try {
            this.socketChannel.close();
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }

    private Request getRequest() {

        try {
            int readCount = socketChannel.read(headerBuffer);
            if (readCount > 0) {
                headerBuffer.flip();

                byte type = headerBuffer.get();
                int length = headerBuffer.getInt();

                bodyBuffer = ByteBuffer.allocate(length);
                socketChannel.read(bodyBuffer);
                bodyBuffer.flip();
                String body = new String(bodyBuffer.array()).trim();

                return new Request(type, body);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void login(Request request) {
        JSONObject requsetData = request.getData();
        String id = (String)requsetData.get("id");
        String password = (String)requsetData.get("password");
        JSONObject userInfo = ServerDBManager.getUser(id, password);
        User user = null;

        this.user = user;
    }

    private void signUp(Request request) {
        String id = null, password = null, nickname = null;
        JSONObject result = ServerDBManager.addUser(id, password, nickname);
    }

    private void sendData(Request request) {
        JSONObject GoupsData = ServerDBManager.getGroupsData();
        JSONObject chatData = ServerDBManager.getChatData((JSONObject) request.getData().get("lastChatId"));
        JSONObject ProfileData = ServerDBManager.getProfileData((JSONObject) request.getData().get("prfiles"));
    }

    private void  createNewGroup(Request request) {
        ((JSONObject)request.getData().get("groupInfo")).put("users", user.userID);
        JSONObject result = ServerDBManager.createGroup((JSONObject) request.getData().get("groupInfo"));
    }

    private void enterGroup(Request request) {
        int uid = 0, gid = 0;
        DBManager.ResultType resultType = ServerDBManager.enterGroup(uid, gid);
    }

    private void chat(Request request) {
        int uid = 0, gid = 0;
        String chatMsg = null;

        int chatId = DBManager.saveChatMessage(uid, gid, chatMsg);

    }

    private void certifyMission(Request request) {
        int uid = 0, gid = 0;
        Path picture = null;

        int chatID = DBManager.saveCertifyPicture(uid, gid, picture);
    }

    private void changePFP(Request request) {
        int uid = 0;
        Path picture = null;

        DBManager.ResultType resultType = DBManager.changePFP(uid, picture);
    }

    private void  changeNickname(Request request) {
        int uid = 0;
        String nickname = null;

        DBManager.ResultType resultType = DBManager.changeNickname(uid, nickname);
    }
}
