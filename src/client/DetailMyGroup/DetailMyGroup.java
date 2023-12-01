package client.DetailMyGroup;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import java.io.File;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import javax.swing.*;

import client.MainPage.MainPage;

class RoundedPanel2 extends JPanel {
    private int radius;

    public RoundedPanel2(int radius) {
        this.radius = radius;
        setOpaque(false); // 패널 배경을 투명하게 설정
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // 모서리가 둥근 사각형을 그림
        g2d.fill(new RoundRectangle2D.Double(0, 0, getWidth()-1, getHeight()-1, radius, radius));
    }
}

public class DetailMyGroup extends JFrame {
   String missionName = "돈 아껴서 부자되자!";
   String missionInfo = "매일 매일 사용한 돈 인증";
   String authPicPath;

   Calendar missionStart = Calendar.getInstance();
   Calendar missionEnd = Calendar.getInstance();
   public int calculateDayCount() {
      missionStart.set(Calendar.HOUR_OF_DAY, 0);
       missionStart.set(Calendar.MINUTE, 0);
       missionStart.set(Calendar.SECOND, 0);
       missionStart.set(Calendar.MILLISECOND, 0);

       missionEnd.set(Calendar.HOUR_OF_DAY, 0);
       missionEnd.set(Calendar.MINUTE, 0);
       missionEnd.set(Calendar.SECOND, 0);
       missionEnd.set(Calendar.MILLISECOND, 0);

       long diffInMillis = missionEnd.getTimeInMillis() - missionStart.getTimeInMillis();
       long diffInDays = TimeUnit.MILLISECONDS.toDays(diffInMillis);
       return ((int) diffInDays)+1;
   }
   int dayCount;
   
   // 먼저 detailmygroup정보에서 userID들을 가져와서 각각의 유저의 진행도와 사진과 이름을 가져오기 
   // int[] groupUserID = {};

   int [][] detailProgress = {{2,2,1,1,0,0,0,0},
         {1,2,1,0,0,0,0,0},
         {1,1,1,1,0,0,0,0},
         {2,2,2,0,0,0,0,0},
         {2,2,2,1,0,0,0,0}};
   int []missionProgRage = {0,0,0,0,0};
   
   String[] username = {"호랑이양말", "임지환", "조연우", "지연우", "최지원"};
   String[] picPath = {"./resource/DetailMyGroup/userPic1.png", "./resource/DetailMyGroup/userPic2.png", "./resource/DetailMyGroup/userPic3.png", "./resource/DetailMyGroup/userPic4.png", "./resource/DetailMyGroup/userPic5.png"};
   // 세부 미션 수행도 만들기
   //int[username.length][dayCount] detailProgress = {};
   // 0 은 기본값 1은 함 2는 하지 않음
   
   JPanel ff = new JPanel();
   public JPanel detailMyGroupP;
   //public JPanel g = new JPanel();
   
   //JPanel 객체 반환
   public JPanel get() {
      return detailMyGroupP;
   }
   //, String id) {
      // id를 통해서 사용자 이름, 프로필 사진을 가져오기 
      // 세부 방정보도 가져오기
   
   public void createProgressDetailPopup(int i, JPanel pan) {
      JFrame popupF = new JFrame();
      popupF.setSize(434,307);
      popupF.setVisible(true);
      
      JPanel popup = new JPanel();
      popup.setBackground(Color.white);
      popupF.add(popup);
      popup.setLayout(null);
      JLabel title = new JLabel("진행상황");
      title.setHorizontalAlignment(JLabel.CENTER);
       title.setVerticalAlignment(JLabel.CENTER);
      title.setFont(new Font("나눔고딕",Font.BOLD, 30));
      title.setBounds(0,0,434,70);
      popup.add(title);
      pan.setBounds(0,70,434,72);
      popup.add(pan);
       
       // 세부진행도 패널 만들기
       JPanel detailProgressP = new JPanel();
       detailProgressP.setBackground(Color.white);
       detailProgressP.setLayout(new GridLayout(3,10,0,0));
       detailProgressP.setBounds(10,142,415,124);
       int count = 0;
       for (int j=0;j<detailProgress[i].length;j++) {
          count++;
          String path=null;
          if(detailProgress[i][j] ==0){path ="./resource/RecruitGroupMember/Default.png"; }
          else if(detailProgress[i][j]==1) {path ="./resource/RecruitGroupMember/clear.png";}
          else if(detailProgress[i][j]==2) {path ="./resource/RecruitGroupMember/fail.png";}
          ImageIcon icon = new ImageIcon(path);
          JLabel detail = new JLabel(icon);
          detail.setSize(50,50);
          detailProgressP.add(detail);
       }
       while(count!=30) { // limit = 30
          detailProgressP.add(new JLabel());
          count++;
       }
       popup.add(detailProgressP);
      
      
      
   }
   
