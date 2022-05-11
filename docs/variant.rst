.. _rstvariant:

###################
VariationDescriptor
###################

Variation Descriptors are part of the `VRSATILE framework <https://vrsatile.readthedocs.io>`_, a set of
conventions extending the GA4GH `Variation Representation Specification (VRS) <https://vrs.ga4gh.org>`_.
Descriptors allow for the complemetary use of human-readable labels, descriptions, alternate contexts,
and identifier cross-references alongside the precise computable representation of variation concepts
provided by VRS.

Consequently, many forms and formats of variation can be used in variation descriptors, including `HGVS
descriptions <https://varnomen.hgvs.org>`_, `VCF Records <https://samtools.github.io/hts-specs/VCFv4.3.pdf>`_,
and `SPDI alleles <https://www.ncbi.nlm.nih.gov/pmc/articles/PMC7523648/>`_. We recommend the use of VRS
Variation objects for representing variants when possible.

The Variation Descriptor should be used to describe candidate variants or diagnosed causative
variants. The ``VariationDescriptor`` element itself is an element of a :ref:`rstvariantinterpretation`.
If it is present, the Phenopacket standard has the following requirements.

A variation can refer to an external source, for example the ClinGen allele registry, ClinVar, dbSNP, dbVAR etc.
using the ``id`` field. It is RECOMMENDED to use a :ref:`rstcurie` identifier and corresponding :ref:`rstresource`.
When indicating multiple alternate ids for a variation, use the ``alternate_ids`` field.

Multiple alleles *in-cis* can be modeled as a VRS `Haplotype <https://https://vrs.ga4gh.org/en/latest/terms_and_model.html#haplotype>`_.

The zygosity of the variant as determined in all of the samples represented in this Phenopacket is represented
using a list of terms taken from the `Genotype Ontology (GENO) <https://www.ebi.ac.uk/ols/ontologies/geno>`_.
For instance, if a variant affects one of two alleles at a certain locus, we could record the zygosity using the
term `heterozygous (GENO:0000135) <https://www.ebi.ac.uk/ols/ontologies/geno/terms?iri=http%3A%2F%2Fpurl.obolibrary.org%2Fobo%2FGENO_0000135>`_.
This value is stored in the Variation Descriptor ``alleleic_state`` field.

Data model
##########

.. list-table::
   :widths: 25 25 25 50
   :header-rows: 1

   * - Field
     - Type
     - Multiplicity
     - Description
   * - id
     - string
     - 1..1
     - Descriptor ID; MUST be unique within document. REQUIRED.
   * - variation
     - :ref:`rstvrsvariation`
     - 0..1
     - The VRS ``Variation`` object
   * - label
     - string
     - 0..1
     - A primary label for the variation
   * - description
     - string
     - 0..1
     - A free-text description of the variation
   * - gene_context
     - :ref:`rstgene`
     - 0..1
     - A specific gene context that applies to this variant
   * - expressions
     - :ref:`rstexpression`
     - 0..*
     - HGVS, SPDI, and gnomAD-style strings should be represented as Expressions
   * - vcf_record
     - :ref:`rstvcfrecord`
     - 0..1
     - A VCF Record of the variant. This SHOULD be a single allele, the VCF genotype (GT) field should be represented in the allelic_state
   * - xrefs
     - string
     - 0..*
     - List of CURIEs representing associated concepts. Allele registry, ClinVar, or other related IDs should be included as xrefs
   * - alternate_labels
     - string
     - 0..*
     - Common aliases for a variant, e.g. EGFR vIII, are alternate labels
   * - extensions
     - :ref:`rstextension`
     - 0..*
     - List of resource-specific Extensions needed to describe the variation
   * - molecule_context
     - :ref:`rstmoleculecontext`
     - 1..1
     - The molecular context of the vrs variation.
   * - structural_type
     - :ref:`rstontologyclass`
     - 0..1
     - The structural variant type associated with this variant, such as a substitution, deletion, or fusion. We RECOMMEND using a descendent term of SO:0001537.
   * - vrs_ref_allele_seq
     - string
     - 0..1
     - A Sequence corresponding to a “ref allele”, describing the sequence expected at a SequenceLocation reference.
   * - allelic_state
     - :ref:`rstontologyclass`
     - 0..1
     - See :ref:`allelic_state` below. RECOMMENDED.


.. _rstvrsvariation:

Variation
~~~~~~~~~

`VRS <https://vrs.ga4gh.org/en/stable/>`_ is a GA4GH standard which provides a computable representation of variation,
be it a genomic, transcript or protein variation. VRS also provides mechanisms for representing haplotypes and systemic
variation such as Copy Number Variants (CNVs).

.. _rstvcfrecord:

VcfRecord
~~~~~~~~~

