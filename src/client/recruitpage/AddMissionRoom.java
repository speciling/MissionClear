package client.recruitpage;

import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import java.awt.Insets;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
//import com.jgoodies.forms.factories.DefaultComponentFactory;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import client.CustomFont;

import javax.swing.JButton;
import javax.swing.ImageIcon;

/**
 * 둥근 모서리를 가진 사용자 지정 테두리를 구현하는 클래스.
 * 지정된 반경과 색상을 가진 테두리를 생성한다.
 */
class RoundedBorder implements Border {
    private int radius;
    private Color color;

    /**
     * 둥근 테두리를 생성하는 생성자.
     * @param radius 둥근 모서리의 반경.
     * @param color 테두리의 색상.
     */
    RoundedBorder(int radius, Color color) {
        this.radius = radius;
        this.color = color;
    }

    /**
     * 주어진 컴포넌트에 대한 테두리 여백을 가져온다.
     * @param c 테두리 여백이 계산될 컴포넌트.
     * @return 테두리의 여백.
     */
    public Insets getBorderInsets(Component c) {
        return new Insets(this.radius+1, this.radius+1, this.radius+2, this.radius);
    }

    /**
     * 테두리가 불투명한지 여부를 확인한다.
     * @return 이 사용자 정의 테두리는 불투명하지 않으므로 false를 반환한다.
     */
    public boolean isBorderOpaque() {
        return false;
    }

    /**
     * 지정된 위치와 크기에 대해 특정 컴포넌트의 테두리를 그린다.
     * @param c 테두리가 그려질 컴포넌트.
     * @param g 그리기 그래픽.
     * @param x 테두리의 x 위치.
     * @param y 테두리의 y 위치.
     * @param width 테두리의 너비.
     * @param height 테두리의 높이.
     */
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        g.setColor(color);
        g.drawRoundRect(x, y, width-1, height-1, radius, radius);
    }
}

/**
 * 새 미션 방을 추가하는 UI를 구현하는 클래스.
 */
public class AddMissionRoom {
	
	private JLabel title;
	private JLabel description;
	protected RoundedPanel addMissionRoomPanel;
	CustomFont customFont = new CustomFont();
	
	 /**
     * 그룹 객체를 매개변수로 받는 생성자.
     * 제공된 그룹 정보를 사용하여 UI를 초기화한다.
     * @param group UI에 표시될 그룹 정보를 담고 있는 Group 객체.
     */
	public AddMissionRoom(Group group) {
        if (group != null) {
            this.initialize(group);
        }
    }

	 /**
     * UI 컴포넌트를 초기화하는 메소드.
     * 라벨, 분리선, 버튼 등을 사용하여 사용자 인터페이스를 구성한다.
     * @param group 이 UI에 표시될 그룹 정보.
     */
	void initialize(Group group) {
		
		addMissionRoomPanel = new RoundedPanel(20); 
		addMissionRoomPanel.setBounds(4, 1, 406, 273);
		addMissionRoomPanel.setForeground(new Color(255, 255, 255));
	    addMissionRoomPanel.setBackground(new Color(255, 255, 255));
	    addMissionRoomPanel.setLayout(null);
	    
	    title = new JLabel(group.getTitle());
	    title.setBounds(22, 21, 357, 35);
	    title.setFont(customFont.deriveFont(Font.BOLD,20));
	    addMissionRoomPanel.add(title);
	    
	    this.description = new JLabel(group.getDescription());
	    this.description.setBounds(22, 73, 357, 42);
	    this.description.setFont(customFont.deriveFont(Font.PLAIN,18));
	    addMissionRoomPanel.add(this.description);
	    
	    JSeparator separator = new JSeparator();
	    separator.setForeground(new Color(128, 128, 128));
	    separator.setBounds(17, 162, 374, 1); // 위치와 크기 설정
	    addMissionRoomPanel.add(separator);
	    
	    JLabel recruitDeadlineLabel = new JLabel("모집기한: ");
	    recruitDeadlineLabel.setBounds(23, 180, 68, 21);
	    recruitDeadlineLabel.setFont(customFont.deriveFont(Font.BOLD,15));
	    addMissionRoomPanel.add(recruitDeadlineLabel);
	    
	    JLabel recruitDeadline = new JLabel(group.getDeadlineYear() + ". " + group.getDeadlineMonth() + ". " + group.getDeadlineDay() + " 까지");
	    recruitDeadline.setFont(customFont.deriveFont(Font.PLAIN,15));
	    recruitDeadline.setBounds(90, 180, 298, 21);
	    addMissionRoomPanel.add(recruitDeadline);
	    
	    JLabel category = new JLabel(group.getCategory());
	    category.setForeground(new Color(56, 183, 255));
	    category.setHorizontalAlignment(JLabel.CENTER);
	    category.setFont(customFont.deriveFont(Font.PLAIN,17));
	    category.setBounds(310, 225, 85, 30);
	    Color customColor = new Color(56, 183, 255);
	    category.setBorder(new RoundedBorder(10, customColor));
	    addMissionRoomPanel.add(category);
	    
	    JButton detailPopup = new JButton("");
	    detailPopup.setBounds(0, 0, 406, 273);
	    detailPopup.setBorderPainted(false);
	    detailPopup.setContentAreaFilled(false);
	    detailPopup.setFocusPainted(false);
	    
	    detailPopup.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {

	            GroupDetailPopup popup = new GroupDetailPopup(group); // 생성자 호출 시 MyGroupList도 전달
	            popup.initialize();
	            popup.getFrame().setVisible(true);
	        }
	    });

	    addMissionRoomPanel.add(detailPopup);
	    
	    JLabel recruitmentCapacityLabel = new JLabel("모집인원: ");
	    recruitmentCapacityLabel.setBounds(23, 201, 68, 21);
	    recruitmentCapacityLabel.setFont(customFont.deriveFont(Font.BOLD,15));
	    addMissionRoomPanel.add(recruitmentCapacityLabel);
	    
	    JLabel recruitmentCapacity = new JLabel(group.getCapacity() + " 명");
	    recruitmentCapacity.setFont(customFont.deriveFont(Font.PLAIN,15));
	    recruitmentCapacity.setBounds(90, 201, 357, 21);
	    addMissionRoomPanel.add(recruitmentCapacity);

	    JLabel activityPeriodLabel = new JLabel("활동기간: ");
	    activityPeriodLabel.setBounds(23, 222, 68, 21);
	    activityPeriodLabel.setFont(customFont.deriveFont(Font.BOLD,15));
	    addMissionRoomPanel.add(activityPeriodLabel);
	    
	    JLabel activityPeriod = new JLabel(group.getStartDateYear() + ". " + group.getStartDateMonth() + ". " + group.getStartDateDay() + ". ~ " + group.getEndDateYear() + ". " + group.getEndDateMonth() + ". " + group.getEndDateDay());
	    activityPeriod.setFont(customFont.deriveFont(Font.PLAIN,15));
	    activityPeriod.setBounds(90, 222, 357, 21);
	    addMissionRoomPanel.add(activityPeriod);
	    
	    JLabel lockLabel = new JLabel("");
	    lockLabel.setIcon(new ImageIcon("./resource/RecruitGroupMember/lock.png"));
	    lockLabel.setBounds(297, 26, 24, 24);
	    if (group.isSecretRoom()) {
            addMissionRoomPanel.add(lockLabel);
        }
	}
	
	/**
     * 이 클래스의 JPanel 객체를 반환하는 메소드.
     * @return 이 클래스의 JPanel 객체.
     */
	public JPanel getPanel() {
		return addMissionRoomPanel;
	}
}