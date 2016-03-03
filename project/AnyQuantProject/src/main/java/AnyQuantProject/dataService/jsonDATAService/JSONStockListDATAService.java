/**
 * 
 */
package AnyQuantProject.dataService.jsonDATAService;

import java.util.Calendar;
import net.sf.json.JSONArray;
import AnyQuantProject.dataStructure.Exchange;

/**
 * @author G
 *
 */
public interface JSONStockListDATAService {
	
	/**
	 * 由指定年份、指定交易所的信息得到股票名称列表
	 * @param date
	 * @param exchange
	 * @return
	 */
	public JSONArray getAllStocks(Calendar date, Exchange exchange);
}
