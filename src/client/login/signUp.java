package client.login;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class signUp {
	private JFrame frame; 
	public void signUp() {
		
        frame = new JFrame("회원가입");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        JLabel usernameLabel = new JLabel("아이디:");
        JTextField usernameField = new JTextField(20);
        JLabel passwordLabel = new JLabel("비밀번호:");
        JPasswordField passwordField = new JPasswordField(20);
        panel.setLayout(new GridLayout(2, 2));
        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);

        JButton signUpButton = new JButton("회원가입");
        signUpButton.setBounds(572,665,58,21);
        signUpButton.setMargin(new Insets(0, 0, 0, 0));
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                char[] password = passwordField.getPassword();

                // 여기에서 입력된 아이디와 비밀번호를 처리할 수 있습니다.
                // 예를 들어, 데이터베이스에 저장하거나 다른 작업 수행

                JOptionPane.showMessageDialog(frame, "회원가입이 완료되었습니다. 사용자 이름: " + username, "회원가입 완료", JOptionPane.INFORMATION_MESSAGE);
                frame.dispose(); // 팝업 창 닫기
            }
        });

        frame.add(panel, BorderLayout.CENTER);
        frame.add(signUpButton, BorderLayout.SOUTH);
        frame.pack();
        frame.setVisible(true);
    }
}
