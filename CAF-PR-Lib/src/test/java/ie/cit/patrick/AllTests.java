package ie.cit.patrick;

import ie.cit.patrick.dao.testBookDao;
import ie.cit.patrick.dao.testMemberDao;
import ie.cit.patrick.service.testBookBatchProcessor;
import ie.cit.patrick.service.testMemberBatchProcessor;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ testMemberDao.class, testBookDao.class,
		testMemberBatchProcessor.class, testBookBatchProcessor.class })
public class AllTests {

}
