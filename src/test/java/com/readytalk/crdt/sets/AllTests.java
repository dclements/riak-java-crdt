package com.readytalk.crdt.sets;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ GSetTest.class, TwoPhaseSetTest.class })
public class AllTests {

}
