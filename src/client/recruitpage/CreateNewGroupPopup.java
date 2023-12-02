package client.recruitpage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JSpinner;
import javax.swing.JList;
import javax.swing.JComboBox;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.ImageIcon;


// 모서리가 둥근 JPanel을 생성하는 클래스
/**
 * Class representing the popup window for creating a new group.
 * This class provides a user interface for users to enter details and create a new group.
 */
public class CreateNewGroupPopup {
	private RecruitGroupMember recruitGroupMember; // RecruitGroupMember 참조 추가

    // 생성자 수정
	/**
     * @wbp.parser.constructor
     * Constructor for CreateNewGroupPopup with a reference to RecruitGroupMember.
     * @param recruitGroupMember A reference to the RecruitGroupMember.
     */
    public CreateNewGroupPopup(RecruitGroupMember recruitGroupMember) {
        this.recruitGroupMember = recruitGroupMember;
        initialize();
    }
    private JFrame frame;
    private JTextField title;
    private JTextField description;
    private JTextField mission;
    private JTextField recruitmentDeadlineYear;
    private JTextField recruitmentDeadlineMonth;
    private JTextField recruitmentDeadlineDay;
    private JTextField recruitmentStartDateYear;
    private JTextField recruitmentStartDateMonth;
    private JTextField recruitmentStartDateDay;
    private JTextField recruitmentEndDateYear;
    private JTextField recruitmentEndDateMonth;
    private JTextField recruitmentEndDateDay;
    private JTextField roomPassword;
    private JRadioButton secretRoom;
    private JRadioButton openRoom;

    /**
     * Default constructor for CreateNewGroupPopup.
     */
    public CreateNewGroupPopup() {
        initialize();
    }

    /**
     * Initializes the contents of the frame.
     * This method sets up the layout and UI components of the popup.
     */
    