   public JPanel makeUserProgress(String name, String path, int rage, int i) {
      JPanel make = new JPanel();
      make.setBackground(Color.white);
      make.setLayout(null);
      //make.setBounds(0,y,390,72);
      
      ImageIcon userPicIcon = new ImageIcon(path);
      JButton userPic = new JButton(userPicIcon);
      userPic.setContentAreaFilled(false);
      userPic.setBorderPainted(false);
      userPic.setBounds(27,6,60,60);
      make.add(userPic);
      
      JPanel up = new JPanel();
      up.setBackground(Color.white);
      make.add(up);
      up.setBounds(100,0,260,36);
      up.setLayout(new BorderLayout());
      JLabel userName = new JLabel(name);
       userName.setFont(new Font("나눔고딕",Font.BOLD, 20));
       up.add(userName, BorderLayout.WEST);
       JLabel userRage = new JLabel(rage+"%");
       userRage.setFont(new Font("나눔고딕",Font.PLAIN, 15));
       up.add(userRage, BorderLayout.EAST);
       
       JPanel down = new JPanel();
       down.setBackground(Color.white);
       down.setLayout(null);
       make.add(down);
       down.setBounds(100,36,260,36);
       JProgressBar progressBar = new JProgressBar();
       progressBar.setBounds(0,0,260,20);
       progressBar.setStringPainted(true); 
       progressBar.setValue(rage);
       progressBar.setStringPainted(false);
       down.add(progressBar);
       
       userPic.addActionListener(event -> {
           createProgressDetailPopup(i, make);
       });
       
      return make;
   }
   public void showProgressRate() {
      RoundedPanel2 missionProgressPanel = new RoundedPanel2(60);
      missionProgressPanel.setLayout(null);
      missionProgressPanel.setForeground(new Color(255, 255, 255));
      missionProgressPanel.setBackground(new Color(255, 255, 255));
       missionProgressPanel.setBounds(510, 20, 390, 490);
      ff.add(missionProgressPanel);
       
       JLabel progressL = new JLabel("진행도 보기");
       progressL.setFont(new Font("나눔고딕",Font.BOLD, 28));
       missionProgressPanel.add(progressL);
       progressL.setBounds(125,20,147,35);
       
       JPanel userProgressP = new JPanel();
       userProgressP.setLayout(null);
       missionProgressPanel.add(userProgressP);
       userProgressP.setBounds(0, 90, 390, 360);  //한칸에72
       userProgressP.setBackground(Color.white);
       
       //각 멤버의 미션수행도 부분 만들기
       for(int i=0;i<missionProgRage.length;i++) {
          JPanel j = makeUserProgress(username[i], picPath[i], missionProgRage[i],i);
          j.setBounds(0,72*i,390,72);
          userProgressP.add(j);
       }
        /*
       JButton detailProg = new JButton();
       detailProg.setBounds(0,0,390,490);
       detailProg.setBorderPainted(false);
       detailProg.setFocusPainted(false);
       detailProg.setOpaque(false);
       missionProgressPanel.add(detailProg);
       
      
        missionProgressPanel.add(progressBar);
        missionProgressPanel.revalidate();
        missionProgressPanel.repaint();
*/
   }
   
