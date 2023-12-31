package client.detailMyGroup;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.RoundRectangle2D;
import java.io.File;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.basic.BasicScrollBarUI;

import org.json.simple.JSONObject;

import client.CustomFont;
import client.MainPage.MainPage;
import client.net.ClientSocket;
import client.recruitpage.Group;
import server.service.Request;
import server.service.RequestType;

/**
 * 둥근 사각형 모양의 패널을 나타내는 클래스입니다.
 */
class RoundedPanel2 extends JPanel {
	private int radius;

	/**
	 * 둥근 패널을 생성합니다.
	 *
	 * @param radius 패널의 둥근 부분의 반지름
	 */
	public RoundedPanel2(int radius) {
		this.radius = radius;
		setOpaque(false);
	}

	/**
	 * 패널을 그릴 때 호출되는 메서드로, 둥근 모양의 사각형을 그립니다.
	 *
	 * @param g 그래픽스 컨텍스트
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.fill(new RoundRectangle2D.Double(0, 0, getWidth() - 1, getHeight() - 1, radius, radius));
	}
}

/**
 * 특정 그룹에 입장 했을 때 보여주는 창 클래스입니다.
 *
 * @author choyeonwoo
 * 
 */
public class DetailMyGroup extends JFrame {
	/**
	 * 사용자 정의 폰트를 사용하기 위한 CustomFont 객체입니다.
	 */
	CustomFont customFont = new CustomFont();
	/**
	 * 그룹의 정보를 저장합니다.
	 */
	String authPicPath;
	Group groupData;
	int gid;
	String missionName;
	String missionInfo;

	Calendar missionStart = Calendar.getInstance();
	Calendar missionEnd = Calendar.getInstance();
	String startYear;
	String startMonth;
	String startDay;
	String endYear;
	String endMonth;
	String endDay;
	Calendar today = Calendar.getInstance();
	MakeProgressData proData;

	public Vector<Vector<Integer>> progresses;

	/**
	 * 두 날짜 사이의 일 수를 계산하는 메서드입니다.
	 *
	 * @param start 시작 날짜
	 * @param end   종료 날짜
	 * @return 시작과 종료 날짜 사이의 일 수
	 */
	public int calculateDayCount(Calendar start, Calendar end) {
		start.set(Calendar.HOUR_OF_DAY, 0);
		start.set(Calendar.MINUTE, 0);
		start.set(Calendar.SECOND, 0);
		start.set(Calendar.MILLISECOND, 0);

		end.set(Calendar.HOUR_OF_DAY, 0);
		end.set(Calendar.MINUTE, 0);
		end.set(Calendar.SECOND, 0);
		end.set(Calendar.MILLISECOND, 0);

		long diffInMillis = end.getTimeInMillis() - start.getTimeInMillis();
		long diffInDays = TimeUnit.MILLISECONDS.toDays(diffInMillis);
		return ((int) diffInDays) + 1;
	}

	/**
	 * 그룹의 날짜 정보를 저장하는 필드 입니다.
	 */
	int dayCount;
	int todayCount;
	/**
	 * 사용자 미션 진행도 정보를 저장하는 필드 입니다.
	 */
	int[][] detailProgress;
	int[] missionProgRage;
	/**
	 * 사용자의 정보를 저장하는 필드입니다.
	 */
	MakeUserData userData;
	HashMap nicknames;
	HashMap pfps;
	List<Integer> uids;
	/**
	 * 채팅정보를 저장하는 필드입니다.
	 */
	MakeChatData chatData;
	HashMap chatUid;
	HashMap chatMessage;
	HashMap chatIsPic;
	List<Integer> chatids;
	/**
	 * 화면을 구성하는 객체입니다.
	 */
	JPanel ff = new JPanel();
	public JPanel detailMyGroupP;

	/**
	 * 패널 객체를 반환합니다.
	 *
	 * @return 생성된 패널 객체
	 */
	public JPanel get() {
		return detailMyGroupP;
	}

