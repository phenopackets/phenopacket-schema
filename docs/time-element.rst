.. _rsttimeelement:

============
Time Element
============

This element intends to bundle all of the various ways of denoting time or age in
phenopackets schema. Starting with version 2, other elements will be required to
use a TimeElement rather than any of the more specific elements. For instance, the
version 1.0 of :ref:`phenotyperst` uses an :ref:`ontologyclassrst` for the age of
onset of the phenotypic feature. Version 2 will replace this with a `TimeElement`. This
will mean that all references to time and age throughout the phenopacket standard
are uniform. That this change was needed became obvious when trying to model an acute
phenotypic abnormality such as an episode of fever occuring one day before admission
to the hospital.



**Schema**

This is the protobuf definition of the ``TimeElement``.

.. code-block:: json

  message TimeElement {
    oneof element {
        Age age = 1;
        AgeRange age_range = 2;
        OntologyClass ontology_class = 3;
        google.protobuf.Timestamp timestamp = 4;
        Interval interval = 5;
    }
  }

That is, the ``TimeElement`` is defined to contain one of the following components:

* :ref:`rstage`
* :ref:`rstagerange`
* :ref:`rstontologyclass`
* a timestamp
* :ref:`rstinterval`

If an ``OntologyClass`` is used, then terms for age of onset can be chosen
from the `Onset subhierarchy of the HPO <https://hpo.jax.org/app/browse/term/HP:0003674>`_.




