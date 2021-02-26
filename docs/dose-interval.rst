.. _rstdoseinterval:

=============
Dose Interval
=============

This element represents a block of time in which the dosage of a medication was
constant, e.g., 30 mg/day for an interval of 10 days.


.. code-block:: json

  message DoseInterval {
        Quantity quantity = 1;
        Interval interval = 2;
  }

This element is still under discussion.