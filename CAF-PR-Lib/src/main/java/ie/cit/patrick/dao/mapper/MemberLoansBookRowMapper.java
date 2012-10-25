package ie.cit.patrick.dao.mapper;

import ie.cit.patrick.MemberLoansBook;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.GregorianCalendar;

import org.springframework.jdbc.core.RowMapper;

public class MemberLoansBookRowMapper implements RowMapper<MemberLoansBook> {

	@Override
	public MemberLoansBook mapRow(ResultSet rs, int x) throws SQLException {
		
		GregorianCalendar loanDate = createDate(rs.getString("loan_date"));
		MemberLoansBook memberLoansBook;
		if(!(rs.getString("return_date")==null)){
			GregorianCalendar returnDate = createDate(rs.getString("return_date"));
			memberLoansBook = new MemberLoansBook(rs.getInt("Book_id"), rs.getInt("Member_id"), rs.getDouble("fine"), loanDate, returnDate);
		}
		else{
			memberLoansBook = new MemberLoansBook(rs.getInt("Book_id"), rs.getInt("Member_id"), loanDate);
		}

		return memberLoansBook;

	}
	
	private GregorianCalendar createDate(String date){
		String[] x = date.split("-");
		GregorianCalendar outputDate = new GregorianCalendar(Integer.parseInt(x[0]), Integer.parseInt(x[1])-1, Integer.parseInt(x[2]));		
		return outputDate;
	}

}
