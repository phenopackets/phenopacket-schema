.. _rstchemotherapytreatment:

#####################
ChemoTherapyTreatment
#####################

Chemotherapy refers to treatment of cancer with drugs that are sufficiently toxic to
kill tumor cells and have a pharmacological action that involves DNA damage. This element
uses an embedded :ref:`rsttreatment` and additionally records the cumulative dose, defined
as the total dose from repeated exposures to chemotherapy, monitoring of which is an important
part of treatment with chemotherapy. For instance, cardiac side effect risk increases with
greater cumulative doses of antrhacycline.


Data model
##########


.. list-table:: Definition  of the ``ChemoTherapyTreatment`` element
   :widths: 25 25 25 75
   :header-rows: 1

   * - Field
     - Type
     - Multiplicity
     - Description
   * - treatment
     - :ref:`rsttreatment`
     - 1..1
     - Details of how the treatment was administered. REQUIRED.
   * - cumulative_dose
     - :ref:`rstquantity`
     - 1..1
     - The cumulative dose administered during the period of the treatment. REQUIRED.


Example
#######

The following example specifies that aclarubicin (a type of anthracycline) was given
intravenously every three weeks in the time period from 2020-07-10 to 2020-08-10,
for a cumulative dose of 200 mg/kg.

.. code-block:: yaml

    chemoTherapyTreatment:
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




