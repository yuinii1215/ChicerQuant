package AnyQuantProject.blService.singleStockInfoBLService;

import AnyQuantProject.dataStructure.Stock;

/** 
*AnyQuantProject//AnyQuantProject.blService.singleStockInfoBLService//SingleStockInfoBLService.java
* @author  cxworks 
* @date 创建时间：2016年3月2日 上午11:39:47 
*/

public interface SingleStockInfoBLService {
	/**
	 * 获取单只股票信息！！！不是交易的信息
	 * 目前只有该股票是否被关注
	 * @param name
	 * @return
	 */
	Stock getSingleStockInfo(String name);
}
