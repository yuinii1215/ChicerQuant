package AnyQuantProject.bl.calculateBL;

import java.lang.invoke.ConstantCallSite;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.plaf.basic.BasicSliderUI.ActionScroller;

import AnyQuantProject.blService.kLineBLService.CalculateLineBLService;
import AnyQuantProject.data.factoryDATA.FactoryDATA;
import AnyQuantProject.dataService.factoryDATAService.FactoryDATAService;
import AnyQuantProject.dataService.realDATAService.singleStockDATAService.SingleStockDATAService;
import AnyQuantProject.dataStructure.Cell;
import AnyQuantProject.dataStructure.JFreeLineData;
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
	private List<Stock> getData(String name) {
		FactoryDATAService factoryDATAService=FactoryDATA.getInstance();
		SingleStockDATAService singleStockDATAService=factoryDATAService.getSingleStockDATAService();
		Calendar now=Calendar.getInstance();
		
		List<Stock> dataT=singleStockDATAService.getStockAmongDate(name, CalendarHelper.getPreviousYear(now), now);
		if (dataT==null||dataT.isEmpty()) {
			return null;
		}
		
		List<Stock> data=dataT.stream().filter(s->s.getClose()>0).collect(Collectors.toList());
		//
		return data;
	}
	
	private List<Stock> getData(String name,Calendar start,Calendar end){
		FactoryDATAService factoryDATAService=FactoryDATA.getInstance();
		SingleStockDATAService singleStockDATAService=factoryDATAService.getSingleStockDATAService();
		
		List<Stock> dataT=singleStockDATAService.getStockAmongDate(name, start, end);
		if (dataT==null||dataT.isEmpty()) {
			return null;
		}
		
		List<Stock> data=dataT.stream().filter(s->s.getClose()>0).collect(Collectors.toList());
		//
		return data;
	}

	@Override
	public JFreeLineData drawRSI(String name, Calendar min, Calendar max) {
		if (!Checker.checkStringNotNull(name)) {
			return new JFreeLineData();
		}
		Calendar temp=Calendar.getInstance();
		temp.setTimeInMillis(min.getTimeInMillis());
		temp.add(Calendar.DAY_OF_MONTH, -6);
		List<Stock> data=this.getData(name,temp,max);
		String title=data.get(0).getChinese()+"相对强弱指数折线图";
		// 
		List<DataCell> rsi1=RSI.calculateRSI(data, 6);
		List<DataCell> rsi2=RSI.calculateRSI(data, 12);
		List<DataCell> rsi3=RSI.calculateRSI(data, 24);
		//
		List<Cell> rsi6=rsi1.stream().map(ce->new Cell(ce)).collect(Collectors.toList());
		List<Cell> rsi12=rsi2.stream().map(ce->new Cell(ce)).collect(Collectors.toList());
		List<Cell> rsi24=rsi3.stream().map(ce->new Cell(ce)).collect(Collectors.toList());
		return new JFreeLineData(title, rsi6,rsi12,rsi24);
	}

	@Override
	public JFreeLineData drawBIAS(String name,Calendar minTime,Calendar maxTime) {
		if (!Checker.checkStringNotNull(name)) {
			return new JFreeLineData();
		}
		Calendar temp=Calendar.getInstance();
		temp.setTimeInMillis(minTime.getTimeInMillis());
		temp.add(Calendar.DAY_OF_MONTH, -24);
		List<Stock> data=this.getData(name,temp,maxTime);
		String title=data.get(0).getChinese()+"乖离率折线图";
		// 
		List<DataCell> bias1=BIAS.calculateBIAS(data, 6);
		List<DataCell> bias2=BIAS.calculateBIAS(data, 12);
		List<DataCell> bias3=BIAS.calculateBIAS(data, 24);
		//
		List<Cell> bias6=bias1.stream().map(d->new Cell(d)).collect(Collectors.toList());
		List<Cell> bias12=bias2.stream().map(d->new Cell(d)).collect(Collectors.toList());
		List<Cell> bias24=bias3.stream().map(d->new Cell(d)).collect(Collectors.toList());
		return new JFreeLineData(title, bias6,bias12,bias24);
	}

	@Override
	public JFreeLineData drawKDJ(String name,Calendar minTime,Calendar maxTime) {
		if (!Checker.checkStringNotNull(name)) {
			return new JFreeLineData();
		}
		Calendar temp=Calendar.getInstance();
		temp.setTimeInMillis(minTime.getTimeInMillis());
		temp.add(Calendar.DAY_OF_MONTH, -7);
		List<Stock> data=this.getData(name,temp,maxTime);
		String title=data.get(0).getChinese()+"KDJ";
		//
		List<DataCell> rsi=KDJ.calculateRSI(data, 6);
		List<DataCell> kdj=KDJ.calculateKDJ(rsi);
		//
		List<Cell> k=kdj.stream().map(ce->new Cell(ce.x, ce.y)).collect(Collectors.toList());
		List<Cell> d=kdj.stream().map(ce->new Cell(ce.x, ce.y2)).collect(Collectors.toList());
		List<Cell> j=kdj.stream().map(ce->new Cell(ce.x, ce.y3)).collect(Collectors.toList());
		return new JFreeLineData(title, k,d,j);
	}

	@Override
	public LineChartData drawPreview(String name) {
		
		if (!Checker.checkStringNotNull(name)) {
			return new LineChartData();
		}
		FactoryDATAService factoryDATAService=FactoryDATA.getInstance();
		SingleStockDATAService singleStockDATAService=factoryDATAService.getSingleStockDATAService();
		Calendar ins=Calendar.getInstance();
		Calendar weekBefore=Calendar.getInstance();
		weekBefore.add(Calendar.DAY_OF_WEEK, -14);
		List<Stock> dataT=singleStockDATAService.getStockAmongDate(name, weekBefore, ins);
		if (dataT==null||dataT.isEmpty()) {
			return null;
		}
		
		List<Stock> data=dataT.stream().filter(s->s.getClose()>0).collect(Collectors.toList());
		String title="近期价格走势";
		//
		CategoryAxis xAxis=new CategoryAxis();
		
		//
		XYChart.Series<String,Double> xSeries=new XYChart.Series();
		data.stream()
		.map(st->new XYChart.Data(st.getDate(),st.getClose()))
		.forEach(d->xSeries.getData().add(d));
		try {
			double max=data.stream().max((s1,s2)->(int)(s1.getClose()-s2.getClose())).get().getClose();
			double min=data.stream().min((s1,s2)->(int)(s1.getClose()-s2.getClose())).get().getClose();
			double low=min-(max-min)*0.2;
			double high=max+(max-min)*0.2;
			NumberAxis yAxis=new NumberAxis(low,high,0.01);
			return new LineChartData(title, xAxis, yAxis, xSeries);
		} catch (Exception e) {
			NumberAxis yAxis=new NumberAxis();
			return new LineChartData(title, xAxis, yAxis);
		}
		
//		XYChart.Series<String,Double> percentSeries=new XYChart.Series();
//		List<DataCell> percent=new ArrayList<>(data.size());
//		//calculate percent
//		for (int i = 1; i < data.size(); i++) {
//			double before=data.get(i-1).getClose();
//			Stock stock=data.get(i);
//			String date=stock.getDate();
//			double now=stock.getClose();
//			double per=(now-before)/before*100;
//			DataCell dataCell=new DataCell(date, per);
//			percent.add(dataCell);
//		}
//		percent.stream().map(cell->new XYChart.Data<>(cell.x, cell.y)).forEach(d->percentSeries.getData().add(d));
//		
//		XYChart.Series<String,Double> volume=new XYChart.Series();
//		data.stream()
//		.map(st->new XYChart.Data(st.getDate(),st.getVolume()))
//		.forEach(d->volume.getData().add(d));
		
	}

	@Override
	public JFreeLineData drawMACD(String name,Calendar minTime,Calendar maxTime) {
       Calendar tempMinTime=Calendar.getInstance();
       tempMinTime.setTimeInMillis(minTime.getTimeInMillis());
		if (!Checker.checkStringNotNull(name)) {
			return new JFreeLineData();
		}
		tempMinTime.add(Calendar.DAY_OF_MONTH, -50);
		List<Stock> data=this.getData(name,tempMinTime,maxTime);
                
                
		String title=data.get(0).getChinese()+"MACD";
		//
		List<DataCell> macd=MACD.calculateMACD(data, 12, 9, 26);
		//
		List<Cell> dif=macd.stream().map(ce->new Cell(ce.x, ce.y)).collect(Collectors.toList());
		List<Cell> dea=macd.stream().map(ce->new Cell(ce.x, ce.y2)).collect(Collectors.toList());
		List<Cell> macdBar=macd.stream().map(ce->new Cell(ce.x, ce.y3)).collect(Collectors.toList());
		return new JFreeLineData(title,dif,dea,macdBar);
	}

	

}
