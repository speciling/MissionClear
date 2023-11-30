package client.recruitpage;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JPasswordField;

public class InputPasswordPopup {

	private JFrame frame;
	protected RoundedPanel inputPasswordPanel;
	private Group group;
	private JTextField passwordField;
	private JPasswordField passwordField_1;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InputPasswordPopup window = new InputPasswordPopup();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public InputPasswordPopup() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setSize(355, 240);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		inputPasswordPanel = new RoundedPanel(20); 
		inputPasswordPanel.setBounds(0, 0, 339, 199);
		inputPasswordPanel.setForeground(new Color(255, 255, 255));
		inputPasswordPanel.setBackground(new Color(255, 255, 255));
	    frame.getContentPane().add(inputPasswordPanel);
	    inputPasswordPanel.setLayout(null);
	    
	    JLabel passwordLabel = new JLabel("비밀번호 입력");
	    passwordLabel.setFont(new Font("나눔고딕", Font.BOLD, 18));
	    passwordLabel.setBounds(115, 31, 110, 35);
	    inputPasswordPanel.add(passwordLabel);
	    
	    passwordField = new  JPasswordField();
	    passwordField.setBounds(13, 74, 314, 35);
	    passwordField.setBackground(new Color(237, 237, 237));
	    passwordField.setBorder(null);
	    inputPasswordPanel.add(passwordField);
	    passwordField.setColumns(10);
	    
	    JButton enterButton = new JButton("");
	    enterButton.setIcon(new ImageIcon("./resource/RecruitGroupMember/참여하기.png"));
	    enterButton.setBounds(115, 150, 110, 38);
	    enterButton.setBackground(new Color(255, 255, 255));
	    enterButton.setBorderPainted(false);
	    inputPasswordPanel.add(enterButton);
	    
	    
	}
}