	/**
	 * 세부 진행 팝업을 생성하고 표시합니다.
	 *
	 * @param i   그룹 내에서의 인덱스
	 * @param pan 세부 정보를 담은 패널
	 */
	public void createProgressDetailPopup(int i, JPanel pan) {
		JFrame popupF = new JFrame();
		popupF.setSize(434, 307);
		popupF.setVisible(true);
		popupF.setLocation(570, 230);

		JPanel popup = new JPanel();
		popup.setBackground(Color.white);
		popupF.add(popup);
		popup.setLayout(null);
		JLabel title = new JLabel("진행상황");
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setVerticalAlignment(JLabel.CENTER);
		title.setFont(customFont.deriveFont(Font.BOLD, 30));
		title.setBounds(0, 0, 434, 70);
		popup.add(title);
		pan.setBounds(0, 70, 434, 72);
		popup.add(pan);

		// 세부진행도 패널 만들기
		JPanel detailProgressP = new JPanel();
		detailProgressP.setBackground(Color.white);
		detailProgressP.setLayout(new GridLayout(3, 10, 0, 0));
		detailProgressP.setBounds(10, 142, 415, 124);
		int count = 0;
		for (int j = 0; j < detailProgress[i].length; j++) {
			count++;
			String path = null;
			if (detailProgress[i][j] == 0) {
				path = "DetailMyGroup/Default.png";
			} else if (detailProgress[i][j] == 1) {
				path = "DetailMyGroup/clear.png";
			} else if (detailProgress[i][j] == 2) {
				path = "DetailMyGroup/fail.png";
			}
			ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource(path));
			JLabel detail = new JLabel(icon);
			detail.setSize(50, 50);
			detailProgressP.add(detail);
		}
		while (count != 30) { // limit = 30
			detailProgressP.add(new JLabel());
			count++;
		}
		popup.add(detailProgressP);

	}

	/**
	 * 사용자 미션진행상황을 생성하고 반환합니다.
	 *
	 * @param name 사용자 이름
	 * @param path 프로필 사진 경로
	 * @param rage 진행률
	 * @return 생성된 사용자 프로그레스 패널
	 */
	public JPanel tempPanel(String name, String path, int rage) {
		JPanel p = makeUserProgress(name, path, rage, 1);
		return p;
	}

	/**
	 * 사용자 미션진행상황 패널을 생성하고 반환합니다.
	 *
	 * @param name 사용자 이름
	 * @param path 프로필 사진 경로
	 * @param rage 진행률
	 * @param i    인덱스
	 * @return 생성된 사용자 프로그레스 패널
	 */
	public JPanel makeUserProgress(String name, String path, int rage, int i) {
		JPanel make = new JPanel();
		make.setBackground(Color.white);
		make.setLayout(null);
		
		ImageIcon userPicIcon;
		if(path.equals(""))
			userPicIcon = new ImageIcon(getClass().getClassLoader().getResource("MainPage/defaultPic.png"));
		else
			userPicIcon = new ImageIcon(path);
		JButton userPic = new JButton(userPicIcon);
		userPic.setContentAreaFilled(false);
		userPic.setBorderPainted(false);
		userPic.setBounds(27, 6, 60, 60);
		make.add(userPic);

		JPanel up = new JPanel();
		up.setBackground(Color.white);
		make.add(up);
		up.setBounds(100, 0, 260, 36);
		up.setLayout(new BorderLayout());
		JLabel userName = new JLabel(name);
		userName.setFont(customFont.deriveFont(Font.BOLD, 20));
		up.add(userName, BorderLayout.WEST);
		JLabel userRage = new JLabel(rage + "%");
		userRage.setFont(customFont.deriveFont(Font.PLAIN, 15));
		up.add(userRage, BorderLayout.EAST);

		JPanel down = new JPanel();
		down.setBackground(Color.white);
		down.setLayout(null);
		make.add(down);
		down.setBounds(100, 36, 260, 36);
		JProgressBar progressBar = new JProgressBar();
		progressBar.setBounds(0, 0, 260, 20);
		progressBar.setStringPainted(true);
		progressBar.setValue(rage);
		progressBar.setStringPainted(false);
		down.add(progressBar);

		userPic.addActionListener(event -> {
			JPanel t = tempPanel(name, path, rage);
			createProgressDetailPopup(i, t);

		});

		return make;
	}

