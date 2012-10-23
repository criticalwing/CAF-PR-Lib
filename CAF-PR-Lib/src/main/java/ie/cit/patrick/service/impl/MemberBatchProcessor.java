	package ie.cit.patrick.service.impl;


import ie.cit.patrick.Member;
import ie.cit.patrick.dao.MemberDao;
import ie.cit.patrick.service.BatchProcessor;

	import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;

public class MemberBatchProcessor implements BatchProcessor {
		
		String fileLocation;
		String delineator;
		@Autowired
		MemberDao MemberDao;
		ArrayList<String> batchFullReport;
		ArrayList<String> errorLog;
		
		//Constructors
		public MemberBatchProcessor(){
			batchFullReport =new ArrayList<String>();
			errorLog = new ArrayList<String>();
		}
		public MemberBatchProcessor(String fileLocation, String delineator) {
			this.fileLocation = fileLocation;
			this.delineator = delineator;
			batchFullReport =new ArrayList<String>();
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
		
		public void processLines(ArrayList<String> memberParts){
			
			for ( String x : memberParts ){
			
			String[] parts = x.split(delineator);
				if(validateStringArray(parts, (memberParts.indexOf(x)+1))){
					processDecider(parts);
				} else {
					batchFullReport.add("*COULD NOT BE PROCESSED*, see Error Log");
				}
			}

		}
		public void processDecider(String[] parts){
		
		
			if(parts[0].equals("A")){
					processAddMember(parts);
				}
			else if(parts[0].equals("U")){
					if (parts[2].equals("*I")){
							processMakeInActive(parts);
						
					} else if (parts[2].equals("*A")){
							processMakeActive(parts);
						
					} else {
							processUpdateMember(parts);
					}
				} 
			else if(parts[0].equals("P")){
				processPayment(parts);
			}
			else{
				batchFullReport.add("While line in batch file is validate no know methods exist to process it");
			}
			
		}
		public void processAddMember(String[] parts){
			
			
			if(parts.length==5){
				Member toAdd = new Member(parts[1], parts[2], parts[3], parts[4]);
				MemberDao.addMember(toAdd);
				batchFullReport.add("Member: " + "\"" + parts[1] + "\"" +" from "+ "\"" + parts[4]+ "\"" + " added to the Database");
			}
			if(parts.length==6){
				Member toAdd = new Member(parts[1], parts[2], parts[3], parts[4], parts[5]);
				MemberDao.addMember(toAdd);
				batchFullReport.add("Member: " + "\"" + toAdd.getName() + "\"" +" from "+ "\"" + toAdd.getTown() + "\"" + " added to the Database");
			}
			
			
			
		}
		public void processMakeActive(String[] parts){

				Member change = MemberDao.findMemberById(Integer.parseInt(parts[1]));
				change.setActive(true);
				MemberDao.updateMember(change);
				batchFullReport.add("Member: " + "\"" + change.getName() + "\"" +" by "+ "\"" + change.getTown() + "\"" + " is now marked active");

		}
		public void processMakeInActive(String[] parts){
			
				Member change = MemberDao.findMemberById(Integer.parseInt(parts[1]));
				change.setActive(false);
				MemberDao.updateMember(change);
				batchFullReport.add("Member: " + "\"" + change.getName() + "\"" +" by "+ "\"" + change.getTown() + "\"" + " is now marked inactive");
			
		}
		public void processUpdateMember(String[] parts){
						
				if(parts.length==7){
					Member change = new Member(parts[2], parts[3], parts[4], parts[5], parts[6]);
					MemberDao.updateMember(change);
					batchFullReport.add("Member: " + "\"" + change.getName() + "\"" +" by "+ "\"" + change.getTown() + "\"" + " is now updated");
				}
				if(parts.length==6){
					Member change = new Member(parts[2], parts[3], parts[4], parts[5]);
					MemberDao.updateMember(change);
					batchFullReport.add("Member: " + "\"" + change.getName() + "\"" +" by "+ "\"" + change.getTown() + "\"" + " is now updated");							
				}
			
		}
		public void processPayment(String[] parts){
			
				Member payment = MemberDao.findMemberById(Integer.parseInt(parts[1]));
				double amount = Double.parseDouble(parts[2]);
				if((payment.getBalance()-amount)<0){
					batchFullReport.add("Over Payment, Member: " + parts[1] + " is due a refund of Û" + (amount-payment.getBalance()) +". Û"+ amount + 
							" paid on a balance of Û"+ payment.getBalance() + ", Balance now set to zero");
					payment.setBalance(0);
					MemberDao.updateMember(payment);
				} else {
					payment.setBalance(payment.getBalance()-amount);
					MemberDao.updateMember(payment);
					batchFullReport.add("Payment Made, Member: " + parts[1] + ", Payment of "+ amount + " made on account, new balance: " + payment.getBalance());
				}
				
			}
		
		
		//Validation Methods
		public boolean validateStringArray(String[] parts, int position){
			
			//TODO check if array holds either six, seven or three elements for processing
			if(parts.length>7||parts.length<3||parts.length==4){
				errorLog.add("Line "+position+" does not contain the appropriate amount of elements to be processed");
				return false;
			}
			//check that the first part only holds 1 character
			if(parts[0].length()!=1){
				errorLog.add("Line "+position+" does not contain an appropriate first command character");
				return false;
			}
			//check that the first character is either A, U or P
			if(!parts[0].contains("A")&&!parts[0].contains("U")&&!parts[0].contains("P")){
				errorLog.add("Line "+ position +" does not contain an appropriate first command character");
				return false;
			}
			//check that the id is an int and exists
			if(parts.length==3||parts.length==7){
				if(validateInt(parts[1])==false){
					errorLog.add("Line "+position+" does not contain a valid integer for referencing the member id");
					return false;
				}
				if(!validateId(Integer.parseInt(parts[1]))){
					errorLog.add("Line "+position+" Member: " + parts[1] + " does not exist");
					return false;
				}
			}
			if(parts[0].equals("P")&&parts.length==3){
				if(!validateDouble(parts[2])){
					errorLog.add("Line "+position+" marked for payment does not contain a valid amount");
					return false;
				}
			}

			return true;
			
		}
		private boolean validateId(int id){
			try{
				if(MemberDao.findMemberById(id).getName()==null){
				return false;}
				else{
					return true;
				}
			} catch(NullPointerException nPE){
				return false;
			}
			
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
		private boolean validateDouble(String input){
			
			try { 
					Double.parseDouble(input);  
					} 
					catch(NumberFormatException nFE) { 
					return false;
					}
			return true;
			
		}
		
		//String Outputs
		@Override
		public String fullReport() {
			String output = "------------ FULL REPORT -------------\n";
			int x = 1;
			for(String line:batchFullReport){
				output = output + x +". " + line + "\n";
				x++;
			}
			
			output = output + "--------------------------------------\n";
			return output;
		}
		public String report() {
			int membersAdded = 0;
			int membersUpdated = 0;
			int membersAvail = 0;
			int membersUnavail = 0;
			int paymentsMade =0;
			int errors=0;
			
			for(String line:batchFullReport){
				if(line.contains(" added")){
					membersAdded++;
					}
				if(line.contains(" updated")){
					membersUpdated++;
				}
				if(line.contains(" active")){
					membersAvail++;
				}
				if(line.contains(" inactive")){
					membersUnavail++;
				}
				if(line.contains(" balance")){
					paymentsMade++;
				}
				if(line.contains(" Error")){
					errors++;
				}
			}
			
			String output = "----------- REPORT -----------------\n" +
					batchFullReport.size() + " total record" + sReturn(batchFullReport.size()) + " processed\n" +
					membersAdded + " member" + sReturn(membersAdded) +" added\n" +
					membersUpdated + " member" + sReturn(membersUpdated) + " updated\n" +
					membersAvail +	" member" + sReturn(membersAvail) + " made active\n" +
					membersUnavail + " member" + sReturn(membersUnavail) + " made inactive\n" +
					paymentsMade + " payment" + sReturn(paymentsMade) + " made\n" +
					errors + " error" + sReturn(errors) + " found" +
							"\n------------------------------------\n\n";
			return output;
		}
		public String sReturn(int x){
			if(x>1||x==0){
				return "s";
			}
			else{
				return"";
			}
			
		}
		
		
		public String errorLog() {
			String output = "\n------------ ERROR LOG ----------------\n";;
			int x = 1;
			for(String line:errorLog){
				output = output + x +". " + line + "\n";
				x++;
			}
			
			output = output + "---------------------------------------\n";
			
			return output;
		}
		public String toString() {
			return "MemberBatchProcessor [fileLocation=" + fileLocation
					+ ", delineator=" + delineator + "]";
		}

}
