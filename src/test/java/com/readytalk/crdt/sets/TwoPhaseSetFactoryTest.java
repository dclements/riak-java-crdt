package com.readytalk.crdt.sets;

import org.junit.After;
import org.junit.Before;

import com.readytalk.crdt.CRDTFactoryTest;

public class TwoPhaseSetFactoryTest extends
		CRDTFactoryTest<TwoPhaseSet<String>, TwoPhaseSetFactory<String>> {

	private static final String ONE = "one";

	private TwoPhaseSet<String> set0;
	private TwoPhaseSet<String> set1;

	private TwoPhaseSetFactory<String> factory;

	@Override
	protected TwoPhaseSet<String> defaultCRDT() {
		return set0;
	}

	@Override
	protected TwoPhaseSet<String> populatedCRDT() {
		return set1;
	}

	@Override
	protected TwoPhaseSetFactory<String> factory() {
		return factory;
	}

	@Before
	public void setUp() throws Exception {
		set0 = new TwoPhaseSet<String>(serializer);

		set1 = new TwoPhaseSet<String>(serializer);

		set1.add(ONE);

		factory = new TwoPhaseSetFactory<String>(serializer);
	}
	
	@After
	public void tearDown() throws Exception {
		
	}


}
