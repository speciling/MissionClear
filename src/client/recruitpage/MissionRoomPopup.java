package client.recruitpage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JSpinner;
import javax.swing.JList;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.ImageIcon;


// 모서리가 둥근 JPanel을 생성하는 클래스
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

public class MissionRoomPopup {

    private JFrame frame;
    private JTextField 미션방제목;
    private JTextField textField;
    private JTextField textField_1;
    private JTextField textField_2;
    private JTextField textField_3;
    private JTextField textField_4;
    private JTextField textField_5;
    private JTextField textField_6;
    private JTextField textField_7;
    private JTextField textField_8;
    private JTextField textField_9;
    private JTextField textField_10;
    private JTextField textField_11;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                	MissionRoomPopup window = new MissionRoomPopup();
                    window.getFrame().setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public MissionRoomPopup() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */

    public void initialize() {
    	setFrame(new JFrame());
    	getFrame().setSize(691, 760);
    	getFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	getFrame().getContentPane().setLayout(null);
    	
    	RoundedPanel createPopup = new RoundedPanel(30); // 모서리 반경을 20으로 설정
    	createPopup.setBounds(0, 0, 675, 721);
    	createPopup.setForeground(new Color(255, 255, 255));
    	createPopup.setBackground(new Color(255, 255, 255));
    	getFrame().getContentPane().add(createPopup);
    	createPopup.setLayout(null);
    	
    	JLabel lblNewLabel = new JLabel("미션방 생성하기");
    	lblNewLabel.setFont(new Font("\uB098\uB214\uACE0\uB515", lblNewLabel.getFont().getStyle() | Font.BOLD, 27));
    	lblNewLabel.setBounds(251, 39, 190, 35);
    	createPopup.add(lblNewLabel);
    	
    	JLabel lblNewLabel_1 = new JLabel("미션방 제목");
    	lblNewLabel_1.setFont(new Font("나눔고딕", Font.PLAIN, 19));
    	lblNewLabel_1.setBounds(56, 94, 101, 35);
    	createPopup.add(lblNewLabel_1);
    	
    	미션방제목 = new JTextField();
    	미션방제목.setBackground(new Color(237, 237, 237));
    	미션방제목.setBounds(56, 129, 580, 45);
    	미션방제목.setBorder(null);
    	createPopup.add(미션방제목);
    	미션방제목.setColumns(10);
    	
    	JLabel lblNewLabel_1_1 = new JLabel("미션 설명");
    	lblNewLabel_1_1.setFont(new Font("나눔고딕", Font.PLAIN, 19));
    	lblNewLabel_1_1.setBounds(56, 179, 80, 35);
    	createPopup.add(lblNewLabel_1_1);
    	
    	textField = new JTextField();
    	textField.setColumns(10);
    	textField.setBackground(new Color(237, 237, 237));
    	textField.setBounds(56, 214, 580, 45);
    	textField.setBorder(null);
    	createPopup.add(textField);
    	
    	textField_1 = new JTextField();
    	textField_1.setColumns(10);
    	textField_1.setBackground(new Color(237, 237, 237));
    	textField_1.setBounds(56, 296, 580, 45);
    	textField_1.setBorder(null);
    	createPopup.add(textField_1);
    	
    	JLabel lblNewLabel_1_1_1 = new JLabel("활동 내용");
    	lblNewLabel_1_1_1.setFont(new Font("나눔고딕", Font.PLAIN, 19));
    	lblNewLabel_1_1_1.setBounds(56, 261, 80, 35);
    	createPopup.add(lblNewLabel_1_1_1);
    	
    	JLabel lblNewLabel_1_1_1_1 = new JLabel("모집 인원");
    	lblNewLabel_1_1_1_1.setFont(new Font("나눔고딕", Font.PLAIN, 19));
    	lblNewLabel_1_1_1_1.setBounds(56, 346, 80, 35);
    	createPopup.add(lblNewLabel_1_1_1_1);
    	
    	JComboBox comboBox = new JComboBox();
    	comboBox.setModel(new DefaultComboBoxModel(new String[] {"인원 선택", "1", "2", "3", "4", "5", "6"}));
    	comboBox.setBounds(56, 384, 160, 39);
    	createPopup.add(comboBox);
    	
    	JLabel lblNewLabel_1_1_1_1_1 = new JLabel("카테고리");
    	lblNewLabel_1_1_1_1_1.setFont(new Font("나눔고딕", Font.PLAIN, 19));
    	lblNewLabel_1_1_1_1_1.setBounds(361, 346, 77, 35);
    	createPopup.add(lblNewLabel_1_1_1_1_1);
    	
    	JComboBox comboBox_1 = new JComboBox();
    	comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"카테고리 선택", "챌린지", "스터디", "다이어트", "기타"}));
    	comboBox_1.setBounds(361, 384, 160, 39);
    	createPopup.add(comboBox_1);
    	
    	JLabel lblNewLabel_1_1_1_2 = new JLabel("모집 기한");
    	lblNewLabel_1_1_1_2.setFont(new Font("나눔고딕", Font.PLAIN, 19));
    	lblNewLabel_1_1_1_2.setBounds(56, 429, 80, 35);
    	createPopup.add(lblNewLabel_1_1_1_2);
    	
    	textField_2 = new JTextField();
    	textField_2.setColumns(10);
    	textField_2.setBorder(null);
    	textField_2.setBackground(new Color(237, 237, 237));
    	textField_2.setBounds(56, 467, 62, 32);
    	createPopup.add(textField_2);
    	
    	textField_3 = new JTextField();
    	textField_3.setColumns(10);
    	textField_3.setBorder(null);
    	textField_3.setBackground(new Color(237, 237, 237));
    	textField_3.setBounds(128, 467, 62, 32);
    	createPopup.add(textField_3);
    	
    	textField_4 = new JTextField();
    	textField_4.setColumns(10);
    	textField_4.setBorder(null);
    	textField_4.setBackground(new Color(237, 237, 237));
    	textField_4.setBounds(200, 467, 62, 32);
    	createPopup.add(textField_4);
    	
    	JLabel lblNewLabel_1_1_1_2_1 = new JLabel("까지");
    	lblNewLabel_1_1_1_2_1.setFont(new Font("나눔고딕", Font.PLAIN, 19));
    	lblNewLabel_1_1_1_2_1.setBounds(271, 465, 39, 35);
    	createPopup.add(lblNewLabel_1_1_1_2_1);
    	
    	JLabel lblNewLabel_1_1_1_2_2 = new JLabel("활동 기간");
    	lblNewLabel_1_1_1_2_2.setFont(new Font("나눔고딕", Font.PLAIN, 19));
    	lblNewLabel_1_1_1_2_2.setBounds(56, 503, 80, 35);
    	createPopup.add(lblNewLabel_1_1_1_2_2);
    	
    	textField_5 = new JTextField();
    	textField_5.setColumns(10);
    	textField_5.setBorder(null);
    	textField_5.setBackground(new Color(237, 237, 237));
    	textField_5.setBounds(56, 541, 62, 32);
    	createPopup.add(textField_5);
    	
    	textField_6 = new JTextField();
    	textField_6.setColumns(10);
    	textField_6.setBorder(null);
    	textField_6.setBackground(new Color(237, 237, 237));
    	textField_6.setBounds(128, 541, 62, 32);
    	createPopup.add(textField_6);
    	
    	textField_7 = new JTextField();
    	textField_7.setColumns(10);
    	textField_7.setBorder(null);
    	textField_7.setBackground(new Color(237, 237, 237));
    	textField_7.setBounds(200, 541, 62, 32);
    	createPopup.add(textField_7);
    	
    	JLabel lblNewLabel_1_1_1_2_1_1 = new JLabel("~");
    	lblNewLabel_1_1_1_2_1_1.setFont(new Font("나눔고딕", Font.PLAIN, 19));
    	lblNewLabel_1_1_1_2_1_1.setBounds(274, 539, 20, 35);
    	createPopup.add(lblNewLabel_1_1_1_2_1_1);
    	
    	textField_8 = new JTextField();
    	textField_8.setColumns(10);
    	textField_8.setBorder(null);
    	textField_8.setBackground(new Color(237, 237, 237));
    	textField_8.setBounds(300, 541, 62, 32);
    	createPopup.add(textField_8);
    	
    	textField_9 = new JTextField();
    	textField_9.setColumns(10);
    	textField_9.setBorder(null);
    	textField_9.setBackground(new Color(237, 237, 237));
    	textField_9.setBounds(371, 541, 62, 32);
    	createPopup.add(textField_9);
    	
    	textField_10 = new JTextField();
    	textField_10.setColumns(10);
    	textField_10.setBorder(null);
    	textField_10.setBackground(new Color(237, 237, 237));
    	textField_10.setBounds(442, 541, 62, 32);
    	createPopup.add(textField_10);
    	
    	JLabel lblNewLabel_1_1_1_2_2_1 = new JLabel("비밀방 설정(비밀번호 4자리 입력)");
    	lblNewLabel_1_1_1_2_2_1.setFont(new Font("나눔고딕", Font.PLAIN, 19));
    	lblNewLabel_1_1_1_2_2_1.setBounds(56, 580, 280, 35);
    	createPopup.add(lblNewLabel_1_1_1_2_2_1);
    	
    	JButton 생성완료 = new JButton("");
    	생성완료.setSelectedIcon(new ImageIcon("C:\\Users\\jihwan\\Desktop\\생성완료.png"));
    	생성완료.setBackground(new Color(255, 255, 255));
    	생성완료.setIcon(new ImageIcon("C:\\Users\\jihwan\\Desktop\\생성완료.png"));
    	생성완료.setBounds(279, 658, 133, 51);
    	생성완료.setBorderPainted(false);
    	createPopup.add(생성완료);
    	
    	JRadioButton rdbtnNewRadioButton = new JRadioButton("예");
    	rdbtnNewRadioButton.setFont(new Font("나눔고딕", Font.PLAIN, 15));
    	rdbtnNewRadioButton.setBackground(new Color(255, 255, 255));
    	rdbtnNewRadioButton.setBounds(56, 621, 50, 23);
    	createPopup.add(rdbtnNewRadioButton);
    	
    	JRadioButton rdbtnNewRadioButton_1 = new JRadioButton("아니오");
    	rdbtnNewRadioButton_1.setFont(new Font("나눔고딕", Font.PLAIN, 15));
    	rdbtnNewRadioButton_1.setBackground(Color.WHITE);
    	rdbtnNewRadioButton_1.setBounds(113, 621, 70, 23);
    	createPopup.add(rdbtnNewRadioButton_1);
    	
    	textField_11 = new JTextField();
    	textField_11.setToolTipText("");
    	textField_11.setColumns(10);
    	textField_11.setBorder(null);
    	textField_11.setBackground(new Color(237, 237, 237));
    	textField_11.setBounds(202, 617, 119, 32);
    	createPopup.add(textField_11);
    }

	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}
}
