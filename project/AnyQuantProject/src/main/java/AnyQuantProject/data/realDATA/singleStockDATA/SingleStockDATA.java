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
import AnyQuantProject.util.method.CalendarHelper;

/**
 * @author G
 *
 */
public class SingleStockDATA implements SingleStockDATAService{

	JSONSingleStockDATAService JSONSingleStock = new JSONSingleStockDATA();
	private static SingleStockDATA singleStockDATA;

	
	private SingleStockDATA() {
	}
	
	
	public static SingleStockDATA getInstance(){
		if ( singleStockDATA == null) {
			singleStockDATA = new SingleStockDATA();
		}
		return singleStockDATA;
	}
	
	
	@Override
	public Stock getOperation(String name, Calendar date) {
		JSONObject resultJsonObject = JSONSingleStock.getOperation(name, date);
		Stock result = (Stock) JSONObject.toBean(resultJsonObject,Stock.class);
		result.setName(name);
		return result;
	}

	
	@Override
	public List<Stock> getStockAmongDate(String name, Calendar start,
			Calendar end) {
		JSONArray resultArray = JSONSingleStock.getSingleStockAmongDate(name, start, end);
		@SuppressWarnings("unchecked")
		List<Stock> resultList = JSONArray.toList(resultArray, new Stock(), new JsonConfig());
		for (int i = 0; i < resultList.size(); i++) {
			resultList.get(i).setName(name);
		}
		return resultList;
	}

	
	public static void main(String[] args) {
		SingleStockDATA s = new SingleStockDATA();
		
		s.getStockAmongDate("sh600000", Calendar.getInstance(), Calendar.getInstance());
//		Calendar c = Calendar.getInstance();
//		c.set(Calendar.YEAR, 2015);
//		Stock stock = s.getOperation("sh600000", c);
////		System.out.println(stock.getName());
//		System.out.println(stock.getClose());
	}
}
