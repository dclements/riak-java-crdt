package com.readytalk.crdt.sets;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.google.common.collect.ImmutableSet;

public class GSetTest extends CRDTSetTest<ImmutableSet<String>, GSet<String>> {
	
	@Rule
	public final ExpectedException thrown = ExpectedException.none();
	
	private GSet<String> set0;
	private GSet<String> set1;
	private GSet<String> set2;
	
	private GSet<String> set12;


	@Before
	public void setUp() throws Exception {
		set0 = new GSet<String>(mapper);
		
		set1 = new GSet<String>(mapper);
		
		set1.add(obj1);
		
		set2 = new GSet<String>(mapper);
		
		set2.add(obj2);
		set2.add(obj3);
		
		set12 = set1.merge(set2);
	}

	@After
	public void tearDown() throws Exception {
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
	public void cannotRemove() {
		thrown.expect(UnsupportedOperationException.class);
		set1.remove(obj1);
	}
	
	@Test
	public void cannotRemoveAll() {
		thrown.expect(UnsupportedOperationException.class);
		set12.removeAll(set1);
	}
	
	@Test
	public void cannotRetainAll() {
		thrown.expect(UnsupportedOperationException.class);
		set12.retainAll(set1);
	}
	
	@Test
	public void cannotClear() {
		thrown.expect(UnsupportedOperationException.class);
		set12.retainAll(set1);
	}
}
