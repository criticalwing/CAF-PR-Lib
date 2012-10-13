package ie.cit.patrick.dao;

import ie.cit.patrick.Member;

public interface MemberDao {
	
	void addMember();
	
	void updateMember();
	
	void deleteMember();
	
	Member findMemberByTitle(String Name);
	
	Member findMemberById(int id);
	
	

}
