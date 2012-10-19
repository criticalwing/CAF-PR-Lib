package ie.cit.patrick;

import static org.junit.Assert.*;
import ie.cit.patrick.dao.BookDao;
import java.util.GregorianCalendar;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( { "classpath:/ie/cit/patrick/test-context.xml" } )

public class testBookDao {
	
	@Autowired
	BookDao bookDao;
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testAddFindBook() {
				
		GregorianCalendar date = new GregorianCalendar(1990,02,01);
		Book x = new Book("Foucalts Pendulum", "Umberto Eco", "Mariners Books", 
							"015603297X", date, false);
		
		bookDao.addBook(x);
		
		Book y = bookDao.findBookByTitle("Foucalts Pendulum");
						
		assertEquals(y.getAuthor(), x.getAuthor());
						
	}
	
	@Test
	public void testUpdateBook(){
		
		Book y = bookDao.findBookByTitle("Foucalts Pendulum");
		
		y.setAuthor("Some Other Guy");
		
		bookDao.updateBook(y);
		
		Book z = bookDao.findBookById(y.getId());
		
		assertEquals("Some Other Guy", z.getAuthor());
	
	}
	
	@Test
	public void deleteBook(){
		
		Book y = bookDao.findBookByTitle("Foucalts Pendulum");
		
		bookDao.deleteBook(y);
		
		assertNull(bookDao.findBookByTitle("Foucalts Pendulum"));
		
	}


}
