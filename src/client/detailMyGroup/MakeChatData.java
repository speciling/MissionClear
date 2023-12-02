package client.detailMyGroup;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import client.db.ClientDBManager;

public class MakeChatData {
	public List<Integer> chatids = new Vector<Integer>();
	public HashMap<Integer,Integer> uid = new HashMap<Integer,Integer>(){{}};
	public HashMap<Integer,String> message = new HashMap<Integer,String>(){{}};
	public HashMap<Integer,Integer> isPic = new HashMap<Integer,Integer>(){{}};
	//int gid=2;
	
	public MakeChatData() {}
	public MakeChatData(int gid) {
		JSONArray chatData = ClientDBManager.getChatData(gid);	
		for(int i=0;i<chatData.size();i++) {		
			JSONObject chat = (JSONObject) chatData.get(i);
			int chatidChat = Integer.parseInt(chat.get("chatid").toString());
			chatids.add(chatidChat);
			
			int uidChat = Integer.parseInt(chat.get("uid").toString());	
			String messageChat = chat.get("message").toString();	
			int isPicChat = Integer.parseInt(chat.get("isPic").toString());	
			
			uid.put(chatidChat,uidChat);
			message.put(chatidChat, messageChat);
			isPic.put(chatidChat, isPicChat);
		}
	}

}
