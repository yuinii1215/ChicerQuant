package AnyQuantProject.bl.kLineBL;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import AnyQuantProject.blService.kLineBLService.StockKLineBLService;
import AnyQuantProject.data.factoryDATA.FactoryDATA;
import AnyQuantProject.dataService.factoryDATAService.FactoryDATAService;
import AnyQuantProject.dataService.realDATAService.singleStockDATAService.SingleStockDATAService;
import AnyQuantProject.dataStructure.KLineData;
import AnyQuantProject.dataStructure.KLineDataDTO;
import AnyQuantProject.dataStructure.Stock;
import AnyQuantProject.util.constant.R;
import AnyQuantProject.util.method.CalendarHelper;
import AnyQuantProject.util.method.Checker;
import AnyQuantProject.util.method.IOHelper;

/** 
* AnyQuantProject//AnyQuantProject.bl.kLineBL//KLineBLImpl.java
* @author  cxworks 
* @date 创建时间：2016年3月14日 下午11:26:54 
*/

public class StockKLineBLImpl implements StockKLineBLService {

	@Override
	public KLineData dayKLineChart(String stockName) {
		if (!Checker.checkStringNotNull(stockName)) {
			return new KLineData("", null);
		}
		boolean shouldSave=true;
		List<Stock> oldStocks=null;
		//get data service
		FactoryDATAService factoryDATAService=FactoryDATA.getInstance();
		SingleStockDATAService singleStockDATAService=factoryDATAService.getSingleStockDATAService();
		//try to read cache
		try{
			oldStocks=(List<Stock>) IOHelper.read(R.CachePath, stockName);
			Calendar oldDate=oldStocks.get(oldStocks.size()-1).getDateInCalendar();
			//get after data
			if (oldDate.before(CalendarHelper.getPreviousDay(Calendar.getInstance()))) {
				List<Stock> newStocks=singleStockDATAService.getStockAmongDate(stockName, CalendarHelper.getAfterDay(oldDate), Calendar.getInstance());
				if (newStocks.isEmpty()) {
					shouldSave=false;
				}
				oldStocks.addAll(newStocks);
			}
			//
			
		}
		catch(Exception e){
			oldStocks=singleStockDATAService
					.getStockAmongDate(stockName, CalendarHelper.convert2Calendar(Begin_Date), Calendar.getInstance());
		}
		if (shouldSave) {
			// save
			Serializable obj = new ArrayList<Stock>(oldStocks);
			Thread thread = new Thread(() -> IOHelper.save(R.CachePath, stockName, obj));
			thread.start();
		}
		final List<KLineDataDTO> ans = oldStocks.stream().map(st -> (KLineDataDTO) st).collect(Collectors.toList());
		return new KLineData(stockName+" 日线图", ans);
		
	}

	@Override
	public KLineData weekKLineChart(String stockName) {
		if (!Checker.checkStringNotNull(stockName)) {
			return new KLineData(null, null);
		}
		//
		boolean shouldSave=true;
		List<Stock> oldStocks=null;
		//get data service
		FactoryDATAService factoryDATAService=FactoryDATA.getInstance();
		SingleStockDATAService singleStockDATAService=factoryDATAService.getSingleStockDATAService();
		//try to read cache
		try{
			oldStocks=(List<Stock>) IOHelper.read(R.CachePath, stockName);
			Calendar oldDate=oldStocks.get(oldStocks.size()-1).getDateInCalendar();
			//get after data
			if (oldDate.before(CalendarHelper.getPreviousDay(Calendar.getInstance()))) {
				List<Stock> newStocks=singleStockDATAService.getStockAmongDate(stockName, CalendarHelper.getAfterDay(oldDate), Calendar.getInstance());
				if (newStocks.isEmpty()) {
					shouldSave=false;
				}
				oldStocks.addAll(newStocks);
			}
		}
		catch(Exception e){
			oldStocks=singleStockDATAService
					.getStockAmongDate(stockName, CalendarHelper.convert2Calendar(Begin_Date), Calendar.getInstance());
		}
		if (shouldSave) {
			// save
			Serializable obj = new ArrayList<Stock>(oldStocks);
			Thread thread = new Thread(() -> IOHelper.save(R.CachePath, stockName, obj));
			thread.start();
		}
		//filter as week
		List<KLineDataDTO> ans=oldStocks.stream()
				.filter(st->st.getDateInCalendar().get(Calendar.DAY_OF_WEEK)==1)
				.map(st->(KLineDataDTO)st)
				.collect(Collectors.toList());
		//
		return new KLineData(stockName+" 周线图", ans);
		
	}

	@Override
	public KLineData monthKLineChart(String stockName) {
		if (!Checker.checkStringNotNull(stockName)) {
			return new KLineData(null, null);
		}
		//
		boolean shouldSave=true;
		List<Stock> oldStocks=null;
		//get data service
		FactoryDATAService factoryDATAService=FactoryDATA.getInstance();
		SingleStockDATAService singleStockDATAService=factoryDATAService.getSingleStockDATAService();
		//try to read cache
		try{
			oldStocks=(List<Stock>) IOHelper.read(R.CachePath, stockName);
			Calendar oldDate=oldStocks.get(oldStocks.size()-1).getDateInCalendar();
			//get after data
			if (oldDate.before(CalendarHelper.getPreviousDay(Calendar.getInstance()))) {
				List<Stock> newStocks=singleStockDATAService.getStockAmongDate(stockName, CalendarHelper.getAfterDay(oldDate), Calendar.getInstance());
				if (newStocks.isEmpty()) {
					shouldSave=false;
				}
				oldStocks.addAll(newStocks);
			}
		}
		catch(Exception e){
			oldStocks=singleStockDATAService
					.getStockAmongDate(stockName, CalendarHelper.convert2Calendar(Begin_Date), Calendar.getInstance());
		}
		if (shouldSave) {
			// save
			Serializable obj = new ArrayList<Stock>(oldStocks);
			Thread thread = new Thread(() -> IOHelper.save(R.CachePath, stockName, obj));
			thread.start();
		}
		//filter as month
		List<KLineDataDTO> ans=oldStocks.stream()
				.filter(st->st.getDateInCalendar().get(Calendar.DAY_OF_MONTH)==1)
				.map(st->(KLineDataDTO)st)
				.collect(Collectors.toList());
		//
		return new KLineData(stockName+" 月线图", ans);
	}

}
