package client.DetailMyGroup;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import client.MainPage.MainPage;

public class DetailMyGroup extends MainPage {
	public DetailMyGroup(boolean vis){//, String id) {
		// id를 통해서 사용자 이름, 프로필 사진을 가져오기 
		super(vis);
		MainPage mp = new MainPage(true);
		
		JPanel changePanel = mp.changePanel;      // 여기에 DetailMypage 작업하기
			JLabel test = new JLabel();
			ImageIcon ticon = new ImageIcon("./resource/DetailMyGroup/Ellipse3.png");
			test.setIcon(ticon);
			test.setBounds(257, 0, 227, 139);
			//main.add(test);
		changePanel.add(test);
		setVisible(true);
	}
}

