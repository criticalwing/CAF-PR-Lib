package ie.cit.patrick.service;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import ie.cit.patrick.MemberLoansBook;
import ie.cit.patrick.dao.BookDao;
import ie.cit.patrick.dao.MemberDao;
import ie.cit.patrick.dao.MemberLoansBookDao;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( { "classpath:/ie/cit/patrick/test-context.xml" } )
public class testLibraryServices {
	
	@Autowired
	LibraryService libraryService;
	@Autowired
	MemberLoansBookDao memberLoansBookDao;
	@Autowired
	BookDao bookDao;
	@Autowired
	MemberDao memberDao;
	SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testLoanBook(){
		
		libraryService.loanBook(4567, 835);
		MemberLoansBook mLB = memberLoansBookDao.findByBookIDandMemberID(4567, 835);
		String x = df.format(mLB.getLoan_date().getTime());
		Calendar cal = new GregorianCalendar();
		df.setCalendar(cal);	
		String y = df.format(cal.getTime());
	
		assertEquals(y,x);
		assertFalse(bookDao.findBookById(835).isAvailable());
		
	}
	
	@Test
	public void testReturnBook(){
		

		libraryService.returnBook(1234, 746);
		double a = memberDao.findMemberById(1234).getBalance();
		assertEquals(1500.05,a,0);
		assertTrue(bookDao.findBookById(746).isAvailable());
		
		//what happens if another member tries to return a book they haven't loaned
		
		assertFalse(libraryService.returnBook(232,1236));
		assertFalse(libraryService.returnBook(232,835));
		assertTrue(libraryService.returnBook(4567, 835));
		
	}
	
	@Test
	public void testIsMemberActive(){
		
		assertTrue(libraryService.memberActive(232));
		assertFalse(libraryService.memberActive(4567));
		
	}
	
	@Test
	public void testMemberNamefromID(){
		
		String x = libraryService.memberNamefromID(232);
		
		assertEquals(x, "Stuart Little");
		
	}
	@Test
	public void testBookNamefromID(){
		
		String x = libraryService.bookNamefromID(3421);
		
		assertEquals(x, "Raspberry Pi User Guide");
		
	}
	@Test
	public void testBookNamefromISBN(){
		
		String x = libraryService.bookNamefromISBN("111846446X");
		
		assertEquals(x, "Raspberry Pi User Guide");	
		
		
	}
	@Test
	public void  testValidateMemberId(){
		
		assertTrue(libraryService.validateMemberId(232));
		assertFalse(libraryService.validateMemberId(9999));		
		
	}
	@Test
	public void testValidateBookId(){
		
		assertTrue(libraryService.validateBookId(3421));
		assertFalse(libraryService.validateBookId(9999));
		
	}
	@Test
	public void testValidateBookISBN(){
		
		assertTrue(libraryService.validateBookISBN("111846446X"));
		assertFalse(libraryService.validateBookISBN("testtest"));
		
	}

}
