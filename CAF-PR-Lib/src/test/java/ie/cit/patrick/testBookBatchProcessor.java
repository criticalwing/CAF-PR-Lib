package ie.cit.patrick;

import static org.junit.Assert.*;

import java.util.ArrayList;

import ie.cit.patrick.service.impl.BookBatchProcessor;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( { "classpath:/ie/cit/patrick/app-context.xml" } )

public class testBookBatchProcessor {
	
	ApplicationContext context = new ClassPathXmlApplicationContext("classpath:/ie/cit/patrick/test-context.xml");
	BookBatchProcessor bBP = (BookBatchProcessor) context.getBean("bookBatchProcessor");
	
		
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void basicSetup(){
						
		String x = "BookBatchProcessor [fileLocation=src/test/resources/batchFile, delineator=~]";
		
		String y = bBP.toString();
		
		assertEquals(x, y);
		
		ArrayList<String> lines = bBP.convertFiletoStrings();
		
		String testOutput = "A~Wuthering Heights~Emily Bronte~Wordsworth Editions Ltd~1992-05-01~1853260010";
		
		assertEquals(testOutput, lines.get(0).toString());
		
	}
	
	@Test
	public void testValidateString(){
		
		String[] test = {"A", "Wuthering Heights", "Emily Bronte", "Wordsworth Editions Ltd", "1992-05-01", "1853260010"};
		String[] test2 = {"U", "1236", "*U"};
		String[] test3 = {"A", "Wuthering Heights", "Emily Bronte", "Wordsworth Editions Ltd", "05-01", "1853260010"};
		String[] test4 = {"X", "Wuthering Heights", "Emily Bronte", "Wordsworth Editions Ltd", "1992-05-01", "1853260010"};
		String[] test5 = {"A", "Wuthering Heights", "Emily Bronte", "1992-05-01", "1853260010"};
		String[] test6 = {"X", "Wuthering Heights", "Emily Bronte", "Wordsworth Editions Ltd", "19920501", "1853260010"};
		
		assertTrue(bBP.validateStringArray(test));
		assertTrue(bBP.validateStringArray(test2));
		assertFalse(bBP.validateStringArray(test3));
		assertFalse(bBP.validateStringArray(test4));
		assertFalse(bBP.validateStringArray(test5));
		assertFalse(bBP.validateStringArray(test6));
		
	}
	
	@Test
	public void testProcessLines(){
				
		String x = "Error while processing line 5 of the batch file.\nThe batch file has been processed\n";
		assertEquals(x, bBP.processLines(bBP.convertFiletoStrings()));
		
	}
	
	
	@After
	public void tearDown() throws Exception {
	}
}
