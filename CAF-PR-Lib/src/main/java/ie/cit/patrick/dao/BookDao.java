package ie.cit.patrick.dao;

import ie.cit.patrick.Book;

public interface BookDao {
	
	void addBook(Book book);
	
	void updateBook(Book book);
	
	void deleteBook(Book book);
	
	Book findBookByTitle(String Name);
	
	Book findBookById(int id);

}
