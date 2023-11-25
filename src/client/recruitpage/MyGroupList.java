package client.recruitpage;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import client.MainPage.MainPage;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
/**
 * Class representing the list of groups a user is part of.
 * This class extends MainPage and provides a user interface for displaying a list of groups the user is in.
 */
public class MyGroupList extends MainPage{

	private JPanel myGroup;

	//JPanel a;
	
	 /**
     * Retrieves the main panel of the group list interface.
     * @return the main JPanel component of the group list interface.
     */
	//public JPanel get() {
	//    return a;
	//}
	 /**
     * Constructor to initialize the MyGroupList interface.
     * @param vis Boolean value to set the visibility of the main page.
     */
	public MyGroupList(boolean vis) {
		super(vis);
		initializeMyGroupList();
	}

	/**
     * Initializes and sets up the My Group List interface.
     * This method sets up the layout and components for displaying the list of groups the user is part of.
     */
	private void initializeMyGroupList() {
		// TODO Auto-generated method stub
		myGroup = new JPanel();
		myGroup.setBackground(new Color(246, 246, 246));
		myGroup.setBounds(0, 0, 930, 850);
		myGroup.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("내그룹목록");
		lblNewLabel.setFont(new Font("나눔고딕", Font.BOLD, 20));
		lblNewLabel.setBounds(13, 22, 156, 35);
		myGroup.add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255));
		panel.setBounds(0, 93, 932, 189);
		myGroup.add(panel);
		panel.setLayout(null);
		
		JLabel title = new JLabel("돈 아껴서 부자되자!");
		title.setFont(new Font("나눔고딕", Font.BOLD, 20));
		title.setBounds(26, 12, 232, 35);
		panel.add(title);
		
		JLabel lblNewLabel_1 = new JLabel("<html><b>활동기간</b>: 2023. 09. 10 ~ 2023. 10. 09<br>\r\n<b>활동내용</b>: 매일 매일 사용한 돈 인증<br>\r\n<b>참여인원</b>: 5명</html>");
		lblNewLabel_1.setFont(new Font("나눔고딕", Font.PLAIN, 18));
		lblNewLabel_1.setBounds(26, 53, 352, 93);
		panel.add(lblNewLabel_1);
		
		JButton enterDetailMyGroupButton = new JButton("");
		enterDetailMyGroupButton.setBounds(0, 0, 932, 189);
		enterDetailMyGroupButton.setBorderPainted(false);
		enterDetailMyGroupButton.setContentAreaFilled(false);
		enterDetailMyGroupButton.setFocusPainted(false);
		panel.add(enterDetailMyGroupButton);
		
		
		MainPage mp = new MainPage(true);
		JPanel a = mp.globPan;
		a.setLayout(null);
        a.add(myGroup);
	}
}
