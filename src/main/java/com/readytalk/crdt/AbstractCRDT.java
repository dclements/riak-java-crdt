package com.readytalk.crdt;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Abstract (state) CRDT representation.
 */
public abstract class AbstractCRDT<S, R extends CRDT<S, R>> implements CRDT<S, R> {
	private final ObjectMapper serializer;
	
	protected AbstractCRDT(final ObjectMapper mapper) {
		serializer = mapper;
	}
	
	protected ObjectMapper serializer() {
		return serializer;
	}
}
