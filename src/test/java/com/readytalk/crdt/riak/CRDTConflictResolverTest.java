package com.readytalk.crdt.riak;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.readytalk.crdt.CRDT;
import com.readytalk.crdt.sets.TwoPhaseSet;

@SuppressWarnings({"unchecked", "rawtypes"})
public class CRDTConflictResolverTest {
	
	private final CRDT obj1 = mock(CRDT.class);
	private final CRDT obj2 = mock(CRDT.class);
	private final CRDT obj12 = mock(CRDT.class);
	private final CRDT obj3 = mock(CRDT.class);
	private final CRDT obj123 = mock(CRDT.class);
	
	private CRDTConflictResolver resolver;
	
	@Before
	public void setUp() {
		reset(obj1, obj2, obj12, obj3, obj123);
		
		when(obj1.merge(eq(obj2))).thenReturn(obj12);
		when(obj12.merge(obj3)).thenReturn(obj123);
		
		resolver = new CRDTConflictResolver();
	}
	
	@Test
	public void mergeCollection() {
		List<CRDT> value = Lists.newArrayList(obj1, obj2, obj3);
		
		assertEquals(obj123, resolver.resolve(value));
	}
	
	
	// per https://github.com/basho/riak-java-client/wiki/Storing-data-in-riak
	@Test
	public void mergeEmptyCollectionReturnsNull() {
		List<TwoPhaseSet<String>> value = Collections.<TwoPhaseSet<String>>emptyList();
		
		assertNull(resolver.resolve(value));
	}

}
