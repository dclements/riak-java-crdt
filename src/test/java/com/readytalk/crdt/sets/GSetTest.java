package com.readytalk.crdt.sets;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableSet;

public class GSetTest extends CRDTSetTest<ImmutableSet<String>, GSet<String>> {
	
	private GSet<String> set0;
	private GSet<String> set1;
	private GSet<String> set2;
	
	private GSet<String> set12;


	@Before
	public void setUp() throws Exception {
		set0 = new GSet<String>(mapper);
		
		set1 = new GSet<String>(mapper);
		
		set1.add(OBJ_1);
		
		set2 = new GSet<String>(mapper);
		
		set2.add(OBJ_2);
		set2.add(OBJ_3);
		
		set12 = set1.merge(set2);
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Override
	public GSet<String> newSet() {
		return new GSet<String>(mapper);
	}
	
	@Override
	protected GSet<String> defaultCRDT() {
		return set0;
	}

	@Override
	protected GSet<String> firstOrtho() {
		return set1;
	}

	@Override
	protected GSet<String> secondOrtho() {
		return set2;
	}

	@Override
	protected GSet<String> firstAndSecond() {
		
		return set12;
	}

	@Override
	protected GSet<String> fromPayload(String clientId, byte[] values) {
		return new GSet<String>(mapper, values);
	}

	@Test
	public void remvoeUnsupported() {
		thrown.expect(UnsupportedOperationException.class);
		set1.remove(OBJ_1);
	}
	
	@Test
	public void removeALlUnsupported() {
		thrown.expect(UnsupportedOperationException.class);
		set12.removeAll(set1);
	}
	
	@Test
	public void retainAllUnsupported() {
		thrown.expect(UnsupportedOperationException.class);
		set12.retainAll(set1);
	}
	
	@Test
	public void clearUnsupported() {
		thrown.expect(UnsupportedOperationException.class);
		set12.retainAll(set1);
	}
}
