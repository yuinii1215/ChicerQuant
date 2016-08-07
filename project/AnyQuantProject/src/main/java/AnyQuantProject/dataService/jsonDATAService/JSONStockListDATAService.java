/**
 * 
 */
package AnyQuantProject.dataService.jsonDATAService;

import java.util.Calendar;

import AnyQuantProject.util.exception.NetFailedException;
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
	public JSONArray getAllStocks(Calendar date, Exchange exchange) throws NetFailedException;


	/**
	 * 由指定年份、指定交易所的信息得到股票名称和中文名列表，代号名和中文名以空格隔开
	 * @param date
	 * @param exchange
	 * @return
	 */
	public JSONArray getAllStocksWithChinese(Calendar date, Exchange exchange) throws NetFailedException;

	
}
