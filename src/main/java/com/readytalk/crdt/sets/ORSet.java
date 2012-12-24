package com.readytalk.crdt.sets;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterators;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.readytalk.crdt.AbstractCRDT;

public class ORSet<E> extends AbstractCRDT<ImmutableSet<E>, ORSet<E>> implements
		CRDTSet<E, ImmutableSet<E>, ORSet<E>> {

	private final Multimap<E, UUID> elements = LinkedHashMultimap.create();
	private final Multimap<E, UUID> tombstones = LinkedHashMultimap.create();

	public ORSet(final ObjectMapper mapper) {
		super(mapper);
	}
	
	public ORSet(final ObjectMapper mapper, final byte [] value) {
		super(mapper);
	}
	
	@Override
	public boolean add(E value) {
		UUID uuid = UUID.randomUUID();
		boolean retval = !elements.containsKey(value);
		
		elements.put(value, uuid);
		
		return retval;
	}

	@Override
	public boolean addAll(Collection<? extends E> values) {
		
		boolean retval = false;
		
		for (E o : values) {
			retval |= this.add(o);
		}
		
		return retval;
	}

	@Override
	public void clear() {
		this.tombstones.putAll(this.elements);
		this.elements.clear();

	}

	@Override
	public boolean contains(Object value) {
		return this.elements.containsKey(value);
	}

	@Override
	public boolean containsAll(Collection<?> values) {
		return this.value().containsAll(values);
	}

	@Override
	public boolean isEmpty() {
		return elements.isEmpty();
	}

	@Override
	public Iterator<E> iterator() {
		return Iterators.unmodifiableIterator(this.elements.keySet().iterator());
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean remove(Object value) {
		return this.tombstones.putAll((E)value, elements.get((E)value));
	}

	@Override
	public boolean removeAll(Collection<?> valus) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean retainAll(Collection<?> values) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int size() {
		return elements.size();
	}

	@Override
	public Object[] toArray() {
		return elements.keySet().toArray();
	}

	@Override
	public <T> T[] toArray(T[] arg) {
		return elements.keySet().toArray(arg);
	}

	@Override
	public ORSet<E> merge(ORSet<E> other) {
		ORSet<E> retval = new ORSet<E>(serializer());
		
		retval.elements.putAll(this.elements);
		retval.tombstones.putAll(this.tombstones);
		
		retval.elements.removeAll(other.tombstones);
		
		return retval;
	}

	@Override
	public ImmutableSet<E> value() {
		return ImmutableSet.copyOf(elements.keySet());
	}

	@Override
	public byte[] payload() {
		// TODO Auto-generated method stub
		return null;
	}

}
