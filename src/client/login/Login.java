package client.login;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;

import client.login.signUpPopUp;
public class Login extends JFrame{
	
	private RoundCornerTextField idTextField;
	private JPasswordField passwordField;
	private JLabel loginWarning;
	
	public static void main(String[] args) {
		Login login = new Login();
		
	}
	
	public Login() {
		/*
		 전체 화면 설정
		 */
		JFrame frame=new JFrame("전체화면");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1200,850);
		
		JPanel panel = new JPanel();
		Color backgroundColor = new Color(56,183,255);
		panel.setBackground(backgroundColor);
		
		frame.add(panel);
		
		/*
		 이미지 추가
		 */
		ImageIcon image = new ImageIcon("./resource/login/로고.png");
		
		JLabel imageLabel = new JLabel(image);
		imageLabel.setBounds(374,45,450,275);
		panel.setLayout(null);
		
		panel.add(imageLabel);
		
		/*
		 텍스트 설정
		 */
		JLabel textLabel=new JLabel("다같이 재밌게 하는 목표달성!");
		textLabel.setBounds(416,305,400,90);
		textLabel.setFont(new Font("AppleSDGothicNeoM00", Font.PLAIN,32));
		textLabel.setForeground(Color.white);
		textLabel.setOpaque(false);
		panel.add(textLabel);
		
		
		JLabel login=new JLabel("로그인");
		login.setBounds(553,365,95,90);
		login.setFont(new Font("AppleSDGothicNeoB00", Font.PLAIN,32));
		login.setForeground(Color.white);
		login.setOpaque(false);
		panel.add(login);
		
		/*
		 로그인 창 구현
		 */
		
		JLabel idLabel = new JLabel("ID");
		idLabel.setBounds(405,445,22,31);
		idLabel.setFont(new Font("AppleSDGothicNeoM00", Font.PLAIN,24));
		idLabel.setForeground(Color.white);
		panel.add(idLabel);
		
		idTextField = new RoundCornerTextField(20);
        idTextField.setBackground(new Color(255, 255, 255));
        idTextField.setBounds(385, 489, 428, 54); // 아이디 입력 필드의 위치와 크기 설정
        panel.add(idTextField);

        JLabel passwordLabel = new JLabel("PW");
        passwordLabel.setBounds(399, 556, 34, 31); // 비밀번호 레이블의 위치와 크기 설정
        passwordLabel.setFont(new Font("AppleSDGothicNeoM00", Font.PLAIN,24));
		passwordLabel.setForeground(Color.white);
        panel.add(passwordLabel);

        
        passwordField = new JPasswordField(20);
        passwordField.setBounds(385, 600, 428, 54); // 비밀번호 입력 필드의 위치와 크기 설정
        panel.add(passwordField);
        
        JButton signUpButton = new JButton("회원가입");
        signUpButton.setFont(new Font("AppleSDGothicNeoM00", Font.PLAIN, 16));
        signUpButton.setBounds(572, 665, 60, 21); // 회원가입 버튼의 위치와 크기 설정
        signUpButton.setMargin(new Insets(0, 0, 0, 0));
        signUpButton.setContentAreaFilled(false);
        signUpButton.setBorderPainted(false);
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 버튼이 클릭되었을 때 수행할 동작
                signUpPopUp su = new signUpPopUp(); // 팝업 다이얼로그 표시
                su.createSignPopUp();
            }
        });
        
        panel.add(signUpButton);
        
        /*
         아이디 비밀번호 미입력시 경고 표시
         */
        loginWarning = new JLabel("아이디 또는 비밀번호를 입력해주세요");
        loginWarning.setBounds(476,683,300,48);
        loginWarning.setFont(new Font("AppleSDGothicNeoM00", Font.PLAIN, 18));
        loginWarning.setForeground(Color.red);
        loginWarning.setVisible(false);
        panel.add(loginWarning);
        
        JLabel loginMatchWarning = new JLabel("아이디 또는 비밀번호가 틀렸습니다.%n 다시 입력해주세요.");
        loginMatchWarning.setBounds(476,683,300,48);
        loginMatchWarning.setFont(new Font("AppleSDGothicNeoM00", Font.PLAIN, 18));
        loginMatchWarning.setForeground(Color.red);
        loginMatchWarning.setVisible(false);
        panel.add(loginMatchWarning);
        
        
        
        /*
         로그인 버튼
         */
        ImageIcon loginButtonImage = new ImageIcon("./resource/login/로그인하기.png");
        JButton loginButton = new JButton(loginButtonImage);
        loginButton.setContentAreaFilled(false);
        loginButton.setBorderPainted(false);
        loginButton.setBounds(386, 730, 428, 60); // 로그인 버튼의 위치와 크기 설정
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 로그인 버튼이 클릭되었을 때 수행할 동작
            	String id=idTextField.getText();
            	char[] password=passwordField.getPassword();
            	String pw=new String(password);
            	
            	checkValid();
            }
        });
        panel.add(loginButton);
		
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	
	private void checkValid() {
		//아이디 비밀번호 입력 되었는지 확인하는 메소드
		if(idTextField.getText().trim().length()==0||passwordField.getPassword().length==0) {
			loginWarning.setVisible(true); 
		}
		else
			loginWarning.setVisible(false); 
	}
	
	private boolean login(String id, String pw) {
		return false; //서버에서 로그인 성공 정보 받아오는메소드
	}
	/*
	void acceptData(Hashmap <int, int>) {
		;
	}
	*/
	
}