This element is used to describe variants using the
`Variant Call Format <https://samtools.github.io/hts-specs/VCFv4.3.pdf>`_, which is in near universal use
for exome, genome, and other Next-Generation-Sequencing-based variant calling. It is an appropriate
option to use for variants reported according to their chromosomal location as derived from a VCF file.

In the Phenopacket format, it is expected that one ``VcfRecord`` message described a single allele (in contrast to
the actual VCF format that allows multiple alleles at the same position to be reported on the same line; to report
these in Phenopacket format, two ``VariantDescriptor`` messages would be required). In general the ``VcfRecord`` should
be used only for the purposes of reporting variants of specific interest, such as in the :ref:`rstvariantinterpretation`,
for cases requiring larger numbers of variants in VCF format, the :ref:`rstfile` should be used.

For structural variation the INFO field should contain the relevant information .
In general, the ``info`` field should only be used to report structural variants and it is not expected that the
Phenopacket will report the contents of the info field for single nucleotide and other small variants.

.. list-table::
   :widths: 25 25 25 50
   :header-rows: 1

   * - Field
     - Type
     - Multiplicity
     - Description
   * - genome_assembly
     - string
     - 1..1
     - Identifier for the genome assembly used to call the allele. REQUIRED.
   * - chrom
     - string
     - 1..1
     - Chromosome or contig identifier. REQUIRED.
   * - pos
     - int
     - 1..1
     - The reference position, with the 1st base having position 1. REQUIRED.
   * - id
     - string
     - 0..1
     - Identifier: Semicolon-separated list of unique identifiers where available. If this is a dbSNP variant thers  number(s)  should  be  used.
   * - ref
     - string
     - 1..1
     - Reference base. REQUIRED.
   * - alt
     - string
     - 1..1
     - Alternate base. REQUIRED.
   * - qual
     - string
     - 0..1
     - Quality: Phred-scaled quality score for the assertion made in ALT.
   * - filter
     - string
     - 0..1
     - Filter status: PASS if this position has passed all filters.
   * - info
     - string
     - 0..1
     - Additional information: Semicolon-separated series of additional information fields


.. _rstextension:

Extension
~~~~~~~~~

The Extension class provides a means to extend descriptions with other attributes unique to a content provider.
These extensions are not expected to be natively understood by all users, but may be used for pre-negotiated exchange of message attributes when needed.

.. list-table::
   :widths: 25 25 25 50
   :header-rows: 1

   * - Field
     - Type
     - Multiplicity
     - Description
   * - name
     - string
     - 1..1
     - A name for the Extension. REQUIRED.
   * - value
     - string
     - 1..1
     - A string representation of the user-defined object. REQUIRED.


.. _rstexpression:

Expression
~~~~~~~~~~

The Expression class is designed to enable descriptions based on a specified nomenclature or syntax for representing an
object. Common examples of expressions for the description of molecular variation include the HGVS and ISCN nomenclatures.

We RECOMMEND the use one of the following values in the ``syntax`` field: ``hgvs``, ``iscn``, ``spdi``

.. list-table::
   :widths: 25 25 25 50
   :header-rows: 1

   * - Field
     - Type
     - Multiplicity
     - Description
   * - syntax
     - string
     - 1..1
     - A name for the expression syntax. REQUIRED.
   * - value
     - string
     - 1..1
     - The concept expression as a string. REQUIRED.
   * - version
     - string
     - 0..1
     - An optional version of the expression syntax.


.. _rstmoleculecontext:

MoleculeContext
~~~~~~~~~~~~~~~

The molecular context of the variant. Default is ``unspecified_molecule_context``.

.. list-table::
   :widths: 25 25 25 50
   :header-rows: 1

   * - Name
     - Ordinal
     - Description
   * - unspecified_molecule_context
     - 0
     - Default
   * - genomic
     - 1
     - :ref:`rstvariation` is a genomic variation
   * - transcript
     - 2
     - :ref:`rstvariation` is a transcript variation
   * - protein
     - 3
     - :ref:`rstvariation` is a protein variation

Examples
########

In these examples we will show how the ClinVar allele `13294 <https://www.ncbi.nlm.nih.gov/clinvar/variation/13294/>`_
can be represented using a ``VariationDescriptor``. While it is possible to combine all these in a single message, we
have separated them for clarity.

VRS
~~~
Here we're representing the genomic variation using VRS, however VRS is capable of representing the variation in genomic,
transcript or protein coordinates.

**Example**

.. code-block:: yaml

    variationDescriptor:
      id: "clinvar:13294"
      variation:
        allele:
          sequenceLocation:
            sequenceId: "NC_000010.11"
            sequenceInterval:
              startNumber:
                value: "121496700"
              endNumber:
                value: "121496701"
          literalSequenceExpression:
            sequence: "G"
      moleculeContext: "genomic"
      vrsRefAlleleSeq: "T"
      allelicState:
        id: "GENO:0000135"
        label: "heterozygous"


