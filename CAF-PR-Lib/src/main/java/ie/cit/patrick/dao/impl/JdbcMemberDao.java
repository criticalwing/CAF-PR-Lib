package ie.cit.patrick.dao.impl;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ie.cit.patrick.Member;
import ie.cit.patrick.dao.MemberDao;
import ie.cit.patrick.dao.mapper.MemberRowMapper;

@Repository
@Transactional
public class JdbcMemberDao implements MemberDao {
	
	private JdbcTemplate jdbcTemplate;
	
	public JdbcMemberDao(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate();
		jdbcTemplate.setDataSource(dataSource);
	}
	
	public void addMember(Member member) {
	
		String sql = "INSERT INTO Member (name,address1,address2,town,contact_number,balance)"+
						"VALUES (?, ?, ?, ?, ?, ?);";
		
		try{
		jdbcTemplate.update(sql, member.getName(),member.getAddress1(),member.getAddress2(),
							member.getTown(), member.getContactNumber(),
							member.getBalance());
		} catch (DataAccessException e){
			System.out.println(e.getMessage());
		}
		
	}

	public void updateMember(Member member) {

		String sql = "UPDATE member SET name = ?, address1 = ?, address2 = ?, " +
				"town = ?, contact_number = ?, book_allowance = ?, balance = ?, active = ? " +
				"WHERE id = ?";
		
		try{
		jdbcTemplate.update(sql, member.getName(),member.getAddress1(),member.getAddress2(),
							member.getTown(), member.getContactNumber(), member.getBookAllowance(),
							member.getBalance(), member.isActive(), member.getId());
		} catch (DataAccessException e){
			System.out.println(e.getMessage());
		}
		
	}

	public void deleteMember(int memberId) {
		
		String sql = "DELETE FROM member WHERE id = ?";
		
		try{
		jdbcTemplate.update(sql, memberId);
		} catch (DataAccessException e){
			System.out.println(e.getMessage());
		}
	}

	@Override
	public List<Member> findMemberByTitle(String name) {
		
		name = "%" + name + "%";
		
		try {
			return jdbcTemplate.query(
				"SELECT * FROM member WHERE name LIKE ?", 
				new MemberRowMapper(), name);
		}
		catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public Member findMemberById(int id) {
		
		try {
			return jdbcTemplate.queryForObject(
				"SELECT * FROM member WHERE id = ?", 
				new MemberRowMapper(), id);
		}
		catch (EmptyResultDataAccessException e) {
			return null;
		}
		
	}

	public void makeMemberInactive(int id){
		
		
		
		
	}

	@Override
	public List<Member> allMembersWithFines() {

		try {
			return jdbcTemplate.query(
				"SELECT * FROM member WHERE balance > 0", 
				new MemberRowMapper());
		}
		catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public List<Member> MembersWithFines(int memberId) {
				
		try {
			return jdbcTemplate.query(
				"SELECT * FROM member WHERE id = ? AND balance>0", 
				new MemberRowMapper(), memberId);
		}
		catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public List<Member> MembersWithFines(String memberName) {
		
		memberName = "%" + memberName + "%";
		
		
		try {
			return jdbcTemplate.query(
				"SELECT * FROM member WHERE name LIKE ? AND balance>0", 
				new MemberRowMapper(), memberName);
		}
		catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
}
		

