/**
 * 
 */
package AnyQuantProject.dataService.jsonDATAService;

import java.util.Calendar;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;





/**
 * @author G
 *
 */
public interface JSONBenchMarkDATAService {
	public JSONArray getAllBenchMark();	
	
	public JSONObject getOperation(String name, Calendar date);
	
	public JSONArray getBenchMarkAmongDate(String name, Calendar start, Calendar end);
}
