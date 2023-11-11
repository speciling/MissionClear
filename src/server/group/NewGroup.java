package server.group;

import server.user.User;

import java.util.Calendar;
import java.util.List;

public class NewGroup extends Group {
    private int recruitmentCapacity;
    private Calendar recruitmentDeadline;
    public final boolean isSecret;
    private final String password;

    public NewGroup(int groupID, String title, String description, String mission, int recruitmentCapacity, int categoryID,
                    Calendar recruitmentDeadline, Calendar startDate, Calendar endDate, boolean isSecret, String password,
                    List<User> users) {
        super(groupID, title, description, mission, categoryID, startDate, endDate, users);
        this.recruitmentCapacity = recruitmentCapacity;
        this.recruitmentDeadline = recruitmentDeadline;
        this.isSecret = isSecret;
        this.password = password;
    }

    public void enter(User user) {
    }

    public Calendar getRecruitmentDeadline() {
        return this.recruitmentDeadline;
    }

    public boolean isCorrectPassword(String password){
        return this.password.equals(password);
    }
}