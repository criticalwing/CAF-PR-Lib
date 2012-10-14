package ie.cit.patrick.service.impl;

import ie.cit.patrick.service.BatchProcessor;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

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
			String line = reader.readLine();
			while (reader != null){
				lines.add(line);
				line = reader.readLine();
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
		
		if(parts[0]=="A"){
			//the add book code
		}
		else{
			if (parts[3]=="*U"){
				//add the make unavailable code
			} else if (parts[3]=="*A"){
				//add the make available code
			} else{
			//the update book code
			}
		}
		
		}
		
	}

	

}
