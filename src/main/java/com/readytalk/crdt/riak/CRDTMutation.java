package com.readytalk.crdt.riak;

import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;

import com.basho.riak.client.cap.Mutation;
import com.google.common.annotations.Beta;
import com.google.common.annotations.VisibleForTesting;
import com.readytalk.crdt.CRDT;

@Beta
@NotThreadSafe
public class CRDTMutation<T extends CRDT<?, T>> implements Mutation<T> {

	private T newCRDT;
	
	public CRDTMutation(final T crdt) {
		newCRDT = crdt;
	}
	
	@Override
	public T apply(@Nullable final T original) {
		
		if (original == null) {
			return newCRDT;
		}
		
		newCRDT = newCRDT.merge(original);
		
		return newCRDT;
	}
	
	@VisibleForTesting
	protected T getValue() {
		return newCRDT;
	}

}
