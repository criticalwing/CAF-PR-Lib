package ie.cit.patrick.dao;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ testBookDao.class, testMemberDao.class,
		testMemberLoansBookDao.class })
public class AllDaoTests {

}
