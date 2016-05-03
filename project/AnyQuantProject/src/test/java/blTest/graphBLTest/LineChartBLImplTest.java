package blTest.graphBLTest;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import AnyQuantProject.bl.factoryBL.SingleStockBLFactory;
import AnyQuantProject.bl.graphBL.LineChartBLImpl;
import AnyQuantProject.dataStructure.Stock;
import AnyQuantProject.util.method.CalendarHelper;

/**
* AnyQuantProject/blTest.graphBLTest/LineChartBLImplTest.java
* @author cxworks
* 2016年5月3日 下午6:54:23
*/
public class LineChartBLImplTest {
	LineChartBLImpl service;
	List<Stock> src;
	Calendar min=CalendarHelper.getPreviousYear(Calendar.getInstance());
	
	
	Calendar max=Calendar.getInstance();
	String name="sh600000";
	@Before
	public void setUp() throws Exception {
		service=new LineChartBLImpl();
		src=SingleStockBLFactory.getSingleStockDealBLService().getSingleStockDeal(name, min, max);
	}

	

	@Test
	public void testDrawKLineChart() {
		assertNotNull(service.drawKLineChart(name));
	}

}
