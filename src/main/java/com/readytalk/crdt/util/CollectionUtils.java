package com.readytalk.crdt.util;

import java.util.Collection;

/**
 * Utility methods for validation when working with collections.
 * 
 */
public final class CollectionUtils {
	private CollectionUtils() {

	}

	public static void checkCollectionDoesNotContainNull(
			final Collection<?> collection) {
		boolean containsNull = false;

		try {
			containsNull = collection.contains(null);
		} catch (NullPointerException ex) {
			// Not a problem.
		}

		if (containsNull) {
			throw new NullPointerException();
		}
	}
}
