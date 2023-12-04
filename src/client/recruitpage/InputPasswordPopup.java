package client.recruitpage;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

import org.json.simple.JSONObject;

import client.CustomFont;
import client.net.ClientSocket;
import server.service.Request;
import server.service.RequestType;

import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JPasswordField;

/**
 * 비밀번호 입력 팝업 창을 관리하는 클래스.
 * 이 클래스는 사용자가 그룹에 참여하기 위해 필요한 비밀번호를 입력하고, 
 * 해당 비밀번호를 서버에 전송하여 검증하는 기능을 제공한다.
 * 사용자는 이 팝업을 통해 그룹 참여 시 필요한 인증 절차를 수행할 수 있다.
 */
public class InputPasswordPopup {

    JFrame frame;
    protected RoundedPanel inputPasswordPanel;
    private Group group; // Group 객체 참조
    private JPasswordField passwordField; // 비밀번호 필드
    private JLabel warningLabel; // 경고 메시지 레이블
    CustomFont customFont = new CustomFont();
    
    /**
     * 그룹 참여를 위한 비밀번호 입력 팝업의 생성자.
     * @param group 참여하려는 그룹 객체
     */
    public InputPasswordPopup(Group group) {
        this.group = group; // Group 객체 할당
        initialize();
    }

    /**
     * 팝업 창의 UI 구성 요소를 초기화하고 설정하는 메소드.
     * 이 메소드는 팝업 창의 크기, 배경색, 레이아웃을 설정하고,
     * 비밀번호 입력 필드, 경고 레이블, 버튼 등의 컴포넌트를 추가한다.
     * 또한, 버튼에 대한 이벤트 리스너를 설정하여 사용자의 입력을 처리한다.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setSize(339, 199);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null);
		frame.setUndecorated(true);
		
		// 둥근 모서리의 패널을 생성하고 설정
        inputPasswordPanel = new RoundedPanel(20);
        inputPasswordPanel.setBounds(0, 0, 339, 199);
        inputPasswordPanel.setForeground(new Color(255, 255, 255));
        inputPasswordPanel.setBackground(new Color(255, 255, 255));
        frame.getContentPane().add(inputPasswordPanel);
        inputPasswordPanel.setLayout(null);

        // 비밀번호 레이블 설정
        JLabel passwordLabel = new JLabel("비밀번호 입력");
        passwordLabel.setFont(customFont.deriveFont(Font.BOLD,18));
        passwordLabel.setBounds(115, 31, 110, 35);
        inputPasswordPanel.add(passwordLabel);

        // 비밀번호 입력 필드 설정
        passwordField = new JPasswordField();
        passwordField.setBounds(13, 74, 314, 35);
        passwordField.setBackground(new Color(237, 237, 237));
        passwordField.setBorder(null);
        inputPasswordPanel.add(passwordField);

        // 경고 레이블 설정
        warningLabel = new JLabel("*비밀번호가 일치하지 않습니다.");
        warningLabel.setFont(customFont.deriveFont(Font.PLAIN,16));
        warningLabel.setForeground(Color.RED);
        warningLabel.setBounds(60, 113, 219, 24);
        warningLabel.setVisible(false); // 기본적으로 보이지 않도록 설정
        inputPasswordPanel.add(warningLabel);

        /**
         * Exit button: 버튼을 클릭하면 팝업 창이 닫히는 기능을 수행한다.
         * 버튼에는 'miniExit' 아이콘이 사용되며, 투명 배경, 테두리 없음, 포커스 테두리 제거 설정이 적용되어 있다.
         */
        JButton exitButton = new JButton("");
    	exitButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("RecruitGroupMember/miniExit.png")));
	    exitButton.setBounds(303, 5, 30, 30);
	    exitButton.setOpaque(false);
	    exitButton.setContentAreaFilled(false);
	    exitButton.setBorderPainted(false);
	    exitButton.setFocusPainted(false); // 포커스 테두리 제거
    	inputPasswordPanel.add(exitButton);
    	
    	exitButton.addActionListener(new ActionListener() {
    	    public void actionPerformed(ActionEvent e) {
    	        frame.dispose(); // 현재 프레임을 닫음
    	    }
    	});
    	
    	/**
         * Enter button: '참여하기' 버튼으로, 클릭하면 입력된 비밀번호를 검증하고 그룹 참여 요청을 서버에 전송한다.
         * 버튼에는 '참여하기' 아이콘이 사용되며, 배경색 및 테두리 없음 설정이 적용되어 있다.
         */
    	JButton enterButton = new JButton("");
        enterButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("RecruitGroupMember/참여하기.png")));
        enterButton.setBounds(115, 150, 110, 38);
        enterButton.setBackground(new Color(255, 255, 255));
        enterButton.setBorderPainted(false);
        inputPasswordPanel.add(enterButton);
        // Enter 버튼에 액션 리스너 추가
        enterButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	// 입력된 비밀번호를 String 형태로 가져온다.
                String enteredPassword = new String(passwordField.getPassword());

                // 서버에 전송할 데이터를 JSONObject 형태로 구성한다.
                JSONObject data = new JSONObject();
                data.put("gid", group.getGid());
                data.put("password", enteredPassword);
                
                // 서버에 그룹 참여 요청을 전송한다.
                Request request = new Request(RequestType.ENTERGROUP, data);
                ClientSocket.send(request);
               
                // 서버로부터의 응답에 따라 액션을 수행한다.
                if(ClientSocket.getResult()) {
                	// 참여 성공 시, 그룹 목록을 새로고침하고 팝업을 닫는다.
                    warningLabel.setVisible(false);
                    MyGroupList myGroupList = new MyGroupList(true);
                    myGroupList.refreshGroupList();
                    frame.dispose();
                    }
                    
                 else {
                	// 비밀번호가 일치하지 않는 경우, 경고 레이블을 보이게 한다.
                    warningLabel.setVisible(true);
                }
            }
        });
    }
}
