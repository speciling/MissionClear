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
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JSpinner;
import javax.swing.JList;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.ImageIcon;


// 모서리가 둥근 JPanel을 생성하는 클래스

public class CreateNewGroupPopup {
	private RecruitGroupMember recruitGroupMember; // RecruitGroupMember 참조 추가

    // 생성자 수정
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
    private JTextField startDateYear;
    private JTextField startDateMonth;
    private JTextField startDateDay;
    private JTextField endDateYear;
    private JTextField endDateMonth;
    private JTextField endDateDay;
    private JTextField roomPassword;

    /**
     * Create the application.
     */
    public CreateNewGroupPopup() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */

    public void initialize() {
    	setFrame(new JFrame());
    	getFrame().setSize(691, 760);
    	getFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	getFrame().getContentPane().setLayout(null);
    	
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
    	
    	startDateYear = new JTextField();
    	startDateYear.setColumns(10);
    	startDateYear.setBorder(null);
    	startDateYear.setBackground(new Color(237, 237, 237));
    	startDateYear.setBounds(56, 541, 62, 32);
    	createPopup.add(startDateYear);
    	
    	startDateMonth = new JTextField();
    	startDateMonth.setColumns(10);
    	startDateMonth.setBorder(null);
    	startDateMonth.setBackground(new Color(237, 237, 237));
    	startDateMonth.setBounds(128, 541, 62, 32);
    	createPopup.add(startDateMonth);
    	
    	startDateDay = new JTextField();
    	startDateDay.setColumns(10);
    	startDateDay.setBorder(null);
    	startDateDay.setBackground(new Color(237, 237, 237));
    	startDateDay.setBounds(200, 541, 62, 32);
    	createPopup.add(startDateDay);
    	
    	JLabel activityPeriod_1 = new JLabel("~");
    	activityPeriod_1.setFont(new Font("나눔고딕", Font.PLAIN, 19));
    	activityPeriod_1.setBounds(274, 539, 20, 35);
    	createPopup.add(activityPeriod_1);
    	
    	endDateYear = new JTextField();
    	endDateYear.setColumns(10);
    	endDateYear.setBorder(null);
    	endDateYear.setBackground(new Color(237, 237, 237));
    	endDateYear.setBounds(300, 541, 62, 32);
    	createPopup.add(endDateYear);
    	
    	endDateMonth = new JTextField();
    	endDateMonth.setColumns(10);
    	endDateMonth.setBorder(null);
    	endDateMonth.setBackground(new Color(237, 237, 237));
    	endDateMonth.setBounds(371, 541, 62, 32);
    	createPopup.add(endDateMonth);
    	
    	endDateDay = new JTextField();
    	endDateDay.setColumns(10);
    	endDateDay.setBorder(null);
    	endDateDay.setBackground(new Color(237, 237, 237));
    	endDateDay.setBounds(442, 541, 62, 32);
    	createPopup.add(endDateDay);
    	
    	JLabel lblNewLabel_1_1_1_2_2_1 = new JLabel("비밀방 설정(비밀번호 4자리 입력)");
    	lblNewLabel_1_1_1_2_2_1.setFont(new Font("나눔고딕", Font.PLAIN, 19));
    	lblNewLabel_1_1_1_2_2_1.setBounds(56, 580, 280, 35);
    	createPopup.add(lblNewLabel_1_1_1_2_2_1);
    	
    	JButton creationComplete = new JButton("");
    	creationComplete.setSelectedIcon(new ImageIcon("./resource/RecruitGroupMember/생성완료.png"));
    	creationComplete.setBackground(new Color(255, 255, 255));
    	creationComplete.setIcon(new ImageIcon("./resource/RecruitGroupMember/생성완료.png"));
    	creationComplete.setBounds(279, 658, 133, 51);
    	creationComplete.setBorderPainted(false);
    	createPopup.add(creationComplete);
    	
    	JRadioButton secretRoom = new JRadioButton("예");
    	secretRoom.setFont(new Font("나눔고딕", Font.PLAIN, 15));
    	secretRoom.setBackground(new Color(255, 255, 255));
    	secretRoom.setBounds(56, 621, 50, 23);
    	createPopup.add(secretRoom);
    	
    	JRadioButton openRoom = new JRadioButton("아니오");
    	openRoom.setFont(new Font("나눔고딕", Font.PLAIN, 15));
    	openRoom.setBackground(Color.WHITE);
    	openRoom.setBounds(113, 621, 70, 23);
    	createPopup.add(openRoom);
    	
    	roomPassword = new JTextField();
    	roomPassword.setToolTipText("");
    	roomPassword.setColumns(10);
    	roomPassword.setBorder(null);
    	roomPassword.setBackground(new Color(237, 237, 237));
    	roomPassword.setBounds(202, 617, 119, 32);
    	createPopup.add(roomPassword);
    	
    	creationComplete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	AddMissionRoom addMissionRoom = new AddMissionRoom();
                addMissionRoom.initialize();

                // RecruitGroupMember의 frame에 패널 추가
                recruitGroupMember.addToGroupRecruitment(addMissionRoom.getPanel());

                frame.dispose(); // 현재 프레임 닫기
            }
        });    }

	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}
}
