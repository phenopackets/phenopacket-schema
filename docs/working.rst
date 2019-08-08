.. _rstworking:

=========================
Working with Phenopackets
=========================

The phenopacket schema has been introduced in :ref:`rstschema` and can be considered the source of truth for the
specification. While it is possible to inter-operate with other services using JSON produced from
hand-crafted/alternative implementations, we **strongly** suggest using the schema to compile any required
language implementations.

Security disclaimer
~~~~~~~~~~~~~~~~~~~
A stand-alone security review has been performed on the specification itself, however this example implementation is
offered as-is, and without any security guarantees. It will need an independent security review before it can be
considered ready for use in security-critical applications. If you integrate this code into your application it is
AT YOUR OWN RISK AND RESPONSIBILITY to arrange for an audit.

Example code
~~~~~~~~~~~~
We provide several examples that demonstrate how to work with Phenopackets in Java and C++.
There are also Python examples in the source code test directory. All three langauge implementations are automatically
produced as part of the build (:ref:`rstjavabuild`).


.. toctree::
   :maxdepth: 1

   Working with Phenopackets in Java <java>
   Working with Phenopackets in C++ <cpp>
   examples