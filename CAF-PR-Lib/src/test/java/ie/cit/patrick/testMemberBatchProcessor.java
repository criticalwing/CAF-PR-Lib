package ie.cit.patrick;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import ie.cit.patrick.dao.BookDao;
import ie.cit.patrick.dao.MemberDao;
import ie.cit.patrick.service.impl.MemberBatchProcessor;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( { "classpath:ie/cit/patrick/test-context.xml" } )

public class testMemberBatchProcessor {
	
	@Autowired
	MemberBatchProcessor memberBatchProcessor;
	@Autowired
	MemberDao memberDao;
	
		
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void basicSetup(){
						
		String x = "MemberBatchProcessor [fileLocation=src/test/resources/batchFile, delineator=~]";
		
		String y = memberBatchProcessor.toString();
		
		assertEquals(x, y);
		
		ArrayList<String> lines = memberBatchProcessor.convertFiletoStrings();
		
		String testOutput = "A~Wuthering Heights~Emily Bronte~Wordsworth Editions Ltd~1992-05-01~1853260010";
		
		assertEquals(testOutput, lines.get(0).toString());
		
	}
	
	@Test
	public void testValidateString(){
		
		String[] test = {"A", "Wuthering Heights", "Emily Bronte", "Wordsworth Editions Ltd", "1992-05-01", "1853260010"};
		String[] test2 = {"U", "1236", "*U"};
		String[] test3 = {"A", "Wuthering Heights", "Emily Bronte", "05-01", "1853260010"};
		String[] test4 = {"X", "Wuthering Heights", "Emily Bronte", "Wordsworth Editions Ltd", "1992-05-01", "1853260010"};
		String[] test5 = {"A", "Wuthering Heights", "Emily Bronte", "1992-05-01", "1853260010"};
		String[] test6 = {"X", "Wuthering Heights", "Emily Bronte", "Wordsworth Editions Ltd", "19920501", "1853260010"};
		String[] test7 = {"X", "Word","Wuthering Heights", "Emily Bronte", "Wordsworth Editions Ltd", "19920501", "1853260010"};
		String[] test8 = {"U", "Test", "*A"};
		
		assertTrue(memberBatchProcessor.validateStringArray(test));
		assertTrue(memberBatchProcessor.validateStringArray(test2));
		assertFalse(memberBatchProcessor.validateStringArray(test3));
		assertFalse(memberBatchProcessor.validateStringArray(test4));
		assertFalse(memberBatchProcessor.validateStringArray(test5));
		assertFalse(memberBatchProcessor.validateStringArray(test6));
		assertFalse(memberBatchProcessor.validateStringArray(test7));
		assertFalse(memberBatchProcessor.validateStringArray(test8));
		
	}
	
	@Test
	public void testChanges(){
		memberBatchProcessor.processLines(memberBatchProcessor.convertFiletoStrings());
		GregorianCalendar date = new GregorianCalendar(1992,05,01);
		Book y = new Book("Wuthering Heights", "Emily Bronte", "Wordsworth Editions Ltd", 
							"1853260010", date, true);
		
		Book x = memberDao.findBookByTitle("Wuthering Heights");
		assertEquals(x.getAuthor(),y.getAuthor());
		assertEquals(x.getIsbn(),y.getIsbn());
		assertEquals(x.getPublicationDate(),y.getPublicationDate());
		
		String a = memberDao.findBookByTitle("Dancing at Lughnasa").getAuthor();
		String b = "Brian Friel";
		
		assertEquals(b,a);
		
		assertTrue(memberDao.findBookById(3421).isAvailable());
		assertFalse(memberDao.findBookById(1236).isAvailable());
		
		System.out.print(memberBatchProcessor.report());
		System.out.print(memberBatchProcessor.fullReport());
		System.out.print(memberBatchProcessor.errorLog());
		

	}
	
	@After
	public void tearDown() throws Exception {
	}
}
