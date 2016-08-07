/**
 * 
 */
package AnyQuantProject.data.jsonDATA;

import java.util.Calendar;

import AnyQuantProject.util.exception.NetFailedException;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import AnyQuantProject.data.util.DataType;
import AnyQuantProject.data.util.JsonHelper;
import AnyQuantProject.dataService.jsonDATAService.JSONSingleStockDATAService;

/**
 * @author G
 *
 */
public class JSONSingleStockDATA implements JSONSingleStockDATAService{
	JsonHelper jHelper = new JsonHelper();
	
	@Override
	public JSONObject getOperation(String name, Calendar date) throws NetFailedException {
		return jHelper.getOperation(DataType.SINGLESTOCK, name, date);
	}

	
	@Override
	public JSONArray getSingleStockAmongDate(String name, Calendar start,
			Calendar end) throws NetFailedException{
		
		return jHelper.getAmongDate(DataType.SINGLESTOCK, name, start, end);
		
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
			
	}
	


}
