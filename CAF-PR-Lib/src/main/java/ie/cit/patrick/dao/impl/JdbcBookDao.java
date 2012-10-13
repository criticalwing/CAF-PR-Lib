package ie.cit.patrick.dao.impl;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import javax.sql.DataSource;

import ie.cit.patrick.Book;
import ie.cit.patrick.dao.BookDao;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

public class JdbcBookDao implements BookDao{
	
	private JdbcTemplate jdbcTemplate;
	
	public JdbcBookDao(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate();
		jdbcTemplate.setDataSource(dataSource);
	}

	public void addBook(Book book) {

		String sql = "INSERT INTO `library`.`book` (`id`, `title`, `author`, " +
				"`publisher`, `publication_date`, `isbn`, `available`) " +
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
		// TODO Auto-generated method stub
		
	}

	public void deleteBook(Book book) {
		
		String sql = "DELETE FROM `library`.`book` WHERE `book`.`id` = ?";
		
		try{
		jdbcTemplate.update(sql, book.getId());
		} catch (DataAccessException e){
			System.out.println(e.getMessage());
		}
		
	}

	public String formatDate(GregorianCalendar inputDate){
		
		String strdate = null;

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

		if (inputDate != null) {
		strdate = sdf.format(inputDate.getTime());
		}
		
		return strdate;
		
	}

	public Book findBookByTitle(String Name) {
		// TODO Auto-generated method stub
		return null;
	}

	public Book findBookById(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
