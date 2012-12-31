package com.readytalk.crdt.sets;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;

public class ORSetTest extends CRDTSetTest<ImmutableSet<String>, ORSet<String>> {
	
	private ORSet<String> set0;
	private ORSet<String> set1;
	private ORSet<String> set2;
	
	private ORSet<String> set12;
	
	@Before
	public void setUp() throws Exception {
		set0 = new ORSet<String>(mapper);
		
		set1 = new ORSet<String>(mapper);
		
		set1.add(OBJ_1);
		
		set2 = new ORSet<String>(mapper);
		
		set2.add(OBJ_2);
		set2.add(OBJ_3);
		
		set12 = set1.merge(set2);
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Override
	public ORSet<String> newSet() {
		return new ORSet<String>(mapper);
	}

	@Override
	protected ORSet<String> defaultCRDT() {
		return set0;
	}

	@Override
	protected ORSet<String> firstOrtho() {
		return set1;
	}

	@Override
	protected ORSet<String> secondOrtho() {
		return set2;
	}

	@Override
	protected ORSet<String> firstAndSecond() {
		return set12;
	}

	@Override
	protected ORSet<String> fromPayload(final String clientId, final byte[] values) {
		return new ORSet<String>(mapper, values);
	}
	
	@Test
	public void removeIsImplemented() {
		assertTrue(set1.remove(OBJ_1));
	}
	
	@Test
	public void removeAllIsImplemented() {
		List<String> objects = Lists.newArrayList(OBJ_2, OBJ_3);
		
		assertTrue(set2.removeAll(objects));
		assertTrue(set2.isEmpty());
	}
	
	@Test
	public void canReAddRemoved() {
		set12.remove(OBJ_1);
		set12.add(OBJ_1);
		
		assertTrue(set12.contains(OBJ_1));
	}
	
	@Test
	public void retainIsSupported() {
		
		set12.retainAll(set1);
		
		assertEquals(1, set12.size());
		assertTrue(set12.contains(OBJ_1));
	}
}
