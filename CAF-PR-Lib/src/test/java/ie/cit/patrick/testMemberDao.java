package ie.cit.patrick;

import static org.junit.Assert.*;
import ie.cit.patrick.dao.MemberDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( { "classpath:/ie/cit/patrick/app-context.xml" } )
	
		
public class testMemberDao {
	
	@Autowired
	ApplicationContext context;
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testAddFindDeleteMember() {
		
		context = new ClassPathXmlApplicationContext("classpath:/ie/cit/patrick/app-context.xml");
		MemberDao memberDao = (MemberDao)context.getBean("memberDao");
		
		Member x = new Member(3, "Patrick Robertson", "Hjaltland", "Pullerick", "Crookstown","0857103950", 10.0, true);
		
		memberDao.addMember(x);
		
		Member y = memberDao.findMemberByTitle("Patrick Robertson");
		
		assertEquals(x.getName(), y.getName());
		
		memberDao.deleteMember(y);
		
		assertNull(memberDao.findMemberByTitle("Patrick Robertson"));
		
		
	}
	
	@Test
	public void testUpdateMember() {
		
		context = new ClassPathXmlApplicationContext("classpath:/ie/cit/patrick/app-context.xml");
		MemberDao memberDao = (MemberDao)context.getBean("memberDao");
		
		Member x = new Member(3, "Patrick Robertson", "Hjaltland", "Pullerick", "Crookstown","0857103950", 10.0, true);
		
		memberDao.addMember(x);
		
		Member y = memberDao.findMemberByTitle("Patrick Robertson");
		
		y.setContactNumber("0123456789");
		
		memberDao.updateMember(y);
		
		Member z = memberDao.findMemberByTitle("Patrick Robertson");
		
		assertEquals(z.getContactNumber(), "0123456789");
		
		memberDao.deleteMember(z);
		
		assertNull(memberDao.findMemberByTitle("Patrick Robertson"));
		
		
	}

}
