package AnyQuantProject.bl.singleStockBL;

import java.util.Calendar;
import java.util.List;

import AnyQuantProject.blService.singleStockDealBLService.SingleStockDealBLService;
import AnyQuantProject.blService.singleStockInfoBLService.SingleStockInfoBLService;
import AnyQuantProject.dataStructure.Stock;

/** 
*AnyQuantProject//AnyQuantProject.bl.singleStockBL//SingleStockBLController.java
* @author  cxworks 
* @date 创建时间：2016年3月2日 下午2:49:07 
*/

public class SingleStockBLController implements SingleStockInfoBLService, SingleStockDealBLService {

	@Override
	public List<Stock> getSingleStockDeal(String name, Calendar year) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Stock getSingleStockInfo(String name) {
		// TODO Auto-generated method stub
		return null;
	}

}
