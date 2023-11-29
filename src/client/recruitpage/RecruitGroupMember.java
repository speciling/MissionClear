package client.recruitpage;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.DropMode;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.border.Border;
import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import client.recruitpage.Group;

import client.MainPage.MainPage;
/**
 * A custom JPanel with rounded corners.
 * This class extends JPanel to create a panel with rounded corners.
 */
class RoundedPanel extends JPanel {
    private int radius;

    /**
     * Constructor to create a rounded panel with a specified radius.
     * @param radius the radius of the corners in the panel.
     */
    public RoundedPanel(int radius) {
        this.radius = radius;
        setOpaque(false); // 패널 배경을 투명하게 설정
    }

    /**
     * Paints the rounded corners on the panel.
     * @param g the Graphics object to protect.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // 모서리가 둥근 사각형을 그림
        g2d.fill(new RoundRectangle2D.Double(0, 0, getWidth()-1, getHeight()-1, radius, radius));
    }
}

/**
 * Class representing the recruitment group member interface.
 * This class extends MainPage and provides a user interface for recruitment functionalities.
 */
public class RecruitGroupMember{
	private int currentPage = 0;
	private final int PANELS_PER_PAGE = 4;
	private List<JPanel> allPanels = new ArrayList<>();
	private List<Group> searchList = new ArrayList<>();
	private JTextField searchTitle;
	private JPanel groupRecruitment;
	//JPanel a;
	private static int panelCount = 0;
	/**
     * Retrieves the main panel of the recruitment interface.
     * @return the main JPanel component of the recruitment interface.
     */
	//public JPanel get() {
	//    return a;
	//}

	/**
     * Constructor to initialize the RecruitGroupMember interface.
     * @param vis Boolean value to set the visibility of the main page.
     */
	public RecruitGroupMember() {
		super();
		initializeGroupRecruitment();
	}
	
