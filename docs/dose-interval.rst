.. _rstdoseinterval:

#############
DoseInterval
#############

This element represents a block of time in which the dosage of a medication was
constant. For example, to represent a period of  30 mg twice a day for an interval of 10 days, we would
use a :ref:`rstquantity` element to represent the individual `30 mg` dose, and :ref:`rstontologyclass`
element to represent `twice a day`, and an :ref:`rstinterval` element to represent the 10-day interval.




Data model
##########


.. list-table:: Definition  of the ``DoseInterval`` element
   :widths: 25 25 50 50
   :header-rows: 1

   * - Field
     - Type
     - Multiplicity
     - Description
   * - quantity
     - :ref:`rstquantity`
     - 1..1
     - Amount administered in one dose. REQUIRED.
   * - schedule_frequency
     - :ref:`rstontologyclass`
     - 1..1
     - how often doses are administered per day (or other indicated duration). REQUIRED.
   * - interval
     - :ref:`rsttimeinterval`
     - 1..1
     - The specific interval over which the dosage was administered in the indicated quantity. REQUIRED.


Example
#######

The following message represents a dose interval from March 15, 2020 to March 25, 2020, in which a constant dose
of 30 mg was given twice a day.

.. code-block:: yaml

  doseInterval:
    quantity:
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


Explanations
############


quantity
~~~~~~~~

The amount of an individual dose (See :ref:`rstquantity`).

schedule_frequency
~~~~~~~~~~~~~~~~~~
This element specifies the number of instances within a specific time period. It is intended
to have the same meaning as the NCIT
`Schedule Frequency <https://www.ebi.ac.uk/ols/ontologies/ncit/terms?iri=http%3A%2F%2Fpurl.obolibrary.org%2Fobo%2FNCIT_C64493>`_
class.

interval
~~~~~~~~
The time interval over which the specified dosage is given. See :ref:`rsttimeinterval` for information
about privacy concerns.

