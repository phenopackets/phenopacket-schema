.. _rstevidence:

================
Evidence element
================

This element intends to represent the evidence for an assertion such as an observation of a :ref:`rstphenotypicfeature`.
We recommend the use of terms from the `Evidence & Conclusion Ontology (ECO) <http://purl.obolibrary.org/obo/eco.owl>`_

.. code-block:: proto

  message Evidence {
    OntologyClass evidence_code = 1;
    ExternalReference reference = 2;
  }


evidence_code
~~~~~~~~~~~~~
For example, in order to describe the evidence for a phenotypic observation that is derived from a publication,
one might use
the ECO term *author statement from published clinical study used in manual assertion*
(`ECO:0006017 <https://www.ebi.ac.uk/ols/ontologies/eco/terms?iri=http%3A%2F%2Fpurl.obolibrary.org%2Fobo%2FECO_0006017>`_)
and record a PubMed id in the reference field
(See :ref:`External Reference element`). 


reference
~~~~~~~~~
An :ref:`rstexternalreference` is used to store a reference to the publication that supports the evidence. Not
all types of evidence will have an external reference, and therefore this field is optional.



  .. list-table:: Phenopacket requirements for the ``Evidence`` element
    :widths: 25 50 50
    :header-rows: 1

    * - Field
      - Example
      - Status
    * - evidence_code
      - :ref:`rstontologyclass` representing ECO:0006017
      - required
    * - reference
      - human phenotype ontology
      - optional



FHIR mapping
~~~~~~~~~~~~
This element is mapped to the FHIR
element `Condition.evidence <https://www.hl7.org/fhir/condition-definitions.html#Condition.evidence>`_.
