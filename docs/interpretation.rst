.. _rstinterpretation:

##############
Interpretation
##############

This message intends to represent the interpretation of a genomic analysis, such as the report from
a diagnostic laboratory.

Data model
##########

 .. list-table::
    :widths: 25 25 25 75
    :header-rows: 1

    * - Field
      - Type
      - Multiplicity
      - Description
    * - id
      - string
      - 1..1
      - Arbitrary identifier. REQUIRED.
    * - progress_status
      - :ref:`rstprogressstatus`
      - 1..1
      - The current resolution status. REQUIRED.
    * - diagnosis
      - :ref:`rstdiagnosis`
      - 0..1
      - The diagnosis, if made.
    * - summary
      - string
      - 0..1
      - Additional data about this interpretation



Example
#######

In this example, a case with id ``CONSORTIUM:0000123456`` is reported to be
solved. The diagnosis is Miller syndrome, and the supporting interpretation
states the involved gene. For privacy reasons, the variant was not reported, but the
intended meaning is that a relevant variant in the named gene was found.


.. code-block:: yaml

  interpretation:
    id: "CONSORTIUM:0000123456"
    progressStatus: "SOLVED"
    diagnosis:
      disease:
        id: "OMIM:263750"
        label: "Miller syndrome"
      genomicInterpretations:
      - interpretationStatus: "CONTRIBUTORY"
        gene:
          valueId: "HGNC:2867"
          symbol: "DHODH"



Explanations
############

id
~~
The id has the same interpretation as the **id** element in the :ref:`rstindividual` element.



.. _rstprogressstatus:

ProgressStatus
~~~~~~~~~~~~~~

The interpretation has a **ProgressStatus** that refers to the status of the attempted diagnosis.


Implementation note - this is an enumerated type, therefore the values represented below are the only legal values. The
value of this type SHALL NOT be null, instead it SHALL use the 0 (zero) ordinal element as the default value, should none
be specified.

.. csv-table::
   :header: Name, Ordinal, Description

    UNKNOWN_PROGRESS, 0, No information is available about the diagnosis
    IN_PROGRESS, 1, No diagnosis has been found to date but additional differential diagnostic work is in progress.
    COMPLETED, 2, The work on the interpretation is complete.
    SOLVED, 3, The interpretation is complete and also  considered to be a definitive diagnosis
    UNSOLVED, 4, The interpretation is complete but no definitive diagnosis was found



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
      - Multiplicity
      - Description
    * - disease
      - :ref:`rstontologyclass`
      - 1..1
      - The diagnosed condition. REQUIRED.
    * - genomic_interpretations
      - :ref:`rstgenomicinterpretation`
      - 0..*
      - The genomic elements assessed as being responsible for the disease or empty

Examples of the intended usage of the Interpretation element
############################################################

Candidate genes
~~~~~~~~~~~~~~~

Research consortia may exchange information about candidate genes in which an undisclosed
variant was found that was assessed to be possibly related to a disease or phenotype but
for which insufficient evidence is available to be certain. The intention is often to find
other researchers with similar cases in order to subsequently share detailed information in
a collaborative project.

In this case, the gene should be marked as ``CANDIDATE``. Here is an example of an interpretation
with the hypothetical gene YFG42.



.. code-block:: yaml

  interpretation:
    id: "CONSORTIUM:0000123456"
    progressStatus: "SOLVED"
    diagnosis:
      disease:
        id: "OMIM:263750"
        label: "Miller syndrome"
      genomicInterpretations:
      - interpretationStatus: "CONTRIBUTORY"
        gene:
          valueId: "HGNC:2867"
          symbol: "DHODH"


Diagnostic finding in an autosomal dominant disease
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

The ``Interpretation`` element might be used in this way to report a laboratory finding in a diagnostic
setting or in a published case report. The following example shows how the variant
`NM_000138.4(FBN1):c.6751T>A (p.Cys2251Ser) <https://www.ncbi.nlm.nih.gov/clinvar/variation/519780/>`_
would be reported.

.. code-block:: yaml

    interpretation:
      id: "Arbitrary interpretation id"
      progressStatus: "SOLVED"
      diagnosis:
        disease:
          id: "OMIM:154700"
          label: "Marfan syndrome"
        genomicInterpretations:
        - subjectOrBiosampleId: "subject 1"
          interpretationStatus: "CONTRIBUTORY"
          variantInterpretation:
            acmgPathogenicityClassification: "PATHOGENIC"
            variationDescriptor:
              expressions:
              - syntax: "hgvs"
                value: "NM_000138.4(FBN1):c.6751T>A"
              allelicState:
                id: "GENO:0000135"
                label: "heterozygous"

The ``subjectOrBiosampleId`` is set to the id of the :ref:`rstindividual` of the enclosing phenopacket
to indicate that the genomic interpretation refers to a germline variant.

Diagnostic finding in an autosomal recessive disease
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

For homozygous variants, the ``zygosity`` would be set to homozygous. The following example
shows a finding of compound heterozygous variants.


.. code-block:: yaml

    interpretation:
      id: "Arbitrary interpretation id"
      progressStatus: "SOLVED"
      diagnosis:
        disease:
          id: "OMIM: 219700"
          label: "Cystic fibrosis"
        genomicInterpretations:
        - subjectOrBiosampleId: "subject 1"
          interpretationStatus: "CONTRIBUTORY"
          variantInterpretation:
            acmgPathogenicityClassification: "PATHOGENIC"
            variationDescriptor:
              expressions:
              - syntax: "hgvs"
                value: "NM_000492.3(CFTR):c.1477C>T (p.Gln493Ter)"
              allelicState:
                id: "GENO:0000135"
                label: "heterozygous"
        - subjectOrBiosampleId: "subject 1"
          interpretationStatus: "CONTRIBUTORY"
          variantInterpretation:
            acmgPathogenicityClassification: "PATHOGENIC"
            variationDescriptor:
              expressions:
              - syntax: "hgvs"
                value: "NM_000492.3(CFTR):c.1521_1523delCTT (p.Phe508delPhe)"
              allelicState:
                id: "GENO:0000135"
                label: "heterozygous"

The ``subjectOrBiosampleId`` is set to the id of the :ref:`rstindividual` of the enclosing phenopacket
to indicate that the genomic interpretation refers to a germline variant.

Diagnostic finding in a cancer
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Cancer cases are not generally solved by genomic analysis. Instead, the intention is often to
identify actionable variants that represent potential indications for targeted therapy. In
this example, a BRAF variant is interpreted as being actionable in this sense.

.. code-block:: yaml

 interpretation:
  id: "Arbitrary interpretation id"
  progressStatus: "COMPLETED"
  diagnosis:
    disease:
      id: "NCIT:C3224"
      label: "Melanoma"
    genomicInterpretations:
    - subjectOrBiosampleId: "biosample id"
      interpretationStatus: "CONTRIBUTORY"
      variantInterpretation:
        acmgPathogenicityClassification: "PATHOGENIC"
        therapeuticActionability: "ACTIONABLE"
        variationDescriptor:
          expressions:
          - syntax: "hgvs"
            value: "NM_001374258.1(BRAF):c.1919T>A (p.Val640Glu)"
          allelicState:
            id: "GENO:0000135"
            label: "heterozygous"

The ``subjectOrBiosampleId`` is set to the id of the :ref:`rstbiosample`
that is contained in the enclosing phenopacket, representing a biopsy from
a melanoma sample taken from the subject of the phenopacket.
