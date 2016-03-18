package AnyQuantProject.bl.kLineBL;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import AnyQuantProject.blService.kLineBLService.BenchmarkKLineBLService;
import AnyQuantProject.data.factoryDATA.FactoryDATA;
import AnyQuantProject.dataService.factoryDATAService.FactoryDATAService;
import AnyQuantProject.dataService.realDATAService.benchMarkDATAService.BenchMarkDATAService;
import AnyQuantProject.dataService.realDATAService.singleStockDATAService.SingleStockDATAService;
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
		refreshData(stockName);
		final List<KLineDataDTO> ans = oldStocks.stream()
				.filter(st->st.getDateInCalendar().before(end)&&st.getDateInCalendar().after(start))
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
}
