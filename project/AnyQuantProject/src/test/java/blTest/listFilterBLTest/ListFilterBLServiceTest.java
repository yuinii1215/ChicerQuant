package blTest.listFilterBLTest;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import AnyQuantProject.bl.factoryBL.ListFilterBLFactory;
import AnyQuantProject.blService.listFilterBLService.ListFilterBLService;
import AnyQuantProject.dataStructure.Stock;

/** 
*AnyQuantProject//blTest.listFilterBLTest//ListFilterBLServiceTest.java
* @author  cxworks 
* @date 创建时间：2016年3月3日 下午4:08:17 
*/

public class ListFilterBLServiceTest {
	ListFilterBLService listFilterBLService;
	List<Stock> stocks;
	Calendar comp;
	@Before
	public void setUp() throws Exception {
		comp=Calendar.getInstance();
		comp.add(Calendar.MONTH, 5);
		listFilterBLService=ListFilterBLFactory.getListFilterBLService();
		stocks=new ArrayList<>();
		for (int i = 1; i < 11; i++) {
			Stock temp=new Stock();
			temp.setName("sh600000");
			temp.setFavor(false);
			temp.setClose(i*0.25);
			temp.setFlow(i*1000);
			Calendar day=Calendar.getInstance();
			day.add(Calendar.MONTH, i);
			temp.setDate("");
			stocks.add(temp);
		}
	}

	@Test
	public void testFilterStocksByFieldAmong() {
		assertEquals(4, listFilterBLService.filterStocksByFieldAmong(stocks, "close", 0.25, 1.0).size());
	}

	@Test
	public void testFilterStocksByFieldGreater() {
		assertEquals(8, listFilterBLService.filterStocksByFieldGreater(stocks, "close", 0.5).size());
	}

	@Test
	public void testFilterStocksByFieldLess() {
		assertEquals(2, listFilterBLService.filterStocksByFieldLess(stocks, "close", 0.65).size());
	}

	@Test
	public void testFilterStocksByFieldEqual() {
		assertEquals(0, listFilterBLService.filterStocksByFieldEqual(stocks, "close", 0).size());
	}

	@Test
	public void testFilterStocksByDateAmong() {
		assertEquals(5, listFilterBLService.filterStocksByDateAmong(stocks, Calendar.getInstance(), comp).size());
	}

	@Test
	public void testFilterStocksByDateGreater() {
		assertEquals(5, listFilterBLService.filterStocksByDateGreater(stocks, comp).size());
	}

	@Test
	public void testFilterStocksByDateLess() {
		assertEquals(5, listFilterBLService.filterStocksByDateLess(stocks, comp).size());
	}

	@Test
	public void testFilterStocksByDateEqual() {
		assertEquals(1, listFilterBLService.filterStocksByDateEqual(stocks, comp).size());
	}

}
