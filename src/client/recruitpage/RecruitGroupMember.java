package client.recruitpage;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.geom.Path2D;
import java.awt.geom.RoundRectangle2D;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.plaf.basic.BasicComboBoxUI;

import client.CustomFont;

import java.util.ArrayList;
import java.util.List;

/**
 * 둥근 모서리를 가진 사용자 정의 JPanel.
 * 이 클래스는 JPanel을 확장하여 둥근 모서리를 가진 시각적으로 패널을 생성
 * 사용자 인터페이스의 미학을 향상시킴
 */
class RoundedPanel extends JPanel {
    private int radius;

    /**
     * 지정된 모서리 반경을 가진 둥근 패널을 생성하는 생성자.
     * @param radius 패널의 모서리 반경.
     */
    public RoundedPanel(int radius) {
        this.radius = radius;
        setOpaque(false); // 패널 배경을 투명하게 설정
    }

    /**
     * 패널에 둥근 모서리를 그림.
     * @param g 그리기 작업에 사용되는 Graphics 객체.
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
 * 모집 그룹 멤버 인터페이스를 나타내는 클래스.
 * 이 클래스는 모집 기능을 위한 사용자 인터페이스 요소를 관리하며, MainPage의 기능을 확장.
 */
public class RecruitGroupMember{
	private int currentPage = 0;
	private final int PANELS_PER_PAGE = 4;
	private List<JPanel> allPanels = new ArrayList<>();
	private List<Group> searchList = new ArrayList<>();
	private JTextField searchTitle;
	public JPanel groupRecruitment;
	CustomFont customFont = new CustomFont();
	
	/**
     * 그룹 모집 패널을 반환.
     * @return 현재의 그룹 모집 JPanel.
     */
	public JPanel get() {
	      return groupRecruitment;
	}
	
	/**
     * 생성자. 그룹 모집 인터페이스를 초기화하고 서버에서 그룹 데이터를 로드.
     * @param vis 인터페이스의 가시성을 설정하는 불리언 값.
     */
	public RecruitGroupMember(boolean vis) {
		super();
		initializeGroupRecruitment();
		loadGroupsFromServerAndDisplay(); 
	}
	
	/**
     * 서버에서 그룹을 로드하고 인터페이스에 표시.
     * 모든 그룹에 대한 UI 패널을 생성하고 화면에 추가.
     */
	private void loadGroupsFromServerAndDisplay() {
        GroupManager.getRecruitingGroupData(); // 서버에서 데이터를 가져옴
        List<Group> groups = GroupManager.getGroupList(); // 현재 등록된 모든 그룹의 데이터를 가져옴
        for (Group group : groups) {
            // 각 그룹에 대해 UI 패널을 생성하고 화면에 추가
            AddMissionRoom missionRoomPanel = new AddMissionRoom(group);
            addToGroupRecruitment(missionRoomPanel.getPanel()); // 패널을 UI에 추가
        }
        // 필요한 경우 UI 업데이트 메소드 호출
    }	
	
