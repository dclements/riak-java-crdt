package com.readytalk.crdt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class CRDTFactoryTest<S extends CRDT<?, S>, T extends CRDTFactory<S>> {
	
	protected static final String CLIENT_ID = "none";
	
	protected final ObjectMapper serializer = new ObjectMapper();

	protected abstract S defaultCRDT();
	protected abstract S populatedCRDT();
	protected abstract T factory();
	
	@Test
	public void validateCRDTs() {
		assertNotNull(defaultCRDT());
		assertNotNull(populatedCRDT());
		
		assertNotEquals(defaultCRDT(), populatedCRDT());
	}
	
	@Test
	public void generatesBlank() {
		assertEquals(defaultCRDT().value(), factory().create().value());
	}
	
	@Test
	public void appliesSerialization() {
		assertEquals(populatedCRDT(), factory().create(populatedCRDT().payload()));
	}

}
