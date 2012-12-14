package com.readytalk.crdt.counters;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Map;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import com.google.common.collect.Maps;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import com.readytalk.crdt.AbstractCRDT;
import com.readytalk.crdt.inject.ClientId;

public class PNCounter extends AbstractCRDT<BigInteger, PNCounter> implements CRDTCounter<BigInteger, PNCounter> {
	
	private final GCounter positive;
	private final GCounter negative;

	private final String clientId;

	@AssistedInject
	public PNCounter(ObjectMapper mapper, @ClientId String clientId) {
		super(mapper);

		positive = new GCounter(mapper, clientId);
		negative = new GCounter(mapper, clientId);

		this.clientId = clientId;
	}

	@AssistedInject
	public PNCounter(ObjectMapper mapper, @ClientId String clientId, @Assisted byte[] payload) {
		super(mapper);
		
		this.clientId = clientId;
		
		try {
			TypeReference<Map<String, JsonNode>> ref = new TypeReference<Map<String, JsonNode>>() {};
			
			JsonNode node = mapper.readTree(payload);
			
			Map<String, JsonNode> retval = mapper.readValue(node, ref);
			
			positive = new GCounter(mapper, clientId, mapper.writeValueAsBytes(retval.get("p")));
			negative = new GCounter(mapper, clientId, mapper.writeValueAsBytes(retval.get("n")));
			
		} catch (IOException ioe) {
			throw new IllegalArgumentException("Invalid JSON", ioe);
		}
		
	}

	private PNCounter(ObjectMapper mapper, @ClientId String clientId, GCounter p, GCounter n) {
		super(mapper);

		positive = p;
		negative = n;

		this.clientId = clientId;
	}

	@Override
	public PNCounter merge(PNCounter other) {
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
			
			retval.put("p", serializer().readTree(positive.payload()));
			retval.put("n", serializer().readTree(negative.payload()));
			
			return serializer().writeValueAsBytes(retval);
		} catch (IOException ioe) {
			throw new IllegalStateException("Could not serialize.", ioe);
		}
	}

	public BigInteger increment() {
		this.positive.increment();

		return this.value();
	}

	public BigInteger decrement() {
		this.negative.increment();

		return this.value();
	}
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof PNCounter)) {
			return false;
		}
		
		PNCounter t = (PNCounter)o;
		
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
