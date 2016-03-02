package AnyQuantProject.blService.listFilterBLService;

import java.util.Calendar;
import java.util.List;

import AnyQuantProject.dataStructure.Stock;

/** 
*AnyQuantProject//AnyQuantProject.blService.listFilterBLService//ListFilterBLService.java
* @author  cxworks 
* @date 创建时间：2016年3月2日 上午11:38:52 
*/

public interface ListFilterBLService {
	/**
	 * 排序
	 * @param srcStocks 源数据
	 * @param columnName 比较域
	 * @param orderType true从小到大，false从大到小
	 * @return
	 */
	List<Stock> orderStocks(List<Stock> srcStocks,String columnName,boolean orderType);
	
	List<Stock> filterStocksByFieldAmong(List<Stock> srcStocks,String columnName,double min,double max);
	
	List<Stock> filterStocksByFieldGreater(List<Stock> srcStocks,String columnName,double min);
	
	List<Stock> filterStocksByFieldLess(List<Stock> srcStocks,String columnName,double max);
	
	List<Stock> filterStocksByFieldEqual(List<Stock> srcStocks,String columnName,double target);
	
	List<Stock> filterStocksByDateAmong(List<Stock> srcStocks,String columnName,Calendar min,Calendar max);
	
	List<Stock> filterStocksByDateGreater(List<Stock> srcStocks,String columnName,Calendar min);
	
	List<Stock> filterStocksByDateLess(List<Stock> srcStocks,String columnName,Calendar max);
	
	List<Stock> filterStocksByDateEqual(List<Stock> srcStocks,String columnName,Calendar target);
	

}
