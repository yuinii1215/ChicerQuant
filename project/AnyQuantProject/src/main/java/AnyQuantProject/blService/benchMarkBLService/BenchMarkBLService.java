package AnyQuantProject.blService.benchMarkBLService;

import java.util.Calendar;
import java.util.List;

import AnyQuantProject.dataStructure.BenchMark;

/** 
*AnyQuantProject//AnyQuantProject.blService.benchMarkBLService//BenchMarkBLService.java
* @author  cxworks 
* @date 创建时间：2016年3月2日 上午11:37:59 
*/

public interface BenchMarkBLService {
	/**
	 * 
	 * @return	返回大盘信息列表
	 */
	List<BenchMark> getAllBenchMark();
	/**
	 * 
	 * @param name	大盘名称
	 * @param year	指定年份
	 * @return	大盘信息列表
	 */
	List<BenchMark> getBenchMarkInfo(String name,Calendar year);
}
