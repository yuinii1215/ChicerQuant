/**
 * 
 */
package AnyQuantProject.util.method;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @author G
 *
 */
public class CalendarHelper {
	/**
	 * 将calendar对象转换成YYYY-MM-dd格式的string
	 * @param date
	 * @return
	 */
	public static String getDate(Calendar date){
		if (date==null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
		return sdf.format(date.getTime());
	}
	
	/**
	 * 得到所给日期的前一天
	 * @param date
	 * @return
	 */
	public static Calendar getPreviousDay(Calendar date){
		if (date==null) {
			return null;
		}
		date.add(Calendar.DAY_OF_MONTH, -1);
		return date;
	}
	/**
	 * 将日期中除了年的信息都归零
	 * @param date
	 * @return
	 */
	public static Calendar zeroYear(Calendar date){
		if (date==null) {
			return null;
		}
		date.set(date.get(Calendar.YEAR), 0, 0, 0, 0);
		return date;
	}
	/**
	 * 获得下一年
	 * @param date
	 * @return
	 */
	public static Calendar getNextYear(Calendar date){
		if (date==null) {
			return null;
		}
		Calendar res=Calendar.getInstance();
		res.setTimeInMillis(date.getTimeInMillis());
		res.add(Calendar.YEAR, 1);
		return res;
	}
	
	//test
	public static void main(String[] args) {
		Calendar c = Calendar.getInstance();
		System.out.println(CalendarHelper.getDate(c));
		CalendarHelper.getPreviousDay(c);
		System.out.println(CalendarHelper.getDate(c));
	}
}
