.. _rstdoseinterval:

=============
Dose Interval
=============

This element represents a block of time in which the dosage of a medication was
constant. For example, to represent a period of  30 mg twice a day for an interval of 10 days, we would
use a :ref:`rstquantity` element to represent the individual `30 mg` dose, and :ref:`rstontologyclass`
element to represent `twice a day`, and an :ref:`rstinterval` element to represent the 10-day interval.




**Data model**


.. list-table:: Definition  of the ``Quantity`` element
   :widths: 25 25 50 50
   :header-rows: 1

   * - Field
     - Type
     - Status
     - Description
   * - quantity
     - Quantity
     - required
     - Amount administered in one dose
   * - schedule_frequency
     - OntologyClass
     - required
     - how often doses are administered per day (or other indicated duration)
   * - interval
     - Interval
     - required
     - The specific interval over which the dosage was administered in the indicated quantity.


**Example**

The following message represents a dose interval from March 15, 2020 to March 25, 2020, in which a constant dose
of 30 mg was given twice a day.

.. code-block:: yaml

  quantity:
    unit:
        id: "NCIT:C28253"
        label: "Milligram"
    value: 30.0
  scheduleFrequency:
    id: "NCIT:C64496"
    label: "Twice Daily"
  interval:
    start: "2020-03-15T13:00:00Z"
    end: "2020-03-25T09:00:00Z"

**Privacy concerns**

In some cases it may be desirable to shift all specific dates in a phenopacket by the same random amount. For instance, we
might shift all dates by 2 years. In this case the above interval element would be represented as follows

.. code-block:: yaml

  interval:
    start: "2018-03-15T13:00:00Z"
    end: "2018-03-25T09:00:00Z"


We are considering adding a boolean element to the :ref:`metadata` that would indicate whether the dates are shifted in this way.