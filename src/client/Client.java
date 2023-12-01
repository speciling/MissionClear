package client;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import client.db.ClientDBManager;
import client.detailMyGroup.DetailMyGroup;
import client.MainPage.MainPage;
import client.login.Login;
import client.mypage.Mypage;
import client.net.ClientSocket;
import client.recruitpage.MyGroupList;
import client.recruitpage.RecruitGroupMember;

public class Client {
	  public static void main(String [] args) {
          ClientSocket socket = new ClientSocket("172.31.99.182",8080);
          socket.start();
          ClientDBManager.init();
          Login login = new Login();
          login.loginpage();
          
          String fontPath = "/NanumGothic.ttf";
    	  
    	  try (InputStream fontStream = Client.class.getResourceAsStream(fontPath)) {
              // InputStream으로부터 Font 객체 생성
              Font customFont = Font.createFont(Font.TRUETYPE_FONT, fontStream);
    	  } catch (IOException | FontFormatException e) {
              e.printStackTrace();
          }
	   }
}
