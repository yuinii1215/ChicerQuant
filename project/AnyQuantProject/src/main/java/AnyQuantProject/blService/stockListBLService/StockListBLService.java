package AnyQuantProject.blService.stockListBLService;

import java.util.List;

import AnyQuantProject.dataStructure.Stock;
import AnyQuantProject.util.exception.NotReadyException;

/** 
*AnyQuantProject//AnyQuantProject.blService.stockListBLService//StockListBLService.java
* @author  cxworks 
* @date 创建时间：2016年3月2日 上午11:40:06 
*/

public interface StockListBLService {
	/**
	 * 返回所有股票列表
	 * @return
	 * @warning 信息是昨天（最新）信息
	 */
	List<Stock> getAllStocks();
	/**
	 * 返回所有可搜索的列表
	 * @return
	 */
	List<String> getSearchList();
	/**
	 * 用来检测目标是否合法，true代表合法
	 * @param target
	 * @return
	 */
	boolean searchLegal(String target);
	
	Stock getTodayData (String name) throws NotReadyException;

}
