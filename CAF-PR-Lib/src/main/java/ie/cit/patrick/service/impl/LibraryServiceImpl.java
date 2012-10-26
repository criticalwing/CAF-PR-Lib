package ie.cit.patrick.service.impl;


import ie.cit.patrick.Member;
import ie.cit.patrick.dao.BookDao;
import ie.cit.patrick.dao.MemberDao;
import ie.cit.patrick.dao.MemberLoansBookDao;
import ie.cit.patrick.service.LibraryService;

public class LibraryServiceImpl implements LibraryService {
	MemberDao memberDao;
	BookDao bookDao;
	MemberLoansBookDao memberLoansBookDao;
	
	public LibraryServiceImpl(){
		
	}
	public LibraryServiceImpl(MemberDao memberDao, BookDao bookDao, MemberLoansBookDao memberLoansBookDao) {
		super();
		this.memberDao = memberDao;
		this.bookDao = bookDao;
		this.memberLoansBookDao = memberLoansBookDao;
	}

	@Override
	public boolean loanBook(int memberId, int bookId) {
		
		if(checkBookAvailable(bookId)){
		memberLoansBookDao.loanBook(memberId, bookId);
		bookDao.makeBookUnavailable(bookId);
		return true;
		} else{
		return false;
		}
	}
	
	@Override
	public boolean loanBook(int memberId, String bookISBN) {
		
		if(checkBookAvailable(bookISBN)){
		int bookId = bookDao.findBookByISBN(bookISBN).getId();
		memberLoansBookDao.loanBook(memberId, bookId);
		bookDao.makeBookUnavailable(bookId);
		return true;
		} else{
		return false;
		}
	}

	@Override
	public boolean returnBook(int memberId, int bookId) {
		
		if(memberId==checkWhoLoanedBook(bookId)){
		memberLoansBookDao.returnBook(bookId, memberId);
		applyfine(bookId, memberId);
		bookDao.makeBookAvailable(bookId);
		return true;
		}else{
		return false;
		}
	}
	
	public boolean returnBook(int memberId, String bookISBN) {
		
		int bookId = bookDao.findBookByISBN(bookISBN).getId();
		if(memberId==checkWhoLoanedBook(bookId)){
		memberLoansBookDao.returnBook(bookId, memberId);
		applyfine(bookId, memberId);
		bookDao.makeBookAvailable(bookId);
		return true;
		}else{
		return false;
		}
	}

	@Override
	public void applyfine(int bookId, int memberId) {
		
		Member member = memberDao.findMemberById(memberId);
		double fine = memberLoansBookDao.findByBookIDandMemberID(memberId, bookId).getFine();
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
		
		output = output + "\n###### TOTAL FINES OUTSTANDING = �" + total + " #####\n\n";
		
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
}
