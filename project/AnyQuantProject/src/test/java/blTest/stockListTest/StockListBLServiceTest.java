package blTest.stockListTest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import AnyQuantProject.bl.factoryBL.StockListBLFactory;
import AnyQuantProject.blService.stockListBLService.StockListBLService;

/** 
*AnyQuantProject//blTest.stockListTest//StockListBLServiceTest.java
* @author  cxworks 
* @date 创建时间：2016年3月3日 下午4:37:21 
*/

public class StockListBLServiceTest {
	StockListBLService list;

	@Before
	public void setUp() throws Exception {
		list=StockListBLFactory.getStockListBLService();
	}

	@Test
	public void testGetAllStocks() {
		list.getAllStocks();
		assertTrue(true);
	}
	@Test
	public void testGetSearch(){
		assertNotNull(list.getSearchList());
	}
	@Test
	public void testSearchLegal(){
		assertTrue(list.searchLegal("sh600000"));
	}
	@Test
	public void testGetData(){
		try {
			list.getTodayData("sh600000");
		} catch (Exception e) {
			
		}
	}
}
