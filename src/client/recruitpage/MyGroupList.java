package client.recruitpage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import client.MainPage.MainPage;
import client.db.ClientDBManager;

import javax.swing.JLabel;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JScrollPane; 

/**
 * Class representing the list of groups a user is part of.
 * This class extends MainPage and provides a user interface for displaying a list of groups the user is in.
 */
public class MyGroupList {
    private JPanel myGroup;
    public List<JPanel> addedPanels = new ArrayList<>();
    public int nextPanelY = 93; // 첫 패널의 Y 위치
    public final int PANEL_HEIGHT = 189; // 각 패널의 높이

    /**
     * Retrieves the main panel of the group list interface.
     * @return the main JPanel component of the group list interface.
     */
    public JPanel get() {
        return myGroup;
    }

    /**
     * Constructor to initialize the MyGroupList interface.
     * @param vis Boolean value to set the visibility of the main page.
     */
    public MyGroupList(boolean vis) {
        super();
        initializeMyGroupList();
        
    }

   

    /**
     * Initializes and sets up the My Group List interface.
     * This method sets up the layout and components for displaying the list of groups the user is part of.
     */
    private void initializeMyGroupList() {
        myGroup = new JPanel();
        myGroup.setBackground(new Color(246, 246, 246));
        myGroup.setBounds(0, 0, 950, 850);
        myGroup.setLayout(null);

        JLabel groupList = new JLabel("내그룹목록");
        groupList.setFont(new Font("나눔고딕", Font.BOLD, 32));
        groupList.setBounds(13, 22, 156, 35);
        myGroup.add(groupList);

        JPanel groupListPanel = new JPanel();
        groupListPanel.setLayout(null);
        groupListPanel.setBackground(new Color(246, 246, 246));
        
        List<Group> groups = ClientDBManager.getMyGroupList();
        int panelY = 13; 
        for (Group group : groups) {
            AddMyGroup addMyGroup = new AddMyGroup(group);
            JPanel addGroupPanel = addMyGroup.getPanel();

            addGroupPanel.setBounds(13, panelY, addGroupPanel.getWidth(), addGroupPanel.getHeight());
            groupListPanel.add(addGroupPanel);

            panelY += PANEL_HEIGHT + 10; // 10은 패널 간의 간격
        }
        groupListPanel.setPreferredSize(new Dimension(930, panelY));

        JScrollPane scrollPane = new JScrollPane(groupListPanel);
        scrollPane.setBounds(10, 70, 930, 760);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        myGroup.add(scrollPane);
    }

    public void refreshGroupList() {
        List<Group> groups = ClientDBManager.getMyGroupList();
        for (Group group : groups) {
            AddMyGroup addMyGroup = new AddMyGroup(group);
            JPanel addGroupPanel = addMyGroup.getPanel();

            addGroupPanel.setBounds(0, nextPanelY, addGroupPanel.getWidth(), addGroupPanel.getHeight());
            myGroup.add(addGroupPanel);

            nextPanelY += PANEL_HEIGHT;
        }
        // UI 갱신
        myGroup.revalidate();
        myGroup.repaint();
    }


}
