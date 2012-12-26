package com.readytalk.crdt.sets;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.readytalk.crdt.util.CollectionUtils.checkCollectionDoesNotContainNull;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ForwardingSet;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterators;
import com.google.common.collect.Sets;

/**
 * Grow-only Sets. Do not implement the remove operations.
 * 
 */
public class GSet<E> extends ForwardingSet<E> implements
		CRDTSet<E, ImmutableSet<E>, GSet<E>> {

	private final Set<E> delegate = Sets.newLinkedHashSet();

	private final ObjectMapper serializer;

	private final TypeReference<List<E>> ref = new TypeReference<List<E>>() {

	};

	@Inject
	public GSet(final ObjectMapper mapper) {
		serializer = mapper;
	}

	@SuppressWarnings("unchecked")
	public GSet(final ObjectMapper mapper, final byte[] payload) {
		serializer = mapper;

		try {
			delegate.addAll((List<E>) serializer.readValue(payload, ref));
		} catch (IOException ioe) {
			throw new IllegalArgumentException("Unable to deserialize.", ioe);
		}

	}

	private GSet(final ObjectMapper mapper, final Set<E> set) {
		serializer = mapper;

		delegate.addAll(set);
	}

	@Override
	protected Set<E> delegate() {
		return delegate;
	}

	@Override
	public void clear() {
		throw new UnsupportedOperationException();

	}

	@Override
	public Iterator<E> iterator() {
		return Iterators.unmodifiableIterator(super.iterator());
	}

	@Override
	public boolean remove(final Object o) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeAll(final Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean retainAll(final Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public GSet<E> merge(final GSet<E> other) {
		Set<E> retval = Sets.newLinkedHashSet();

		retval.addAll(delegate);
		retval.addAll(other.delegate);

		return new GSet<E>(serializer, retval);
	}

	@Override
	public ImmutableSet<E> value() {
		return ImmutableSet.copyOf(delegate);
	}

	@Override
	public byte[] payload() {
		try {
			return serializer.writeValueAsBytes(delegate);
		} catch (IOException ioe) {
			throw new IllegalStateException("Unable to serialize.", ioe);
		}
	}

	@Override
	public boolean contains(final Object object) {
		checkNotNull(object);

		return super.contains(object);
	}

	@Override
	public boolean add(final E element) {
		checkNotNull(element);

		return super.add(element);
	}

	@Override
	public boolean containsAll(final Collection<?> collection) {
		checkCollectionDoesNotContainNull(collection);

		return super.containsAll(collection);
	}

	@Override
	public boolean addAll(final Collection<? extends E> collection) {
		checkCollectionDoesNotContainNull(collection);

		return super.addAll(collection);
	}

}
