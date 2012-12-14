package com.readytalk.crdt.counters;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonView;
import org.codehaus.jackson.type.TypeReference;

import com.google.common.base.Objects;
import com.google.common.collect.Maps;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import com.readytalk.crdt.AbstractCRDT;
import com.readytalk.crdt.inject.ClientId;

public class GCounter extends AbstractCRDT<BigInteger, GCounter> implements CRDTCounter<BigInteger, GCounter> {

	private final String clientId;
	
	private final Map<String, BigInteger> payload = Maps.newHashMap();
	
	@AssistedInject
	public GCounter(ObjectMapper mapper, @ClientId String client) {
		super(mapper);
		
		clientId = client;
		
		payload.put(clientId, BigInteger.ZERO);
	}
	
	@SuppressWarnings("unchecked")
	@AssistedInject
	public GCounter(ObjectMapper mapper, @ClientId String clientId, @Assisted byte [] payload) {
		this(mapper, clientId);
		
		TypeReference<Map<String, BigInteger>> ref = new TypeReference<Map<String, BigInteger>>() {};
		
		try {
			this.payload.putAll((Map<String, BigInteger>)serializer().readValue(payload, ref));
		} catch (IOException ioe) {
			throw new IllegalArgumentException("Unable to deserialize payload.", ioe);
		}
	}
	
	private GCounter(ObjectMapper mapper, @ClientId String clientId, Map<String, BigInteger> payload) {
		this(mapper, clientId);
		
		this.payload.putAll(payload);
	}
	
	@Override
	public GCounter merge(GCounter other) {
		Map<String, BigInteger> retmap = Maps.newHashMapWithExpectedSize(Math.max(payload.size(), other.payload.size()));
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
		BigInteger retval = payload.get(clientId).add(BigInteger.ONE);
		
		payload.put(clientId, retval);
		
		return this.value();
	}
	
	@JsonView
	@Override
	public byte[] payload() {
		try {
			return serializer().writeValueAsBytes(payload);
		} catch (IOException ioe) {
			throw new IllegalStateException("Cannot serialize payload.");
		}
	}
	
	@Override
	public BigInteger decrement() throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof GCounter)) {
			return false;
		}
		
		GCounter t = (GCounter)o;
		
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
