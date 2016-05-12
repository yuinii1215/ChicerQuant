package webProject.server.daily;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

import AnyQuantProject.data.factoryDATA.FactoryDATA;
import AnyQuantProject.dataService.factoryDATAService.FactoryDATAService;
import AnyQuantProject.dataService.realDATAService.benchMarkDATAService.BenchMarkDATAService;
import AnyQuantProject.util.exception.NetFailedException;
import AnyQuantProject.util.method.CalendarHelper;

/**
* AnyQuantProject/webProject.server.daily/SetupBenchMark.java
* @author cxworks
* 2016年5月10日 下午4:09:27
*/

public class SetupBenchMark {
	
	public static void SetupBenchMark(Connection connection) throws NetFailedException{
		create(connection);
		delete(connection);
		//dataservice
		FactoryDATAService factoryDATAService=FactoryDATA.getInstance();
		BenchMarkDATAService service=factoryDATAService.getBenchMarkDATAService();
		Calendar start=CalendarHelper.convert2Calendar("2001-01-01");
		Calendar now=Calendar.getInstance();
		List<AnyQuantProject.dataStructure.BenchMark> src=service.getBenchMarkAmongDate("hs300", start, now);
		List<BenchMark> dst=BenchCal.trans(src);
		List<Stock> faker=BenchCal.buildFake1(dst);
		Calculate.cal(BenchCal.buildFake2(src), faker);
		BenchCal.transFake(faker, dst);
		insert(connection, dst);
		
		
		
	}
	private static void delete(Connection connection){
		try {
			PreparedStatement preparedStatement=connection.prepareStatement(Q.Bench.truncate);
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	private static void create(Connection connection){
		try {
			PreparedStatement preparedStatement=connection.prepareStatement(Q.Bench.create);
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	private static void insert(Connection connection,List<BenchMark> src){
		for (int i = 0; i < src.size(); i++) {
			try {
				PreparedStatement preparedStatement=connection.prepareStatement(Q.Bench.insert);
				set(preparedStatement, src.get(i));
				preparedStatement.executeUpdate();
				System.out.println((i+1)+" completed");
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
	private static void set(PreparedStatement preparedStatement,BenchMark stock) throws SQLException{
		int i=1;
		preparedStatement.setDate(i++, stock.date);
		preparedStatement.setString(i++, stock.stock_id);
		preparedStatement.setString(i++, "沪深300");
		preparedStatement.setDouble(i++, stock.open);
		preparedStatement.setDouble(i++, stock.high);
		preparedStatement.setDouble(i++, stock.low);
		preparedStatement.setDouble(i++, stock.close);
		preparedStatement.setInt(i++, stock.volumn);
		preparedStatement.setDouble(i++, stock.adj_price);
		preparedStatement.setDouble(i++, stock.transaction);
		preparedStatement.setDouble(i++, stock.marketvalue);
		preparedStatement.setDouble(i++, stock.flow);
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
	

