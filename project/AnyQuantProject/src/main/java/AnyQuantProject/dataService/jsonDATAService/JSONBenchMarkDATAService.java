/**
 * 
 */
package AnyQuantProject.dataService.jsonDATAService;

import java.util.Calendar;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;





/**
 * @author G
 *
 */
public interface JSONBenchMarkDATAService {
	/**
	 * 返回当前的大盘列表
	 */
	public JSONArray getAllBenchMark();	
	
	/**
	 * 由大盘名称、指定日期得到该大盘指定日期的数据
	 * @param name
	 * @param date
	 * @return
	 */
	public JSONObject getOperation(String name, Calendar date);
	
	/**
	 * 由大盘名称、日期区间得到该大盘指定日期的数据
	 * @param name
	 * @param start
	 * @param end
	 * @return
	 */
	public JSONArray getBenchMarkAmongDate(String name, Calendar start, Calendar end);
}
