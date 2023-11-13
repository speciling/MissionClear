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

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSeparator;
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

public class Mypage {

    private JFrame frame;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Mypage window = new Mypage();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public Mypage() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        ImagePanel mypagepanel = new ImagePanel(new ImageIcon("C:\\Users\\binwo\\Desktop\\jiwon\\2학년2학기\\소프트웨어시스템설계\\develop\\missionclear\\resource\\mypage\\background.jpg").getImage());
        mypagepanel.setBackground(new Color(169, 169, 169));
        frame.setSize(1200, 850);
        frame.getContentPane().add(mypagepanel, BorderLayout.EAST);
        mypagepanel.setLayout(null);
        
        RoundedPanel2 missionInProgress = new RoundedPanel2(32); 
        missionInProgress.setBounds(37, 343, 420, 462);
        missionInProgress.setForeground(new Color(255, 255, 255));
        missionInProgress.setBackground(new Color(255, 255, 255));
        mypagepanel.add(missionInProgress); // Add missionInProgress to mypagepanel
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
        mypagepanel.add(missionended); // Add missionInProgress to mypagepanel
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
        lblNewLabel.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        	}
        });
        lblNewLabel.setIcon(new ImageIcon(Mypage.class.getResource("/mypage/tigerimage.png")));
        lblNewLabel.setBounds(396, 104, 149, 149);
        lblNewLabel.setContentAreaFilled(false);
        lblNewLabel.setBorderPainted(false);
        mypagepanel.add(lblNewLabel);
        
        JLabel lblNewLabel_1 = new JLabel("호랑이양말 님");
        lblNewLabel_1.setFont(new Font("나눔고딕", Font.BOLD, 20));
        lblNewLabel_1.setBounds(392, 265, 124, 35);
        mypagepanel.add(lblNewLabel_1);
        
        JButton btnNewButton = new JButton("");
        btnNewButton.setIcon(new ImageIcon(Mypage.class.getResource("/mypage/pencil.png")));
        btnNewButton.setBounds(525, 270, 25, 25);
        btnNewButton.setContentAreaFilled(false);
        btnNewButton.setBorderPainted(false);
        mypagepanel.add(btnNewButton);
        
        frame.setVisible(true);
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