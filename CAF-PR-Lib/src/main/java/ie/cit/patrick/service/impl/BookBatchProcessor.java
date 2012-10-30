package ie.cit.patrick.service.impl;

import ie.cit.patrick.Book;
import ie.cit.patrick.dao.BookDao;
import ie.cit.patrick.service.BatchProcessor;
import ie.cit.patrick.service.Workers;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;


public class BookBatchProcessor implements BatchProcessor {
	
	String[] fileLocation;
	String delineator;
	@Autowired
	BookDao bookDao;
	ArrayList<String> batchFullReport;
	ArrayList<String> errorLog;
	
	//Constructors
	public BookBatchProcessor(){
		batchFullReport =new ArrayList<String>();
		errorLog = new ArrayList<String>();
	}
	public BookBatchProcessor(String[] fileLocation, String delineator) {
		this.fileLocation = fileLocation;
		this.delineator = delineator;
		batchFullReport =new ArrayList<String>();
		errorLog = new ArrayList<String>();
	}
	//if it is to be used as static this allows Dao to be set
	public BookBatchProcessor(BookDao bookDao, String[] fileLocation, String delineator) {
		this.bookDao = bookDao;
		this.fileLocation = fileLocation;
		this.delineator = delineator;
		batchFullReport =new ArrayList<String>();
		errorLog = new ArrayList<String>();
	}
	
	//Getter & Setters
	public String[] getFileLocation() {
		return fileLocation;
	}
	public void setFileLocation(String[] fileLocation) {
		this.fileLocation = fileLocation;
	}
	public String getDelineator() {
		return delineator;
	}
	public void setDelineator(String delineator) {
		this.delineator = delineator;
	}
	public ArrayList<String> getBatchFileReport() {
		return batchFullReport;
	}
	public void setBatchFileReport(ArrayList<String> batchFileReport) {
		this.batchFullReport = batchFileReport;
	}
	public ArrayList<String> getBatchFullReport() {
		return batchFullReport;
	}
	public void setBatchFullReport(ArrayList<String> batchFullReport) {
		this.batchFullReport = batchFullReport;
	}
	public ArrayList<String> getErrorLog() {
		return errorLog;
	}
	public void setErrorLog(ArrayList<String> errorLog) {
		this.errorLog = errorLog;
	}
	
	//Processing Methods
	public ArrayList<String> convertFiletoStrings(){		
		ArrayList<String> lines = new ArrayList<String>();
		for (String location : fileLocation){
			try {
				BufferedReader reader = new BufferedReader(new FileReader(location));
				String line;
				while ((line = reader.readLine()) != null) {
					lines.add(line);
				}
				reader.close();
			} catch (FileNotFoundException fNFE) {
				System.out.print(fNFE.getMessage());
			} catch (IOException iOE) {
				System.out.print(iOE.getMessage());
			}
		}
		return lines;
	}
	
	public void processLines(ArrayList<String> bookParts){
		
		for ( String x : bookParts ){
		
		String[] parts = x.split(delineator);
		
			if(validateStringArray(parts,(bookParts.indexOf(x)+1))){
				processDecider(parts);
			} else {
				batchFullReport.add("*COULD NOT BE PROCESSED*, see Error Log");
			}
		}

	}
	
	public void processDecider(String[] parts){
	
	
		if(parts[0].equals("A")){
			String[] wholeDate = parts[4].split("-");
			GregorianCalendar date = new GregorianCalendar(Integer.parseInt(wholeDate[0]), Integer.parseInt(wholeDate[1]), Integer.parseInt(wholeDate[2]));
			Book toAdd = new Book(parts[1], parts[2], parts[3], parts[5], date, true);
			bookDao.addBook(toAdd);
			batchFullReport.add("Book: " + "\"" + parts[1] + "\"" +" by "+ "\"" + parts[2]+ "\"" + " added to the Database");
		}
		else {
			if (parts[2].equals("*U")){
				bookDao.makeBookUnavailable(Integer.parseInt(parts[1]));
				Book output = bookDao.findBookById(Integer.parseInt(parts[1]));
				batchFullReport.add("Book: " + "\"" + output.getTitle() + "\"" +" by "+ "\"" + output.getAuthor() + "\"" + " is now marked unavailable");
			} else if (parts[2].equals("*A")){
				bookDao.makeBookAvailable(Integer.parseInt(parts[1]));
				Book output = bookDao.findBookById(Integer.parseInt(parts[1]));
				batchFullReport.add("Book: " + "\"" + output.getTitle() + "\"" +" by "+ "\"" + output.getAuthor() + "\"" + " is now marked available");
			} else{	
				String[] wholeDate = parts[5].split("-");
				GregorianCalendar date = new GregorianCalendar(Integer.parseInt(wholeDate[0]), Integer.parseInt(wholeDate[1]), Integer.parseInt(wholeDate[2]));
				Book toUpDate = new Book(Integer.parseInt(parts[1]), parts[2], parts[3], parts[4], parts[6], date, true);
				bookDao.updateBook(toUpDate);
				batchFullReport.add("Book: " + "\"" + parts[2] + "\"" +" by "+ "\"" + parts[3]+ "\"" + " updated");
			}
		}
		
	}
	
