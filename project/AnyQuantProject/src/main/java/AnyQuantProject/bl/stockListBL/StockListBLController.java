package AnyQuantProject.bl.stockListBL;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import AnyQuantProject.blService.stockListBLService.StockListBLService;
import AnyQuantProject.data.factoryDATA.FactoryDATA;
import AnyQuantProject.dataService.factoryDATAService.FactoryDATAService;
import AnyQuantProject.dataService.realDATAService.stockListDATAService.stockListDATAService;
import AnyQuantProject.dataStructure.Exchange;
import AnyQuantProject.dataStructure.Stock;

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
		stockListDATAService stockListDATAService=factoryDATAService.getStockListDATAService();
		//get names
		List<String> avaliable=new LinkedList<>();
		return null;
		
	}

}
