==================
PhenoPacket basics
==================

The phenopacket schema is defined in two files that are maintained in the `phenopacket-schema GitHub repository <https://github.com/phenopackets/phenopacket-schema>`_. We will explain the ``phenopackets.proto`` here; this file contains the top-level concepts that make up a phenopacket, using more detailed definitions from a second file called ``base.proto``. Both files are available in the **src/main/proto/org/phenopackets/schema/v1** directory, with base.proto located in the core subdirectory.

~~~~~~~~~~~~~~~~~~
phenopackets.proto
~~~~~~~~~~~~~~~~~~

This is the main definition file

 .. code::

  syntax = "proto3";
  package org.phenopackets.schema.v1;
  
  import "org/phenopackets/schema/v1/core/base.proto";
  option java_multiple_files = true;

  message PhenoPacket {
    string id = 1;
    org.phenopackets.schema.v1.core.Individual subject = 2;
    repeated org.phenopackets.schema.v1.core.Individual individuals = 3;
    org.phenopackets.schema.v1.core.Pedigree pedigree = 4;
    repeated org.phenopackets.schema.v1.core.Biosample biosamples = 5;
    repeated org.phenopackets.schema.v1.core.Gene genes = 6;
    repeated org.phenopackets.schema.v1.core.Variant variants = 7;
    repeated org.phenopackets.schema.v1.core.Disease diseases = 8;
    repeated org.phenopackets.schema.v1.core.HtsFile hts_files = 9;
    org.phenopackets.schema.v1.core.MetaData metaData = 10;
  }

The header specifies that we are using protobuf version 3 and defines the package name. It imports the ``base.proto`` file mentioned above. The
command ``option java_multiple_files = true;`` puts each top-level message type from the .proto file into an independent .java file. The message then goes on to define the main components of the PhenoPacket.

~~
id
~~

The id is an identifier specific for this phenopacket.
TODO recommendations for how to use this field.

~~~~~~~
subject
~~~~~~~

This is typically the individual human (or another organism) that the PhenoPacket is describing. In many cases, the individual will
be a patient or proband of the study. This field maps to the FHIR `Patient <https://www.hl7.org/fhir/patient.html>`_ element. The PhenoPacket :ref:`Individual` element contains elements that describe attributes such as biological sex, age, or date of birth.

~~~~~~~~~~~
individuals
~~~~~~~~~~~
This field can contain a list of other individuals. The PhenoPacket standard expects
that these individuals are related to the proband in some way, e.g., mother, father, siblings or members of a cohort.
TODO see https://github.com/phenopackets/phenopacket-schema/issues/15

~~~~~~~~
pedigree
~~~~~~~~
The pedigree object contains information that is comparable to a PED file. It
defines the relations between the patient and their relatives.
TODO detail page.


~~~~~~~~~~
biosamples
~~~~~~~~~~

This field describes samples that have been derived from the patient who is the object of the PhenoPacket.
or a collection of biosamples in isolation. TODO see  https://github.com/phenopackets/phenopacket-schema/issues/16

~~~~~
genes
~~~~~
This field defines a list of one or more genes that can be used to denote a collection of 
candidate genes or causative genes. The resources using these fields should define what this
represents in their context.

~~~~~~~~
variants
~~~~~~~~
This field defines a list of genetic variants and should be used for either describing candidate
variants or diagnosed causative variants. The resources using these fields should define what this
represents in their context.
TODO see https://github.com/phenopackets/phenopacket-schema/issues/17

~~~~~~~~
diseases
~~~~~~~~

This field should be used to describe a list of  diagnosed or suspected conditions. The
resources using these fields should define what this represents in their context. If this
field is used to describe an individual with a rare disease, then the intended semantics is
that the phenopacket is analogous to a case report that presents the phenotypes (and in many
cases the variants) observed in an individual with the named disease(s).

~~~~~~~~~
hts_files
~~~~~~~~~
This element contains a list of pointers to the relevant HTS file(s) for the patient. Each element
describes what type of file is meant (e.g., BAM file), which genome assembly was used for mapping,
as well as a map of samples and individuals represented in that file. It also contains a
File element which optionally refers to a file on a given file system or can be a URI that
refers to a resource on the web.

~~~~~~~~
metaData
~~~~~~~~
This element contains structured definitions of the resources and ontologies used within the phenopacket.
It is expected that every valid PhenoPacket contains a metaData element.

TODO link the above elements to the corresponding pages.

