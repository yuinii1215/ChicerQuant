/**
 * 
 */
package AnyQuantProject.dataService.factoryDATAService;

import AnyQuantProject.dataService.realDATAService.IndustryNameDATAService;
import AnyQuantProject.dataService.realDATAService.benchMarkDATAService.BenchMarkDATAService;
import AnyQuantProject.dataService.realDATAService.singleStockDATAService.SingleStockDATAService;
import AnyQuantProject.dataService.realDATAService.stockListDATAService.StockListDATAService;
import AnyQuantProject.dataService.realDATAService.stockListDATAService.TurnoverDATAService;

/**
 * @author G
 *
 */
public interface FactoryDATAService {
	public BenchMarkDATAService getBenchMarkDATAService();
	public SingleStockDATAService getSingleStockDATAService();
	public StockListDATAService getStockListDATAService();
	public IndustryNameDATAService getIndustryDATAService();
	public TurnoverDATAService geTurnoverDATAService();
}