HGVS
~~~~

Variants can be represented using the `HGVS nomenclature <https://varnomen.hgvs.org/>`_ as follows.

For example, the `Human Genome Variation Society (HGVS) <http://www.hgvs.org/>`_ expression
``NM_000226.3:c.470T>G`` indicates that a T at position 470 of the sequence represented by version 3 of
NM_000226 (which is the mRNA of the human keratin 9 gene `KRT9 <https://www.ncbi.nlm.nih.gov/nuccore/NM_000226.3>`_).

We recommend using a tool such as `VariantValidator <https://variantvalidator.org/>`_ or
`Mutalyzer <https://mutalyzer.nl/>`_ to validate the HGVS string. See the
`HGVS recommendations <http://varnomen.hgvs.org/recommendations/DNA/variant/alleles/>`_ for details about the
HGVS nomenclature.

**Example**

.. code-block:: yaml

    variationDescriptor:
      id: "clinvar:13294"
      expressions:
      - syntax: "hgvs"
        value: "NM_000226.3:c.470T>G"
      allelicState:
        id: "GENO:0000135"
        label: "heterozygous"

VCF
~~~

**Example**

.. code-block:: yaml

    variationDescriptor:
        id: "clinvar:13294"
        vcfRecord:
            genomeAssembly: "GRCh38"
            chrom: "10"
            pos: 121496701
            id: "rs121918506"
            ref: "T"
            alt: "G"
            qual: "."
            filter: "."
            info: "."
        zygosity:
            id: "GENO:0000135"
            label: "heterozygous"


.. _spdi:

SPDI
~~~~
The `Sequence Position Deletion Insertion (SPDI) notation <https://www.ncbi.nlm.nih.gov/variation/notation/>`_ is a
relatively new notation which uses the same normalisation protocol as `VRS <https://vrs.ga4gh.org/en/stable/>`_. We
recommend that users familiarize themselves with this relatively new notation, which differs in important ways from other
standards such as VCF and HGVS.

Tools for interconversion between SPDI, HGVS and VCF exist at the `NCBI <https://api.ncbi.nlm.nih.gov/variation/v0/>`_.

SPDI stands for

1. S = SequenceId
2. P = Position , a 0-based coordinate for where the Deleted Sequence starts
3. D = DeletedSequence , sequence for the deletion, can be empty
4. I = InsertedSequence , sequence for the insertion, can be empty

For instance, ``Seq1:4:A:G`` refers to a single nucleotide variant at the fifth nucleotide (
nucleotide 4 according to zero-based numbering) from an ``A`` to a ``G``. See the
`SPDI webpage <https://www.ncbi.nlm.nih.gov/variation/notation/>`_ for more
examples.

The SPDI notation represents variation as deletion of a sequence (D) at a given position (P) in reference sequence (S)
followed by insertion of a replacement sequence (I) at that same position. Position 0 indicates a deletion that
starts immediately before the first nucleotide, and position 1 represents a deletion interval that starts between the
first and second residues, and so on. Either the deleted or the inserted interval can be empty, resulting in a pure
insertion or deletion.

Note that the deleted and inserted sequences in SPDI are all written on the positive strand for two-stranded molecules.

**Example**

.. code-block:: yaml

    variationDescriptor:
      id: "clinvar:13294"
      expressions:
      - syntax: "spdi"
        value: "NC_000010.11:121496700:T:G"
      allelicState:
        id: "GENO:0000135"
        label: "heterozygous"

.. _iscn:

ISCN
~~~~
The `International System for Human Cytogenetic Nomenclature (ISCN) <https://www.ncbi.nlm.nih.gov/pubmed/?term=18428230>`_,
an international standard for human chromosome nomenclature, which includes band names, symbols and
abbreviated terms used in the description of human chromosome and chromosome abnormalities.

For example
del(6)(q23q24) describes a deletion from band q23 to q24 on chromosome 6.

**Example**

.. code-block:: yaml

    variationDescriptor:
      id: "id:A"
      expressions:
      - syntax: "iscn"
        value: "t(8;9;11)(q12;p24;p12)"

.. _zygosity:

allelic_state
#############

The zygosity of the variant as determined in all of the samples represented in this Phenopacket is represented using a list of
terms taken from the `Genotype Ontology (GENO) <https://www.ebi.ac.uk/ols/ontologies/geno>`_. For instance, if a variant
affects one of two alleles at a certain locus, we could record the zygosity using the term
`heterozygous (GENO:0000135) <https://www.ebi.ac.uk/ols/ontologies/geno/terms?iri=http%3A%2F%2Fpurl.obolibrary.org%2Fobo%2FGENO_0000135>`_.

