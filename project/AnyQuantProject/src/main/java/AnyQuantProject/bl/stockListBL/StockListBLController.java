package AnyQuantProject.bl.stockListBL;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import AnyQuantProject.bl.factoryBL.IndustryBLFactory;
import AnyQuantProject.blService.industryBLService.IndustryBLService;
import AnyQuantProject.blService.stockListBLService.StockListBLService;
import AnyQuantProject.data.factoryDATA.FactoryDATA;
import AnyQuantProject.dataService.factoryDATAService.FactoryDATAService;
import AnyQuantProject.dataService.realDATAService.IndustryNameDATAService;
import AnyQuantProject.dataService.realDATAService.singleStockDATAService.SingleStockDATAService;
import AnyQuantProject.dataService.realDATAService.stockListDATAService.StockListDATAService;
import AnyQuantProject.dataStructure.Exchange;
import AnyQuantProject.dataStructure.Stock;
import AnyQuantProject.ui.net.TipPop;
import AnyQuantProject.util.constant.R;
import AnyQuantProject.util.exception.NetFailedException;
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
		
		avaliableCHN=getCHN();
		
		
		avaliable = (List<String>) IOHelper.read(R.CachePath, R.StockNameFile);
		Calendar today = Calendar.getInstance();
		stockData=(List<Stock>) IOHelper.read(R.CachePath, CalendarHelper.getDate(today));
		
		
	}
	private List<String> getCHN(){
		List<String> ans=new ArrayList<>(3000);
		FactoryDATAService factoryDATAService = FactoryDATA.getInstance();
		StockListDATAService stockListDATAService = factoryDATAService.getStockListDATAService();
		try {
			List<String> szCHN = stockListDATAService.getAllWithChinese(Calendar.getInstance(), Exchange.SZ);
			List<String> shCHN = stockListDATAService.getAllWithChinese(Calendar.getInstance(), Exchange.SH);
			ans.addAll(shCHN);
			ans.addAll(szCHN);
			return ans;
		} catch (NetFailedException e) {
			TipPop.showTip();
			return getCHN();
		}
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
	
	private List<String> getName(){
		List<String> ans=new ArrayList<>(3000);
		// get dataService
		FactoryDATAService factoryDATAService = FactoryDATA.getInstance();
		StockListDATAService stockListDATAService = factoryDATAService.getStockListDATAService();
		try {
			List<String> sz = stockListDATAService.getAllStocks(Calendar.getInstance(), Exchange.SZ);
			List<String> sh = stockListDATAService.getAllStocks(Calendar.getInstance(), Exchange.SH);
			ans.addAll(sz);
			ans.addAll(sh);
			return ans;
		} catch (NetFailedException e) {
			TipPop.showTip();
			return getName();
		}
	}
	private List<Stock> getStocks(){
		// get dataService
		FactoryDATAService factoryDATAService = FactoryDATA.getInstance();
		SingleStockDATAService singleStockDATAService=factoryDATAService.getSingleStockDATAService();
		IndustryBLService industryBLService=IndustryBLFactory.getIndustryBLService();
		Calendar date=Calendar.getInstance();
		List<Stock> stocks=new ArrayList<>(avaliable.size());
		try {
			for (int i = 0; i < avaliable.size(); i++) {
				String name=avaliable.get(i);
				Stock today=singleStockDATAService.getOperation(name, date);
				today.setYesterday(industryBLService.getYesterday(name));
				stocks.add(today);
			}
			return stocks;
		} catch (NetFailedException e) {
			TipPop.showTip();
			return getStocks();
		}
		
	}
	@Override
	public void run() {
		isAlive=true;
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			System.exit(0);
		}
		//init avaliable
		avaliable=getName();
		
		//save
		IOHelper.save(R.CachePath, R.StockNameFile, (Serializable) avaliable);
		//
		//init data
		stockData=getStocks();
		Calendar c=Calendar.getInstance();
		//save
		IOHelper.save(R.CachePath, CalendarHelper.getDate(c), (Serializable) stockData);
		//
		isAlive=false;
	}

}
