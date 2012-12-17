package com.readytalk.crdt.riak;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.readytalk.crdt.CRDT;

@SuppressWarnings({"rawtypes", "unchecked"})
public class CRDTMutationTest {
	
	
	private final CRDT obj1 = mock(CRDT.class);
	private final CRDT obj2 = mock(CRDT.class);
	private final CRDT obj12 = mock(CRDT.class);
	
	private CRDTMutation mutation;
	
	@Before
	public void setUp() {
		reset(obj1, obj2, obj12);
		
		when(obj1.merge(obj2)).thenReturn(obj12);
		
		mutation = new CRDTMutation(obj1);
	}
	
	@Test
	public void applyReturnsNewObject() {
		assertEquals(obj12, mutation.apply(obj2));
	}
	
	@Test
	public void applyUpdatesObject() {
		mutation.apply(obj2);
		
		assertEquals(obj12, mutation.getValue());
	}
	
	@Test
	public void nullReturnsCurrentObject() {
		assertEquals(obj1, mutation.apply(null));
	}

}
