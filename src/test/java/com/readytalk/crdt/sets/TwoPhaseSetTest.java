package com.readytalk.crdt.sets;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

public class TwoPhaseSetTest extends CRDTSetTest<ImmutableSet<String>, TwoPhaseSet<String>> {
	
	private TwoPhaseSet<String> set0;
	private TwoPhaseSet<String> set1;
	private TwoPhaseSet<String> set2;
	
	private TwoPhaseSet<String> set12;
	
	@Before
	public void setUp() {
		set0 = new TwoPhaseSet<String>(mapper);
		
		set1 = new TwoPhaseSet<String>(mapper);
		
		set1.add(OBJ_1);
		
		set2 = new TwoPhaseSet<String>(mapper);
		
		set2.add(OBJ_2);
		set2.add(OBJ_3);
		
		set12 = set1.merge(set2);
	}
	
	@After
	public void tearDown() {
		
	}
	
	@Override
	public TwoPhaseSet<String> newSet() {
		return new TwoPhaseSet<String>(mapper);
	}
	
	@Override
	protected TwoPhaseSet<String> defaultCRDT() {
		return set0;
	}

	@Override
	protected TwoPhaseSet<String> firstOrtho() {
		return set1;
	}

	@Override
	protected TwoPhaseSet<String> secondOrtho() {
		return set2;
	}

	@Override
	protected TwoPhaseSet<String> firstAndSecond() {
		return set12;
	}

	@Override
	protected TwoPhaseSet<String> fromPayload(String _clientId, byte[] values) {
		return new TwoPhaseSet<String>(mapper, values);
	}

	@Test
	public void addingRemovedThrowsIllegalArgument() {
		thrown.expect(IllegalArgumentException.class);
		
		set1.remove(OBJ_1);
		
		set1.add(OBJ_1);
	}
	
	@Test
	public void addingAllWithRemovedThrowsIllegalArgument() {
		thrown.expect(IllegalArgumentException.class);
		
		set1.remove(OBJ_1);
		
		set1.addAll(Sets.newHashSet(OBJ_2, OBJ_1));
	}
	
	@Test
	public void clearPreventsFutureAdds() {
		thrown.expect(IllegalArgumentException.class);
		
		set1.clear();
		
		set1.add(OBJ_1);
	}
	
	@Test
	public void retainIsSupported() {
		
		set12.retainAll(set1);
		
		assertEquals(1, set12.size());
		assertTrue(set12.contains(OBJ_1));
	}
	
	@Test
	public void removeIsSupported() {
		set1.remove(OBJ_1);
	}

}
