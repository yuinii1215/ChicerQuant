package blTest.singleStockBLTest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import AnyQuantProject.bl.factoryBL.SingleStockBLFactory;
import AnyQuantProject.blService.singleStockInfoBLService.SingleStockInfoBLService;

/** 
*AnyQuantProject//blTest.singleStockBLTest//SingleStockInfoBLServiceTest.java
* @author  cxworks 
* @date 创建时间：2016年3月3日 下午4:34:34 
*/

public class SingleStockInfoBLServiceTest {
	SingleStockInfoBLService info;
	@Before
	public void setUp() throws Exception {
		info=SingleStockBLFactory.getSingleStockInfoBLService();
	}

	@Test
	public void testGetSingleStockInfo() {
//		assertNotNull(info.getSingleStockInfo("sh600000"));
	}

}
