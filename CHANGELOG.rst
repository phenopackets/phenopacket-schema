=========
Changelog
=========

v1.0.0 to v2.0
--------------


Phenopacket
~~~~~~~~~~~
- Removed `genes` and `variant` fields. These should be sent as part of an `Interpretation`.


TimeElement
~~~~~~~~~~~
- Added a new `TimeElement` message to wrap all the previous `oneof`

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




