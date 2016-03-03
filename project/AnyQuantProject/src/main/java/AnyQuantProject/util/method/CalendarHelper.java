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
	 * 将calendar对象转换成YYYY-mm-dd格式的string
	 * @param date
	 * @return
	 */
	public static String getDate(Calendar date){
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-mm-dd");
		return sdf.format(date.getTime());
	}
	
	/**
	 * 得到所给日期的前一天
	 * @param date
	 * @return
	 */
	public static Calendar getPreviousDay(Calendar date){
		date.add(Calendar.DAY_OF_MONTH, -1);
		return date;
	}
	
	//test
	public static void main(String[] args) {
		Calendar c = Calendar.getInstance();
		System.out.println(CalendarHelper.getDate(c));
		CalendarHelper.getPreviousDay(c);
		System.out.println(CalendarHelper.getDate(c));
	}
}
