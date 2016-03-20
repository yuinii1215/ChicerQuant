package AnyQuantProject.bl.kLineBL;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import AnyQuantProject.blService.kLineBLService.BenchmarkKLineBLService;
import AnyQuantProject.data.factoryDATA.FactoryDATA;
import AnyQuantProject.dataService.factoryDATAService.FactoryDATAService;
import AnyQuantProject.dataService.realDATAService.benchMarkDATAService.BenchMarkDATAService;
import AnyQuantProject.dataService.realDATAService.singleStockDATAService.SingleStockDATAService;
import AnyQuantProject.dataStructure.AbstractStock;
import AnyQuantProject.dataStructure.BenchMark;
import AnyQuantProject.dataStructure.KLineData;
import AnyQuantProject.dataStructure.KLineDataDTO;
import AnyQuantProject.dataStructure.Stock;
import AnyQuantProject.util.constant.R;
import AnyQuantProject.util.method.CalendarHelper;
import AnyQuantProject.util.method.Checker;
import AnyQuantProject.util.method.IOHelper;

/** 
* AnyQuantProject//AnyQuantProject.bl.kLineBL//BenchmarkKLineBLImpl.java
* @author  cxworks 
* @date 创建时间：2016年3月14日 下午11:27:43 
*/

public class BenchmarkKLineBLImpl implements BenchmarkKLineBLService{
	private List<BenchMark> oldStocks;

	@Override
	public KLineData dayKLineChart(String stockName,Calendar start,Calendar end) {
		
		if (!Checker.checkStringNotNull(stockName)) {
			return new KLineData("", null);
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
		//
		final List<KLineDataDTO> ans = oldStocks.stream()
				.filter(st->st.getDateInCalendar().before(localEnd)&&st.getDateInCalendar().after(localStart))
				.map(st -> (KLineDataDTO) st).collect(Collectors.toList());
		return new KLineData(stockName+" 日线图", ans);
		
	}

	@Override
	public KLineData weekKLineChart(String stockName) {
		if (!Checker.checkStringNotNull(stockName)) {
			return new KLineData("", null);
		}
		refreshData(stockName);
		List<KLineDataDTO> ans = oldStocks.stream().filter(ben->ben.getDateInCalendar().get(Calendar.DAY_OF_WEEK)==2).map(st -> (KLineDataDTO) st).collect(Collectors.toList());
		return new KLineData(stockName+" 周线图", ans);
	}

	@Override
	public KLineData monthKLineChart(String stockName) {
		if (!Checker.checkStringNotNull(stockName)) {
			return new KLineData("", null);
		}
		refreshData(stockName);
		final List<KLineDataDTO> ans = oldStocks.stream().filter(ben->ben.getDateInCalendar().get(Calendar.DAY_OF_MONTH)==1).map(st -> (KLineDataDTO) st).collect(Collectors.toList());
		return new KLineData(stockName+" 月线图", ans);
	}
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
		
		return new KLineData(stockName, calculateAverage(ans, aver));
	}
	
	private List<KLineDataDTO> calculateAverage(List<KLineDataDTO> ans,int aver){
		Iterator<KLineDataDTO> iterator=ans.iterator();
		List<KLineDataDTO> tar=new ArrayList<>((ans.size()/aver)+5);
		while (iterator.hasNext()) {
			//init data
			double open=0;
			double close=0;
			double high=0;
			double low=0;
			long flow=0;
			int volume=0;
			int total=0;
			String date=null;
			for(int i=0;i<aver&&iterator.hasNext();i++){
				KLineDataDTO temp=iterator.next();
				if (i==0) {
					date=((AbstractStock)temp).getDate();
				}
				if (temp.getOpen()==0) {
					continue;
				}
				open+=temp.getOpen();
				close+=temp.getClose();
				high+=temp.getHigh();
				low+=temp.getLow();
				flow+=temp.getFlow();
				volume+=temp.getVolume();
				total++;
			}
			//build
			open/=total;
			close/=total;
			high/=total;
			low/=total;
			flow/=total;
			volume/=total;
			Stock stock=new Stock();
			stock.setDate(date);
			stock.setOpen(open);
			stock.setClose(close);
			stock.setHigh(high);
			stock.setLow(low);
			stock.setFlow(flow);
			stock.setVolume(volume);
			tar.add(stock);
		}
		return tar;
	}
	
	private void refreshData(String stockName) {
		boolean shouldSave = true;
		if (oldStocks == null || !oldStocks.get(0).getName().equalsIgnoreCase(stockName)) {
			// get data service
			FactoryDATAService factoryDATAService = FactoryDATA.getInstance();
			BenchMarkDATAService benchMarkDATAService = factoryDATAService.getBenchMarkDATAService();
			// try to read cache
			try {
				oldStocks = (List<BenchMark>) IOHelper.read(R.CachePath, stockName);
				if (oldStocks == null) {
					throw new NullPointerException();
				}
				Calendar oldDate = oldStocks.get(oldStocks.size() - 1).getDateInCalendar();
				// get after data
				if (oldDate.before(CalendarHelper.getPreviousDay(Calendar.getInstance()))) {
					List<BenchMark> newStocks = benchMarkDATAService.getBenchMarkAmongDate(stockName,
							CalendarHelper.getAfterDay(oldDate), Calendar.getInstance());
					if (newStocks.isEmpty()) {
						shouldSave = false;
					}
					oldStocks.addAll(newStocks);
				}
				//

			} catch (Exception e) {
				oldStocks = benchMarkDATAService.getBenchMarkAmongDate(stockName,
						CalendarHelper.convert2Calendar(R.startDate), Calendar.getInstance());
			}
			if (shouldSave) {
				// save
				Serializable obj = new ArrayList<BenchMark>(oldStocks);
				Thread thread = new Thread(() -> IOHelper.save(R.CachePath, stockName, obj));
				thread.start();
			}
		}
	}

	@Override
	public KLineData getWeekAverageLine(String stockName, int aver) {
		List<KLineDataDTO> ans=this.weekKLineChart(stockName).geKLineDataDTOs();
		if (aver<1) {
			aver=5;
		}
		return new KLineData(stockName, calculateAverage(ans, aver));
	}

	@Override
	public KLineData getMonthAverageLine(String stockName, int aver) {
		List<KLineDataDTO> ans=this.monthKLineChart(stockName).geKLineDataDTOs();
		if (aver<1) {
			aver=5;
		}
		return new KLineData(stockName, calculateAverage(ans, aver));
	}
}
