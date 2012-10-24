package ie.cit.patrick;

import static org.junit.Assert.*;
import ie.cit.patrick.dao.MemberDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( { "classpath:/ie/cit/patrick/test-context.xml" } )
	
		
public class testMemberDao {
	
	@Autowired
	MemberDao memberDao;
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testAddFindDeleteMember() {
		
		Member x = new Member("Patrick Robertson", "Hjaltland", "Pullerick", "Crookstown","0857103950");
		
		memberDao.addMember(x);
		
		Member y = memberDao.findMemberByTitle("Patrick Robertson").get(0);
		
		assertEquals(x.getName(), y.getName());
		
		memberDao.deleteMember(y);
		
		assertNull(memberDao.findMemberByTitle("Patrick Robertson"));
		
		
	}
	
	@Test
	public void testUpdateMember() {
		
		Member x = new Member("Patrick Robertson", "Hjaltland", "Pullerick", "Crookstown","0857103950");
		
		memberDao.addMember(x);
		
		Member y = memberDao.findMemberByTitle("Patrick Robertson").get(0);
		
		y.setContactNumber("0123456789");
		
		memberDao.updateMember(y);
		
		Member z = memberDao.findMemberByTitle("Patrick Robertson").get(0);
		
		assertEquals(z.getContactNumber(), "0123456789");
		
		memberDao.deleteMember(z);
		
		assertNull(memberDao.findMemberByTitle("Patrick Robertson"));
		
		
	}

}
