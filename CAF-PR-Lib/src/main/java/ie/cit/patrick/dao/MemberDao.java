package ie.cit.patrick.dao;

import ie.cit.patrick.Member;

public interface MemberDao {
	
	void addMember(Member member);
	
	void updateMember(Member member);
	
	void deleteMember(Member member);
	
	Member findMemberByTitle(String Name);
	
	Member findMemberById(int id);
	
	

}