	/**
     * 그룹 모집 인터페이스를 초기화하고 설정.
     * 여러 UI 구성 요소를 초기화하고 배치.
     */
	private JPanel dynamicPanel;
	private JComboBox<String> comboBox;
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
        missionRoomCreate.setIcon(new ImageIcon(getClass().getClassLoader().getResource("RecruitGroupMember/미션방생성.png")));
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
        searchButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("RecruitGroupMember/search.png")));
        searchButton.setBounds(56, 26, 30, 30);
        groupRecruitment.add(searchButton);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performSearch();
            }
        });
        
        // 미션 방 검색
        searchTitle = new JTextField("          원하는 미션방을 검색할 수 있어요!") {
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
        searchTitle.setForeground(SystemColor.controlShadow); // 초기 텍스트 색상 설정
        searchTitle.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchTitle.getText().trim().equals("원하는 미션방을 검색할 수 있어요!")) {
                    searchTitle.setText("                ");
                    searchTitle.setForeground(Color.BLACK); // 사용자가 클릭하면 텍스트 색상을 변경
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (searchTitle.getText().trim().isEmpty()) {
                    searchTitle.setText("          원하는 미션방을 검색할 수 있어요!");
                    searchTitle.setForeground(SystemColor.controlShadow); // 포커스를 잃으면 다시 초기 텍스트와 색상을 설정
                }
            }
        });
        searchTitle.setBounds(39, 12, 866, 59);
        groupRecruitment.add(searchTitle);
        
        searchTitle.setOpaque(false); // 배경을 투명하게 설정
        searchTitle.setForeground(SystemColor.controlShadow);
        searchTitle.setFont(customFont.deriveFont(Font.BOLD,18));
        searchTitle.setBorder(null);
        searchTitle.setColumns(10);
        groupRecruitment.add(missionRoomCreate);
        
        searchTitle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performSearch();
            }
        });
        
        JLabel searchCategory = new JLabel("카테고리 선택");
        searchCategory.setBounds(50, 81, 110, 35);
        searchCategory.setFont(customFont.deriveFont(Font.BOLD,18));
        groupRecruitment.add(searchCategory);
        
        comboBox = new JComboBox<>();
        comboBox.setBounds(180, 82, 121, 35);
        comboBox.setModel(new DefaultComboBoxModel(new String[] {"선택하기", "다이어트", "챌린지", "스터디", "기타"}));
        comboBox.setFont(customFont.deriveFont(Font.PLAIN,15));
        comboBox.setBackground(new Color(246, 246, 246));
        comboBox.setForeground(new Color(56, 183, 255));

        comboBox.setBorder(new RoundedBorder(new Color(56, 183, 255), 2, 10));
        comboBox.setRenderer(new CustomComboBoxRenderer());
        comboBox.setUI(new BasicComboBoxUI() {
    	    @Override
    	    public void paintCurrentValueBackground(Graphics g, Rectangle bounds, boolean hasFocus) {
    	        g.setColor(Color.WHITE); // 원하는 배경색으로 변경
    	        g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
    	    }
    	    protected JButton createArrowButton() {
    	        JButton arrowButton = new JButton("▼");
    	        arrowButton.setBackground(new Color(56, 183, 255)); 
    	        arrowButton.setForeground(Color.WHITE);
    	        arrowButton.setBorder(BorderFactory.createEmptyBorder());
    	        arrowButton.setOpaque(true);
    	        return arrowButton;
    	    }
    	});
        groupRecruitment.add(comboBox);
        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performSearch(); // 카테고리 선택 시 검색 수행
            }
        });
        
        JButton backButton = new JButton("");
        backButton.setBackground(new Color(246, 246, 246));
        backButton.setBorderPainted(false);
        backButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("RecruitGroupMember/backButton.png")));
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
        nextButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("RecruitGroupMember/nextButton.png")));
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
     * 그룹 모집 인터페이스에 패널을 추가.
     * @param panel 그룹 모집 인터페이스에 추가될 패널.
     */
	public void addToGroupRecruitment(JPanel panel) {
		allPanels.add(panel);
        if (allPanels.size() <= (currentPage + 1) * PANELS_PER_PAGE) {
            placePanel(panel, allPanels.size() - 1);
        }
    }
	private void performSearch() {
	    String searchText = searchTitle.getText().trim().toLowerCase();
	    if (searchText.equals("원하는 미션방을 검색할 수 있어요!")) {
	        searchText = ""; // 기본 텍스트인 경우 검색 텍스트를	 빈 문자열로 설정합니다.
	    }
	    String selectedCategory = comboBox.getSelectedItem().toString(); // comboBox에서 선택된 카테고리를 가져옵니다.

	    searchList.clear(); // 이전 검색 결과를 지웁니다.

	    for (Group group : GroupManager.getGroupList()) {
	        boolean titleMatches = group.getTitle().toLowerCase().contains(searchText);
	        boolean categoryMatches = selectedCategory.equals("선택하기") || group.getCategory().equals(selectedCategory); // 카테고리가 일치하는지 확인

	        if (titleMatches && categoryMatches) {
	            searchList.add(group); // 검색 조건과 카테고리 모두 일치하는 그룹을 searchList에 추가합니다.
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
	    dynamicPanel.add(panel);
	}
	 class RoundedBorder extends AbstractBorder {
	        private final Color color;
	        private final int thickness;
	        private final int radius;
	        private final Insets insets;
	        private final BasicStroke stroke;

	        public RoundedBorder(Color color, int thickness, int radius) {
	            this.color = color;
	            this.thickness = thickness;
	            this.radius = radius;
	            this.insets = new Insets(thickness, thickness, thickness, thickness + thickness / 2); // Adjust for arrow button width
	            this.stroke = new BasicStroke(thickness);
	        }

	        @Override
	        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
	            Graphics2D g2d = (Graphics2D) g.create();
	            g2d.setColor(color);
	            g2d.setStroke(stroke);
	            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

	            // Draw the rounded border
	            int r = radius;
	            int w = width - thickness;
	            int h = height - thickness;
	            Path2D.Float path = new Path2D.Float();
	            path.moveTo(x + r, y);
	            path.lineTo(x + w - r, y);
	            path.quadTo(x + w, y, x + w, y + r);
	            path.lineTo(x + w, y + h - r);
	            path.quadTo(x + w, y + h, x + w - r, y + h);
	            path.lineTo(x + r, y + h);
	            path.quadTo(x, y + h, x, y + h - r);
	            path.lineTo(x, y + r);
	            path.quadTo(x, y, x + r, y);
	            path.closePath();
	            g2d.draw(path);
	            g2d.dispose();
	        }

	        @Override
	        public Insets getBorderInsets(Component c) {
	            return insets;
	        }

	        @Override
	        public Insets getBorderInsets(Component c, Insets insets) {
	            return getBorderInsets(c);
	        }

	        @Override
	        public boolean isBorderOpaque() {
	            return false;
	        }
	    }
	 class CustomComboBoxRenderer extends DefaultListCellRenderer {
	        @Override
	        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
	            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
	            setHorizontalAlignment(SwingConstants.CENTER); // 텍스트를 가운데로 정렬합니다.
	            setOpaque(true); // 배경색이 보이도록 opaque 값을 true로 설정합니다.
	            if (!isSelected) {
	                setBackground(Color.WHITE); // 선택되지 않았을 때 배경색을 흰색으로 설정합니다.
	                setForeground(Color.BLACK); // 선택되지 않았을 때 글자색을 검은색으로 설정합니다.
	            }
	            return this;
	        }
	    }
}
