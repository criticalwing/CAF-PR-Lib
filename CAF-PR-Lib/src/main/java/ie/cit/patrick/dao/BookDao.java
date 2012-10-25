package ie.cit.patrick.dao;

import ie.cit.patrick.Book;

public interface BookDao {
	
	void addBook(Book book);
	
	void updateBook(Book book);
	
	void deleteBook(int bookId);
	
	void makeBookUnavailable(int id);
	
	void makeBookAvailable(int id);
	
	Book findBookByTitle(String Name);
	
	Book findBookById(int id);
	
	Book findBookByISBN(String ISBN);

}
