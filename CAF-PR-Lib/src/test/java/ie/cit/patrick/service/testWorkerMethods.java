package ie.cit.patrick.service;

import static org.junit.Assert.*;

import ie.cit.patrick.dao.MemberDao;
import ie.cit.patrick.service.Workers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( { "classpath:ie/cit/patrick/test-context.xml" } )
public class testWorkerMethods {

	@Autowired
	MemberDao memberDao;
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testconvertFiletoStrings() {
		
		String x = Workers.convertFiletoStrings("src/test/resources/testMenu").toString();
		String y = "[testMenu, testMenu, testMenu]";
	
		assertEquals(x,y);
	
	}
	
	@Test
	public void testDisplayMenu(){
		String a = Workers.DisplayMenu(Workers.convertFiletoStrings("src/test/resources/testMenu"));
		String b = "testMenu\ntestMenu\ntestMenu\n";
		
		assertEquals(a,b);
	
	
	}
	
	@Test
	public void testValidateInt(){
		
		assertTrue(Workers.validateInt("1234"));
		assertFalse(Workers.validateInt("abcd"));
		
		
	}
	@Test
	public void testValidateDouble(){
		
		assertTrue(Workers.validateDouble("1.54"));
		assertFalse(Workers.validateDouble("abcd"));
		
	}
	@Test
	public void testSReturn(){
	
		assertEquals(Workers.sReturn(1), "");
		assertEquals(Workers.sReturn(0), "s");
		assertEquals(Workers.sReturn(2), "s");
		
	}
	@Test
	public void testValidateDate(){
		
		assertTrue(Workers.validateDate("1999-05-12"));
		assertFalse(Workers.validateDate("12-199"));
		assertFalse(Workers.validateDate("abcd"));

	}
	@Test
	public void testDaysBetween(){
		
		int x = 0;
		x = Workers.daysBetween("2012-10-05", "2012-10-10");
		
		assertEquals(5,x,0);
		
		
	}
}
