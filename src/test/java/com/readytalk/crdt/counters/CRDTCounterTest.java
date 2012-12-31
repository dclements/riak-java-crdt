package com.readytalk.crdt.counters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;

import org.junit.Assume;
import org.junit.Test;

import com.readytalk.crdt.CRDTTest;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

public abstract class CRDTCounterTest<S extends Number, R extends CRDTCounter<S, R>> extends CRDTTest<S, R> {
	
	@Test
	public void firstOrthoCounterNonZero() {
		assertNotEquals(0, firstOrtho().value().intValue());
	}

	@Test
	public void secondtOrthoCounterNonZero() {
		assertNotEquals(0, secondOrtho().value().intValue());
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
	public void incrementNReturnsValuePlus1() {
		int value = firstAndSecond().value().intValue();

		assertEquals(5 + value, firstAndSecond().increment(5).intValue());
	}

	@Test
	public void incrementSelfModifies() {
		int value = firstAndSecond().value().intValue();

		firstAndSecond().increment();

		assertEquals(1 + value, firstAndSecond().value().intValue());
	}
	
	@Test
	public void incrementNSelfModifies() {
		int value = firstAndSecond().value().intValue();

		firstAndSecond().increment(5);

		assertEquals(5 + value, firstAndSecond().value().intValue());
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
	public void decrementNSelfModifies() {
		try {
			int value = firstAndSecond().value().intValue();

			firstAndSecond().decrement(5);

			assertEquals(value - 5, firstAndSecond().value().intValue());
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
	public void decrementNReturnsValueMinusN() {
		try {
			int value = firstAndSecond().value().intValue();

			assertEquals(value - 5, firstAndSecond().decrement(5).intValue());
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
		defaultCRDT().increment(3);

		assertEquals(3, defaultCRDT().value().intValue());

	}

	@Test
	public void addTwoSubtractTwoEqualsSame() {
		try {
			int value = firstOrtho().value().intValue();
			firstOrtho().increment(2);
			firstOrtho().decrement(2);

			assertEquals(value, firstOrtho().value().intValue());
		} catch (UnsupportedOperationException ex) {
			Assume.assumeNoException(ex);
		}
	}
	
	@Test
	@SuppressFBWarnings("TQ_NEVER_VALUE_USED_WHERE_ALWAYS_REQUIRED")
	public void incrementNegativeNThrowsException() {
		thrown.expect(IllegalArgumentException.class);
		
		defaultCRDT().increment(-1);
	}
	
	@Test
	@SuppressFBWarnings("TQ_NEVER_VALUE_USED_WHERE_ALWAYS_REQUIRED")
	public void decrementNegativeThrowsException() {
		try {
			defaultCRDT().decrement(-1);
			fail();
		} catch (UnsupportedOperationException ex) {
			Assume.assumeNoException(ex);
		} catch (IllegalArgumentException ex) {
			//Expected.
		}
	}
}
