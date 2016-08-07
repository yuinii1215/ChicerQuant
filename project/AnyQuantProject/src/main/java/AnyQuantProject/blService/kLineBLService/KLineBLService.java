package AnyQuantProject.blService.kLineBLService;

import java.util.Calendar;

import AnyQuantProject.dataStructure.KLineData;

/** 
* AnyQuantProject//AnyQuantProject.blService.kLineBLService//KLineBLService.java
* @author  cxworks 
* @date 创建时间：2016年3月14日 下午11:25:13 
*/

public interface KLineBLService {
	KLineData dayKLineChart(String stockName,Calendar start,Calendar end);
	KLineData weekKLineChart(String stockName);
	KLineData monthKLineChart(String stockName);
	KLineData getDayAverageLine(String stockName,Calendar start,Calendar end,int aver);
	KLineData getWeekAverageLine(String stockName,int aver);
	KLineData getMonthAverageLine(String stockName,int aver);
}
