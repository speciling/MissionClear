package client.DetailMyGroup;

import java.awt.*;
import java.io.File;
import java.util.Calendar;

import javax.swing.*;

import client.MainPage.MainPage;

public class DetailMyGroup extends MainPage {
	private static final char[] savePath = null;
	String missionName = "돈 아껴서 부자되자!";
	String missionInfo = "매일 매일 사용한 돈 인증";
	
	
	
	public DetailMyGroup(boolean vis){//, String id) {
		// id를 통해서 사용자 이름, 프로필 사진을 가져오기 
		// 세부 방정보도 가져오기
		super(vis);
		
		//임시날짜
		Calendar missionStart = Calendar.getInstance();
		missionStart.set(Calendar.YEAR, 2023);
		missionStart.set(Calendar.MONTH, Calendar.NOVEMBER);
		missionStart.set(Calendar.DAY_OF_MONTH, 12);
		Calendar missionEnd = Calendar.getInstance();
		missionEnd.set(Calendar.YEAR, 2023);
		missionEnd.set(Calendar.MONTH, Calendar.NOVEMBER);
		missionEnd.set(Calendar.DAY_OF_MONTH, 19);
		
		
		
		MainPage mp = new MainPage(true);
		//globPan = mp.globPan;
		
		
		JPanel g = mp.globPan;
		//배너
		g.setLayout(null);
		JPanel banner = new JPanel();
		banner.setLayout(null);
		banner.setBackground(new Color(220,243,255));
		banner.setBounds(0,0,946,202);
		
		JPanel missionNameP = new JPanel();
		missionNameP.setBackground(new Color(220,243,255));
		missionNameP.setLayout(new BorderLayout());
		JLabel missionNameL = new JLabel(missionName);
		missionNameL.setFont(new Font("나눔고딕",Font.BOLD, 35));	
		missionNameP.add(missionNameL);
		missionNameP.setBounds(30,30,870,60);
		banner.add(missionNameP);
		
		JPanel missionInfoP = new JPanel();
		missionInfoP.setBackground(new Color(220,243,255));
		String missionInfoT = "<html>"+"활동기간: "+missionStart.get(Calendar.YEAR)+". "+(missionStart.get(Calendar.MONTH)+1)+". "+missionStart.get(Calendar.DAY_OF_MONTH)
				+ " ~ " + missionEnd.get(Calendar.YEAR)+". "+(missionEnd.get(Calendar.MONTH)+1)+". "+missionEnd.get(Calendar.DAY_OF_MONTH) + " <br>"
				+ "활동내용: "+missionInfo+"</html>";
		JLabel missionInfoL = new JLabel(missionInfoT);
		missionInfoP.setLayout(new BorderLayout());
		missionInfoL.setFont(new Font("나눔고딕", Font.BOLD, 20));
		missionInfoP.add(missionInfoL);
		missionInfoP.setBounds(30,100,870,70);
		banner.add(missionInfoP);
		
		
		g.add(banner);
		
		JPanel ff = new JPanel();
		ff.setLayout(null);
		ff.setBackground(new Color(246,246,246));
		ff.setBounds(0,202,946,850-202);
		g.add(ff);
		
		
		JPanel authMissionP = new JPanel();
		ImageIcon authMissionIcon = new ImageIcon("./resource/DetailMyGroup/Frame 12.png");
		JButton authMission = new JButton(authMissionIcon);
		authMission.setBackground(new Color(246,246,246));
		authMission.setBorderPainted(false);
		authMissionP.setBounds(20,10,455,75);
		authMissionP.setBackground(new Color(246,246,246));
		authMissionP.add(authMission);
		ff.add(authMissionP);
		
		//String savePath=null;// 해당파일의 경로가 저장되는 변수
		/*
		authMission.addActionListener(event -> {
			JFileChooser jfc = new JFileChooser();
			jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			jfc.showDialog(this, null);
			File dir = jfc.getSelectedFile();
			String savePath = ((dir!=null)?dir.getPath():"");
		});
		System.out.println(savePath);
		
		
		*/
		
		
		
		
		setVisible(false);
	}
	
	
	
	
	
}

