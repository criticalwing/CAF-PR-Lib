package ie.cit.patrick;

import static org.junit.Assert.*;

import java.util.ArrayList;
import ie.cit.patrick.dao.MemberDao;
import ie.cit.patrick.service.impl.MemberBatchProcessor;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( { "classpath:ie/cit/patrick/test-context.xml" } )

public class testMemberBatchProcessor {
	
	@Autowired
	MemberBatchProcessor memberBatchProcessor;
	@Autowired
	MemberDao memberDao;
	
		
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void basicSetup(){
						
		String x = "MemberBatchProcessor [fileLocation=src/test/resources/memberbatchFile, delineator=~]";
		
		String y = memberBatchProcessor.toString();
		
		assertEquals(x, y);
		
		ArrayList<String> lines = memberBatchProcessor.convertFiletoStrings();
		
		String testOutput = "A~Bob Hope~50 Rodeo Drive~~Limerick~0871234567";
		
		assertEquals(testOutput, lines.get(0).toString());
		
	}
	
	@Test
	public void testValidateString(){
		
		String[] test = {"A", "Bob Hope","50 Rodeo Drive","Limerick","0871234567"};
		String[] test2 = {"U", "232","*I"};
		String[] test3 = {"U","9332","*A"};
		String[] test4 = {"P","543","23.50"};
		String[] test5 = {"U","3425","Janet Leigh","22 Boreenmana Road","Ballinlough","Cork","0214444333"};
		//Too Short
		String[] test6 = {"Bob Hope","50 Rodeo Drive","Limerick","0871234567"};
		//command char does not exist
		String[] test8 = {"X", "Bob Hope","50 Rodeo Drive","Limerick","0871234567"};
		//second command char does not exist
		String[] test9 = {"P","543","23.50"};
		//last element is not a double
		String[] test10 = {"P","543","sdgsghsf"};

		
		assertTrue(memberBatchProcessor.validateStringArray(test, 10));
		assertTrue(memberBatchProcessor.validateStringArray(test2, 11));
		assertTrue(memberBatchProcessor.validateStringArray(test3, 12));
		assertTrue(memberBatchProcessor.validateStringArray(test4, 13));
		assertTrue(memberBatchProcessor.validateStringArray(test5, 14));
		assertFalse(memberBatchProcessor.validateStringArray(test6, 15));
		assertFalse(memberBatchProcessor.validateStringArray(test8, 16));
		assertTrue(memberBatchProcessor.validateStringArray(test9, 17));
		assertFalse(memberBatchProcessor.validateStringArray(test10, 18));
		
		memberBatchProcessor.setErrorLog(new ArrayList<String>());
		
	}
	
	@Test
	public void testChanges(){
		memberBatchProcessor.processLines(memberBatchProcessor.convertFiletoStrings());
		//Test member has been successfully added to database from batch file
		Member y = new Member("Bob Hope","50 Rodeo Drive","Limerick","0871234567");
		Member x = memberDao.findMemberByTitle("Bob Hope");
		assertEquals(x.getName(),y.getName());
		assertEquals(x.getAddress1(),y.getAddress1());
		assertEquals(x.getBalance(), y.getBalance(),0);
		
		//Test member has been successfully added to database from batch file
		String a = memberDao.findMemberByTitle("Janet Leigh").getName();
		String b = "Janet Leigh";
		assertEquals(b,a);
		
		//Check that the members have been made inactive/active
		assertTrue(memberDao.findMemberById(1234).isActive());
		assertFalse(memberDao.findMemberById(4567).isActive());
		
		//print reports
		System.out.print(memberBatchProcessor.report());
		System.out.print(memberBatchProcessor.fullReport());
		System.out.print(memberBatchProcessor.errorLog());
		

	}
	
	@After
	public void tearDown() throws Exception {
	}
}
