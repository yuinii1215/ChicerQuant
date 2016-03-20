package AnyQuantProject.blService.singleStockDealBLService;

import java.util.Calendar;
import java.util.List;

import AnyQuantProject.dataStructure.Stock;

/** 
*AnyQuantProject//AnyQuantProject.blService.singleStockDealBLService//SingleStockDealBLService.java
* @author  cxworks 
* @date 创建时间：2016年3月2日 上午11:39:26 
*/

public interface SingleStockDealBLService {
	/**
	 * 获取股票的交易信息，用于表格
	 * @param name
	 * @param year
	 * @return
	 */
	List<Stock> getSingleStockDeal(String name, Calendar year);
	
	List<Stock> getSingleStockDeal(String name,Calendar start,Calendar end);
}
