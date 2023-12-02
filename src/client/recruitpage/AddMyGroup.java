package client.recruitpage;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Font;

public class AddMyGroup {

	protected RoundedPanel addGroupPanel;
	private Group group;
	/**
	 * Launch the application.
	 */
	

	/**
	 * Create the application.
	 */
	public AddMyGroup() {
		initialize();
	}

	public AddMyGroup(Group group) {
		this.group = group;
		initialize();
	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		addGroupPanel =  new RoundedPanel(0); 
		addGroupPanel.setBounds(0, 0, 943, 189);
		addGroupPanel.setForeground(new Color(255, 255, 255));
		addGroupPanel.setBackground(new Color(255, 255, 255));
		addGroupPanel.setLayout(null);
		
		JLabel missionTitleLabel = new JLabel("돈 아껴서 부자되자!");
		missionTitleLabel.setFont(new Font("나눔고딕", Font.BOLD, 25));
		missionTitleLabel.setBounds(26, 12, 232, 35);
		addGroupPanel.add(missionTitleLabel);
		
		JLabel activityPeriodLabel = new JLabel("<html><b>활동기간</b>: " + group.getStartDateYear() + ". " + group.getStartDateMonth() + ". " + group.getStartDateDay() + " ~ " + group.getEndDateYear() + ". " + group.getEndDateMonth() + ". " + group.getEndDateDay() + "</html>");
		activityPeriodLabel.setFont(new Font("나눔고딕", Font.PLAIN, 15));
		activityPeriodLabel.setBounds(26, 53, 352, 25);
		addGroupPanel.add(activityPeriodLabel);
		
		JLabel missionLabel = new JLabel("<html><b>활동내용</b>: " + group.getMission() + "</html>");
		missionLabel.setFont(new Font("나눔고딕", Font.PLAIN, 15));
		missionLabel.setBounds(26, 83, 352, 25);
		addGroupPanel.add(missionLabel);
		
		JLabel personnelPanel = new JLabel("참여인원");
		personnelPanel.setFont(new Font("나눔고딕", Font.PLAIN, 15));
		personnelPanel.setBounds(26, 113, 352, 25);
		addGroupPanel.add(personnelPanel);
		
	}
	/**
     * Gets the panel of this class.
     * @return The JPanel object of this class.
     */
    public JPanel getPanel() {
        return addGroupPanel;
    }
	
}
