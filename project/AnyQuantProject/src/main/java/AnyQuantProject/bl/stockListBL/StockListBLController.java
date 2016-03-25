package AnyQuantProject.bl.stockListBL;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import AnyQuantProject.blService.stockListBLService.StockListBLService;
import AnyQuantProject.data.factoryDATA.FactoryDATA;
import AnyQuantProject.dataService.factoryDATAService.FactoryDATAService;
import AnyQuantProject.dataService.realDATAService.singleStockDATAService.SingleStockDATAService;
import AnyQuantProject.dataService.realDATAService.stockListDATAService.StockListDATAService;
import AnyQuantProject.dataStructure.Exchange;
import AnyQuantProject.dataStructure.Stock;
import AnyQuantProject.util.constant.R;
import AnyQuantProject.util.method.CalendarHelper;
import AnyQuantProject.util.method.IOHelper;

/** 
*AnyQuantProject//AnyQuantProject.bl.stockListBL//StockListBLController.java
* @author  cxworks 
* @date 创建时间：2016年3月2日 下午2:49:40 
*/

public class StockListBLController implements StockListBLService,Runnable {
	private List<String> avaliable;
    private List<String> avaliableCHN;

	private volatile List<Stock> stockData;
	private volatile boolean isAlive=false;
	
	
	public StockListBLController() {
		FactoryDATAService factoryDATAService = FactoryDATA.getInstance();
		StockListDATAService stockListDATAService = factoryDATAService.getStockListDATAService();
		avaliableCHN=new ArrayList<>(3000);
		List<String> szCHN = stockListDATAService.getAllWithChinese(Calendar.getInstance(), Exchange.SZ);
		List<String> shCHN = stockListDATAService.getAllWithChinese(Calendar.getInstance(), Exchange.SH);
		avaliableCHN.addAll(shCHN);
		avaliableCHN.addAll(szCHN);
		avaliable = (List<String>) IOHelper.read(R.CachePath, R.StockNameFile);
		Calendar today = Calendar.getInstance();
		stockData=(List<Stock>) IOHelper.read(R.CachePath, CalendarHelper.getDate(today));
		
		
	}
	
	public boolean shouldInit(){
		return avaliable==null||stockData==null;
	}

	@Override
	public List<Stock> getAllStocks() {

		if (isAlive) {
			
			return this.stockData;
		}
		else {
			return this.stockData;
		}

	}

	@Override
	public List<String> getSearchList() {
		return this.avaliableCHN;
	}

	@Override
	public boolean searchLegal(String target) {
		return avaliable.contains(target);
	}
	@Override
	public void run() {
		
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//init avaliable
		avaliable=new ArrayList<>();
		// get dataService
		FactoryDATAService factoryDATAService = FactoryDATA.getInstance();
		StockListDATAService stockListDATAService = factoryDATAService.getStockListDATAService();
		// get names
		List<String> sz = stockListDATAService.getAllStocks(Calendar.getInstance(), Exchange.SZ);
		List<String> sh = stockListDATAService.getAllStocks(Calendar.getInstance(), Exchange.SH);
		avaliable.addAll(sh);
		avaliable.addAll(sz);
		//save
		IOHelper.save(R.CachePath, R.StockNameFile, (Serializable) avaliable);
		//
		
//		avaliableCHN.forEach(s->System.out.println(s));
		//init data
		stockData=new ArrayList<>();
		isAlive=true;
		SingleStockDATAService singleStockDATAService=factoryDATAService.getSingleStockDATAService();
		Calendar c = Calendar.getInstance();
		avaliable.stream()
		.map(nam->singleStockDATAService.getOperation(nam, CalendarHelper.getPreviousDay(c)))
		.forEach(st->stockData.add(st));
		//save
		IOHelper.save(R.CachePath, CalendarHelper.getDate(c), (Serializable) stockData);
		//delete
		IOHelper.deleteFile(R.CachePath, CalendarHelper.getDate(CalendarHelper.getPreviousDay(c)));
		//
		isAlive=false;
	}

}
