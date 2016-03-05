package AnyQuantProject.bl.singleStockBL;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import AnyQuantProject.bl.factoryBL.FavoriteBLFactory;
import AnyQuantProject.blService.favoriteBLService.FavoriteBLService;
import AnyQuantProject.blService.singleStockDealBLService.SingleStockDealBLService;
import AnyQuantProject.blService.singleStockInfoBLService.SingleStockInfoBLService;
import AnyQuantProject.data.factoryDATA.FactoryDATA;
import AnyQuantProject.dataService.factoryDATAService.FactoryDATAService;
import AnyQuantProject.dataService.realDATAService.singleStockDATAService.SingleStockDATAService;
import AnyQuantProject.dataStructure.Stock;
import AnyQuantProject.util.method.CalendarHelper;
import AnyQuantProject.util.method.Checker;

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
		//reset year
		year=CalendarHelper.zeroYear(year);
		FactoryDATAService factoryDATAService=FactoryDATA.getInstance();
		SingleStockDATAService singleStockDATAService=factoryDATAService.getSingleStockDATAService();
		//
		try {
			List<Stock> ans=singleStockDATAService
					.getStockAmongDate(name, year, CalendarHelper.getNextYear(year));
			return ans;
		} catch (Exception e) {
			return new ArrayList<>();
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
		//
		try{
			Stock ans=singleStockDATAService
					.getOperation(name, CalendarHelper.getPreviousDay(Calendar.getInstance()));
			if (favoriteBLService.checkIsFavored(name)) {
				ans.setFavor(true);
			}
			return ans;
		}
		catch(Exception e){
			return null;
		}
	}

}
