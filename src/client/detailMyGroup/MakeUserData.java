package client.detailMyGroup;

import java.util.HashMap;

import org.json.simple.*;

import client.db.*;

public class MakeUserData {
	HashMap<Integer,String> nicknames = new HashMap<Integer,String>(){{}};
	HashMap<Integer,String> pfps = new HashMap<Integer,String>(){{}};
	int gid;
	JSONArray usersData = ClientDBManager.getGroupUsers(gid);
	
	for(int i=0;i<userData.size();i++) {
		JSONObject user = (JSONObject) usersData.get(i);
		int uid = Integer.parseInt(user.get("uid").toString());
		String nickname = user.get("nickname").toString();
		nicknames.put(uid,nickname);
		String pfp = user.get("pfp").toString();
		pfps.put(uid,pfp);
	}
}
