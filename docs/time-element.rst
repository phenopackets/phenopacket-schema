.. _rsttimeelement:

############
TimeElement
############

This element intends to bundle all of the various ways of denoting time or age in
phenopackets schema. Starting with version 2, other elements will be required to
use a TimeElement rather than any of the more specific elements. For instance, the
version 1.0 of :ref:`rstphenotypicfeature` uses an :ref:`rstontologyclass` for the age of
onset of the phenotypic feature. Version 2 will replace this with a `TimeElement`. This
will mean that all references to time and age throughout the phenopacket standard
are uniform. That this change was needed became obvious when trying to model an acute
phenotypic abnormality such as an episode of fever occurring one day before admission
to the hospital.


Data model
##########


.. list-table:: Definition  of the ``TimeElement`` element
   :widths: 25 25 25 75
   :header-rows: 1

   * - Field
     - Type
     - Multiplicity
     - Description
   * - gestational_age
     - :ref:`rstgestationalage`
     - (one of the options)
     - measure of the age of a pregnancy
   * - age
     - :ref:`rstage`
     - (one of the options)
     - represents age as a ISO8601 duration (e.g., P40Y10M05D).
   * - age_range
     - :ref:`rstagerange`
     - (one of the options)
     - indicates that the individual's age lies within a given range
   * - ontology_class
     - :ref:`rstontologyclass`
     - (one of the options)
     - indicates the age of the individual as an ontology class
   * - timestamp
     - :ref:`rsttimestamp`
     - (one of the options)
     - indicates a specific time
   * - interval
     - :ref:`rsttimeinterval`
     - (one of the options)
     - indicates an interval of time

Example
#######

The following shows a TimeElement with the :ref:`rstage` option.

.. code-block:: yaml

    timeElement:
        age:
            iso8601duration: "P25Y"

Explanations
############

gestational_age
~~~~~~~~~~~~~~~

A measure of the age of a pregnancy. Gestation, defined as the time between conception and birth,
is measured in weeks and days from the first day of the last menstrual period. See :ref:`rstgestationalage`.

age
~~~
This element can be used to represent age as a ISO8601 duration (e.g., P40Y10M05D). See :ref:`rstage`.

age_range
~~~~~~~~~
This element can be used indicates that the individual's age lies within a given range, which may be
desirable to help preserve privacy. See  :ref:`rstagerange`

ontology_class
~~~~~~~~~~~~~~

If an :ref:`rstontologyclass` is used to represent the age of onset of a phenotypic feature,
then terms for age of onset can be chosen
from the `Onset subhierarchy of the HPO <https://hpo.jax.org/app/browse/term/HP:0003674>`_. See :ref:`rstontologyclass`.


timestamp
~~~~~~~~~
A :ref:`rsttimestamp` can be used to represent a specific time. Note that all timestamps in a phenopacket can be shifted
by the same amount to help preserve privacy if desired.

interval
~~~~~~~~
This element can be used to represent a specific interval of time. See :ref:`rsttimeinterval`.






