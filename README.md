# Objectify - Nurture Cloud Fork

Objectify is a Java data access API specifically designed for the Google Cloud Datastore (aka the Google App Engine Datastore aka Firestore in Cloud Datastore mode).  It occupies a "middle ground"; easier to use and more transparent than JDO or JPA, but significantly more convenient than the low-level API libraries that Google provides.  Objectify is designed to make novices immediately productive yet also expose the full power of the Datastore.

**Important note about versions**: Objectify v5 and prior use the _Google App Engine API for Java_ and therefore can only be used within Google App Engine Standard. Objectify v6+ uses the _Cloud Datastore API_ and can be used from anywhere - GAE Standard, GAE Flex, GCE, or outside Google Cloud entirely. See the [FAQ](https://github.com/objectify/objectify/wiki/FrequentlyAskedQuestions) for more information.

## Releasing

This fork has been configured to release to our internal artifactory. 

1. You need to run memecached-docker to run the tests (required for release). You can do this by running `docker-compose up` in the `./memcached-docker` folder.
2. You then need to release using the maven release plugin - note you should do this on the `main` branch
3. Start with `mvn release:prepare -Dresume=false` - this will prompt you for the version number and tag the release. The version number should follow SEMVAR (so 6.1.0 for a minor release, 6.1.1 for a bug fix release etc)
4. Then run `mvn release:perform` - this will build the release and push it to artifactory.
5. Finally run `git push && git push --tags` to ensure you changes are in the repo

## Features

  * Objectify lets you persist, retrieve, delete, and query your own **typed objects**.
  ```
  @Entity
  class Car {
      @Id String vin; // Can be Long, long, or String
      String color;
  }
    
  ofy().save().entity(new Car("123123", "red")).now();
  Car c = ofy().load().type(Car.class).id("123123").now();
  ofy().delete().entity(c);
  ```
  * Objectify surfaces **all native datastore features**, including batch operations, queries, transactions, asynchronous operations, and partial indexes.
  * Objectify provides **type-safe key and query classes** using Java generics.
  * Objectify provides a **human-friendly query interface**.
  * Objectify can automatically **cache your data in memcache** for improved read performance.
  * Objectify can store polymorphic entities and perform **true polymorphic queries**.
  * Objectify provides a simple, **easy-to-understand transaction model**.
  * Objectify provides built-in facilities to **help migrate schema changes** forward.
  * Objectify provides **thorough documentation** of concepts as well as use cases.
  * Objectify has an **extensive test suite** to prevent regressions.

## Documentation

Full documentation is available in the [Objectify Wiki](https://github.com/objectify/objectify/wiki).


## Help

Help is provided in the
[Objectify App Engine User Group](https://groups.google.com/forum/?fromgroups#!forum/objectify-appengine)

## Running tests

You need memcached for most of the tests, running `docker-compose up` in the `./memcached-docker` folder before running
tests (like you do with postges for the monolith) will work. 