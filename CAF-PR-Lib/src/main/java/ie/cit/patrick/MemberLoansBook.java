package ie.cit.patrick;

import java.util.GregorianCalendar;

public class MemberLoansBook {
	
	private int BookID, MemberID;
	private double fine;
	private GregorianCalendar loan_date, return_date;
	
	public MemberLoansBook(int bookID, int memberID,
			GregorianCalendar loan_date) {
		super();
		BookID = bookID;
		MemberID = memberID;
		this.loan_date = loan_date;
	}
		
	public MemberLoansBook(int bookID, int memberID, double fine,
			GregorianCalendar loan_date, GregorianCalendar return_date) {
		super();
		BookID = bookID;
		MemberID = memberID;
		this.fine = fine;
		this.loan_date = loan_date;
		this.return_date = return_date;
	}
	
	public int getBookID() {
		return BookID;
	}
	public void setBookID(int bookID) {
		BookID = bookID;
	}
	public int getMemberID() {
		return MemberID;
	}
	public void setMemberID(int memberID) {
		MemberID = memberID;
	}
	public double getFine() {
		return fine;
	}
	public void setFine(double fine) {
		this.fine = fine;
	}
	public GregorianCalendar getLoan_date() {
		return loan_date;
	}
	public void setLoan_date(GregorianCalendar loan_date) {
		this.loan_date = loan_date;
	}
	public GregorianCalendar getReturn_date() {
		return return_date;
	}
	public void setReturn_date(GregorianCalendar return_date) {
		this.return_date = return_date;
	}

	
	@Override
	public String toString() {
		return "MemberLoansBook [BookID=" + BookID + ", MemberID=" + MemberID
				+ ", fine=" + fine + ", loan_date=" + loan_date
				+ ", return_date=" + return_date + "]";
	}

	
	
	

}
