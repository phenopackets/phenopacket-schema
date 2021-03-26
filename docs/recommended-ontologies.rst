.. _rstrecommendedontologies:

**********************
Recommended Ontologies
**********************

The phenopacket schema can be used with any ontologies. The phenopacket can be compared to a hierarchical structure
with "slots" for ontology terms and other data. Different use cases may require different ontology terms to cover
the subject matter or to fulfil requirements of a specific research project. The spectrum of requirements is so broad
that we do not think it is appropriate to require a specific set of ontologies for use with phenopackets. Nonetheless,
the value of phenopacket-encoded data will be greatly increased if the community of users converges towards a common
set of ontologies (to the extent possible). Here, we provide general recommendations for ontologies that we have found
to be useful. This list is incomplete and we would welcome feedback from the community about ontologies that should be
added to this page.

Units of measurement
####################


The
`Units of measurement ontology <https://pubmed.ncbi.nlm.nih.gov/23060432/>`_
(see also `UoM on OLS <https://www.ebi.ac.uk/ols/ontologies/uo>`_) provides terms for units commonly encountered in
medical data. The following table shows some typical examples.


.. list-table:: ``Example terms from Units of measurement ontology``
   :widths: 50 50
   :header-rows: 1

   * - Label
     - Id
   * - millimolar
     - `UO:0000063 <https://www.ebi.ac.uk/ols/ontologies/uo/terms?iri=http%3A%2F%2Fpurl.obolibrary.org%2Fobo%2FUO_0000063>`_
   * - milligram
     - `UO:0000022 <https://www.ebi.ac.uk/ols/ontologies/uo/terms?iri=http%3A%2F%2Fpurl.obolibrary.org%2Fobo%2FUO_0000022>`_
   * - millimetres of mercury
     - `UO:0000272 <https://www.ebi.ac.uk/ols/ontologies/uo/terms?iri=http%3A%2F%2Fpurl.obolibrary.org%2Fobo%2FUO_0000272>`_

The National Cancer Institute's thesaurus provides a wide range of terms under
the subhierarchy for `Unit of Measure (NCIT:C25709) <https://www.ebi.ac.uk/ols/ontologies/ncit/terms?iri=http%3A%2F%2Fpurl.obolibrary.org%2Fobo%2FNCIT_C25709>`_.
and for ``Schedule Frequency (NCIT:C64493) <https://www.ebi.ac.uk/ols/ontologies/ncit/terms?iri=http%3A%2F%2Fpurl.obolibrary.org%2Fobo%2FNCIT_C64493>`_.
Some typical terms are

.. list-table:: ``Example terms from NCIT Unit of Measure and Schedule Frequency subhierarchies``
   :widths: 50 50
   :header-rows: 1

   * - Label
     - Id
   * - Milligram per Kilogram per Dose
     - `NCIT:C124458 <https://www.ebi.ac.uk/ols/ontologies/ncit/terms?iri=http%3A%2F%2Fpurl.obolibrary.org%2Fobo%2FNCIT_C124458>`_
   * - Twice Daily
     - `NCIT:C64496 <https://www.ebi.ac.uk/ols/ontologies/ncit/terms?iri=http%3A%2F%2Fpurl.obolibrary.org%2Fobo%2FNCIT_C64496>`_
   * - Cells per Milliliter
     - `NCIT:C74919 <https://www.ebi.ac.uk/ols/ontologies/ncit/terms?iri=http%3A%2F%2Fpurl.obolibrary.org%2Fobo%2FNCIT_C74919>`_




