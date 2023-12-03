package client.detailMyGroup;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import client.db.ClientDBManager;
public class MakeProgressData {
	DetailMyGroup a;
	int gid;
	
	public HashMap<Integer,String> authDateData = new HashMap<Integer,String>(){{}};
	MakeProgressData(){}
	MakeProgressData(DetailMyGroup t){
		a=t;
		gid = a.gid;
		JSONArray authData = ClientDBManager.getGroupProgress(gid);	
		for(int i=0;i<authData.size();i++) {		
			JSONObject authDataObject = (JSONObject) authData.get(i);
			int uid = Integer.parseInt(authDataObject.get("uid").toString());
			String date = authDataObject.get("date").toString();	
			authDateData.put(uid,date);
		}
	}
}
