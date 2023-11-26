package client.recruitpage;

public class Group {
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

    // 게터와 세터 메서드들
    // ... (모든 필드에 대한 게터와 세터 메서드들)

    // main 메서드는 필요에 따라 사용
    public static void main(String[] args) {
        // 테스트 코드나 메인 애플리케이션 코드
    }
}
