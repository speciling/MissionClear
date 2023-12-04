package client.MainPage;

import javax.swing.*;
import java.awt.*;
import client.detailMyGroup.DetailMyGroup;
import client.CustomFont;
import client.db.ClientDBManager;
import client.detailMyGroup.DetailMyGroup;
import client.login.Login;
import client.mypage.Mypage;
import client.recruitpage.Group;
import client.recruitpage.MyGroupList;
import client.recruitpage.RecruitGroupMember;
import org.json.simple.JSONObject;

/**
 * 로그인 후 모든 페이지를 관리하는 메인페이지 클래스입니다. Main page class managing all pages after
 * login.
 * 
 * @author choyeonwoo
 *
 */

public class MainPage extends JFrame {
	/**
	 * 사용자 정의 폰트를 사용하기 위한 CustomFont 객체입니다.
	 */
	CustomFont customFont = new CustomFont();
	/**
	 * 메인 페이지의 전역 패널 객체이고 페이지를 이곳에서 전환합니다.
	 */
	public JPanel globPan;
	/**
	 * 사용자의 이름을 나타내는 문자열입니다. 초기값은 "이름을 입력해주세요"입니다.
	 */
	public String userName = "이름을 입력해주세요";
	/**
	 * 사용자의 고유 식별자를 나타내는 정수형 변수입니다.
	 */
	public int uid;
	/**
	 * 사용자의 프로필 사진 경로를 나타내는 문자열입니다. 초기값은 기본 이미지의 경로입니다.
	 */
	public String userPic = "./resource/MainPage/defaultPic.png";
	
	public Container main;
	public static MainPage instance;

	/**
	 * DB에서 userName과 userPic 같은 유저의 정보를 가져오는 함수이다.
	 */
	public void callingInfo() {
		;
	}

	/**
	 * JPanel의 크기를 메인페이지에 맞는 절대적인 크기로 설정하는 함수입니다.
	 * 
	 *
	 * @param p 설정할 JPanel
	 * @return 설정된 JPanel
	 */
	public JPanel makePan(JPanel p) {
		p.setBounds(0, 0, 943, 781);
		return p;
	}

	/**
	 * 메인페이지를 새로고침하는 함수입니다.
	 */
	private void refreshPan() {
		if (globPan != null)
			this.remove(globPan);
		globPan = new JPanel();
		globPan.setBackground(Color.gray);
		globPan.setBounds(257, 69, 943, 781);
		globPan.setLayout(null);
		main.add(globPan);
	}

	/**
	 * MainPage 클래스의 생성자입니다.
	 *
	 * @param vis 페이지의 표시 여부
	 */
	public MainPage(boolean vis) {
		instance = this;
		initializeMainPage();
	}

	/**
	 * 메인 페이지를 초기화하고 화면을 구성하는 메서드입니다. 사용자 정보를 가져오고 화면의 구성요소들을 설정합니다.
	 */
	public void initializeMainPage() {
		// 사용자 정보 가져오기
		JSONObject myInfo = ClientDBManager.getMyInfo();
		this.userName = myInfo.get("nickname").toString();
		String pfp = myInfo.get("pfp").toString();
		if (!pfp.equals(""))
			this.userPic = myInfo.get("pfp").toString();
		uid = Integer.parseInt(myInfo.get("uid").toString());
		// 메인 페이지의 배경색과 로고 경로 설정
		Color mainColor = new Color(56, 183, 255);
		String logoPath = "./resource/MainPage/logo.png";
		// 프레임 설정
		setResizable(false);
		main = getContentPane();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(new Dimension(1210, 880));
		setBackground(Color.gray);
		main.setLayout(new BorderLayout());
		main.setPreferredSize(new Dimension(1200, 850));
		main.setBackground(mainColor);
		main.setLayout(null);
		// 상단 마이 페이지 정보 영역 설정
		JPanel mainInfo = new JPanel();
		mainInfo.setBackground(Color.white);
		mainInfo.setLayout(new BorderLayout());
		JPanel loc = new JPanel();
		JLabel userNameInfo = new JLabel(userName + " 님");
		userNameInfo.setFont(customFont.deriveFont(Font.BOLD, 20));
		ImageIcon userPicIcon = new ImageIcon(userPic);
		Image img = userPicIcon.getImage();
		Image updateImg = img.getScaledInstance(58, 58, Image.SCALE_SMOOTH);
		ImageIcon updateIcon = new ImageIcon(updateImg);

		JLabel userPicInfo = new JLabel(updateIcon);
		loc.add(userNameInfo);
		loc.add(userPicInfo);
		loc.setBackground(Color.white);
		mainInfo.add(loc, BorderLayout.EAST);

		mainInfo.setBackground(Color.white);
		mainInfo.setBounds(257, 0, 943, 69);
		main.add(mainInfo, BorderLayout.NORTH);
		// 패널 갱신
		refreshPan();
		// 메뉴 바 설정
		JPanel menuBar = new JPanel();
		menuBar.setLayout(null);
		menuBar.setBounds(0, 0, 257, 850);
		menuBar.setBackground(mainColor);
		main.add(menuBar);
		// 로고삽입
		JPanel logoP = new JPanel();
		logoP.setBackground(mainColor);
		JLabel logo = new JLabel();
		ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("MainPage/logo.png"));
		logo.setIcon(icon);
		logoP.add(logo);
		logoP.setBounds(13, 23, 227, 139);
		menuBar.add(logoP);
		// 페이지이동버튼3개
		JPanel bpRecruit = new JPanel();
		bpRecruit.setBounds(0, 242, 257, 59);
		bpRecruit.setBackground(mainColor);
		ImageIcon iconRecruit = new ImageIcon(getClass().getClassLoader().getResource("MainPage/group.png"));
		JButton bRecruit = new JButton(iconRecruit);
		bRecruit.setBackground(mainColor);
		bRecruit.setBorderPainted(false);
		bRecruit.setFocusPainted(false);
		bpRecruit.add(bRecruit);

