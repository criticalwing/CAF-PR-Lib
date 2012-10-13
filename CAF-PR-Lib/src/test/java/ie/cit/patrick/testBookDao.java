package ie.cit.patrick;

import static org.junit.Assert.*;
import ie.cit.patrick.dao.BookDao;
import java.util.GregorianCalendar;
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

public class testBookDao {
	
	@Autowired
	ApplicationContext context;
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testAddFindDeleteBook() {
		
		context = new ClassPathXmlApplicationContext("classpath:/ie/cit/patrick/app-context.xml");
		BookDao bookDao;
				
		GregorianCalendar date = new GregorianCalendar(1990,02,01);
		Book x = new Book("Foucalts Pendulum", "Umberto Eco", "Mariners Books", 
							"015603297X", date, false);
				
		bookDao = (BookDao)context.getBean("bookDao");
		
		bookDao.addBook(x);
		
		Book y = bookDao.findBookByTitle("Foucalts Pendulum");
						
		assertEquals(y.getAuthor(), x.getAuthor());
		
		bookDao.deleteBook(y);
		
		assertNull(bookDao.findBookByTitle("Foucalts Pendulum"));
		
						
	}
	
	@Test
	public void testUpdateBook(){
		
		context = new ClassPathXmlApplicationContext("classpath:/ie/cit/patrick/app-context.xml");
		BookDao bookDao;
				
		GregorianCalendar date = new GregorianCalendar(1990,02,01);
		Book x = new Book("Foucalts Pendulum", "Umberto Eco", "Mariners Books", 
							"015603297X", date, false);
		
		bookDao = (BookDao)context.getBean("bookDao");
		
		bookDao.addBook(x);
		
		Book y = bookDao.findBookByTitle("Foucalts Pendulum");
		
		y.setAuthor("Some Other Guy");
		
		bookDao.updateBook(y);
		
		Book z = bookDao.findBookById(y.getId());
		
		assertEquals("Some Other Guy", z.getAuthor());
		
		bookDao.deleteBook(z);
	
	}


}
