package webProject.server.daily;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import AnyQuantProject.data.factoryDATA.FactoryDATA;
import AnyQuantProject.dataService.factoryDATAService.FactoryDATAService;
import AnyQuantProject.dataService.realDATAService.singleStockDATAService.SingleStockDATAService;
import AnyQuantProject.dataStructure.Stock;
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
	
	public static void dailyStock(Connection connection,Calendar now){
		//create and clear
		DailyStock.setUptoday(connection);
		Calendar calendar=Calendar.getInstance();
		calendar.setTimeInMillis(now.getTimeInMillis());
		calendar.add(Calendar.YEAR, -1);
		calendar.add(Calendar.MONTH, -2);
		Calendar use=Calendar.getInstance();
		use.setTimeInMillis(now.getTimeInMillis());
		use.add(Calendar.DAY_OF_MONTH, 1);
		Date date=new Date(calendar.getTimeInMillis());
		//
		FactoryDATAService factoryDATAService=FactoryDATA.getInstance();
		SingleStockDATAService service=factoryDATAService.getSingleStockDATAService();
		//cal
		try {
			int j=0;
			for (String stock_id : id) {
				j++;
				PreparedStatement preparedStatement=connection.prepareStatement(Q.Stock.selectStock+stock_id+Q.Stock.seleTail);
				
				preparedStatement.setDate(1, date);
				ResultSet resultSet=preparedStatement.executeQuery();
				List<Stock> src=getStock(resultSet);
				Stock toStock=service.getOperation(stock_id, now);
				if (toStock.getDate()==null) {
					continue;
				}
				src.add(toStock);
				if (src.size()<251) {
					while (src.size()<251) {
						src.add(0, new Stock());
					}
				}
				webProject.server.daily.Stock stock=new webProject.server.daily.Stock();
				//
				stock.industry=indu.get(stock_id);
				stock.stock_name=chn.get(stock_id);
				CalculateCore.trans(stock, toStock);
				//
				List<webProject.server.daily.Stock> temp=new ArrayList<>(1);
				temp.add(stock);
				Calculate.cal(src, temp);
				stock.date=new Date(use.getTimeInMillis());
				//stock
				SetupSQL.insertStock(connection, stock_id, temp);
				//today
				insertToday(connection, temp);
				System.out.println(stock_id+" "+(j)+" completed");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	private static void insertToday(Connection connection,List<webProject.server.daily.Stock> src) throws SQLException{
		PreparedStatement preparedStatement=connection.prepareStatement(Q.Stock.insertToday);
		webProject.server.daily.Stock stock=src.get(0);
		int i=1;
		preparedStatement.setString(i++, stock.stock_id);
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
		preparedStatement.executeUpdate();
	}
	
	private static List<Stock> getStock(ResultSet resultSet) throws SQLException{
		int col=resultSet.getMetaData().getColumnCount();
		List<Stock> stocks=new ArrayList<>(300);
		while(resultSet.next()){
			String date=resultSet.getString("date");
			double open=Double.parseDouble(resultSet.getString("open"));
			double high=Double.parseDouble(resultSet.getString("high"));
			double close=Double.parseDouble(resultSet.getString("close"));
			double low=Double.parseDouble(resultSet.getString("low"));
			int volumn=Integer.parseInt(resultSet.getString("volumn"));
			double adj_price=Double.parseDouble(resultSet.getString("adj_price"));
			double turnover=Double.parseDouble(resultSet.getString("turnover"));
			double pe_ttm=Double.parseDouble(resultSet.getString("pe_ttm"));
			double pb=Double.parseDouble(resultSet.getString("pb"));
			Stock stock=new Stock();
			stock.setDate(date);
			stock.setOpen(open);
			stock.setHigh(high);
			stock.setClose(close);
			stock.setLow(low);
			stock.setAdj_price(adj_price);
			stock.setTurnover(turnover);
			stock.setVolume(volumn);
			stock.setPe_ttm(pe_ttm);
			stock.setPb(pb);
			stocks.add(stock);
		}
		return stocks;
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
