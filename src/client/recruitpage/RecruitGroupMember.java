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
/**
 * A custom JPanel with rounded corners.
 * This class extends JPanel to create a panel with rounded corners.
 */
class RoundedPanel extends JPanel {
    private int radius;

    /**
     * Constructor to create a rounded panel with a specified radius.
     * @param radius the radius of the corners in the panel.
     */
    public RoundedPanel(int radius) {
        this.radius = radius;
        setOpaque(false); // 패널 배경을 투명하게 설정
    }

    /**
     * Paints the rounded corners on the panel.
     * @param g the Graphics object to protect.
     */
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
 * Class representing the recruitment group member interface.
 * This class extends MainPage and provides a user interface for recruitment functionalities.
 */
public class RecruitGroupMember{
	
	private JTextField textField;
	private JPanel groupRecruitment;
	//JPanel a;
	private static int panelCount = 0;
	/**
     * Retrieves the main panel of the recruitment interface.
     * @return the main JPanel component of the recruitment interface.
     */
	//public JPanel get() {
	//    return a;
	//}

	/**
     * Constructor to initialize the RecruitGroupMember interface.
     * @param vis Boolean value to set the visibility of the main page.
     */
	public RecruitGroupMember() {
		super();
		initializeGroupRecruitment();
	}
	
	/**
     * Initializes and sets up the group recruitment interface.
     */
	private void initializeGroupRecruitment() {
        groupRecruitment = new JPanel();
        groupRecruitment.setBackground(new Color(246, 246, 246));
        groupRecruitment.setBounds(0, 0, 950, 850);
        groupRecruitment.setLayout(null);
		
        JButton missionRoomCreate = new JButton("");
        missionRoomCreate.setBounds(707, 84, 189, 40);
        missionRoomCreate.setBackground(new Color(246, 246, 246));
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
        
        JButton searchButton = new JButton("");
        searchButton.setBackground(new Color(255, 255, 255));
        searchButton.setBorderPainted(false);
        searchButton.setIcon(new ImageIcon("./resource/RecruitGroupMember/search.png"));
        searchButton.setBounds(56, 26, 30, 30);
        groupRecruitment.add(searchButton);
        
        
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

        MainPage mp = new MainPage(true);
		JPanel a = mp.globPan;
		a.setLayout(null);
        a.add(groupRecruitment);
        
        JButton backButton = new JButton("");
        backButton.setBackground(new Color(246, 246, 246));
        backButton.setBorderPainted(false);
        backButton.setIcon(new ImageIcon("./resource/RecruitGroupMember/backButton.png"));
        backButton.setBounds(875, 725, 30, 40);
        groupRecruitment.add(backButton);
        
        JButton nextButton = new JButton("");
        nextButton.setBackground(new Color(246, 246, 246));
        nextButton.setBorderPainted(false);
        nextButton.setIcon(new ImageIcon("./resource/RecruitGroupMember/nextButton.png"));
        nextButton.setBounds(905, 725, 30, 40);
        groupRecruitment.add(nextButton);
	}
   
	 /**
     * Adds a panel to the group recruitment interface.
     * @param panel The panel to be added to the group recruitment interface.
     */
	public void addToGroupRecruitment(JPanel panel) {
		if (groupRecruitment != null) {
            int top, left;

            // 배열 인덱스에 따라 위치를 결정합니다.
            switch (panelCount % 4) { // % 연산자를 사용하여 4개의 패널마다 위치를 변경합니다.
                case 0:
                    top = 141;
                    left = 50;
                    break;
                case 1:
                    top = 141;
                    left = 490;
                    break;
                case 2:
                    top = 439;
                    left = 50;
                    break;
                case 3:
                    top = 439;
                    left = 490;
                    break;
                default:
                    top = 141; // 기본값 설정
                    left = 50; // 기본값 설정
                    break;
            }

            panel.setBounds(left, top, panel.getWidth(), panel.getHeight());
            groupRecruitment.add(panel);
            groupRecruitment.revalidate();
            groupRecruitment.repaint();

            panelCount++; // 패널이 추가될 때마다 카운트를 증가시킵니다.
        }
    }
	
}
