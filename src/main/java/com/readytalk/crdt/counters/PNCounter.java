package com.readytalk.crdt.counters;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Map;

import javax.annotation.Nonnegative;
import javax.inject.Inject;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.readytalk.crdt.AbstractCRDT;
import com.readytalk.crdt.inject.ClientId;

/**
 * Counter that supports both positive and negative operations.
 *
 */
public class PNCounter extends AbstractCRDT<BigInteger, PNCounter> implements CRDTCounter<BigInteger, PNCounter> {
	private static final TypeReference<Map<String, JsonNode>> REF = new TypeReference<Map<String, JsonNode>>() {
		
	};
	
	
	private static final String POSITIVE_TOKEN = "p";
	private static final String NEGATIVE_TOKEN = "n";
	
	private final GCounter positive;
	private final GCounter negative;

	private final String clientId;

	@Inject
	public PNCounter(final ObjectMapper mapper, @ClientId final String client) {
		super(mapper);

		this.clientId = client;

		positive = new GCounter(mapper, client);
		negative = new GCounter(mapper, client);

	}

	public PNCounter(final ObjectMapper mapper, @ClientId final String client, final byte[] value) {
		super(mapper);

		this.clientId = client;

		try {
			
			Map<String, JsonNode> retval = mapper.readValue(value, REF);

			positive = new GCounter(mapper, client, mapper.writeValueAsBytes(retval.get(POSITIVE_TOKEN)));
			negative = new GCounter(mapper, client, mapper.writeValueAsBytes(retval.get(NEGATIVE_TOKEN)));

		} catch (IOException ioe) {
			throw new IllegalArgumentException("Invalid JSON", ioe);
		}

	}

	private PNCounter(final ObjectMapper mapper, @ClientId final String client, final GCounter p, final GCounter n) {
		super(mapper);

		positive = p;
		negative = n;

		this.clientId = client;
	}

	@Override
	public PNCounter merge(final PNCounter other) {
		return new PNCounter(serializer(), clientId, positive.merge(other.positive), negative.merge(other.negative));
	}

	@Override
	public BigInteger value() {
		return this.positive.value().subtract(this.negative.value());
	}

	@Override
	public byte[] payload() {
		try {
			Map<String, JsonNode> retval = Maps.newHashMap();

			retval.put(POSITIVE_TOKEN, serializer().readTree(positive.payload()));
			retval.put(NEGATIVE_TOKEN, serializer().readTree(negative.payload()));

			return serializer().writeValueAsBytes(retval);
		} catch (IOException ioe) {
			throw new IllegalStateException("Could not serialize.", ioe);
		}
	}
	
	@Override
	public BigInteger increment() {
		this.positive.increment();

		return this.value();
	}
	
	@Override
	public BigInteger increment(@Nonnegative final int n) {
		Preconditions.checkArgument(n >= 0);
		
		this.positive.increment(n);

		return this.value();
	}

	@Override
	public BigInteger decrement() {
		this.negative.increment();

		return this.value();
	}
	
	@Override
	public BigInteger decrement(@Nonnegative final int n) {
		Preconditions.checkArgument(n >= 0);
		
		this.negative.increment(n);

		return this.value();
	}

	@Override
	public boolean equals(final Object o) {
		if (!(o instanceof PNCounter)) {
			return false;
		}

		PNCounter t = (PNCounter) o;

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
