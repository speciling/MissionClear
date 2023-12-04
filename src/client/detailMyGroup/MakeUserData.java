package client.detailMyGroup;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import org.json.simple.*;

import client.db.ClientDBManager;

/**
 * {@code MakeUserData} 클래스는 특정 그룹과 관련된 사용자 데이터를 생성하는 데 책임이 있습니다. 사용자 ID, 닉네임 및
 * 프로필 사진 URL을 포함합니다.
 */
public class MakeUserData {
	/**
	 * 사용자 ID에 해당하는 닉네임을 매핑하는 맵입니다.
	 */
	public HashMap<Integer, String> nicknames = new HashMap<Integer, String>() {
		{
		}
	};
	/**
	 * 사용자 ID에 해당하는 프로필 사진 URL을 매핑하는 맵입니다.
	 */
	public HashMap<Integer, String> pfps = new HashMap<Integer, String>() {
		{
		}
	};
	/**
	 * 그룹에 속한 사용자 ID 목록입니다.
	 */
	public List<Integer> uids = new Vector<Integer>();

	/**
	 * {@code MakeUserData}의 기본 생성자입니다.
	 */
	public MakeUserData() {
	}

	/**
	 * 특정 그룹 ID를 기반으로 사용자 데이터를 초기화하는 {@code MakeUserData} 생성자입니다.
	 *
	 * @param gid 사용자 데이터를 검색할 그룹 ID입니다.
	 */
	public MakeUserData(int gid) {
		// 데이터베이스에서 지정된 그룹 ID에 대한 사용자 데이터를 검색합니다.
		JSONArray usersData = ClientDBManager.getGroupUsers(gid);
		// 사용자 데이터를 반복하며 관련 데이터 구조를 채웁니다.
		for (int i = 0; i < usersData.size(); i++) {
			// JSON 데이터에서 사용자 정보를 추출합니다.
			JSONObject user = (JSONObject) usersData.get(i);
			int uid = Integer.parseInt(user.get("uid").toString());
			uids.add(uid);
			String nickname = user.get("nickname").toString();
			nicknames.put(uid, nickname);
			String pfp = user.get("pfp").toString();
			pfps.put(uid, pfp);
		}
	}
}