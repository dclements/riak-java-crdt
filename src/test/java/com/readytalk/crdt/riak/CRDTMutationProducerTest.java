package com.readytalk.crdt.riak;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;

import com.readytalk.crdt.CRDT;

@SuppressWarnings({"unchecked", "rawtypes"})
public class CRDTMutationProducerTest {
	
	private final CRDT obj1 = mock(CRDT.class);
	
	private CRDTMutationProducer producer;
	
	@Before
	public void setUp() {
		producer = new CRDTMutationProducer();
	}
	
	@Test
	public void producesWithSetObject() {
		CRDTMutation mutation = producer.produce(obj1);
		
		assertEquals(obj1, mutation.getValue());
	}

}