		JPanel bpMyGroup = new JPanel();
		bpMyGroup.setBounds(0, 242 + 59, 257, 59);
		bpMyGroup.setBackground(mainColor);
		ImageIcon iconMyGroup = new ImageIcon(getClass().getClassLoader().getResource("MainPage/mygroup.png"));
		JButton bMyGroup = new JButton(iconMyGroup);
		bMyGroup.setBackground(mainColor);
		bMyGroup.setBorderPainted(false);
		bMyGroup.setFocusPainted(false);
		bpMyGroup.add(bMyGroup);

		JPanel bpMyPage = new JPanel();
		bpMyPage.setBounds(0, 242 + 59 * 2, 257, 59);
		bpMyPage.setBackground(mainColor);
		ImageIcon iconMyPage = new ImageIcon(getClass().getClassLoader().getResource("MainPage/mypage.png"));
		JButton bMyPage = new JButton(iconMyPage);
		bMyPage.setBackground(mainColor);
		bMyPage.setBorderPainted(false); // 버튼 그림자 없애기
		bMyPage.setFocusPainted(false);
		bpMyPage.add(bMyPage);

		menuBar.add(bpRecruit);
		menuBar.add(bpMyGroup);
		menuBar.add(bpMyPage);

		// 로그아웃 부분 설정
		JPanel logout_panel = new JPanel();
		logout_panel.setBackground(mainColor);

		ImageIcon line_icon = new ImageIcon(getClass().getClassLoader().getResource("MainPage/Line 1.png"));
		JLabel line = new JLabel(line_icon);
		logout_panel.add(line);

		ImageIcon logout_icon = new ImageIcon(getClass().getClassLoader().getResource("MainPage/logout.png"));
		JButton logout = new JButton(logout_icon);
		logout.setBorderPainted(false);
		logout.setPreferredSize(new Dimension(257, 59));
		logout_panel.add(logout);

		logout_panel.setBounds(0, 751, 257, 90);
		menuBar.add(logout_panel);

		setVisible(true);

		// 화면전환 버튼 클릭 이벤트
		bRecruit.addActionListener(event -> {
			changePanel("group");
		});
		bMyGroup.addActionListener(event -> {
			changePanel("mygroup");
		});
		bMyPage.addActionListener(event -> {
			changePanel("mypage");
		});

		// 로그아웃 버튼 클릭 이벤트
		logout.addActionListener(event -> {
			createLogoutPopup();
		});
	}

	/**
	 * 로그아웃 팝업을 생성하는 함수입니다.
	 */
	public void createLogoutPopup() {
		int answer = JOptionPane.showConfirmDialog(this, "로그아웃 하시겠습니까?", "", JOptionPane.YES_NO_OPTION);
		if (answer == JOptionPane.YES_OPTION) {
			changePanel("login");
		} else {
			System.out.println("종료를 취소합니다.");
		}
		logout(answer);
	}

	/**
	 * 로그아웃을 처리하는 함수입니다.
	 *
	 * @param check 로그아웃 확인 여부
	 */
	private void logout(int check) {
		if (check == 0) {
			Login l = new Login();
		}
	}

	/**
	 * 그룹 상세 정보 패널로 전환하는 함수입니다.
	 *
	 * @param a 그룹 정보를 담고 있는 Group 객체
	 */
	public void changeDetailPan(Group a) {
		DetailMyGroup dg = new DetailMyGroup(a, true);
		JPanel p = dg.get();
		p = makePan(p);
		refreshPan();

		globPan.add(p);
		globPan.repaint();
		globPan.revalidate();

	}

	/**
	 * 주어진 패널 이름에 따라 메인 페이지의 화면을 변경하는 함수입니다.
	 *
	 * @param panelName 변경할 패널의 이름("group", "mygroup", "mypage", "login")
	 */
	public void changePanel(String panelName) {

		if (panelName.equals("group")) {
			// "group" 패널로 전환하는 구현부
			RecruitGroupMember r = new RecruitGroupMember(true);
			JPanel p = r.get();
			p = makePan(p);
			refreshPan();
			globPan.add(p);
			globPan.repaint();
			globPan.revalidate();
		}

		else if (panelName.equals("mygroup")) {
			// "mygroup" 패널로 전환하는 구현부
			MyGroupList r = new MyGroupList(true);
			JPanel p = r.get();
			p = makePan(p);
			refreshPan();
			globPan.add(p);
			globPan.repaint();
			globPan.revalidate();

		}

		else if (panelName.equals("mypage")) {
			// "mypage" 패널로 전환하는 구현부
			Mypage r = new Mypage(uid, userName, userPic);
			JPanel p = r.get();
			p = makePan(p);
			refreshPan();
			globPan.add(p);
			globPan.repaint();
			globPan.revalidate();
		} else if (panelName.equals("login")) {
			// "login" 패널로 전환하는 구현부
			Login l = new Login();
			l.loginpage();
			ClientDBManager.init();
			this.dispose();
		}
	}
}