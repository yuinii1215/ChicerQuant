package webProject.server.daily;

import java.sql.*;
import java.util.Calendar;
import java.util.List;

import AnyQuantProject.data.factoryDATA.FactoryDATA;
import AnyQuantProject.dataService.factoryDATAService.FactoryDATAService;
import AnyQuantProject.dataService.realDATAService.singleStockDATAService.SingleStockDATAService;
import AnyQuantProject.util.constant.R;
import AnyQuantProject.util.exception.NetFailedException;
import AnyQuantProject.util.method.CalendarHelper;
import AnyQuantProject.util.method.IOHelper;
/**
* AnyQuantProject/webProject.server.daily/DailyCalculate.java
* @author cxworks
* 2016年5月5日 上午9:12:17
*/


public class SetupSQL {
	public static void main(String[] args) throws SQLException {
		setup();
	}
	
	public static void setup(){
		//run from 2006/01/01 to now
		Connection connection=getConn();
		Calendar calendar=Calendar.getInstance();
		calendar.set(2005, 1, 1);
		Calendar se=Calendar.getInstance();
		//
		List<String> id=(List<String>) IOHelper.read(R.CachePath	, R.StockNameFile);
		
		for (int i = 0; i < id.size(); i++) {
			String stockid=id.get(i);
			try {
				List<Stock> data=CalculateCore.initBase(stockid, calendar, se);
				createStockTable(connection, stockid);
				insertStock(connection, stockid, data);
				System.out.println(i+1+"/"+id.size()+" "+stockid+" completed");
			} catch (NetFailedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private static Connection getConn(){
		String driver="com.mysql.cj.jdbc.Driver";
		String url="jdbc:mysql://localhost:3306/chicer?useUnicode=true&characterEncoding=utf-8&useSSL=false";
		String username="chicer";
		String password="chicer";
		Connection conn = null;
	    try {
	        Class.forName(driver); //classLoader,加载对应驱动
	        conn = (Connection) DriverManager.getConnection(url, username, password);
//	        conn.setAutoCommit(false);
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return conn;
	}
	
	public static String createStockTable(Connection connection,String table){
		String tableName=table;
		String sql=Q.Create+tableName+Q.tailBase;
//		System.out.println(sql);
		try {
			PreparedStatement preparedStatement=connection.prepareStatement(sql);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tableName;
	}
	public static void insertStock(Connection connection,String table,List<Stock> stocks){
		String sql=Q.Insert+table+Q.insTailBase;
		try {
			PreparedStatement preparedStatement=connection.prepareStatement(sql);
			for	(int i=0;i<stocks.size();i++){
				addbatch(preparedStatement, stocks.get(i));
				preparedStatement.executeUpdate();
				
			}
			
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	private static void addbatch(PreparedStatement preparedStatement,Stock stock) throws SQLException{
		int i=1;
		preparedStatement.setTimestamp(i++, stock.timestamp);
		preparedStatement.setString(i++, stock.stock_name);
		preparedStatement.setDouble(i++, stock.open);
		preparedStatement.setDouble(i++, stock.high);
		preparedStatement.setDouble(i++, stock.low);
		preparedStatement.setDouble(i++, stock.close);
		preparedStatement.setInt(i++, stock.volumn);
		preparedStatement.setDouble(i++, stock.adj_price);
		preparedStatement.setDouble(i++, stock.turnover);
		preparedStatement.setDouble(i++, stock.pe_ttm);
		preparedStatement.setDouble(i++, stock.pb);
		preparedStatement.setString(i++, stock.industry);
		preparedStatement.setDouble(i++, stock.PMA5_day);
		preparedStatement.setDouble(i++, stock.PMA5_week);
		preparedStatement.setDouble(i++, stock.PMA5_month);
		preparedStatement.setDouble(i++, stock.PMA10_day);
		preparedStatement.setDouble(i++, stock.PMA10_week);
		preparedStatement.setDouble(i++, stock.PMA10_month);
		preparedStatement.setDouble(i++, stock.PMA30_day);
		preparedStatement.setDouble(i++, stock.PMA30_week);
		preparedStatement.setDouble(i++, stock.PMA30_month);
		preparedStatement.setDouble(i++, stock.RSI6);
		preparedStatement.setDouble(i++, stock.RSI12);
		preparedStatement.setDouble(i++, stock.RSI24);
		preparedStatement.setDouble(i++, stock.BIAS6);
		preparedStatement.setDouble(i++, stock.BIAS12);
		preparedStatement.setDouble(i++, stock.BIAS24);
		preparedStatement.setDouble(i++, stock.K);
		preparedStatement.setDouble(i++, stock.D);
		preparedStatement.setDouble(i++, stock.J);
		preparedStatement.setDouble(i++, stock.DEA);
		preparedStatement.setDouble(i++, stock.DIF);
		preparedStatement.setDouble(i++, stock.MACHBar);
		preparedStatement.setDouble(i++, stock.poly);
	}
}







