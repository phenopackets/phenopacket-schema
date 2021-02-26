.. _rstinterpretation:

Interpretation
==============
This message intends to represent the interpretation of a genomic analysis, such as the report from
a diagnostic laboratory.

The Interpretation message contains a Family or Phenopacket. Interpretation interprets the Phenopacket based on its component elements, whereas the Phenopacket simply states what was observed. They are both composed of Phenopacket building block elements, but the additional elements described in this section are only used to compose the Interpretation.

**Data model**

 .. list-table::
    :widths: 25 50 50 50
    :header-rows: 1

    * - Field
      - Type
      - Status
      - Description
    * - id
      - string
      - required
      - Arbitrary identifier
    * - resolution_status
      - :ref:`rstresolutionstatus`
      - required
      - The current status of work on the case
    * - phenopacket_or_family
      - :ref:`rstphenopacket` or :ref:`rstfamily` element
      - required
      - The subject of this interpretation
    * - diagnosis
      - :ref:`rstdiagnosis` (list)
      - optional
      - One or more diagnoses, if made
    * - meta_data
      - See :ref:`rstmetadata`
      - required
      - Metadata about this interpretation

**Example**

.. code-block:: json

  {
  "id": "SOLVERD:0000123456",
  "resolutionStatus": "SOLVED",
  "phenopacket": {
    "id": "SOLVERD:0000234567",
    "subject": {
      "id": "SOLVERD:0000345678",
      "dateOfBirth": "1998-01-01T00:00:00Z",
      "sex": "MALE"
    },
    "phenotypicFeatures": [{
      "type": {
        "id": "HP:0001159",
        "label": "Syndactyly"
      },
      "classOfOnset": {
        "id": "HP:0003577",
        "label": "Congenital onset"
      }
    }, {
      "type": {
        "id": "HP:0002090",
        "label": "Pneumonia"
      },
      "classOfOnset": {
        "id": "HP:0011463",
        "label": "Childhood onset"
      }
    }, {
      "type": {
        "id": "HP:0000028",
        "label": "Cryptorchidism"
      },
      "classOfOnset": {
        "id": "HP:0003577",
        "label": "Congenital onset"
      }
    }, {
      "type": {
        "id": "HP:0011109",
        "label": "Chronic sinusitis"
      },
      "severity": {
        "id": "HP:0012828",
        "label": "Severe"
      },
      "classOfOnset": {
        "id": "HP:0003581",
        "label": "Adult onset"
      }
    }],
    "variants": [{
      "hgvsAllele": {
        "hgvs": "NM_001361.4:c.403C\u003eT"
      },
      "zygosity": {
        "id": "GENO:0000135",
        "label": "heterozygous"
      }
    }, {
      "hgvsAllele": {
        "hgvs": "NM_001361.4:c.454G\u003eA"
      },
      "zygosity": {
        "id": "GENO:0000135",
        "label": "heterozygous"
      }
    }, {
      "hgvsAllele": {
        "hgvs": "NM_001369.2:c.12599dupA"
      },
      "zygosity": {
        "id": "GENO:0000136",
        "label": "homozygous"
      }
    }]
  },
  "diagnosis": [{
    "disease": {
      "term": {
        "id": "OMIM:263750",
        "label": "Miller syndrome"
      }
    },
    "genomicInterpretations": [{
      "status": "CAUSATIVE",
      "gene": {
        "id": "HGNC:2867",
        "symbol": "DHODH"
      }
    }]
  }]
 }

id
~~
The id has the same interpretation as the **id** element in the :ref:`rstindividual` element.

.. _rstresolutionstatus:

Resolution_status
~~~~~~~~~~~~~~~~~

The interpretation has a **ResolutionStatus** that refers to the status of the attempted diagnosis.

**Data model**

Implementation note - this is an enumerated type, therefore the values represented below are the only legal values. The
value of this type SHALL NOT be null, instead it SHALL use the 0 (zero) ordinal element as the default value, should none
be specified.

.. csv-table::
   :header: Name, Ordinal, Description

    UNKNOWN, 0, No information is available about the diagnosis
    SOLVED, 1, The interpretation is considered to be a definitive diagnosis
    UNSOLVED, 2, No definitive diagnosis was found
    IN_PROGRESS, 3, No diagnosis has been found to date but additional differential diagnostic work is in progress.

**Example**

.. code-block:: json

 {
    "resolutionStatus": "SOLVED"
 }


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

Diagnosis
~~~~~~~~~

The diagnosis element is meant to refer to the disease that is inferred to be present in the individual
or family being analyzed. The diagnosis can be made by  means of an analysis of the phenotypic or the genomic findings or both.
The element is optional because if the **resolution_status** is **UNSOLVED** then there is no diagnosis.

**Data elements**

 .. list-table::
    :widths: 25 50 50 50
    :header-rows: 1

    * - Field
      - Type
      - Status
      - Description
    * - disease
      - :ref:`rstdisease`
      - required
      - The diagnosed condition
    * - genomic_interpretations
      - :ref:`rstgenomicinterpretation` (list)
      - optional
      - The genomic elements assessed as being responsible for the disease or empty

**Example**

.. code-block:: json

 {
    "disease": {
      "term": {
        "id": "OMIM:263750",
        "label": "Miller syndrome"
      }
    },
    "genomicInterpretations": [{
      "status": "CAUSATIVE",
      "gene": {
        "id": "HGNC:2867",
        "symbol": "DHODH"
      }
    }]
 }

The *genomic_interpretations* should be used if the genetic findings were used to help make the diagnosis, but can be
omitted if genetic/genomic analysis was not contributory or were not performed.

.. _rstgenomicinterpretation:

GenomicInterpretation
~~~~~~~~~~~~~~~~~~~~~
A statement about the contribution of a genomic element towards the observed phenotype. Note that this does not intend
to encode any knowledge or results of specific computations.

**Data model**
 .. list-table::
    :widths: 25 50 50 50
    :header-rows: 1

    * - Field
      - Type
      - Status
      - Example
    * - status
      - :ref:`rstgenomicinterpretationstatus`
      - required
      - How the `call` of this :ref:`rstgenomicinterpretation` was interpreted
    * - call
      - :ref:`rstgene` or :ref:`rstvariant`
      - required
      - The gene or variant contributing to the diagnosis

**Example**

.. code-block:: json

    {
      "status": "CAUSATIVE",
      "gene": {
        "id": "HGNC:2867",
        "symbol": "DHODH"
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


.. _rstgenomicinterpretationstatus:

GenomicInterpretation Status
~~~~~~~~~~~~~~~~~~~~~~~~~~~~

An enumeration describing the status of a :ref:`rstgenomicinterpretation`

**Data model**

Implementation note - this is an enumerated type, therefore the values represented below are the only legal values. The
value of this type SHALL NOT be null, instead it SHALL use the 0 (zero) ordinal element as the default value, should none
be specified.

.. csv-table::
   :header: Name, Ordinal, Description

    UNKNOWN, 0,  It is not known how this genomic element contributes to the diagnosis
    REJECTED, 1, The genomic element has been investigated and ruled-out
    CANDIDATE, 2, The genomic element is under investigation
    CAUSATIVE, 3, The genomic element has been judged to be contributing to the diagnosis

**Example**

.. code-block:: json

    {
      "status": "CAUSATIVE"
    }
