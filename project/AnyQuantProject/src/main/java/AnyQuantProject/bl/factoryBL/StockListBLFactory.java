package AnyQuantProject.bl.factoryBL;

import AnyQuantProject.bl.stockListBL.StockListBLController;
import AnyQuantProject.blService.stockListBLService.StockListBLService;

/** 
*AnyQuantProject//AnyQuantProject.bl.factoryBL//StockListBLFactory.java
* @author  cxworks 
* @date 创建时间：2016年3月2日 下午11:54:14 
*/

public class StockListBLFactory extends FactoryBL {
	private static StockListBLService stockListBLService=null;
	public static StockListBLService getStockListBLService(){
		if (stockListBLService==null) {
			stockListBLService=new StockListBLController();
		}
		return stockListBLService;
	}
}
