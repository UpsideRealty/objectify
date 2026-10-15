package com.googlecode.objectify.test;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.test.util.TestBase;
import com.googlecode.objectify.util.Closeable;
import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;
import static com.googlecode.objectify.ObjectifyService.factory;
import static com.googlecode.objectify.ObjectifyService.ofy;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests of how the per-thread Objectify stack behaves when it has been corrupted.
 */
class ObjectifyStackTests extends TestBase {

	@Test
	void closingDiscardsInstancesLeakedAboveIt() {
		final Objectify outerOfy = ofy();
		final Closeable middle = factory().begin();
		factory().begin();

		middle.close();

		assertThat(ofy()).isSameInstanceAs(outerOfy);
	}

	@Test
	void closingAnInstanceNotOnTheStackThrowsAndLeavesTheStackAlone() {
		final Objectify outerOfy = ofy();
		final Closeable alreadyClosed = factory().begin();
		alreadyClosed.close();

		assertThrows(IllegalStateException.class, alreadyClosed::close);

		assertThat(ofy()).isSameInstanceAs(outerOfy);
	}
}
