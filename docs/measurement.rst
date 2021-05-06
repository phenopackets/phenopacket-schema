.. _rstmeasurement:

###########
Measurement
###########

The measurement element is used to record individual measurements. It can capture
quantitative, ordinal (e.g., absent/present), or categorical measurements.


Data model
##########


.. list-table:: Definition  of the ``Quantity`` element
   :widths: 25 25 25 75
   :header-rows: 1

   * - Field
     - Type
     - Multiplicity
     - Description
   * - description
     - string
     - 0..1
     - free text.
   * - assay
     - :ref:`rstontologyclass`
     - 1..1
     - Class that describes the assay used to produce the measurement. REQUIRED.
   * - measurement_value
     - one of {:ref:`rstvalue` | :ref:`rstcomplexvalue`}
     - 1..1
     - The result of the measurement. REQUIRED.
   * - time_observed
     - :ref:`rsttimeelement`
     - 0..1
     - Time at which measurement was performed. RECOMMENDED.
   * - procedure
     - :ref:`rstprocedure`
     - 0..1
     - Clinical procdure performed to acquire the sample used for the measurement

Examples
########

The following example shows measurement of platelet count. The result is abnormally low, but in
general this element can be used to represent normal or abnormal measurements.

.. code-block:: yaml

    measurement:
        assay:
            id: "LOINC:26515-7"
            label: "Platelets [#/volume] in Blood"
        value:
            quantity:
                unit:
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

The following example presents a blood pressure measurement. The measurement of blood pressure
consists of two measurements (systolic and diastolic), that are represented as a :ref:`rstcomplexquantity`.


.. code-block:: yaml

    measurement:
        assay:
          id: "CMO:0000003"
          label: "blood pressure measurement"
        complexValue:
          typedQuantities:
          - type:
              id: "NCIT:C25298"
              label: "Systolic Blood Pressure"
            quantity:
                unit:
                  id: "NCIT:C49670"
                  label: "Millimeter of Mercury"
                value: 125.0
          - type:
              id: "NCIT:C25299"
              label: "Diastolic Blood Pressure"
            quantity:
              unit:
                id: "NCIT:C49670"
                label: "Millimeter of Mercury"
              value: 75.0
        timeObserved:
          timestamp: "2021-01-01T10:54:20.021Z"

Explanations
############


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

complex_value
~~~~~~~~~~~~~

This is intended to represent measurements that consist of a tightly coupled group of related quanitities.
For instance, blood pressure represents a measurement of systolic and diastolic blood pressure.


time_observed
~~~~~~~~~~~~~
The time at which the measurement was made.

procedure
~~~~~~~~~
Clinical procedure performed on the subject in order to obtain the sample used for the measurement.
Examples include blood draw and biopsy. If the procedure can be inferred from the measurement or if the
details of the measurement are deemed unimportant (e.g., a blood glucose test is performed on a blood sample
obtained with some procedure that is not specified), this element can be omitted.
