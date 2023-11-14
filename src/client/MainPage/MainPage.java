package client.MainPage;

import javax.swing.*;
import java.awt.*;

public class MainPage extends JFrame {

	public JPanel globPan;
	public String userName = "호랑이양말";
	public String userPic = "./resource/DetailMyGroup/Ellipse3.png";
	// 사용자의 이름과 사진을 불러오는 함수를 작성
	public void callingInfo() {
		;
	}
	
	
	
	
	public MainPage(boolean vis) {
		Color mainColor = new Color(56, 183, 255);
		String logoPath = "./resource/MainPage/logo.png";

		Container main = getContentPane();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //
		setSize(1200, 850); // 프레임 크기 설정
		setBackground(Color.gray);
		main.setLayout(new BorderLayout());
		
		main.setBackground(mainColor);
		main.setLayout(null);

		// 위쪽 마이 페이지 정보 만들기 
		JPanel mainInfo = new JPanel();
		mainInfo.setBackground(Color.white);
		mainInfo.setLayout(new BorderLayout());
		JPanel loc = new JPanel();
		JLabel userNameInfo = new JLabel(userName+" 님");
		userNameInfo.setFont(new Font("나눔고딕", Font.BOLD, 20));
		ImageIcon userPicIcon = new ImageIcon(userPic);
		JLabel userPicInfo = new JLabel(userPicIcon);
		loc.add(userNameInfo);
		loc.add(userPicInfo);
		loc.setBackground(Color.white);
		mainInfo.add(loc,BorderLayout.EAST);
		// 사용자의 이름과 사진을 불러오기
		// 함수호출
		
		
		mainInfo.setBackground(Color.white);
		mainInfo.setBounds(257,0,930,69);
		main.add(mainInfo, BorderLayout.NORTH);
		
		
		
		
		
		
		globPan = new JPanel();
		globPan.setBackground(Color.gray);
		globPan.setBounds(257, 69, 930, 850);
		main.add(globPan);
		
		
		// 로고삽입
		JLabel logo = new JLabel();
		ImageIcon icon = new ImageIcon(logoPath);
		logo.setIcon(icon);
		logo.setBounds(6, 23, 227, 139);
		main.add(logo);

		// 페이지이동버튼3개
		JPanel button_panel = new JPanel();
		button_panel.setBackground(mainColor);

		ImageIcon group_icon = new ImageIcon("./resource/MainPage/group.png");
		JButton group = new JButton(group_icon);
		group.setBorderPainted(false); //버튼 그림자 없애기
		group.setPreferredSize(new Dimension(257, 59));
		button_panel.add(group);

		ImageIcon mygroup_icon = new ImageIcon("./resource/MainPage/mygroup.png");
		JButton mygroup = new JButton(mygroup_icon);
		mygroup.setBorderPainted(false);
		mygroup.setPreferredSize(new Dimension(257, 59));
		button_panel.add(mygroup);

		ImageIcon mypage_icon = new ImageIcon("./resource/MainPage/mypage.png");
		JButton mypage = new JButton(mypage_icon);
		mypage.setBorderPainted(false);
		mypage.setPreferredSize(new Dimension(257, 59));
		button_panel.add(mypage);

		button_panel.setBounds(0, 232, 257, 59 * 3 + 50);
		main.add(button_panel);

		// 로그아웃부분
		JPanel logout_panel = new JPanel();
		logout_panel.setBackground(mainColor);

		ImageIcon line_icon = new ImageIcon("./resource/MainPage/Line 1.png");
		JLabel line = new JLabel(line_icon);
		logout_panel.add(line);

		ImageIcon logout_icon = new ImageIcon("./resource/MainPage/logout.png");
		JButton logout = new JButton(logout_icon);
		logout.setBorderPainted(false);
		logout.setPreferredSize(new Dimension(257, 59));
		logout_panel.add(logout);

		logout_panel.setBounds(0, 720, 257, 850 - 740);
		main.add(logout_panel);

		setVisible(true); // 화면에 프레임 출력

		// 화면전환 버튼 클릭 이벤트
		group.addActionListener(event -> {
			// 현재창을 false
			changePanel("group");
		});
		mygroup.addActionListener(event -> {
			// 현재창을 false
			changePanel("mygroup");
		});
		mypage.addActionListener(event -> {
			// 현재창을 false
			changePanel("mypage");
		});

		// 로그아웃 버튼 클릭 이벤트
		logout.addActionListener(event -> {
			createLogoutPopup();
		});
	}

	/*private Dimension newDemension(int i, int j) {
		// TODO Auto-generated method stub
		return null;
	}*/

	public void createLogoutPopup() {
		int answer = JOptionPane.showConfirmDialog(this, "로그아웃 하시겠습니까?", "", JOptionPane.YES_NO_OPTION);
		if (answer == JOptionPane.YES_OPTION) {
			System.out.println("프로그램을 종료합니다.");
		} else {
			System.out.println("종료를 취소합니다.");
		}
		// answer이 0이면 실행 1이면 실행취소
		logout(answer);
	}

	public void logout(int check) {
		if (check == 0) {
			// false를 할 방법을 잘 찾아보기 MenuBar(false);
			// logout(true);
		}
	}

	public void changePanel(String panelName) {
		// 모두 페이지가 존재한다고 가정하고 패널을 불러올 때 boolean 매개변수가 존재하는 함수호출을 통해 불러오기(현재 함수의
		// setvisible은 false)
		if (panelName.equals("group"))
			;// add함수(True);
		else if (panelName.equals("mygroup"))
			;// add함수(True);
		else if (panelName.equals("mypage"))
			;
	}
	//public void changePanel()
}
