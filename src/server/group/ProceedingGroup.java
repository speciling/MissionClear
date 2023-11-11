package server.group;

import server.user.User;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

class ProceedingGroup extends Group {
    private ConcurrentHashMap<User, int[]> progress;

    public ProceedingGroup(int groupID, String title, String description, String mission, int categoryID, Calendar startDate,
                           Calendar endDate, List<User> users, ConcurrentHashMap<User, int[]> progress) {
        super(groupID, title, description, mission, categoryID, startDate, endDate, users);
        this.progress = progress;
    }

    public ConcurrentHashMap<User, int[]> getProgress() {
        return progress;
    }


}