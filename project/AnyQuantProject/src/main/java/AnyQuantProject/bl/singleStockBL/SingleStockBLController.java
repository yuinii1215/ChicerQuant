package AnyQuantProject.bl.singleStockBL;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import AnyQuantProject.bl.factoryBL.FavoriteBLFactory;
import AnyQuantProject.bl.factoryBL.IndustryBLFactory;
import AnyQuantProject.bl.factoryBL.StockListBLFactory;
import AnyQuantProject.blService.favoriteBLService.FavoriteBLService;
import AnyQuantProject.blService.industryBLService.IndustryBLService;
import AnyQuantProject.blService.singleStockDealBLService.SingleStockDealBLService;
import AnyQuantProject.blService.singleStockInfoBLService.SingleStockInfoBLService;
import AnyQuantProject.blService.stockListBLService.StockListBLService;
import AnyQuantProject.data.factoryDATA.FactoryDATA;
import AnyQuantProject.dataService.factoryDATAService.FactoryDATAService;
import AnyQuantProject.dataService.realDATAService.singleStockDATAService.SingleStockDATAService;
import AnyQuantProject.dataStructure.AbstractStock;
import AnyQuantProject.dataStructure.Stock;
import AnyQuantProject.ui.net.TipPop;
import AnyQuantProject.util.exception.NetFailedException;
import AnyQuantProject.util.method.CalendarHelper;
import AnyQuantProject.util.method.Checker;
import AnyQuantProject.util.method.NetCheck;

/** 
*AnyQuantProject//AnyQuantProject.bl.singleStockBL//SingleStockBLController.java
* @author  cxworks 
* @date 创建时间：2016年3月2日 下午2:49:07 
*/

public class SingleStockBLController implements SingleStockInfoBLService, SingleStockDealBLService {

	@Override
	public List<Stock> getSingleStockDeal(String name, Calendar year) {
		
		//name check
		if (!Checker.checkStringNotNull(name)) {
			return new ArrayList<Stock>();
		}
		//year check
		if (!Checker.checkCalendarBefore(year)) {
			return new ArrayList<>();
		}
		//
		FactoryDATAService factoryDATAService=FactoryDATA.getInstance();
		SingleStockDATAService singleStockDATAService=factoryDATAService.getSingleStockDATAService();
		//
		try {
			List<Stock> ans=singleStockDATAService
					.getStockAmongDate(name, CalendarHelper.getPreviousDay(CalendarHelper.getMonthStart(year)), CalendarHelper.getMonthEnd(year));
			for (int i = 1; i < ans.size(); i++) {
				ans.get(i).setYesterday(ans.get(i-1));
			}
			return ans.subList(1, ans.size());
		} catch (NetFailedException e) {
			TipPop.showTip();

			return getSingleStockDeal(name, year);
			
		}
	}

	@Override
	public Stock getSingleStockInfo(String name) {
		//check name
		if (!Checker.checkStringNotNull(name)) {
			return null;
		}
		//get dataService
		FactoryDATAService factoryDATAService=FactoryDATA.getInstance();
		SingleStockDATAService singleStockDATAService=factoryDATAService.getSingleStockDATAService();
		//get favor info
		FavoriteBLService favoriteBLService=FavoriteBLFactory.getFavoriteBLService();
		StockListBLService stockListBLService=StockListBLFactory.getStockListBLService();
		if (!stockListBLService.searchLegal(name)) {
			return null;
		}
		//
		try{
			Stock ans=singleStockDATAService
					.getOperation(name, CalendarHelper.getPreviousDay(Calendar.getInstance()));
			if (favoriteBLService.checkIsFavored(name)) {
				ans.setFavor(true);
			}
			return ans;
		}
		catch(NetFailedException e){
			TipPop.showTip();
			return getSingleStockInfo(name);
		}
	}

	@Override
	public List<Stock> getSingleStockDeal(String name, Calendar start, Calendar end) {
		//name check
				if (!Checker.checkStringNotNull(name)) {
					return new ArrayList<Stock>();
				}
				if (start==null||end==null) {
					return new ArrayList<>();
				}
				//year check
				if (start.after(end)) {
					return new ArrayList<>();
				}
				start=CalendarHelper.getPreviousDay(start);
				//
				FactoryDATAService factoryDATAService=FactoryDATA.getInstance();
				SingleStockDATAService singleStockDATAService=factoryDATAService.getSingleStockDATAService();
				//
				try {
					List<Stock> ans=singleStockDATAService
							.getStockAmongDate(name, start, end);
					for (int i = 1; i < ans.size(); i++) {
						ans.get(i).setYesterday(ans.get(i-1));
					}
					return ans.subList(1, ans.size());
				} catch (NetFailedException e) {
					TipPop.showTip();
					return getSingleStockDeal(name, start, end);
				}
	}

	

}
