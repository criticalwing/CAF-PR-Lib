package ie.cit.patrick;

import static org.junit.Assert.*;
import ie.cit.patrick.service.impl.BookBatchProcessor;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( { "classpath:/ie/cit/patrick/app-context.xml" } )

public class testBookBatchProcessor {
	
	@Autowired
	ApplicationContext context;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void basicSetup(){

		context = new ClassPathXmlApplicationContext("classpath:/ie/cit/patrick/app-context.xml");
		
		BookBatchProcessor bBP = new BookBatchProcessor();
		
		System.out.print(bBP.toString());
		
	}
	
	
	
	
	@After
	public void tearDown() throws Exception {
	}
}
