/**
 * 
 */
package AnyQuantProject.data.realDATA.stockListDATA;


import java.util.Calendar;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import AnyQuantProject.data.jsonDATA.JSONStockListDATA;
import AnyQuantProject.dataService.jsonDATAService.JSONStockListDATAService;
import AnyQuantProject.dataService.realDATAService.stockListDATAService.stockListDATAService;
import AnyQuantProject.dataStructure.Exchange;

/**
 * @author G
 *
 */
public class StockListDATA implements stockListDATAService{

	JSONStockListDATAService JSONStockList = new JSONStockListDATA();
	
	@Override
	public List<String> getAllStocks(Calendar date, Exchange exchange) {
		JSONArray resultArray = JSONStockList.getAllStocks(date, exchange);
		List<String> resultList = null;
		for (Object object : resultArray) {
			  JSONObject o = JSONObject.fromObject(object);
			  resultList.add((String) o.get("name"));
			}
		return resultList;
	}

}
