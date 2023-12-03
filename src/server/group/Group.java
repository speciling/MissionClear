package server.group;

import server.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * {@code Group} 클래스는 그룹 정보를 관리하는 클래스입니다.
 *
 * @author 지연우
 */
public class Group {
    /** gid로 그룹 객체를 찾을 수 있도록 그룹 객체들을 저장하는 ConcurrentHashMap */
    private static final ConcurrentHashMap<Integer, Group> groupMap = new ConcurrentHashMap<>();
    /** 그룹에 속한 사용자 리스트 */
    private final List<User> connectedUserList;

    /**
     * {@code Group} 클래스의 생성자입니다.
     *
     * @param gid 그룹 식별자
     */
    public Group(int gid) {
        this.connectedUserList = new ArrayList<>();
        groupMap.put(gid, this);
    }

    /**
     * 주어진 그룹 식별자에 해당하는 {@code Group} 객체를 반환합니다.
     * 만약 해당 그룹이 존재하지 않으면 새로운 그룹을 생성하여 반환합니다.
     *
     * @param gid 그룹 식별자
     * @return 주어진 그룹 식별자에 해당하는 {@code Group} 객체
     */
    public static Group get(int gid) {
        Group group = groupMap.get(gid);
        if (group != null)
            return group;
        return new Group(gid);
    }

    /**
     * 그룹에 소속의 연결된 사용자 리스트를 반환합니다.
     *
     * @return 그룹에 속한 연결된 사용자 리스트
     */
    public List<User> getConnectedUserList() {
        return this.connectedUserList;
    }

    /**
     * 주어진 사용자를 그룹에 등록합니다.
     *
     * @param user 등록할 사용자
     */
    public void registerUser(User user) { this.connectedUserList.add(user); }

    /**
     * 주어진 사용자를 그룹에서 등록 해제합니다.
     *
     * @param user 제거할 사용자
     */
    public void unRegisterUser(User user) { this.connectedUserList.remove(user); }

}



