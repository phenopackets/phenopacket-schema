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
     - :ref:`rsttimelement`
     - optional
     - Should be left blank if patient not known to be deceased
   * - cause_of_death
     - :ref:`rstontologyclass`
     - optional
     - Should be left blank if patient not known to be deceased

The vital status is commonly collected in cohort studies on cancer.

The following shows how the element can be used to report the time and cause of death.

Status
~~~~~~

.. csv-table::
   :header: Name, Ordinal, Description

    UNKNOWN_STATUS, 0, Not assessed or not available.
    ALIVE, 1, Alive. Maps to `NCIT:C37987 <https://www.ebi.ac.uk/ols/ontologies/ncit/terms?iri=http%3A%2F%2Fpurl.obolibrary.org%2Fobo%2FNCIT_C37987>`_
    DECEASED, 2, Dead. Maps to `NCIT:C28554 <https://www.ebi.ac.uk/ols/ontologies/ncit/terms?iri=http%3A%2F%2Fpurl.obolibrary.org%2Fobo%2FNCIT_C28554>`_


.. code-block:: yaml

    vitalStatus:
        status: "DECEASED"
        timeOfDeath:
            timestamp: "2015-10-01T10:54:20.021Z"
        causeOfDeath:
            id: "NCIT:C36263"
            label: "Metastatic Malignant Neoplasm"



The following element should be used to report the individual is alive.

.. code-block:: yaml

    vitalStatus:
        status: "ALIVE"

In practice, this element is useful in cohort studies in which the association of some treatment or genetic variation is
compared with mortality. For many other applications, it may not be necessary to use a VitalStatus element.





