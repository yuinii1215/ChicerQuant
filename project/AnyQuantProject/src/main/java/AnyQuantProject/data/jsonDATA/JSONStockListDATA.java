/**
 * 
 */
package AnyQuantProject.data.jsonDATA;

import java.util.Calendar;

import net.sf.json.JSONArray;
import AnyQuantProject.data.util.JsonHelper;
import AnyQuantProject.dataService.jsonDATAService.JSONStockListDATAService;
import AnyQuantProject.dataStructure.Exchange;

/**
 * @author G
 *
 */
public class JSONStockListDATA implements JSONStockListDATAService{
	JsonHelper jHelper = new JsonHelper();
	
	@Override
	public JSONArray getAllStocks(Calendar date, Exchange exchange) {
		int year = date.get(Calendar.YEAR)-1;
		String key = "stocks/?year="+year+"&exchange="+exchange.getEnglish();
		return jHelper.getAll(key);
	}
	
	
	public static void main(String[] args) {
		JSONStockListDATA j = new JSONStockListDATA();
		j.getAllStocks(Calendar.getInstance(), Exchange.SH);
	}


	
	@Override
	public JSONArray getAllStocksWithChinese(Calendar date, Exchange exchange) {
		int year = date.get(Calendar.YEAR)-1;
		String key = "stocks/?year="+year+"&exchange="+exchange.getEnglish();
		return jHelper.getAllWithChinese(key);
	}

}
