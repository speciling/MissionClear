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
		JSONArray usersData = ClientDBManager.getGroupUsers(gid);
		for(int i=0;i<usersData.size();i++) {		
			JSONObject user = (JSONObject) usersData.get(i);		
			int uid = Integer.parseInt(user.get("uid").toString());	
			uids.add(uid);
			String nickname = user.get("nickname").toString();		
			nicknames.put(uid,nickname);	
			String pfp = user.get("pfp").toString();
			if(pfp.equals("")) {pfp = "./resource/MainPage/defaultPic.png";}
			pfps.put(uid,pfp);
		}
	}
}