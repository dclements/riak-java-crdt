package com.readytalk.crdt.util;

import static com.readytalk.crdt.util.CollectionUtils.checkCollectionDoesNotContainNull;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.google.common.collect.Lists;

public class CollectionUtilsTest {
	
	@Rule
	public final ExpectedException thrown = ExpectedException.none();

	@Test
	public void checkCollectionWithoutNullPasses() {
		checkCollectionDoesNotContainNull(Lists.newArrayList(1, 2, 3));
	}
	
	@Test
	public void checkCollectionWithNullThrowsNPE() {
		thrown.expect(NullPointerException.class);
		
		checkCollectionDoesNotContainNull(Lists.newArrayList(1, 2, 3, null));
	}

}
