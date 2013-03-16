Java CRDT Library [![Build Status](https://api.travis-ci.org/dclements/riak-java-crdt.png)](https://travis-ci.org/dclements/riak-java-crdt)
=========

A collection of basic conflict-free replicated data types (CRDTs).  Currently everything is set up to serialize into JSON objects.

Goals
=====
* Provide a set of common interfaces, patterns, and objects for working with CRDTs.
* Some degree of storage independence.  The emphasis is on Riak, but the objects should be portable.

Features
========
* Support for GSets, 2PSets, ORSets, GCounters, PNCounters.
* Unified interfaces for serialization/deserialization of CRDT objects.
* JSON serialization. 
* [JSR 330](http://code.google.com/p/atinject/) annotations for dependency injection.
* [JSR 305](http://findbugs-tutorials.googlecode.com/files/UFIA-305.pdf) annotations for documenting proper use of the API and documenting thread safety.

Future Work
===========

Roughly in order:

* Better [Jackson](http://jackson.codehaus.org) use and integration.
* Removing the self-serialization aspects (or at least make them optional) to better integrate with available tools for working with annotated objects.  There are some [challenges here](http://stackoverflow.com/questions/14004854/circular-generics-with-jackson) that I am trying to find a clean solution for. 
* Tools for specifically working with Riak. 
* Integration tests.
* Full, eventual transition to Java 7.
* Some form of versioning for (de)serialization purposes.
* Have the counters support other Number types.
* Guice modules with the bindings for convenience.
* Derived types for collecting more specific types of information (e.g., statistics). 
* More basic types (e.g., graphs, LWW).
* Add garbage collection.
* Support for other serialization methods.
* Local caching of immutable views. 

Based On
=======

* [Conflict Free Replicated Data Types](http://hal.inria.fr/docs/00/61/73/41/PDF/RR-7687.pdf)
* [A comprehensive study of Convergent and Commutative Replicated Data Types](http://hal.upmc.fr/docs/00/55/55/88/PDF/techreport.pdf)
* [An Optimized Conflict-free Replicated Set](http://www-user.rhrk.uni-kl.de/~bieniusa/paper/techrep2012-semantics.pdf)
* @ericmortiz's [CRDT Library in Python](https://github.com/ericmoritz/crdt)
