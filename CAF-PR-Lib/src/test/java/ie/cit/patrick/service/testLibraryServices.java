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
import org.springframework.test.annotation.NotTransactional;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@Transactional
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
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

	@Before
	public void setUp() throws Exception {
	}

	@Test @Transactional
	public void testLoanBook(){
		
		libraryService.loanBook(4567, 746);
		MemberLoansBook mLB = memberLoansBookDao.findByBookIDandMemberID(4567, 746);
		String x = df.format(mLB.getLoan_date().getTime());
		Calendar cal = new GregorianCalendar();
		df.setCalendar(cal);	
		String y = df.format(cal.getTime());
	
		assertEquals(y,x);
		assertFalse(bookDao.findBookById(835).isAvailable());
		
	}
	
	@Test @Transactional
	public void testReturnBook(){

		assertTrue(libraryService.returnBook(9332, 1236));
		double a = memberDao.findMemberById(9332).getBalance();
		Calendar today = Calendar.getInstance();
		double days = (Workers.daysBetween("2012-08-06", df.format(today.getTime())));
		double b = 10.00 + ((days*5-70)/100);
		assertEquals(b,a,0);
		assertTrue(bookDao.findBookById(746).isAvailable());
		
		assertFalse(libraryService.returnBook(232,1236));
		assertFalse(libraryService.returnBook(232,835));
		assertTrue(libraryService.returnBook(9332, 835));
		
	}
	
	@Test
	public void testTransactonRollback(){
		
		assertTrue(libraryService.returnBook(9332, 1236));	
		assertFalse(libraryService.returnBook(9332, 1236));
		
	}
	
	@Test
	public void testCalculateFine(){

		Calendar today = Calendar.getInstance();
		double days = (Workers.daysBetween("2012-10-10", df.format(today.getTime())));
		double x = 3.05 + (((days*5)-70)/100);
		double y = libraryService.calculateFine(9332, 835);
		
		assertEquals(y,x,0.05);
		
	}
	@Test 
	public void testApplyFine(){
		libraryService.applyfine(1234, 10.0);
		assertEquals(1510.00,memberDao.findMemberById(1234).getBalance(),0);
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
	public void testValidateMemberId(){
		
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
	@Test
	public void testCheckBookAvailableInt() {
		assertTrue(libraryService.checkBookAvailable(746));
		assertFalse(libraryService.checkBookAvailable(4454));
	}
	@Test
	public void testCheckBookAvailableString() {
		assertTrue(libraryService.checkBookAvailable("0571144899"));
		assertFalse(libraryService.checkBookAvailable("0571144799"));
	}
	@Test
	public void testCheckWhoLoanedBook() {
		assertEquals(1234, libraryService.checkWhoLoanedBook(627));
	}
	@Test @Transactional
	public void testGetMembersWithFines() {
		
		assertTrue(libraryService.returnBook(1234, 627));
		Calendar today = Calendar.getInstance();
		double days = (Workers.daysBetween("2012-10-10", df.format(today.getTime())));
		double b = 1500 + ((days*5-70)/100);
		String bertieBalance = Double.toString(b);
		String totalBalance = Double.toString(b+82);
		
		String x = "\n\n###### All Members With Fines #####\n\nMember [ 232, Stuart Little, 100 Boardwalk, " +
				"Cork, Cork, 0211234667, Balance= Û20.0 ]\nMember [ 543, Logan Robertson, Hjaltland, " +
				"Pullerick, Cork, 0211234667, Balance= Û10.0 ]\nMember [ 1234, Bertie Ahern, Big House, " +
				"Rich Street, Dublin, 018001231235, Balance= Û" + bertieBalance +" ]\nMember [ 3425, Janet Leigh, " +
				"Hjaltland, Pullerick, Cork, 0211234667, Balance= Û12.0 ]\nMember [ 4568, Peter Smith, " +
				"18, St Johns Close, Macroom, 02212344567, Balance= Û10.0 ]\nMember [ 4569, Vera Saunders, " +
				"24A, Elysian, Cork, 0211234667, Balance= Û20.0 ]\nMember [ 9332, Steven Saunders, 24A, " +
				"Elysian, Cork, 0211234667, Balance= Û10.0 ]\n\n###### TOTAL FINES OUTSTANDING = Û" + totalBalance +  " #####\n\n";

		String y = libraryService.getMembersWithFines();
		assertEquals(x,y);
	}	
	@Test
	public void testGetMembersWithFinesInt() {
		String x = "Member [ 543, Logan Robertson, Hjaltland, Pullerick, Cork, 0211234667, Balance= Û10.0 ]\n";
		assertEquals(x, libraryService.getMembersWithFines(543));
	}
	@Test
	public void testValidateMemberName() {
		assertTrue(libraryService.validateMemberName("Logan"));
		assertFalse(libraryService.validateMemberName("Felicity"));
	}
	@Test
	public void testGetMembersWithFinesString() {
		String x = "Member [ 543, Logan Robertson, Hjaltland, Pullerick, Cork, 0211234667, Balance= Û10.0 ]\n";
		assertEquals(x, libraryService.getMembersWithFines("Logan"));
	}

	
}
