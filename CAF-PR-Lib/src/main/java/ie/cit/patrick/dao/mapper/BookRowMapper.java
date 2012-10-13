package ie.cit.patrick.dao.mapper;

import ie.cit.patrick.Book;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.GregorianCalendar;

import org.springframework.jdbc.core.RowMapper;

public class BookRowMapper implements RowMapper<Book>{
	
	
	public Book mapRow(ResultSet rs, int i) throws SQLException {
		
		GregorianCalendar date = createDate(rs.getString("publicationDate"));
		 
		Book book = new Book(rs.getInt("id"), rs.getString("title"), rs.getString("author"), 
				rs.getString("publisher"), rs.getString("isbn"), date, 
				rs.getBoolean("available"));
		return book;
	}
	
	private GregorianCalendar createDate(String date){
		String[] x = date.split("/", 3);
		GregorianCalendar outputDate = new GregorianCalendar(Integer.parseInt(x[2]), Integer.parseInt(x[1]), Integer.parseInt(x[0]));		
		return outputDate;
	}

}
