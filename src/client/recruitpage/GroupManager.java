package client.recruitpage;

import java.util.ArrayList;

public class GroupManager {
    private static ArrayList<Group> groupList = new ArrayList<>();

    public static void addGroup(Group group) {
        groupList.add(group);
    }

    public static ArrayList<Group> getGroupList() {
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
