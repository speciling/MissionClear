package client.recruitpage;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;

public class AddMyGroup {

	protected RoundedPanel addGroupPanel;
	/**
	 * Launch the application.
	 */
	

	/**
	 * Create the application.
	 */
	public AddMyGroup() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		addGroupPanel =  new RoundedPanel(0); 
		addGroupPanel.setBounds(4, 1, 943, 189);
		addGroupPanel.setForeground(new Color(255, 255, 255));
		addGroupPanel.setBackground(new Color(255, 255, 255));
		addGroupPanel.setLayout(null);
	}

}
