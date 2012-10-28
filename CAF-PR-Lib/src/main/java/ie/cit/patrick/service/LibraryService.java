package ie.cit.patrick.service;



public interface LibraryService {
	
	boolean loanBook(int membersId , int bookId);
	
	boolean loanBook(int membersId, String bookISBN);
	
	boolean returnBook(int memberId, int bookId);
	
	boolean returnBook(int memberId, String bookISBN);
	
	void applyfine(int memberId, double fine);
	
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

	String getMembersWithFines(int memberId);

	String getMembersWithFines();

	boolean validateMemberName(String name);

	String getMembersWithFines(String memberName);

	boolean checkBookAllowance(int memberId);

	boolean checkIfAnyBooksAreLoaned(int memberId);

	double calculateFine(int memberId, int bookId);
}
