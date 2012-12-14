package com.readytalk.crdt.counters;

import java.math.BigInteger;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class GCounterTest extends CRDTCounterTest<BigInteger, GCounter> {
	
	@Rule
	public final ExpectedException thrown = ExpectedException.none();
	
	private GCounter counter0;
	private GCounter counter1;
	private GCounter counter2;
	private GCounter counter3;
	
	@Before
	public void setUp() throws Exception {
		counter0 = new GCounter(mapper, CLIENT_ID1);
		counter1 = new GCounter(mapper, CLIENT_ID1);
		
		counter1.increment();
		
		counter2 = new GCounter(mapper, CLIENT_ID2);
		
		counter2.increment();
		counter2.increment();
		
		counter3 = counter1.merge(counter2);
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Override
	protected GCounter defaultCRDT() {
		return counter0;
	}

	@Override
	protected GCounter firstOrtho() {
		return counter1;
	}

	@Override
	protected GCounter secondOrtho() {
		return counter2;
	}

	@Override
	protected GCounter firstAndSecond() {
		return counter3;
	}

	@Override
	protected GCounter fromPayload(String clientId, byte[] values) {
		return new GCounter(mapper, clientId, values);
	}
	
	@Test
	public void decrementNotImplemented() {
		thrown.expect(UnsupportedOperationException.class);
		
		counter1.decrement();
	}


}
