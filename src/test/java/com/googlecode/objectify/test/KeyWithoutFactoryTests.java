package com.googlecode.objectify.test;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.test.entity.Trivial;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;

/**
 * Key.create(String) without a registered factory returns the key as encoded.
 */
class KeyWithoutFactoryTests {

	private ObjectifyFactory previous;

	@BeforeEach
	void clearFactory() {
		previous = ObjectifyService.factoryOrNull();
		ObjectifyService.init(null);
	}

	@AfterEach
	void restoreFactory() {
		ObjectifyService.init(previous);
	}

	private static Key<Trivial> keyIn(final String project, final String database) {
		return Key.create(com.google.cloud.datastore.Key.newBuilder(project, "Trivial", 123L, database).build());
	}

	@Test
	void urlSafeKeyKeepsItsProjectAndDatabase() throws Exception {
		final Key<Trivial> key = keyIn("some-other-project", "monolith");

		final com.google.cloud.datastore.Key decoded = Key.<Trivial>create(key.toUrlSafe()).getRaw();

		assertThat(decoded.getProjectId()).isEqualTo("some-other-project");
		assertThat(decoded.getDatabaseId()).isEqualTo("monolith");
		assertThat(decoded.getId()).isEqualTo(123L);
	}

	@Test
	void legacyKeyKeepsItsProject() throws Exception {
		final Key<Trivial> key = keyIn("some-other-project", "");

		final com.google.cloud.datastore.Key decoded = Key.<Trivial>create(key.toLegacyUrlSafe()).getRaw();

		assertThat(decoded.getProjectId()).isEqualTo("some-other-project");
		assertThat(decoded.getDatabaseId()).isEqualTo("");
		assertThat(decoded.getId()).isEqualTo(123L);
	}
}
