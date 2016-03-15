package AnyQuantProject.blService.kLineBLService;

import AnyQuantProject.dataStructure.KLineData;

/** 
* AnyQuantProject//AnyQuantProject.blService.kLineBLService//KLineBLService.java
* @author  cxworks 
* @date 创建时间：2016年3月14日 下午11:25:13 
*/

public interface KLineBLService {
	static String Begin_Date="2006-1-1";
	KLineData dayKLineChart(String stockName);
	KLineData weekKLineChart(String stockName);
	KLineData monthKLineChart(String stockName);
}
