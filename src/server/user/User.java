package server.user;

import org.json.simple.JSONObject;
import server.group.Group;
import server.service.RequestHandler;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class User {
    public int userID;
    RequestHandler requestHandler;
    private List<Group> groupList;

    public User(int userID, List<Integer> gidList, RequestHandler requestHandler) {
        this.userID = userID;
        this.groupList = new ArrayList<>();
        this.requestHandler = requestHandler;
        for (int gid : gidList)
            groupList.add(Group.get(gid));
        connect();
    }

    public void connect() {

    }

    public void send(ByteBuffer data) {
        requestHandler.addTask(data);
    }

    public void disconnect() {

    }
}


