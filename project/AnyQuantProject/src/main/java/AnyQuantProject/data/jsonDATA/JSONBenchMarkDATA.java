/**
 * 
 */
package AnyQuantProject.data.jsonDATA;

import java.io.IOException;
import java.util.Calendar;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import AnyQuantProject.data.util.APIHelper;
import AnyQuantProject.dataService.jsonDATAService.JSONBenchMarkDATAService;
import AnyQuantProject.util.method.CalendarHelper;


/**
 * @author G
 *
 */
public class JSONBenchMarkDATA implements JSONBenchMarkDATAService{

	APIHelper helper = new APIHelper();
	String keyheader = "http://121.41.106.89:8010/api/";
	String key;
	
	@Override
	public JSONArray getAllBenchMark() {
		key = "benchmark/all";
		JSONArray jarr = new JSONArray();
		JSONArray result = new JSONArray();
		try {
			jarr = helper.getAPI(keyheader+key);
		} catch (IOException e) {
			return result;
		}

		for (int i = 0; i < jarr.size(); i++) {
			JSONObject jo = (JSONObject) jarr.get(i);
			result.add(jo.get("name"));
		}
		
		return result;
	}

	
	@Override
	public JSONObject getOperation(String name, Calendar date) {		
		key = getKeyWithDate(name, date, date);
		JSONObject jo = new JSONObject();
		try {
			jo = helper.getAPI(keyheader+key).getJSONObject(0);
		} catch (IOException e) {
			return new JSONObject();
		}
		JSONArray arr = jo.getJSONArray("trading_info");
		return arr.getJSONObject(0);
	}

	
	@Override
	public JSONArray getBenchMarkAmongDate(String name, Calendar start,
			Calendar end) {
		key = getKeyWithDate(name, start, end);
		JSONObject jo = new JSONObject();
		try {
			jo = helper.getAPI(keyheader+key).getJSONObject(0);
		} catch (IOException e) {
			return new JSONArray();
		}
		JSONArray result = jo.getJSONArray("trading_info");
		return result;
	}

	private String getKeyWithDate(String name, Calendar start, Calendar end){
		Calendar previousdate = CalendarHelper.getPreviousDay(start);
		String previousday = CalendarHelper.getDate(previousdate);
		Calendar afterdate = CalendarHelper.getAfterDay(end);
		String afterday = CalendarHelper.getDate(afterdate);
		key = "benchmark/"+name+"?start="+previousday+"&end="+afterday+"&fields=open+high+low+close+volume+adj_price";
		return key;
	}


	public static void main(String[] args) {
		JSONBenchMarkDATA j = new JSONBenchMarkDATA();
//		j.getAllBenchMark();
//		j.getOperation("hs300", Calendar.getInstance());
		j.getBenchMarkAmongDate("hs300", CalendarHelper.getPreviousDay(Calendar.getInstance()), Calendar.getInstance());
	}
}
