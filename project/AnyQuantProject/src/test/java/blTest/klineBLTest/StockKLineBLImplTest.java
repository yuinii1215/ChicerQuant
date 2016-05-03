package blTest.klineBLTest;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

import AnyQuantProject.bl.factoryBL.KLineBLFactory;
import AnyQuantProject.blService.kLineBLService.StockKLineBLService;
import AnyQuantProject.util.method.CalendarHelper;

/**
* AnyQuantProject/blTest.klineBLTest/StockKLineBLImplTest.java
* @author cxworks
* 2016年5月3日 下午6:59:20
*/
public class StockKLineBLImplTest {
	StockKLineBLService service;

	Calendar min=CalendarHelper.getPreviousYear(Calendar.getInstance());
	
	
	Calendar max=Calendar.getInstance();
	String name="sh600000";
	@Before
	public void setUp() throws Exception {
		service=KLineBLFactory.getStockKLineBLService();
	}

	@Test
	public void testDayKLineChart() {
		assertNotNull(service.dayKLineChart(name, min, max));
	}

	@Test
	public void testWeekKLineChart() {
		assertNotNull(service.weekKLineChart(name));
	}

	@Test
	public void testMonthKLineChart() {
		assertNotNull(service.monthKLineChart(name));
	}

	@Test
	public void testGetDayAverageLine() {
		assertNotNull(service.getDayAverageLine(name, min, max, 5));
	}

	@Test
	public void testGetWeekAverageLine() {
		assertNotNull(service.getWeekAverageLine(name, 5));
	}

	@Test
	public void testGetMonthAverageLine() {
		assertNotNull(service.getMonthAverageLine(name, 5));
	}

}
