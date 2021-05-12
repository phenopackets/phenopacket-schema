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

.. csv-table::
   :header-rows: 1

   * - Field
     - Type
     - Multiplicity
     - Description
   * - id
     - string
     - 1..1
     - Official identifier of the gene. REQUIRED.
   * - alternate_ids
     - repeated string
     - 0..*
     - Alternative identifier(s) of the gene
   * - symbol
     - string
     - 1..1
     - Official gene symbol. REQUIRED.


    allele, :ref:`Allele`, 1..1, one of the Allele types described below. REQUIRED.
    zygosity, :ref:`rstontologyclass` , 0..1, See :ref:`zygosity` below. RECOMMENDED.


Examples
########


HGVS Variant
~~~~~~~~~~~~

Variants can be represented using the `HGVS nomenclature <https://varnomen.hgvs.org/>`_ as follows.

.. code-block:: yaml
    variationDescriptor:
      id: "clinvar:13294"
      expressions:
      - syntax: "hgvsc"
        value: "NM_000226.3:c.470T>G"
      allelicState:
        id: "GENO:0000135"
        label: "heterozygous"

.. _allele:

allele
######

The allele element is required and can be one and only one of ``HgvsAllele``, ``VcfAlelle``, ``SpdiAllele`` or ``IcsnAllele``.

.. _hgvs:

HgvsAllele
~~~~~~~~~~

This element is used to describe an allele according to the nomenclature of the
`Human Genome Variation Society (HGVS) <http://www.hgvs.org/>`_. For instance,
``NM_000226.3:c.470T>G`` indicates that a T at position 470 of the sequence represented by version 3 of
NM_000226 (which is the mRNA of the human keratin 9 gene `KRT9 <https://www.ncbi.nlm.nih.gov/nuccore/NM_000226.3>`_).

We recommend using a tool such as `VariantValidator <https://variantvalidator.org/>`_ or
`Mutalyzer <https://mutalyzer.nl/>`_ to validate the HGVS string. See the
`HGVS recommendations <http://varnomen.hgvs.org/recommendations/DNA/variant/alleles/>`_ for details about the
HGVS nomenclature.

**Data model**

.. csv-table::
   :header: Field, Type, Multiplicity, Description

    id, string, 0..1, An arbitrary identifier. RECOMMENDED.
    hgvs, string, 1..1, NM_000226.3:c.470T>G. REQUIRED.

**Example**

.. code-block:: yaml

    variant:
        hgvsAllele:
            hgvs: "NM_000226.3:c.470T>G"
        zygosity:
            id: "GENO:0000135"
            label: "heterozygous"

.. _vcf:

VcfAllele
~~~~~~~~~
This element is used to describe variants using the
`Variant Call Format <https://samtools.github.io/hts-specs/VCFv4.3.pdf>`_, which is in near universal use
for exome, genome, and other Next-Generation-Sequencing-based variant calling. It is an appropriate
option to use for variants reported according to their chromosomal location as derived from a VCF file.

In the Phenopacket format, it is expected that one ``VcfAllele`` message described a single allele (in contrast to
the actual VCF format that allows multiple alleles at the same position to be reported on the same line; to report
these in Phenopacket format, two ``variant`` messages would be required).

For structural variation the INFO field should contain the relevant information .
In general, the ``info`` field should only be used to report structural variants and it is not expected that the
Phenopacket will report the contents of the info field for single nucleotide and other small variants.

**Data model**

.. csv-table::
   :header: Field, Type, Multiplicity, Description

    genome_assembly, string, 1..1, The reference genome identifier e.g. GRCh38. REQUIRED.
    id, string, 0..1, An arbitrary identifier
    chr, string, 1..1, A chromosome identifier e.g. chr2 or 2. REQUIRED.
    pos, int32, 1..1, The 1-based genomic position e.g. 134327882. REQUIRED.
    ref, string, 1..1, The reference base(s). REQUIRED.
    alt, string, 1..1, The alternate base(s). REQUIRED.
    end, int32, 0..1, The `END` field for this allele, if present in the VCF record. RECOMMENDED.
    sv_type, string, 0..1, The `SV_TYPE` field for this allele, if present in the VCF record.
    sv_length, int32, 0..1, The `SV_LEN` field for this allele, if present in the VCF record.
    mate_id, string, 0..1, The `MATE_ID` field for this allele, if present in the VCF record.
    event_id, string, 0..1, The `EVENT_ID` field for this allele, if present in the VCF record.

**Example**

.. code-block:: yaml

    variant:
        vcfAllele:
            genomeAssembly: "GRCh38"
            id: "."
            chr: "2"
            pos: 134327882
            ref: "A"
            alt: "T"
        zygosity:
            id: "GENO:0000135"
            label: "heterozygous"


.. _spdi:

SpdiAllele
~~~~~~~~~~
This option can be used as an alternative to the VcfAllele, and describes variants using the
`Sequence Position Deletion Insertion (SPDI) notation <https://www.ncbi.nlm.nih.gov/variation/notation/>`_. We
recommend that users familiarize themselves with this relatively new notation, which
differs in important ways from other standards such as VCF and HGVS.

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

**Data model**

.. csv-table::
   :header: Field, Type, Multiplicity, Description

    id, string, 0..1, An arbitrary identifier. RECOMMENDED.
    seq_id, string, 1..1, Seq1. REQUIRED.
    position, int32, 1..1, 4. REQUIRED.
    deleted_sequence, 1..1, required, A. REQUIRED.
    inserted_sequence, 1..1, required, G. REQUIRED.

**Example**

.. code-block:: yaml
    variationDescriptor:
      id: "clinvar:13294"
      expressions:
      - syntax: "spdi"
        value: "NC_000010.10:123256214:T:G"
      allelicState:
        id: "GENO:0000135"
        label: "heterozygous"

.. _iscn:

IscnKaryotype
~~~~~~~~~~
This element can be used to describe cytogenetic anomalies according to the
`International System for Human Cytogenetic Nomenclature (ISCN) <https://www.ncbi.nlm.nih.gov/pubmed/?term=18428230>`_,
an international standard for human chromosome nomenclature, which includes band names, symbols and
abbreviated terms used in the description of human chromosome and chromosome abnormalities.

For example
del(6)(q23q24) describes a deletion from band q23 to q24 on chromosome 6.

**Data model**

.. csv-table::
   :header: Field, Type, Multiplicity, Description

   id, string, 0..1, An arbitrary identifier. RECOMMENDED.
   iscn, string, 1..1, t(8;9;11)(q12;p24;p12). REQUIRED.

**Example**

.. code-block:: yaml
    variationDescriptor:
      id: "id:A"
      expressions:
      - syntax: "iscn"
        value: "t(8;9;11)(q12;p24;p12)"

.. _zygosity:

zygosity
########

The zygosity of the variant as determined in all of the samples represented in this Phenopacket is represented using a list of
terms taken from the `Genotype Ontology (GENO) <https://www.ebi.ac.uk/ols/ontologies/geno>`_. For instance, if a variant
affects one of two alleles at a certain locus, we could record the zygosity using the term
`heterozygous (GENO:0000135) <https://www.ebi.ac.uk/ols/ontologies/geno/terms?iri=http%3A%2F%2Fpurl.obolibrary.org%2Fobo%2FGENO_0000135>`_.

