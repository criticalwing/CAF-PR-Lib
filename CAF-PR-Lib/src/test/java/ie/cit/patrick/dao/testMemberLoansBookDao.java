package ie.cit.patrick.dao;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import ie.cit.patrick.MemberLoansBook;
import ie.cit.patrick.dao.MemberLoansBookDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( { "classpath:/ie/cit/patrick/test-context.xml" } )
public class testMemberLoansBookDao {
	@Autowired
	MemberLoansBookDao memberLoansBookDao;
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testLoanBook() {
			
		memberLoansBookDao.loanBook(232, 627);
			
		MemberLoansBook x = memberLoansBookDao.findByBookIDandMemberID(232, 627);
		
		String d = df.format(x.getLoan_date().getTime());
		Calendar cal = new GregorianCalendar();
		df.setCalendar(cal);	
		String d2 = df.format(cal.getTime());
	
		assertEquals(d,d2);
	
	}
	
	@Test
	public void testReturnBook(){
		memberLoansBookDao.returnBook(835, 9332, 0.25);
		MemberLoansBook x = memberLoansBookDao.findByBookIDandMemberID(9332, 835);
		String d = df.format(x.getReturn_date().getTime());
		Calendar cal = new GregorianCalendar();
		df.setCalendar(cal);
		String d2 = df.format(cal.getTime());
		double fine = x.getFine();
		assertEquals(fine,0.25,0);
		assertEquals(d,d2);
		
	}

	@Test
	public void testReturnBook2() throws ParseException{
		
		memberLoansBookDao.returnBook(835, 9332, 0.2);
		MemberLoansBook x = memberLoansBookDao.findByBookIDandMemberID(9332, 835);
		String d = df.format(x.getLoan_date().getTime());
		Calendar cal = new GregorianCalendar();
		df.setCalendar(cal);
		double fine = x.getFine();	
		
		assertEquals(fine,0.2,0);
		assertEquals(d,"2012-08-10");
		
	}

	@Test
	public void testFindLoanedBookById(){
		
		int memberId = memberLoansBookDao.findLoanedBookByID(1236).getMemberID();
		
		assertEquals(9332,memberId);
		
		
		
	}
	
	@Test
	public void  testFindByBookIDandMemberID(){
		
		String x = df.format(memberLoansBookDao.findByBookIDandMemberID(3425, 3421).getLoan_date().getTime());
		
		String y = "2012-10-25";
		assertEquals(x,y);
		
		
		
		
	}
	
	@Test
	public void  testFindByBookIDMemberIDReturnDate(){
		
		String x = df.format(memberLoansBookDao.findByBookIDMemberIDReturnDate(3425, 3421,"2012-11-08").getLoan_date().getTime());
		String y = "2012-10-25";
		assertEquals(x,y);
		
	}
	
	@Test
	public void  testFindLoanedBookByID(){
		String x = df.format(memberLoansBookDao.findLoanedBookByID(835).getLoan_date().getTime());
		String y = "2012-08-10";
		
		assertEquals(x,y);
		
		
	}

	public void  testFindCurrentLoansByMemberId(){
		
	}
	
	
}
