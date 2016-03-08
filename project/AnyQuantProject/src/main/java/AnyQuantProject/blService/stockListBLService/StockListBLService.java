package AnyQuantProject.blService.stockListBLService;

import java.util.List;

import AnyQuantProject.dataStructure.Stock;

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
	 * 预测的结果，比如输入600，返回所有含600的股票的名称
	 * @param target 目标字符
	 * @return
	 */
	List<String> searchPredict(String target);
	/**
	 * 用来检测目标是否合法，true代表合法
	 * @param target
	 * @return
	 */
	boolean searchLegal(String target);
}
