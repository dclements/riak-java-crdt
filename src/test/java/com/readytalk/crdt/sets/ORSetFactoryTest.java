package com.readytalk.crdt.sets;

import org.junit.After;
import org.junit.Before;

import com.readytalk.crdt.CRDTFactoryTest;

public class ORSetFactoryTest extends
		CRDTFactoryTest<ORSet<String>, ORSetFactory<String>> {

	private static final String ONE = "one";

	private ORSet<String> set0;
	private ORSet<String> set1;

	private ORSetFactory<String> factory;

	@Override
	protected ORSet<String> defaultCRDT() {
		return set0;
	}

	@Override
	protected ORSet<String> populatedCRDT() {
		return set1;
	}

	@Override
	protected ORSetFactory<String> factory() {
		return factory;
	}

	@Before
	public void setUp() throws Exception {
		set0 = new ORSet<String>(serializer);

		set1 = new ORSet<String>(serializer);

		set1.add(ONE);

		factory = new ORSetFactory<String>(serializer);
	}
	
	@After
	public void tearDown() throws Exception {
		
	}


}
