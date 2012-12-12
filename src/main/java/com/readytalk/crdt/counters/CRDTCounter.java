package com.readytalk.crdt.counters;

import javax.annotation.Nonnegative;

import com.readytalk.crdt.CRDT;

/**
 * Conflict-free replicated counters. 
 *
 * @param <S> The return type. 
 * @param <R> The counter type. 
 */
public interface CRDTCounter<S extends Number, R extends CRDT<S, R>> extends CRDT<S, R> {
	/**
	 * Increments the counter.
	 * 
	 * @return The current value of the counter. 
	 */
	S increment();
	
	/**
	 * Increments the counter by the amount n.
	 * 
	 * @return The current value of the counter. 
	 */
	S increment(@Nonnegative int n);
	
	/**
	 * Decrements the counter. 
	 * 
	 * @return The current value of the counter. 
	 * @throws UnsupportedOperationException If it is not implemented. 
	 */
	S decrement();
	
	/**
	 * Decrements the counter by the amount n.
	 * 
	 * @return The current value of the counter. 
	 * @throws UnsupportedOperationException If it is not implemented. 
	 */
	S decrement(@Nonnegative int n);
}
