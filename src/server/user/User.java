package server.user;

import server.group.Group;
import server.service.RequestHandler;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * {@code User} 클래스는 서버에 연결된 사용자를 나타내는 클래스입니다.
 *
 * @author 지연우
 */
public class User {
    public int userID;
    RequestHandler requestHandler;
    private List<Group> groupList;

    /**
     * {@code User} 클래스의 생성자입니다.
     *
     * @param userID 사용자의 고유 식별자
     * @param gidList 사용자가 속한 그룹의 고유 식별자 목록
     * @param requestHandler 사용자의 요청을 처리하는 핸들러
     */
    public User(int userID, List<Integer> gidList, RequestHandler requestHandler) {
        this.userID = userID;
        this.groupList = new ArrayList<>();
        this.requestHandler = requestHandler;
        for (int gid : gidList) {
            Group group = Group.get(gid);
            groupList.add(group);
        }
    }

    /**
     * 사용자를 그룹 객체에 등록합니다.
     */
    public void connect() {
        for (Group group : groupList)
            group.registerUser(this);
    }

    /**
     * 사용자를 특정 그룹에 등록시킵니다.
     *
     * @param gid 입장할 그룹의 고유 식별자
     */
    public void enterGroup(int gid) {
        Group group = Group.get(gid);
        groupList.add(group);
        group.registerUser(this);
    }

    /**
     * 사용자에게 데이터를 전송할 때 호출되어 요청 핸들러에 데이터를 추가합니다.
     *
     * @param data 전송할 데이터를 담은 {@code ByteBuffer} 객체
     */
    public void send(ByteBuffer data) {
        requestHandler.addTask(data);
    }

    /**
     * 소속 그룹에서 사용자의 등록을 해제합니다.
     */
    public void disconnect() {
        for(Group group: groupList)
            group.unRegisterUser(this);
    }
}


