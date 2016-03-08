package AnyQuantProject.bl.stockListBL;

import java.util.ArrayList;
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
import AnyQuantProject.util.method.Checker;

/** 
*AnyQuantProject//AnyQuantProject.bl.stockListBL//StockListBLController.java
* @author  cxworks 
* @date 创建时间：2016年3月2日 下午2:49:40 
*/

public class StockListBLController implements StockListBLService {
	private List<String> avaliable;
        StockListDATAService stockListDATAService;
	public StockListBLController() {
		avaliable=new LinkedList<>();
		// get dataService
		FactoryDATAService factoryDATAService = FactoryDATA.getInstance();
		stockListDATAService = factoryDATAService.getStockListDATAService();
		// get names
		List<String> sz = stockListDATAService.getAllStocks(Calendar.getInstance(), Exchange.SZ);
		List<String> sh = stockListDATAService.getAllStocks(Calendar.getInstance(), Exchange.SH);
		avaliable.addAll(sh);
		avaliable.addAll(sz);
	}

	@Override
	public List<Stock> getAllStocks() {
		FactoryDATAService factoryDATAService = FactoryDATA.getInstance();
		SingleStockDATAService singleStockDATAService=factoryDATAService.getSingleStockDATAService();

		//get names
		List<String> avaliable=new LinkedList<>();
		//TODO
		List<String> sz=stockListDATAService.getAllStocks(Calendar.getInstance(), Exchange.SZ);
//		List<String> sh=stockListDATAService.getAllStocks(Calendar.getInstance(), Exchange.SH);
//		avaliable.addAll(sh);
		avaliable.addAll(sz);
//		
		//TODO

		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, 2015);
		//get data
		List<Stock> ans=avaliable.stream()
				.map(name -> 
				singleStockDATAService.getOperation(name, CalendarHelper.getPreviousDay(c)))
				.collect(Collectors.toList());
		return ans;
	}

	@Override
	public List<String> searchPredict(String target) {
		if (Checker.checkStringNotNull(target)) {
			return new ArrayList<>();
		}
		// get dataService
		FactoryDATAService factoryDATAService = FactoryDATA.getInstance();
		StockListDATAService stockListDATAService = factoryDATAService.getStockListDATAService();
		SingleStockDATAService singleStockDATAService = factoryDATAService.getSingleStockDATAService();
		// get names
		List<String> ans=avaliable
				.stream()
				.filter(nam->nam.contains(target))
				.collect(Collectors.toList());
		return ans;
		
	}

	@Override
	public boolean searchLegal(String target) {
		return avaliable.contains(target);
	}

}
