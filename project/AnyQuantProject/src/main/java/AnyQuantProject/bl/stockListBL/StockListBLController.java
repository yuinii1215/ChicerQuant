package AnyQuantProject.bl.stockListBL;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import AnyQuantProject.blService.stockListBLService.StockListBLService;
import AnyQuantProject.data.factoryDATA.FactoryDATA;
import AnyQuantProject.dataService.factoryDATAService.FactoryDATAService;
import AnyQuantProject.dataService.realDATAService.singleStockDATAService.SingleStockDATAService;
import AnyQuantProject.dataService.realDATAService.stockListDATAService.StockListDATAService;
import AnyQuantProject.dataStructure.Exchange;
import AnyQuantProject.dataStructure.Stock;
import AnyQuantProject.util.method.CalendarHelper;

/** 
*AnyQuantProject//AnyQuantProject.bl.stockListBL//StockListBLController.java
* @author  cxworks 
* @date 创建时间：2016年3月2日 下午2:49:40 
*/

public class StockListBLController implements StockListBLService {

	@Override
	public List<Stock> getAllStocks() {
		//get dataService
		FactoryDATAService factoryDATAService=FactoryDATA.getInstance();
		StockListDATAService stockListDATAService=factoryDATAService.getStockListDATAService();
		SingleStockDATAService singleStockDATAService=factoryDATAService.getSingleStockDATAService();
		//get names
		List<String> avaliable=new LinkedList<>();
		List<String> sz=stockListDATAService.getAllStocks(Calendar.getInstance(), Exchange.SZ);
		List<String> sh=stockListDATAService.getAllStocks(Calendar.getInstance(), Exchange.SH);
		avaliable.addAll(sh);
		avaliable.addAll(sz);
		//get data
		List<Stock> ans=avaliable.stream()
				.map(name -> 
				singleStockDATAService.getOperation(name, CalendarHelper.getPreviousDay(Calendar.getInstance())))
				.collect(Collectors.toList());
		return ans;
	}

}
