/**
 * 
 */
package AnyQuantProject.dataService.realDATAService.singleStockDATAService;

import java.util.*;

import AnyQuantProject.dataStructure.Exchange;
import AnyQuantProject.dataStructure.Stock;

/**
 * @author G
 *
 */
public interface SingleStockDATAService {
	/**
	 * 由股票名称、指定日期得到该股票指定日期的数据
	 * @param name
	 * @param date
	 * @return
	 */
	public Stock getOperation(String name, Calendar date);
	/**
	 * 由股票名称、日期区间得到该股票指定日期内的信息
	 * @param name
	 * @param start
	 * @param end
	 * @return
	 */
	public List<Stock> getBenchMarkAmongDate(String name, Calendar start, Calendar end);
}
