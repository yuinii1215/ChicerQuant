/**
 * 
 */
package AnyQuantProject.data.util;

import java.io.IOException;
import java.util.Calendar;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import AnyQuantProject.util.method.CalendarHelper;

/**
 * @author G
 *
 */
public class JsonHelper {
	
	APIHelper helper = new APIHelper();
	String keyheader = "http://121.41.106.89:8010/api/";
	String key;
	
	
	public JSONArray getAll(String Pkey){
		JSONArray jarr = new JSONArray();
		JSONArray result = new JSONArray();
		try {
			jarr = helper.getAPI(keyheader+Pkey);
		} catch (IOException e) {
			return new JSONArray();
		}

		for (int i = 0; i < jarr.size(); i++) {
			JSONObject jo = (JSONObject) jarr.get(i);
			result.add(jo.get("name"));
		}
		
		return result;
	}
	
	public JSONObject getOperation(DataType type, String name, Calendar date) {
		key = getKeyWithDate(type, name, date, date);
		JSONObject jo = new JSONObject();
		try {
			jo = helper.getAPI(keyheader+key).getJSONObject(0);
		} catch (IOException e) {
			return new JSONObject();
		}
		JSONArray arr = jo.getJSONArray("trading_info");
		return arr.getJSONObject(0);
	}
	
	public JSONArray getAmongDate(DataType type, String name, Calendar start,
			Calendar end) {
		key = getKeyWithDate(type, name, start, end);
		JSONObject jo = new JSONObject();
		try {
			jo = helper.getAPI(keyheader+key).getJSONObject(0);
		} catch (IOException e) {
			return new JSONArray();
		}
		JSONArray result = jo.getJSONArray("trading_info");
		return result;
	}
	
	public String getKeyWithDate(DataType type, String name, Calendar start, Calendar end) {
//		Calendar previousdate = CalendarHelper.getPreviousDay(start);
//		String previousday = CalendarHelper.getDate(previousdate);
		Calendar afterdate = CalendarHelper.getAfterDay(end);
		String afterday = CalendarHelper.getDate(afterdate);
		switch (type) {
		case BENCHMARK:
			return "benchmark/"+name+"?start="+start+"&end="+afterday+"&fields=open+high+low+close+volume+adj_price";
		default:
			return "stock/"+name+"/?start="+start+"&end="+afterday+"&fields=open+high+low+close+volume+adj_price+turnover+pe_ttm+pb";
		}
	}

}
