================
Metadata element
================


This element contains structured definitions of the resources and ontologies used within the phenopacket. It is considered to be a required element of a valid Phenopacket and application Q/C software should check this.

The protobuf code is

.. code-block:: proto

  message MetaData {
    google.protobuf.Timestamp created = 1;
    string created_by = 2;
    repeated Resource resources = 3;
  }


created (metadata)
==================
This element is a `ISO8601 UTC timestamp <https://en.wikipedia.org/wiki/ISO_8601>`_ for when this phenopacket was created in ISO, e.g.,  "2018-03-01T00:00:00Z"


created_by (metadata)
=====================
This is a string that represents identifier for the contributor/ program. The expected syntax and semantics are application-dependent.


resources (metadata)
====================
This element contains a listing of the ontologies/resources referenced in the phenopacket.
