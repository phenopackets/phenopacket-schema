.. _rstevidence:

========
Evidence
========

This element intends to represent the evidence for an assertion such as an observation of a :ref:`rstphenotypicfeature`.
We recommend the use of terms from the `Evidence & Conclusion Ontology (ECO) <http://purl.obolibrary.org/obo/eco.owl>`_


**Data model**

 .. list-table:: Definition the ``Evidence`` element
    :widths: 25 25 50 50
    :header-rows: 1

    * - Field
      - Type
      - Status
      - Description
    * - evidence_code
      - :ref:`rstontologyclass` representing ECO:0006017
      - required
      - An ontology class that represents the evidence type
    * - reference
      - :ref:`rstexternalreference`
      - optional
      - Representation of the source of the evidence


**Example**

.. code-block:: json

 {
   "evidenceCode": {
     "id": "ECO:0006017",
     "label": "author statement from published clinical study used in manual assertion"
   },
   "reference": {
     "id": "PMID:30962759",
     "description": "Recurrent Erythema Nodosum in a Child with a SHOC2 Gene Mutation"
   }
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
An :ref:`rstexternalreference` is used to store a reference to the publication or other source
that supports the evidence. Not all types of evidence will have an external reference, and therefore
this field is optional.



FHIR mapping
~~~~~~~~~~~~
This element is mapped to the FHIR
element `Condition.evidence <https://www.hl7.org/fhir/condition-definitions.html#Condition.evidence>`_.
