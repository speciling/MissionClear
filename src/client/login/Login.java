package client.login;

import java.awt.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;

import client.login.signUpPopUp;
import client.recruitpage.RecruitGroupMember;

/**
 * 
  This class represents the login screen where users can log in by entering their username and password.
 */
public class Login extends JFrame{
	
	public static void main(String [] args) {
		  Login login = new Login();
		  login.loginpage();
	   }
	   
	private RoundCornerTextField idTextField;
	private JPasswordField passwordField;
	private JLabel loginWarning;
	
	  /**
     * loginpage 띄우는 메소드
     * Initializes GUI components and sets up the screen.
     */
	public void loginpage() {
		/**
		 frame setting
		 */
		JFrame frame=new JFrame("전체화면");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1200,850);
		
		JPanel panel = new JPanel();
		Color backgroundColor = new Color(56,183,255);
		panel.setBackground(backgroundColor);
		
		frame.add(panel);
		
		/**
		 image append
		 */
		ImageIcon image = new ImageIcon("./resource/login/로고.png");
		
		JLabel imageLabel = new JLabel(image);
		imageLabel.setBounds(374,45,450,275);
		panel.setLayout(null);
		
		panel.add(imageLabel);
		
		/**
		 text setting
		 */
		JLabel textLabel=new JLabel("다같이 재밌게 하는 목표달성!");
		textLabel.setBounds(416,305,420,100);
		textLabel.setFont(new Font("나눔고딕", Font.BOLD,32));
		textLabel.setForeground(Color.white);
		textLabel.setOpaque(false);
		panel.add(textLabel);
		
		
		JLabel login=new JLabel("로그인");
		login.setBounds(553,365,95,90);
		login.setFont(new Font("나눔고딕", Font.BOLD,32));
		login.setForeground(Color.white);
		login.setOpaque(false);
		panel.add(login);
		
		/**
		 login
		 */
		
		JLabel idLabel = new JLabel("ID");
		idLabel.setBounds(405,445,30,35);
		idLabel.setFont(new Font("나눔고딕", Font.BOLD,24));
		idLabel.setForeground(Color.white);
		panel.add(idLabel);
		
		/** id field text setting*/
		idTextField = new RoundCornerTextField(20);
        idTextField.setBackground(new Color(255, 255, 255));
        idTextField.setBounds(385, 489, 428, 54); 
        panel.add(idTextField);

        /** password field text setting*/
        JLabel passwordLabel = new JLabel("PW");
        passwordLabel.setBounds(399, 556, 40, 35); 
        passwordLabel.setFont(new Font("나눔고딕", Font.PLAIN,24));
		passwordLabel.setForeground(Color.white);
        panel.add(passwordLabel);

        
        passwordField = new JPasswordField(20);
        passwordField.setBounds(385, 600, 428, 54); /** password input text setting*/
        panel.add(passwordField);
        
        JButton signUpButton = new JButton("회원가입");
        signUpButton.setFont(new Font("나눔고딕", Font.PLAIN, 16));
        signUpButton.setBounds(572, 665, 70, 30); /** signup button*/
        signUpButton.setMargin(new Insets(0, 0, 0, 0));
        signUpButton.setContentAreaFilled(false);
        signUpButton.setBorderPainted(false);
        /** button click event*/
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	/** popup dialog method*/
                signUpPopUp su = new signUpPopUp(); 
                su.createSignPopUp();
            }
        });
        
        panel.add(signUpButton);
        
        /**
         id, pw warning
         */
        loginWarning = new JLabel("아이디 또는 비밀번호를 입력해주세요");
        loginWarning.setBounds(476,683,300,50);
        loginWarning.setFont(new Font("나눔고딕", Font.PLAIN, 18));
        loginWarning.setForeground(Color.red);
        loginWarning.setVisible(false);
        panel.add(loginWarning);
        
        JLabel loginMatchWarning = new JLabel("아이디 또는 비밀번호가 틀렸습니다.%n 다시 입력해주세요.");
        loginMatchWarning.setBounds(476,683,300,48);
        loginMatchWarning.setFont(new Font("나눔고딕", Font.PLAIN, 25));
        loginMatchWarning.setForeground(Color.red);
        loginMatchWarning.setVisible(false);
        panel.add(loginMatchWarning);
        
        
        
        /**
         login button
         */
        ImageIcon loginButtonImage = new ImageIcon("./resource/login/로그인하기.png");
        JButton loginButton = new JButton(loginButtonImage);
        loginButton.setContentAreaFilled(false);
        loginButton.setBorderPainted(false);
        /** login button size*/
        loginButton.setBounds(386, 730, 428, 60); 
        /** login button click event*/
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	String id=idTextField.getText();
            	char[] passwordChars=passwordField.getPassword();
            	String password=new String(passwordChars);
            	
            	checkValid();
            }
        });
        panel.add(loginButton);
		
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	/**
     * id, pw valid check method
     */
	private void checkValid() {
		/**id, pw check*/
		if(idTextField.getText().trim().length()==0||passwordField.getPassword().length==0) {
			loginWarning.setVisible(true); 
		}
		else
			loginWarning.setVisible(false);
	}
	/**
     * try login and return success
     */
	private boolean login(String id, String pw) {
		return false;
	}
	/*
	void acceptData(Hashmap <int, int>) {
		;
	}
	*/
	
}

