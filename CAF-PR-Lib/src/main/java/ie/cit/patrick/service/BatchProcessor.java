package ie.cit.patrick.service;

import java.util.ArrayList;

public interface BatchProcessor {
	
	ArrayList<String> convertFiletoStrings();
	
	String processLines(ArrayList<String> parts);
	
	void processChanges(String[] parts);
	
	boolean validateStringArray(String[] parts);

}
