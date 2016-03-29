package AnyQuantProject.bl.factoryBL;

import AnyQuantProject.bl.calculateBL.CalculateLineBLImpl;
import AnyQuantProject.bl.graphBL.LineChartBLImpl;
import AnyQuantProject.blService.graphBLService.LineChartBLService;
import AnyQuantProject.blService.kLineBLService.CalculateLineBLService;

/** 
*AnyQuantProject//AnyQuantProject.bl.factoryBL//LineChartBLFactory.java
* @author  cxworks 
* @date 创建时间：2016年3月5日 下午4:16:08 
*/

public class LineChartBLFactory extends FactoryBL {

	private static LineChartBLService lineChartBLService=null;
	private static CalculateLineBLService calculateLineBLService=null;
	public static CalculateLineBLService getCalculateLineBL(){
		if (calculateLineBLService==null) {
			calculateLineBLService=new CalculateLineBLImpl();
		}
		return calculateLineBLService;
	}
	public static LineChartBLService getLineChartBLService(){
		if (lineChartBLService==null) {
			lineChartBLService=new LineChartBLImpl();
		}
		return lineChartBLService;
	}
}
