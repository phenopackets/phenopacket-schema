.. _rstpharmaceuticaltreatment:

========================
Pharmaceutical treatment
========================


This represents treatment with a pharmaceutical agent, broadly defined
as prescription and over-the-counter
medicines, vaccines, and large-molecule biologic therapies.


**Data model**


.. list-table:: Definition  of the ``PharmaceuticalTreatment`` element
   :widths: 25 25 50 50
   :header-rows: 1

   * - Field
     - Type
     - Status
     - Description
   * - drug
     - OntologyClass
     - required
     - The drug.
   * - route_of_administration
     - OntologyClass
     - recommended
     - How was the drug administered?
   * - dose_intervals
     - DoseInterval (list)
     - recommended
     - dosages
   * - drug_type
     - DrugType
     - optional
     - Context of the drug administration
   * - stop_reason_id
     - StopReason
     - optional
     - reason to stop taking the drug


drug
~~~~
An ontology term representing the pharmaceutical agent. This can be
a term from `DrugCentral <http://drugcentral.org/>`_,
`RxNorm <https://www.nlm.nih.gov/research/umls/rxnorm/index.html>`_,
`Drugbank <https://www.drugbank.ca/>`_,
`ChEBI <https://www.ebi.ac.uk/chebi/>`_, or other ontologies.


route_of_administration
~~~~~~~~~~~~~~~~~~~~~~~
How the drug is administered, e.g., by mouth or intravenously. This can be
specified by ontology terms from the NCIT subhierarchy for
`Route of Administration <https://www.ebi.ac.uk/ols/ontologies/ncit/terms?iri=http%3A%2F%2Fpurl.obolibrary.org%2Fobo%2FNCIT_C38114>`_.


dose_intervals
~~~~~~~~~~~~~~
block of time in which the dosage of a medication was
constant, e.g., 30 mg/day for an interval of 10 days.
See :ref:`rstdoseinterval`.


drug_type
~~~~~~~~~
The context in which a drug was administered.
See :ref:`rstdrugtype`.



stop_reason_id
~~~~~~~~~~~~~~
the reason for which a medication was discontinued.
See :ref:`rststopreason`.
Todo -- if the medication is still ongoing, we will need to represent this.


