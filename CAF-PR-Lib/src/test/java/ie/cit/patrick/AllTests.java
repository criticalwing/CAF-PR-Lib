package ie.cit.patrick;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ testMemberDao.class, testBookDao.class,
		testMemberBatchProcessor.class, testBookBatchProcessor.class })
public class AllTests {

}
