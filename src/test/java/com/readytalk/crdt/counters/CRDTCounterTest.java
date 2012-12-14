package com.readytalk.crdt.counters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Assume;
import org.junit.Test;

import com.readytalk.crdt.CRDTTest;

public abstract class CRDTCounterTest<S extends Number, R extends CRDTCounter<S, R>> extends CRDTTest<S, R> {

	@Test
	public void firstOrthoCounterNonZero() {
		assertFalse(firstOrtho().value().intValue() == 0);
	}

	@Test
	public void secondtOrthoCounterNonZero() {
		assertFalse(secondOrtho().value().intValue() == 0);
	}

	@Test
	public void defaultsToZero() {
		assertEquals(0, defaultCRDT().value().intValue());
	}

	@Test
	public void mergerWithOrthoSums() {
		assertEquals(firstOrtho().value().intValue() + secondOrtho().value().intValue(),
				firstOrtho().merge(secondOrtho()).value().intValue());
	}

	@Test
	public void incrementReturnsValuePlus1() {
		int value = firstAndSecond().value().intValue();

		assertEquals(1 + value, firstAndSecond().increment().intValue());
	}

	@Test
	public void incrementSelfModifies() {
		int value = firstAndSecond().value().intValue();

		firstAndSecond().increment();

		assertEquals(1 + value, firstAndSecond().value().intValue());
	}

	@Test
	public void decrementSelfModifies() {
		try {
			int value = firstAndSecond().value().intValue();

			firstAndSecond().decrement();

			assertEquals(value - 1, firstAndSecond().value().intValue());
		} catch (UnsupportedOperationException ex) {
			Assume.assumeNoException(ex);
		}
	}

	@Test
	public void decrementReturnsValueMinus1() {
		try {
			int value = firstAndSecond().value().intValue();

			assertEquals(value - 1, firstAndSecond().decrement().intValue());
		} catch (UnsupportedOperationException ex) {
			Assume.assumeNoException(ex);
		}
	}

	@Test
	public void decrementCanGoNegative() {
		try {
			assertEquals(-1, defaultCRDT().decrement().intValue());
		} catch (UnsupportedOperationException ex) {
			Assume.assumeNoException(ex);
		}
	}

	@Test
	public void addThreeEqualsThree() {
		defaultCRDT().increment();
		defaultCRDT().increment();
		defaultCRDT().increment();

		assertEquals(3, defaultCRDT().value().intValue());

	}

	@Test
	public void addTwoSubtractTwoEqualsSame() {
		try {
			int value = firstOrtho().value().intValue();
			firstOrtho().increment();
			firstOrtho().increment();
			firstOrtho().decrement();
			firstOrtho().decrement();

			assertEquals(value, firstOrtho().value().intValue());
		} catch (UnsupportedOperationException ex) {
			Assume.assumeNoException(ex);
		}
	}
}
