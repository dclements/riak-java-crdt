package com.readytalk.crdt.sets;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.readytalk.crdt.CRDTFactory;

@Singleton
public class TwoPhaseSetFactory<E> implements CRDTFactory<TwoPhaseSet<E>> {

	private final ObjectMapper serializer;

	@Inject
	public TwoPhaseSetFactory(final ObjectMapper mapper) {
		this.serializer = mapper;
	}

	@Override
	public TwoPhaseSet<E> create() {
		return new TwoPhaseSet<E>(serializer);
	}

	@Override
	public TwoPhaseSet<E> create(final byte[] payload) {
		return new TwoPhaseSet<E>(serializer, payload);
	}

}
