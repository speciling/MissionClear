package client.recruitpage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;

import client.CustomFont;
import client.MainPage.MainPage;
import client.db.ClientDBManager;

import javax.swing.JLabel;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.MatteBorder;
import javax.swing.plaf.basic.BasicScrollBarUI; 

/**
 * 사용자가 속한 그룹 목록을 표시하는 클래스.
 * 이 클래스는 사용자 인터페이스를 통해 사용자가 참여하고 있는 그룹의 목록을 제공한다.
 * 사용자는 이 목록을 통해 소속된 그룹의 정보를 확인하고 관리할 수 있다.
 */
public class MyGroupList {
    private JPanel myGroup; // 사용자의 그룹 목록을 표시하는 메인 패널
    public List<JPanel> addedPanels = new ArrayList<>(); // 동적으로 추가된 그룹 패널 목록
    public int nextPanelY = 93; // 다음 패널이 배치될 Y 좌표
    public final int PANEL_HEIGHT = 189; // 각 패널의 높이
    CustomFont customFont = new CustomFont();
    
    /**
     * MyGroupList 인터페이스의 메인 패널을 반환한다.
     * @return 그룹 목록 인터페이스의 메인 JPanel 컴포넌트
     */
    public JPanel get() {
        return myGroup;
    }

    /**
     * MyGroupList 인터페이스를 초기화하는 생성자.
     * @param vis 메인 페이지의 가시성을 설정하는 불리언 값. 메인 페이지의 가시성을 제어한다.
     */
    public MyGroupList(boolean vis) {
        super();
        initializeMyGroupList();
        
    }

    /**
     * '내 그룹 목록' 인터페이스를 초기화하고 설정하는 메소드.
     * 이 메소드는 사용자의 그룹 목록을 표시하기 위한 레이아웃과 컴포넌트를 설정한다.
     * 그룹 목록은 스크롤 가능한 패널에 동적으로 추가되며, 각 그룹에 대한 정보를 표시한다.
     * 이 메소드는 그룹 목록 레이블, 스크롤 패널, 각 그룹의 패널을 초기화하고 설정한다.
     */
    private void initializeMyGroupList() {
        myGroup = new JPanel();
        myGroup.setBackground(new Color(246, 246, 246));
        myGroup.setBounds(0, 0, 950, 850);
        myGroup.setLayout(null);

        JLabel groupList = new JLabel("내 그룹 목록");
        groupList.setFont(customFont.deriveFont(Font.BOLD,32));
        groupList.setBounds(13, 22, 180, 35);
        myGroup.add(groupList);

        JPanel groupListPanel = new JPanel();
        groupListPanel.setLayout(null);
        groupListPanel.setBackground(new Color(246, 246, 246));
        
        List<Group> groups = ClientDBManager.getMyGroupList();
        int panelY = 5; 
        for (Group group : groups) {
            AddMyGroup addMyGroup = new AddMyGroup(group);
            JPanel addGroupPanel = addMyGroup.getPanel();

            addGroupPanel.setBounds(13, panelY, addGroupPanel.getWidth(), addGroupPanel.getHeight());
            groupListPanel.add(addGroupPanel);

            panelY += PANEL_HEIGHT + 5; // 5는 패널 간의 간격
        }
        groupListPanel.setPreferredSize(new Dimension(930, panelY));

        JScrollPane scrollPane = new JScrollPane(groupListPanel);
        scrollPane.setBounds(10, 70, 930, 760);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBorder(new MatteBorder(1, 1, 1, 1, new Color(246, 246, 246)));

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

    /**
     * 그룹 목록을 새로고침하는 메소드.
     * 이 메소드는 데이터베이스에서 최신 그룹 목록을 가져와서 인터페이스에 표시한다.
     * 그룹 목록은 동적으로 업데이트되며, UI는 해당 변경사항을 반영하여 갱신된다.
     * 새로고침 기능은 사용자가 그룹 정보를 최신 상태로 유지하고 싶을 때 유용하다.
     */
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
