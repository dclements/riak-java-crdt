Java CRDT Library
=========

A collection of basic conflict-free replicated data types (CRDTs).  Currently everything is set up to serialize into JSON objects.

Goals
=====
* Provide a set of common interfaces, patterns, and objects for working with CRDTs.
* Some degree of storage independence.  The emphasis is on Riak, but they should be portable.

Features
========
* Support for GSets, 2PSets, GCounters, PNCounters.
* JSR 330 annotations for dependency injection.

Future Work
===========
* Better [Jackson](http://jackson.codehaus.org) use and integration. 
* Removing the self-serialization aspects (or at least make them optional) to better integrate with available tools for working with annotated objects.
* Tools for specifically working with Riak.  
* More basic types (e.g., ORSets).
* Integration tests.
* Derived types for collecting more specific types of information (e.g., statistics). 
* Eclipse style configuration, checkstyle configurations for tests.
* Support for other serialization strategies.

Based On
=======

* [Conflict Free Replicated Data Types](http://hal.inria.fr/docs/00/61/73/41/PDF/RR-7687.pdf)
* [A comprehensive study of Convergent and Commutative Replicated Data Types](http://hal.upmc.fr/docs/00/55/55/88/PDF/techreport.pdf)
* @ericmortiz's [CRDT Library in Python](https://github.com/ericmoritz/crdt)
