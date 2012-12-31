package com.readytalk.crdt.sets;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.readytalk.crdt.util.CollectionUtils.checkCollectionDoesNotContainNull;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Nullable;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterators;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.google.common.collect.Sets;
import com.readytalk.crdt.AbstractCRDT;

public class ORSet<E> extends AbstractCRDT<ImmutableSet<E>, ORSet<E>> implements
		CRDTSet<E, ImmutableSet<E>, ORSet<E>> {

	private static final String ELEMENTS_TOKEN = "e";
	private static final String TOMBSTONES_TOKEN = "t";

	private final Multimap<E, UUID> elements = LinkedHashMultimap.create();
	private final Multimap<E, UUID> tombstones = LinkedHashMultimap.create();

	public ORSet(final ObjectMapper mapper) {
		super(mapper);
	}

	public ORSet(final ObjectMapper mapper, final byte[] value) {
		super(mapper);

		TypeReference<Map<String, Map<E, Collection<UUID>>>> ref =
				new TypeReference<Map<String, Map<E, Collection<UUID>>>>() {

		};

		try {
			Map<String, Map<E, Collection<UUID>>> s1 = mapper.readValue(value,
					ref);

			Map<E, Collection<UUID>> e = s1.get(ELEMENTS_TOKEN);
			Map<E, Collection<UUID>> t = s1.get(TOMBSTONES_TOKEN);

			for (Map.Entry<E, Collection<UUID>> o : e.entrySet()) {
				elements.putAll(o.getKey(), o.getValue());
			}

			for (Map.Entry<E, Collection<UUID>> o : t.entrySet()) {
				tombstones.putAll(o.getKey(), o.getValue());
			}

		} catch (IOException ex) {
			throw new IllegalArgumentException("Unable to deserialize.", ex);
		}

	}

	@Override
	public boolean add(final E value) {
		checkNotNull(value);
		
		UUID uuid = UUID.randomUUID();
		boolean retval = !elements.containsKey(value);

		elements.put(value, uuid);

		return retval;
	}

	@Override
	public boolean addAll(final Collection<? extends E> values) {
		checkNotNull(values);
		checkCollectionDoesNotContainNull(values);

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
	public boolean contains(final Object value) {
		checkNotNull(value);
		
		return this.elements.containsKey(value);
	}

	@Override
	public boolean containsAll(final Collection<?> values) {
		checkCollectionDoesNotContainNull(values);
		return this.value().containsAll(values);
	}

	@Override
	public boolean isEmpty() {
		return elements.isEmpty();
	}

	@Override
	public Iterator<E> iterator() {
		return Iterators
				.unmodifiableIterator(this.elements.keySet().iterator());
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean remove(final Object value) {
		checkNotNull(value);

		this.tombstones.putAll((E) value, elements.get((E) value));

		return elements.removeAll(value).size() > 0;

	}

	@Override
	public boolean removeAll(final Collection<?> values) {
		checkNotNull(values);
		checkCollectionDoesNotContainNull(values);

		Multimap<E, UUID> subset = Multimaps.filterKeys(elements,
				new Predicate<E>() {

					@Override
					public boolean apply(final E input) {

						return values.contains(input);
					}
				});

		if (subset.isEmpty()) {
			return false;
		}

		for (E o : Sets.newLinkedHashSet(subset.keySet())) {
			Collection<UUID> result = this.elements.removeAll(o);

			this.tombstones.putAll(o, result);
		}

		return true;
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean retainAll(final Collection<?> values) {
		checkNotNull(values);
		checkCollectionDoesNotContainNull(values);
		
		Set<E> input = Sets.newHashSet((Collection<E>)values);
		Set<E> diff = Sets.difference(this.elements.keySet(), input);
		
		return this.removeAll(diff);
	}

	@Override
	public int size() {
		return elements.keySet().size();
	}

	@Override
	public Object[] toArray() {
		return elements.keySet().toArray();
	}

	@Override
	public <T> T[] toArray(final T[] arg) {
		return elements.keySet().toArray(arg);
	}

	@Override
	public ORSet<E> merge(final ORSet<E> other) {
		ORSet<E> retval = new ORSet<E>(serializer());

		retval.elements.putAll(this.elements);
		retval.elements.putAll(other.elements);
		retval.tombstones.putAll(this.tombstones);
		retval.tombstones.putAll(other.elements);

		retval.elements.removeAll(retval.tombstones);

		return retval;
	}

	@Override
	public ImmutableSet<E> value() {
		return ImmutableSet.copyOf(elements.keySet());
	}

	@Override
	public byte[] payload() {
		Map<String, Object> retval = Maps.newLinkedHashMap();

		retval.put(ELEMENTS_TOKEN, elements.asMap());
		retval.put(TOMBSTONES_TOKEN, tombstones.asMap());

		try {
			return serializer().writeValueAsBytes(retval);
		} catch (IOException ex) {
			throw new IllegalStateException("Unable to serialize object.", ex);
		}
	}

	@Override
	public final boolean equals(@Nullable final Object o) {
		if (!(o instanceof ORSet)) {
			return false;
		}

		ORSet<?> t = (ORSet<?>) o;

		if (this == t) {
			return true;
		} else {
			return this.value().equals(t.value());
		}
	}

	@Override
	public final int hashCode() {
		return this.value().hashCode();
	}
	
	@Override
	public String toString() {
		return this.value().toString();
	}
}
