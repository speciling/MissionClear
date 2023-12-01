package client.detailMyGroup;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import org.json.simple.*;

import client.db.ClientDBManager;

public class MakeUserData {
	public HashMap<Integer,String> nicknames = new HashMap<Integer,String>(){{}};
	public HashMap<Integer,String> pfps = new HashMap<Integer,String>(){{}};
	int gid=2;
	List<Integer> uids = new Vector<Integer>();
	
	
	public MakeUserData() {
		System.out.println(11111);
		JSONArray usersData = ClientDBManager.getGroupUsers(gid);
		System.out.println(usersData);
		for(int i=0;i<usersData.size();i++) {		
			JSONObject user = (JSONObject) usersData.get(i);		
			int uid = Integer.parseInt(user.get("uid").toString());	
			System.out.println(uid);
			
			uids.add(uid);
			String nickname = user.get("nickname").toString();		
			
			System.out.println(nickname);
			nicknames.put(uid,nickname);	
			String pfp = user.get("pfp").toString();
			if(pfp.equals("")) {pfp = "./resource/MainPage/defaultPic.png";}
			System.out.println(pfp);
			pfps.put(uid,pfp);
		}
	}
}