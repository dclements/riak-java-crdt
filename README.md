Java CRDT Library
=========

A collection of basic conflict-free replicated data types (CRDTs).  Currently everything is set up to serialize into JSON objects.  It is designed to be used with [Guice assisted injections](http://code.google.com/p/google-guice/wiki/AssistedInject), but will work without it.


Future Work
===========
* Factories. 
* Tools for specifically working with Riak.
* More basic types (e.g., ORSets).
* Better [Jackson](http://jackson.codehaus.org) use and integration. 
* Checkstyle and Eclipse style configuration. 
* Support for other serialization strategies.


Based On
=======

* [Conflict Free Replicated Data Types](http://hal.inria.fr/docs/00/61/73/41/PDF/RR-7687.pdf)
* [A comprehensive study of Convergent and Commutative Replicated Data Types](http://hal.upmc.fr/docs/00/55/55/88/PDF/techreport.pdf)
* @ericmortiz's [CRDT Library in Python](https://github.com/ericmoritz/crdt)

.