	/**
	 * 미션 진행률을 표시하는 패널을 생성하고 화면에 표시합니다.
	 */
	public void showProgressRate() {
		RoundedPanel2 missionProgressPanel = new RoundedPanel2(60);
		missionProgressPanel.setLayout(null);
		missionProgressPanel.setForeground(new Color(255, 255, 255));
		missionProgressPanel.setBackground(new Color(255, 255, 255));
		missionProgressPanel.setBounds(510, 20, 390, 490);
		ff.add(missionProgressPanel);

		JLabel progressL = new JLabel("진행도 보기");
		progressL.setFont(customFont.deriveFont(Font.BOLD, 28));
		missionProgressPanel.add(progressL);
		progressL.setBounds(125, 20, 147, 35);

		JPanel userProgressP = new JPanel();
		userProgressP.setLayout(null);
		missionProgressPanel.add(userProgressP);
		userProgressP.setBounds(0, 90, 390, 360); // 한칸에72
		userProgressP.setBackground(Color.white);

		// 각 멤버의 미션수행도 부분 만들기
		for (int i = 0; i < uids.size(); i++) {
			int uid = uids.get(i);
			JPanel j = makeUserProgress(nicknames.get(uid).toString(), pfps.get(uid).toString(), missionProgRage[i], i);
			j.setBounds(0, 72 * i, 390, 72);
			userProgressP.add(j);
		}
	}

	public int picnum = 0;

	/**
	 * 채팅 박스를 생성하고 반환합니다.
	 *
	 * @param x   폭
	 * @param num 인덱스
	 * @param cid 채팅 아이디
	 * @return 생성된 채팅 박스 패널
	 */
	public JPanel chatBox(int x, int num, int cid) {
		JPanel chatBox = new JPanel();
		chatBox.setBackground(Color.white);
		chatBox.setLayout(null);

		int userID = (int) chatUid.get(cid);
		String userPicPathChat = pfps.get(userID).toString();
		String userNicknameChat = nicknames.get(userID).toString();
		String messageChat = chatMessage.get(cid).toString();

		ImageIcon chatUserPic;
		if(userPicPathChat.equals(""))
			chatUserPic = new ImageIcon(getClass().getClassLoader().getResource("MainPage/defaultPic.png"));
		else
			chatUserPic = new ImageIcon(userPicPathChat);
		JLabel chatUserPicL = new JLabel(chatUserPic);
		chatUserPicL.setBounds(10, 10, 60, 60);
		chatBox.add(chatUserPicL);
		JLabel chatUserName = new JLabel(userNicknameChat);
		chatUserName.setBounds(80, 10, 300, 20);
		chatUserName.setFont(customFont.deriveFont(Font.BOLD, 16));
		chatBox.add(chatUserName);

		int isPic = (int) chatIsPic.get(cid);
		if ((isPic == 1) && (x == 900)) {
			chatBox.setBounds(0, 95 * num + 305 * picnum, x, 400);
		} else if (x == 900) {
			chatBox.setBounds(0, 95 * num + 305 * picnum, x, 95);
		} else {
			chatBox.setBounds(0, 95 * num, x, 95);
		}
		// 채팅일때
		if (isPic == 0) {
			JLabel chattingL = new JLabel(messageChat);
			chattingL.setBounds(90, 21, x - 100, 60);
			chattingL.setFont(customFont.deriveFont(Font.PLAIN, 18));
			chatBox.add(chattingL);
			// 인증일때
		} else if (isPic == 1) {
			JLabel chattingL = new JLabel(userNicknameChat + "님이 오늘의 미션을 인증하였습니다.");
			chattingL.setBounds(90, 21, x - 100, 60);
			chattingL.setFont(customFont.deriveFont(Font.BOLD, 14));
			chatBox.add(chattingL);
			// 큰 사이즈일 때
			if (x == 900) {
				ImageIcon authPic900 = new ImageIcon(messageChat);
				JLabel authPicShow900 = new JLabel(authPic900);
				authPicShow900.setBounds(100, 100, 700, 250);
				chatBox.add(authPicShow900);
				picnum++;
			}
		}
		return chatBox;
	}

