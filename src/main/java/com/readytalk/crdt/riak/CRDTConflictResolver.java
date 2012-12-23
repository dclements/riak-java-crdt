package com.readytalk.crdt.riak;

import java.util.Collection;
import java.util.Iterator;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import javax.inject.Inject;

import com.basho.riak.client.cap.ConflictResolver;
import com.google.common.annotations.Beta;
import com.readytalk.crdt.CRDT;

@Beta
@Immutable
public class CRDTConflictResolver<T extends CRDT<?, T>> implements ConflictResolver<T> {
	
	@Inject
	public CRDTConflictResolver() {
		
	}

	@Override
	@Nullable
	public T resolve(final Collection<T> siblings) {
		
		T retval = null;
		Iterator<T> iter = siblings.iterator();
		
		while (iter.hasNext()) {
			if (retval == null) {
				retval = iter.next();
			} else {
				retval = retval.merge(iter.next());
			}
		}
		
		return retval;
	}

}
