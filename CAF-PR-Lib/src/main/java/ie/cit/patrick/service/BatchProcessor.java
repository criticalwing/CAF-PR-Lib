package ie.cit.patrick.service;

import java.util.ArrayList;

public interface BatchProcessor {
	
	ArrayList<String> convertFiletoStrings();
	
	void processLines(ArrayList<String> parts);
	
	void processDecider(String[] parts);
	
	boolean validateStringArray(String[] parts, int position);
	
	String report();

	String errorLog();

	String fullReport();
}
