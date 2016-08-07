package AnyQuantProject.bl.factoryBL;

import AnyQuantProject.bl.benchMarkBL.BenchMarkBLImpl;
import AnyQuantProject.bl.kLineBL.BenchmarkKLineBLImpl;
import AnyQuantProject.bl.kLineBL.StockKLineBLImpl;
import AnyQuantProject.blService.kLineBLService.BenchmarkKLineBLService;
import AnyQuantProject.blService.kLineBLService.StockKLineBLService;

/** 
* AnyQuantProject//AnyQuantProject.bl.factoryBL//KLineBLFactory.java
* @author  cxworks 
* @date 创建时间：2016年3月14日 下午11:29:59 
*/

public class KLineBLFactory extends FactoryBL {
	private static StockKLineBLService stockKLineBLService;
	private static BenchmarkKLineBLService benchmarkKLineBLService;
	public static StockKLineBLService getStockKLineBLService(){
		if (stockKLineBLService==null) {
			stockKLineBLService=new StockKLineBLImpl();
		}
		return stockKLineBLService;
	}
	public static BenchmarkKLineBLService getBenchmarkBLService(){
		if (benchmarkKLineBLService==null) {
			benchmarkKLineBLService=new BenchmarkKLineBLImpl();
		}
		return benchmarkKLineBLService;
	}
}
