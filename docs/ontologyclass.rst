.. _rstontologyclass:

=============
OntologyClass
=============

This element is used to represent classes (terms) from ontologies, and is used in many places throughout the
Phenopacket standard. It is a simple, two element message that represents the identifier and the label of
an ontology class.

The ID should be a CURIE-style identifier e.g. HP:0100024, MP:0001284, UBERON:0001690, i.e.,
the primary key for the ontology class. The label should be the corresponding class name.
The Phenopacket standard requires that the id and the label match in the original ontology. We note that
occasionally, ontology maintainers change the primary label of a term. The id used in a Phenopacket
should match the label of the version of the ontology indicated in the metadata element.


**Data model**

.. csv-table::
   :header: Field, Type, Status, Description

    id, string, required, a CURIE-style identifier e.g. HP:0001875
    label, string, required, human-readable class name e.g. Neutropenia


**Example**

.. code-block:: json

    {
      "id": "HP:0001875",
      "label": "Neutropenia"
    }


The ID of an OntologyClass element requires a `CURIE <https://www.w3.org/TR/2010/NOTE-curie-20101216/>`_ format.
It must reference the namespace prefix of a :ref:`rstresource` named in the :ref:`rstmetadata`.
