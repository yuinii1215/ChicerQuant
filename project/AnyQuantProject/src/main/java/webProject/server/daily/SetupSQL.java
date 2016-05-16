package webProject.server.daily;

import java.io.Serializable;
import java.sql.*;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import AnyQuantProject.data.factoryDATA.FactoryDATA;
import AnyQuantProject.dataService.realDATAService.stockListDATAService.StockListDATAService;
import AnyQuantProject.dataService.realDATAService.stockListDATAService.TurnoverDATAService;
import AnyQuantProject.dataStructure.Exchange;
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
	
	static List<String> id=(List<String>) IOHelper.read(R.CachePath	, R.StockNameFile);;
	static Map<String, String> indu=(Map<String, String>) IOHelper.read(R.CachePath, R.IndustryNameFile);
	static Map<String, Double> share;
	static Map<String, Double> guben;
	public static void main(String[] args) throws SQLException {
		Connection connection=getConn();
		Calendar calendar=CalendarHelper.convert2Calendar(args[0]);
		int i=Integer.parseInt(args[1]);
//		DailySQL.dailyStock(connection,calendar);
//		industry_stock(connection);
//		setup(connection);
//		Del.delStock(id, connection, calendar);
//		try {
//			SetupBenchMark.SetupBenchMark(connection);
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
		industry(connection, calendar,i,args[2]);
		connection.close();
	}
	private static void buildindustry(Connection connection,String name){
		try {
			PreparedStatement preparedStatement=connection.prepareStatement(Q.Create+name+Q.Industry.tailIndustry);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO: handle exception

			e.printStackTrace();
		}
	}
	
	public static void industry(Connection connection,Calendar now,int index,String begin){
		//
		FactoryDATA data=FactoryDATA.getInstance();
		StockListDATAService stockListDATAService=data.getStockListDATAService();
		
		TurnoverDATAService service=FactoryDATA.getInstance().geTurnoverDATAService();
		share=(Map<String, Double>) IOHelper.read(R.CachePath, R.SHARES);
		guben=(Map<String, Double>) IOHelper.read(R.CachePath, R.NonRest);
		if (share==null||guben==null) {
			try {
				if (id==null) {
					id=stockListDATAService.getAllStocks(Calendar.getInstance(), Exchange.SH	);
					List<String> strings=stockListDATAService.getAllStocks(Calendar.getInstance()	,Exchange.SZ);
					id.addAll(strings);
				}
				share=new HashMap<>();
				guben=new HashMap<>();
				for (int i = 0; i < id.size(); i++) {
					double t=service.getTotalShares(id.get(i));
					double g=service.getNonrestFloatShares(id.get(i));
					guben.put(id.get(i), g);
					share.put(id.get(i), t);
				}
			} catch (Exception e2) {
				e2.printStackTrace();
				System.out.println("error");
				return;
			}
			System.out.println("shit");
		}
		
		//
		IOHelper.save(R.CachePath, R.SHARES, (Serializable)share);
		IOHelper.save(R.CachePath, R.NonRest, (Serializable)guben);
		//
		Map<String, List<String>> map=indu.entrySet().stream().map(e->e.getKey()).collect(Collectors.groupingBy(s->indu.get(s)));
		Set<Entry<String, List<String>>> set=map.entrySet();
		Iterator<Entry<String, List<String>>> iterator=set.iterator();
		//build
		for (int i = 0; i < index; i++) {
			iterator.next();
		}
		//
		boolean last=true;
		while (iterator.hasNext()) {
			Entry<String, List<String>> entry=iterator.next();
			System.out.println(entry.getValue().size());
			//
			buildindustry(connection, entry.getKey());
			//
			Calendar yesterday;
			if (last) {
				yesterday=CalendarHelper.convert2Calendar(begin);
				last=false;
			}else {
				yesterday=CalendarHelper.convert2Calendar("2005-12-29");
			}
			
			Map<String,Stock> yest=getStock(connection, yesterday, entry.getValue());
			yesterday.add(Calendar.DAY_OF_MONTH, 1);
			while (yesterday.before(now)) {
				Map<String,Stock> today=getStock(connection, yesterday, entry.getValue());
				while (today==null||today.isEmpty()) {
					yesterday.add(Calendar.DAY_OF_MONTH, 1);
					today=getStock(connection, yesterday, entry.getValue());
				}
				//
				double total=today.entrySet().stream().mapToDouble(s->s.getValue().turnover*s.getValue().adj_price).sum();
				double pure=today.entrySet().stream().mapToDouble(s->guben.get(s.getKey())).sum();
				double open,close,max,min,lprice,lupdown,yestotal;
				int volumn=0;
				String leaderid=null,leadername=null;
				open=close=max=min=lprice=yestotal=0;
				lupdown=-20000;
				for(int i=0;i<entry.getValue().size();i++){
					String stid=entry.getValue().get(i);
					Stock yes=yest.get(stid);
					Stock tod=today.get(stid);
					if (tod==null||yes==null) {
						continue;
					}
					double per=tod.turnover*tod.adj_price/total;
					open+=tod.open*per;
					close+=tod.close*per;
					max+=tod.high*per;
					min+=tod.low*per;
					volumn+=tod.volumn*per;
					yestotal+=yes.turnover*yes.adj_price;
					double t=(tod.close-yes.close)/yes.close;
					if (t>lupdown) {
						lupdown=t;
						leaderid=tod.stock_id;
						leadername=tod.stock_name;
						lprice=tod.adj_price;
					}
				}
				int sum=today.size();
				if (close==0&&open==0) {
					yesterday.add(Calendar.DAY_OF_MONTH, 1);
					continue;
				}
				double updown=(total-yestotal)/yestotal;
				if (yestotal<1) {
					updown=0;
				}
				//
				try {
					PreparedStatement preparedStatement=connection.prepareStatement(Q.Insert+entry.getKey()+Q.Industry.insert);
					int tt=1;
					preparedStatement.setString(tt++, entry.getKey());
					preparedStatement.setDate(tt++, new Date(CalendarHelper.getAfterDay(yesterday).getTimeInMillis()));
					preparedStatement.setDouble(tt++, open);
					preparedStatement.setDouble(tt++, close);
					preparedStatement.setDouble(tt++, max);
					preparedStatement.setDouble(tt++, min);
					preparedStatement.setInt(tt++, volumn);
					preparedStatement.setDouble(tt++, updown);
					preparedStatement.setDouble(tt++, pure);
					preparedStatement.setDouble(tt++, total);
					preparedStatement.setInt(tt++, sum);
					preparedStatement.setString(tt++, leaderid);
					preparedStatement.setString(tt++, leadername);
					preparedStatement.setDouble(tt++, lprice);
					preparedStatement.setDouble(tt++, lupdown);
					preparedStatement.executeUpdate();
					connection.close();
					connection=getConn();
				} catch (SQLException e2) {
					e2.printStackTrace();
				}
				yesterday.add(Calendar.DAY_OF_MONTH, 1);
				yest=today;
			}
			System.out.println(entry.getKey()+" completed");
			
			
		}
		
	}
	
	public static Map<String,Stock> getStock(Connection connection,Calendar calendar,List<String> ids){
		Map<String, Stock> ans=new HashMap<>(ids.size());
		Date date=new Date(calendar.getTimeInMillis());
		int c=0;
		for (int i = 0; i < ids.size(); i++) {
			String sql=Q.Industry.selectStock+ids.get(i)+Q.Industry.seleTail;
			try {
				PreparedStatement preparedStatement=connection.prepareStatement(sql);
				preparedStatement.setDate(1, date);
				ResultSet resultSet=preparedStatement.executeQuery();
				while (resultSet.next()) {
					double open=Double.parseDouble(resultSet.getString("open"));
					double high=Double.parseDouble(resultSet.getString("high"));
					double close=Double.parseDouble(resultSet.getString("close"));
					double low=Double.parseDouble(resultSet.getString("low"));
					int volumn=Integer.parseInt(resultSet.getString("volumn"));
					double adj_price=Double.parseDouble(resultSet.getString("adj_price"));
					double pe_ttm=Double.parseDouble(resultSet.getString("pe_ttm"));
					double pb=Double.parseDouble(resultSet.getString("pb"));
					String stockname=resultSet.getString("stock_name");
					Stock stock=new Stock();
					stock.stock_id=ids.get(i);
					stock.setOpen(open);
					stock.setHigh(high);
					stock.setClose(close);
					stock.setLow(low);
					stock.setAdj_price(adj_price);
					stock.volumn=volumn;
					stock.setPe_ttm(pe_ttm);
					stock.setPb(pb);
					stock.stock_name=stockname;
					stock.setStock_id(ids.get(i));
					stock.turnover=share.get(ids.get(i));
					ans.put(ids.get(i), stock);
					
				}
			} catch (SQLException e) {
				e.printStackTrace();
				continue;
			}
		}
		return ans;
		
	}
	
	public static void industry_stock(Connection connection){
		try {
			PreparedStatement preparedStatement=connection.prepareStatement(Q.CreateIndustry_Stock);
			preparedStatement.executeUpdate();
			//
			for (String string : id) {
//				String ch=chn.get(string);
				String ind=indu.get(string);
				PreparedStatement preparedStatement2=connection.prepareCall(Q.insertIndustry_Stock);
				preparedStatement2.setString(1, string);
//				preparedStatement2.setString(2, ch);
				preparedStatement2.setString(3, ind);
				preparedStatement2.executeUpdate();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	public static void setup(Connection connection){
		//run from 2006/01/01 to now
		Calendar calendar=Calendar.getInstance();
		calendar.set(2005, 1, 1);
		Calendar se=Calendar.getInstance();
		//
		
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
	//
	private static Connection getConn(){
		String driver="com.mysql.cj.jdbc.Driver";
//		String url="jdbc:mysql://10.66.115.75:3306/chicer?useUnicode=true&characterEncoding=utf-8&useSSL=false";

		String url="jdbc:mysql://10.66.171.146:3306/chicer?useUnicode=true&characterEncoding=utf-8&useSSL=false";
		String username="chicer";
		String password="chicer2016";
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
		
		
			for (int i = 0; i < stocks.size(); i++) {
				try {
					PreparedStatement preparedStatement=connection.prepareStatement(sql);
					addbatch(preparedStatement, stocks.get(i));
				} catch (Exception e) {
					continue;
				}
				
				
			}
			
				
			
			
		
	}
	
	private static void addbatch(PreparedStatement preparedStatement,Stock stock) throws SQLException{
		int i=1;
		preparedStatement.setDate(i++, stock.date);
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
}







