package client.MainPage;

import javax.swing.*;
import java.awt.*;

import DetailMyGroup.DetailMyGroup;

public class MainPage extends JFrame {

   public JPanel globPan;
   public String userName = "호랑이양말";
   public String userPic = "./resource/DetailMyGroup/Ellipse3.png";
   public Container main;
   // 사용자의 이름과 사진을 불러오는 함수를 작성
   public void callingInfo() {
      ;
   }
   
   public JPanel makePan(JPanel p) {
      p.setBounds(0,0,943,781);
      return p;
   }

   public void changePan() {
      globPan = new JPanel();
      globPan.setBackground(Color.gray);
      globPan.setBounds(257, 69, 943, 781);
      globPan.setLayout(null);
      main.add(globPan);
   }
   
   public MainPage(boolean vis) {
      initializeMainPage();
   }
   public void initializeMainPage() {
      Color mainColor = new Color(56, 183, 255);
      String logoPath = "./resource/MainPage/logo.png";

      main = getContentPane();
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //
      //setSize(1200, 850); // 프레임 크기 설정
      setSize(new Dimension(1210,880));
      setBackground(Color.gray);
      main.setLayout(new BorderLayout());
      main.setPreferredSize(new Dimension(1200,850));
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
      mainInfo.setBounds(257,0,943,69);
      main.add(mainInfo, BorderLayout.NORTH);
      
      
      
      
      
      
      changePan();
      
      JPanel menuBar = new JPanel();
      menuBar.setLayout(null);
      menuBar.setBounds(0,0,257,850);
      menuBar.setBackground(mainColor);
      main.add(menuBar);
      
   
      // 로고삽입
      JPanel logoP = new JPanel();
      logoP.setBackground(mainColor);
      JLabel logo = new JLabel();
      ImageIcon icon = new ImageIcon(logoPath);
      logo.setIcon(icon);
      logoP.add(logo);
      logoP.setBounds(13, 23, 227, 139);
      menuBar.add(logoP);

      // 페이지이동버튼3개
      JPanel bpRecruit = new JPanel();
      bpRecruit.setBounds(0,242,257,59);
      bpRecruit.setBackground(mainColor);
      ImageIcon iconRecruit = new ImageIcon("./resource/MainPage/group.png");
      JButton bRecruit = new JButton(iconRecruit);
      bRecruit.setBackground(mainColor);
      bRecruit.setBorderPainted(false); //버튼 그림자 없애기
      bpRecruit.add(bRecruit);
      
      JPanel bpMyGroup = new JPanel();
      bpMyGroup.setBounds(0,242+59,257,59);
      bpMyGroup.setBackground(mainColor);
      ImageIcon iconMyGroup = new ImageIcon("./resource/MainPage/mygroup.png");
      JButton bMyGroup = new JButton(iconMyGroup);
      bMyGroup.setBackground(mainColor);
      bMyGroup.setBorderPainted(false); //버튼 그림자 없애기
      bpMyGroup.add(bMyGroup);
      
      JPanel bpMyPage = new JPanel();
      bpMyPage.setBounds(0,242+59*2,257,59);
      bpMyPage.setBackground(mainColor);
      ImageIcon iconMyPage = new ImageIcon("./resource/MainPage/mypage.png");
      JButton bMyPage = new JButton(iconMyPage);
      bMyPage.setBackground(mainColor);
      bMyPage.setBorderPainted(false); //버튼 그림자 없애기
      bpMyPage.add(bMyPage);
      
      menuBar.add(bpRecruit);
      menuBar.add(bpMyGroup);
      menuBar.add(bpMyPage);

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

      logout_panel.setBounds(0, 751, 257, 90);
      menuBar.add(logout_panel);

      setVisible(true); // 화면에 프레임 출력

      // 화면전환 버튼 클릭 이벤트
      bRecruit.addActionListener(event -> {
         // 현재창을 false
         changePanel("group");
      });
      bMyGroup.addActionListener(event -> {
         // 현재창을 false
         changePanel("mygroup");
      });
      bMyPage.addActionListener(event -> {
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

   }
   
   
   

   public void changePanel(String panelName) {
      // 모두 페이지가 존재한다고 가정하고 패널을 불러올 때 boolean 매개변수가 존재하는 함수호출을 통해 불러오기(현재 함수의
      // setvisible은 false)
      if (panelName.equals("group")) {
         recruit r = new recruit();
         JPanel p = r.get();
         p = makePan(p);
         globPan.add(p);
      }// add함수(True);
      
      else if (panelName.equals("mygroup")) {
         
         //changePan();//panel 초기화
         /*
         DetailMyGroup dg = new DetailMyGroup();
         JPanel p = dg.get();
         p = makePan(p);
         globPan.add(p);*/
         
      }
         // add함수(True);
      else if (panelName.equals("mypage")) {
         Mypage mp = new Mypage();
         JPanel p = mp.get();
         p = makePan(p);
         globPan.add(p);
      }
   }
   //public void changePanel()
}