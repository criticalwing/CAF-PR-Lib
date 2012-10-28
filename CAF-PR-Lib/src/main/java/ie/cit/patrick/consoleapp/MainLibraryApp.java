package ie.cit.patrick.consoleapp;

import ie.cit.patrick.service.LibraryService;
import ie.cit.patrick.service.Workers;
import java.util.Scanner;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainLibraryApp {
	
	static ApplicationContext context;
	static Scanner keys = new Scanner(System.in);
	static LibraryService lS;

	public static void main(String[] args) {
		
		context = new ClassPathXmlApplicationContext("classpath:ie/cit/patrick/consoleapp-context.xml");
		lS = (LibraryService)context.getBean("libraryService");
		runMenu();

	}
	
	public static void runMenu(){

			String input;
			do {
				System.out.print(Workers.DisplayMenu(Workers.convertFiletoStrings("src/main/resources/ie/cit/patrick/menutext/StartScreenLoan")));
				input = keys.next();
				if (!handleChoice(input)) {
					System.out.println("\nPlease choose a correct option (A,B, or X)");
				}
			} while(input.compareToIgnoreCase("X")!=0);
			System.out.println("GoodBye\n");
			keys.close();
		
	}

	private static boolean handleChoice(String choice) {

		choice = choice.toUpperCase();
		int memberId;
		String input = "";
		switch(choice){
			
		case "A": 	memberId = getID();
					if(lS.checkBookAllowance(memberId)){
							if(lS.memberActive(memberId)){
									do {
										System.out.print("\n### Member: " + lS.memberNamefromID(memberId) + " ###");
										System.out.print(Workers.DisplayMenu(Workers.convertFiletoStrings("src/main/resources/ie/cit/patrick/menutext/BookLoan")));
										input = keys.next();
										if (!loanBookMenu(input, memberId)) {
											System.out.println("\nPlease choose a correct option (A,B, or X)");
										}
									//check if user has press x to return to previous screen
									} while(input.compareToIgnoreCase("X")!=0||!loanBookMenu(input, memberId));
							} else{
								System.out.print("\n### NO MORE LOANS POSSIBLE Member: " + lS.memberNamefromID(memberId) + " : STATUS INACTIVE ###\n");
							}
					} else{
						System.out.print("\n### NO MORE LOANS POSSIBLE Member: " + lS.memberNamefromID(memberId) + " : ALREADY HAS FULL MAX BOOKS LOANED ###\n");
						return true;
					}
					break;
		case "B": 	memberId = getID();
					if(lS.checkIfAnyBooksAreLoaned(memberId)){
					do {
						System.out.print("\n### Member: " + lS.memberNamefromID(memberId) + " ###");
						System.out.print(Workers.DisplayMenu(Workers.convertFiletoStrings("src/main/resources/ie/cit/patrick/menutext/BookReturn")));
						input = keys.next();
						if (!returnBookMenu(input, memberId)) {
							System.out.println("\nPlease choose a correct option (A,B, or X)");
						}
					} while(input.compareToIgnoreCase("X")!=0||!loanBookMenu(input, memberId));
					}else {
						System.out.print("\n### Member: " + lS.memberNamefromID(memberId) + " : HAS NO BOOKS CURRENTLY OUT ON LOAN ###\n");
					}
					break;
		case "x": 	break;
		case "X":	break;
		default: 	return false;

		}
		return true;
		}
	
	private static boolean loanBookMenu(String input, int memberID){
		
		input = input.toUpperCase();
		switch(input){
					
		case "A":
			int bookId = getBookID();
			if (lS.loanBook(memberID, bookId)) {
				System.out.print("Book: " + "\"" + lS.bookNamefromID(bookId)
						+ "\"" + " loaned to " + lS.memberNamefromID(memberID)
						+ "\n");
				// complete check after book is loaned to see if max allowance reached
				if (!lS.checkBookAllowance(memberID)) {
					System.out.print("\n### NO MORE LOANS POSSIBLE Member: "
							+ lS.memberNamefromID(memberID)
							+ " : NOW HAS MAX BOOKS LOANED ###\n");
					return true;
				}
				// check why they can't loan a book and print result
			} else if (!lS.checkBookAllowance(memberID)) {
				System.out.print("\n### NO MORE LOANS POSSIBLE Member: "
						+ lS.memberNamefromID(memberID)
						+ " : NOW HAS MAX BOOKS LOANED ###\n");
				return true;
			} else {
				System.out.print("Book: " + "\"" + lS.bookNamefromID(bookId)
						+ "\"" + " is unavailable\n");
			}
			break;
					

		case "B":
			String bookISBN = getBookISBN();
			if (lS.loanBook(memberID, bookISBN)) {
				System.out.print("Book: " + "\""
						+ lS.bookNamefromISBN(bookISBN) + "\"" + " loaned to "
						+ lS.memberNamefromID(memberID) + "\n");
				// complete check after book is loaned to see if max allowance reached
				if (!lS.checkBookAllowance(memberID)) {
					System.out.print("\n### NO MORE LOANS POSSIBLE Member: "
							+ lS.memberNamefromID(memberID)
							+ " : NOW HAS MAX BOOKS LOANED ###\n");
					return true;
				}
				// check why they can't loan a book and print result
			} else if (!lS.checkBookAllowance(memberID)) {
				System.out.print("\n### NO MORE LOANS POSSIBLE Member: "
						+ lS.memberNamefromID(memberID)
						+ " : NOW HAS MAX BOOKS LOANED ###\n");
				return true;
			} else {
				System.out.print("Book: " + "\""
						+ lS.bookNamefromISBN(bookISBN) + "\""
						+ " is unavailable\n");
			}
break;
		case "x": 	break;
		case "X":	break;
		default: 	return false;
		}
		return true;
	}

	private static boolean returnBookMenu(String input, int memberID){
		
		input = input.toUpperCase();
		switch (input) {

		case "A":
			int bookId = getBookID();
			if (lS.returnBook(memberID, bookId)) {
				System.out.print("Book: " + "\"" + lS.bookNamefromID(bookId)
						+ "\"" + " returned\n");
				// check why they can't return a book and print result
				if(lS.checkIfAnyBooksAreLoaned(memberID)){
					System.out.print("\n### Member: " + lS.memberNamefromID(memberID) + " : HAS NOW RETURNED ALL THEIR BOOKS ###\n");
				}
			} else {
				if (!(lS.checkBookAvailable(bookId))) {
					System.out.print("Error, Book: " + "\""
							+ lS.bookNamefromID(bookId) + "\""
							+ " is not loaned out to "
							+ lS.memberNamefromID(memberID) + "\n");
				} else {
					System.out.print("Book: " + "\""
							+ lS.bookNamefromID(bookId) + "\""
							+ " has already been returned\n");
				}
			}
			break;
		case "B":
			String bookISBN = getBookISBN();
			if (lS.returnBook(memberID, bookISBN)) {
				System.out.print("Book: " + "\""
						+ lS.bookNamefromISBN(bookISBN) + "\"" + " returned\n");
				if(lS.checkIfAnyBooksAreLoaned(memberID)){
					System.out.print("\n### Member: " + lS.memberNamefromID(memberID) + " : HAS NOW RETURNED ALL THEIR BOOKS ###\n");
				}
				// check why they can't return a book and print result
			} else {
				if (!(lS.checkBookAvailable(bookISBN))) {
					System.out.print("Error, Book: " + "\""
							+ lS.bookNamefromISBN(bookISBN) + "\""
							+ " is not loaned out to "
							+ lS.memberNamefromID(memberID) + "\n");
				} else {
					System.out.print("Book: " + "\""
							+ lS.bookNamefromISBN(bookISBN) + "\""
							+ " has already been returned\n");
				}
			}
			break;
		case "x":
			break;
		case "X":
			break;
		default:
			return false;
		}
		return true;
	}
	
	//collect and validate Member ID
 	private static int getID(){
			
			String tempId="";
			int id=-1;
			do{
				System.out.print("\nPlease enter member id ==>\n");
				tempId=keys.next();
					if(Workers.validateInt(tempId)){
						id = Integer.parseInt(tempId);
					}
					if(!lS.validateMemberId(id)){
						System.out.print("ERROR, No Member exists with id No:" + id);
					}
			}while(!lS.validateMemberId(id));
		
			return id;
	}
	
	//collect and validate Book ID
	private static int getBookID(){
		
		String tempId="";
		int id=-1;
		do{
			System.out.print("\nPlease enter book id ==>\n");
			tempId=keys.next();
				if(Workers.validateInt(tempId)){
					id = Integer.parseInt(tempId);
				}
				if(!lS.validateBookId(id)){
					System.out.print("ERROR, No book available with id No:" + id);
				}
		}while(!lS.validateBookId(id));
	
		return id;
}


	//collect and validate Book ISBN
	private static String getBookISBN(){
		
		String ISBN="";
		do{
			System.out.print("\nPlease enter book ISBN ==>\n");
			ISBN=keys.next();
				if(!lS.validateBookISBN(ISBN)){
					System.out.print("ERROR, No book exists with ISBN:" + ISBN);
				}
		}while(!lS.validateBookISBN(ISBN));
	
		return ISBN;
}


}
