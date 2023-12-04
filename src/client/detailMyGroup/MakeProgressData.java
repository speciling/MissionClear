package client.detailMyGroup;

import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import client.db.ClientDBManager;

/**
 * {@code MakeProgressData} 클래스는 그룹에 들어있는 유저의 미션 진행 상황 데이터를 생성하는 데 책임이 있습니다.
 * 사용자의 미션 인증 날짜 및 사용자 ID에 대한 정보를 포함합니다.
 */
public class MakeProgressData {
	/**
	 * 두 날짜 간의 일 수를 계산하는 메서드입니다.
	 *
	 * @param start 시작 날짜인 {@code Calendar} 객체입니다.
	 * @param end   종료 날짜인 {@code Calendar} 객체입니다.
	 * @return 시작 날짜와 종료 날짜 사이의 일 수입니다.
	 */
	private int calculateDayCount(Calendar start, Calendar end) {
		start.set(Calendar.HOUR_OF_DAY, 0);
		start.set(Calendar.MINUTE, 0);
		start.set(Calendar.SECOND, 0);
		start.set(Calendar.MILLISECOND, 0);

		end.set(Calendar.HOUR_OF_DAY, 0);
		end.set(Calendar.MINUTE, 0);
		end.set(Calendar.SECOND, 0);
		end.set(Calendar.MILLISECOND, 0);

		long diffInMillis = end.getTimeInMillis() - start.getTimeInMillis();
		long diffInDays = TimeUnit.MILLISECONDS.toDays(diffInMillis);
		return ((int) diffInDays) + 1;
	}

	DetailMyGroup a;
	int gid;
	/**
	 * 사용자의 미션 인증 날짜 데이터를 저장하는 2D 벡터입니다. 외부 벡터는 사용자 ID에 대한 인덱스를 가지며, 내부 벡터는 시작
	 * 날짜로부터의 일 수에 대한 인덱스를 가집니다.
	 */
	public Vector<Vector<Integer>> authDateData = new Vector<Vector<Integer>>();

	/**
	 * {@code MakeProgressData}의 기본 생성자입니다.
	 */
	MakeProgressData() {
	}

	/**
	 * {@code MakeProgressData} 생성자로, 특정 그룹과 시작 날짜를 기반으로 미션 인증 진행 상황 데이터를 초기화합니다.
	 *
	 * @param t     어떤 그룹인지 id를 받아오기 위해 사용되는 {@code DetailMyGroup} 객체입니다.
	 * @param start 시작 날짜로 사용되는 {@code Calendar} 객체입니다.
	 */
	MakeProgressData(DetailMyGroup t, Calendar start) {
		a = t;
		gid = a.gid;
		// 데이터베이스에서 지정된 그룹 ID에 대한 미션 인증 진행 상황 데이터를 검색합니다.
		JSONArray authData = ClientDBManager.getGroupProgress(gid);

		// 미션 인증 진행 상황 데이터를 반복하며 관련 데이터 구조를 채웁니다.
		for (int i = 0; i < authData.size(); i++) {
			Vector<Integer> temp = new Vector<Integer>();
			JSONObject authDataObject = (JSONObject) authData.get(i);
			int uid = Integer.parseInt(authDataObject.get("uid").toString());
			String dateString = authDataObject.get("date").toString();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = null;
			try {
				date = dateFormat.parse(dateString);
			} catch (ParseException e) {
				e.printStackTrace();
			}

			Calendar c = Calendar.getInstance();
			if (date != null) {
				c.setTime(date);
			}
			int idx = calculateDayCount(start, c) - 1;
			temp.add(uid);
			temp.add(idx);
			authDateData.add(temp);
		}
	}
}
