.. _rstevidence:

================
Evidence element
================

This element intends to represent the evidence for an assertion such as an observation of a :ref:`Phenotype element`.
We recommend the use of terms from the `Evidence & Conclusion Ontology (ECO) <http://purl.obolibrary.org/obo/eco.owl>`_::

  message Evidence {
    OntologyClass evidence_code = 1;
    ExternalReference reference = 2;
  }




evidence_code and reference
===========================
For example, in order to describe the evidence for a phenotypic observation that is derived from a publication, one might use
the ECO term `author statement from published clinical study used in manual assertion` <https://www.ebi.ac.uk/ols/ontologies/eco/terms?iri=http%3A%2F%2Fpurl.obolibrary.org%2Fobo%2FECO_0006017>`_ and record a PubMed id in the reference field
(See :ref:`External Reference element`). 


  

FHIR mapping (Evidence)
=======================
This element is mapped to the FHIR
element `Condition.evidence <https://www.hl7.org/fhir/condition-definitions.html#Condition.evidence>`_.
