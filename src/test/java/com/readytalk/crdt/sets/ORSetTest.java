package com.readytalk.crdt.sets;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.google.common.collect.ImmutableSet;

public class ORSetTest extends CRDTSetTest<ImmutableSet<String>, ORSet<String>> {
	
	@Rule
	public final ExpectedException thrown = ExpectedException.none();
	
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
	protected ORSet<String> fromPayload(String clientId, byte[] values) {
		return new ORSet<String>(mapper, values);
	}
	
	@Ignore
	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
