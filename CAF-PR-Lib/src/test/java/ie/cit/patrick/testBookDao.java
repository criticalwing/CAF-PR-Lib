package ie.cit.patrick;

import static org.junit.Assert.*;

import java.util.GregorianCalendar;

import ie.cit.patrick.dao.BookDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( { "classpath:ie/cit/patrick/app-context.xml" } )

public class testBookDao {
	
	@Autowired
	ApplicationContext context;

	@Test
	public void testAddCheckDeleteBook() {
		
		GregorianCalendar date = new GregorianCalendar(1990,01,01);
		Book x = new Book("Foucalts Pendulum", "Umberto Eco", "Mariners Books", 
							"015603297X", date, false);
		
		System.out.println(x.toString());
		
		
	}

}