   public DetailMyGroup(boolean vis) {
      detailMyGroupP = new JPanel();
      detailMyGroupP.setBounds(0, 0, 943, 850);
      detailMyGroupP.setLayout(null);
      // TODO Auto-generated method stub
      
      
      for (int i=0;i<detailProgress.length;i++) {
         int count =0;
         for (int j =0;j<detailProgress[0].length;j++) {
            if(detailProgress[i][j]==1) count++;
         //System.out.println(count);
         }
         missionProgRage[i] = (int)(count*100/detailProgress[0].length);
      }
      
      
      showProgressRate();
      

      //임시날짜
      missionStart.set(Calendar.YEAR, 2023);
      missionStart.set(Calendar.MONTH, Calendar.NOVEMBER);
      missionStart.set(Calendar.DAY_OF_MONTH, 12);
      missionEnd.set(Calendar.YEAR, 2023);
      missionEnd.set(Calendar.MONTH, Calendar.NOVEMBER);
      missionEnd.set(Calendar.DAY_OF_MONTH, 19);
      
      //dayCount만들기
      dayCount = calculateDayCount();
      
      //배너
      detailMyGroupP.setLayout(null);
      JPanel banner = new JPanel();
      banner.setLayout(null);
      banner.setBackground(new Color(220,243,255));
      banner.setBounds(0,0,943,202);
      
      JPanel missionNameP = new JPanel();
      missionNameP.setBackground(new Color(220,243,255));
      missionNameP.setLayout(new BorderLayout());
      JLabel missionNameL = new JLabel(missionName);
      missionNameL.setFont(new Font("나눔고딕",Font.BOLD, 35));   
      missionNameP.add(missionNameL);
      missionNameP.setBounds(30,30,870,60);
      banner.add(missionNameP);
      
      JPanel missionInfoP = new JPanel();
      missionInfoP.setBackground(new Color(220,243,255));
      String missionInfoT = "<html>"+"활동기간: "+missionStart.get(Calendar.YEAR)+". "+(missionStart.get(Calendar.MONTH)+1)+". "+missionStart.get(Calendar.DAY_OF_MONTH)
            + " ~ " + missionEnd.get(Calendar.YEAR)+". "+(missionEnd.get(Calendar.MONTH)+1)+". "+missionEnd.get(Calendar.DAY_OF_MONTH) + " <br>"
            + "활동내용: "+missionInfo+"</html>";
      JLabel missionInfoL = new JLabel(missionInfoT);
      missionInfoP.setLayout(new BorderLayout());
      missionInfoL.setFont(new Font("나눔고딕", Font.BOLD, 20));
      missionInfoP.add(missionInfoL);
      missionInfoP.setBounds(30,100,870,70);
      banner.add(missionInfoP);
      
      
      detailMyGroupP.add(banner);
      
      ff.setLayout(null);
      ff.setBackground(new Color(246,246,246));
      ff.setBounds(0,202,943,850-202);
      detailMyGroupP.add(ff);
      
      
      JPanel authMissionP = new JPanel();
      ImageIcon authMissionIcon = new ImageIcon("./resource/DetailMyGroup/Frame 12.png");
      JButton authMission = new JButton(authMissionIcon);
      authMission.setBackground(new Color(246,246,246));
      authMission.setBorderPainted(false);
      authMissionP.setBounds(20,10,455,75);
      authMissionP.setBackground(new Color(246,246,246));
      authMissionP.add(authMission);
      ff.add(authMissionP);
      
      
      authMission.addActionListener(event -> {
         JFileChooser jfc = new JFileChooser();
           int returnVal = jfc.showSaveDialog(null);
           if(returnVal == 0) {
               File file = jfc.getSelectedFile();
               try {
                   authPicPath = file.getPath();
                   //System.out.println(authPicPath);
                   }
               catch(Exception e) {
                   e.printStackTrace();
                  }
               }
           else
           {
               System.out.println("파일 열기를 취소하였습니다.");
           }
       
      });

      //System.out.println(savePath);
      
      
      
      //g = mp.makePan(g);
      //add.add(g);
      
      
      //setVisible(false);
      
      
   }
   
   
   
   
   
}