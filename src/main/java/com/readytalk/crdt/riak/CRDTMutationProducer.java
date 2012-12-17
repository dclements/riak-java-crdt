package com.readytalk.crdt.riak;

import javax.annotation.concurrent.ThreadSafe;

import com.basho.riak.client.cap.MutationProducer;
import com.readytalk.crdt.CRDT;

@ThreadSafe
public class CRDTMutationProducer<T extends CRDT<?, T>> implements MutationProducer<T> {

	@Override
	public CRDTMutation<T> produce(final T o) {
		return new CRDTMutation<T>(o);
	}

}
