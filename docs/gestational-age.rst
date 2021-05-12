.. _rstgestationalage:

###############
GestationalAge
###############


Gestational age (or menstrual age) is the time elapsed between the first day of the last normal menstrual period and
the day of delivery. The first day of the last menstrual period occurs approximately 2 weeks before ovulation and
approximately 3 weeks before implantation of the blastocyst. Because most women know when their last period began but
not when ovulation occurred, this definition traditionally has been used when estimating the expected date of delivery.
In contrast, chronological age (or postnatal age) is the time elapsed after birth.

Gestational age is conventionally expressed as completed weeks. Therefore, a 25-week, 5-day fetus is considered a
25-week fetus. Gestational age is often reported as W+D. For instance, 33 weeks and 2 days could be reported as 33+2.


Data model
##########


.. list-table:: Definition  of the ``GestationalAge`` element
   :widths: 25 25 25 75
   :header-rows: 1

   * - Field
     - Type
     - Multiplicity
     - Description
   * - weeks
     - int32
     - 1..1
     - Completed weeks of gestation according to the above definition. REQUIRED.
   * - days
     - int32
     - 0..1
     - RECOMMENDED, If available


The following shows how the element can be used to report the gestational age of 33 weeks and 2 days.


.. code-block:: yaml

    gestationalAge:
        weeks: 33
        days: 2


The gestational age element is intended for use in phenopackets that describe prenatal clinical data.


