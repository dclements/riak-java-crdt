package com.readytalk.crdt.sets;

import java.util.Set;

import com.readytalk.crdt.CRDT;

/**
 * Reconciliation of the java.util.Set and the CRDT Set. Will try to keep to the
 * java standard set implementation where possible.
 * 
 * These sets do not accept null values and will throw exceptions accordingly.
 */
public interface CRDTSet<E, S extends Set<E>, R extends CRDTSet<E, S, R>>
		extends CRDT<S, R>, Set<E> {

}
