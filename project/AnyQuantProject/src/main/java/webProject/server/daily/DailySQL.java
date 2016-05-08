package webProject.server.daily;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.List;
import java.util.Map;


import AnyQuantProject.util.constant.R;
import AnyQuantProject.util.method.CalendarHelper;
import AnyQuantProject.util.method.IOHelper;

/**
* AnyQuantProject/webProject.server.daily/DailySQL.java
* @author cxworks
* 2016年5月8日 上午9:10:29
*/
public class DailySQL {
	static{
		id=(List<String>) IOHelper.read(R.CachePath	, R.StockNameFile);
		chn = (Map<String, String>) IOHelper.read(R.CachePath, R.ChineseNameFile);
		indu = (Map<String, String>) IOHelper.read(R.CachePath, R.IndustryNameFile);
	}
	static List<String> id;
	static Map<String, String> chn;
	static Map<String, String> indu;
	
	public static void dailyStock(Connection connection){
		//create and clear
		DailyStock.setUptoday(connection);
		Calendar calendar=Calendar.getInstance();
		calendar.add(Calendar.YEAR, -2);
		Date date=new Date(calendar.getTimeInMillis());
		//cal
		try {
			for (String stock_id : id) {
				PreparedStatement preparedStatement=connection.prepareStatement(Q.Stock.selectStock);
				
				preparedStatement.setDate(2, date);
				ResultSet resultSet=preparedStatement.executeQuery();
				int col=resultSet.getMetaData().getColumnCount();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	
	static class DailyStock{
		public static void setUptoday(Connection connection){
			//create
			try {
				PreparedStatement preparedStatement=connection.prepareStatement(Q.Stock.createToday);
				preparedStatement.executeUpdate();
				//trunc
				preparedStatement=connection.prepareStatement(Q.Stock.truncate);
				
				preparedStatement.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
