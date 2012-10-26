package ie.cit.patrick.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Workers {
	

	public static ArrayList<String> convertFiletoStrings(String fileLocation){		
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
	
	public static String DisplayMenu(ArrayList<String> lines){
		String output="";
		
		for(String line: lines){
			if(lines.indexOf(line)==lines.size()){
				output = output + line;
			} else{
			output = output + line + "\n";
			}
		}
		return output;
		
	}

	public static boolean validateInt(String input){
		
		try { 
				Integer.parseInt(input);  
				} 
				catch(NumberFormatException nFE) { 
				return false;
				}
		return true;
		
	}
	public static boolean validateDouble(String input){
		
		try { 
				Double.parseDouble(input);  
				} 
				catch(NumberFormatException nFE) { 
				return false;
				}
		return true;
		
	}
	public static String sReturn(int x){
		if(x>1||x==0){
			return "s";
		}
		else{
			return"";
		}
		
	}
	public static boolean validateDate(String date){
		
		SimpleDateFormat x = new SimpleDateFormat("yyyy-MM-dd");
				
		try{
			x.parse(date);
		} catch (ParseException e){
			return false;			
		}
		return true;

	}
	public static String dateReturn(int weeks){
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar now = Calendar.getInstance();
		now.add(Calendar.WEEK_OF_YEAR, weeks);
		return (dateFormat.format(now.getTime()));

	}
	
	public static int daysBetween(String startDate, String endDate) {  
			SimpleDateFormat x = new SimpleDateFormat("yyyy-MM-dd");

			Calendar start = new GregorianCalendar();
			Calendar end = new GregorianCalendar();
			
			if(validateDate(startDate)&&validateDate(endDate)){
				try{
					start.setTime(x.parse(startDate));
				}catch(ParseException pE){
					pE.printStackTrace();
				}
				try{
					end.setTime(x.parse(endDate));
				}catch(ParseException pE){
					pE.printStackTrace();
				}
				return (int)((end.getTimeInMillis()-start.getTimeInMillis()) / (1000 * 60 * 60 * 24));
			} else{
					return -1;
		}  
		}
	
	
	
	
}
