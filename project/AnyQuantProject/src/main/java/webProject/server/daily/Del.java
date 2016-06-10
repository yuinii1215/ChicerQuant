package webProject.server.daily;

import AnyQuantProject.util.method.CalendarHelper;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Stream;

/**
* AnyQuantProject/webProject.server.daily/Del.java
* @author cxworks
* 2016年5月9日 下午8:42:19
*/

public class Del {
	public static void delStock(List<String> id,Connection connection,Calendar calendar){
		Date date=new Date(calendar.getTimeInMillis());
		for (int i = 0; i < id.size(); i++) {
			String idString=id.get(i);
			try {
				PreparedStatement preparedStatement=connection.prepareStatement(Q.Stock.del+idString+Q.Stock.deltail);
				preparedStatement.setDate(1, date);
				preparedStatement.executeUpdate();
			} catch (Exception e) {
				// TODO: handle exception
			}
			System.out.println((i+1)+" completed");
		}
	}
	public  static  void  main(String[] args){
		Calendar t= CalendarHelper.convert2Calendar("2016-02-29");
		System.out.println(t);

		java.util.Date date=new java.util.Date(t.getTimeInMillis());
		Timestamp tt=new Timestamp(date.getTime());
		System.out.println(tt);
		System.out.println(date);
	}
}
