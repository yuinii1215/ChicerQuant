package AnyQuantProject.bl.calculateBL;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

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

	@Override
	public LineChartData drawRSI(String name) {
		if (Checker.checkStringNotNull(name)) {
			return new LineChartData();
		}
		List<Stock> data=this.getData(name);
		String title=data.get(0).getChinese()+"相对强弱指数折线图";
		// 
		List<DataCell> rsi1=RSI.calculateRSI(data, 6);
		List<DataCell> rsi2=RSI.calculateRSI(data, 12);
		List<DataCell> rsi3=RSI.calculateRSI(data, 24);
		//
		CategoryAxis xAxis=new CategoryAxis();
		NumberAxis yAxis=new NumberAxis();
		//
		XYChart.Series<String,Double> rsi1Series=new XYChart.Series();
		rsi1.stream()
		.map(st->new XYChart.Data<>(st.x, st.y))
		.forEach(d->rsi1Series.getData().add( d));
		rsi1Series.setName("6 days RSI");
		//
		XYChart.Series<String,Double> rsi2Series=new XYChart.Series();
		rsi2.stream()
		.map(st->new XYChart.Data<>(st.x, st.y))
		.forEach(d->rsi2Series.getData().add( d));
		rsi2Series.setName("12 days RSI");
		//
		XYChart.Series<String,Double> rsi3Series=new XYChart.Series();
		rsi3.stream()
		.map(st->new XYChart.Data<>(st.x, st.y))
		.forEach(d->rsi3Series.getData().add( d));
		rsi3Series.setName("24 days RSI");
		//
		return new LineChartData(title, xAxis, yAxis, rsi1Series,rsi2Series,rsi3Series);
	}

	@Override
	public LineChartData drawBIAS(String name) {
		if (Checker.checkStringNotNull(name)) {
			return new LineChartData();
		}
		List<Stock> data=this.getData(name);
		String title=data.get(0).getChinese()+"乖离率折线图";
		// 
		List<DataCell> bias1=BIAS.calculateBIAS(data, 6);
		List<DataCell> bias2=BIAS.calculateBIAS(data, 12);
		List<DataCell> bias3=BIAS.calculateBIAS(data, 24);
		//
		CategoryAxis xAxis=new CategoryAxis();
		NumberAxis yAxis=new NumberAxis();
		//
		XYChart.Series<String,Double> bias1Series=new XYChart.Series();
		bias1.stream()
		.map(st->new XYChart.Data<>(st.x, st.y))
		.forEach(d->bias1Series.getData().add( d));
		bias1Series.setName("6 days BIAS");
		//
		XYChart.Series<String,Double> bias2Series=new XYChart.Series();
		bias2.stream()
		.map(st->new XYChart.Data<>(st.x, st.y))
		.forEach(d->bias2Series.getData().add( d));
		bias2Series.setName("12 days BIAS");
		//
		XYChart.Series<String,Double> bias3Series=new XYChart.Series();
		bias3.stream()
		.map(st->new XYChart.Data<>(st.x, st.y))
		.forEach(d->bias3Series.getData().add( d));
		bias3Series.setName("24 days BIAS");
		return new LineChartData(title, xAxis, yAxis, bias1Series,bias2Series,bias3Series);
	}

	@Override
	public LineChartData drawKDJ(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LineChartData drawPreview(String name) {
		if (Checker.checkStringNotNull(name)) {
			return new LineChartData();
		}
		List<Stock> data=this.getData(name);
		String title=data.get(0).getChinese()+" 价格走势";
		//
		CategoryAxis xAxis=new CategoryAxis();
		NumberAxis yAxis=new NumberAxis();
		//
		XYChart.Series<String,Double> xSeries=new XYChart.Series();
		data.stream()
		.map(st->new XYChart.Data(st.getDate(),st.getClose()))
		.forEach(d->xSeries.getData().add(d));
		
		XYChart.Series<String,Double> percentSeries=new XYChart.Series();
		List<DataCell> percent=new ArrayList<>(data.size());
		//calculate percent
		for (int i = 1; i < data.size(); i++) {
			double before=data.get(i-1).getClose();
			Stock stock=data.get(i);
			String date=stock.getDate();
			double now=stock.getClose();
			double per=(now-before)/before*100;
			DataCell dataCell=new DataCell(date, per);
			percent.add(dataCell);
		}
		percent.stream().map(cell->new XYChart.Data<>(cell.x, cell.y)).forEach(d->percentSeries.getData().add(d));
		
		XYChart.Series<String,Double> volume=new XYChart.Series();
		data.stream()
		.map(st->new XYChart.Data(st.getDate(),st.getVolume()))
		.forEach(d->xSeries.getData().add(d));
		return new LineChartData(title, xAxis, yAxis, xSeries,percentSeries,volume);
	}

	

}
