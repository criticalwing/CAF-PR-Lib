package ie.cit.patrick.service.impl;

import ie.cit.patrick.Book;
import ie.cit.patrick.dao.BookDao;
import ie.cit.patrick.service.BatchProcessor;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;


public class BookBatchProcessor implements BatchProcessor {
	
	String fileLocation;
	String delineator;
	@Autowired
	BookDao bookDao;
	ArrayList<String> batchFileReport;
	ArrayList<String> errorLog;
	
	//Constructors
	public BookBatchProcessor(){
		batchFileReport =new ArrayList<String>();
		errorLog = new ArrayList<String>();
	}
	public BookBatchProcessor(String fileLocation, String delineator) {
		this.fileLocation = fileLocation;
		this.delineator = delineator;
		batchFileReport =new ArrayList<String>();
		errorLog = new ArrayList<String>();
	}
	
	//Getter & Setters
	public String getFileLocation() {
		return fileLocation;
	}
	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}
	public String getDelineator() {
		return delineator;
	}
	public void setDelineator(String delineator) {
		this.delineator = delineator;
	}
	public ArrayList<String> getBatchFileReport() {
		return batchFileReport;
	}
	public void setBatchFileReport(ArrayList<String> batchFileReport) {
		this.batchFileReport = batchFileReport;
	}
	
	//Processing Methods
	public ArrayList<String> convertFiletoStrings(){		
		ArrayList<String> lines = new ArrayList<String>();
		
			try{
				
			BufferedReader reader = new BufferedReader(new FileReader(fileLocation));
			String line;
			while ((line = reader.readLine())!=null){
				lines.add(line);
			}
			reader.close();
			} catch(FileNotFoundException fNFE){
				System.out.print(fNFE.getMessage());
			} catch (IOException iOE){
				System.out.print(iOE.getMessage());
			} 
		
		return lines;
	}
	
	public void processLines(ArrayList<String> bookParts){
		
		for ( String x : bookParts ){
		
		String[] parts = x.split(delineator);
		
			if(validateStringArray(parts)){
				processChanges(parts);
			} else {
				batchFileReport.add("*ERROR*");
				errorLog.add("There is an error on line " + (bookParts.indexOf(x)+1) + " of the batch file.");
			}
		}

	}
	
	public void processChanges(String[] parts){
	
	
		if(parts[0].equals("A")){
			String[] wholeDate = parts[4].split("-");
			GregorianCalendar date = new GregorianCalendar(Integer.parseInt(wholeDate[0]), Integer.parseInt(wholeDate[1]), Integer.parseInt(wholeDate[2]));
			Book toAdd = new Book(parts[1], parts[2], parts[3], parts[5], date, true);
			bookDao.addBook(toAdd);
			batchFileReport.add("Book: " + "\"" + parts[1] + "\"" +" by "+ "\"" + parts[2]+ "\"" + " added to the Database");
		}
		else {
			if (parts[2].equals("*U")){
				bookDao.makeBookUnavailable(Integer.parseInt(parts[1]));
				Book output = bookDao.findBookById(Integer.parseInt(parts[1]));
				batchFileReport.add("Book: " + "\"" + output.getTitle() + "\"" +" by "+ "\"" + output.getAuthor() + "\"" + " is now marked unavailable");
			} else if (parts[2].equals("*A")){
				bookDao.makeBookAvailable(Integer.parseInt(parts[1]));
				Book output = bookDao.findBookById(Integer.parseInt(parts[1]));
				batchFileReport.add("Book: " + "\"" + output.getTitle() + "\"" +" by "+ "\"" + output.getAuthor() + "\"" + " is now marked available");
			} else{			
				String[] wholeDate = parts[5].split("-");
				GregorianCalendar date = new GregorianCalendar(Integer.parseInt(wholeDate[0]), Integer.parseInt(wholeDate[1]), Integer.parseInt(wholeDate[2]));
				Book toUpDate = new Book(Integer.parseInt(parts[1]), parts[2], parts[3], parts[4], parts[6], date, true);
				bookDao.updateBook(toUpDate);
				batchFileReport.add("Book: " + "\"" + parts[2] + "\"" +" by "+ "\"" + parts[3]+ "\"" + " updated");
			}
		}
		
	}
	
	//Validation Methods
	public boolean validateStringArray(String[] parts){
		
		//check if array holds either six, seven or three elements for processing
		if(parts.length>7||parts.length<3||parts.length==4||parts.length==5){
			return false;
		}
		//check that the first part only holds 1 character
		if(parts[0].length()!=1){
			return false;
		}
		//check that the first character is either A or U
		if(parts[0].contains("A")==false&&parts[0].contains("U")==false){
			return false;
		}
		//check that if a book is to be added or updated it has the correct date format
		if(parts.length==6){
			if(validateDate(parts[4])==false){
				return false;
			}
		}
		if(parts.length==7){
			if(validateDate(parts[5])==false){
				return false;
			}
		}
		if(parts.length==3||parts.length==7){
			if(validateInt(parts[1])==false){
				return false;
			}
		}
		return true;
		
	}
	private boolean validateDate(String date){
		
		SimpleDateFormat x = new SimpleDateFormat("yyyy-MM-dd");
				
		try{
			x.parse(date);
		} catch (ParseException e){
			return false;			
		}
		return true;

	}
	private boolean validateInt(String input){
		
		try { 
				Integer.parseInt(input);  
				} 
				catch(NumberFormatException nFE) { 
				return false;
				}
		return true;
		
	}
	
	//String Outputs
	@Override
	public String report() {
		String output = "";
		int x = 1;
		for(String line:batchFileReport){
			output = output + x +". " + line + "\n";
			x++;
		}
		
		output = output + "-----------------------------------\nThe batch file has been processed\n";
		
		return output;
	}
	public String errorLog() {
		String output = "\nERROR LOG\n-----------------------------------\n";;
		int x = 1;
		for(String line:errorLog){
			output = output + x +". " + line + "\n";
			x++;
		}
		
		output = output + "-----------------------------------\n";
		
		return output;
	}
	public String toString() {
		return "BookBatchProcessor [fileLocation=" + fileLocation
				+ ", delineator=" + delineator + "]";
	}


	

}
