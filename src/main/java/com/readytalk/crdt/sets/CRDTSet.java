package com.readytalk.crdt.sets;

import java.util.Set;

import com.readytalk.crdt.CRDT;

public interface CRDTSet<E, S extends Set<E>, R extends CRDTSet<E, S, R>> extends CRDT<S, R>, Set<E> {
	
}
