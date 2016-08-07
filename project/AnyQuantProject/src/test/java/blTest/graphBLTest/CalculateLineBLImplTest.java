package blTest.graphBLTest;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

import AnyQuantProject.bl.calculateBL.CalculateLineBLImpl;
import AnyQuantProject.blService.kLineBLService.CalculateLineBLService;
import AnyQuantProject.util.method.CalendarHelper;

/**
* AnyQuantProject/blTest.graphBLTest/CalculateLineBLImplTest.java
* @author cxworks
* 2016年5月3日 下午6:53:29
*/
public class CalculateLineBLImplTest {
	CalculateLineBLService service;
	Calendar min;
	Calendar max;
	String name;

	@Before
	public void setUp() throws Exception {
		service=new CalculateLineBLImpl();
		min=CalendarHelper.getPreviousYear(Calendar.getInstance());
		max=Calendar.getInstance();
		name="sh600000";
	}

	@Test
	public void testDrawRSI() {
		assertNotNull(service.drawRSI("sh600000", min, max));
	}

	@Test
	public void testDrawBIAS() {
		assertNotNull(service.drawBIAS(name, min, max));
	}

	@Test
	public void testDrawKDJ() {
		assertNotNull(service.drawKDJ(name, min, max));
	}

	

	@Test
	public void testDrawMACD() {
		assertNotNull(service.drawMACD(name, min, max));
	}

	

}
