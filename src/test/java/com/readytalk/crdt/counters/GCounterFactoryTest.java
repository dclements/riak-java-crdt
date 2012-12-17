package com.readytalk.crdt.counters;

import org.junit.After;
import org.junit.Before;

import com.readytalk.crdt.CRDTFactoryTest;

public class GCounterFactoryTest extends CRDTFactoryTest<GCounter, GCounterFactory> {
	
	private GCounter counter0;
	private GCounter counter1;
	
	private GCounterFactory factory;
	
	@Override
	protected GCounter defaultCRDT() {
		return counter0;
	}

	@Override
	protected GCounter populatedCRDT() {
		return counter1;
	}
	
	@Override
	protected GCounterFactory factory() {
		return factory;
	}
	
	@Before
	public void setUp() throws Exception {
		counter0 = new GCounter(serializer, CLIENT_ID);
		
		counter1 = new GCounter(serializer, CLIENT_ID);
		
		counter1.increment(3);
		
		factory = new GCounterFactory(serializer, CLIENT_ID);
	}

	@After
	public void tearDown() throws Exception {
		
	}

}
