package com.readytalk.crdt.counters;

import java.math.BigInteger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PNCounterTest extends CRDTCounterTest<BigInteger, PNCounter> {
	
	private PNCounter counter0;
	private PNCounter counter1;
	private PNCounter counter2;
	private PNCounter counter3;
	
	@Before
	public void setUp() throws Exception {
		counter0 = new PNCounter(mapper, CLIENT_ID1);
		counter1 = new PNCounter(mapper, CLIENT_ID1);
		
		counter1.increment(2);
		counter1.decrement();
		
		counter2 = new PNCounter(mapper, CLIENT_ID2);
		
		counter2.increment(4);
		counter2.decrement(2);
		
		counter3 = counter1.merge(counter2);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Override
	protected PNCounter defaultCRDT() {
		return counter0;
	}

	@Override
	protected PNCounter firstOrtho() {
		return counter1;
	}

	@Override
	protected PNCounter secondOrtho() {
		return counter2;
	}

	@Override
	protected PNCounter firstAndSecond() {
		return counter3;
	}

	@Override
	protected PNCounter fromPayload(String clientId, byte[] values) {
		return new PNCounter(mapper, clientId, values);
	}
	
	@Test
	public void decrementDoesNotThrowException() {
		defaultCRDT().decrement();
	}
	
	@Test
	public void decrementNDoesNotThrowException() {
		defaultCRDT().decrement(2);
	}
	
}
