.. _rstontologyclass:

#############
OntologyClass
#############

This element is used to represent classes (terms) from ontologies, and is used in many places throughout the
Phenopacket standard. It is a simple, two element message that represents the identifier and the label of
an ontology class.

The ID SHALL be a CURIE-style identifier e.g. HP:0100024, MP:0001284, UBERON:0001690, i.e., the primary key for the
ontology class. The label should be the corresponding class name. The Phenopacket standard REQUIRES that the id and the
label match in the original ontology. We note that occasionally, ontology maintainers change the primary label of a
term.


Data model
##########

.. csv-table::
   :header: Field, Type, Multiplicity, Description

    id, string, 1..1, a CURIE-style identifier e.g. HP:0001875. REQUIRED.
    label, string, 1..1, human-readable class name e.g. Neutropenia. REQUIRED.


Example
#######

.. code-block:: yaml

    ontologyClass:
        id: "HP:0001875"
        label: "Neutropenia"

Explanations
############


id
~~
The ID of an OntologyClass element MUST take the form of a :ref:`rstcurie` format.
In order that the class is resolvable, it MUST reference the namespace prefix of a :ref:`rstresource` named in the
:ref:`rstmetadata`.

label
~~~~~
The the human-readable label for the concept. This MUST match the ID in the ontology referenced by the namespace prefix
in a :ref:`rstresource` named in the :ref:`rstmetadata`.
