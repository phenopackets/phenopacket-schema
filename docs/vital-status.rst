.. _rstvitalstatus:

============
Vital status
============
This element can be used to report whether the individual is living or dead at the timepoint when the phenopacket
was created (or if the status is unknown).


**Data model**


.. list-table:: Definition  of the ``VitalStatus`` element
   :widths: 25 25 50 50
   :header-rows: 1

   * - Field
     - Type
     - Status
     - Description
   * - status
     - enum
     - required
     - one of UNKNOWN_STATUS, ALIVE, DECEASED.
   * - time_of_death
     - TimeElement
     - optional
     - Should be left blank if patient not known to be deceased
   * - cause_of_death
     - OntologyClass
     - optional
     - Should be left blank if patient not known to be deceased

The vital status is commonly collected in cohort studies on cancer.

The following shows how the element can be used to report the time and cause of death.


.. code-block:: yaml

    VitalStatus:
        status: "DECEASED"
        timeOfDeath:
            timestamp: "2015-10-01T10:54:20.021Z"
        causeOfDeath:
            id: "NCIT:C36263"
            label: "Metastatic Malignant Neoplasm"



The following element should be used to report the individual is alive.

.. code-block:: yaml

    VitalStatus:
        status: "ALIVE"







