package com.readytalk.crdt;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ com.readytalk.crdt.counters.AllTests.class,
		com.readytalk.crdt.riak.AllTests.class,
		com.readytalk.crdt.sets.AllTests.class,
		com.readytalk.crdt.util.AllTests.class})
public class AllStaticTests {

}
