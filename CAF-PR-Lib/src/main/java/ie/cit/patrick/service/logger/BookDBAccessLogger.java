package ie.cit.patrick.service.logger;

import ie.cit.patrick.Book;
import ie.cit.patrick.dao.BookDao;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class BookDBAccessLogger {

	Logger bookDBlogger = Logger.getLogger(BookDao.class.getName());

	
	public BookDBAccessLogger() {

	}
	
	@Before("execution(* *..BookDao+.addBook(..))")
	public void trackBookAddStart(JoinPoint point){
		Book book = (Book)point.getArgs()[0];
		String method = point.getSignature().toShortString();
		bookDBlogger.info(method + " is attempting to add new Book: " + book.getTitle() + " to database");
		//System.out.print("Attempting to add new Book: " + book.getTitle() + " to database\n");
	}
	
	@After("execution(* *..BookDao+.addBook(..))")
	public void trackBookAddEnd(JoinPoint point){
		Book book = (Book)point.getArgs()[0];
		String method = point.getSignature().toShortString();
		bookDBlogger.info(method + " successfully added Book: " + book.getTitle() + " to the database\n");
		//System.out.print("Book: " + book.getTitle() + " successfully added to database\n\n");
	}
	
	@Before("execution(* *..BookDao+.updateBook(..))")
	public void trackBookUpdateStart(JoinPoint point){
		Book book = (Book)point.getArgs()[0];
		String method = point.getSignature().toShortString();
		bookDBlogger.info(method + " is attempting to update Book: " + book.getTitle() + " in database");
		//System.out.print("Attempting to update Book: " + book.getTitle() + " in database\n");
	}
	
	@After("execution(* *..BookDao+.updateBook(..))")
	public void trackBookUpdateEnd(JoinPoint point){
		Book book = (Book)point.getArgs()[0];
		String method = point.getSignature().toShortString();
		bookDBlogger.info(method + " successfully updated Book: " + book.getTitle() + " in database\n");
		//System.out.print("Book: " + book.getTitle() + " successfully updated in database\n\n");
	}
	
	@Before("execution(* *..BookDao+.deleteBook(..))")
	public void trackBookDeleteStart(JoinPoint point){
		String bookId = point.getArgs()[0].toString();	
		String method = point.getSignature().toShortString();
		bookDBlogger.info(method + "is attempting to delete Book with Id: " + bookId + " from database");
		//System.out.print("Attempting to delete Book with Id: " + bookId + " from database\n");
	}
	
	@After("execution(* *..BookDao+.deleteBook(..))")
	public void trackBookDeleteEnd(JoinPoint point){
		String bookId = point.getArgs()[0].toString();
		String method = point.getSignature().toShortString();
		bookDBlogger.info(method + " successfully deleted Book: " + bookId + " from database\n");
		//System.out.print("Member: " + bookId + " successfully deleted from database\n\n");
	}

	@Before("execution(* *..BookDao+.makeBook*(..))")
	public void trackBookUpdateAvailstart(JoinPoint point){
		int id = (int)point.getArgs()[0];
		String method = point.getSignature().toShortString();
		bookDBlogger.info(method + " is attempting to update Book: " + id + " in database");
		//System.out.print("Attempting to update Book: " + book.getTitle() + " in database\n");
	}
	
	@After("execution(* *..BookDao+.makeBook*(..))")
	public void trackBookUpdateAvailend(JoinPoint point){
		int id = (int)point.getArgs()[0];
		String method = point.getSignature().toShortString();
		bookDBlogger.info(method + " successfully updated Book: " + id + " in database\n");
		//System.out.print("Book: " + book.getTitle() + " successfully updated in database\n\n");
	}
	
}
