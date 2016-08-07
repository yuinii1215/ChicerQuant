package AnyQuantProject.util.method;

import java.util.Calendar;
import java.util.List;

import AnyQuantProject.dataStructure.Stock;

/** 
*AnyQuantProject//AnyQuantProject.util.method//Checker.java
* @author  cxworks 
* @date 创建时间：2016年3月3日 下午5:23:03 
*/

public class Checker {
	public static boolean checkStock(Stock stock){
		if (stock.getClose()==0&&stock.getOpen()==0&&stock.getHigh()==0&&stock.getLow()==0) {
			return false;
		}
		return true;
	} 
	
	public static boolean checkStringNotNull(String tar){
		if (tar==null||tar.equalsIgnoreCase("")) {
			return false;
		}
		return true;
	}
	
	public static boolean checkCalendarBefore(Calendar tar){
		if (tar==null||tar.after(Calendar.getInstance())) {
			return false;
		}
		return true;
	}
	public static boolean checkListNotNull(List<?> src){
		if(src==null||src.isEmpty()){
			return false;
		}
		return true;
	}
}
