/**
 * 
 */
package AnyQuantProject.dataService.factoryDATAService;

import AnyQuantProject.dataService.benchMarkDATAService.BenchMarkDATAService;
import AnyQuantProject.dataService.singleStockDATAService.SingleStockDATAService;
import AnyQuantProject.dataService.stockListDATAService.stockListDATAService;

/**
 * @author G
 *
 */
public interface FactoryDATAService {
	public BenchMarkDATAService getBenchMarkDATAService();
	public SingleStockDATAService getSingleStockDATAService();
	public stockListDATAService getStockListDATAService();
}
