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
		return getHelper(key);
	}

	
	@Override
	public JSONObject getOperation(String name, Calendar date) {
		// TODO Auto-generated method stub		
		key = "benchmark/"+name+"?start="+CalendarHelper.getDate(date)+"&end="+CalendarHelper.getDate(date)+"&fields";
		return null;
	}

	
	@Override
	public JSONArray getBenchMarkAmongDate(String name, Calendar start,
			Calendar end) {
		// TODO Auto-generated method stub
		return null;
	}

	
	private JSONArray getHelper(String key){
		return helper.getAPI(key);
	}
	

}
