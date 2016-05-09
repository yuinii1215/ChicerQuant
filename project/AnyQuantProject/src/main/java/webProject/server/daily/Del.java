package webProject.server.daily;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.Calendar;
import java.util.List;

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

}
