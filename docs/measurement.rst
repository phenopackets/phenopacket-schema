.. _rstmeasurement:

===========
Measurement
===========

The measurement element is used to record individual measurements. It can capture
quantitative, ordinal (e.g., absent/present), or categorical measurements.


**Data model**


.. list-table:: Definition  of the ``Quantity`` element
   :widths: 25 25 50 50
   :header-rows: 1

   * - Field
     - Type
     - Status
     - Description
   * - description
     - string
     - optional
     - free text.
   * - assay
     - OntologyClass
     - required
     - Class that describes the assay used to produce the measurement
   * - value
     - Value
     - required
     - The result of the measurement
   * - complex_quantity
     - ComplexQuantity
     - optional
     - List of ComplexQuantity elements
   * - time_observed
     - TimeElement
     - recommend
     - Time at which measurement was performed
   * - procedure
     - :ref:`Procedure`
     - optional
     - Clinical procdure performed to acquire the sample used for the measurement


The following example shows measurement of platelet count. The result is abnormally low, but in
general this element can be used to represent normal or abnormal measurements.

.. code-block:: yaml

    measurement:
        assay:
            id: "LOINC:26515-7"
            label: "Platelets [#/volume] in Blood"
        value:
            quantity:
                unitClass:
                    id: "UO:0000316"
                    label: "cells per microliter"
                value: 24000.0
        referenceRange:
            unit:
                id: "UO:0000316"
                label: "cells per microliter"
            low: 150000.0
            high: 450000.0
        timeObserved:
            timestamp: "2020-10-01T10:54:20.021Z"

The following example shows an ordinal measurement. The measurement is for nitrite in urine, and
the result is positive (present).

.. code-block:: yaml

    measurement:
        assay:
            id: "LOINC:5802-4"
            label: "Nitrite [Presence] in Urine by Test strip"
        value:
            ontologyClass:
                id: "NCIT:C25626"
                label: "Present"
        timeObserved:
            timestamp: "2021-01-01T10:54:20.021Z"

This element represents a specific measurement. It may also be appropriate to represent the result of
this test as a :ref:`rstphenotypicfeature` using the HPO term
`Nitrituria <https://hpo.jax.org/app/browse/term/HP:0031812>`_.
Which option to use depends on the analysis goals. The measurement object is intended to represent
specific measurements, and the :ref:`rstphenotypicfeature` is often used to represent a conclusion
that is reached on the basis of the test.

Categorical measurements, in which the outcome of the measurement is represented by one of two or more
options that are not ordered, are represented in an analogous fashion.



description
~~~~~~~~~~~
Free-text description of the feature. Note this is not a acceptable place to document/describe t
he phenotype - the type and onset etc... fields should be used for this purpose.

assay
~~~~~

An ontology class which describes the assay used to produce the measurement.
For example "body temperature" (CMO:0000015) or
"Platelets [#/volume] in Blood" (LOINC:26515-7)
FHIR mapping: Observation.code


value
~~~~~

This element represents the result of the measurement. The measurement can
be quantitative, such as `LOINC:2472-9 <https://loinc.org/2472-9/>`_ (IgM [Mass/volume] in Serum or Plasma)
or ordinal or categorical.

complex_quantity
~~~~~~~~~~~~~~~~

This is intended to represent measurements that consist of a tightly coupled group of related quanitities.
For instance, blood pressure represents a measurement of systolic and diastolic blood pressure.

    // https://github.com/phenopackets/phenopacket-schema/issues/261
    // e.g. type: Increased circulating antibody level (HP:0010702)
    //      quantity: unit: Microgram per Milliliter (NCIT:C64572), value: 23456.0

    //      type:  body temperature (CMO:0000015)
    //      quantity: unit: Degrees Celsius (UO:0000027), value: 37.5
    //                      Degree Celsius (NCIT:C42559), value: 37.5

    //      type:  LOINC: 26515-7 Platelets [#/volume] in Blood
    //      value: quantity: unit: NCIT:C173275 (Count per Cubic Millimeter), value: 600,000
    Value value = 3;

    repeated ComplexQuantity complex_quantity = 7;

    // The time at which the measurement was made
    TimeElement time_observed = 5;

    // Clinical procedure performed on the subject in order to produce the measurement.
    Procedure procedure = 8;
}