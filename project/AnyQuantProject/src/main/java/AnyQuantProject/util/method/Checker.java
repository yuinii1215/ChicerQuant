package AnyQuantProject.util.method;

import java.util.Calendar;

/** 
*AnyQuantProject//AnyQuantProject.util.method//Checker.java
* @author  cxworks 
* @date 创建时间：2016年3月3日 下午5:23:03 
*/

public class Checker {
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
}
