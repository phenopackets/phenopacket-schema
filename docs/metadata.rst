.. _rstmetadata:

========
Metadata
========


This element contains structured definitions of the resources and ontologies used within the phenopacket. It is considered to be a required element of a valid Phenopacket and application Q/C software should check this.

The protobuf code is

.. code-block:: proto

  message MetaData {
    google.protobuf.Timestamp created = 1;
    string created_by = 2;
    string submitted_by = 3;
    repeated Resource resources = 4;
    repeated google.protobuf.Timestamp updated = 5;
    string phenopacket_schema_version = 6;
    repeated ExternalReference external_references = 7;
  }


created
~~~~~~~
This element is a `ISO8601 UTC timestamp <https://en.wikipedia.org/wiki/ISO_8601>`_ for when this phenopacket was
created in ISO, e.g.,  "2018-03-01T00:00:00Z".


created_by
~~~~~~~~~~
This is a string that represents an identifier for the contributor/ program. The expected syntax and semantics are application-dependent.


submitted_by
~~~~~~~~~~~~
This is a string that represents an identifier for the person who submitted the phenopacket (who may not be
the person who created the phenopacket).


resources
~~~~~~~~~
This element contains a listing of the ontologies/resources referenced in the phenopacket.


updated
~~~~~~~
This element is a list of  `ISO8601 UTC timestamp <https://en.wikipedia.org/wiki/ISO_8601>`_ for when
this phenopacket was updated.

phenopacket_schema_version
~~~~~~~~~~~~~~~~~~~~~~~~~~
A string representing the version of the phenopacket-schema according to which a phenopacket was made.

external_references
~~~~~~~~~~~~~~~~~~~
A list of :ref:`rstexternalreference` (such as the PubMed id of a publication from which a
phenopacket was derived).


  .. list-table:: Phenopacket requirements for the ``Metadata`` element
    :widths: 25 50 50
    :header-rows: 1

    * - Field
      - Example
      - Status
    * - created
      - 2019-04-01T15:10:17.808Z
      - required
    * - created_by
      - (Name of person who created the phenopacket
      - required
    * - submitted_by
      - Name of person who submitted the phenopacket
      - optional
    * - resources
      - (see text)
      - required
    * - updated
      - list of timestamps for when phenopacket was updated
      - optional
    * - phenopacket_schema_version
      - 1.0.0
      - optional
    * - external_references
      - :ref:`rstexternalreference`
      - optional

The `Metadata` element MUST have one :ref:`rstresource` element for each ontology or terminology whose
terms are used in the Phenopacket. For instance, if a MONDO term is used to specificy the disease and
HPO terms are used to specificy the phenotypes of a patient, then the `Metadata` element MUST have
one `Resource` element each for MONDO and HPO.