    public void initialize() {
    	setFrame(new JFrame());
    	getFrame().setSize(691, 760);
    	getFrame().setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    	getFrame().getContentPane().setLayout(null);
    	getFrame().setLocationRelativeTo(null);
    	
    	RoundedPanel createPopup = new RoundedPanel(30); // 모서리 반경을 20으로 설정
    	createPopup.setBounds(0, 0, 675, 721);
    	createPopup.setForeground(new Color(255, 255, 255));
    	createPopup.setBackground(new Color(255, 255, 255));
    	getFrame().getContentPane().add(createPopup);
    	createPopup.setLayout(null);
    	
    	JLabel createMissionRoomLabel = new JLabel("미션방 생성하기");
    	createMissionRoomLabel.setFont(new Font("\uB098\uB214\uACE0\uB515", createMissionRoomLabel.getFont().getStyle() | Font.BOLD, 27));
    	createMissionRoomLabel.setBounds(251, 39, 190, 35);
    	createPopup.add(createMissionRoomLabel);
    	
    	JLabel mssionRoomTitle = new JLabel("미션방 제목");
    	mssionRoomTitle.setFont(new Font("나눔고딕", Font.PLAIN, 19));
    	mssionRoomTitle.setBounds(56, 94, 101, 35);
    	createPopup.add(mssionRoomTitle);
    	
    	title = new JTextField();
    	title.setBackground(new Color(237, 237, 237));
    	title.setBounds(56, 129, 580, 45);
    	title.setBorder(null);
    	createPopup.add(title);
    	title.setColumns(10);
    	
    	JLabel missionDescription = new JLabel("미션 설명");
    	missionDescription.setFont(new Font("나눔고딕", Font.PLAIN, 19));
    	missionDescription.setBounds(56, 179, 80, 35);
    	createPopup.add(missionDescription);
    	
    	description = new JTextField();
    	description.setColumns(10);
    	description.setBackground(new Color(237, 237, 237));
    	description.setBounds(56, 214, 580, 45);
    	description.setBorder(null);
    	createPopup.add(description);
    	
    	mission = new JTextField();
    	mission.setColumns(10);
    	mission.setBackground(new Color(237, 237, 237));
    	mission.setBounds(56, 296, 580, 45);
    	mission.setBorder(null);
    	createPopup.add(mission);
    	
    	JLabel missionContents = new JLabel("활동 내용");
    	missionContents.setFont(new Font("나눔고딕", Font.PLAIN, 19));
    	missionContents.setBounds(56, 261, 80, 35);
    	createPopup.add(missionContents);
    	
    	JLabel missionRecruitmentCapacity = new JLabel("모집 인원");
    	missionRecruitmentCapacity.setFont(new Font("나눔고딕", Font.PLAIN, 19));
    	missionRecruitmentCapacity.setBounds(56, 346, 80, 35);
    	createPopup.add(missionRecruitmentCapacity);
    	
    	JComboBox recruitmentCapacity = new JComboBox();
    	recruitmentCapacity.setModel(new DefaultComboBoxModel(new String[] {"인원 선택", "1", "2", "3", "4", "5", "6"}));
    	recruitmentCapacity.setBounds(56, 384, 160, 39);
    	createPopup.add(recruitmentCapacity);
    	
    	JLabel missionCategory = new JLabel("카테고리");
    	missionCategory.setFont(new Font("나눔고딕", Font.PLAIN, 19));
    	missionCategory.setBounds(361, 346, 77, 35);
    	createPopup.add(missionCategory);
    	
    	JComboBox category = new JComboBox();
    	category.setModel(new DefaultComboBoxModel(new String[] {"카테고리 선택", "챌린지", "스터디", "다이어트", "기타"}));
    	category.setBounds(361, 384, 160, 39);
    	createPopup.add(category);
    	
    	JLabel recruitmentDeadline = new JLabel("모집 기한");
    	recruitmentDeadline.setFont(new Font("나눔고딕", Font.PLAIN, 19));
    	recruitmentDeadline.setBounds(56, 429, 80, 35);
    	createPopup.add(recruitmentDeadline);
    	
    	recruitmentDeadlineYear = new JTextField();
    	recruitmentDeadlineYear.setColumns(10);
    	recruitmentDeadlineYear.setBorder(null);
    	recruitmentDeadlineYear.setBackground(new Color(237, 237, 237));
    	recruitmentDeadlineYear.setBounds(56, 467, 62, 32);
    	createPopup.add(recruitmentDeadlineYear);
    	
    	recruitmentDeadlineMonth = new JTextField();
    	recruitmentDeadlineMonth.setColumns(10);
    	recruitmentDeadlineMonth.setBorder(null);
    	recruitmentDeadlineMonth.setBackground(new Color(237, 237, 237));
    	recruitmentDeadlineMonth.setBounds(128, 467, 62, 32);
    	createPopup.add(recruitmentDeadlineMonth);
    	
    	recruitmentDeadlineDay = new JTextField();
    	recruitmentDeadlineDay.setColumns(10);
    	recruitmentDeadlineDay.setBorder(null);
    	recruitmentDeadlineDay.setBackground(new Color(237, 237, 237));
    	recruitmentDeadlineDay.setBounds(200, 467, 62, 32);
    	createPopup.add(recruitmentDeadlineDay);
    	
    	JLabel recruitmentDeadline_1 = new JLabel("까지");
    	recruitmentDeadline_1.setFont(new Font("나눔고딕", Font.PLAIN, 19));
    	recruitmentDeadline_1.setBounds(271, 465, 39, 35);
    	createPopup.add(recruitmentDeadline_1);
    	
    	JLabel activityPeriod = new JLabel("활동 기간");
    	activityPeriod.setFont(new Font("나눔고딕", Font.PLAIN, 19));
    	activityPeriod.setBounds(56, 503, 80, 35);
    	createPopup.add(activityPeriod);
    	
    	recruitmentStartDateYear = new JTextField();
    	recruitmentStartDateYear.setColumns(10);
    	recruitmentStartDateYear.setBorder(null);
    	recruitmentStartDateYear.setBackground(new Color(237, 237, 237));
    	recruitmentStartDateYear.setBounds(56, 541, 62, 32);
    	createPopup.add(recruitmentStartDateYear);
    	
    	recruitmentStartDateMonth = new JTextField();
    	recruitmentStartDateMonth.setColumns(10);
    	recruitmentStartDateMonth.setBorder(null);
    	recruitmentStartDateMonth.setBackground(new Color(237, 237, 237));
    	recruitmentStartDateMonth.setBounds(128, 541, 62, 32);
    	createPopup.add(recruitmentStartDateMonth);
    	
    	recruitmentStartDateDay = new JTextField();
    	recruitmentStartDateDay.setColumns(10);
    	recruitmentStartDateDay.setBorder(null);
    	recruitmentStartDateDay.setBackground(new Color(237, 237, 237));
    	recruitmentStartDateDay.setBounds(200, 541, 62, 32);
    	createPopup.add(recruitmentStartDateDay);
    	
    	JLabel activityPeriod_1 = new JLabel("~");
    	activityPeriod_1.setFont(new Font("나눔고딕", Font.PLAIN, 19));
    	activityPeriod_1.setBounds(274, 539, 20, 35);
    	createPopup.add(activityPeriod_1);
    	
    	recruitmentEndDateYear = new JTextField();
    	recruitmentEndDateYear.setColumns(10);
    	recruitmentEndDateYear.setBorder(null);
    	recruitmentEndDateYear.setBackground(new Color(237, 237, 237));
    	recruitmentEndDateYear.setBounds(300, 541, 62, 32);
    	createPopup.add(recruitmentEndDateYear);
    	
    	recruitmentEndDateMonth = new JTextField();
    	recruitmentEndDateMonth.setColumns(10);
    	recruitmentEndDateMonth.setBorder(null);
    	recruitmentEndDateMonth.setBackground(new Color(237, 237, 237));
    	recruitmentEndDateMonth.setBounds(371, 541, 62, 32);
    	createPopup.add(recruitmentEndDateMonth);
    	
    	recruitmentEndDateDay = new JTextField();
    	recruitmentEndDateDay.setColumns(10);
    	recruitmentEndDateDay.setBorder(null);
    	recruitmentEndDateDay.setBackground(new Color(237, 237, 237));
    	recruitmentEndDateDay.setBounds(442, 541, 62, 32);
    	createPopup.add(recruitmentEndDateDay);
    	
    	JLabel setPasswordLabel = new JLabel("비밀방 설정(비밀번호 4자리 입력)");
    	setPasswordLabel.setFont(new Font("나눔고딕", Font.PLAIN, 19));
    	setPasswordLabel.setBounds(56, 580, 280, 35);
    	createPopup.add(setPasswordLabel);
    	
    	JButton creationComplete = new JButton("");
    	creationComplete.setSelectedIcon(new ImageIcon("./resource/RecruitGroupMember/생성완료.png"));
    	creationComplete.setBackground(new Color(255, 255, 255));
    	creationComplete.setIcon(new ImageIcon("./resource/RecruitGroupMember/생성완료.png"));
    	creationComplete.setBounds(279, 658, 133, 51);
    	creationComplete.setBorderPainted(false);
    	createPopup.add(creationComplete);
    	
    	JRadioButton secretRoom = new JRadioButton("예");
    	JRadioButton openRoom = new JRadioButton("아니오");
    	
    	ButtonGroup roomTypeGroup = new ButtonGroup();
        roomTypeGroup.add(secretRoom);
        roomTypeGroup.add(openRoom);
        
        secretRoom.setFont(new Font("나눔고딕", Font.PLAIN, 15));
    	secretRoom.setBackground(new Color(255, 255, 255));
    	secretRoom.setBounds(56, 621, 50, 23);
    	createPopup.add(secretRoom);
    	
    	openRoom.setFont(new Font("나눔고딕", Font.PLAIN, 15));
    	openRoom.setBackground(Color.WHITE);
    	openRoom.setBounds(113, 621, 70, 23);
    	createPopup.add(openRoom);
    	
    	
    	roomPassword = new JPasswordField();
    	roomPassword.setToolTipText("");
    	roomPassword.setColumns(10);
    	roomPassword.setBorder(null);
    	roomPassword.setBackground(new Color(237, 237, 237));
    	roomPassword.setBounds(202, 617, 119, 32);
    	createPopup.add(roomPassword);
    	
    	JButton exitButton = new JButton("");
    	exitButton.setIcon(new ImageIcon("./resource/RecruitGroupMember/exit.png"));
	    exitButton.setBounds(620, 10, 50, 50);
	    exitButton.setOpaque(false);
	    exitButton.setContentAreaFilled(false);
	    exitButton.setBorderPainted(false);
	    exitButton.setFocusPainted(false); // 포커스 테두리 제거
    	createPopup.add(exitButton);
    	
    	JLabel warningLabel_1 = new JLabel("* 날짜정보를 다시 입력해주세요.");
    	warningLabel_1.setForeground(Color.RED);
    	warningLabel_1.setFont(new Font("나눔고딕", Font.PLAIN, 13));
    	warningLabel_1.setBounds(145, 435, 200, 20);
    	warningLabel_1.setVisible(false);
    	createPopup.add(warningLabel_1);
    	
    	JLabel warningLabel_2 = new JLabel("* 날짜정보를 다시 입력해주세요.");
    	warningLabel_2.setForeground(Color.RED);
    	warningLabel_2.setFont(new Font("나눔고딕", Font.PLAIN, 13));
    	warningLabel_2.setBounds(145, 511, 200, 20);
    	warningLabel_2.setVisible(false);
    	createPopup.add(warningLabel_2);
    	
    	exitButton.addActionListener(new ActionListener() {
    	    public void actionPerformed(ActionEvent e) {
    	        frame.dispose(); // 현재 프레임을 닫음
    	    }
    	});
    	
    	creationComplete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	// AddMissionRoom 객체를 생성하면서 제목을 전달
            	if (!isValidDateInput(recruitmentDeadlineYear.getText(), recruitmentDeadlineMonth.getText(), recruitmentDeadlineDay.getText())) {
                    warningLabel_1.setVisible(true);
                    return;
                } else {
                    warningLabel_1.setVisible(false);
                }
            	if (!isValidActivityPeriod(recruitmentStartDateYear.getText(), recruitmentStartDateMonth.getText(), recruitmentStartDateDay.getText())
                        || !isValidActivityPeriod(recruitmentEndDateYear.getText(), recruitmentEndDateMonth.getText(), recruitmentEndDateDay.getText())) {
                        warningLabel_2.setVisible(true);
                        return;
                    } else {
                        warningLabel_2.setVisible(false);
                    }
            	String missionTitle = title.getText();
            	String missionDescription = description.getText();
            	String missionContent = mission.getText();// Fetch the mission description text
            	int deadlineYear = Integer.parseInt(recruitmentDeadlineYear.getText());
                int deadlineMonth = Integer.parseInt(recruitmentDeadlineMonth.getText());
                int deadlineDay = Integer.parseInt(recruitmentDeadlineDay.getText());
                
                int startDateYear = Integer.parseInt(recruitmentStartDateYear.getText());
                int startDateMonth = Integer.parseInt(recruitmentStartDateMonth.getText());
                int startDateDay = Integer.parseInt(recruitmentStartDateDay.getText());
                
                int endDateYear = Integer.parseInt(recruitmentEndDateYear.getText());
                int endDateMonth = Integer.parseInt(recruitmentEndDateMonth.getText());
                int endDateDay = Integer.parseInt(recruitmentEndDateDay.getText());
                
                String selectedCapacity = (String) recruitmentCapacity.getSelectedItem();
                int recruitmentCapacityValue = Integer.parseInt(selectedCapacity);
                
                String selectedCategory = (String) category.getSelectedItem();
                
                boolean isSecretRoom = secretRoom.isSelected();
                
                Group group = new Group(
                	    missionTitle, 
                	    missionDescription, 
                	    missionContent, // 누락된 mission 필드 추가
                	    Integer.parseInt(recruitmentCapacity.getSelectedItem().toString()), // recruitmentCapacity는 int 타입
                	    selectedCategory, 
                	    recruitmentDeadlineYear.getText(), 
                	    recruitmentDeadlineMonth.getText(), 
                	    recruitmentDeadlineDay.getText(), 
                	    recruitmentStartDateYear.getText(), 
                	    recruitmentStartDateMonth.getText(), 
                	    recruitmentStartDateDay.getText(), 
                	    recruitmentEndDateYear.getText(), 
                	    recruitmentEndDateMonth.getText(), 
                	    recruitmentEndDateDay.getText(),
                	    roomPassword.getText(), // roomPassword 필드 추가
                	    isSecretRoom 
                	);                // RecruitGroupMember의 frame에 패널 추가
                GroupManager.addGroup(group);
               	
                frame.dispose(); // 현재 프레임 닫기
            }
        });   
    	}
    private boolean isValidDateInput(String year, String month, String day) {
        try {
            int yearValue = Integer.parseInt(year);
            int monthValue = Integer.parseInt(month);
            int dayValue = Integer.parseInt(day);

            // 필요한 경우 추가적인 유효성 검사 수행

            return true; // 예외가 발생하지 않고 입력이 유효한 경우
        } catch (NumberFormatException e) {
            return false; // 파싱이 실패하면 입력이 유효하지 않음
        }
    }
    private boolean isValidActivityPeriod(String year, String month, String day) {
        try {
            int yearValue = Integer.parseInt(year);
            int monthValue = Integer.parseInt(month);
            int dayValue = Integer.parseInt(day);

            // 필요한 경우 추가적인 유효성 검사 수행

            return true; // 예외가 발생하지 않고 입력이 유효한 경우
        } catch (NumberFormatException e) {
            return false; // 파싱이 실패하면 입력이 유효하지 않음
        }
    }
    /**
     * Gets the frame of the popup window.
     * @return The JFrame object of this popup.
     */
	public JFrame getFrame() {
		return frame;
	}
	/**
     * Sets the frame of the popup window.
     * @param frame The JFrame object to set for this popup.
     */
	public void setFrame(JFrame frame) {
		this.frame = frame;
	}
}