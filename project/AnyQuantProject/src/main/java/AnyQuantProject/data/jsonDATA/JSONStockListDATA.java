/**
 * 
 */
package AnyQuantProject.data.jsonDATA;

import java.io.IOException;
import java.util.Calendar;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import AnyQuantProject.data.util.APIHelper;
import AnyQuantProject.dataService.jsonDATAService.JSONStockListDATAService;
import AnyQuantProject.dataStructure.Exchange;

/**
 * @author G
 *
 */
public class JSONStockListDATA implements JSONStockListDATAService{

	APIHelper helper = new APIHelper();
	String keyheader = "http://121.41.106.89:8010/api/";
	String key;
	
	@Override
	public JSONArray getAllStocks(Calendar date, Exchange exchange) {
		int year = date.get(Calendar.YEAR)-1;
		key = "stocks/?year="+year+"&exchange="+exchange.getEnglish();
		JSONArray jarr = new JSONArray();
		try {
			jarr = helper.getAPI(keyheader+key);
		} catch (IOException e) {
			return new JSONArray();
		}
		JSONArray result = new JSONArray();
		for (int i = 0; i < jarr.size(); i++) {
			JSONObject jo = (JSONObject) jarr.get(i);
			result.add(jo.get("name"));
		}
		
		return result;
	}
	
	
	public static void main(String[] args) {
		JSONStockListDATA j = new JSONStockListDATA();
		j.getAllStocks(Calendar.getInstance(), Exchange.SH);
	}

}
