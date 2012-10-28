package ie.cit.patrick.service.impl;


import java.text.SimpleDateFormat;

import org.springframework.transaction.annotation.Transactional;

import ie.cit.patrick.Member;
import ie.cit.patrick.dao.BookDao;
import ie.cit.patrick.dao.MemberDao;
import ie.cit.patrick.dao.MemberLoansBookDao;
import ie.cit.patrick.service.LibraryService;
import ie.cit.patrick.service.Workers;

@Transactional
public class LibraryServiceImpl implements LibraryService {
	MemberDao memberDao;
	BookDao bookDao;
	MemberLoansBookDao memberLoansBookDao;
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	
	public LibraryServiceImpl(){
		
	}
	public LibraryServiceImpl(MemberDao memberDao, BookDao bookDao, MemberLoansBookDao memberLoansBookDao) {
		super();
		this.memberDao = memberDao;
		this.bookDao = bookDao;
		this.memberLoansBookDao = memberLoansBookDao;
	}
	
	@Transactional
	@Override
	public boolean loanBook(int memberId, int bookId) {

		if (checkBookAvailable(bookId) && checkBookAllowance(memberId)) {
			memberLoansBookDao.loanBook(memberId, bookId);
			bookDao.makeBookUnavailable(bookId);
			return true;
		} else {
			return false;
		}
	}
	
	@Transactional
	@Override
	public boolean loanBook(int memberId, String bookISBN) {

		if (checkBookAvailable(bookISBN) && checkBookAllowance(memberId)) {
			int bookId = bookDao.findBookByISBN(bookISBN).getId();
			memberLoansBookDao.loanBook(memberId, bookId);
			bookDao.makeBookUnavailable(bookId);
			return true;
		} else {
			return false;
		}
	}
	
	@Transactional
	@Override
	public boolean returnBook(int memberId, int bookId) {

		if (memberId == checkWhoLoanedBook(bookId)
				&& !(checkBookAvailable(bookId))) {
			double fine = calculateFine(memberId, bookId);
			memberLoansBookDao.returnBook(bookId, memberId, fine);
			applyfine(memberId, fine);
			bookDao.makeBookAvailable(bookId);
			return true;
		} else {
			return false;
		}
	}
	
	@Transactional
	@Override
	public boolean returnBook(int memberId, String bookISBN) {

		int bookId = bookDao.findBookByISBN(bookISBN).getId();
		if (memberId == checkWhoLoanedBook(bookId)
				&& !(checkBookAvailable(bookId))) {
			double fine = calculateFine(memberId, bookId);
			memberLoansBookDao.returnBook(bookId, memberId, fine);
			applyfine(memberId, fine);
			bookDao.makeBookAvailable(bookId);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public double calculateFine(int memberId, int bookId) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		double fine = 0;
		String loanDate = df.format(memberLoansBookDao.findLoanedBookByID(bookId).getLoan_date().getTime());
		int daysBetween = Workers.daysBetween(loanDate, Workers.dateReturn(0));

		fine = daysBetween*5-70;
			if(fine>0){
			}else{
				fine = 0;
			}
			//doubles give crazy results, damn floating points
		return fine/100;
	}
	
	
	@Override
	public void applyfine(int memberId, double fine) {
		
		Member member = memberDao.findMemberById(memberId);
		member.setBalance(member.getBalance()+fine);
		memberDao.updateMember(member);
	}
	
	@Override
	public boolean validateMemberId(int id) {
		
		try{
			memberDao.findMemberById(id).getName();
			} catch(NullPointerException nPE){
			return false;
			
			}
		return true;

	}
	
	public boolean memberActive(int memberId){
		
		if(memberDao.findMemberById(memberId).isActive()){
			return true;
		} else{		
		return false;
		}
		
	}

	@Override
 	public boolean validateBookId(int id) {
		
		try{
			bookDao.findBookById(id).getAuthor();
			} catch(NullPointerException nPE){
			return false;
			
			}
		return true;
		
		
	}

	@Override
	public boolean validateBookISBN(String ISBN) {
		
		try{
			bookDao.findBookByISBN(ISBN).getAuthor();
			} catch(NullPointerException nPE){
			return false;
			
			}
		return true;
	}

	@Override
	public String memberNamefromID(int id) {
		
		return memberDao.findMemberById(id).getName();
	}

	@Override
	public String bookNamefromID(int id) {

		return bookDao.findBookById(id).getTitle();
	}

	@Override
	public String bookNamefromISBN(String ISBN) {
		
		return bookDao.findBookByISBN(ISBN).getTitle();
	}

	public boolean checkBookAvailable(int bookId){
		
		if(bookDao.findBookById(bookId).isAvailable()){
			return true;
		}
		else{		
		return false;
		}
	}
	
	public boolean checkBookAvailable(String bookISBN){
		try{
		if(bookDao.findBookByISBN(bookISBN).isAvailable()){
			return true;
		}
		else{		
		return false;
		}
		}catch(NullPointerException nPE){
			return false;
		}
	}

	public int checkWhoLoanedBook(int bookId){
		int memberId = 0;
		
		try{
		memberId = memberLoansBookDao.findLoanedBookByID(bookId).getMemberID();
		}catch(NullPointerException nPE){
			return memberId;			
		}
		return memberId;
		
	}
	
	@Override
	public String getMembersWithFines() {
		double total = 0;
		String output="\n\n###### All Members With Fines #####\n\n";
		try{
		for(Member member : memberDao.allMembersWithFines()){
			
			output = output + member.toString() + "\n";
			total = total + member.getBalance();
			
		}}catch (NullPointerException nPE){
			
			output = output + "\n\n All Members currently have a balance of zero \n\n";

			
		}
		
		output = output + "\n###### TOTAL FINES OUTSTANDING = Û" + total + " #####\n\n";
		
		return output;
	}
	
	@Override
	public String getMembersWithFines(int memberId) {
		String output="";
		if(memberDao.MembersWithFines(memberId).isEmpty()){
			output = "\n### Member "+ memberId + " currently has no outstanding fines.\n\n";
		}else {for(Member member : memberDao.MembersWithFines(memberId)){
			
			output = output + member.toString() + "\n";
			
		}
		}
		return output;
	}
	
	@Override
	public boolean validateMemberName(String name) {
		
		try{
			memberDao.findMemberByTitle(name).get(0);
			} catch(IndexOutOfBoundsException iOOBE){
			return false;
			
			}
		return true;
	}
	
	@Override
	public String getMembersWithFines(String memberName) {
		String output="";
		if(memberDao.MembersWithFines(memberName).isEmpty()){
			output = "\n### All Members named "+ memberName + " currently have no outstanding fines.\n\n";
		}else{
			for(Member member : memberDao.MembersWithFines(memberName)){
				
				output = output + member.toString() + "\n";
				
			}
		}
		return output;
	}
	
	@Override
	public boolean checkBookAllowance(int memberId) {
		
		if(memberDao.findMemberById(memberId).getBookAllowance()<=
				memberLoansBookDao.findCurrentLoansByMemberId(memberId).size()){
			return false;			
		}		
		
		return true;
	}
	
	@Override
	public boolean checkIfAnyBooksAreLoaned(int memberId) {
		
		if(!memberLoansBookDao.findCurrentLoansByMemberId(memberId).isEmpty()){
			return true;
		} else{		
		return false;
		}
	}
}
