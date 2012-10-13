package ie.cit.patrick.dao.impl;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import ie.cit.patrick.Member;
import ie.cit.patrick.dao.MemberDao;

public class JdbcMemberDao implements MemberDao {
	
	private JdbcTemplate jdbcTemplate;
	
	public JdbcMemberDao(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate();
		jdbcTemplate.setDataSource(dataSource);
	}
	
	public void addMember() {
		// TODO Auto-generated method stub
		
	}

	public void updateMember() {
		// TODO Auto-generated method stub
		
	}

	public void deleteMember() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Member findMemberByTitle(String Name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Member findMemberById(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
