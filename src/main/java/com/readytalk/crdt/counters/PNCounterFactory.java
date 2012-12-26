package com.readytalk.crdt.counters;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.readytalk.crdt.CRDTFactory;
import com.readytalk.crdt.inject.ClientId;

@Singleton
public class PNCounterFactory implements CRDTFactory<PNCounter> {
	
	private final ObjectMapper serializer;
	private final String clientId;
	
	@Inject
	public PNCounterFactory(final ObjectMapper mapper, @ClientId final String client) {
		this.serializer = mapper;
		this.clientId = client;
	}

	@Override
	public PNCounter create() {
		return new PNCounter(serializer, clientId);
	}

	@Override
	public PNCounter create(final byte[] payload) {
		return new PNCounter(serializer, clientId, payload);
	}
	
}
