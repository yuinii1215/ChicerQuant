/**
 * 
 */
package AnyQuantProject.dataService.benchMarkDATAService;


import java.util.*;

import AnyQuantProject.dataStructure.BenchMark;

/**
 * @author G
 *
 */
public interface BenchMarkDATAService {
	/**
	 * 返回当前的大盘列表
	 * @return
	 */
	public List<BenchMark> getAllBenchMark();	
	/**
	 * 由大盘名称、指定日期得到该大盘指定日期的数据
	 * @param name
	 * @param date
	 * @return
	 */
	public BenchMark getOperation(String name, Calendar date);
	/**
	 * 由大盘名称、日期区间得到该大盘指定日期的数据
	 * @param name
	 * @param start
	 * @param end
	 * @return
	 */
	public List<BenchMark> getBenchMarkAmongDate(String name, Calendar start, Calendar end);
	
}
