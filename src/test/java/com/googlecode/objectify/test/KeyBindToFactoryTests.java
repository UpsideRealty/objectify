package com.googlecode.objectify.test;

import com.google.cloud.datastore.DatastoreOptions;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.test.entity.Trivial;
import com.googlecode.objectify.test.util.TestBase;
import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;
import static com.googlecode.objectify.ObjectifyService.factory;

/**
 * Key.create(String) binds the decoded key to the registered factory's project and database.
 */
class KeyBindToFactoryTests extends TestBase {

	private static com.google.cloud.datastore.Key rawKey(final String project, final String database) {
		return com.google.cloud.datastore.Key.newBuilder(project, "Trivial", 123L, database).build();
	}

	@Test
	void keyAlreadyInTheFactoryLocationIsUnchanged() throws Exception {
		final Key<Trivial> key = Key.create(Trivial.class, 123L);

		assertThat(Key.<Trivial>create(key.toUrlSafe())).isEqualTo(key);
		assertThat(Key.<Trivial>create(key.toLegacyUrlSafe())).isEqualTo(key);
	}

	@Test
	void keyFromAnotherProjectIsBoundToTheFactoryProject() throws Exception {
		final DatastoreOptions options = factory().datastore().getOptions();
		final String urlSafe = Key.<Trivial>create(rawKey("some-other-project", "")).toUrlSafe();

		final com.google.cloud.datastore.Key decoded = Key.<Trivial>create(urlSafe).getRaw();

		assertThat(decoded.getProjectId()).isEqualTo(options.getProjectId());
		assertThat(decoded.getDatabaseId()).isEqualTo(options.getDatabaseId());
		assertThat(decoded.getId()).isEqualTo(123L);
	}

	@Test
	void legacyKeyIsBoundToTheFactoryDatabase() throws Exception {
		final ObjectifyFactory original = factory();
		final DatastoreOptions named = original.datastore().getOptions().toBuilder().setDatabaseId("monolith").build();
		final String legacy = Key.<Trivial>create(rawKey(named.getProjectId(), "monolith")).toLegacyUrlSafe();

		ObjectifyService.init(new ObjectifyFactory(named.getService()));
		try {
			final com.google.cloud.datastore.Key decoded = Key.<Trivial>create(legacy).getRaw();

			assertThat(decoded.getDatabaseId()).isEqualTo("monolith");
			assertThat(decoded.getProjectId()).isEqualTo(named.getProjectId());
			assertThat(decoded.getId()).isEqualTo(123L);
		} finally {
			ObjectifyService.init(original);
		}
	}
}
