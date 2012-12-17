package com.readytalk.crdt.riak;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ CRDTConflictResolverTest.class, CRDTMutationProducerTest.class,
		CRDTMutationTest.class })
public class AllTests {

}
