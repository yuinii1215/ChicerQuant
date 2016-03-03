package blTest.favoriteBL;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import AnyQuantProject.bl.factoryBL.FavoriteBLFactory;
import AnyQuantProject.blService.favoriteBLService.FavoriteBLService;
import AnyQuantProject.dataStructure.Stock;

/** 
*AnyQuantProject//blTest.favoriteBL//FavoriteBLServiceTest.java
* @author  cxworks 
* @date 创建时间：2016年3月3日 下午4:00:05 
*/

public class FavoriteBLServiceTest {
	FavoriteBLService favoriteBLService;
	List<Stock> favor;

	@Before
	public void setUp() throws Exception {
		favoriteBLService=FavoriteBLFactory.getFavoriteBLService();
	}

	@Test
	public void testGetMyFavor() {
		favor=favoriteBLService.getMyFavor();
		assertTrue(true);
	}

	@Test
	public void testFavorStock() {
		if (!favor.isEmpty()) {
			assertTrue(favoriteBLService.favorStock("sh600000").result);
		}
	}

	@Test
	public void testUnFavorStock() {
		if (!favor.isEmpty()) {
			assertTrue(favoriteBLService.unFavorStock("sh600000").result);
		}
	}

}
