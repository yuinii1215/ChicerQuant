/**
 * 
 */
package AnyQuantProject.data.jsonDATA;

import java.util.Calendar;
import java.util.List;

import net.sf.json.JSONArray;

import org.json.JSONObject;

import AnyQuantProject.dataService.jsonDATAService.JSONSingleStockDATAService;

/**
 * @author G
 *
 */
public class JSONSingleStockDATA implements JSONSingleStockDATAService{

	
	@Override
	public JSONObject getOperation(String name, Calendar date) {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public JSONArray getBenchMarkAmongDate(String name, Calendar start,
			Calendar end) {
		// TODO Auto-generated method stub
		return null;
	}

}
