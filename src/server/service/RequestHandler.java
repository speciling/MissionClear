package server.service;

import org.json.simple.JSONObject;
import server.db.DBManager;
import server.db.ResultType;
import server.db.ServerDBManager;
import server.group.Group;
import server.user.User;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * {@code RequestHandler}는 {@code Handler} 인터페이스를 구현하는 클래스로, 클라이언트의 요청을 처리합니다.
 *
 * @see Handler
 *
 * @author 지연우
 */
public class RequestHandler implements Handler{
    private static final int READING = 0, SENDING = 1;

    private final BlockingQueue<ByteBuffer> writeQueue = new LinkedBlockingQueue<>();
    private final SocketChannel socketChannel;
    private final SelectionKey selectionKey;
    private int state = READING;
    private User user;


    /**
     * {@code RequestHandler}의 생성자
     *
     * @param selector       이 핸들러를 등록할 {@code Selector} 객체
     * @param socketChannel  클라이언트와의 통신을 담당하는 {@code SocketChannel}
     * @throws IOException   입출력 예외가 발생할 경우 던지집니다.
     *
     * @author 지연우
     */
    RequestHandler(Selector selector, SocketChannel socketChannel) throws IOException {
        this.socketChannel = socketChannel;
        this.socketChannel.configureBlocking(false);
        // Attach a handler to handle when an event occurs in SocketChannel.
        selectionKey = this.socketChannel.register(selector, SelectionKey.OP_READ);
        selectionKey.attach(this);
        selector.wakeup();
    }

