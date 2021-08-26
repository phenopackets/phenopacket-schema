=========
Changelog
=========

1.0.0 to 2.0
--------------

This is no an exhaustive list of changes, but highlights the most important additions and breaking changes.

Phenopacket
~~~~~~~~~~~
- Removed `genes` and `variant` fields. These should be sent as part of an `Interpretation`.


Biosample
~~~~~~~~~~~
- Removed `variant` field. These should be sent as part of an `Interpretation`.


TimeElement
~~~~~~~~~~~
- Added a new `TimeElement` message to wrap all the previous `oneof` in `Individual`, `Biosample`, `Disease`, `PhenotypicFeature`

.. code-block::

    message TimeElement {
        oneof element {
            GestationalAge gestational_age = 6;
            Age age = 1;
            AgeRange age_range = 2;
            OntologyClass ontology_class = 3;
            google.protobuf.Timestamp timestamp = 4;
            TimeInterval interval = 5;
        }
    }

GestationalAge
~~~~~~~~~~~~~~
- Added new `GestationalAge` message for pre-natal individuals and samples


Individual
~~~~~~~~~~
- Replaced oneof `age_at_collection` with new `TimeElement` message
- Added new `OntologyClass` `gender` field
- Added new `VitalStatus` field


VitalStatus
~~~~~~~~~~~
- Added new message for capturing the vital status of an individual


PhenotypicFeature
~~~~~~~~~~~~~~~~~
- Replaced oneof `onset` with new `TimeElement` message


Disease
~~~~~~~~~~~~~~~~~
- Replaced oneof `onset` with new `TimeElement` message


Measurement
~~~~~~~~~~~

- Added new `Measurement` message for capturing quantitative, ordinal (e.g., absent/present), or categorical measurements. This element is available as a repeated field in the `Phenopacket` and `Biosample` top-level elements.


MedicalAction
~~~~~~~~~~~~~

- Added new `MedicalAction` to capture medications, procedures, other actions taken for clinical management. This element is available as a repeated field in the `Phenopacket`.


Interpretation
~~~~~~~~~~~~~~

- `Interpretation` is now a sub-element of a `Phenopacket`, rather than an enclosing element. The change allows for better semantics on the `Gene` and `Variant` types and their relationship to an `Individual` or `Biosample` in the context of a `Diagnosis` based on a `GenomincInterpretation`.
