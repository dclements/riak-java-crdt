package com.readytalk.crdt.sets;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ GSetFactoryTest.class, GSetTest.class, ORSetFactoryTest.class,
		ORSetTest.class, TwoPhaseSetFactoryTest.class, TwoPhaseSetTest.class })
public class AllTests {

}
