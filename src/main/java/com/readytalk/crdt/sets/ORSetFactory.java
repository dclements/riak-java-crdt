package com.readytalk.crdt.sets;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.readytalk.crdt.CRDTFactory;

@Singleton
public class ORSetFactory<E> implements CRDTFactory<ORSet<E>> {
	private final ObjectMapper serializer;
	
	@Inject
	public ORSetFactory(final ObjectMapper mapper) {
		this.serializer = mapper;
	}

	@Override
	public ORSet<E> create() {
		return new ORSet<E>(serializer);
	}

	@Override
	public ORSet<E> create(final byte[] payload) {
		return new ORSet<E>(serializer, payload);
	}
}
