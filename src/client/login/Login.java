package client.login;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;

import client.login.signUpPopUp;
class Login extends JFrame{
	
	public static void main(String[] args) {
		Login login = new Login();
	}
	
	//폰트 설정
	Font M = new Font("AppleSDGothicNeoM00",Font.PLAIN,32);
	Font B = new Font("AppleSDGothicNeoB00",Font.PLAIN,32);
	Font id = new Font("AppleSDGothicNeoM00",Font.PLAIN,24);
	
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
		ImageIcon image = new ImageIcon("C:\\Users\\binwo\\Desktop\\jiwon\\2학년2학기\\소프트웨어시스템설계\\로고.png");
		
		JLabel imageLabel = new JLabel(image);
		imageLabel.setBounds(374,45,450,275);
		panel.setLayout(null);
		
		panel.add(imageLabel);
		
		/*
		 텍스트 설정
		 */
		JLabel textLabel=new JLabel("다같이 재밌게 하는 목표달성!");
		textLabel.setBounds(416,305,400,90);
		
		textLabel.setFont(M);
		textLabel.setForeground(Color.white);
		
		textLabel.setOpaque(false);
		
		panel.add(textLabel);
		
		JLabel login=new JLabel("로그인");
		login.setBounds(553,365,95,90);
		
		login.setFont(B);
		login.setForeground(Color.white);
		
		login.setOpaque(false);
		
		panel.add(login);
		
		/*
		 로그인 창 구현
		 */
		
		JLabel idLabel = new JLabel("ID");
		idLabel.setBounds(405,445,22,31);
		panel.add(idLabel);
		
		RoundCornerTextField idTextField = new RoundCornerTextField(20);
        idTextField.setBackground(new Color(255, 255, 255));
        idTextField.setBounds(385, 489, 428, 54); // 아이디 입력 필드의 위치와 크기 설정
        panel.add(idTextField);

        JLabel passwordLabel = new JLabel("PW");
        passwordLabel.setBounds(399, 556, 34, 31); // 비밀번호 레이블의 위치와 크기 설정
        panel.add(passwordLabel);

        
        JPasswordField passwordField = new JPasswordField(20);
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
                su.signUpPopUp();
            }
        });
        
        panel.add(signUpButton);
        
        ImageIcon loginButtonImage = new ImageIcon("C:\\Users\\binwo\\Desktop\\jiwon\\2학년2학기\\소프트웨어시스템설계\\component\\로그인하기.png");
        JButton loginButton = new JButton(loginButtonImage);
        loginButton.setContentAreaFilled(false);
        loginButton.setBorderPainted(false);
        loginButton.setBounds(386, 730, 428, 60); // 로그인 버튼의 위치와 크기 설정
        
        panel.add(loginButton);
        
       
		idLabel.setFont(id);
		idLabel.setForeground(Color.white);
		passwordLabel.setFont(id);
		passwordLabel.setForeground(Color.white);
		
		
		
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
}

