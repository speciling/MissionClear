package client.recruitpage;

import java.util.ArrayList;
import java.util.List;

import client.net.ClientSocket;
import server.service.Request;
import server.service.RequestType;


/**
 * 그룹을 관리하는 클래스.
 * 이 클래스는 서버와의 통신을 통해 그룹을 생성, 조회 및 관리하는 기능을 제공한다.
 * 클라이언트는 이 클래스를 통해 새로운 그룹을 추가하고, 가용한 그룹 데이터를 가져올 수 있다.
 */
public class GroupManager {
    private static List<Group> groupList = new ArrayList<>();

    /**
     * 새로운 그룹을 추가하는 메서드.
     * 서버에 새 그룹 생성 요청을 전송하고, 성공 여부에 따라 그룹 목록을 갱신한다.
     * @param group 추가할 그룹 객체
     * @return 그룹 추가 성공 여부 (true: 성공, false: 실패)
     */
    public static boolean addGroup(Group group) {
    	Request request = new Request(RequestType.CREATENEWGROUP, group.toJSON());
    	ClientSocket.send(request);
    	if (ClientSocket.getResult()) {
    		getRecruitingGroupData();
    		return true;
    	}
    	else {
    		return false;
    	}
    }
    
    /**
     * 서버로부터 모집 중인 그룹 데이터를 가져오는 메서드.
     * 이 메서드는 서버로부터 최신 그룹 데이터를 요청하고, 응답을 groupList에 저장한다.
     */
    public static void getRecruitingGroupData() {
    	groupList = ClientSocket.getRecruitingGroupData();
    }
    
    /**
     * 현재 가용한 그룹 목록을 반환하는 메서드.
     * @return 현재 가용한 그룹 목록
     */
    public static List<Group> getGroupList() {
        return groupList;
    }
    
    /**
     * 가장 최근에 추가된 Group 객체를 반환하는 메서드.
     * groupList가 비어 있지 않다면 가장 마지막에 추가된 Group 객체를 반환한다.
     * @return 가장 최근에 추가된 Group 객체, 또는 groupList가 비어 있으면 null
     */

    public static Group getLatestGroup() {
        if (!groupList.isEmpty()) {
            return groupList.get(groupList.size() - 1);
        }
        return null;
    }

}
