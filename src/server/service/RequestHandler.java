package server.service;

import server.db.DBManager;
import server.user.User;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.sql.SQLException;

public class RequestHandler implements Handler{
    public final SocketChannel socketChannel;
    public final SelectionKey selectionKey;
    private final ByteBuffer headerBuffer = ByteBuffer.allocate(5);
    private ByteBuffer bodyBuffer;
    User user;


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
        Request request = getRequest();
        if (request != null) {
            switch (request.type) {
                case LOGIN:
                    login();
                    break;
                case SIGNUP:
                    signUp();
                    break;
                case SENDDATA:
                    sendData();
                    break;
                case CREATENEWGROUP:
                    createNewGroup();
                    break;
                case ENTERGROUP:
                    enterGroup();
                    break;
                case CHAT:
                    chat();
                    break;
                case CERTIFYMISSION:
                    certifyMission();
                    break;
                case CHANGEPFP:
                    changePFP();
                    break;
                case CHANGENICKNAME:
                    changeNickname();
                    break;
            }
        }
    }

    public Request getRequest() {

        try {
            int readCount = socketChannel.read(headerBuffer);
            if (readCount > 0) {
                headerBuffer.flip();

                byte type = headerBuffer.get();
                int length = headerBuffer.getInt();

                bodyBuffer = ByteBuffer.allocate(length);

                return new Request(type, bodyBuffer);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void login() {
        int uid = -1;
        String id = null, password = null;
        try {
            uid = DBManager.checkLogin(id, password);
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    public void signUp() {
        String id = null, password = null;
        try {
            DBManager.ResultType resultType = DBManager.addUser(new User());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void sendData() {
        try {
            String data = DBManager.getData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void  createNewGroup() {
        try {
            DBManager.ResultType resultType = DBManager.createGroupTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void enterGroup() {
        int uid = 0, gid = 0;
        try {
            DBManager.ResultType resultType = DBManager.enterGroup(uid, gid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void chat() {
        int uid = 0, gid = 0;
        String chatMsg = null;
        try {
            int chatID = DBManager.saveChatMessage(uid, gid, chatMsg);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void certifyMission() {
        int uid = 0, gid = 0;
        File picture = null;
        try {
            int chatID = DBManager.saveCertifyPicture(uid, gid, picture);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void changePFP() {
        int uid = 0;
        File picture = null;
        try {
            DBManager.ResultType resultType = DBManager.changePFP(uid, picture);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void  changeNickname() {
        int uid = 0;
        String nickname = null;
        try {
            DBManager.ResultType resultType = DBManager.changeNickname(uid, nickname);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
