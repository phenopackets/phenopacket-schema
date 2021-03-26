.. _rsttreatment:

*********
Treatment
*********




This represents treatment with an agent such as a drug (pharmaceutical agent), broadly defined
as prescription and over-the-counter
medicines, vaccines, and large-molecule biologic therapies.


Data model
##########


.. list-table:: Definition  of the ``Treatment`` element
   :widths: 25 25 50 50
   :header-rows: 1

   * - Field
     - Type
     - Status
     - Description
   * - agent
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

Example
#######

The following example describes twice daily dosing of 30 mg of losartan given orally.

.. code-block:: yaml

    treatment:
        agent:
            id: "DrugCentral:1610"
            label: "losartan"
        routeOfAdministration:
            id: "NCIT:C38288"
            label: "Oral Route of Administration"
        doseIntervals:
            - quantity:
                unitClass:
                    id: "UO:0000022"
                    label: "milligram"
                value: 30.0
        scheduleFrequency:
            id: "NCIT:C64496"
            label: "Twice Daily"
        interval:
            start: "2020-03-15T13:00:00Z"
            end: "2020-03-25T09:00:00Z"
        drugType: "PRESCRIPTION"


Explanations
############

agent
~~~~
An ontology term representing the therapeutic agent. This can be
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


