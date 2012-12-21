package com.readytalk.crdt;

import javax.annotation.concurrent.NotThreadSafe;

@NotThreadSafe
public interface CRDT<S, R extends CRDT<S, R>> {
	/**
	 * Merge this CRDT with another of the same type.
	 */
	R merge(R other);
	
	/**
	 * The value of this CRDT.  Should be immutable or a defensive copy.
	 */
	S value();
	
	/**
	 * The serialized version of this object.
	 */
	byte [] payload();
}
