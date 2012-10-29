package ie.cit.patrick.service.logger;

import ie.cit.patrick.service.impl.LibraryServiceImpl;

import org.apache.log4j.Logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class LoanReturnLogger {

	Logger logger = Logger.getLogger(LibraryServiceImpl.class.getName());
	
	LoanReturnLogger(){
	}
	
	@Before("execution(* *..LibraryService+.returnBook(..))")
	public void trackReturnStart(JoinPoint point){
		String memberId = point.getArgs()[0].toString();
		String bookId = point.getArgs()[1].toString();		
		logger.info("Member:" + memberId + " will attempt to return book with Id:" + bookId);
		//System.out.print("Member:" + memberId + " will attempt to return book with Id:" + bookId + "\n");
	}
	
	@After("execution(* *..LibraryService+.returnBook(..))")
	public void trackReturnEnd(JoinPoint point){
		String memberId = point.getArgs()[0].toString();
		String bookId = point.getArgs()[1].toString();		
		logger.info("Member:" + memberId + " has returned book book with Id:" + bookId);
		//System.out.print("Member:" + memberId + " has returned book book with Id:" + bookId + "\n\n");
	}
	
	@Before("execution(* *..LibraryService+.loanBook(..))")
	public void trackLoanStart(JoinPoint point){
		String memberId = point.getArgs()[0].toString();
		String bookId = point.getArgs()[1].toString();		
		logger.info("Member:" + memberId + " will attempt to loan book with Id:" + bookId);
		//System.out.print("Member:" + memberId + " will attempt to loan book with Id:" + bookId + "\n");
	}
	
	@After("execution(* *..LibraryService+.loanBook(..))")
	public void trackLoanEnd(JoinPoint point){
		String memberId = point.getArgs()[0].toString();
		String bookId = point.getArgs()[1].toString();		
		logger.info("Member:" + memberId + " has loaned book with Id:" + bookId);
		//System.out.print("Member:" + memberId + " has loaned book with Id:" + bookId + "\n\n");
	}
	
	
	
}

