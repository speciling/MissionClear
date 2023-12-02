package client.recruitpage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;

import client.MainPage.MainPage;
import client.db.ClientDBManager;

import javax.swing.JLabel;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.plaf.basic.BasicScrollBarUI; 

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
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        myGroup.add(scrollPane);
        
        JScrollBar verticalBar = scrollPane.getVerticalScrollBar();
        verticalBar.setPreferredSize(new Dimension(15, 0)); // 스크롤바의 너비 설정
        verticalBar.setUnitIncrement(16); // 단위 증가량을 16픽셀로 설정

        // 스크롤바의 썸 부분을 더 돋보이게 하는 색상으로 변경
        verticalBar.setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(180, 180, 180); // 썸의 색상 설정
                this.trackColor = new Color(246, 246, 246); // 트랙의 색상 설정
            }

            @Override
            protected JButton createDecreaseButton(int orientation) {
                return createZeroButton();
            }

            @Override
            protected JButton createIncreaseButton(int orientation) {
                return createZeroButton();
            }

            private JButton createZeroButton() {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(0, 0));
                button.setMinimumSize(new Dimension(0, 0));
                button.setMaximumSize(new Dimension(0, 0));
                return button;
            }
        });

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
