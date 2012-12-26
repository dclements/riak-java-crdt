package com.readytalk.crdt.counters;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.readytalk.crdt.CRDTFactory;
import com.readytalk.crdt.inject.ClientId;

@Singleton
public class GCounterFactory implements CRDTFactory<GCounter> {
	
	private final ObjectMapper serializer;
	private final String clientId;
	
	@Inject
	public GCounterFactory(final ObjectMapper mapper, @ClientId final String client) {
		serializer = mapper;
		clientId = client;
	}

	@Override
	public GCounter create() {
		return new GCounter(serializer, clientId);
	}

	@Override
	public GCounter create(final byte[] payload) {
		return new GCounter(serializer, clientId, payload);
	}
	
	
}
