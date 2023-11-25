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
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.ImageIcon;


/**
 * Class representing the detail popup for a group.
 * This class is responsible for displaying the detailed information of a group in a popup window.
 */
public class GroupDetailPopup {

	private JFrame frame;
	protected RoundedPanel groupDetailPopupPanel;

    /**
     * Constructor for GroupDetailPopup.
     * Initializes the UI components of the popup.
     */
	public GroupDetailPopup() {
		initialize();
	}


    /**
     * Initializes the contents of the popup window.
     * Sets up the frame and layout of the popup, including labels, buttons, and other UI elements.
     */ 
	void initialize() {
		frame = new JFrame();
		frame.setSize(422, 425);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		groupDetailPopupPanel = new RoundedPanel(20); 
		groupDetailPopupPanel.setBounds(0, 0, 406, 387);
		groupDetailPopupPanel.setForeground(new Color(255, 255, 255));
		groupDetailPopupPanel.setBackground(new Color(255, 255, 255));
	    frame.getContentPane().add(groupDetailPopupPanel);
	    groupDetailPopupPanel.setLayout(null);
	    
	    JLabel title = new JLabel("미라클모닝 챌린지 할 사람 구합니다!");
	    title.setFont(new Font("나눔고딕", Font.BOLD, 20));
	    title.setBounds(19, 53, 357, 35);
	    groupDetailPopupPanel.add(title);
	    
	    JLabel lblNewLabel = new JLabel("<html>정말 열심히 할 분만 모집하고 있습니다.<br>벌금 있습니다!</html>");
	    lblNewLabel.setFont(new Font("나눔고딕", Font.PLAIN, 18));
	    lblNewLabel.setBounds(19, 100, 369, 42);
	    groupDetailPopupPanel.add(lblNewLabel);
	    
	    JSeparator separator = new JSeparator();
	    separator.setForeground(new Color(128, 128, 128));
	    separator.setBounds(19, 168, 374, 1); // 위치와 크기 설정
	    groupDetailPopupPanel.add(separator);
	    
	    JLabel category = new JLabel("챌린지");
	    category.setForeground(new Color(56, 183, 255));
	    category.setHorizontalAlignment(JLabel.CENTER);
	    category.setFont(new Font("나눔고딕", Font.PLAIN, 17));
	    category.setBounds(19, 180, 81, 35);
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
	    
	    JLabel lblNewLabel_1 = new JLabel("<html><b>모집기한</b>: 2023. 10. 01까지<br>\r\n<b>모집인원</b>: 5명<br>\r\n<b>활동기간</b>: 2023. 10. 03 ~ 2023. 12. 03<br>\r\n<b>활동내용</b>: 매일매일 기상시간 인증</html>");
	    lblNewLabel_1.setFont(new Font("나눔고딕", Font.PLAIN, 18));
	    lblNewLabel_1.setBounds(19, 220, 357, 113);
	    groupDetailPopupPanel.add(lblNewLabel_1);
	    
	    JButton enterButton = new JButton("");
	    enterButton.setBackground(new Color(255, 255, 255));
	    enterButton.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    	}
	    });
	    enterButton.setIcon(new ImageIcon("./resource/RecruitGroupMember/참여하기.png"));
	    enterButton.setBounds(149, 334, 110, 38);
	    enterButton.setBorderPainted(false);
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