package com.readytalk.crdt.sets;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.After;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;

import com.readytalk.crdt.CRDTTest;

public abstract class CRDTSetTest<S extends Set<String>, R extends CRDTSet<String, S, R>> extends CRDTTest<S, R> {

	protected final String obj1 = "1";
	protected final String obj2 = "2";
	protected final String obj3 = "3";
	
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
		assertFalse(firstOrtho().value().equals(secondOrtho().value()));
	}
	
	@Test
	public void addReturnsAbsence() {
		assertTrue(defaultCRDT().add(obj1));
		assertFalse(defaultCRDT().add(obj1));
	}

	@Test
	public void addToSetFunctions() {
		defaultCRDT().add(obj1);

		assertTrue(defaultCRDT().contains(obj1));
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
		
		defaultCRDT().add(obj1);
		
		try {
			assertTrue(defaultCRDT().remove(obj1));

			assertFalse(defaultCRDT().contains(obj1));
		} catch (UnsupportedOperationException ex) {
			Assume.assumeNoException(ex);
		}
	}
	
	@Test
	public void removeReturnsFalseIfNotPresent() {
		
		try {
			assertFalse(defaultCRDT().remove(obj1));
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
		defaultCRDT().add(obj1);
		
		assertEquals(1, defaultCRDT().size());
	}
	
	@Test
	public void addAllReturnsExistence() {
		assertTrue(defaultCRDT().add(obj1));
		
		assertFalse(defaultCRDT().add(obj1));
	}


}
