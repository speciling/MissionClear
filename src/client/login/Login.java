package client.login;

import java.awt.*;

import client.CustomFont;
import client.MainPage.*;
import client.recruitpage.*;
import org.json.simple.JSONObject;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.RoundRectangle2D;

import client.db.ClientDBManager;
import client.login.signUpPopUp;
import client.net.ClientSocket;
import client.recruitpage.RecruitGroupMember;
import server.db.ResultType;
import server.service.Request;
import server.service.RequestType;
/**
 * 
  로그인 화면 클래스
 */

public class Login extends JFrame{
	   
	private RoundCornerTextField idTextField;
	private RoundCornerPasswordField passwordField;
	private JLabel loginWarning;
	private JLabel loginMatchWarning;
	private JPanel panel;
	private JFrame frame;
	CustomFont customFont = new CustomFont();
	
	  /**
     * loginpage 띄우는 메소드
     * Initializes GUI components and sets up the screen.
     */
	public void loginpage() {

		frame=new JFrame("전체화면");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1200,850);
		frame.setResizable(false);
		
		panel = new JPanel();
		Color backgroundColor = new Color(56,183,255);
		panel.setBackground(backgroundColor);
		
		frame.add(panel);
		ImageIcon image = new ImageIcon(getClass().getClassLoader().getResource("login/로고.png"));
		
		JLabel imageLabel = new JLabel(image);
		imageLabel.setBounds(374,45,450,275);
		panel.setLayout(null);
		
		panel.add(imageLabel);

		JLabel textLabel=new JLabel("다같이 재밌게 하는 목표달성!");
		textLabel.setBounds(405,298,420,90);
		//textLabel.setFont(new Font("나눔고딕",Font.BOLD,32));
		textLabel.setFont(customFont.deriveFont(Font.BOLD,32));
		textLabel.setForeground(Color.white);
		textLabel.setOpaque(false);
		panel.add(textLabel);
		
		
		JLabel login=new JLabel("로그인");
		login.setBounds(553,360,120,100);
		login.setFont(customFont.deriveFont(Font.BOLD,36));
		//login.setFont(new Font("나눔고딕", Font.BOLD,36));
		login.setForeground(Color.white);
		login.setOpaque(false);
		panel.add(login);
		
		JLabel idLabel = new JLabel("ID");
		idLabel.setBounds(392,445,30,35);
		idLabel.setFont(customFont.deriveFont(Font.BOLD,24));
		idLabel.setForeground(Color.white);
		panel.add(idLabel);
		
		idTextField = new RoundCornerTextField(20);
        idTextField.setBackground(new Color(255, 255, 255));
        idTextField.setBounds(385, 489, 428, 54); 
        idTextField.setFont(customFont.deriveFont(Font.PLAIN, 16));
        panel.add(idTextField);

        JLabel passwordLabel = new JLabel("PW");
        passwordLabel.setBounds(392, 556, 50, 50); 
        passwordLabel.setFont(customFont.deriveFont( Font.BOLD,24));
		passwordLabel.setForeground(Color.white);
        panel.add(passwordLabel);

        passwordField = new RoundCornerPasswordField(20);
        passwordField.setBounds(385, 600, 428, 54); /** password input text setting*/
        passwordField.setFont(customFont.deriveFont(Font.PLAIN,16));
        panel.add(passwordField);
        
        JButton signUpButton = new JButton("회원가입");
        signUpButton.setFont(customFont.deriveFont( Font.BOLD, 16));
        signUpButton.setBounds(572, 665, 70, 30); /** signup button*/
        signUpButton.setMargin(new Insets(0, 0, 0, 0));
        signUpButton.setContentAreaFilled(false);
        signUpButton.setBorderPainted(false);
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                signUpPopUp su = new signUpPopUp();
                su.createSignPopUp();
            }
        });
        
        panel.add(signUpButton);
        
        loginWarning = new JLabel("아이디 또는 비밀번호를 입력해주세요");
        loginWarning.setBounds(460,683,300,50);
        loginWarning.setFont(customFont.deriveFont(Font.PLAIN, 18));
        loginWarning.setForeground(Color.red);
        loginWarning.setVisible(false);
        panel.add(loginWarning);
        
        loginMatchWarning = new JLabel("아이디 또는 비밀번호가 틀렸습니다");
        loginMatchWarning.setBounds(470,683,300,50);
        loginMatchWarning.setFont(customFont.deriveFont( Font.PLAIN, 18));
        loginMatchWarning.setForeground(Color.red);
        loginMatchWarning.setVisible(false);
        panel.add(loginMatchWarning);

        ImageIcon loginButtonImage = new ImageIcon(getClass().getClassLoader().getResource("login/로그인하기.png"));
        JButton loginButton = new JButton(loginButtonImage);
        loginButton.setContentAreaFilled(false);
        loginButton.setBorderPainted(false);
        loginButton.setBounds(386, 730, 428, 60);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performLogin();
            }
        });

        panel.add(loginButton);
		idTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    performLogin();
                }
            }
        });
        
        passwordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    performLogin();
                }
            }
        });
        
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}

	/**
	 * @param id
	 * @param password
     * 아이디와 비밀번호가 입력칸에 입력이 되어있으면 true를 반환하고
	 * 아무것도 입력되어 있지 않는다면 false를 반환하는 메소드
     */
	private boolean checkValid(String id,String password) {
		if(id.trim().length()==0||password.length()==0) {
			loginWarning.setVisible(true); 
			return false;
		}
		else {
			loginWarning.setVisible(false);
			return true;
		}
	}
	/**
	 * @param id
	 * @param pw
     * 서버와 연결하여 로그인 성공 정보를 받아오는 메소드
     */
	public boolean login(String id, String pw) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("password", pw);

        ClientSocket.send(new Request(RequestType.LOGIN, jsonObject));
        return ClientSocket.getResult();
    }

	/**
	 * 로그인 시도했을 때 조건에 맞을 시에만 로그인이 되도록 하는 메소드
	 * 조건에 맞지 않으면 경고메시지를 출력함.
	 */
	private void performLogin() {
	    String id = idTextField.getText();
	    char[] passwordChars = passwordField.getPassword();
	    String password = new String(passwordChars);

	    if (checkValid(id, password)) {
	        if (login(id, password)) {
				JSONObject jsonObject = new JSONObject();
				ClientSocket.send(new Request(RequestType.SENDDATA, jsonObject));
				ClientSocket.getResult();
	            MainPage mp = new MainPage(true);
	            mp.changePanel("group");
	            frame.dispose();
	        } else {
	            loginMatchWarning.setVisible(true);
	        }
	    }
	}
	
}

