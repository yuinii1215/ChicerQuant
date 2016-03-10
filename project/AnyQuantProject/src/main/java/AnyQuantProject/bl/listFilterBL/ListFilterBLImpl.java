package AnyQuantProject.bl.listFilterBL;

import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import AnyQuantProject.blService.listFilterBLService.ListFilterBLService;
import AnyQuantProject.dataStructure.Stock;
import AnyQuantProject.util.method.CalendarHelper;
import AnyQuantProject.util.method.Checker;

/** 
*AnyQuantProject//AnyQuantProject.bl.listFilterBL//ListFilterBLImpl.java
* @author  cxworks 
* @date 创建时间：2016年3月2日 下午2:50:18 
*/

public class ListFilterBLImpl implements ListFilterBLService{
	/**
	 * 
	 */
	@Override
	public List<Stock> filterStocksByFieldAmong(List<Stock> srcStocks, String columnName, double min, double max) {
		//check list
		if (!Checker.checkListNotNull(srcStocks)||min>max||!Checker.checkStringNotNull(columnName)) {
			return srcStocks;
		}
		//start
		List<Stock> ans=srcStocks.stream().filter(stock->{
			double num;
			try {
				num = stock.getValueByName(columnName);
				return num>=min&&num<=max;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}).collect(Collectors.toList());
		return ans;
		
	}

	@Override
	public List<Stock> filterStocksByFieldGreater(List<Stock> srcStocks, String columnName, double min) {
		System.out.println(columnName+" min "+min);
		//check
		if (!Checker.checkListNotNull(srcStocks)||!Checker.checkStringNotNull(columnName)) {
			return srcStocks;
		}
		//start filter
		List<Stock> ans=srcStocks.stream().filter(stock->{
			double num;
			try {
				num = stock.getValueByName(columnName);
				return num>=min;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
			
		}).collect(Collectors.toList());
		return ans;
	}

	@Override
	public List<Stock> filterStocksByFieldLess(List<Stock> srcStocks, String columnName, double max) {
		System.out.println(columnName+" max "+max);
		//check
		if (!Checker.checkListNotNull(srcStocks)||!Checker.checkStringNotNull(columnName)) {
			return srcStocks;
		}
		//filter
		List<Stock> ans=srcStocks.stream().filter(stock->{
			double num;
			try {
				num = stock.getValueByName(columnName);
				return num<=max;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}).collect(Collectors.toList());
		return ans;
	}

	@Override
	public List<Stock> filterStocksByFieldEqual(List<Stock> srcStocks, String columnName, double target) {
		//Check
		if (!Checker.checkListNotNull(srcStocks)||!Checker.checkStringNotNull(columnName)) {
			return srcStocks;
		}
		//filter
		List<Stock> ans=srcStocks.stream().filter(stock->{
			double num;
			try {
				num = stock.getValueByName(columnName);
				return num==target;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
			
		}).collect(Collectors.toList());
		return ans;
	}

	@Override
	public List<Stock> filterStocksByDateAmong(List<Stock> srcStocks, Calendar min, Calendar max) {
		//check
		if (!Checker.checkListNotNull(srcStocks)||min==null||max==null||min.after(max)) {
			return srcStocks;
		}
		max.add(Calendar.DAY_OF_MONTH, 1);
		//filter
		List<Stock> ans=srcStocks.stream()
				.filter(stock->
				stock.getDateInCalendar().before(max)&&stock.getDateInCalendar().after(min))
				.collect(Collectors.toList());
		return ans;
	}

	@Override
	public List<Stock> filterStocksByDateGreater(List<Stock> srcStocks, Calendar min) {
		//check
		if (!Checker.checkListNotNull(srcStocks)||min==null) {
			return srcStocks;
		}
		//filter
		List<Stock> ans=srcStocks.stream()
				.filter(stock->stock.getDateInCalendar().after(min))
				.collect(Collectors.toList());
		return ans;
	}

	@Override
	public List<Stock> filterStocksByDateLess(List<Stock> srcStocks, Calendar max) {
		//check
		if (!Checker.checkListNotNull(srcStocks)||max==null) {
			return srcStocks;
		}
		//filter
		List<Stock> ans=srcStocks.stream()
				.filter(stock->stock.getDateInCalendar().before(max))
				.collect(Collectors.toList());
		return ans;
	}

	@Override
	public List<Stock> filterStocksByDateEqual(List<Stock> srcStocks, Calendar target) {
		//check
		if (!Checker.checkListNotNull(srcStocks)||target==null) {
			return srcStocks;
		}
		//filter
		List<Stock> ans=srcStocks.stream()
				.filter(stock->CalendarHelper.compareDateEquals(target, stock.getDateInCalendar()))
				.collect(Collectors.toList());
		return ans;
	}

}
