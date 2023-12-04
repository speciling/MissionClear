package client.recruitpage;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import org.json.simple.JSONObject;

import client.CustomFont;
import client.net.ClientSocket;
import server.service.Request;
import server.service.RequestType;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.ImageIcon;


/**
 * 그룹 상세 정보를 표시하는 팝업 창을 관리하는 클래스.
 * 이 클래스는 특정 그룹의 상세 정보를 사용자에게 보여주는 데 사용된다.
 * 사용자는 이 팝업을 통해 그룹의 제목, 설명, 카테고리, 모집 기한, 활동 기간 등의 정보를 확인할 수 있다.
 */
public class GroupDetailPopup {
	private MyGroupList myGroupList;
	private JFrame frame;
	protected RoundedPanel groupDetailPopupPanel;
	private Group group;
	CustomFont customFont = new CustomFont();
	
	/**
     * GroupDetailPopup의 생성자.
     * @param group 상세 정보를 표시할 그룹 객체
     */
	public GroupDetailPopup(Group group) {
        this.group = group;
        initialize();
    }

	/**
     * 팝업 창의 내용을 초기화하는 메서드.
     * 프레임과 레이아웃을 설정하고, 레이블, 버튼 및 기타 UI 요소를 구성한다.
     */
	void initialize() {
		frame = new JFrame();
		frame.setSize(406, 387);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null);
		frame.setUndecorated(true);
		
		// 패널 설정
		groupDetailPopupPanel = new RoundedPanel(20); 
		groupDetailPopupPanel.setBounds(0, 0, 406, 387);
		groupDetailPopupPanel.setForeground(new Color(255, 255, 255));
		groupDetailPopupPanel.setBackground(new Color(255, 255, 255));
	    frame.getContentPane().add(groupDetailPopupPanel);
	    groupDetailPopupPanel.setLayout(null);
	    
	    // 그룹 제목 레이블 설정
	    JLabel title = new JLabel(group.getTitle());
	    title.setFont(customFont.deriveFont(Font.BOLD,20));
	    title.setBounds(19, 53, 357, 35);
	    groupDetailPopupPanel.add(title);
	    
	    // 미션 설명 레이블 설정
	    JLabel description = new JLabel(group.getDescription());
	    description.setFont(customFont.deriveFont(Font.PLAIN,18));
	    description.setBounds(19, 100, 369, 42);
	    groupDetailPopupPanel.add(description);
	    
	    // 구분선 설정
	    JSeparator separator = new JSeparator();
	    separator.setForeground(new Color(128, 128, 128));
	    separator.setBounds(19, 168, 374, 1); // 위치와 크기 설정
	    groupDetailPopupPanel.add(separator);
	    
	    // 그룹 카테고리 레이블 설정
	    JLabel category = new JLabel(group.getCategory());
	    category.setForeground(new Color(56, 183, 255));
	    category.setHorizontalAlignment(JLabel.CENTER);
	    category.setFont(customFont.deriveFont(Font.PLAIN,17));
	    category.setBounds(19, 180, 85, 35);
	    Color customColor = new Color(56, 183, 255);
	    category.setBorder(new RoundedBorder(10, customColor));
	    groupDetailPopupPanel.add(category);
	    
