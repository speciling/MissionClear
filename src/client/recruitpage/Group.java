package client.recruitpage;

import org.json.simple.JSONObject;

/**
 * 그룹 정보를 관리하는 클래스.
 * 그룹의 상세 정보 및 설정을 관리하며, JSON 객체로 변환하는 기능을 제공한다.
 */
public class Group {
    private int gid;
    private String title;
    private String description;
    private String mission;
    private int recruitmentCapacity;
    private int usercount;
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

    /**
     * 그룹의 고유 식별자(GID)를 반환한다.
     * @return 그룹의 고유 식별자
     */
    public int getGid() {
    	return gid;
    }
    
    /**
     * 그룹의 제목을 반환한다.
     * @return 그룹 제목
     */
    public String getTitle() {
        return title;
    }
    
    /**
     * 미션 내용 반환한다.
     * @return 미션 내용
     */
    public String getDescription() {
    	return description;
    }
    
    /**
     * 미션 방 활동내용을 반환한다.
     * @return 미션 방 활동내용
     */
    public String getMission() {
    	return mission;
    }
    
    /**
     * 미션 방 모집기한(년도)을 반환한다.
     * @return 미션 방 모집기한(년도)
     */
    public String getDeadlineYear() {
    	return recruitmentDeadlineYear;
    }
    
    /**
     * 미션 방 모집기한(달)을 반환한다.
     * @return 미션 방 모집기한(달)
     */
    public String getDeadlineMonth() {
    	return recruitmentDeadlineMonth;
    }
    
    /**
     * 미션 방 모집기한(일)을 반환한다.
     * @return 미션 방 모집기한(일)
     */
    public String getDeadlineDay() {
    	return recruitmentDeadlineDay;
    }
    
    /**
     * 그룹의 모집인원을 반환한다.
     * @return 그룹 모집인원
     */
    public int getCapacity() {
        return recruitmentCapacity;
    }
    
    /**
     * 그룹의 현재인원을 반환한다.
     * @return 그룹 현재인원
     */
    public int getUserCount() {
        return usercount;
    }
    
    /**
     * 미션 방 카테고리를 반환한다.
     * @return 미션 방 카테고리
     */
    public String getCategory() {
    	return category;
    }
    
    /**
     * 미션 방 활동 기간 시작날짜(년도)를 반환한다.
     * @return 미션 방 활동 기간 시작날짜(년도)
     */
    public String getStartDateYear() {
        return recruitmentStartDateYear;
    }

    /**
     * 미션 방 활동 기간 시작날짜(달)를 반환한다.
     * @return 미션 방 활동 기간 시작날짜(달)
     */
    public String getStartDateMonth() {
        return recruitmentStartDateMonth;
    }

    /**
     *  미션 방 활동 기간 시작날짜(일)를 반환한다.
     * @return 미션 방 활동 기간 시작날짜(일)
     */
    public String getStartDateDay() {
        return recruitmentStartDateDay;
    }

    /**
     * 미션 방 활동 기간 종료날짜(년도)를 반환한다.
     * @return 미션 방 활동 기간 종료날짜(년도)
     */
    public String getEndDateYear() {
        return recruitmentEndDateYear;
    }

    /**
     * 미션 방 활동 기간 종료날짜(달)를 반환한다.
     * @return 미션 방 활동 기간 종료날짜(달)
     */
    public String getEndDateMonth() {
        return recruitmentEndDateMonth;
    }

    /**
     * 미션 방 활동 기간 종료날짜(일)를 반환한다.
     * @return 미션 방 활동 기간 종료날짜(일)
     */
    public String getEndDateDay() {
        return recruitmentEndDateDay;
    }
    
    /**
     * 미션 방 비밀번호 설정 여부를 반환한다.
     * @return 미션 방 비밀번호 설정 여부
     */
    public boolean isSecretRoom() {
        return isSecretRoom;
    }
    
    /**
     * 미션 방 비밀번호를 반환한다.
     * @return 미션 방 비밀번호
     */
    private String getRoomPassword() {
        return roomPassword;
    }
    
    /**
     * 기본 생성자. Group 객체를 초기화한다.
     */
    public Group() {
    }

    /**
     * 모든 필드를 포함한 생성자. Group 객체를 초기화한다.
     * @param title 그룹 제목
     * @param description 그룹 설명
     * @param mission 그룹 미션
     * @param recruitmentCapacity 모집 인원
     * @param category 그룹 카테고리
     * @param recruitmentDeadlineYear 모집 마감 년도
     * @param recruitmentDeadlineMonth 모집 마감 월
     * @param recruitmentDeadlineDay 모집 마감 일
     * @param recruitmentStartDateYear 모집 시작 년도
     * @param recruitmentStartDateMonth 모집 시작 월
     * @param recruitmentStartDateDay 모집 시작 일
     * @param recruitmentEndDateYear 모집 종료 년도
     * @param recruitmentEndDateMonth 모집 종료 월
     * @param recruitmentEndDateDay 모집 종료 일
     * @param roomPassword 방 비밀번호
     * @param isSecretRoom 비밀방 여부
     */
    public Group(String title, String description, String mission, int recruitmentCapacity,String category, String recruitmentDeadlineYear, String recruitmentDeadlineMonth, String recruitmentDeadlineDay, String recruitmentStartDateYear, String recruitmentStartDateMonth, String recruitmentStartDateDay, String recruitmentEndDateYear, String recruitmentEndDateMonth, String recruitmentEndDateDay, String roomPassword, boolean isSecretRoom ) {
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

    /**
     * JSONObject로부터 Group 객체를 생성하는 생성자.
     * @param data JSONObject 형태의 그룹 데이터
     */
    public Group(JSONObject data) {
        this.gid = Integer.parseInt(data.get("gid").toString());
        this.title = data.get("title").toString();
        this.description = data.get("description").toString();
        this.mission = data.get("mission").toString();
        this.recruitmentCapacity = Integer.parseInt(data.get("capacity").toString());
        int category = Integer.parseInt(data.get("category").toString());
        this.category = (category==0?"챌린지":(category==1?"스터디":(category==2?"다이어트":"기타")));
        String[] deadline = data.get("deadline").toString().split("-");
        this.usercount = Integer.parseInt(data.get("usercnt").toString());
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
        Object isSecret = data.get("isSecret");
        if (isSecret != null)
            this.isSecretRoom = Boolean.parseBoolean(isSecret.toString());
    }

    /**
     * Group 객체를 JSONObject로 변환한다.
     * @return 그룹 정보가 담긴 JSONObject
     */
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

}
