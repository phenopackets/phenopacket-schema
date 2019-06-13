.. _rstinterpretation:

Interpretation
==============
This message intends to represent the interpretation of a genomic analysis, such as the report from
a diagnostic laboratory.


.. code-block:: proto

  message Interpretation {
      enum ResolutionStatus {
          UNKNOWN = 0;
          SOLVED = 1;
          UNSOLVED = 3;
          IN_PROGRESS = 4;
      }
      string id = 1;
      ResolutionStatus resolution_status = 2;
      oneof phenopacket_or_family {
        org.phenopackets.schema.v1.Phenopacket phenopacket = 3;
        org.phenopackets.schema.v1.Family family = 4;
      }
      repeated Diagnosis diagnosis = 5;
      org.phenopackets.schema.v1.core.MetaData meta_data = 6;
  }

id
~~
The id has the same interpretation as the **id** element in the :ref:`rstindividual` element.

resolution_status
~~~~~~~~~~~~~~~~~

The interpretation has a **ResolutionStatus** that refers to the status of the attempted diagnosis.

* UNKNOWN No information is available about the diagnosis
* SOLVED  The interpretation is considered to be a definitive diagnosis
* UNSOLVED No definitive diagnosis was found
* IN_PROGRESS No diagnosis has been found to date, but additional differential diagnostic work is in progress.


phenopacket_or_family
~~~~~~~~~~~~~~~~~~~~~

This element refers to the :ref:`rstphenopacket` or :ref:`rstfamily` element for whom the interpretation is being made.

diagnosis
~~~~~~~~~
This refers to the diagnosis (or if applicable to the diagnoses) made. See :ref:`rstdiagnosis`, below.

meta_data
~~~~~~~~~
This element contains structured definitions of the resources and ontologies used within the phenopacket.
See :ref:`rstmetadata` for further information.


.. _rstdiagnosis:



 .. list-table:: Phenopacket requirements for the ``Interpretation`` element
    :widths: 25 50 50
    :header-rows: 1

    * - Field
      - Example
      - Status
    * - id
      - Arbitrary identifier
      - required
    * - resolution_status
      - SOLVED
      - required
    * - phenopacket_or_family
      - :ref:`rstphenopacket` or :ref:`rstfamily` element
      - required
    * - diagnosis
      - see below
      - optional
    * - meta_data
      - See :ref:`rstmetadata`
      - required






.. _rstdiagnosis:

Diagnosis
~~~~~~~~~

The diagnosis element is meant to refer to the disease that is infered to be present in the individual
or family being analyzed. The diagnosis can be made by  means of an analysis of the phenotypic or the genomic findings or both.
The element is optional because if the **resolution_status** is **UNSOLVED** then there is not diagnosis.



.. code-block:: proto

  message Diagnosis {
    org.phenopackets.schema.v1.core.Disease disease = 1;
    repeated GenomicInterpretation genomic_interpretations = 2;
  }



 .. list-table:: Phenopacket requirements for the ``Diagnosis`` element
    :widths: 25 50 50
    :header-rows: 1

    * - Field
      - Example
      - Status
    * - disease
      - :ref:`rstdisease` element
      - required
    * - genomic_interpretations
      - see below
      - optional

The *genomic_interpretations* should be used if the genetic findings were used to help make the diagnosis, but can be
omitted if genetic/genomic analysis was not contributory or were not performed.


GenomicInterpretation
~~~~~~~~~~~~~~~~~~~~~
A statement about the contribution of a genomic element towards the observed phenotype. Note that this does not intend
to encode any knowledge or results of specific computations.


.. code-block:: proto

  message GenomicInterpretation {
    enum Status {
      UNKNOWN = 0;
      REJECTED = 1;
      CANDIDATE = 3;
      CAUSATIVE = 4;
    }
    Status status = 1;
    oneof call {
      org.phenopackets.schema.v1.core.Gene gene = 2;
      org.phenopackets.schema.v1.core.Variant variant = 3;
    }
  }

A gene can be listed as **CAUSATIVE**. Alternatively, or additionally, a variant may be listed as
**CAUSATIVE**. Note that the intended semantics is different from the
`ACMG interpretation of sequence variants <https://www.ncbi.nlm.nih.gov/pubmed/27993330>`_, which
classifies variants with respect to their pathogenicity. The Interpretation element classifies
variants as being responsible or not for the phenotypic and disease observations in the proband.
A variant can be pathogenic according to the ACMG guidelines but not be causative for the particular
disease being investigated (for instance, a heterozygous variant associated with an autosomal recessive disease
may be found in a proband with causative variants in another gene).




 .. list-table:: Phenopacket requirements for the ``GenomicInterpretation`` element
    :widths: 25 50 50
    :header-rows: 1

    * - Field
      - Example
      - Status
    * - status
      - CAUSATIVE
      - required
    * - call
      - A :ref:`rstgene` or :ref:`rstvariant` element
      - required