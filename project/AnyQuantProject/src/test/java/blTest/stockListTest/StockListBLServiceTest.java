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
		assertNotNull(list.getAllStocks());
	}

}
