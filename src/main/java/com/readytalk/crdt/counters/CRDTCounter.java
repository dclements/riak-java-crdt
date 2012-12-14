package com.readytalk.crdt.counters;

import com.readytalk.crdt.CRDT;

public interface CRDTCounter<S extends Number, R extends CRDT<S, R>> extends CRDT<S, R> {
	S increment();
	
	/**
	 * Optional.
	 */
	S decrement();
}
