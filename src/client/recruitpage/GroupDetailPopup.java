package client.recruitpage;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JFrame;
import javax.swing.JPanel;



public class GroupDetailPopup {

	private JFrame frame;
	protected RoundedPanel groupDetailPopupPanel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GroupDetailPopup window = new GroupDetailPopup();
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
	public GroupDetailPopup() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setSize(422, 425);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		groupDetailPopupPanel = new RoundedPanel(20); 
		groupDetailPopupPanel.setBounds(0, 0, 406, 387);
		groupDetailPopupPanel.setForeground(new Color(255, 255, 255));
		groupDetailPopupPanel.setBackground(new Color(255, 255, 255));
	    frame.getContentPane().add(groupDetailPopupPanel);
	    groupDetailPopupPanel.setLayout(null);
	}

}
