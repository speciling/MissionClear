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
import javax.swing.JButton;
import javax.swing.ImageIcon;


/**
 * A custom border with rounded corners.
 * This class implements a border with specified radius and color.
 */
class RoundedBorder implements Border {
    private int radius;
    private Color color;

    /**
     * Constructor to create a rounded border with specified radius and color.
     * @param radius the radius of the rounded corners.
     * @param color the color of the border.
     */
    RoundedBorder(int radius, Color color) {
        this.radius = radius;
        this.color = color;
    }

    /**
     * Gets the border insets for a given component.
     * @param c the component for which this border insets value is to be computed.
     * @return the insets of the border.
     */
    public Insets getBorderInsets(Component c) {
        return new Insets(this.radius+1, this.radius+1, this.radius+2, this.radius);
    }

    /**
     * Checks if the border is opaque.
     * @return false as this custom border is not opaque.
     */
    public boolean isBorderOpaque() {
        return false;
    }

    /**
     * Paints the border for the specified component with the specified position and size.
     * @param c the component for which this border is being painted.
     * @param g the paint graphics.
     * @param x the x position of the border.
     * @param y the y position of the border.
     * @param width the width of the border.
     * @param height the height of the border.
     */
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        g.setColor(color);
        g.drawRoundRect(x, y, width-1, height-1, radius, radius);
    }
}

/**
 * This class represents the UI for adding a new mission room.
 * It initializes and displays the UI components for creating a mission room.
 */

public class AddMissionRoom {
	
	private JLabel title;
	private JLabel description;
	protected RoundedPanel addMissionRoomPanel;
	 
	public AddMissionRoom(Group group) {
        if (group != null) {
            this.initialize(group);
        }
    }

	 /**
     * @wbp.parser.entryPoint
     */
	void initialize(Group group) {
		
		addMissionRoomPanel = new RoundedPanel(20); 
		addMissionRoomPanel.setBounds(4, 1, 406, 273);
		addMissionRoomPanel.setForeground(new Color(255, 255, 255));
	    addMissionRoomPanel.setBackground(new Color(255, 255, 255));
	    addMissionRoomPanel.setLayout(null);
	    
	    title = new JLabel(group.getTitle());
	    title.setBounds(22, 21, 357, 35);
	    title.setFont(new Font("나눔고딕", Font.BOLD, 20));
	    addMissionRoomPanel.add(title);
	    
	    this.description = new JLabel("<html>" + group.getDescription() + "</html>");
	    this.description.setBounds(22, 73, 357, 42);
	    this.description.setFont(new Font("나눔고딕", Font.PLAIN, 18));
	    addMissionRoomPanel.add(this.description);
	    
	    JSeparator separator = new JSeparator();
	    separator.setForeground(new Color(128, 128, 128));
	    separator.setBounds(17, 162, 374, 1); // 위치와 크기 설정
	    addMissionRoomPanel.add(separator);
	    
	    JLabel recruitDeadline = new JLabel("<html><b>모집기한:</b> " + group.getDeadlineYear() + ". " + group.getDeadlineMonth() + ". " + group.getDeadlineDay() + "까지<br></html>");
	    recruitDeadline.setFont(new Font("나눔고딕", Font.PLAIN, 15));
	    recruitDeadline.setBounds(23, 180, 357, 21);
	    addMissionRoomPanel.add(recruitDeadline);
	    
	    JLabel category = new JLabel(group.getCategory());
	    category.setForeground(new Color(56, 183, 255));
	    category.setHorizontalAlignment(JLabel.CENTER);
	    category.setFont(new Font("나눔고딕", Font.PLAIN, 17));
	    category.setBounds(300, 225, 75, 30);
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
	            // GroupDetailPopup 인스턴스 생성 및 프레임 표시
	            GroupDetailPopup popup = new GroupDetailPopup(group);
	            popup.initialize();
	            popup.getFrame().setVisible(true);
	        }
	    });
	    addMissionRoomPanel.add(detailPopup);
	    
	    JLabel recruitmentCapacity = new JLabel("<html><b>모집인원:</b> " + group.getCapacity() + "명<br></html>");
	    recruitmentCapacity.setFont(new Font("나눔고딕", Font.PLAIN, 15));
	    recruitmentCapacity.setBounds(23, 201, 357, 21);
	    addMissionRoomPanel.add(recruitmentCapacity);

	    JLabel activityPeriod = new JLabel("<html><b>활동기간:</b> " + group.getStartDateYear() + ". " + group.getStartDateMonth() + ". " + group.getStartDateDay() + ". ~ " + group.getEndDateYear() + ". " + group.getEndDateMonth() + ". " + group.getEndDateDay() + "</html>");
	    activityPeriod.setFont(new Font("나눔고딕", Font.PLAIN, 15));
	    activityPeriod.setBounds(23, 222, 357, 21);
	    addMissionRoomPanel.add(activityPeriod);
	    
	    JLabel lockLabel = new JLabel("");
	    lockLabel.setIcon(new ImageIcon("./resource/RecruitGroupMember/lock.png"));
	    lockLabel.setBounds(297, 26, 24, 24);
	    if (group.isSecretRoom()) {
            addMissionRoomPanel.add(lockLabel);
        }

	}
	
	
	
	public JPanel getPanel() {
		// TODO Auto-generated method stub
		return addMissionRoomPanel;
	}

}