	/**
     * Initializes and sets up the group recruitment interface.
     */
	private JPanel dynamicPanel;
	private void initializeGroupRecruitment() {
        groupRecruitment = new JPanel();
        groupRecruitment.setBackground(new Color(246, 246, 246));
        groupRecruitment.setBounds(0, 0, 950, 850);
        groupRecruitment.setLayout(null);
        dynamicPanel = new JPanel();
        dynamicPanel.setLayout(null);
        dynamicPanel.setOpaque(false); // 배경을 투명하게 설정
        dynamicPanel.setBounds(0, 0, 950, 850); // 동적 패널의 위치와 크기 설정
        groupRecruitment.add(dynamicPanel);

        JButton missionRoomCreate = new JButton("");
        missionRoomCreate.setBounds(707, 84, 189, 40);
        missionRoomCreate.setBackground(new Color(246, 246, 246));
        missionRoomCreate.setIcon(new ImageIcon("./resource/RecruitGroupMember/미션방생성.png"));
        missionRoomCreate.setToolTipText("");
        missionRoomCreate.setBorderPainted(false);
        missionRoomCreate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	CreateNewGroupPopup cp = new CreateNewGroupPopup(RecruitGroupMember.this); // 수정됨
                cp.getFrame().setVisible(true);
            }
        });
        
        JButton searchButton = new JButton("");
        searchButton.setBackground(new Color(255, 255, 255));
        searchButton.setBorderPainted(false);
        searchButton.setIcon(new ImageIcon("./resource/RecruitGroupMember/search.png"));
        searchButton.setBounds(56, 26, 30, 30);
        groupRecruitment.add(searchButton);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performSearch();
            }
        });
        
        // 미션 방 검색
        searchTitle = new JTextField() {
            @Override
            protected void paintComponent(Graphics g) {
                if (!isOpaque()) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(getBackground());
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 50, 50); // 둥근 모서리 그리기
                    g2.dispose();
                }
                super.paintComponent(g);
            }
        };
        searchTitle.setBounds(39, 12, 866, 59);
        groupRecruitment.add(searchTitle);
        
        searchTitle.setOpaque(false); // 배경을 투명하게 설정
        searchTitle.setText("          원하는 미션방을 검색할 수 있어요!");
        searchTitle.setForeground(SystemColor.controlShadow);
        searchTitle.setFont(new Font("나눔고딕", Font.BOLD, 18));
        searchTitle.setBorder(null);
        searchTitle.setColumns(10);
        groupRecruitment.add(missionRoomCreate);
        
        JLabel lblNewLabel_1 = new JLabel("카테고리 선택");
        lblNewLabel_1.setBounds(50, 81, 110, 35);
        lblNewLabel_1.setFont(new Font("\uB098\uB214\uACE0\uB515", lblNewLabel_1.getFont().getStyle() | Font.BOLD, lblNewLabel_1.getFont().getSize() + 6));
        groupRecruitment.add(lblNewLabel_1);
        JComboBox comboBox = new JComboBox();
        comboBox.setBounds(180, 82, 121, 35);
        comboBox.setModel(new DefaultComboBoxModel(new String[] {"선택하기", "다이어트", "챌린지", "스터디", "기타"}));
        groupRecruitment.add(comboBox);

        MainPage mp = new MainPage(true);
		JPanel a = mp.globPan;
		a.setLayout(null);
        a.add(groupRecruitment);
        
        JButton backButton = new JButton("");
        backButton.setBackground(new Color(246, 246, 246));
        backButton.setBorderPainted(false);
        backButton.setIcon(new ImageIcon("./resource/RecruitGroupMember/backButton.png"));
        backButton.setBounds(875, 725, 30, 40);
        groupRecruitment.add(backButton);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentPage > 0) {
                    currentPage--;
                    updatePanels();
                }
            }
        });
        JButton nextButton = new JButton("");
        nextButton.setBackground(new Color(246, 246, 246));
        nextButton.setBorderPainted(false);
        nextButton.setIcon(new ImageIcon("./resource/RecruitGroupMember/nextButton.png"));
        nextButton.setBounds(905, 725, 30, 40);
        groupRecruitment.add(nextButton);
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if ((currentPage + 1) * PANELS_PER_PAGE < allPanels.size()) {
                    currentPage++;
                    updatePanels();
                }
            }
        });
	}
   
	 /**
     * Adds a panel to the group recruitment interface.
     * @param panel The panel to be added to the group recruitment interface.
     */
	public void addToGroupRecruitment(JPanel panel) {
		allPanels.add(panel);
        if (allPanels.size() <= (currentPage + 1) * PANELS_PER_PAGE) {
            placePanel(panel, allPanels.size() - 1);
        }
    }
	private void performSearch() {
        String searchText = searchTitle.getText().trim().toLowerCase();
        searchList.clear(); // 이전 검색 결과를 지웁니다.

        for (Group group : GroupManager.getGroupList()) { // GroupManager에서 groupList를 가져옵니다.
            if (group.getTitle().toLowerCase().contains(searchText)) {
                searchList.add(group); // 검색 텍스트와 일치하는 그룹을 searchList에 추가합니다.
            }
        }

        updateSearchResults(); // 검색 결과에 따라 패널을 업데이트합니다.
    }
	private void updateSearchResults() {
	    dynamicPanel.removeAll(); // 현재 동적 패널의 모든 컴포넌트를 제거합니다.

	    int searchPanelIndex = 0; // 검색된 패널의 인덱스
	    for (Group group : searchList) {
	        for (JPanel panel : allPanels) {
	            if (isPanelMatchingGroup(panel, group)) {
	                placePanel(panel, searchPanelIndex % PANELS_PER_PAGE); // 검색된 패널을 앞쪽에 배치합니다.
	                searchPanelIndex++; // 다음 검색된 패널의 인덱스를 업데이트합니다.
	                if (searchPanelIndex >= PANELS_PER_PAGE) {
	                    break; // 한 페이지에 표시할 수 있는 최대 패널 수에 도달했습니다.
	                }
	            }
	        }
	        if (searchPanelIndex >= PANELS_PER_PAGE) {
	            break; // 한 페이지의 패널 한계에 도달했으므로 더 이상의 검색은 중단합니다.
	        }
	    }

	    dynamicPanel.revalidate();
	    dynamicPanel.repaint();
	}

	private boolean isPanelMatchingGroup(JPanel panel, Group group) {
	    // 패널 내부를 순회하며 JLabel 컴포넌트를 찾습니다.
	    for (Component comp : panel.getComponents()) {
	        if (comp instanceof JLabel) {
	            JLabel label = (JLabel)	 comp;
	            if (label.getText().equals(group.getTitle())) {
	                return true; // 레이블의 텍스트가 Group의 제목과 일치하면 true를 반환합니다.
	            }
	        }
	    }
	    return false; // 일치하는 레이블이 없으면 false를 반환합니다.
	}

	private void updatePanels() {
	    dynamicPanel.removeAll(); // 현재 페이지의 패널들을 모두 제거
	    int start = currentPage * PANELS_PER_PAGE;
	    int end = Math.min(start + PANELS_PER_PAGE, allPanels.size());
	    for (int i = start; i < end; i++) {
	        placePanel(allPanels.get(i), i % PANELS_PER_PAGE); // 현재 페이지에 맞는 패널만 추가
	    }
	    dynamicPanel.revalidate();
	    dynamicPanel.repaint();
	    
	}
	
	private void placePanel(JPanel panel, int index) {
	    int position = index % PANELS_PER_PAGE;
	    int top, left;
	    switch (position) {
	        case 0:
	            top = 141;
	            left = 50;
	            break;
	        case 1:
	            top = 141;
	            left = 490;
	            break;
	        case 2:
	            top = 439;
	            left = 50;
	            break;
	        case 3:
	            top = 439;
	            left = 490;
	            break;
	        default:
	            throw new IllegalStateException("Unexpected panel index: " + position);
	    }

	    panel.setBounds(left, top, panel.getWidth(), panel.getHeight());
	    dynamicPanel.add(panel); // Add panel to dynamicPanel instead of groupRecruitment
	}

	
}
