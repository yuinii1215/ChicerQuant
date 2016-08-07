/**
 * 
 */
package AnyQuantProject.dataService.realDATAService.benchMarkDATAService;


import java.util.*;

import AnyQuantProject.dataStructure.BenchMark;
import AnyQuantProject.util.exception.NetFailedException;

/**
 * @author G
 *
 */
public interface BenchMarkDATAService {
	/**
	 * 返回当前的大盘代号名列表
	 * @return
	 */
	public List<String> getAllBenchMark() throws NetFailedException;
	
	
	/**
	 * 返回当前的大盘代号名和中文名列表，名称以空格隔开
	 * @return
	 */
	public List<String> getAllBenchMarkWithChinese() throws NetFailedException;
	/**
	 * 由大盘名称、指定日期得到该大盘指定日期的数据
	 * @param name
	 * @param date
	 * @return
	 */
	public BenchMark getOperation(String name, Calendar date) throws NetFailedException;
	/**
	 * 由大盘名称、日期区间得到该大盘指定日期的数据
	 * @param name
	 * @param start
	 * @param end
	 * @return
	 */
	public List<BenchMark> getBenchMarkAmongDate(String name, Calendar start, Calendar end) throws NetFailedException;
	
}
