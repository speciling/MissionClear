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

public class RecruitGroupMember {
	private static RecruitGroupMember instance;
	JFrame frame;
	private JTextField textField;
	ImagePanel groupRecruitment;

	/**
	 * Launch the application.
	 */
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

	/**
	 * Create the application.
	 */
	private RecruitGroupMember() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public static synchronized RecruitGroupMember getInstance() {
        if (instance == null) {
            instance = new RecruitGroupMember();
        }
        return instance;
    }
	
	private void initialize() {
		if (frame == null) { // 프레임이 아직 생성되지 않았다면 새로 생성
            frame = new JFrame();
            frame.setSize(1200, 850);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}
		groupRecruitment = new ImagePanel(new ImageIcon("./resource/RecruitGroupMember/RecruitPageMain.jpg").getImage());
		groupRecruitment.setBackground(new Color(169, 169, 169));
		frame.setSize(1200, 850);
		frame.getContentPane().add(groupRecruitment, BorderLayout.EAST);
		groupRecruitment.setLayout(null);
		
		JPanel 윗부분 = new JPanel();
		윗부분.setBackground(new Color(255, 250, 250));
		윗부분.setBounds(0, 0, 942, 69);
		groupRecruitment.add(윗부분);
		윗부분.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("호랑이양말 님");
		lblNewLabel.setFont(new Font("나눔고딕", Font.BOLD, 18));
		lblNewLabel.setBounds(732, 18, 119, 35);
		윗부분.add(lblNewLabel);
		
		JLabel lblNewLabel_2 = new JLabel("New label");
		lblNewLabel_2.setIcon(new ImageIcon("./resource/RecruitGroupMember/호랑이.png"));
		lblNewLabel_2.setBounds(864, 8, 58, 58);
		윗부분.add(lblNewLabel_2);
		
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
		groupRecruitment.add(textField);

        textField.setOpaque(false); // 배경을 투명하게 설정
        textField.setText("          원하는 미션방을 검색할 수 있어요!");
        textField.setForeground(SystemColor.controlShadow);
        textField.setFont(new Font("나눔고딕", Font.BOLD, 18));
        textField.setBorder(null);
        textField.setBounds(39, 81, 866, 59);
        textField.setColumns(10);
        
        JButton missionRoomCreate = new JButton("");
        missionRoomCreate.setBackground(new Color(255, 255, 255));
        missionRoomCreate.setIcon(new ImageIcon("./resource/RecruitGroupMember/미션방생성.png"));
        missionRoomCreate.setToolTipText("");
        missionRoomCreate.setBounds(707, 155, 189, 35);
        missionRoomCreate.setBorderPainted(false);
        missionRoomCreate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	CreateNewGroupPopup cp = new CreateNewGroupPopup(RecruitGroupMember.this); // 수정됨
                cp.getFrame().setVisible(true);
            }
        });
        groupRecruitment.add(missionRoomCreate);
        
        JLabel lblNewLabel_1 = new JLabel("카테고리 선택");
        lblNewLabel_1.setFont(new Font("\uB098\uB214\uACE0\uB515", lblNewLabel_1.getFont().getStyle() | Font.BOLD, lblNewLabel_1.getFont().getSize() + 6));
        lblNewLabel_1.setBounds(50, 155, 130, 35);
        groupRecruitment.add(lblNewLabel_1);
        JComboBox comboBox = new JComboBox();
        comboBox.setModel(new DefaultComboBoxModel(new String[] {"선택하기", "다이어트", "챌린지", "스터디", "기타"}));
        comboBox.setBounds(180, 155, 121, 35);
        groupRecruitment.add(comboBox);
        frame.setVisible(true);
	}
	public void addToGroupRecruitment(JPanel panel) {
		panel.setBounds(50, 210, 406, 273);
        groupRecruitment.add(panel);
        groupRecruitment.revalidate();
        groupRecruitment.repaint();	
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

