package AnyQuantProject.bl.factoryBL;

import AnyQuantProject.bl.graphBL.LineChartBLImpl;
import AnyQuantProject.blService.graphBLService.LineChartBLService;

/** 
*AnyQuantProject//AnyQuantProject.bl.factoryBL//LineChartBLFactory.java
* @author  cxworks 
* @date 创建时间：2016年3月5日 下午4:16:08 
*/

public class LineChartBLFactory extends FactoryBL {

	private static LineChartBLService lineChartBLService=null;
	public static LineChartBLService getLineChartBLService(){
		if (lineChartBLService==null) {
			lineChartBLService=new LineChartBLImpl();
		}
		return lineChartBLService;
	}
}
