package ie.cit.patrick.dao;

import java.util.List;

import ie.cit.patrick.MemberLoansBook;


public interface MemberLoansBookDao {
	
	void loanBook(int MemberId, int bookId);
	
	void returnBook(int bookId, int memberId, double fine);
	
	MemberLoansBook findByBookIDandMemberID(int memberID, int bookID);
	
	MemberLoansBook findByBookIDMemberIDReturnDate(int memberID, int bookID, String Date);
		
	MemberLoansBook findLoanedBookByID(int bookID);

	List<MemberLoansBook> findCurrentLoansByMemberId(int memberId);
	

}
