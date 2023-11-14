package server.service;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import server.db.DBManager;
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

    private final Queue<JSONObject> writeQueue = new LinkedList<>();
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

    public void addTask(JSONObject data) {
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
            JSONObject data = writeQueue.poll();
            // ... 구현
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

    private User login(Request request) {
        String id = null, password = null;
        JSONArray userInfo = new JSONArray();
        User user = null;
        DBManager.ResultType resultType = DBManager.checkLogin(userInfo, id, password);

        return user;
    }

    private void signUp(Request request) {
        String id = null, password = null, nickname = null;
        DBManager.ResultType resultType = DBManager.addUser(id, password, nickname);
    }

    private void sendData(Request request) {
        JSONArray data = new JSONArray();
        Map<Integer, Integer> req = new HashMap<>();
        sendGoupsData(data, req);
        sendChatData(data, req);
        sendProfileData(data, req);
    }

    private void sendGoupsData(JSONArray data, Map<Integer, Integer> request) {
        DBManager.ResultType resultType = DBManager.getGroupsData(data, request);
    }

    private void sendChatData(JSONArray data, Map<Integer, Integer> request) {
        DBManager.ResultType resultType = DBManager.getChatData(data, request);
    }

    private void sendProfileData(JSONArray data, Map<Integer, Integer> request) {
        DBManager.ResultType resultType = DBManager.getProfileData(data, request);
    }

    private void  createNewGroup(Request request) {
        DBManager.ResultType resultType = DBManager.createGroupTable();
    }

    private void enterGroup(Request request) {
        int uid = 0, gid = 0;
        DBManager.ResultType resultType = DBManager.enterGroup(uid, gid);
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
