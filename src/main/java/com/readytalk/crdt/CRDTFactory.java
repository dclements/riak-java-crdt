package com.readytalk.crdt;

import javax.annotation.concurrent.ThreadSafe;

/**
 * A factory for producing a specific CRDT.
 *
 * @param <T>  The implementing CRDT type.
 */
@ThreadSafe
public interface CRDTFactory<T extends CRDT<?, T>> {
	T create();
	T create(byte [] payload);
}
