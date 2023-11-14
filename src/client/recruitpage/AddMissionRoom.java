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

class RoundedBorder implements Border {
    private int radius;
    private Color color;

    RoundedBorder(int radius, Color color) {
        this.radius = radius;
        this.color = color;
    }

    public Insets getBorderInsets(Component c) {
        return new Insets(this.radius+1, this.radius+1, this.radius+2, this.radius);
    }

    public boolean isBorderOpaque() {
        return false;
    }

    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        g.setColor(color);
        g.drawRoundRect(x, y, width-1, height-1, radius, radius);
    }
}


public class AddMissionRoom {

	private JFrame frame;
	protected RoundedPanel addMissionRoomPanel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddMissionRoom window = new AddMissionRoom();
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
	public AddMissionRoom() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	void initialize() {
		frame = new JFrame();
		frame.setSize(433, 315);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		addMissionRoomPanel = new RoundedPanel(20); 
		addMissionRoomPanel.setBounds(4, 1, 406, 273);
		addMissionRoomPanel.setForeground(new Color(255, 255, 255));
	    addMissionRoomPanel.setBackground(new Color(255, 255, 255));
	    frame.getContentPane().add(addMissionRoomPanel);
	    addMissionRoomPanel.setLayout(null);
	    
	    JLabel 방제목 = new JLabel("미라클 모닝 챌린지 할 사람 구합니다!");
	    방제목.setBounds(22, 21, 357, 35);
	    방제목.setFont(new Font("나눔고딕", Font.BOLD, 20));
	    addMissionRoomPanel.add(방제목);
	    
	    JLabel 미션내용 = new JLabel("<html>정말 열심히 할 분만 모집하고 있습니다.<br>벌금 있습니다!</html>");
	    미션내용.setBounds(22, 73, 357, 42);
	    미션내용.setFont(new Font("나눔고딕", Font.PLAIN, 18));
	    addMissionRoomPanel.add(미션내용);
	    
	    JSeparator separator = new JSeparator();
	    separator.setForeground(new Color(128, 128, 128));
	    separator.setBounds(17, 162, 374, 1); // 위치와 크기 설정
	    addMissionRoomPanel.add(separator);
	    
	    JLabel lblNewLabel = new JLabel("<html><b>모집기한:</b> 2023. 10. 01까지<br><b>모집인원:</b> 5명<br><b>활동기간:</b> 2023. 10. 23 ~ 2023. 12. 03\r\n");
	    lblNewLabel.setFont(new Font("나눔고딕", Font.PLAIN, 15));
	    lblNewLabel.setBounds(23, 180, 357, 63);
	    addMissionRoomPanel.add(lblNewLabel);
	    
	    JLabel category = new JLabel("챌린지");
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
	            GroupDetailPopup popup = new GroupDetailPopup();
	            popup.initialize();
	            popup.getFrame().setVisible(true);
	        }
	    });
	    addMissionRoomPanel.add(detailPopup);

	}
	
	public JPanel getPanel() {
		// TODO Auto-generated method stub
		return addMissionRoomPanel;
	}

}
