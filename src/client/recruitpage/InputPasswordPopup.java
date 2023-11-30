package client.recruitpage;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JPasswordField;

public class InputPasswordPopup {

    JFrame frame;
    protected RoundedPanel inputPasswordPanel;
    private Group group; // Group 객체 참조
    private JPasswordField passwordField; // 비밀번호 필드
    private JLabel warningLabel; // 경고 메시지 레이블

    public InputPasswordPopup(Group group) {
        this.group = group; // Group 객체 할당
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setSize(355, 240);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        inputPasswordPanel = new RoundedPanel(20);
        inputPasswordPanel.setBounds(0, 0, 339, 199);
        inputPasswordPanel.setForeground(new Color(255, 255, 255));
        inputPasswordPanel.setBackground(new Color(255, 255, 255));
        frame.getContentPane().add(inputPasswordPanel);
        inputPasswordPanel.setLayout(null);

        JLabel passwordLabel = new JLabel("비밀번호 입력");
        passwordLabel.setFont(new Font("나눔고딕", Font.BOLD, 18));
        passwordLabel.setBounds(115, 31, 110, 35);
        inputPasswordPanel.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(13, 74, 314, 35);
        passwordField.setBackground(new Color(237, 237, 237));
        passwordField.setBorder(null);
        inputPasswordPanel.add(passwordField);

        JButton enterButton = new JButton("");
        enterButton.setIcon(new ImageIcon("./resource/RecruitGroupMember/참여하기.png"));
        enterButton.setBounds(115, 150, 110, 38);
        enterButton.setBackground(new Color(255, 255, 255));
        enterButton.setBorderPainted(false);
        inputPasswordPanel.add(enterButton);

        warningLabel = new JLabel("*비밀번호가 일치하지 않습니다.");
        warningLabel.setFont(new Font("나눔고딕", Font.PLAIN, 16));
        warningLabel.setForeground(Color.RED);
        warningLabel.setBounds(60, 113, 219, 24);
        warningLabel.setVisible(false); // 기본적으로 보이지 않도록 설정
        inputPasswordPanel.add(warningLabel);

        // Enter 버튼에 액션 리스너 추가
        enterButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 입력된 비밀번호 확인
                String enteredPassword = new String(passwordField.getPassword());

                // 비밀번호 검증
                if (enteredPassword.equals(group.getRoomPassword())) {
                    // 비밀번호가 일치하는 경우
                    warningLabel.setVisible(false);
                    // 여기에 비밀번호가 맞을 때 수행할 로직 추가
                } else {
                    // 비밀번호가 일치하지 않거나 입력되지 않은 경우
                    warningLabel.setVisible(true);
                }
            }
        });
    }
}
