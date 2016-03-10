package AnyQuantProject.blService.graphBLService;

import java.util.List;

import AnyQuantProject.dataStructure.AbstractStock;
import AnyQuantProject.dataStructure.LineChartData;

/** 
*AnyQuantProject//AnyQuantProject.blService.graphBLService//LineChartBLService.java
* @author  cxworks 
* @date 创建时间：2016年3月5日 下午3:12:35 
*/

public interface LineChartBLService extends GraphBLService {
	String chartType="折线图";
	public LineChartData drawMonthLineChart(List<? extends AbstractStock> src);
	
	public LineChartData drawMonthKLineChart(List<? extends AbstractStock> src);
}
