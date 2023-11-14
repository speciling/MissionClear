package client.recruitpage;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.DropMode;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;

import javax.swing.border.Border;
import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;


import client.MainPage.MainPage;

class RoundedPanel extends JPanel {
    private int radius;

    public RoundedPanel(int radius) {
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

public class RecruitGroupMember extends MainPage{
	
	private static RecruitGroupMember instance;
	private JTextField textField;
	private JPanel groupRecruitment;

	JPanel a;
	
	public JPanel get() {
	    return a;
	}
	/**
	 * Launch the application.
	 */
	/*
	public void setFrame(JFrame frame) {
        this.frame = frame;
        initialize();
    }
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RecruitGroupMember window = new RecruitGroupMember();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
*/
	/**
	 * Create the application.
	 */
	/*
	private RecruitGroupMember() {
		initialize();
	}
*/
	/**
	 * Initialize the contents of the frame.
	 */
//	public static synchronized RecruitGroupMember getInstance() {
//        if (instance == null) {
//            instance = new RecruitGroupMember(boolean vis);
//        }
//        return instance;
//    }
	
	public RecruitGroupMember(boolean vis) {
		/*
		if (frame == null) { // 프레임이 아직 생성되지 않았다면 새로 생성
            frame = new JFrame();
            frame.setSize(1200, 850);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(null);
		}
		*/
		super(vis);
		initializeGroupRecruitment();
	}
	private void initializeGroupRecruitment() {
        groupRecruitment = new JPanel();
        groupRecruitment.setBackground(new Color(246, 246, 246));
        groupRecruitment.setBounds(0, 0, 930, 850);
        groupRecruitment.setLayout(null);
		
        JButton missionRoomCreate = new JButton("");
        missionRoomCreate.setBounds(707, 84, 189, 40);
        missionRoomCreate.setBackground(new Color(255, 255, 255));
        missionRoomCreate.setIcon(new ImageIcon("./resource/RecruitGroupMember/미션방생성.png"));
        missionRoomCreate.setToolTipText("");
        missionRoomCreate.setBorderPainted(false);
        missionRoomCreate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	CreateNewGroupPopup cp = new CreateNewGroupPopup(RecruitGroupMember.this); // 수정됨
                cp.getFrame().setVisible(true);
            }
        });
        
        
        // 미션 방 검색
        textField = new JTextField() {
            @Override
            protected void paintComponent(Graphics g) {
                if (!isOpaque()) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(getBackground());
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 50, 50); // 둥근 모서리 그리기
                    g2.dispose();
                }
                super.paintComponent(g);
            }
        };
        textField.setBounds(39, 12, 866, 59);
        groupRecruitment.add(textField);
        
                textField.setOpaque(false); // 배경을 투명하게 설정
                textField.setText("          원하는 미션방을 검색할 수 있어요!");
                textField.setForeground(SystemColor.controlShadow);
                textField.setFont(new Font("나눔고딕", Font.BOLD, 18));
                textField.setBorder(null);
                textField.setColumns(10);
        groupRecruitment.add(missionRoomCreate);
        
        JLabel lblNewLabel_1 = new JLabel("카테고리 선택");
        lblNewLabel_1.setBounds(50, 81, 110, 35);
        lblNewLabel_1.setFont(new Font("\uB098\uB214\uACE0\uB515", lblNewLabel_1.getFont().getStyle() | Font.BOLD, lblNewLabel_1.getFont().getSize() + 6));
        groupRecruitment.add(lblNewLabel_1);
        JComboBox comboBox = new JComboBox();
        comboBox.setBounds(180, 82, 121, 35);
        comboBox.setModel(new DefaultComboBoxModel(new String[] {"선택하기", "다이어트", "챌린지", "스터디", "기타"}));
        groupRecruitment.add(comboBox);
        /*
        frame.setVisible(true);
        frame.add(groupRecruitment); 
        frame.setVisible(true);
        */
        MainPage mp = new MainPage(true);
		JPanel a = mp.globPan;
		a.setLayout(null);
        a.add(groupRecruitment);
	}
   
	public void addToGroupRecruitment(JPanel panel) {
		if (groupRecruitment != null) {
            panel.setBounds(50, 141, 406, 273);
            groupRecruitment.add(panel);
            groupRecruitment.revalidate();
            groupRecruitment.repaint();
        }
    }
	
}
