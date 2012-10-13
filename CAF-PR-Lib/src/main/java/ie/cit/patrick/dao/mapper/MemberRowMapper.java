package ie.cit.patrick.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import ie.cit.patrick.Member;
import org.springframework.jdbc.core.RowMapper;

public class MemberRowMapper implements RowMapper<Member>{

	
	public Member mapRow(ResultSet rs, int i) throws SQLException {
		return new Member(rs.getInt("id"), rs.getInt("book_allowance"), rs.getString("name"), 
				rs.getString("address1"), rs.getString("address2"), rs.getString("town"), 
				rs.getString("contact_number"), rs.getDouble("balance"), rs.getBoolean("active"));
	}
	
	
	
	
	
	
	

}
