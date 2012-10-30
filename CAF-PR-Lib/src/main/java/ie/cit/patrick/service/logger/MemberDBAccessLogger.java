package ie.cit.patrick.service.logger;

import ie.cit.patrick.Member;
import ie.cit.patrick.dao.MemberDao;

import org.apache.log4j.Logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class MemberDBAccessLogger {

	Logger memberDBlogger = Logger.getLogger(MemberDao.class.getName());

	
	public MemberDBAccessLogger() {

	}
	
	@Before("execution(* *..MemberDao+.addMember(..))")
	public void trackMemberAddStart(JoinPoint point){
		Member member = (Member)point.getArgs()[0];	
		memberDBlogger.info("Attempting to add new Member: " + member.getName() + " to database");
		//System.out.print("Attempting to add new Member: " + member.getName() + " to database\n");
	}
	
	@After("execution(* *..MemberDao+.addMember(..))")
	public void trackMemberAddEnd(JoinPoint point){
		Member member = (Member)point.getArgs()[0];	
		memberDBlogger.info("Member: " + member.getName() + " successfully added to the database\n");
		//System.out.print("Member: " + member.getName() + " successfully added to database\n\n");
	}
	
	@Before("execution(* *..MemberDao+.updateMember(..))")
	public void trackMemberUpdateStart(JoinPoint point){
		Member member = (Member)point.getArgs()[0];	
		memberDBlogger.info("Attempting to update Member: " + member.getName() + " in database");
		//System.out.print("Attempting to update Member: " + member.getName() + " in database\n");
	}
	
	@After("execution(* *..MemberDao+.updateMember(..))")
	public void trackMemberUpdateEnd(JoinPoint point){
		Member member = (Member)point.getArgs()[0];	
		memberDBlogger.info("Member: " + member.getName() + " successfully updated in database\n");
		//System.out.print("Member: " + member.getName() + " successfully updated in database\n\n");
	}
	
	@Before("execution(* *..MemberDao+.deleteMember(..))")
	public void trackMemberDeleteStart(JoinPoint point){
		String memberId = point.getArgs()[0].toString();	
		memberDBlogger.info("Attempting to delete Member with Id: " + memberId + " from database");
		//System.out.print("Attempting to delete Member with Id: " + memberId + " from database\n");
	}
	
	@After("execution(* *..MemberDao+.deleteMember(..))")
	public void trackMemberDeleteEnd(JoinPoint point){
		String memberId = point.getArgs()[0].toString();	
		memberDBlogger.info("Member: " + memberId + " successfully deleted from database\n");
		//System.out.print("Member: " + memberId + " successfully deleted from database\n\n");
	}

}
