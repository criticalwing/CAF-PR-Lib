package ie.cit.patrick.dao.impl;


import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ie.cit.patrick.MemberLoansBook;
import ie.cit.patrick.dao.MemberLoansBookDao;
import ie.cit.patrick.dao.mapper.MemberLoansBookRowMapper;
import ie.cit.patrick.service.Workers;

@Repository
@Transactional
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
	public void returnBook(int bookId, int memberId, double fine) {
		

		String sql = "UPDATE Member_loans_Book SET return_date = ?, fine = ?" +
				"WHERE Book_id = ? AND Member_id = ?";
		
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
			e.getMessage();
		}
		return null;
		
		
	}
	
	@Override
	public MemberLoansBook findByBookIDandMemberID(int memberID, int bookID){
		
		String sql = "SELECT * FROM Member_loans_Book WHERE Book_id = ? AND Member_id = ?";
		
		try{
		return jdbcTemplate.queryForObject(sql, new MemberLoansBookRowMapper(), bookID, memberID);
		} catch (DataAccessException e){
			System.out.println("Error finding loan by bookId and MemberId" +e.getMessage());
		}
		return null;
		
		
	}
	
	@Override
	public MemberLoansBook findByBookIDMemberIDReturnDate(int memberID, int bookID, String date){
		
		String sql = "SELECT * FROM Member_loans_Book WHERE Book_id = ? AND Member_id = ? AND return_date = ?";
		
		try{
		return jdbcTemplate.queryForObject(sql, new MemberLoansBookRowMapper(), bookID, memberID, date);
		} catch (DataAccessException e){
			System.out.println("Error finding loan by bookId, MemberId and date " +e.getMessage());
		}
		return null;
		
		
	}
	


	@Override
	public List<MemberLoansBook> findCurrentLoansByMemberId(int memberId) {
		
		String sql = "SELECT * FROM Member_loans_Book WHERE Member_id = ? AND return_date IS NULL";
		
		try{
		return jdbcTemplate.query(sql, new MemberLoansBookRowMapper(), memberId);
		} catch (DataAccessException e){
			System.out.println("Error finding loan " +e.getMessage());
		}
		return null;

	}


}
