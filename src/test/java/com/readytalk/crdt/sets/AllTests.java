package com.readytalk.crdt.sets;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ GSetTest.class, GSetFactoryTest.class, TwoPhaseSetTest.class, TwoPhaseSetFactoryTest.class })
public class AllTests {

}
