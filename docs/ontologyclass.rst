.. _rstontologyclass:

==============
Ontology Class
==============

This element is used to represent classes (terms) from ontologies, and is used in many places throughout the
Phenopacket standard. It is a simple, two element message that represents the identifier and the label of
an ontology class. ::

    message OntologyClass {
       string id = 1;
       string label = 2;
    }

The ID should be a CURIE-style identifier e.g. HP:0100024, MP:0001284, UBERON:0001690, i.e.,
the primary key for the ontology class. The label should be the corresponding class name.
The Phenopacket standard requires that the id and the label match in the original ontology. We note that
occasionally, ontology maintainers change the primary label of a term. The id used in a Phenopacket
should match the label of the version of the ontology indicated in the metadata element.


 .. list-table:: Phenopacket requirements for the ``OntologyClass`` element
   :widths: 25 50 50
   :header-rows: 1

   * - Field
     - Example
     - Status
   * - id
     - HP:0001875
     - required
   * - label
     - Neutropenia
     - required



The FHIR mapping is a `CodeableConcept <http://www.hl7.org/fhir/datatypes.html#CodeableConcept>`_.
See also `Coding <http://www.hl7.org/fhir/datatypes.html#Coding>`_.