	//Validation Methods
	public boolean validateStringArray(String[] parts, int position){
		
		//check if array holds either six, seven or three elements for processing
		if(parts.length>7||parts.length<3||parts.length==4||parts.length==5){
			return false;
		}
		//check that the first part only holds 1 character
		if(parts[0].length()!=1){
			errorLog.add("Line "+position+" does not contain an appropriate first command character");
			return false;
		}
		//check that the first character is either A or U
		if(parts[0].contains("A")==false&&parts[0].contains("U")==false){
			errorLog.add("Line "+position+" does not contain an appropriate first command character");
			return false;
		}
		//check that if a book is to be added or updated it has the correct date format
		if(parts.length==6){
			if(!Workers.validateDate(parts[4])){
				errorLog.add("Line "+position+" does not contain an appropriately formatted date");
				return false;
			}
		}
		if(parts.length==7){
			if(Workers.validateDate(parts[5])==false){
				errorLog.add("Line "+position+" does not contain an appropriately formatted date");
				return false;
			}
		}
		if(parts.length==3||parts.length==7){
			if(!Workers.validateInt(parts[1])){
				errorLog.add("Line "+position+" does not contain an appropriately formatted id");
				return false;
			}
			if(!validateId(Integer.parseInt(parts[1]))){
				errorLog.add("Line "+position+" Book: " + parts[1] + " does not exist");
				return false;
			}
		}
		//ensure that update lines have correct number of parts
		if(parts[0].equals("U")){
			if(parts.length!=3&&parts.length!=7){
				errorLog.add("Line "+position+" does not contain enough elements for an update");
			return false;
			}
		}
		
		
		return true;
		
	}
	private boolean validateId(int id){
		try{
			if(bookDao.findBookById(id)==null){
			return false;}
			else{
				return true;
			}
		} catch(NullPointerException nPE){
			return false;
		}
		
	}

	
	//String Outputs
	@Override
	public String fullReport() {
		String output = "------------ FULL REPORT -------------\n" +
				"Line No.	Details\n";
		int x = 1;
		for(String line:batchFullReport){
			output = output + x +". " + line + "\n";
			x++;
		}
		
		output = output + "--------------------------------------\n";
		return output;
	}
	public String report() {
		int booksAdded = 0;
		int booksUpdated = 0;
		int booksAvail = 0;
		int booksUnavail = 0;
		int errors =0;
		
		for(String line:batchFullReport){
			if(line.contains(" added")){
				booksAdded++;
				}
			if(line.contains(" updated")){
				booksUpdated++;
			}
			if(line.contains(" available")){
				booksAvail++;
			}
			if(line.contains(" unavailable")){
				booksUnavail++;
			}
			if(line.contains( " Error")){
				errors++;
			}
		}		
		String output = "----------- REPORT -----------------\n" +
				batchFullReport.size() + " total record" + Workers.sReturn(batchFullReport.size()) + " processed\n" +
				booksAdded + " book" + Workers.sReturn(booksAdded) +" added\n" +
				booksUpdated + " book" + Workers.sReturn(booksUpdated) + " updated\n" +
				booksAvail +	" book" + Workers.sReturn(booksAvail) + " made available\n" +
				booksUnavail + " book" + Workers.sReturn(booksUnavail) + " made unavailable\n" +
				errors + " error" + Workers.sReturn(errors) + " found\n" +
						"------------------------------------\n\n";
		return output;
	}

	public String errorLog() {
		String output = "\n------------ ERROR LOG ----------------\n" +
				"Line No.	Details\n";
		int x = 1;
		for(String line:errorLog){
			output = output + x +". " + line + "\n";
			x++;
		}
		
		output = output + "---------------------------------------\n";
		
		return output;
	}
	@Override
	public String toString() {
		String locations ="";
		for(String x:fileLocation){
			locations = locations + x + " ";
		}
		
		return "BookBatchProcessor [fileLocation="
				+ locations + ", delineator=" + delineator + "]";
	}



	

}
