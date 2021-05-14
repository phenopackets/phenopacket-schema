.. _rstevidence:

########
Evidence
########

This element intends to represent the evidence for an assertion such as an observation of a :ref:`rstphenotypicfeature`.
We recommend the use of terms from the `Evidence & Conclusion Ontology (ECO) <http://purl.obolibrary.org/obo/eco.owl>`_


Data model
##########

 .. list-table:: Definition the ``Evidence`` element
    :widths: 25 25 50 50
    :header-rows: 1

    * - Field
      - Type
      - Multiplicity
      - Description
    * - evidence_code
      - :ref:`rstontologyclass`
      - 1..1
      - An ontology class that represents the evidence type. REQUIRED.
    * - reference
      - :ref:`rstexternalreference`
      - 0..1
      - Representation of the source of the evidence


Example
#######

.. code-block:: yaml

    evidence:
        evidenceCode:
            id: "ECO:0006017"
            label: "author statement from published clinical study used in manual assertion"
        reference:
            id: "PMID:30962759"
            description: "Recurrent Erythema Nodosum in a Child with a SHOC2 Gene Mutation"


Explanations
############

evidence_code
~~~~~~~~~~~~~
For example, in order to describe the evidence for a phenotypic observation that is derived from a publication,
one might use
the ECO term *author statement from published clinical study used in manual assertion*
(`ECO:0006017 <https://www.ebi.ac.uk/ols/ontologies/eco/terms?iri=http%3A%2F%2Fpurl.obolibrary.org%2Fobo%2FECO_0006017>`_)
and record a PubMed id in the reference field
(See :ref:`rstexternalreference`).


reference
~~~~~~~~~
An :ref:`rstexternalreference` is used to store a reference to the publication or other source
that supports the evidence. Not all types of evidence will have an external reference, and therefore
this field is optional.

