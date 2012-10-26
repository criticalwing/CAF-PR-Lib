package ie.cit.patrick.dao;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import ie.cit.patrick.MemberLoansBook;
import ie.cit.patrick.dao.MemberLoansBookDao;
import ie.cit.patrick.service.Workers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( { "classpath:/ie/cit/patrick/test-context.xml" } )
public class testMemberLoansBookDao {
	@Autowired
	MemberLoansBookDao memberLoansBookDao;
	SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	
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
		memberLoansBookDao.returnBook(627, 232);
		MemberLoansBook x = memberLoansBookDao.findByBookIDandMemberID(232, 627);
		String d = df.format(x.getReturn_date().getTime());
		Calendar cal = new GregorianCalendar();
		df.setCalendar(cal);
		String d2 = df.format(cal.getTime());
		double fine = x.getFine();
		assertEquals(fine,0,0);
		assertEquals(d,d2);
		
	}
	@Test
	public void testCalculateFine(){
		//TODO add in that the return fine is based on todays date
		double x = 3.15;
		double y = memberLoansBookDao.calculateFine(9332, 835);
		
		assertEquals(x,y,0);
		
	}

	@Test
	public void testReturnBook2() throws ParseException{
		//TODO add in that the return fine is based on todays date
		memberLoansBookDao.returnBook(835, 9332);
		MemberLoansBook x = memberLoansBookDao.findByBookIDandMemberID(9332, 835);
		String d = df.format(x.getLoan_date().getTime());
		Calendar cal = new GregorianCalendar();
		df.setCalendar(cal);
		double fine = x.getFine();	
		double cFine = ((Workers.daysBetween("2012-08-10", "2012-10-26")*5)-70);
		
		assertEquals(fine,cFine/100,0);
		assertEquals(d,"10/08/2012");
		
	}

	@Test
	public void testFindLoanedBookById(){
		
		int memberId = memberLoansBookDao.findLoanedBookByID(1236).getMemberID();
		
		assertEquals(9332,memberId);
		
		
		
	}

}
