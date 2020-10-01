.. _rsthormonetherapy:

===============
Hormone therapy
===============

Hormone therapy for cancer blocks the production or utilization of hormones that cancer cells are dependent on.





**Data model**


.. list-table:: Definition  of the ``Hormonetherapy`` element
   :widths: 25 25 50 50
   :header-rows: 1

   * - Field
     - Type
     - Status
     - Description
   * - treatment
     - Treatment
     - required
     - The modality of radiation therapy (e.g., electron, photon,...)
   * - cumulative_dose
     - CumulativeDose
     - required
     - The anatomical site where radiation therapy was administered.



**Example**

This example represents treatment with tamoxifen, 20 mg a day by mouth, administered over a time period of
5 years from 2015 to 2020 with a total cumulative dose of 36500 mg.

.. code-block:: yaml

    treatment:
        agent:
            id: "DrugCentral:2561"
            label: "Tamoxifen"
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
        quantity:
            unit:
                id: "NCIT:C28253"
                label: "Milligram"
            value: 36500.0




