package ie.cit.patrick.dao;

import java.util.List;

import ie.cit.patrick.MemberLoansBook;

public interface MemberLoansBookDao {
	
	void loanBook(int MemberId, int bookId);
	
	void returnBook(int bookId, int memberId);
	
	MemberLoansBook findByBookIDandMemberID(int bookID, int memberID);
	
	MemberLoansBook findLoanedBookByID(int bookID);
	
	double calculateFine(int memberId, int bookId);

	List<MemberLoansBook> findCurrentLoansByMemberId(int memberId);

}
