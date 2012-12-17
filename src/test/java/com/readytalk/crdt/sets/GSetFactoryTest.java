package com.readytalk.crdt.sets;

import org.junit.After;
import org.junit.Before;

import com.readytalk.crdt.CRDTFactoryTest;

public class GSetFactoryTest extends
		CRDTFactoryTest<GSet<String>, GSetFactory<String>> {
	private static final String ONE = "one";

	private GSet<String> set0;
	private GSet<String> set1;

	private GSetFactory<String> factory;

	@Override
	protected GSet<String> defaultCRDT() {
		return set0;
	}

	@Override
	protected GSet<String> populatedCRDT() {
		return set1;
	}

	@Override
	protected GSetFactory<String> factory() {
		return factory;
	}

	@Before
	public void setUp() throws Exception {
		set0 = new GSet<String>(serializer);

		set1 = new GSet<String>(serializer);

		set1.add(ONE);

		factory = new GSetFactory<String>(serializer);
	}

	@After
	public void tearDown() throws Exception {

	}

}
