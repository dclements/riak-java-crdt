package com.readytalk.crdt.sets;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.After;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;

import com.readytalk.crdt.CRDTTest;

public abstract class CRDTSetTest<S extends Set<String>, R extends CRDTSet<String, S, R>> extends CRDTTest<S, R> {

	protected static final String OBJ_1 = "1";
	protected static final String OBJ_2 = "2";
	protected static final String OBJ_3 = "3";
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void validateDefaults() {
		assertTrue(defaultCRDT().isEmpty());
		assertFalse(firstOrtho().isEmpty());
		assertFalse(secondOrtho().isEmpty());
		assertNotEquals(firstOrtho().value(), secondOrtho().value());
		assertNotEquals(firstOrtho().value(), firstAndSecond().value());
		assertNotEquals(secondOrtho().value(), firstAndSecond().value());
	}
	
	@Test
	public void addReturnsAbsence() {
		assertTrue(defaultCRDT().add(OBJ_1));
		assertFalse(defaultCRDT().add(OBJ_1));
	}

	@Test
	public void addToSetFunctions() {
		defaultCRDT().add(OBJ_1);

		assertTrue(defaultCRDT().contains(OBJ_1));
	}

	@Test
	public void containsAllOfSubset() {
		assertTrue(firstAndSecond().containsAll(firstOrtho()));
	}

	@Test
	public void doesNotContainAllOfSuperset() {
		assertFalse(firstOrtho().containsAll(firstAndSecond()));
	}

	@Test
	public void afterClearIsEmpty() {
		try {
			firstAndSecond().clear();

			assertTrue(firstAndSecond().isEmpty());
		} catch (UnsupportedOperationException ex) {
			Assume.assumeNoException(ex);
		}
	}
	
	@Test
	public void removeShouldRemove() {
		
		defaultCRDT().add(OBJ_1);
		
		try {
			assertTrue(defaultCRDT().remove(OBJ_1));

			assertFalse(defaultCRDT().contains(OBJ_1));
		} catch (UnsupportedOperationException ex) {
			Assume.assumeNoException(ex);
		}
	}
	
	@Test
	public void removeReturnsFalseIfNotPresent() {
		
		try {
			assertFalse(defaultCRDT().remove(OBJ_1));
		} catch (UnsupportedOperationException ex) {
			Assume.assumeNoException(ex);
		}
	}
	
	@Test
	public void retainRemoves() {
		try {
			firstAndSecond().retainAll(firstOrtho());
			
			assertEquals(firstOrtho(), firstAndSecond());
		} catch (UnsupportedOperationException ex) {
			Assume.assumeNoException(ex);
		}
	}
	
	@Test
	public void retainReturnsTrueIfChanged() {
		try {
			assertTrue(firstAndSecond().retainAll(firstOrtho()));
		} catch (UnsupportedOperationException ex) {
			Assume.assumeNoException(ex);
		}
	}
	
	@Test
	public void retainReturnsFalseIfUnchanged() {
		try {
			assertFalse(firstOrtho().retainAll(firstOrtho()));
		} catch (UnsupportedOperationException ex) {
			Assume.assumeNoException(ex);
		}
	}

	@Test
	public void addAllAddsAll() {
		defaultCRDT().addAll(firstOrtho());

		assertTrue(defaultCRDT().containsAll(firstOrtho()));
	}
	
	@Test
	public void sizeChanges() {
		defaultCRDT().add(OBJ_1);
		
		assertEquals(1, defaultCRDT().size());
	}
	
	@Test
	public void addAllReturnsExistence() {
		assertTrue(defaultCRDT().add(OBJ_1));
		
		assertFalse(defaultCRDT().add(OBJ_1));
	}
	
	@Test
	public void mergeCombinesItems() {
		int o1 = firstOrtho().size();
		int o2 = secondOrtho().size();
		
		assertEquals(o1 + o2, firstOrtho().merge(secondOrtho()).size());
	}


}
