package ie.cit.patrick.dao.impl;


import java.text.SimpleDateFormat;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;


import ie.cit.patrick.MemberLoansBook;
import ie.cit.patrick.dao.MemberLoansBookDao;
import ie.cit.patrick.dao.mapper.MemberLoansBookRowMapper;
import ie.cit.patrick.service.Workers;

public class JdbcMemberLoansBookDao implements MemberLoansBookDao{
	
	private JdbcTemplate jdbcTemplate;
	
	public JdbcMemberLoansBookDao(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate();
		jdbcTemplate.setDataSource(dataSource);
	}
	
	@Override
	public void loanBook(int memberId, int bookId) {
		
		
		
		String sql = "INSERT INTO Member_loans_Book (Member_id, Book_id, loan_date)"+
				"VALUES (?, ?, ?);";
			try{
			jdbcTemplate.update(sql, memberId, bookId, Workers.dateReturn(0));
			} catch (DataAccessException e){
				
				System.out.println("Error loaning Book " + e.getMessage());
			}
		
	}

	@Override
	public void returnBook(int bookId, int memberId) {
		

		String sql = "UPDATE Member_loans_Book SET return_date = ?, fine = ?" +
				"WHERE Book_id = ? AND Member_id = ?";
		
		double fine = calculateFine(memberId,bookId);
		
		try{
		jdbcTemplate.update(sql, Workers.dateReturn(0),fine, bookId, memberId);
		} catch (DataAccessException e){
			System.out.println(e.getMessage());
		}
		
	}

	
	public MemberLoansBook findLoanedBookByID(int bookID){
		
		String sql = "SELECT * FROM Member_loans_Book WHERE Book_id = ? " +
				"AND return_date IS NULL";
		
		try{
		return jdbcTemplate.queryForObject(sql, new MemberLoansBookRowMapper(), bookID);
		} catch (DataAccessException e){
			System.out.println(e.getMessage());
		}
		return null;
		
		
	}
	@Override
	public MemberLoansBook findByBookIDandMemberID(int memberID, int bookID){
		
		String sql = "SELECT * FROM Member_loans_Book WHERE Book_id = ? AND Member_id = ?";
		
		try{
		return jdbcTemplate.queryForObject(sql, new MemberLoansBookRowMapper(), bookID, memberID);
		} catch (DataAccessException e){
			System.out.println("Error finding loan " +e.getMessage());
		}
		return null;
		
		
	}
	
	@Override
	public double calculateFine(int memberId, int bookId) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		double fine = 0;
		String loanDate = df.format(findByBookIDandMemberID(memberId, bookId).getLoan_date().getTime());
		int daysBetween = Workers.daysBetween(loanDate, Workers.dateReturn(0));

		fine = daysBetween*5-70;
			if(fine>0){
			}else{
				fine = 0;
			}
			//doubles give crazy results, damn floating points
		return fine/100;
	}


}
