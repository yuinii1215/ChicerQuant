package AnyQuantProject.bl.calculateBL;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import AnyQuantProject.blService.kLineBLService.CalculateLineBLService;
import AnyQuantProject.data.factoryDATA.FactoryDATA;
import AnyQuantProject.dataService.factoryDATAService.FactoryDATAService;
import AnyQuantProject.dataService.realDATAService.singleStockDATAService.SingleStockDATAService;
import AnyQuantProject.dataStructure.Cell;
import AnyQuantProject.dataStructure.JFreeLineData;
import AnyQuantProject.dataStructure.LineChartData;
import AnyQuantProject.dataStructure.Stock;
import AnyQuantProject.ui.net.TipPop;
import AnyQuantProject.util.exception.NetFailedException;
import AnyQuantProject.util.method.CalendarHelper;
import AnyQuantProject.util.method.Checker;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

/**
* AnyQuantProject/AnyQuantProject.bl.calculateBL/LineChartBLImpl.java
* @author cxworks
* 2016年3月29日 下午7:32:29
*/
public class CalculateLineBLImpl implements CalculateLineBLService  {
	private List<Stock> getData(String name) {
		FactoryDATAService factoryDATAService=FactoryDATA.getInstance();
		SingleStockDATAService singleStockDATAService=factoryDATAService.getSingleStockDATAService();
		Calendar now=Calendar.getInstance();

		try {
			List<Stock> dataT = singleStockDATAService.getStockAmongDate(name, CalendarHelper.getPreviousYear(now), now);

			if (dataT==null||dataT.isEmpty()) {
				return null;
			}
			List<Stock> data=dataT.stream().filter(s->s.getClose()>0).collect(Collectors.toList());
			//
			return data;
		} catch (NetFailedException e) {
			TipPop.showTip();
			return getData(name);
		}
		
	}
	
	private List<Stock> getData(String name,Calendar start,Calendar end){
		FactoryDATAService factoryDATAService=FactoryDATA.getInstance();
		SingleStockDATAService singleStockDATAService=factoryDATAService.getSingleStockDATAService();
		try {
			List<Stock> dataT = singleStockDATAService.getStockAmongDate(name, start, end);

			if (dataT==null||dataT.isEmpty()) {
				return null;
			}
			
			List<Stock> data=dataT.stream().filter(s->s.getClose()>0).collect(Collectors.toList());
			//
			return data;
		} catch (NetFailedException e) {
			TipPop.showTip();
			return getData(name, start, end);
		}
		
	}

	@Override
	public JFreeLineData drawRSI(String name, Calendar min, Calendar max) {
		if (!Checker.checkStringNotNull(name)) {
			return new JFreeLineData();
		}
		Calendar temp=Calendar.getInstance();
		temp.setTimeInMillis(min.getTimeInMillis());
		temp.add(Calendar.DAY_OF_MONTH, -30);
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
		temp.add(Calendar.DAY_OF_MONTH, -30);
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
		List<Stock> dataT;
		try {
			dataT = singleStockDATAService.getStockAmongDate(name, weekBefore, ins);

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
		} catch (NetFailedException e) {
			TipPop.showTip();
			return drawPreview(name);
		}
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

	@Override
	public LineChartData drawPoly(String name) {
		Calendar today=Calendar.getInstance();
		Calendar fiveago=Calendar.getInstance();
		fiveago.add(Calendar.DAY_OF_MONTH, -8);
		List<Stock> data=this.getData(name, fiveago, today);
		List<Double> datac=data.stream().map(s->s.getClose()).collect(Collectors.toList());
		double predict=POLY.calculatePOLY(datac);
		List<Cell> graph=new ArrayList<>(7);
		for (int i = 0; i < 5; i++) {
			Cell cell=new Cell(Integer.toString(i+1), datac.get(i));
			graph.add(cell);
		}
		graph.add(new Cell(Integer.toString(6), predict));
		CategoryAxis xAxis=new CategoryAxis();
		
		//
		XYChart.Series<String,Number> series1=new XYChart.Series();
		graph.stream().map(g->new XYChart.Data<>(g.x, (Number)g.y)).forEach(d->series1.getData().add(d));
		//
		XYChart.Series<String,Number> series2=new XYChart.Series();
		for (int i = 0; i < graph.size(); i++) {
			series2.getData().add(new XYChart.Data<String, Number>(Integer.toString(i+1), (Number)data.get(i).getClose()));
		}
                series1.setName("预测曲线");
		series2.setName("原本曲线");
		double pmax=graph.stream().mapToDouble(g->g.y).max().getAsDouble();
		double pmin=graph.stream().mapToDouble(g->g.y).min().getAsDouble();
		double cmax=datac.subList(datac.size()-graph.size(), datac.size()).stream().mapToDouble(d->d).max().getAsDouble();
		double cmin=datac.subList(datac.size()-graph.size(), datac.size()).stream().mapToDouble(d->d).min().getAsDouble();
		pmax=(pmax>cmax)?pmax:cmax;
		pmin=(pmin>cmin)?cmin:pmin;
		double range=pmax-pmin;
		pmax+=range*0.2;
		pmin-=range*0.2;
		NumberAxis yAxis=new NumberAxis(pmin,pmax,0.5);
		return new LineChartData(null, xAxis, yAxis, series1,series2);
	}

	

}
