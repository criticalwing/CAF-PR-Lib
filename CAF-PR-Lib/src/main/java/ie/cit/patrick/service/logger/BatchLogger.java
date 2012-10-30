package ie.cit.patrick.service.logger;

import ie.cit.patrick.service.BatchProcessor;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class BatchLogger {
	
	Logger bookBatchLogger = Logger.getLogger(BatchProcessor.class.getName());
	
	public BatchLogger(){
		
	}
	
	@Before("execution(void *..BatchProcessor+.processLines(..))")
	public void trackProcessLineStart(JoinPoint point){
		String line= point.getArgs()[0].toString();
		String method = point.getSignature().toShortString();
		bookBatchLogger.info(method + " is attempting to process this line: " + line);
		//System.out.print("Attempting to add new Book: " + book.getTitle() + " to database\n");
	}
	
	@After("execution(void *..BatchProcessor+.processLines(..))")
	public void trackProcessLineEnd(JoinPoint point){
		String line= point.getArgs()[0].toString();
		String method = point.getSignature().toShortString();
		bookBatchLogger.info(method + " completed processing this line: " + line);
		//System.out.print("Attempting to add new Book: " + book.getTitle() + " to database\n");
	}
	
	@Before("execution(void *..BatchProcessor+.processDecider(..))")
	public void trackProcessDeciderStart(JoinPoint point){
		String[] parts= (String[]) point.getArgs()[0];
		String output = "\n";
		for (String lines:parts){
			output = output+lines+"\n";
		}
		String method = point.getSignature().toShortString();
		bookBatchLogger.info(method + " is attempting to process these parts: " + output);
		//System.out.print("Attempting to add new Book: " + book.getTitle() + " to database\n");
	}
	
	@After("execution(void *..BatchProcessor+.processDecider(..))")
	public void trackProcessDeciderEnd(JoinPoint point){
		String[] parts= (String[]) point.getArgs()[0];
		String output = "\n";
		for (String lines:parts){
			output = output+lines+"\n";
		}
		String method = point.getSignature().toShortString();
		bookBatchLogger.info(method + " completed processing these parts: " + output);
		//System.out.print("Attempting to add new Book: " + book.getTitle() + " to database\n");
	}
	
	@Before("execution(boolean *..BatchProcessor+.validateStringArray(..))")
	public void validateStringArrayStart(JoinPoint point){
		String[] parts= (String[]) point.getArgs()[0];
		String output = "\n";
		for (String lines:parts){
			output = output+lines+"\n";
		}
		String method = point.getSignature().toShortString();
		bookBatchLogger.info(method + " is attempting to validate these parts: " + output);
		//System.out.print("Attempting to add new Book: " + book.getTitle() + " to database\n");
	}
	
	@After("execution(boolean *..BatchProcessor+.validateStringArray(..))")
	public void validateStringArrayEnd(JoinPoint point){
		String[] parts= (String[]) point.getArgs()[0];
		String output = "\n";
		for (String lines:parts){
			output = output+lines+"\n";
		}
		String method = point.getSignature().toShortString();
		bookBatchLogger.info(method + " completed validating these parts: " + output);
		//System.out.print("Attempting to add new Book: " + book.getTitle() + " to database\n");
	}
	
	

}
