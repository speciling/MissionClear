package DetailMyGroup;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Calendar;

import javax.swing.*;

import client.MainPage.MainPage;

public class DetailMyGroup extends JFrame {
   String missionName = "돈 아껴서 부자되자!";
   String missionInfo = "매일 매일 사용한 돈 인증";
   public JPanel detailMyGroupP;
   //public JPanel g = new JPanel();
   
   //JPanel 객체 반환
   public JPanel get() {
      return detailMyGroupP;
   }
   //, String id) {
      // id를 통해서 사용자 이름, 프로필 사진을 가져오기 
      // 세부 방정보도 가져오기
   

   public DetailMyGroup(boolean vis) {
      detailMyGroupP = new JPanel();
      detailMyGroupP.setBounds(0, 0, 943, 850);
      detailMyGroupP.setLayout(null);
      // TODO Auto-generated method stub

      //임시날짜
      Calendar missionStart = Calendar.getInstance();
      missionStart.set(Calendar.YEAR, 2023);
      missionStart.set(Calendar.MONTH, Calendar.NOVEMBER);
      missionStart.set(Calendar.DAY_OF_MONTH, 12);
      Calendar missionEnd = Calendar.getInstance();
      missionEnd.set(Calendar.YEAR, 2023);
      missionEnd.set(Calendar.MONTH, Calendar.NOVEMBER);
      missionEnd.set(Calendar.DAY_OF_MONTH, 19);
      
      
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
      
      JPanel ff = new JPanel();
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
      
      /*
      authMission.addActionListener(event -> {
         JFileChooser jfc = new JFileChooser();
           int returnVal = jfc.showSaveDialog(null);
           if(returnVal == 0) {
               File file = jfc.getSelectedFile();
               try {
                   String tmp, str = null;
                   BufferedReader br = new BufferedReader(new FileReader(file));
                   while((tmp = br.readLine()) != null)
                   {
                       str += tmp;
                   }
                   System.out.println(str);
               }catch(Exception e) {
                   e.printStackTrace();
               }
                
           }
           else
           {
               System.out.println("파일 열기를 취소하였습니다.");
           }
       
      });
      System.out.println(savePath);
      */
      
      
      //g = mp.makePan(g);
      //add.add(g);
      
      
      //setVisible(false);

      
   }
   
   
   
   
   
}
