package com.readytalk.crdt.sets;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

public class TwoPhaseSetTest extends CRDTSetTest<ImmutableSet<String>, TwoPhaseSet<String>> {
	@Rule
	public final ExpectedException thrown = ExpectedException.none();
	
	private TwoPhaseSet<String> set0;
	private TwoPhaseSet<String> set1;
	private TwoPhaseSet<String> set2;
	
	private TwoPhaseSet<String> set12;
	
	@Before
	public void setUp() {
		set0 = new TwoPhaseSet<String>(mapper);
		
		set1 = new TwoPhaseSet<String>(mapper);
		
		set1.add(obj1);
		
		set2 = new TwoPhaseSet<String>(mapper);
		
		set2.add(obj2);
		set2.add(obj3);
		
		set12 = set1.merge(set2);
	}
	
	@After
	public void tearDown() {
		
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
		
		set1.remove(obj1);
		
		set1.add(obj1);
	}
	
	@Test
	public void addingAllWithRemovedThrowsIllegalArgument() {
		thrown.expect(IllegalArgumentException.class);
		
		set1.remove(obj1);
		
		set1.addAll(Sets.newHashSet(obj2, obj1));
	}
	
	@Test
	public void clearPreventsFutureAdds() {
		thrown.expect(IllegalArgumentException.class);
		
		set1.clear();
		
		set1.add(obj1);
	}
	
	@Test
	public void retainIsUnsupported() {
		thrown.expect(UnsupportedOperationException.class);
		
		set12.retainAll(set1);
	}

}
