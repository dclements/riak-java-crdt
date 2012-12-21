package com.readytalk.crdt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Ignore;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class CRDTTest<S, R extends CRDT<S, R>> {

	protected static final String CLIENT_ID1 = "one";
	protected static final String CLIENT_ID2 = "two";
	
	protected final ObjectMapper mapper = new ObjectMapper();;

	protected abstract R defaultCRDT();

	protected abstract R firstOrtho();

	protected abstract R secondOrtho();

	protected abstract R firstAndSecond();

	protected abstract R fromPayload(String clientId, byte[] values);

	@Test
	public void secondOrthoCounterDifferent() {
		assertNotEquals(secondOrtho().value(), firstOrtho().value());
	}

	@Test
	public void mergerWithSelfIsEqual() {
		assertNotNull(firstOrtho().value());
		assertEquals(firstOrtho().value(), firstOrtho().merge(firstOrtho()).value());
	}

	@Test
	public void roundTripSerialization() {
		assertEquals(firstAndSecond(), fromPayload("none", firstAndSecond().payload()));
	}
	

	@Test
	public void equalsNullIsFalse() {
		assertFalse(defaultCRDT().equals(null));
	}

	@Test
	public void equalsSelfIsTrue() {
		assertTrue(defaultCRDT().equals(defaultCRDT()));
	}

	@Test
	public void equalsDifferentIsFalse() {
		assertFalse(defaultCRDT().equals(firstOrtho()));
	}

	@Test
	public void equalsSameValueIsTrue() {
		assertTrue(firstAndSecond().equals(firstOrtho().merge(secondOrtho())));
	}

	@Test
	public void hashCodeRemainsSame() {
		assertEquals(defaultCRDT().hashCode(), defaultCRDT().hashCode());
	}

	@Test
	public void hashCodeIsDifferent() {
		assertNotEquals(defaultCRDT().hashCode(), firstAndSecond().hashCode());
	}

	@Test
	public void equalValueSameHashCode() {
		assertEquals(firstAndSecond().hashCode(), firstOrtho().merge(secondOrtho()).hashCode());
	}
	
	@Test
	@Ignore("future")
	public void directlySerializes() throws Exception {
		assertNotNull(mapper.writeValueAsString(firstOrtho()));
	}

}
