/**
 * 
 */
package AnyQuantProject.dataService.jsonDATAService;

import java.util.Calendar;
import java.util.List;

import net.sf.json.JSONArray;

import org.json.JSONObject;


/**
 * @author G
 *
 */
public interface JSONSingleStockDATAService {

	public JSONObject getOperation(String name, Calendar date);
	public JSONArray getBenchMarkAmongDate(String name, Calendar start, Calendar end);
}
