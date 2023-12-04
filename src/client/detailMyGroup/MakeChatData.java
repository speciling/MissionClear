package client.detailMyGroup;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import client.db.ClientDBManager;

/**
 * {@code MakeChatData} 클래스는 특정 그룹과 관련된 채팅 데이터를 생성하는 데 책임이 있습니다. 채팅 ID, 사용자 ID,
 * 메시지 및 이미지 여부를 포함합니다.
 */
public class MakeChatData {
	/**
	 * 채팅 ID 목록입니다.
	 */
	public List<Integer> chatids = new Vector<Integer>();
	/**
	 * 채팅 ID에 해당하는 사용자 ID를 매핑하는 맵입니다.
	 */
	public HashMap<Integer, Integer> uid = new HashMap<Integer, Integer>() {
		{
		}
	};
	/**
	 * 채팅 ID에 해당하는 메시지를 매핑하는 맵입니다.
	 */
	public HashMap<Integer, String> message = new HashMap<Integer, String>() {
		{
		}
	};
	/**
	 * 채팅 ID에 해당하는 메세지가 메세지인지 이미지인지 여부를 매핑하는 맵입니다.
	 */
	public HashMap<Integer, Integer> isPic = new HashMap<Integer, Integer>() {
		{
		}
	};

	/**
	 * {@code MakeChatData}의 기본 생성자입니다.
	 */
	public MakeChatData() {
	}

	/**
	 * 특정 그룹 ID를 기반으로 채팅 데이터를 초기화하는 {@code MakeChatData} 생성자입니다.
	 *
	 * @param gid 채팅 데이터를 검색할 그룹 ID입니다.
	 */
	public MakeChatData(int gid) {
		// 데이터베이스에서 지정된 그룹 ID에 대한 채팅 데이터를 검색합니다.
		JSONArray chatData = ClientDBManager.getChatData(gid);
		// 채팅 데이터를 반복하며 관련 데이터 구조를 채웁니다.
		for (int i = 0; i < chatData.size(); i++) {
			// JSON 데이터에서 채팅 정보를 추출합니다.
			JSONObject chat = (JSONObject) chatData.get(i);
			int chatidChat = Integer.parseInt(chat.get("chatId").toString());
			chatids.add(chatidChat);

			int uidChat = Integer.parseInt(chat.get("uid").toString());
			String messageChat = chat.get("message").toString();
			int isPicChat = Integer.parseInt(chat.get("isPic").toString());

			uid.put(chatidChat, uidChat);
			message.put(chatidChat, messageChat);
			isPic.put(chatidChat, isPicChat);
		}
	}

}
