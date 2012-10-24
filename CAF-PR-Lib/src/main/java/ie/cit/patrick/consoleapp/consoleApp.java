package ie.cit.patrick.consoleapp;

import ie.cit.patrick.Member;
import ie.cit.patrick.dao.BookDao;
import ie.cit.patrick.dao.MemberDao;
import ie.cit.patrick.service.Workers;
import java.util.Scanner;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class consoleApp {
	
	static ApplicationContext context;
	static Scanner keys = new Scanner(System.in);
	static MemberDao memberDao;
	static BookDao bookDao;

	public static void main(String[] args) {
		
		context = new ClassPathXmlApplicationContext("classpath:ie/cit/patrick/consoleapp-context.xml");
		memberDao = (MemberDao)context.getBean("memberDao");
		bookDao = (BookDao)context.getBean("bookDao");
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
		int MemberId;
		String input = "";
		switch(choice){
			
		case "A": 	MemberId = getID();
						do {
							Member current = memberDao.findMemberById(MemberId);
							System.out.print("\n### Member: " + current.getName() + " ###");
							System.out.print(Workers.DisplayMenu(Workers.convertFiletoStrings("src/main/resources/ie/cit/patrick/menutext/BookIDtype")));
							input = keys.next();
							if (!loanBook(input, MemberId)) {
								System.out.println("\nPlease choose a correct option (A,B, or X)");
							}
						} while(input.compareToIgnoreCase("X")!=0||!loanBook(input, MemberId));				
					break;
		case "B": 	MemberId = getID();
					do {
						Member current = memberDao.findMemberById(MemberId);
						System.out.print("\n### Member: " + current.getName() + " ###");
						System.out.print(Workers.DisplayMenu(Workers.convertFiletoStrings("src/main/resources/ie/cit/patrick/menutext/BookIDtype")));
						input = keys.next();
						if (!loanBook(input, MemberId)) {
							System.out.println("\nPlease choose a correct option (A,B, or X)");
						}
					} while(input.compareToIgnoreCase("X")!=0||!loanBook(input, MemberId));				
					break;
		case "x": 	break;
		case "X":	break;
		default: 	return false;

		}
		return true;
		}
	
	private static boolean loanBook(String input, int memberID){
		
		input = input.toUpperCase();
		switch(input){
		
		case "A": 	System.out.print("Book: " + "\"" + bookDao.findBookById(getBookID()).getTitle()
										+ "\"" + " loaned to " + memberDao.findMemberById(memberID).getName() + "\n");
					break;
		case "B":  	System.out.print("Book: " + "\"" + bookDao.findBookByISBN(getBookISBN()).getTitle()
										+ "\"" + " loaned to " + memberDao.findMemberById(memberID).getName() + "\n");
					break;
		case "x": 	break;
		case "X":	break;
		default: 	return false;
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
					if(!validateMemberId(id)){
						System.out.print("ERROR, No Member exists with id No:" + id);
					}
			}while(!validateMemberId(id));
		
			return id;
	}
	public static boolean validateMemberId(int id){
		
		try{
			memberDao.findMemberById(id).getName();
			} catch(NullPointerException nPE){
			return false;
			
			}
		return true;
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
				if(!validateBookId(id)){
					System.out.print("ERROR, No book exists with id No:" + id);
				}
		}while(!validateBookId(id));
	
		return id;
}
	private static boolean validateBookId(int id){
	
		try{
			bookDao.findBookById(id).getAuthor();
			} catch(NullPointerException nPE){
			return false;
			
			}
		return true;

}

	//collect and validate Book ID
	private static String getBookISBN(){
		
		String ISBN="";
		do{
			System.out.print("\nPlease enter book ISBN ==>\n");
			ISBN=keys.next();
				if(!validateBookISBN(ISBN)){
					System.out.print("ERROR, No book exists with ISBN:" + ISBN);
				}
		}while(!validateBookISBN(ISBN));
	
		return ISBN;
}
	private static boolean validateBookISBN(String ISBN){
	
		try{
			bookDao.findBookByISBN(ISBN).getAuthor();
			} catch(NullPointerException nPE){
			return false;
			
			}
		return true;

}

}
