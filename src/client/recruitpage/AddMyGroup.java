package client.recruitpage;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import client.CustomFont;
import client.MainPage.MainPage;

import java.awt.Font;
import javax.swing.JButton;

public class AddMyGroup {

	protected RoundedPanel addGroupPanel;
	private MainPage mainPage;
	private Group group;
	CustomFont customFont = new CustomFont();
	
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
		missionTitleLabel.setFont(customFont.deriveFont(Font.BOLD,25));
		missionTitleLabel.setBounds(26, 12, 232, 35);
		addGroupPanel.add(missionTitleLabel);
		
		JLabel activityPeriod = new JLabel("활동기간: ");
		activityPeriod.setFont(customFont.deriveFont(Font.BOLD,15));
		activityPeriod.setBounds(26, 53, 70, 25);
		addGroupPanel.add(activityPeriod);
		
		JLabel activityPeriodLabel = new JLabel(group.getStartDateYear() + ". " + group.getStartDateMonth() + ". " + group.getStartDateDay() + " ~ " + group.getEndDateYear() + ". " + group.getEndDateMonth() + ". " + group.getEndDateDay());
		activityPeriodLabel.setFont(customFont.deriveFont(Font.PLAIN,15));
		activityPeriodLabel.setBounds(94, 53, 352, 25);
		addGroupPanel.add(activityPeriodLabel);
		
		JLabel activitycontents = new JLabel("활동내용: ");
		activitycontents.setFont(customFont.deriveFont(Font.BOLD,15));
		activitycontents.setBounds(26, 83, 70, 25);
		addGroupPanel.add(activitycontents);
		
		JLabel missionLabel = new JLabel(group.getMission());
		missionLabel.setFont(customFont.deriveFont(Font.PLAIN,15));
		missionLabel.setBounds(94, 83, 352, 25);
		addGroupPanel.add(missionLabel);
		
		JLabel Personnel = new JLabel("참여인원: ");
		Personnel.setFont(customFont.deriveFont(Font.BOLD,15));
		Personnel.setBounds(26, 113, 70, 25);
		addGroupPanel.add(Personnel);
		
		JLabel personnelLabel = new JLabel(group.getUserCount() + "명");
		personnelLabel.setFont(customFont.deriveFont(Font.PLAIN,15));
		personnelLabel.setBounds(94, 113, 352, 25);
		addGroupPanel.add(personnelLabel);
		
		JLabel category = new JLabel(group.getCategory());
	    category.setForeground(new Color(56, 183, 255));
	    category.setHorizontalAlignment(JLabel.CENTER);
	    category.setFont(customFont.deriveFont(Font.PLAIN,17));
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
