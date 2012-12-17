package com.readytalk.crdt.counters;

import org.junit.After;
import org.junit.Before;

import com.readytalk.crdt.CRDTFactoryTest;

public class PNCounterFactoryTest extends CRDTFactoryTest<PNCounter, PNCounterFactory> {
	
	private PNCounter counter0;
	private PNCounter counter1;
	
	private PNCounterFactory factory;
	
	@Override
	protected PNCounter defaultCRDT() {
		return counter0;
	}

	@Override
	protected PNCounter populatedCRDT() {
		return counter1;
	}
	
	@Override
	protected PNCounterFactory factory() {
		return factory;
	}
	
	@Before
	public void setUp() throws Exception {
		counter0 = new PNCounter(serializer, CLIENT_ID);
		
		counter1 = new PNCounter(serializer, CLIENT_ID);
		
		counter1.increment(3);
		
		counter1.decrement();
		
		factory = new PNCounterFactory(serializer, CLIENT_ID);
	}

	@After
	public void tearDown() throws Exception {
		
	}

}