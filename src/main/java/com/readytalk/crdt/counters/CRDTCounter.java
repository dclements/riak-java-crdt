package com.readytalk.crdt.counters;

import com.readytalk.crdt.CRDT;

/**
 * Conflict-free replicated counters. 
 *
 * @param <S> The return type. 
 * @param <R> The counter type. 
 */
public interface CRDTCounter<S extends Number, R extends CRDT<S, R>> extends CRDT<S, R> {
	/**
	 * Increments the counter
	 * @return The current value of the counter. 
	 */
	S increment();
	
	/**
	 * Decrements the counter. 
	 * 
	 * @return The current value of the counter. 
	 * @throws UnsupportedOperationException If it is not implemented. 
	 */
	S decrement();
}
