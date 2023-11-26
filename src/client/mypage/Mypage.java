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
import javax.swing.JProgressBar;
import javax.swing.JSeparator;

import client.MainPage.MainPage;
import client.login.Login;
import client.recruitpage.RecruitGroupMember;

import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

/**
 * @author 최지원
 * 
 * 사각형의 바텀부분에만 검정색 선을 더하기 위해 JPanel을 커스텀한 클래스
 *
 */
class CustomPanel extends JPanel {

	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // 현재 패널의 너비와 높이 구하기
        int width = getWidth();
        int height = getHeight();

        // 아래쪽에 1px의 검정색 선 그리기
        g.setColor(Color.BLACK);
        g.drawLine(0, height - 1, width, height - 1);
    }
}
	
/**
 * 사각형의 모서리를 둥글게 만들기 위해 JPanel 커스텀한 클래스
 * */
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

/**
 * 
 * mypage 클래스
 *
 */
public class Mypage {


	public JPanel box;
	//JPanel main;
	
	public static void main(String [] args) {
		  MainPage mp = new MainPage(true);
		  //Mypage mypage = new Mypage(true);
		 
		  //mp.globPan.add(mypage.get());
		  //창 크기 변경안해도 뜨게 하는거
		  //mp.globPan.repaint();
	   }

	public CustomPanel missionProgressPanel;
	private JLabel ongoingGroupName;
	public RoundedPanel2 missionInProgress;

    public JPanel get() {
    	return box;

    }
	
    /**
     * 화면에 보이게 하기 위한 생성자
     * @param vis
     */
    public Mypage(boolean vis) {
    	//super(vis);
    	initializeMypage();
    }
    /**
     * ui메소드
     */
    private void initializeMypage() {
		// TODO Auto-generated method stub
    	box = new JPanel();    	
        
        //JPanel mypagePanel = new JPanel();
        box.setBackground(new Color(246, 246, 246));

        box.setBounds(0,0,943,781);

        box.setLayout(null);
        
        missionInProgress = new RoundedPanel2(32);
        missionInProgress.setBounds(29, 249, 420, 462);
        missionInProgress.setForeground(new Color(255, 255, 255));
        missionInProgress.setBackground(new Color(255, 255, 255));
        box.add(missionInProgress); // Add missionInProgress to mypagepanel
        missionInProgress.setLayout(null);
        
        JLabel lblNewLabel_2_1 = new JLabel("진행중인 미션");
        lblNewLabel_2_1.setFont(new Font("나눔고딕", Font.BOLD, 20));
        lblNewLabel_2_1.setBounds(147, 10, 129, 35);
        missionInProgress.add(lblNewLabel_2_1);
	    
	    showOngoingMission();
       
        RoundedPanel2 missionended = new RoundedPanel2(32);
        missionended.setBounds(476, 249, 420, 462);
        missionended.setForeground(new Color(255, 255, 255));
        missionended.setBackground(new Color(255, 255, 255));
        box.add(missionended);
        missionended.setLayout(null);
        
        JLabel lblNewLabel_2 = new JLabel("종료된 미션");
        lblNewLabel_2.setFont(new Font("나눔고딕", Font.BOLD, 20));
        lblNewLabel_2.setBounds(158, 10, 102, 35);
        missionended.add(lblNewLabel_2);
       
        JButton lblNewLabel = new JButton("");
        lblNewLabel.setBounds(388, 10, 149, 149);
        lblNewLabel.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        	}
        });
        lblNewLabel.setIcon(new ImageIcon(Mypage.class.getResource("/mypage/tigerimage.png")));
        lblNewLabel.setContentAreaFilled(false);
        lblNewLabel.setBorderPainted(false);
        box.add(lblNewLabel);
        
        JLabel lblNewLabel_1 = new JLabel("호랑이양말 님");
        lblNewLabel_1.setBounds(384, 171, 124, 35);
        lblNewLabel_1.setFont(new Font("나눔고딕", Font.BOLD, 20));
        box.add(lblNewLabel_1);
        
        JButton btnNewButton = new JButton("");
        btnNewButton.setBounds(517, 176, 25, 25);
        btnNewButton.setIcon(new ImageIcon(Mypage.class.getResource("/mypage/pencil.png")));
        btnNewButton.setContentAreaFilled(false);
        btnNewButton.setBorderPainted(false);
        box.add(btnNewButton);
        
        
        
//        MainPage mp = new MainPage(true);
//        JPanel main = mp.globPan;
//        main.setLayout(null);
//        main.add(box); 
//        
       // nav=mypagePanel;
        //setVisible(false);
		
        //frame.setVisible(true);
	}
	
    /**서버에 닉네임 변경을 요청하는 함수*/
    public void changeNickName(String newNickName) {
    	; //서버에 닉네임 변경을 요청하는 함수
    }
    
    /**서버에 프로필 사진 변경을 요청하는 함수*/
    public void changePFP(File Picture) {
    	;//서버에 프로필 사진 변경을 요청하는 함수
    }
    
    /**진행중인 미션을 보여주는 함수*/
    public void showOngoingMission() {
    	
    	missionProgressPanel = new CustomPanel();
	    missionProgressPanel.setBackground(new Color(255, 255, 255));
	    missionProgressPanel.setBounds(23, 56, 374, 107);
	    missionInProgress.add(missionProgressPanel);
	    missionProgressPanel.setLayout(null);
	    
	    ongoingGroupName = new JLabel();
	    ongoingGroupName.setFont(new Font("나눔고딕", Font.PLAIN, 25));
	    ongoingGroupName.setText("돈 아껴서 부자되자");
	    ongoingGroupName.setBounds(12, 10, 265, 35);
	    missionProgressPanel.add(ongoingGroupName);
	    
    	int progressValue = 50;

      
        JProgressBar progressBar = new JProgressBar();
        progressBar.setStringPainted(true); 
        progressBar.setValue(progressValue); 
        progressBar.setBounds(12, 55, 304, 30);

        missionProgressPanel.add(progressBar);
        missionProgressPanel.revalidate();
        missionProgressPanel.repaint();
    }
    
    
    
    /** 완료된 미션을 보여주는 함수*/
    public void showFinishedMission() {
    	//완료된 미션을 보여주는 함수
    }
    
}

/** 이미지패널 클래스*/

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