	/**
	 * 채팅 화면을 구성하는 메서드입니다.
	 */
	public void chatting() {
		// 채팅 JPanel
		RoundedPanel2 chatPan = new RoundedPanel2(32);
		chatPan.setLayout(null);
		chatPan.setBounds(20, 100, 455, 410);
		ff.add(chatPan);
		chatPan.setForeground(Color.white);

		// 자세히보기 버튼
		JButton showMore = new JButton("자세히 보기 >");
		showMore.setFont(customFont.deriveFont(Font.PLAIN, 15));
		showMore.setContentAreaFilled(false);
		showMore.setBorderPainted(false);
		showMore.setBounds(325, 5, 130, 35);
		chatPan.add(showMore);

		showMore.addActionListener(event -> {
			JFrame showMoreF = new JFrame();
			showMoreF.getContentPane().setBackground(Color.white);
			showMoreF.setVisible(true);
			showMoreF.setSize(900, 700);
			showMoreF.setLocation(200, 100);

			showMoreF.setLayout(null);
			// 메세지를 보내는 부분
			RoundedPanel2 sendMessage = new RoundedPanel2(15);
			sendMessage.setLayout(null);
			sendMessage.setBounds(20, 600, 840, 50); //
			sendMessage.setForeground(new Color(239, 239, 239));
			showMoreF.add(sendMessage);

			ImageIcon sendIcon = new ImageIcon(getClass().getClassLoader().getResource("DetailMyGroup/sendButton.png"));
			JButton sendButton900 = new JButton(sendIcon);

			sendMessage.add(sendButton900);
			sendButton900.setBounds(800, 8, 33, 33);
			sendButton900.setContentAreaFilled(false);
			sendButton900.setBorderPainted(false);
			JTextField inputText = new JTextField() {
				@Override
				public void setBorder(Border border) {
				}
			};
			inputText.setBounds(10, 0, 780, 50);
			inputText.setFont(customFont.deriveFont(Font.PLAIN, 15));
			inputText.setOpaque(false);
			sendMessage.add(inputText);
			sendButton900.addActionListener(event900 -> {
				JSONObject j = new JSONObject();
				String message = inputText.getText();
				j.put("message", message);
				j.put("isPic", 0);
				j.put("gid", gid);
				Request r = new Request(RequestType.CHAT, j);
				ClientSocket.send(r);
			});

			JPanel chatMoreBox = new JPanel();
			chatMoreBox.setLayout(null);
			// 채팅스레드
			Thread chatPopupThread = new Thread(() -> {
				while (true) {
					picnum = 0;
					JPanel chatMoreBoxBox = new JPanel();
					chatMoreBoxBox.setBackground(Color.white);
					chatMoreBoxBox.setLayout(null);

					int idx = chatids.size();
					int num = 0;
					for (idx = idx - 1; idx >= 0; idx--) {
						int cid = chatids.get(idx);
						chatMoreBoxBox.add(chatBox(900, num, cid));
						num++;
					}

					chatMoreBoxBox.setBounds(0, 0, 900, 95 * num + 305 * picnum);
					chatMoreBox.setPreferredSize(new Dimension(900, 95 * num + 305 * picnum));

					chatMoreBox.add(chatMoreBoxBox);
					chatMoreBox.repaint();
					chatMoreBox.revalidate();

					try {
						Thread.sleep(100);
					} catch (Exception e) {
						e.printStackTrace();
					}

					chatMoreBox.remove(chatMoreBoxBox);
					chatData = new MakeChatData(gid);
					chatUid = chatData.uid;
					chatMessage = chatData.message;
					chatIsPic = chatData.isPic;
					chatids = chatData.chatids;

				}
			});
			chatPopupThread.start();

			JScrollPane p = new JScrollPane(chatMoreBox, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
					JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			p.setViewportView(chatMoreBox);
			JPanel attachP = new JPanel();
			attachP.setBounds(0, 0, 900, 580);
			attachP.setLayout(null);
			p.setBounds(0, 0, 900, 580);

			showMoreF.add(p, BorderLayout.CENTER);
			JScrollBar verticalBar = p.getVerticalScrollBar();
			verticalBar.setPreferredSize(new Dimension(15, 0));
			verticalBar.setUnitIncrement(16);

			verticalBar.setUI(new BasicScrollBarUI() {
				@Override
				protected void configureScrollBarColors() {
					this.thumbColor = new Color(180, 180, 180);
					this.trackColor = new Color(246, 246, 246);
				}

				@Override
				protected JButton createDecreaseButton(int orientation) {
					return createZeroButton();
				}

				@Override
				protected JButton createIncreaseButton(int orientation) {
					return createZeroButton();
				}

				private JButton createZeroButton() {
					JButton button = new JButton();
					button.setPreferredSize(new Dimension(0, 0));
					button.setMinimumSize(new Dimension(0, 0));
					button.setMaximumSize(new Dimension(0, 0));
					return button;
				}
			});
			JLabel add = new JLabel();
			add.setSize(1, 900);
		});

		// 채팅부분 JPanel
		JPanel chatPart = new JPanel();
		chatPart.setBackground(Color.white);
		chatPart.setLayout(null);
		chatPart.setBounds(20, 45, 415, 285);
		chatPan.add(chatPart);

		// 메세지를 보내는 부분
		RoundedPanel2 sendMessage = new RoundedPanel2(15);
		sendMessage.setLayout(null);
		sendMessage.setBounds(20, 340, 415, 50);
		sendMessage.setForeground(new Color(239, 239, 239));
		chatPan.add(sendMessage);

		ImageIcon sendIcon = new ImageIcon(getClass().getClassLoader().getResource("DetailMyGroup/sendButton.png"));
		JButton sendButton = new JButton(sendIcon);

		sendMessage.add(sendButton);
		sendButton.setBounds(370, 8, 33, 33);
		sendButton.setContentAreaFilled(false);
		sendButton.setBorderPainted(false);
		JTextField inputText = new JTextField() {
			@Override
			public void setBorder(Border border) {
			}
		};
		inputText.setBounds(10, 0, 360, 50);
		inputText.setFont(customFont.deriveFont(Font.PLAIN, 15));
		inputText.setOpaque(false);
		sendMessage.add(inputText);
		sendButton.addActionListener(event -> {
			JSONObject j = new JSONObject();
			String message = inputText.getText();
			j.put("message", message);
			j.put("isPic", 0);
			j.put("gid", gid);
			Request r = new Request(RequestType.CHAT, j);
			ClientSocket.send(r);
		});
		// 채팅스레드
		Thread chatThread = new Thread(() -> {
			while (true) {

				JPanel chatPartPart = new JPanel();
				chatPartPart.setBackground(Color.white);
				chatPartPart.setLayout(null);
				chatPartPart.setBounds(0, 0, 415, 285);

				int a = 0;
				for (int k = 2; k >= 0; k--) {
					try {
						int cid = chatids.get(k);
						chatPartPart.add(chatBox(415, a, cid));
						a++;
					} catch (Exception e) {
					}
				}

				chatPart.add(chatPartPart);
				chatPart.repaint();
				chatPart.revalidate();

				try {
					Thread.sleep(100);
				} catch (Exception e) {
					e.printStackTrace();
				}

				chatData = new MakeChatData(gid);
				chatUid = chatData.uid;
				chatMessage = chatData.message;
				chatIsPic = chatData.isPic;
				chatids = chatData.chatids;

				chatPart.remove(chatPartPart);
			}
		});
		chatThread.start();
	}

	/**
	 * DetailMyGroup 클래스의 생성자입니다.
	 *
	 * @param g   그룹 정보를 담고 있는 Group 객체
	 * @param vis 창의 가시성 여부
	 */
	public DetailMyGroup(Group g, boolean vis) {
		// 그룹 정보를 포함하는 배너를 만들기 위한 정보
		groupData = g;
		gid = groupData.getGid();
		String missionName = groupData.getTitle();
		String missionInfo = groupData.getMission();
		detailMyGroupP = new JPanel();
		detailMyGroupP.setBounds(0, 0, 943, 850);
		detailMyGroupP.setLayout(null);

		userData = new MakeUserData(gid);
		nicknames = userData.nicknames;
		pfps = userData.pfps;
		uids = userData.uids;

		chatData = new MakeChatData(gid);
		chatUid = chatData.uid;
		chatMessage = chatData.message;
		chatIsPic = chatData.isPic;
		chatids = chatData.chatids;

		// 임시날짜
		startYear = groupData.getStartDateYear();
		startMonth = groupData.getStartDateMonth();
		startDay = groupData.getStartDateDay();
		endYear = groupData.getEndDateYear();
		endMonth = groupData.getEndDateMonth();
		endDay = groupData.getEndDateDay();
		int sYear = Integer.parseInt(startYear);
		int sMonth = Integer.parseInt(startMonth) - 1;
		int sDay = Integer.parseInt(startDay);
		int eYear = Integer.parseInt(endYear);
		int eMonth = Integer.parseInt(endMonth) - 1;
		int eDay = Integer.parseInt(endDay);

		missionStart.set(Calendar.YEAR, sYear);
		missionStart.set(Calendar.MONTH, sMonth);
		missionStart.set(Calendar.DAY_OF_MONTH, sDay);
		missionEnd.set(Calendar.YEAR, eYear);
		missionEnd.set(Calendar.MONTH, eMonth);
		missionEnd.set(Calendar.DAY_OF_MONTH, eDay);

		dayCount = calculateDayCount(missionStart, missionEnd);
		todayCount = calculateDayCount(missionStart, today);

		proData = new MakeProgressData(this, missionStart);
		progresses = proData.authDateData;

		int usernumber = uids.size();
		detailProgress = new int[usernumber][dayCount];
		missionProgRage = new int[usernumber];
		for (int j = 0; j < usernumber; j++) {
			for (int i = 0; i < todayCount - 1; i++) {
				detailProgress[j][i] = 2;
			}
		}

		for (int i = 0; i < progresses.size(); i++) {
			Vector<Integer> tempVector = progresses.get(i);
			int uid = tempVector.get(0);
			int uidIdx = 5; //
			for (int j = 0; j < uids.size(); j++) {
				if (uids.get(j) == uid) {
					uidIdx = j;
				}
			}
			if (uidIdx == 5) {
			} else {
				int authIdx = tempVector.get(1);
				detailProgress[uidIdx][authIdx] = 1;
			}
		}
		for (int i = 0; i < detailProgress.length; i++) {
			int count = 0;
			for (int j = 0; j < detailProgress[0].length; j++) {
				if (detailProgress[i][j] == 1)
					count++;
			}
			missionProgRage[i] = (int) (count * 100 / detailProgress[0].length);
		}

		showProgressRate();

		detailMyGroupP.setLayout(null);
		JPanel banner = new JPanel();
		banner.setLayout(null);
		banner.setBackground(new Color(220, 243, 255));
		banner.setBounds(0, 0, 943, 202);

		JPanel missionNameP = new JPanel();
		missionNameP.setBackground(new Color(220, 243, 255));
		missionNameP.setLayout(new BorderLayout());
		JLabel missionNameL = new JLabel(missionName);
		missionNameL.setFont(customFont.deriveFont(Font.BOLD, 35));
		missionNameP.add(missionNameL);
		missionNameP.setBounds(30, 30, 870, 60);
		banner.add(missionNameP);

		JPanel missionInfoP = new JPanel();
		missionInfoP.setBackground(new Color(220, 243, 255));
		String missionInfoT = "<html>" + "활동기간: " + missionStart.get(Calendar.YEAR) + ". "
				+ (missionStart.get(Calendar.MONTH) + 1) + ". " + missionStart.get(Calendar.DAY_OF_MONTH) + " ~ "
				+ missionEnd.get(Calendar.YEAR) + ". " + (missionEnd.get(Calendar.MONTH) + 1) + ". "
				+ missionEnd.get(Calendar.DAY_OF_MONTH) + " <br>" + "활동내용: " + missionInfo + "</html>";
		JLabel missionInfoL = new JLabel(missionInfoT);
		missionInfoP.setLayout(new BorderLayout());
		missionInfoL.setFont(customFont.deriveFont(Font.BOLD, 20));
		missionInfoP.add(missionInfoL);
		missionInfoP.setBounds(30, 100, 870, 70);
		banner.add(missionInfoP);

		detailMyGroupP.add(banner);

		ff.setLayout(null);
		ff.setBackground(new Color(246, 246, 246));
		ff.setBounds(0, 202, 943, 850 - 202);
		detailMyGroupP.add(ff);

		JPanel authMissionP = new JPanel();
		ImageIcon authMissionIcon = new ImageIcon(
				getClass().getClassLoader().getResource("DetailMyGroup/Frame 12.png"));
		JButton authMission = new JButton(authMissionIcon);
		authMission.setBackground(new Color(246, 246, 246));
		authMission.setBorderPainted(false);
		authMissionP.setBounds(20, 10, 455, 75);
		authMissionP.setBackground(new Color(246, 246, 246));
		authMissionP.add(authMission);
		ff.add(authMissionP);

		authMission.addActionListener(event -> {
			missionPic();
		});

		chatting();
	}

	/**
	 * 미션 수행 사진을 업로드하는 메서드입니다.
	 */
	public void missionPic() {
		JFileChooser fileChooser = new JFileChooser();

		// Create a filter to show only image files (jpg and png)
		FileNameExtensionFilter imageFilter = new FileNameExtensionFilter("Image files", "jpg", "png");
		fileChooser.setFileFilter(imageFilter);

		int result = fileChooser.showOpenDialog(null);

		// 파일을 선택하지 않은 경우 종료합니다.
		if (result != JFileChooser.APPROVE_OPTION) {
			return;
		}

		// 선택한 파일을 가져옵니다.
		File selectedFile = fileChooser.getSelectedFile();

		// 선택한 파일이 사진 파일인지 확인합니다.
		if (!selectedFile.getName().toLowerCase().endsWith(".jpg")
				&& !selectedFile.getName().toLowerCase().endsWith(".png")) {
			JOptionPane.showMessageDialog(null, "사진 파일을 선택하세요.");
			return;
		}

		JSONObject j = new JSONObject();
		String filePath = selectedFile.getAbsolutePath();
		j.put("filePath", filePath);
		j.put("gid", gid);
		Request r = new Request(RequestType.CERTIFYMISSION, j);
		ClientSocket.send(r);

	}

}