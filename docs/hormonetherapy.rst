.. _rsthormonetherapytreatment:

#######################
HormoneTherapyTreatment
#######################

Hormone therapy for cancer blocks the production or utilization of hormones that cancer cells are dependent on.



Data model
##########


.. list-table:: Definition  of the ``HormoneTherapyTreatment`` element
   :widths: 25 25 25 75
   :header-rows: 1

   * - Field
     - Type
     - Multiplicity
     - Description
   * - treatment
     - Treatment
     - 1..1
     - Details of how the treatment was administered. REQUIRED.
   * - cumulative_dose
     - :ref:`rstquantity`
     - 1..1
     - The cumulative dose administered during the period of the treatment. REQUIRED.



Example
#######

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




