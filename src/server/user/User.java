package server.user;


import server.group.Group;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class User {
    private static final ConcurrentHashMap<Integer, User> connectedUsers = new ConcurrentHashMap<>();
    public int userID;
    public String Nickname;
    private List<Group> groupList;

    public User() {
    }

    public User(int userID) {

    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void register() {
        connectedUsers.put(this.userID, this);
    }

    public void unregister() {
        connectedUsers.remove(this.userID);
    }

    public static User getConnectedUser(int userID) {
        return connectedUsers.get(userID);
    }

}


