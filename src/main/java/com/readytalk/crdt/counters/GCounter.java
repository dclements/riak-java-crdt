package com.readytalk.crdt.counters;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Map;

import javax.annotation.Nonnegative;
import javax.inject.Inject;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.readytalk.crdt.AbstractCRDT;
import com.readytalk.crdt.inject.ClientId;

/**
 * Grow-only counter.  Does not support decrementing.
 *
 */
public class GCounter extends AbstractCRDT<BigInteger, GCounter> implements CRDTCounter<BigInteger, GCounter> {
	
	private static final TypeReference<Map<String, BigInteger>> REF = new TypeReference<Map<String, BigInteger>>() {

	};
	
	private final String clientId;

	private final Map<String, BigInteger> payload = Maps.newHashMap();

	@Inject
	public GCounter(final ObjectMapper mapper, @ClientId final String client) {
		super(mapper);

		clientId = client;
		
		payload.put(clientId, BigInteger.ZERO);
	}

	@SuppressWarnings("unchecked")
	public GCounter(final ObjectMapper mapper, @ClientId final String client, final byte[] value) {
		this(mapper, client);

		

		try {
			this.payload.putAll((Map<String, BigInteger>) serializer().readValue(value, REF));
		} catch (IOException ioe) {
			throw new IllegalArgumentException("Unable to deserialize payload.", ioe);
		}
	}

	private GCounter(final ObjectMapper mapper, @ClientId final String client, final Map<String, BigInteger> value) {
		this(mapper, client);

		this.payload.putAll(value);
	}

	@Override
	public GCounter merge(final GCounter other) {
		Map<String, BigInteger> retmap = Maps
				.newHashMapWithExpectedSize(Math.max(payload.size(), other.payload.size()));
		retmap.putAll(payload);

		for (Map.Entry<String, BigInteger> o : other.payload.entrySet()) {
			BigInteger value = Objects.firstNonNull(retmap.get(o.getKey()), BigInteger.ZERO);

			retmap.put(o.getKey(), o.getValue().max(value));
		}

		return new GCounter(serializer(), clientId, retmap);
	}

	@Override
	public BigInteger value() {
		BigInteger retval = BigInteger.ZERO;

		for (BigInteger o : payload.values()) {
			retval = retval.add(o);
		}

		return retval;
	}

	public BigInteger increment() {
		return this.increment(1);
	}
	
	@Override
	public BigInteger increment(@Nonnegative final int n) {
		Preconditions.checkArgument(n >= 0);
		
		BigInteger retval = payload.get(clientId).add(BigInteger.valueOf(n));

		payload.put(clientId, retval);

		return this.value();
	}
	
	@Override
	public byte[] payload() {
		try {
			return serializer().writeValueAsBytes(payload);
		} catch (IOException ioe) {
			throw new IllegalStateException("Cannot serialize payload.");
		}
	}

	@Override
	public BigInteger decrement() {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public BigInteger decrement(@Nonnegative final int n) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean equals(final Object o) {
		if (!(o instanceof GCounter)) {
			return false;
		}

		GCounter t = (GCounter) o;

		if (this == t) {
			return true;
		} else {
			return this.value().equals(t.value());
		}
	}

	@Override
	public int hashCode() {
		return this.value().hashCode();
	}

}
