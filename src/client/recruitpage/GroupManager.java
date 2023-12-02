package client.recruitpage;

import java.util.ArrayList;
import java.util.List;

import client.net.ClientSocket;
import server.service.Request;
import server.service.RequestType;

public class GroupManager {
    private static List<Group> groupList = new ArrayList<>();

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
    
    public static void getRecruitingGroupData() {
    	groupList = ClientSocket.getRecruitingGroupData();
    }
    
    public static List<Group> getGroupList() {
        return groupList;
    }

    // 최신 Group 객체를 반환하는 메서드
    public static Group getLatestGroup() {
        if (!groupList.isEmpty()) {
            return groupList.get(groupList.size() - 1);
        }
        return null; // groupList가 비어 있으면 null 반환
    }

    // 필요한 경우 여기에 추가적인 메서드를 구현할 수 있습니다.
}
