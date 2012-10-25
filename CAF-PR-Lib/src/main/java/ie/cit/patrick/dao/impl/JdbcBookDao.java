package ie.cit.patrick.dao.impl;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import javax.sql.DataSource;

import ie.cit.patrick.Book;
import ie.cit.patrick.dao.BookDao;
import ie.cit.patrick.dao.mapper.BookRowMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

public class JdbcBookDao implements BookDao{
	
	private JdbcTemplate jdbcTemplate;
	
	public JdbcBookDao(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate();
		jdbcTemplate.setDataSource(dataSource);
	}

	public void addBook(Book book) {

		String sql = "INSERT INTO book (id, title, author, " +
				"publisher, publication_date, isbn, available) " +
				"VALUES (NULL, ?, ?, " +
				"?, ?, ?, ?)";
		
		String date = formatDate(book.getPublicationDate());
		
		try{
		jdbcTemplate.update(sql, book.getTitle(), book.getAuthor(), 
							book.getPublisher(), date, 
							book.getIsbn(), book.isAvailable());
		} catch (DataAccessException e){
			System.out.println(e.getMessage());
		}
		
	}

	public void updateBook(Book book) {
		
		String sql = "UPDATE BOOK SET title = ?, author = ?, publisher = ?, " +
				"publication_date = ?, isbn = ?, available = ? " +
				"WHERE id = ?";
		
		String date = formatDate(book.getPublicationDate());
		
		try{
		jdbcTemplate.update(sql, book.getTitle(), book.getAuthor(), 
				book.getPublisher(), date, 
				book.getIsbn(), book.isAvailable(), book.getId());
		} catch (DataAccessException e){
			System.out.println(e.getMessage());
		}
	}

	public void deleteBook(int bookId) {
		
		String sql = "DELETE FROM book WHERE id = ?";
		
		try{
		jdbcTemplate.update(sql, bookId);
		} catch (DataAccessException e){
			System.out.println(e.getMessage());
		}
		
	}

	public void makeBookAvailable(int id){
				
		String sql = "UPDATE BOOK SET available = true " +
				"WHERE id = ?";
		
		try{
		jdbcTemplate.update(sql, id);
		} catch (DataAccessException e){
			System.out.println(e.getMessage());
		}
	}
	
	public void makeBookUnavailable(int id){
		
		String sql = "UPDATE BOOK SET available = false " +
				"WHERE id = ?";
		
		try{
		jdbcTemplate.update(sql, id);
		} catch (DataAccessException e){
			System.out.println(e.getMessage());
		}
	}
	
	public Book findBookByTitle(String title) {
		
		try {
			return jdbcTemplate.queryForObject(
				"SELECT * FROM book WHERE title = ?", 
				new BookRowMapper(), title);
		}
		catch (EmptyResultDataAccessException e) {
			return null;
		}

	}

	public Book findBookById(int id) {
		
		try {
			return jdbcTemplate.queryForObject(
				"SELECT * FROM book WHERE id = ?", 
				new BookRowMapper(), id);
		}
		catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public String formatDate(GregorianCalendar inputDate){
		
		inputDate.add(inputDate.MONTH, -1);
		
		String strdate = null;
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		if (inputDate != null) {
		strdate = sdf.format(inputDate.getTime());
		}
				
		return strdate;
		
	}

	
	public Book findBookByISBN(String ISBN) {
		
		try {
			return jdbcTemplate.queryForObject(
				"SELECT * FROM book WHERE isbn = ?", 
				new BookRowMapper(), ISBN);
		}
		catch (EmptyResultDataAccessException e) {
			return null;
		}		

	}

}
