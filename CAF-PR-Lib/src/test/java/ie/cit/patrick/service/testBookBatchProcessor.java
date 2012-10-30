package ie.cit.patrick.service;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import ie.cit.patrick.Book;
import ie.cit.patrick.dao.BookDao;
import ie.cit.patrick.service.impl.BookBatchProcessor;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( { "classpath:ie/cit/patrick/test-context.xml" } )

public class testBookBatchProcessor {
	
	@Autowired
	BookBatchProcessor bookBatchProcessor;
	@Autowired
	BookDao bookDao;
	

	@Test
	public void basicSetup(){
						
		String x = "BookBatchProcessor [fileLocation=src/test/resources/bookbatchFile , delineator=~]";
		
		String y = bookBatchProcessor.toString();
		
		System.out.print(bookBatchProcessor.toString());
		
		assertEquals(x, y);
		
		ArrayList<String> lines = bookBatchProcessor.convertFiletoStrings();
		
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
		
		assertTrue(bookBatchProcessor.validateStringArray(test,11));
		assertTrue(bookBatchProcessor.validateStringArray(test2,12));
		assertFalse(bookBatchProcessor.validateStringArray(test3,13));
		assertFalse(bookBatchProcessor.validateStringArray(test4,14));
		assertFalse(bookBatchProcessor.validateStringArray(test5,15));
		assertFalse(bookBatchProcessor.validateStringArray(test6,16));
		assertFalse(bookBatchProcessor.validateStringArray(test7,17));
		assertFalse(bookBatchProcessor.validateStringArray(test8,18));
		
		bookBatchProcessor.setErrorLog(new ArrayList<String>());
		
	}
	
	@Test
	public void testChanges(){
		bookBatchProcessor.processLines(bookBatchProcessor.convertFiletoStrings());
		GregorianCalendar date = new GregorianCalendar(1992,05,01);
		Book y = new Book("Wuthering Heights", "Emily Bronte", "Wordsworth Editions Ltd", 
							"1853260010", date, true);
		
		Book x = bookDao.findBookByTitle("Wuthering Heights");
		assertEquals(x.getAuthor(),y.getAuthor());
		assertEquals(x.getIsbn(),y.getIsbn());
		assertEquals(x.getPublicationDate(),y.getPublicationDate());
		
		String a = bookDao.findBookByTitle("Dancing at Lughnasa").getAuthor();
		String b = "Brian Friel";
		
		assertEquals(b,a);
		
		assertTrue(bookDao.findBookById(3421).isAvailable());
		assertFalse(bookDao.findBookById(1236).isAvailable());
		
		System.out.print(bookBatchProcessor.report());
		System.out.print(bookBatchProcessor.fullReport());
		System.out.print(bookBatchProcessor.errorLog());
		

	}
	
	@After
	public void tearDown() throws Exception {
	}
}
