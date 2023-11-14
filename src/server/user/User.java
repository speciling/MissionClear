package server.user;

import org.json.simple.JSONObject;
import server.group.Group;
import server.service.RequestHandler;

import java.util.ArrayList;
import java.util.List;

public class User {
    public int userID;
    RequestHandler requestHandler;
    private List<Group> groupList;

    public User(int userID, RequestHandler requestHandler, List<Integer> gidList) {
        this.userID = userID;
        this.requestHandler = requestHandler;
        this.groupList = new ArrayList<>();
        for (int gid : gidList)
            groupList.add(Group.get(gid));
        connect();
    }

    public void connect() {

    }

    public void send(JSONObject data) {
        requestHandler.addTask(data);
    }

    public void disconnect() {

    }
}


