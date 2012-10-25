package ie.cit.patrick.service;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ testBookBatchProcessor.class,testMemberBatchProcessor.class, testMySQLConnection.class,
		testWorkerMethods.class })
public class BatchSQLandWorkerTests {

}
