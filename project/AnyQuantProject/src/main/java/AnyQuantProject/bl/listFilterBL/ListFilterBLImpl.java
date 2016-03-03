package AnyQuantProject.bl.listFilterBL;

import java.util.Calendar;
import java.util.List;

import AnyQuantProject.blService.listFilterBLService.ListFilterBLService;
import AnyQuantProject.dataStructure.Stock;

/** 
*AnyQuantProject//AnyQuantProject.bl.listFilterBL//ListFilterBLImpl.java
* @author  cxworks 
* @date 创建时间：2016年3月2日 下午2:50:18 
*/

public class ListFilterBLImpl implements ListFilterBLService{

	@Override
	public List<Stock> filterStocksByFieldAmong(List<Stock> srcStocks, String columnName, double min, double max) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Stock> filterStocksByFieldGreater(List<Stock> srcStocks, String columnName, double min) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Stock> filterStocksByFieldLess(List<Stock> srcStocks, String columnName, double max) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Stock> filterStocksByFieldEqual(List<Stock> srcStocks, String columnName, double target) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Stock> filterStocksByDateAmong(List<Stock> srcStocks, Calendar min, Calendar max) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Stock> filterStocksByDateGreater(List<Stock> srcStocks, Calendar min) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Stock> filterStocksByDateLess(List<Stock> srcStocks, Calendar max) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Stock> filterStocksByDateEqual(List<Stock> srcStocks, Calendar target) {
		// TODO Auto-generated method stub
		return null;
	}

}
