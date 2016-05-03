package blTest.singleStockBLTest;

import static org.junit.Assert.*;import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

import AnyQuantProject.bl.factoryBL.SingleStockBLFactory;
import AnyQuantProject.blService.singleStockDealBLService.SingleStockDealBLService;
import AnyQuantProject.util.method.CalendarHelper;

/** 
*AnyQuantProject//blTest.singleStockBLTest//SingleStockDealBLServiceTest.java
* @author  cxworks 
* @date 创建时间：2016年3月3日 下午4:32:02 
*/

public class SingleStockDealBLServiceTest {
	SingleStockDealBLService deal;

	@Before
	public void setUp() throws Exception {
		deal=SingleStockBLFactory.getSingleStockDealBLService();
	}

	@Test
	public void testGetSingleStockDeal() {
		assertNotNull(deal.getSingleStockDeal("sh600000", Calendar.getInstance()));
		assertNotNull(deal.getSingleStockDeal("sh600000", CalendarHelper.getPreviousDay(Calendar.getInstance()), Calendar.getInstance()));
	}

}
