package ie.cit.patrick;

import static org.junit.Assert.*;
import java.util.List;
import ie.cit.patrick.dao.MemberDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( { "classpath:ie/cit/patrick/consoleapp-context.xml" } )


public class testMySQLConnection {
	
	@Autowired
	MemberDao memberdao;
	

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testFindMember() {
		
		
		assertEquals(memberdao.findMemberById(232).getName(),"Stuart Little");
		List<Member> temp = memberdao.findMemberByTitle("Saunders");
		String[] x = {"Vera Saunders","Steven Saunders"};
		String [] y = {temp.get(0).getName(), temp.get(1).getName()};
		
		assertArrayEquals(x,y);

		
	}

	
}
