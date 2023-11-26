package client.recruitpage;

import org.json.simple.JSONObject;

public class Group {
    private int gid;
    private String title;
    private String description;
    private String mission;
    private int recruitmentCapacity;
    private String category;
    private String recruitmentDeadlineYear;
    private String recruitmentDeadlineMonth;
    private String recruitmentDeadlineDay;
    private String recruitmentStartDateYear;
    private String recruitmentStartDateMonth;
    private String recruitmentStartDateDay;
    private String recruitmentEndDateYear;
    private String recruitmentEndDateMonth;
    private String recruitmentEndDateDay;
    private String roomPassword;
    private boolean isSecretRoom;

    public String getTitle() {
        return title;
    }
    public String getDescription() {
    	return description;
    }
    public String getDeadlineYear() {
    	return recruitmentDeadlineYear;
    }
    public String getDeadlineMonth() {
    	return recruitmentDeadlineMonth;
    }
    public String getDeadlineDay() {
    	return recruitmentDeadlineDay;
    }
    public int getCapacity() {
        return recruitmentCapacity;
    }
    public String getCategory() {
    	return category;
    }
    public String getStartDateYear() {
        return recruitmentStartDateYear;
    }

    public String getStartDateMonth() {
        return recruitmentStartDateMonth;
    }

    public String getStartDateDay() {
        return recruitmentStartDateDay;
    }

    public String getEndDateYear() {
        return recruitmentEndDateYear;
    }

    public String getEndDateMonth() {
        return recruitmentEndDateMonth;
    }

    public String getEndDateDay() {
        return recruitmentEndDateDay;
    }
    public boolean isSecretRoom() {
        return isSecretRoom;
    }
    
    // 기본 생성자
    public Group() {
    }

    // 모든 필드를 포함한 생성자
    public Group(String title, String description, String mission, int recruitmentCapacity, String category, String recruitmentDeadlineYear, String recruitmentDeadlineMonth, String recruitmentDeadlineDay, String recruitmentStartDateYear, String recruitmentStartDateMonth, String recruitmentStartDateDay, String recruitmentEndDateYear, String recruitmentEndDateMonth, String recruitmentEndDateDay, String roomPassword, boolean isSecretRoom ) {
        this.title = title;
        this.description = description;
        this.mission = mission;
        this.recruitmentCapacity = recruitmentCapacity;
        this.category = category;
        this.recruitmentDeadlineYear = recruitmentDeadlineYear;
        this.recruitmentDeadlineMonth = recruitmentDeadlineMonth;
        this.recruitmentDeadlineDay = recruitmentDeadlineDay;
        this.recruitmentStartDateYear = recruitmentStartDateYear;
        this.recruitmentStartDateMonth = recruitmentStartDateMonth;
        this.recruitmentStartDateDay = recruitmentStartDateDay;
        this.recruitmentEndDateYear = recruitmentEndDateYear;
        this.recruitmentEndDateMonth = recruitmentEndDateMonth;
        this.recruitmentEndDateDay = recruitmentEndDateDay;
        this.roomPassword = roomPassword;
        this.isSecretRoom = isSecretRoom;
    }

    public Group(JSONObject data) {
        this.gid = Integer.parseInt(data.get("gid").toString());
        this.title = data.get("title").toString();
        this.description = data.get("description").toString();
        this.mission = data.get("mission").toString();
        this.recruitmentCapacity = Integer.parseInt(data.get("capacity").toString());
        int category = Integer.parseInt(data.get("capacity").toString());
        this.category = (category==0?"챌린지":(category==1?"스터디":(category==2?"다이어트":"기타")));
        String[] deadline = data.get("deadline").toString().split("-");
        this.recruitmentDeadlineYear = deadline[0];
        this.recruitmentDeadlineMonth = deadline[1];
        this.recruitmentDeadlineDay = deadline[2];
        String[] startDate = data.get("startDate").toString().split("-");
        this.recruitmentStartDateYear = startDate[0];
        this.recruitmentStartDateMonth = startDate[1];
        this.recruitmentStartDateDay = startDate[2];
        String[] endDate = data.get("endDate").toString().split("-");
        this.recruitmentEndDateYear = endDate[0];
        this.recruitmentEndDateMonth = endDate[1];
        this.recruitmentEndDateDay = endDate[2];
        this.roomPassword = null;
        this.isSecretRoom = Boolean.parseBoolean(data.get("isSecret").toString());
    }

    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.put("title", this.title);
        json.put("description", this.description);
        json.put("mission", this.mission);
        json.put("capacity", this.recruitmentCapacity);
        int c = (category.equals("챌린지")?0:(category.equals("스터디")?1:(category.equals("다이어트")?2:(category.equals("기타")?3:-1))));
        json.put("category", c);
        String deadline = recruitmentDeadlineYear + "-" + recruitmentDeadlineMonth + "-" + recruitmentDeadlineDay;
        json.put("deadline", deadline);
        String startDate = recruitmentStartDateYear + "-" + recruitmentStartDateMonth + "-" + recruitmentStartDateDay;
        json.put("startDate", startDate);
        String endDate = recruitmentEndDateYear + "-" + recruitmentEndDateMonth + "-" + recruitmentEndDateDay;
        json.put("endDate", endDate);
        if (roomPassword != null) {
            json.put("password", roomPassword);
        }
        return json;
    }

    // 게터와 세터 메서드들
    // ... (모든 필드에 대한 게터와 세터 메서드들)

    // main 메서드는 필요에 따라 사용
    public static void main(String[] args) {
        // 테스트 코드나 메인 애플리케이션 코드
    }
}
