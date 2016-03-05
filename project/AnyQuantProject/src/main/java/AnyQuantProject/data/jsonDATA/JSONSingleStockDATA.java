/**
 * 
 */
package AnyQuantProject.data.jsonDATA;

import java.io.IOException;
import java.util.Calendar;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import AnyQuantProject.data.util.APIHelper;
import AnyQuantProject.dataService.jsonDATAService.JSONSingleStockDATAService;
import AnyQuantProject.util.method.CalendarHelper;

/**
 * @author G
 *
 */
public class JSONSingleStockDATA implements JSONSingleStockDATAService{
	
	APIHelper helper = new APIHelper();
	String keyheader = "http://121.41.106.89:8010/api/";
	String key;
	
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
//		Calendar previousdate = CalendarHelper.getPreviousDay(start);
//		String previousday = CalendarHelper.getDate(previousdate);
//		Calendar afterdate = CalendarHelper.getAfterDay(end);
//		String afterday = CalendarHelper.getDate(afterdate);
//		key = "stock/"+name+"/?start="+previousday+"&end="+afterday+"&fields=pe+pb";
//		JSONObject jo2= helper.getAPI(keyheader+key).getJSONObject(0);
//		JSONArray result2 = jo2.getJSONArray("trading_info");
//		for (int i = 0; i < jo.size(); i++) {
//			((JSONObject)result.get(i)).put("pe", ((JSONObject)result2.get(i)).get("pe"));
//			((JSONObject)result.get(i)).put("pb", ((JSONObject)result2.get(i)).get("pb"));
//		}
//		
//		System.out.println("-----   "+((JSONObject)result.get(0)).get("pe")+((JSONObject)result.get(0)).get("pb"));
		return result;
	}
	

	private String getKeyWithDate(String name, Calendar start, Calendar end){
		Calendar previousdate = CalendarHelper.getPreviousDay(start);
		String previousday = CalendarHelper.getDate(previousdate);
		Calendar afterdate = CalendarHelper.getAfterDay(end);
		String afterday = CalendarHelper.getDate(afterdate);
		key = "stock/"+name+"/?start="+previousday+"&end="+afterday+"&fields=open+high+low+close+volume+adj_price+turnover+pe_ttm+pb";
		return key;
	}

}
