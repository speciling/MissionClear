package server.group;

import server.user.User;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public abstract class Group {
    private final int groupID;
    private String title;
    private String description;
    private String mission;
    private int categoryID;
    private Calendar startDate;
    private Calendar endDate;
    private final List<User> userList;

    public Group(int groupID, String title, String description, String mission, int categoryID, Calendar startDate, Calendar endDate, List<User> users) {
        this.groupID = groupID;
        this.title = title;
        this.description = description;
        this.mission = mission;
        this.categoryID = categoryID;
        this.startDate = startDate;
        this.endDate = endDate;
        this.userList = users;
    }

    public int getGroupID() {
        return this.groupID;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public String getMission() {
        return this.mission;
    }

    public int getCategoryID() {
        return this.categoryID;
    }

    public Calendar getStartDate() {
        return this.startDate;
    }

    public Calendar getEndDate() {
        return this.endDate;
    }

    public List<User> getUserList() {
        return this.userList;
    }

}



