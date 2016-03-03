/**
 * 
 */
package AnyQuantProject.data.realDATA.singleStockDATA;

import java.util.Calendar;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import AnyQuantProject.data.jsonDATA.JSONSingleStockDATA;
import AnyQuantProject.dataService.jsonDATAService.JSONSingleStockDATAService;
import AnyQuantProject.dataService.realDATAService.singleStockDATAService.SingleStockDATAService;
import AnyQuantProject.dataStructure.Stock;

/**
 * @author G
 *
 */
public class SingleStockDATA implements SingleStockDATAService{

	JSONSingleStockDATAService JSONSingleStock = new JSONSingleStockDATA();
	
	@Override
	public Stock getOperation(String name, Calendar date) {
		JSONObject resultJsonObject = JSONSingleStock.getOperation(name, date);
		Stock result = (Stock) JSONObject.toBean(resultJsonObject,Stock.class);
		return result;
	}

	
	@Override
	public List<Stock> getStockAmongDate(String name, Calendar start,
			Calendar end) {
		JSONArray resultArray = JSONSingleStock.getBenchMarkAmongDate(name, start, end);
		@SuppressWarnings("unchecked")
		List<Stock> resultList = JSONArray.toList(resultArray, new Stock(), new JsonConfig());
		return resultList;
	}

}
