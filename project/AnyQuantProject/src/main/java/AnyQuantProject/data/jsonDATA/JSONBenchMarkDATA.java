/**
 * 
 */
package AnyQuantProject.data.jsonDATA;

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
	String key;
	
	@Override
	public JSONArray getAllBenchMark() {
		key = "benchmark/all";
		return helper.getAPI(key);
	}

	
	@Override
	public JSONObject getOperation(String name, Calendar date) {		
		key = getKeyWithDate(name, date, date);
		return helper.getAPI(key).getJSONObject(0);
	}

	
	@Override
	public JSONArray getBenchMarkAmongDate(String name, Calendar start,
			Calendar end) {
		key = getKeyWithDate(name, start, end);
		return helper.getAPI(key);
	}

	private String getKeyWithDate(String name, Calendar start, Calendar end){
		Calendar previousdate = CalendarHelper.getPreviousDay(start);
		String previousday = CalendarHelper.getDate(previousdate);
		Calendar afterdate = CalendarHelper.getAfterDay(end);
		String afterday = CalendarHelper.getDate(afterdate);
		key = "benchmark/"+name+"?start="+previousday+"&end="+afterday+"&fields=open+high+low+close+volume+adj_price";
		return key;
	}


}
