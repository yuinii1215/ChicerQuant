package AnyQuantProject.bl.kLineBL;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import AnyQuantProject.blService.kLineBLService.StockKLineBLService;
import AnyQuantProject.data.factoryDATA.FactoryDATA;
import AnyQuantProject.dataService.factoryDATAService.FactoryDATAService;
import AnyQuantProject.dataService.realDATAService.singleStockDATAService.SingleStockDATAService;
import AnyQuantProject.dataStructure.AbstractStock;
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
	private List<Stock> oldStocks;

	@Override
	public KLineData dayKLineChart(String stockName,Calendar start,Calendar end) {
		if (!Checker.checkStringNotNull(stockName)) {
                    
			return new KLineData("", null);
		}
		//
		if (start==null) {
			start=CalendarHelper.convert2Calendar(R.startDate);
		}
		if (end==null) {
			end=Calendar.getInstance();
		}
		Calendar localStart=start;
		Calendar localEnd=end;
		refreshData(stockName);
		List<KLineDataDTO> ans = oldStocks.stream()
				.filter(st->st.getDateInCalendar().before(localEnd)&&st.getDateInCalendar().after(localStart))
				.map(st -> (KLineDataDTO) st).collect(Collectors.toList());
		return new KLineData(stockName+" 日线图", ans);
		
	}

	@Override
	public KLineData weekKLineChart(String stockName) {
		if (!Checker.checkStringNotNull(stockName)) {
			return new KLineData(null, null);
		}
		//
		refreshData(stockName);
		//filter as week
		List<KLineDataDTO> ans=oldStocks.stream()
				.filter(st->st.getDateInCalendar().get(Calendar.DAY_OF_WEEK)==2)
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
		refreshData(stockName);
		//filter as month
		List<KLineDataDTO> ans=oldStocks.stream()
				.filter(st->st.getDateInCalendar().get(Calendar.DAY_OF_MONTH)==1)
				.map(st->(KLineDataDTO)st)
				.collect(Collectors.toList());
		//
		return new KLineData(stockName+" 月线图", ans);
	}

	private void refreshData(String stockName) {
		boolean shouldSave = true;
		if (oldStocks == null || !oldStocks.get(0).getName().equalsIgnoreCase(stockName)) {
			// get data service
			FactoryDATAService factoryDATAService = FactoryDATA.getInstance();
			SingleStockDATAService singleStockDATAService = factoryDATAService.getSingleStockDATAService();
			// try to read cache
			try {
				oldStocks = (List<Stock>) IOHelper.read(R.CachePath, stockName);
				if (oldStocks == null) {
					throw new NullPointerException();
				}
				Calendar oldDate = oldStocks.get(oldStocks.size() - 1).getDateInCalendar();
				// get after data
				if (oldDate.before(CalendarHelper.getPreviousDay(Calendar.getInstance()))) {
					List<Stock> newStocks = singleStockDATAService.getStockAmongDate(stockName,
							CalendarHelper.getAfterDay(oldDate), Calendar.getInstance());
					if (newStocks.isEmpty()) {
						shouldSave = false;
					}
					oldStocks.addAll(newStocks);
				}
			} catch (Exception e) {
				oldStocks = singleStockDATAService.getStockAmongDate(stockName,
						CalendarHelper.convert2Calendar(R.startDate), Calendar.getInstance());
			}
			if (shouldSave) {
				// save
				Serializable obj = new ArrayList<Stock>(oldStocks);
				Thread thread = new Thread(() -> IOHelper.save(R.CachePath, stockName, obj));
				thread.start();
			}
		}
	}

	@Override
	public KLineData getDayAverageLine(String stockName, Calendar start, Calendar end, int aver) {
		if (!Checker.checkStringNotNull(stockName)) {
            
			return new KLineData("", null);
		}
		//
		if (aver<=0) {
			aver=5;
		}
		if (start==null) {
			start=CalendarHelper.convert2Calendar(R.startDate);
		}
		if (end==null) {
			end=Calendar.getInstance();
		}
		Calendar localStart=start;
		Calendar localEnd=end;
		refreshData(stockName);
		List<KLineDataDTO> ans = oldStocks.stream()
				.filter(st->st.getDateInCalendar().before(localEnd)&&st.getDateInCalendar().after(localStart))
				.map(st -> (KLineDataDTO) st).collect(Collectors.toList());
		
		return new KLineData(stockName, calculateAver(ans, aver));
	}
	
	private List<KLineDataDTO> calculateAver(List<KLineDataDTO> ans,int aver){
		List<KLineDataDTO> tar=new ArrayList<>(ans.size());
		for(int j=0;j<ans.size()-aver;j++){
			//init data
			double close=0;
			int total=0;
			String date=null;
			for(int i=0;i<aver;i++){
				KLineDataDTO temp=ans.get(j+i);
				if (i==0) {
					date=((AbstractStock)temp).getDate();
				}
				if (temp.getOpen()==0) {
					continue;
				}
				
				close+=temp.getClose();
				
				total++;
			}
			//build
			//first check
			if (total==0){
				total=1;
			}
			close/=total;
			Stock stock=new Stock();
			stock.setDate(date);
			stock.setClose(close);
			tar.add(stock);
		}
		return tar;
	}

	@Override
	public KLineData getWeekAverageLine(String stockName, int aver) {
		if (aver<1) {
			aver=5;
		}
		List<KLineDataDTO> ans=this.weekKLineChart(stockName).geKLineDataDTOs();
		return new KLineData(stockName, calculateAver(ans, aver));
	}

	@Override
	public KLineData getMonthAverageLine(String stockName, int aver) {
		if (aver<1) {
			aver=5;
		}
		List<KLineDataDTO> ans=this.monthKLineChart(stockName).geKLineDataDTOs();
		return new KLineData(stockName, calculateAver(ans, aver));
	}
}
