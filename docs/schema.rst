.. _rstschema:

##################
Phenopacket Schema
##################

The goal of the `phenopacket-schema <https://github.com/phenopackets/phenopacket-schema>`_ is to define a machine-readable
phenotypic description of a patient/sample in the context of rare disease, common/complex disease, or cancer. It aims to
provide sufficient and shareable information of the data outside of the EHR (Electronic Health Record) with the aim of
enabling capturing of sufficient structured data at the point of care by a clinician or clinical geneticist for sharing
with other labs or computational analysis of the data in clinical or research environments.

This work has been produced as part of the `GA4GH Clinical Phenotype Data Capture Workstream <https://ga4gh-cp.github.io/>`_ and is designed to
be compatible with `GA4GH metadata-schemas <https://github.com/ga4gh-metadata/metadata-schemas>`_.

The phenopacket schema defines a common, limited set of data types which may be composed into more specialised types for
data sharing between resources using an agreed upon common schema.

This common schema has been used to define the 'Phenopacket' which is a catch-all collection of data types, specifically
focused on representing disease data both initial data capture and analysis. The phenopacket schema is designed to be both human
and machine-readable, and to inter-operate with standards being developed in organizations such as in the `ISO TC215 committee <https://www.iso.org/committee/7546903.html>`_ and the `HL7 Fast Healthcare Interoperability Resources Specification (aka FHIR®) <http://hl7.org/fhir/>`_.

The structure of the schema is defined in `protobuf <https://developers.google.com/protocol-buffers>`_. You can find out more in the section :ref:`rstprotobuf`.

.. _phenopacket-schema-diagram:

Version 1.0
###########

The diagram below shows an overview of the schema elements.

.. figure:: graph/phenopacket-schema-v1.svg

   Version 1.0 of the schema. Lines between elements indicate composition.


Version 2.0
###########

.. figure:: graph/phenopacket-schema-v2.svg

   Version 2.0 of the schema. Lines between elements indicate composition.

Version 2.0 includes significant changes and additions to the model to enable better representation of cancer and common
disease, as well as catering for the original use-case for rare-disease.

Additions
~~~~~~~~~

Measurements
------------

Added a new :ref:`rstmeasurement` message for capturing quantitative, ordinal (e.g., absent/present), or categorical
measurements. This element is available in the :ref:`rstphenopacket` and :ref:`rstbiosample` top-level elements.

Medical actions
---------------

Time element
------------

Biosample
---------


Breaking Changes
~~~~~~~~~~~~~~~~

Gene and Variant contexts
-------------------------

Interpretation
--------------
