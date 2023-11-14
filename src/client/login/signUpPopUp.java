package client.login;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Dialog.ModalExclusionType;
import java.awt.Color;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.DropMode;

public class signUpPopUp {

	private JFrame frame;
	private JTextField idTextField;
	private JTextField nicknameTextField;
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;

	/**
	 * @wbp.parser.entryPoint
	 */
	public void signUpPopUp() {
		frame = new JFrame();
		frame.setBackground(new Color(255, 255, 255));
		frame.setBounds(353, 132, 550, 609);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255));
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel signUpTitle = new JLabel("회원가입");
		signUpTitle.setFont(new Font("AppleSDGothicNeoB00", Font.PLAIN, 40));
		signUpTitle.setBounds(205, 49, 139, 35);
		panel.add(signUpTitle);
		
		JPanel idInputPanel = new JPanel();
		idInputPanel.setBackground(new Color(255, 255, 255));
		idInputPanel.setBounds(39, 108, 473, 90);
		panel.add(idInputPanel);
		idInputPanel.setLayout(null);
		
		JLabel idInput = new JLabel("아이디 입력");
		idInput.setFont(new Font("AppleSDGothicNeoM00", Font.PLAIN, 22));
		idInput.setBounds(0, 0, 117, 35);
		idInput.setHorizontalAlignment(SwingConstants.CENTER);
		idInputPanel.add(idInput);
		
		idTextField = new JTextField();
		idTextField.setBackground(new Color(237, 237, 237));
		idTextField.setBounds(0, 35, 461, 41);
		idInputPanel.add(idTextField);
		idTextField.setColumns(10);
		
		JPanel pwInputPanel = new JPanel();
		pwInputPanel.setBackground(new Color(255, 255, 255));
		pwInputPanel.setBounds(39, 196, 473, 125);
		panel.add(pwInputPanel);
		pwInputPanel.setLayout(null);
		
		JLabel pwInput = new JLabel("비밀번호 입력");
		pwInput.setFont(new Font("AppleSDGothicNeoM00", Font.PLAIN, 22));
		pwInput.setBounds(0, 0, 131, 35);
		pwInputPanel.add(pwInput);
		
		JLabel pwRule = new JLabel("- 16자 이내 영어, 숫자, 특수문자 조합으로 입력");
		pwRule.setFont(new Font("AppleSDGothicNeoM00", Font.PLAIN, 15));
		pwRule.setBounds(0, 96, 295, 15);
		pwInputPanel.add(pwRule);
		
		passwordField = new JPasswordField();
		passwordField.setColumns(16);
		passwordField.setBackground(new Color(237, 237, 237));
		passwordField.setBounds(0, 45, 461, 41);
		pwInputPanel.add(passwordField);
		
		JPanel pwCheckPanel = new JPanel();
		pwCheckPanel.setLayout(null);
		pwCheckPanel.setBackground(Color.WHITE);
		pwCheckPanel.setBounds(39, 318, 473, 90);
		panel.add(pwCheckPanel);
		
		JLabel pwCheck = new JLabel("비밀번호 확인");
		pwCheck.setHorizontalAlignment(SwingConstants.LEFT);
		pwCheck.setFont(new Font("AppleSDGothicNeoM00", Font.PLAIN, 22));
		pwCheck.setBounds(0, 0, 144, 35);
		pwCheckPanel.add(pwCheck);
		
		passwordField_1 = new JPasswordField();
		passwordField_1.setColumns(16);
		passwordField_1.setBackground(new Color(237, 237, 237));
		passwordField_1.setBounds(0, 39, 461, 41);
		pwCheckPanel.add(passwordField_1);
		
		JPanel nicknamePanel = new JPanel();
		nicknamePanel.setLayout(null);
		nicknamePanel.setBackground(Color.WHITE);
		nicknamePanel.setBounds(39, 415, 473, 90);
		panel.add(nicknamePanel);
		
		JLabel nicknameInput = new JLabel("닉네임 입력");
		nicknameInput.setHorizontalAlignment(SwingConstants.LEFT);
		nicknameInput.setFont(new Font("AppleSDGothicNeoM00", Font.PLAIN, 22));
		nicknameInput.setBounds(0, 0, 144, 35);
		nicknamePanel.add(nicknameInput);
		
		nicknameTextField = new JTextField();
		nicknameTextField.setColumns(10);
		nicknameTextField.setBackground(new Color(237, 237, 237));
		nicknameTextField.setBounds(0, 35, 461, 41);
		nicknamePanel.add(nicknameTextField);
		
		JButton signUpButton = new JButton("");
		signUpButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = idTextField.getText();
                char[] password = passwordField.getPassword();

                // 여기에서 입력된 아이디와 비밀번호를 처리
                // 예를 들어, 데이터베이스에 저장하거나 다른 작업 수행

                JOptionPane.showMessageDialog(frame, "회원가입이 완료되었습니다.", "회원가입 완료", JOptionPane.INFORMATION_MESSAGE);
                frame.dispose(); // 팝업 창 닫기
			}
		});
		signUpButton.setIcon(new ImageIcon(signUpPopUp.class.getResource("/signup/SignUpButton.png")));
		signUpButton.setBounds(208, 513, 133, 51);
		signUpButton.setContentAreaFilled(false);
        signUpButton.setBorderPainted(false);
		panel.add(signUpButton);
		
		frame.getContentPane().add(panel, BorderLayout.CENTER);
        //frame.add(SignUpButton, BorderLayout.SOUTH);
        //frame.pack();
        frame.setVisible(true);
	}
	
}