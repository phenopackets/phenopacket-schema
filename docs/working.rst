.. _rstworking:

=========================
Working with Phenopackets
=========================

The phenopacket schema has been defined in :ref:`rstprotobuf` which can be considered the source of truth for the
specification. While it is possible to interoperate with other services using JSON produced from
hand-crafted/alternative implementations, we **strongly** suggest using the schema to compile any required
langauge implementations. We provide several examples that demonstrate how to work with Phenopackets in Java and C++.
There are also Python examples in the source code test directory. All three langauge implementations are automatically
produced as part of the build (:ref:`rstjavabuild`)



.. toctree::
   :maxdepth: 1

   Working with Phenopackets in Java <java>
   Working with Phenopackets in C++ <cpp>
   examples