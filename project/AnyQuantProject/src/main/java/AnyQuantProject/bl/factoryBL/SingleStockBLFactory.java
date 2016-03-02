package AnyQuantProject.bl.factoryBL;

import AnyQuantProject.bl.singleStockBL.SingleStockBLController;
import AnyQuantProject.blService.singleStockDealBLService.SingleStockDealBLService;
import AnyQuantProject.blService.singleStockInfoBLService.SingleStockInfoBLService;

/** 
*AnyQuantProject//AnyQuantProject.bl.factoryBL//SingleStockBLFactory.java
* @author  cxworks 
* @date 创建时间：2016年3月2日 下午11:53:32 
*/

public class SingleStockBLFactory extends FactoryBL {
	private static SingleStockInfoBLService singleStockInfoBLService=null;
	private static SingleStockDealBLService singleStockDealBLService=null;
	
	public static SingleStockInfoBLService getSingleStockInfoBLService(){
		if (singleStockInfoBLService==null) {
			SingleStockBLController singleStockBLController=new SingleStockBLController();
			singleStockInfoBLService=singleStockBLController;	
			singleStockDealBLService=singleStockBLController;
		}
		return singleStockInfoBLService;
	}
	
	public static SingleStockDealBLService getSingleStockDealBLService(){
		if (singleStockDealBLService==null) {
			SingleStockBLController singleStockBLController=new SingleStockBLController();
			singleStockInfoBLService=singleStockBLController;	
			singleStockDealBLService=singleStockBLController;
		}
		return singleStockDealBLService;
	}
}
