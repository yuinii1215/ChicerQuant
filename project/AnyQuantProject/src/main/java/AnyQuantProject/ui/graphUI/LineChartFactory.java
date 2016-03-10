package AnyQuantProject.ui.graphUI;

import java.util.List;

import AnyQuantProject.bl.factoryBL.LineChartBLFactory;
import AnyQuantProject.blService.graphBLService.LineChartBLService;
import AnyQuantProject.dataStructure.AbstractStock;
import AnyQuantProject.dataStructure.LineChartData;
import AnyQuantProject.dataStructure.Stock;
import AnyQuantProject.util.method.Checker;
import javafx.scene.chart.Axis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart.Series;

/** 
*AnyQuantProject//AnyQuantProject.ui.graphUI//LineChartFactory.java
* @author  cxworks 
* @date 创建时间：2016年3月5日 下午4:10:04 
*/

public class LineChartFactory {
	/**
	 * 已月为单位的关于最高价、最低价的统计图
	 * @param src
	 * @return
	 */
	public static LineChart<?, ?> getMonthLineChart(List<? extends AbstractStock> src){
		//check
		if (!Checker.checkListNotNull(src)) {
			System.out.println("not lucky");
			return null;
		}
		//get bl service
		LineChartBLService lineChartBLService=LineChartBLFactory.getLineChartBLService();
		LineChartData data=lineChartBLService.drawMonthLineChart(src);
		LineChart<String, Number> ans=(LineChart<String, Number>) new LineChart<>(data.getxAxis(), data.getyAxis());
		ans.setTitle(data.getTitle());
		
		data.getSeries().stream().forEach(ser->{
			ans.getData().add((Series<String, Number>) ser);});
		return ans;
	}
	//
	public static LineChart<?, ?> getMonthKLineChart(List<? extends AbstractStock> src){
		// check
		if (!Checker.checkListNotNull(src)) {
			System.out.println("not lucky");
			return null;
		}
		//get bl service
		LineChartBLService lineChartBLService=LineChartBLFactory.getLineChartBLService();
		LineChartData data=lineChartBLService.drawMonthKLineChart(src);
		LineChart<String, Number> ans=new MyKLineChart<String,Number>((Axis<String>)data.getxAxis(), (Axis<Number>)data.getyAxis());
		ans.setTitle(data.getTitle());
		//
		data.getSeries().stream().forEach(ser->{
			ans.getData().add((Series<String, Number>) ser);});
		return ans;
	}
}
