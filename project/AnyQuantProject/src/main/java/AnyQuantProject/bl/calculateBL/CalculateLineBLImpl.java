package AnyQuantProject.bl.calculateBL;

import java.util.Calendar;
import java.util.List;

import AnyQuantProject.blService.kLineBLService.CalculateLineBLService;
import AnyQuantProject.data.factoryDATA.FactoryDATA;
import AnyQuantProject.dataService.factoryDATAService.FactoryDATAService;
import AnyQuantProject.dataService.realDATAService.singleStockDATAService.SingleStockDATAService;
import AnyQuantProject.dataStructure.LineChartData;
import AnyQuantProject.dataStructure.Stock;
import AnyQuantProject.util.method.CalendarHelper;
import AnyQuantProject.util.method.Checker;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;

/**
* AnyQuantProject/AnyQuantProject.bl.calculateBL/LineChartBLImpl.java
* @author cxworks
* 2016年3月29日 下午7:32:29
*/
public class CalculateLineBLImpl implements CalculateLineBLService {

	@Override
	public LineChartData drawRSI(String name) {
		if (Checker.checkStringNotNull(name)) {
			return new LineChartData();
		}
		FactoryDATAService factoryDATAService=FactoryDATA.getInstance();
		SingleStockDATAService singleStockDATAService=factoryDATAService.getSingleStockDATAService();
		Calendar now=Calendar.getInstance();
		
		List<Stock> data=singleStockDATAService.getStockAmongDate(name, CalendarHelper.getPreviousYear(now), now);
		if (data==null||data.isEmpty()) {
			return new LineChartData();
		}
		//
		String title=data.get(0).getChinese()+"";
		// 
		List<Stock> rsi1=RSI.calculateRSI(data, 6);
		List<Stock> rsi2=RSI.calculateRSI(data, 12);
		List<Stock> rsi3=RSI.calculateRSI(data, 24);
		//
		CategoryAxis xAxis=new CategoryAxis();
		NumberAxis yAxis=new NumberAxis();
		//
		XYChart.Series<String,Double> rsi1Series=new XYChart.Series();
		rsi1.stream()
		.map(st->new XYChart.Data<>(st.getDate(), st.getClose()))
		.forEach(d->rsi1Series.getData().add( d));
		rsi1Series.setName("6 days RSI");
		//
		XYChart.Series<String,Double> rsi2Series=new XYChart.Series();
		rsi2.stream()
		.map(st->new XYChart.Data<>(st.getDate(), st.getClose()))
		.forEach(d->rsi1Series.getData().add( d));
		rsi2Series.setName("12 days RSI");
		//
		XYChart.Series<String,Double> rsi3Series=new XYChart.Series();
		rsi3.stream()
		.map(st->new XYChart.Data<>(st.getDate(), st.getClose()))
		.forEach(d->rsi1Series.getData().add( d));
		rsi3Series.setName("12 days RSI");
		//
		return new LineChartData(title, xAxis, yAxis, rsi1Series,rsi2Series,rsi3Series);
	}

	

}
