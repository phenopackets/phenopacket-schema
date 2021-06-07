.. _rsttreatment:

#########
Treatment
#########


This represents treatment with an agent such as a drug (pharmaceutical agent), broadly defined
as prescription and over-the-counter medicines, vaccines, and large-molecule biologic therapies.


Data model
##########


.. list-table:: Definition  of the ``Treatment`` element
   :widths: 25 25 25 75
   :header-rows: 1

   * - Field
     - Type
     - Multiplicity
     - Description
   * - agent
     - :ref:`rstontologyclass`
     - 1..1
     - The drug or therapeutic agent. REQUIRED.
   * - route_of_administration
     - :ref:`rstontologyclass`
     - 0..1
     - How was the drug administered. RECOMMENDED.
   * - dose_intervals
     - :ref:`rstdoseinterval` (List)
     - 0..*
     - dosages. RECOMMENDED.
   * - drug_type
     - :ref:`rstdrugtype`
     - 0..1
     - Context of the drug administration
   * - cumulative_dose
     - :ref:`rstquantity`
     - 0..1
     - The cumulative dose administered during the period of the treatment.

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
          unit:
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


The following example specifies that aclarubicin (a type of anthracycline) was given
intravenously every three weeks in the time period from 2020-07-10 to 2020-08-10, as part of a cancer chemotherapy treatment
for a cumulative dose of 200 mg/kg.

.. code-block:: yaml

    treatment:
        treatment:
            agent:
                id: "DrugCentral:80"
                label: "aclarubicin"
            routeOfAdministration:
                id: "NCIT:C38276"
                label: "Intravenous Route of Administration"
            doseIntervals:
                - quantity:
                    unit:
                        id: "NCIT:C124458"
                        label: "Milligram per Kilogram per Dose"
                    value: 100.0
                scheduleFrequency:
                    id: "NCIT:C64535"
                    label: "Every Three Weeks"
                interval:
                    start: "2020-07-10T00:00:00Z"
                    end: "2020-08-10T00:00:00Z"
            drugType: "EHR_MEDICATION_LIST"
        cumulativeDose:
            unit:
                id: "EFO:0002902"
                label: "milligram per kilogram"
            value: 200.0


This example represents treatment with tamoxifen, 20 mg a day by mouth, administered over a time period of
5 years from 2015 to 2020 with a total cumulative dose of 36500 mg.

.. code-block:: yaml

    treatment:
        agent:
            id: "DrugCentral:2561"
            label: "tamoxifen"
        routeOfAdministration:
            id: "NCIT:C38288"
            label: "Oral Route of Administration"
        doseIntervals:
            - quantity:
                unit:
                    id: "NCIT:C28253"
                    label: "Milligram"
                value: 20.0
        scheduleFrequency:
            id: "NCIT:C125004"
            label: "Once Daily"
        interval:
            start: "2020-03-15T13:00:00Z"
            end: "2020-03-25T09:00:00Z"
        drugType: "PRESCRIPTION"
    cumulativeDose:
        unit:
            id: "NCIT:C28253"
            label: "Milligram"
        value: 36500.0


Explanations
############

agent
~~~~~
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


cumulative_dose
~~~~~~~~~~~~~~~
The cumulative dose, defined as the total dose from repeated exposures to chemotherapy, monitoring of which is an
important part of treatment with chemotherapy. For instance, cardiac side effect risk increases with greater cumulative
doses of antrhacycline.

