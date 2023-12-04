package client.recruitpage;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import client.MainPage.MainPage;

import java.awt.Font;
import javax.swing.JButton;

public class AddMyGroup {

	protected RoundedPanel addGroupPanel;
	private MainPage mainPage;
	private Group group;
	
	/**
     * 기본 생성자. UI 초기화를 담당한다.
     */ 
	AddMyGroup() {
		initialize();
	}

	/**
     * 그룹 정보를 받아서 UI를 초기화하는 생성자.
     * @param group UI에 표시될 그룹 정보를 담고 있는 Group 객체.
     */
	public AddMyGroup(Group group) {
		this.group = group;
		initialize();
	}
	
	 /**
     * MainPage 인스턴스를 설정한다.
     * @param mainPage 이 클래스와 연동될 MainPage 인스턴스.
     */
	public void setMainPage(MainPage mainPage) {
        this.mainPage = mainPage;
    }

	/**
     * 패널의 내용을 초기화한다. 여기에 UI 구성 요소들을 배치하고 설정한다.
     * RoundedPanel, JLabel, JButton 등을 사용하여 사용자 인터페이스를 구성한다.
     * 각 UI 요소는 그룹의 정보를 표시하고, 사용자가 상세 정보를 볼 수 있도록 한다.
     */
	private void initialize() {
		addGroupPanel =  new RoundedPanel(0); 
		addGroupPanel.setBounds(0, 0, 890, 189);
		addGroupPanel.setForeground(new Color(255, 255, 255));
		addGroupPanel.setBackground(new Color(255, 255, 255));
		addGroupPanel.setLayout(null);
		
		addGroupPanel.setBorder(new LineBorder(new Color(200, 200, 200), 1, true));

		JLabel missionTitleLabel = new JLabel(group.getTitle());
		missionTitleLabel.setFont(new Font("나눔고딕", Font.BOLD, 25));
		missionTitleLabel.setBounds(26, 12, 232, 35);
		addGroupPanel.add(missionTitleLabel);
		
		JLabel activityPeriodLabel = new JLabel("<html><b>활동기간</b>: " + group.getStartDateYear() + ". " + group.getStartDateMonth() + ". " + group.getStartDateDay() + " ~ " + group.getEndDateYear() + ". " + group.getEndDateMonth() + ". " + group.getEndDateDay() + "</html>");
		activityPeriodLabel.setFont(new Font("나눔고딕", Font.PLAIN, 15));
		activityPeriodLabel.setBounds(26, 53, 352, 25);
		addGroupPanel.add(activityPeriodLabel);
		
		JLabel missionLabel = new JLabel("<html><b>활동내용</b>: " + group.getMission() + "</html>");
		missionLabel.setFont(new Font("나눔고딕", Font.PLAIN, 15));
		missionLabel.setBounds(26, 83, 352, 25);
		addGroupPanel.add(missionLabel);
		
		JLabel personnelPanel = new JLabel("<html><b>참여인원</b>: " + group.getUserCount() + "명");
		personnelPanel.setFont(new Font("나눔고딕", Font.PLAIN, 15));
		personnelPanel.setBounds(26, 113, 352, 25);
		addGroupPanel.add(personnelPanel);
		
		JLabel category = new JLabel(group.getCategory());
	    category.setForeground(new Color(56, 183, 255));
	    category.setHorizontalAlignment(JLabel.CENTER);
	    category.setFont(new Font("나눔고딕", Font.PLAIN, 17));
	    category.setBounds(780, 144, 90, 35);
	    Color customColor = new Color(56, 183, 255);
	    category.setBorder(new RoundedBorder(10, customColor));
	    addGroupPanel.add(category);
	    
	    JButton detailEnterButton = new JButton("");
        detailEnterButton.setBounds(0, 0, 890, 189);
        detailEnterButton.setContentAreaFilled(false);
        detailEnterButton.setBorderPainted(false);
        detailEnterButton.addActionListener(e -> {
            if (MainPage.instance != null) {
                MainPage.instance.changeDetailPan(group); // 정적 변수를 통해 MainPage의 메서드 호출
            }
        });
		addGroupPanel.add(detailEnterButton);
		
	}

	 /**
     * 이 클래스의 JPanel 객체를 반환한다.
     * @return 이 클래스의 JPanel 객체.
     */
    public JPanel getPanel() {
        return addGroupPanel;
    }
}
