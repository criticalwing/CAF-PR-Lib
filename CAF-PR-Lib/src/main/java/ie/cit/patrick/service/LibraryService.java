package ie.cit.patrick.service;

public interface LibraryService {
	
	boolean loanBook(int membersId , int bookId);
	
	boolean loanBook(int membersId, String bookISBN);
	
	boolean returnBook(int memberId, int bookId);
	
	boolean returnBook(int memberId, String bookISBN);
	
	void applyfine(int bookId, int memberId);
	
	String memberNamefromID(int id);
	
	String bookNamefromID(int id);
	
	String bookNamefromISBN(String ISBN);
	
	boolean validateMemberId(int id);
	
	boolean validateBookId(int id);
	
	boolean validateBookISBN(String ISBN);
	
	boolean checkBookAvailable(int bookId);
	
	boolean checkBookAvailable(String bookISBN);
	
	boolean memberActive(int memberId);
	
	int checkWhoLoanedBook(int bookId);
}