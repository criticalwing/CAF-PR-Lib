package ie.cit.patrick.dao;

import ie.cit.patrick.Book;
import ie.cit.patrick.Member;
import ie.cit.patrick.MemberLoansBook;

public interface MemberLoansBookDao {
	
	public void loanBook(int MemberId, int bookId);
	
	public void returnBook(int bookId, int memberId);
	
	public MemberLoansBook findByBookIDandMemberID(int bookID, int memberID);
	
	MemberLoansBook findLoanedBookByID(int bookID);
	
	double calculateFine(int memberId, int bookId);

}
