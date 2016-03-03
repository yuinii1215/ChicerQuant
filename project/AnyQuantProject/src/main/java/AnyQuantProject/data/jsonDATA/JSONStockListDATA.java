/**
 * 
 */
package AnyQuantProject.data.jsonDATA;

import java.util.Calendar;
import net.sf.json.JSONArray;
import AnyQuantProject.data.util.APIHelper;
import AnyQuantProject.dataService.jsonDATAService.JSONStockListDATAService;
import AnyQuantProject.dataStructure.Exchange;

/**
 * @author G
 *
 */
public class JSONStockListDATA implements JSONStockListDATAService{

	APIHelper helper = new APIHelper();
	String key;
	
	@Override
	public JSONArray getAllStocks(Calendar date, Exchange exchange) {
		// TODO sh与exchange互换
		int year = date.get(Calendar.YEAR);
		key = "stocks/?year="+year+"&exchange=";
		return helper.getAPI(key);
	}

}
