package client.recruitpage;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import client.MainPage.MainPage;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;

public class MyGroupList extends MainPage{

	private JPanel myGroup;

	JPanel a;
	
	public JPanel get() {
	    return a;
	}
	/**
	 * Launch the application.
	 */

	/**
	 * Create the application.
	 */
	public MyGroupList(boolean vis) {
		super(vis);
		initializeMyGroupList();
	}

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
		
		JButton btnNewButton = new JButton("");
		btnNewButton.setBounds(0, 0, 932, 189);
		btnNewButton.setBorderPainted(false);
		btnNewButton.setContentAreaFilled(false);
		btnNewButton.setFocusPainted(false);
		panel.add(btnNewButton);
		
		
		MainPage mp = new MainPage(true);
		JPanel a = mp.globPan;
		a.setLayout(null);
        a.add(myGroup);
	}
}