    /**
     * RequsetHandler 객체의 상태에 따라 요청을 읽어오고, 필요한 함수를 호출하거나, 요청을 보내는 메소드
     *
     * @author 지연우
     */
    @Override
    public void handle() {
        if (state == SENDING) {
            send();
        } else {
            Request request = getRequest();
            if (request != null) {
                try {
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
                        case GETRECRUITINGGROUPDATA:
                            getRecruitingGroupData(request);
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
                        case GETFILE:
                            getFile(request);
                            break;
                    }
                } catch (Exception e) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("resultType", ResultType.WARNING.getCode());
                    addTask(Request.toByteBuffer(request.type, jsonObject));
                }
            }
        }
    }

    /**
     * {@code ByteBuffer} 데이터를 전송 대기열에 추가하고 상태를 SENDING으로 변경합니다
     *
     * @param data  전송 대기열에 추가할 데이터를 담은 {@code ByteBuffer} 객체입니다.
     */
    public void addTask(ByteBuffer data) {
        writeQueue.add(data);
        selectionKey.interestOps(SelectionKey.OP_WRITE);
        state = SENDING;
    }

    /**
     * 전송 대기열의 데이터 1개를 클라이언트에게 전송합니다. 전송 대기열이 비어있따면 상태를 READING으로 변경합니다.
     *
     *
     */
    private void send() {
        if (writeQueue.isEmpty()) {
            selectionKey.interestOps(SelectionKey.OP_READ);
            state = READING;
        }
        else {
            ByteBuffer data = writeQueue.poll();
            try{
                socketChannel.write(data);
            } catch (IOException e) {
                closeSocket();
            }
        }
    }

    private void closeSocket() {
        if (user != null){
            this.user.disconnect();
        }
        this.selectionKey.cancel();
        try {
            this.socketChannel.close();
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }

    private Request getRequest() {
        ByteBuffer headerBuffer = ByteBuffer.allocate(5);

        try {
            int readCount = socketChannel.read(headerBuffer);
            if (readCount > 0) {

                headerBuffer.flip();

                byte type = headerBuffer.get();
                int length = headerBuffer.getInt();

                ByteBuffer bodyBuffer = ByteBuffer.allocate(length);
                while (bodyBuffer.remaining() > 0)
                    socketChannel.read(bodyBuffer);
                bodyBuffer.flip();
                String body = new String(bodyBuffer.array());
                System.out.println(type+body);
                Request request = new Request(type, body);

                if (type == RequestType.CERTIFYMISSION.getCode() || type == RequestType.CHANGEPFP.getCode()) {
                    headerBuffer = ByteBuffer.allocate(4);
                    socketChannel.read(headerBuffer);
                    headerBuffer.flip();
                    length = headerBuffer.getInt();

                    bodyBuffer = ByteBuffer.allocate(length);
                    while (bodyBuffer.remaining() > 0)
                        socketChannel.read(bodyBuffer);
                    System.out.println("사진 길이: " + length + "  버퍼 크기: " + bodyBuffer.position());
                    bodyBuffer.flip();
                    request.file = bodyBuffer.array();
                }

                if (this.user != null) {
                    request.getData().put("uid", this.user.userID);
                }

                return request;
            }

        } catch (IOException e) {
            closeSocket();
        }
        return null;
    }

    private void login(Request request) {
        JSONObject requestData = request.getData();
        String id = (String)requestData.get("id");
        String password = (String)requestData.get("password");
        JSONObject result = ServerDBManager.getUser(id, password);
        addTask(Request.toByteBuffer(RequestType.LOGIN, result));
        System.out.println("로그인 결과: " + result.toJSONString());

        if(ResultType.of((Integer) result.get("resultType")).equals(ResultType.SUCCESS)) {
            String groups = "";
            try {
                groups = (String)result.get("groups");
            } catch (ClassCastException e ){
                groups = ((Integer)result.get("groups")).toString();
            }
            List<Integer> gidList;
            if(groups.equals("")){
                gidList = new ArrayList<>();
            } else{
                try {
                    gidList = Arrays.stream(((String)result.get("groups")).split(",")).map(Integer::parseInt).toList();
                } catch (ClassCastException e) {
                    Integer gid = (Integer) result.get("groups");
                    gidList = new ArrayList<>();
                    gidList.add(gid);
                }
            }

            JSONObject initData = ServerDBManager.getInitData(gidList);
            addTask(Request.toByteBuffer(RequestType.SENDDATA, initData));

            this.user = new User((Integer)result.get("uid"), gidList, this);
        }

    }

    private void signUp(Request request) {
        JSONObject requestData = request.getData();
        String id = (String)requestData.get("id");
        String password = (String)requestData.get("password");
        String nickname = (String)requestData.get("nickname");
        JSONObject result = ServerDBManager.addUser(id, password, nickname);

        addTask(Request.toByteBuffer(RequestType.SIGNUP, result));
    }

    private void sendData(Request request) {
        this.user.connect();
    }

    private void getRecruitingGroupData(Request request) {
        JSONObject result = ServerDBManager.getRecruitingGroupData();
        addTask(Request.toByteBuffer(RequestType.GETRECRUITINGGROUPDATA, result));
    }

    private void  createNewGroup(Request request) {
        request.getData().put("users", user.userID);
        JSONObject result = ServerDBManager.createGroup((JSONObject) request.getData());
        addTask(Request.toByteBuffer(RequestType.CREATENEWGROUP, result));
    }

    private void enterGroup(Request request) {
        int uid = user.userID, gid = Integer.parseInt(request.getData().get("gid").toString());
        String pw = request.getData().get("password").toString();
        JSONObject result = ServerDBManager.enterGroup(uid, gid, pw);
        addTask(Request.toByteBuffer(RequestType.ENTERGROUP, result));
    }

    private void chat(Request request) {
        int chatId = DBManager.saveChatMessage(request.getData());
        int gid = Integer.parseInt(request.getData().get("gid").toString());
        request.getData().put("chatId", chatId);
        for (User user: Group.get(gid).getConnectedUserList())
            user.send(Request.toByteBuffer(request));
    }

    private void certifyMission(Request request) {
        int chatId = DBManager.saveCertifyPicture(request);
        int gid = Integer.parseInt(request.getData().get("gid").toString());
        request.getData().put("chatId", chatId);
        for (User user: Group.get(gid).getConnectedUserList())
            user.send(Request.toByteBuffer(request));
    }

    private void changePFP(Request request) {
        ResultType resultType = DBManager.changePFP(request.getData(), request.file);
        JSONObject result = request.getData();
        result.put("resultType", resultType.getCode());
        addTask(Request.toByteBuffer(request));
    }

    private void  changeNickname(Request request) {
        int uid = user.userID;
        String nickname = request.getData().get("nickname").toString();

        ResultType resultType = DBManager.changeNickname(uid, nickname);
        JSONObject result = request.getData();
        result.put("resultType", resultType.getCode());
        addTask(Request.toByteBuffer(RequestType.CHANGENICKNAME, result));
    }

    private void getFile(Request request) {
        ServerDBManager.getFile(request);

        addTask(Request.toByteBuffer(request));
    }
}
