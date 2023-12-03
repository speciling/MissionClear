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



public class MakeProgressData {
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
	       return ((int) diffInDays)+1;
	   }
	DetailMyGroup a;
	int gid;
	public Vector<Vector<Integer>> authDateData = new Vector<Vector<Integer>>();
	MakeProgressData(){}
	MakeProgressData(DetailMyGroup t, Calendar start){
		a=t;
		gid = a.gid;
		JSONArray authData = ClientDBManager.getGroupProgress(gid);	
		
		for(int i=0;i<authData.size();i++) {		
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
	        int idx = calculateDayCount(start,c)-1;
	        temp.add(uid);temp.add(idx);
	        authDateData.add(temp);		
		}
	}
}
