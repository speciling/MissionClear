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

import client.net.ClientSocket;
import server.service.Request;
import server.service.RequestType;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.ImageIcon;


/**
 * Class representing the detail popup for a group.
 * This class is responsible for displaying the detailed information of a group in a popup window.
 */
public class GroupDetailPopup {
	private MyGroupList myGroupList;
	private JFrame frame;
	protected RoundedPanel groupDetailPopupPanel;
	private Group group;
    /**
     * Constructor for GroupDetailPopup.
     * Initializes the UI components of the popup.
     */
	public GroupDetailPopup(Group group) {
        this.group = group;
        initialize();
    }

    /**
     * Initializes the contents of the popup window.
     * Sets up the frame and layout of the popup, including labels, buttons, and other UI elements.
     */ 
	void initialize() {
		frame = new JFrame();
		frame.setSize(406, 387);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null);
		frame.setUndecorated(true);
		
		groupDetailPopupPanel = new RoundedPanel(20); 
		groupDetailPopupPanel.setBounds(0, 0, 406, 387);
		groupDetailPopupPanel.setForeground(new Color(255, 255, 255));
		groupDetailPopupPanel.setBackground(new Color(255, 255, 255));
	    frame.getContentPane().add(groupDetailPopupPanel);
	    groupDetailPopupPanel.setLayout(null);
	    
	    JLabel title = new JLabel(group.getTitle());
	    title.setFont(new Font("나눔고딕", Font.BOLD, 20));
	    title.setBounds(19, 53, 357, 35);
	    groupDetailPopupPanel.add(title);
	    
	    JLabel description = new JLabel(group.getDescription());
	    description.setFont(new Font("나눔고딕", Font.PLAIN, 18));
	    description.setBounds(19, 100, 369, 42);
	    groupDetailPopupPanel.add(description);
	    
	    JSeparator separator = new JSeparator();
	    separator.setForeground(new Color(128, 128, 128));
	    separator.setBounds(19, 168, 374, 1); // 위치와 크기 설정
	    groupDetailPopupPanel.add(separator);
	    
	    JLabel category = new JLabel(group.getCategory());
	    category.setForeground(new Color(56, 183, 255));
	    category.setHorizontalAlignment(JLabel.CENTER);
	    category.setFont(new Font("나눔고딕", Font.PLAIN, 17));
	    category.setBounds(19, 180, 85, 35);
	    Color customColor = new Color(56, 183, 255);
	    category.setBorder(new RoundedBorder(10, customColor));
	    groupDetailPopupPanel.add(category);
	    
	    JButton exitButton = new JButton("");
	    exitButton.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            frame.dispose(); // 현재 프레임 닫기
	        }
	    });
	    groupDetailPopupPanel.add(exitButton);
	    exitButton.setIcon(new ImageIcon("./resource/RecruitGroupMember/exit.png"));
	    exitButton.setBounds(351, 3, 50, 50);
	    
	 // 버튼 배경 투명 설정
	    exitButton.setOpaque(false);
	    exitButton.setContentAreaFilled(false);
	    exitButton.setBorderPainted(false);
	    exitButton.setFocusPainted(false); // 포커스 테두리 제거
	    
	    groupDetailPopupPanel.add(exitButton);
	    
	    JLabel missionInfo = new JLabel("<html><b>모집기한</b>: " + group.getDeadlineYear() + ". " + group.getDeadlineMonth() + ". " + group.getDeadlineDay() + "까지<br>\r\n<b>모집인원</b>: " + group.getCapacity() + "명<br>\r\n<b>활동기간</b>: " + group.getStartDateYear() + ". " + group.getStartDateMonth() + ". " + group.getStartDateDay() + " ~ " + group.getEndDateYear() + ". " + group.getEndDateMonth() + ". " + group.getEndDateDay() + "<br>\r\n<b>활동내용</b>: " + group.getMission() + "</html>");
	    missionInfo.setFont(new Font("나눔고딕", Font.PLAIN, 18));
	    missionInfo.setBounds(19, 220, 357, 113);
	    groupDetailPopupPanel.add(missionInfo);
	    
	    JButton enterButton = new JButton("");
	    enterButton.setBackground(new Color(255, 255, 255));
	    enterButton.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    	}
	    });
	    enterButton.setIcon(new ImageIcon("./resource/RecruitGroupMember/참여하기.png"));
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
     * Gets the frame of the popup window.
     * @return The JFrame object of this popup.
     */
	public JFrame getFrame() {
        return frame;
    }
}