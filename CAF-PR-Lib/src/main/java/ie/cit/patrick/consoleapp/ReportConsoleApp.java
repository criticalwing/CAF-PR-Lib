package ie.cit.patrick.consoleapp;

import ie.cit.patrick.service.LibraryService;
import ie.cit.patrick.service.Workers;

import java.util.Scanner;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ReportConsoleApp {
	
	static ApplicationContext context;
	static Scanner keys = new Scanner(System.in);
	static LibraryService lS;

	public static void main(String[] args) {
		
		context = new ClassPathXmlApplicationContext("classpath:ie/cit/patrick/batchApp-context.xml");
		lS = (LibraryService)context.getBean("libraryService");
		runMenu();

	}

	private static void runMenu(){

		String input;
		do {
			//print out the screen sourced from a text file
			System.out.print(Workers.DisplayMenu(Workers.convertFiletoStrings("src/main/resources/ie/cit/patrick/menutext/StartScreenReport")));
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
		String input = "";
		switch(choice){
			
		case "A": 	System.out.print(lS.getMembersWithFines());
					break;
		case "B": 	
					do {
						//print out the screen sourced from a text file
						System.out.print(Workers.DisplayMenu(Workers.convertFiletoStrings("src/main/resources/ie/cit/patrick/menutext/ReportSelected")));
						input = keys.next();
						if (!reportForSelected(input)) {
							System.out.println("\nPlease choose a correct option (A,B, or X)");
						}
					} while(input.compareToIgnoreCase("X")!=0);				
					break;
		case "x": 	break;
		case "X":	break;
		default: 	return false;

		}
		return true;
		}
	
	private static boolean reportForSelected(String input){
		int memberId;
		String memberName;
		//convert to uppercase to allow for any case being pressed
		input = input.toUpperCase();
		switch(input){
					//call method to get a valid Id
		case "A": 	memberId = getID();
					if (memberId==0){
						break;
					}else{
					System.out.print(lS.getMembersWithFines(memberId));
					}
					break;
					//call method to get a valid Name
		case "B": 	memberName = getName();
					//check if it was x they entered to return to previous screen
					if(memberName.compareToIgnoreCase("X")==0){
					System.out.print(lS.getMembersWithFines(memberName));
					}
					break;
					//breaks is an x is pressed
		case "x": 	break;
		case "X":	break;
		default: 	return false;

		}
		return true;
		}

	//re-usable way of getting id
 	private static int getID(){
		
		String tempId="";
		int id=-1;
		do{
			System.out.print("\nPlease enter member id ==>\n[Enter x for previous screen]");
			tempId=keys.next();
				//check they have enter a number
				if(Workers.validateInt(tempId)){
					id = Integer.parseInt(tempId);
					if(!lS.validateMemberId(id)){
						System.out.print("ERROR, No Member exists with id No:" + id);
					}
				} else {
					System.out.print("Please enter a valid number, or enter X to exit\n");
					//check whether they entered an X to return
					if(tempId.compareToIgnoreCase("X")==0){
					return -1;
					}
				}

		}while(!lS.validateMemberId(id));
	
		return id;
}
 	//re-usable way of getting name
 	private static String getName(){
		
		String name="";
		do{
			System.out.print("\nPlease enter member Name to Search ==>\n[Enter x for previous screen]\n");
			name=keys.next();
				//check if they have entered an x to return
				if(name.compareToIgnoreCase("X")==0){
					return name;
				}else if(!lS.validateMemberName(name)){
					System.out.print("ERROR, No Member exists with name:" + name);
				}
		}while(!lS.validateMemberName(name));
	
		return name;
}
	
}
