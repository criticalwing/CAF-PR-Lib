package ie.cit.patrick.consoleapp;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;
import ie.cit.patrick.service.BatchProcessor;
import ie.cit.patrick.service.Workers;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BatchProcessorApp {
	static ApplicationContext context;
	static Scanner keys = new Scanner(System.in);
	static BatchProcessor bookBP;
	static BatchProcessor memberBP;
	static String BatchProcessDate;
	static String BatchProcessTime;

	public static void main(String[] args) {
				
		context = new ClassPathXmlApplicationContext("classpath:ie/cit/patrick/batchApp-context.xml");
		bookBP=(BatchProcessor)context.getBean("bookBatchProcessor");
		memberBP=(BatchProcessor)context.getBean("memberBatchProcessor");
		
		runMenu();

	}
	
	public static void runMenu(){

			String input;
			do{
			System.out.print(Workers.DisplayMenu(Workers.convertFiletoStrings("src/main/resources/ie/cit/patrick/menutext/BatchProcessScreen")));
			input = keys.next();
			}while((input.toLowerCase()=="y"));
				System.out.println("##### Processing Batch Files #####\n-\n-\n-\n-\n-\n-\n-\n-\n-\n-\n-\n");
			if(!processBatchFiles()){
				System.out.println("##### Batch file(s) not found #####");
			}
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Calendar cal = new GregorianCalendar();
			df.setCalendar(cal);
			BatchProcessDate = df.format(cal.getTime());
			df.applyPattern("HHmm");
			BatchProcessTime = df.format(cal.getTime());
			do {
				System.out.print(Workers.DisplayMenu(Workers.convertFiletoStrings("src/main/resources/ie/cit/patrick/menutext/BatchLogChoice")));
				input = keys.next();
				if (!handleChoice(input)) {
					System.out.println("\nPlease choose a correct option (A,B, or X)");
				}
			} while(input.compareToIgnoreCase("X")!=0);
			
			System.out.println("##### Goodbye #####");
			keys.close();
		
	}
	
	public static boolean processBatchFiles(){
		
		bookBP.processLines(bookBP.convertFiletoStrings());
		memberBP.processLines(memberBP.convertFiletoStrings());
		
		return true;
		
	}

	public static boolean handleChoice(String choice){
		
		choice = choice.toUpperCase();
		switch(choice){
			
		case "A": 	System.out.println("\n\n##### Book Batch Process Simple Report #####");
					System.out.print(bookBP.report());
					System.out.println("\n\n##### Member Batch Process Simple Report #####");
					System.out.print(memberBP.report());
					System.out.print("\n\n\n");
					break;
		case "B": 	System.out.println("\n\n##### Book Batch Process Full Report #####");
					System.out.print(bookBP.fullReport());
					System.out.println("\n\n##### Member Batch Process Full Report #####");
					System.out.print(memberBP.fullReport());
					System.out.print("\n\n\n");		
					break;
		case "C": 	System.out.println("\n\n##### Book Batch Process Error Report #####");
					System.out.print(bookBP.errorLog());
					System.out.println("\n\n##### Member Batch Process Error Report #####");
					System.out.print(memberBP.errorLog());
					System.out.print("\n\n\n");		
					break;
		case "D": 	System.out.println("\n\n##### Book Batch Process System Report #####");
					String fileLocation = "src/main/logs/batchlogs/bookDAOLog-" + BatchProcessDate + "/" + BatchProcessTime + ".out";
					System.out.print(Workers.DisplayMenu(Workers.convertFiletoStrings(fileLocation)));
					System.out.println("\n\n##### Member Batch Process System Report #####");
					fileLocation = "src/main/logs/batchlogs/memberDAOLog-" + BatchProcessDate + "/" + BatchProcessTime + ".out";
					System.out.print(Workers.DisplayMenu(Workers.convertFiletoStrings(fileLocation)));
					System.out.print("\n\n\n");		
					break;
		case "X":	break;
		default: 	return false;

		}
		return true;

	}

}

