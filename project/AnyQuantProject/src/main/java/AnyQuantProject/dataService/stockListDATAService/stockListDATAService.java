/**
 * 
 */
package AnyQuantProject.dataService.stockListDATAService;

import java.util.Calendar;
import java.util.List;

import AnyQuantProject.dataStructure.Exchange;

/**
 * @author G
 *
 */
public interface stockListDATAService {
	/**
	 * 由指定年份、指定交易所的信息得到股票名称列表
	 * @param date
	 * @param exchange
	 * @return
	 */
	public List<String> getAllStocks(Calendar date, Exchange exchange);
}
