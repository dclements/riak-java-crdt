package com.readytalk.crdt.sets;

import javax.inject.Inject;

import org.codehaus.jackson.map.ObjectMapper;

import com.readytalk.crdt.CRDTFactory;

public class GSetFactory<E> implements CRDTFactory<GSet<E>> {
	
	private final ObjectMapper serializer;
	
	@Inject
	public GSetFactory(final ObjectMapper mapper) {
		this.serializer = mapper;
	}

	@Override
	public GSet<E> create() {
		return new GSet<E>(serializer);
	}

	@Override
	public GSet<E> create(final byte[] payload) {
		return new GSet<E>(serializer, payload);
	}

}
