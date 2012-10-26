package ie.cit.patrick.service;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.springframework.test.context.ContextConfiguration;

@RunWith(Suite.class)
@ContextConfiguration( { "classpath:ie/cit/patrick/test-context.xml" } )
@SuiteClasses({testLibraryServices.class, testBookBatchProcessor.class,testMemberBatchProcessor.class,
		testWorkerMethods.class })
public class BatchSQLandWorkerTests {

}
