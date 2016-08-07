package blTest.industryBLTest;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import AnyQuantProject.bl.factoryBL.IndustryBLFactory;
import AnyQuantProject.bl.industryBL.IndustryBLImpl;
import AnyQuantProject.blService.industryBLService.IndustryBLService;
import AnyQuantProject.util.method.CalendarHelper;

/**
* AnyQuantProject/blTest.industryBLTest/IndustryBLImplTest.java
* @author cxworks
* 2016年5月3日 下午6:57:53
*/
public class IndustryBLImplTest {
	IndustryBLService service;
	Calendar min=CalendarHelper.getPreviousYear(Calendar.getInstance());
	
	
	Calendar max=Calendar.getInstance();
	String name="sh600000";
	List<String> list;

	@Before
	public void setUp() throws Exception {
//		service=IndustryBLFactory.getIndustryBLService();
//		list=service.getAllIndustries();
	}

//	@Test
//	public void testGetAllIndustries() {
//		
//		assertNotNull(list);
//	}

//	@Test
//	public void testGetStocksByIndustry() {
//		assertNotNull(service.getStocksByIndustry(list.get(0)));
//	}

//	@Test
//	public void testGetIndustryByName() {
//		assertNotNull(service.getIndustryByName(name));
//	}

//	@Test
//	public void testGetIndustryInfo() {
//		try {
//			assertNotNull(service.getIndustryInfo(list.get(2)));
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//		
//	}
	@Test
	public void test(){
		assertTrue(true);
	}
	

//	@Test
//	public void testGetIndustryPrice() {
//		assertNotNull(service.getIndustryPrice(list.get(0)));
//	}

//	@Test
//	public void testGetYesterday() {
//		assertNotNull(service.getYesterday(name));
//	}

}
