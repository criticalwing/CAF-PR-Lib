package ie.cit.patrick.dao;

import java.util.List;

import ie.cit.patrick.Member;

public interface MemberDao {
	
	void addMember(Member member);
	
	void updateMember(Member member);
	
	void deleteMember(Member member);
	
	List<Member> findMemberByTitle(String Name);
	
	Member findMemberById(int id);
	
	

}
