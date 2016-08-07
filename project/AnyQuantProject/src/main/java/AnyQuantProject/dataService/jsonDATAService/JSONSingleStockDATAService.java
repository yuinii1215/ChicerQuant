/**
 * 
 */
package AnyQuantProject.dataService.jsonDATAService;

import java.util.Calendar;

import AnyQuantProject.util.exception.NetFailedException;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;



/**
 * @author G
 *
 */
public interface JSONSingleStockDATAService {

	/**
	 * 由股票名称、指定日期得到该股票指定日期的数据
	 * @param name
	 * @param date
	 * @return
	 */
	public JSONObject getOperation(String name, Calendar date)throws NetFailedException;
	
	
	/**
	 * 由股票名称、日期区间得到该股票指定日期内的信息
	 * @param name
	 * @param start
	 * @param end
	 * @return
	 */
	public JSONArray getSingleStockAmongDate(String name, Calendar start, Calendar end)throws NetFailedException;
}