	    // 팝업 종료 버튼 설정
	    JButton exitButton = new JButton("");
	    exitButton.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            frame.dispose(); // 현재 프레임 닫기
	        }
	    });
	    groupDetailPopupPanel.add(exitButton);
	    exitButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("RecruitGroupMember/exit.png")));
	    exitButton.setBounds(351, 3, 50, 50);
	    
	    // 버튼 배경 투명 설정
	    exitButton.setOpaque(false);
	    exitButton.setContentAreaFilled(false);
	    exitButton.setBorderPainted(false);
	    exitButton.setFocusPainted(false); // 포커스 테두리 제거
	    
	    groupDetailPopupPanel.add(exitButton);
	    
	    // 그룹 상세 정보 레이블 설정
	    JLabel recruitDeadlineLabel = new JLabel("모집기한: ");
	    recruitDeadlineLabel.setBounds(19, 245, 100, 21);
	    recruitDeadlineLabel.setFont(customFont.deriveFont(Font.BOLD,18));
	    groupDetailPopupPanel.add(recruitDeadlineLabel);
	    
	    JLabel recruitDeadline = new JLabel(group.getDeadlineYear() + ". " + group.getDeadlineMonth() + ". " + group.getDeadlineDay() + " 까지");
	    recruitDeadline.setFont(customFont.deriveFont(Font.PLAIN,18));
	    recruitDeadline.setBounds(100, 245, 298, 21);
	    groupDetailPopupPanel.add(recruitDeadline);

	    JLabel recruitmentCapacityLabel = new JLabel("모집인원: ");
	    recruitmentCapacityLabel.setBounds(19, 268, 100, 21);
	    recruitmentCapacityLabel.setFont(customFont.deriveFont(Font.BOLD,18));
	    groupDetailPopupPanel.add(recruitmentCapacityLabel);
	    
	    JLabel recruitmentCapacity = new JLabel(group.getCapacity() + "명");
	    recruitmentCapacity.setFont(customFont.deriveFont(Font.PLAIN,18));
	    recruitmentCapacity.setBounds(100, 268, 50, 21);
	    groupDetailPopupPanel.add(recruitmentCapacity);
	    
	    JLabel activityPeriodLabel = new JLabel("활동기간: ");
	    activityPeriodLabel.setBounds(19, 291, 100, 21);
	    activityPeriodLabel.setFont(customFont.deriveFont(Font.BOLD,18));
	    groupDetailPopupPanel.add(activityPeriodLabel);
	    
	    JLabel activityPeriod = new JLabel(group.getStartDateYear() + ". " + group.getStartDateMonth() + ". " + group.getStartDateDay() + ". ~ " + group.getEndDateYear() + ". " + group.getEndDateMonth() + ". " + group.getEndDateDay());
	    activityPeriod.setFont(customFont.deriveFont(Font.PLAIN,18));
	    activityPeriod.setBounds(100, 291, 357, 21);
	    groupDetailPopupPanel.add(activityPeriod);

	    JLabel activitycontents = new JLabel("활동내용: ");
		activitycontents.setFont(customFont.deriveFont(Font.BOLD,18));
		activitycontents.setBounds(19, 314, 100, 21);
		groupDetailPopupPanel.add(activitycontents);
		
		JLabel missionLabel = new JLabel(group.getMission());
		missionLabel.setFont(customFont.deriveFont(Font.PLAIN,18));
		missionLabel.setBounds(100, 314, 352, 21);
		groupDetailPopupPanel.add(missionLabel);
	    // 그룹 참여 버튼 설정
	    JButton enterButton = new JButton("");
	    enterButton.setBackground(new Color(255, 255, 255));
	    enterButton.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    	}
	    });
	    enterButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("RecruitGroupMember/참여하기.png")));
	    enterButton.setBounds(149, 334, 110, 38);
	    enterButton.setBorderPainted(false);
	    enterButton.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            if (group.isSecretRoom()) {
	                // 비밀방 관련 처리
	                InputPasswordPopup passwordPopup = new InputPasswordPopup(group);
	                passwordPopup.frame.setVisible(true);
	                
	            } else {
	                // 비밀방이 아닌 경우
	            	JSONObject data = new JSONObject();
	            	data.put("gid", group.getGid());
	            	data.put("password", "");
	            	Request request = new Request(RequestType.ENTERGROUP, data);
	        
	            	ClientSocket.send(request);
	            	
	            	if(ClientSocket.getResult()) {
	            		// 방이동	
	            		MyGroupList myGroupList = new MyGroupList(true);
	                    myGroupList.refreshGroupList();
	                    frame.dispose();
	            	}
	            	else {
	            		// 경고문구
	            	}
	            	
	            }
	        }
	    });

	    groupDetailPopupPanel.add(enterButton);
	}

	 /**
     * 팝업 창의 JFrame 객체를 반환하는 메서드.
     * @return 이 팝업의 JFrame 객체
     */
	public JFrame getFrame() {
        return frame;
    }
}