package client.recruitpage;

import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import client.MainPage.MainPage;
import javax.swing.JLabel;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;

/**
 * Class representing the list of groups a user is part of.
 * This class extends MainPage and provides a user interface for displaying a list of groups the user is in.
 */
public class MyGroupList {
    private static MyGroupList instance;
    private JPanel myGroup;
    private List<JPanel> addedPanels = new ArrayList<>();
    private int nextPanelY = 93; // 첫 패널의 Y 위치
    private final int PANEL_HEIGHT = 189; // 각 패널의 높이

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

    public static MyGroupList getInstance() {
        if (instance == null) {
            instance = new MyGroupList(true);
        }
        return instance;
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
        groupList.setFont(new Font("나눔고딕", Font.BOLD, 20));
        groupList.setBounds(13, 22, 156, 35);
        myGroup.add(groupList);

        List<Group> groups = GroupManager.getGroupList();
        for (Group group : groups) {
            AddMyGroup addMyGroup = new AddMyGroup(group);
            JPanel addGroupPanel = addMyGroup.getPanel();

            addGroupPanel.setBounds(0, nextPanelY, addGroupPanel.getWidth(), addGroupPanel.getHeight());
            myGroup.add(addGroupPanel);

            nextPanelY += PANEL_HEIGHT;
        }
    }

    public void addNewGroup(Group group) {
        AddMyGroup addMyGroup = new AddMyGroup(group); // AddMyGroup 인스턴스 생성
        JPanel addGroupPanel = addMyGroup.getPanel(); // addGroupPanel 가져오기

        // addGroupPanel의 위치와 크기 설정
        addGroupPanel.setBounds(0, nextPanelY, addGroupPanel.getWidth(), addGroupPanel.getHeight());
        addedPanels.add(addGroupPanel); // 패널 목록에 추가

        myGroup.add(addGroupPanel); // myGroup 패널에 addGroupPanel 추가
        myGroup.setComponentZOrder(addGroupPanel, 0); // addGroupPanel을 가장 상단으로 이동

        nextPanelY += PANEL_HEIGHT; // 다음 패널의 Y 위치 조정
        myGroup.revalidate();
        myGroup.repaint();
    }

}
