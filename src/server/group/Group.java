package server.group;

import server.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class Group {
    private static final ConcurrentHashMap<Integer, Group> groupMap = new ConcurrentHashMap<>();
    private final List<User> connectedUserList;

    public Group(int groupID) {
        this.connectedUserList = new ArrayList<>();
        groupMap.put(groupID, this);
    }

    public static Group get(int gid) {
        Group group = groupMap.get(gid);
        if (group != null)
            return group;
        return new Group(gid);
    }
    public List<User> getConnectedUserList() {
        return this.connectedUserList;
    }

    public void registerUser(User user) { this.connectedUserList.add(user); }

    public void unRegisterUser(User user) { this.connectedUserList.remove(user); }

}



