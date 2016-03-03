package blTest.benchMarkBLTest;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import AnyQuantProject.bl.factoryBL.BenchMarkBLFactory;
import AnyQuantProject.blService.benchMarkBLService.BenchMarkBLService;
import AnyQuantProject.dataStructure.BenchMark;

/** 
*AnyQuantProject//blTest.benchMarkBLTest//BenchMarkBLServiceTest.java
* @author  cxworks 
* @date 创建时间：2016年3月3日 下午3:51:53 
*/

public class BenchMarkBLServiceTest {
	BenchMarkBLService benchMarkBLService;

	@Before
	public void setUp() throws Exception {
		benchMarkBLService=BenchMarkBLFactory.getBenchMarBLService();
	}

	@Test
	public void testGetAllBenchMark() {
		List<BenchMark> benchMarks=benchMarkBLService.getAllBenchMark();
		assertNotNull(benchMarks);
		assertTrue(!benchMarks.isEmpty());
	}

	@Test
	public void testGetBenchMarkInfo() {
		List<BenchMark> benchMarks=benchMarkBLService.getBenchMarkInfo("hs300", Calendar.getInstance());
		assertNotNull(benchMarks);
		assertTrue(!benchMarks.isEmpty());
	}

}
