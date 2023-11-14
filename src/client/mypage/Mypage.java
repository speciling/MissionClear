package client.mypage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import client.MainPage.MainPage;
import client.login.Login;

import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

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

public class Mypage extends MainPage{

	//private JFrame frame;
    
    public Mypage(boolean vis) {
    	super(vis);
    	
        MainPage mp = new MainPage(true);
        JPanel box = mp.globPan;
        box.setLayout(null);
        
        
        //JPanel mypagePanel = new JPanel();
        box.setBackground(new Color(246, 246, 246));
        box.setBounds(257,69,930,850);
        //frame.setSize(1200, 850);
        //frame.getContentPane().add(mypagePanel, BorderLayout.EAST);
        box.setLayout(null);
        
        RoundedPanel2 missionInProgress = new RoundedPanel2(32);
        missionInProgress.setBounds(37, 343, 420, 462);
        missionInProgress.setForeground(new Color(255, 255, 255));
        missionInProgress.setBackground(new Color(255, 255, 255));
        box.add(missionInProgress); // Add missionInProgress to mypagepanel
        missionInProgress.setLayout(null);
        
        JLabel lblNewLabel_2_1 = new JLabel("진행중인 미션");
        lblNewLabel_2_1.setFont(new Font("나눔고딕", Font.BOLD, 20));
        lblNewLabel_2_1.setBounds(147, 10, 137, 35);
        missionInProgress.add(lblNewLabel_2_1);
        
	    JSeparator separator = new JSeparator();
	    separator.setForeground(new Color(128, 128, 128));
	    separator.setBounds(23, 162, 374, 1); // 위치와 크기 설정
	    missionInProgress.add(separator);
       
        RoundedPanel2 missionended = new RoundedPanel2(32);
        missionended.setBounds(484, 343, 420, 462);
        missionended.setForeground(new Color(255, 255, 255));
        missionended.setBackground(new Color(255, 255, 255));
        box.add(missionended); // Add missionInProgress to mypagepanel
        missionended.setLayout(null);
        
        JLabel lblNewLabel_2 = new JLabel("종료된 미션");
        lblNewLabel_2.setFont(new Font("나눔고딕", Font.BOLD, 20));
        lblNewLabel_2.setBounds(147, 10, 137, 35);
        missionended.add(lblNewLabel_2);
        
        JSeparator separator1 = new JSeparator();
        separator1.setForeground(new Color(128, 128, 128));
        separator1.setBounds(23, 264, 374, 1); // 위치와 크기 설정
        missionended.add(separator1);
       
        JSeparator separator2 = new JSeparator();
        separator2.setForeground(new Color(128, 128, 128));
        separator2.setBounds(23, 162, 374, 1); // 위치와 크기 설정
        missionended.add(separator2);
       
        JButton lblNewLabel = new JButton("");
        lblNewLabel.setBounds(396, 104, 149, 149);
        lblNewLabel.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        	}
        });
        lblNewLabel.setIcon(new ImageIcon(Mypage.class.getResource("/mypage/tigerimage.png")));
        lblNewLabel.setContentAreaFilled(false);
        lblNewLabel.setBorderPainted(false);
        box.add(lblNewLabel);
        
        JLabel lblNewLabel_1 = new JLabel("호랑이양말 님");
        lblNewLabel_1.setBounds(392, 265, 124, 35);
        lblNewLabel_1.setFont(new Font("나눔고딕", Font.BOLD, 20));
        box.add(lblNewLabel_1);
        
        JButton btnNewButton = new JButton("");
        btnNewButton.setBounds(525, 270, 25, 25);
        btnNewButton.setIcon(new ImageIcon(Mypage.class.getResource("/mypage/pencil.png")));
        btnNewButton.setContentAreaFilled(false);
        btnNewButton.setBorderPainted(false);
        box.add(btnNewButton);
        
       // nav=mypagePanel;
        setVisible(false);
		
        //frame.setVisible(true);
    }
    
    public void changeNickName(String newNickName) {
    	; //서버에 닉네임 변경을 요청하는 함수
    }
    
    public void changePFP(File Picture) {
    	;//서버에 프로필 사진 변경을 요청하는 함수
    }
    public void showOngoingMission() {
    	//진행중인 미션을 보여주는 함수
    }
    public void showFinishedMission() {
    	//완료된 미션을 보여주는 함수
    }
    
}

class ImagePanel extends JPanel{
	   private Image img;
	   
	   public ImagePanel(Image img) {
	      this.img = img;
	      setSize(new Dimension(img.getWidth(null), img.getHeight(null)));
	      setPreferredSize(new Dimension(img.getWidth(null), img.getHeight(null)));
	      setLayout(null);
	   }
	   
	   public int getWidth() {
	      return img.getWidth(null);
	   }
	   
	   public int getHeight() {
	      return img.getHeight(null);
	   }   
	   
	   public void paintComponent(Graphics g) {
	      g.drawImage(img, 0, 0, null);
	   }
	   
	}