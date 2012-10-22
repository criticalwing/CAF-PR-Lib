package ie.cit.patrick.service;

import java.util.ArrayList;

public interface BatchProcessor {
	
	ArrayList<String> convertFiletoStrings();
	
	void processLines(ArrayList<String> parts);
	
	void processChanges(String[] parts);
	
	boolean validateStringArray(String[] parts);
	
	String report();

	String errorLog();

	String fullReport();
}
