package AnyQuantProject.dataStructure;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.Axis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

/** 
*AnyQuantProject//AnyQuantProject.dataStructure//LineChartData.java
* @author  cxworks 
* @date 创建时间：2016年3月5日 下午2:51:56 
*/

public class LineChartData extends GraphData {
	
	private Axis<?> xAxis;
	private Axis<?> yAxis;
	//
	private List<XYChart.Series<?,?>> series;
	
	@Deprecated
	public LineChartData(){
		super("not found");
		xAxis=new NumberAxis();
		yAxis=new NumberAxis();
		series=FXCollections.observableArrayList(new XYChart.Series());              
	}
	public LineChartData(String title,Axis<?> x,Axis<?> y,XYChart.Series<?,?>... series ) {
		super(title);
		this.xAxis=x;
		this.yAxis=y;
		this.series= new ArrayList<>(series.length);
		for (int i=0;i<series.length;i++){
			this.series.add(series[i]);
		}
                
	}
	public Axis<?> getxAxis() {
		return xAxis;
	}
	public Axis<?> getyAxis() {
		return yAxis;
	}
	public List<XYChart.Series<?,?>> getSeries() {
		return series;
	}
	
	
}
