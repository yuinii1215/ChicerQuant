/**
 * 
 */
package AnyQuantProject.data.factoryDATA;

import AnyQuantProject.data.realDATA.benchMarkDATA.BenchMarkDATA;
import AnyQuantProject.data.realDATA.singleStockDATA.SingleStockDATA;
import AnyQuantProject.data.realDATA.stockListDATA.StockListDATA;
import AnyQuantProject.dataService.factoryDATAService.FactoryDATAService;
import AnyQuantProject.dataService.realDATAService.benchMarkDATAService.BenchMarkDATAService;
import AnyQuantProject.dataService.realDATAService.singleStockDATAService.SingleStockDATAService;
import AnyQuantProject.dataService.realDATAService.stockListDATAService.StockListDATAService;

/**
 * @author G
 *
 */
public class FactoryDATA implements FactoryDATAService{
	
	private static FactoryDATA  fData;
	
	private FactoryDATA() {}
	
	public static FactoryDATA getInstance(){
		if (fData == null) {
			fData = new FactoryDATA();
		}
		return fData;
	}

	
	@Override
	public BenchMarkDATAService getBenchMarkDATAService() {
		return BenchMarkDATA.getInstance();
	}

	
	@Override
	public SingleStockDATAService getSingleStockDATAService() {
		return SingleStockDATA.getInstance();
	}

	
	@Override
	public StockListDATAService getStockListDATAService() {
		return StockListDATA.getInstance();
	}
	
	
	
}
