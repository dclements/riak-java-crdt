package com.readytalk.crdt;

import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
public interface CRDTFactory<T extends CRDT<?, T>> {
	T create();
	T create(byte [] payload);
}
