package ie.cit.patrick.service.impl;

import ie.cit.patrick.service.BatchProcessor;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class BookBatchProcessor implements BatchProcessor {
	
	String fileLocation;
	String delineator;	
	
	public BookBatchProcessor(){
	}
	public BookBatchProcessor(String fileLocation, String delineator) {
		this.fileLocation = fileLocation;
		this.delineator = delineator;
	}
	
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
	
	public String processLines(ArrayList<String> bookParts){
		
		String output= "";
		
		for ( String x : bookParts ){
		
		String[] parts = x.split(delineator);
		
			if(validateStringArray(parts)){
				processChanges(parts);
			} else {
				output = output + "Error while processing line " + (bookParts.indexOf(x)+1) + " of the batch file.\n";
			}
		}
		output = output + "The batch file has been processed\n";
		return output;

	}
	
	public void processChanges(String[] parts){
		
		if(parts[0].equals("A")){
			System.out.print("Add Book\n");
			/*String[] wholeDate = parts[5].split("-");
			GregorianCalendar date = new GregorianCalendar(Integer.parseInt(wholeDate[0]), Integer.parseInt(wholeDate[1]), Integer.parseInt(wholeDate[2]));
			bookDao.addBook(new Book(parts[0], parts[1], parts[2], parts[3], date, true));*/
		}
		else {
			if (parts[2].equals("*U")){
				//TODO add the make unavailable code
				System.out.print("Make book Unavailable\n");
			} else if (parts[2].equals("*A")){
				//TODO add the make available code
				System.out.print("Make book Available\n");
			} else{
			//TODO the update book code
				System.out.print("Update Book\n");
			}
		}
		
	}
	
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
		if(parts.length==6){
			if(validateDate(parts[4])==false){
				return false;
			}
		}
		if(parts.length==7){
			if(validateDate(parts[5])==false){
				return false;
			}
			//TODO add a bit that validates that the ID number exists
		}
		return true;
		
	}
	
	private boolean validateDate(String date){
		
		SimpleDateFormat x = new SimpleDateFormat("yyyy-MM-dd");
		
		Date valDate = null;
		
		try{
			valDate = x.parse(date);
		} catch (ParseException e){
			return false;			
		}
		return true;

	}
		
	@Override
 	public String toString() {
		return "BookBatchProcessor [fileLocation=" + fileLocation
				+ ", delineator=" + delineator + "]";
	}

	

}
