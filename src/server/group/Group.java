package server.group;

import server.user.User;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class Group {
    private static final ConcurrentHashMap<Integer, Group> groupMap = new ConcurrentHashMap<>();
    private final int groupID;
    private final List<User> userList;

    public Group(int groupID) {
        this.groupID = groupID;
        this.userList = new ArrayList<>();
        groupMap.put(groupID, this);
    }

    public static Group get(int gid) {
        Group group = groupMap.get(gid);
        if (group != null)
            return group;
        return new Group(gid);
    }
    public List<User> getUserList() {
        return this.userList;
    }

    public void registerUser(User user) { this.userList.add(user); }

    public void unRegisterUser(User user) { this.userList.remove(user); }

}



