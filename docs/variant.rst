.. _rstvariant:

=======
Variant
=======

This element should be used to describe candidate variants or diagnosed causative
variants. There is currently no standard variant nomenclature that can represent all kinds
of genetic variation that is relevant to human medicine, science, and model organisms. Therefore,
we represent variants using the keyword ``oneof``, which is used in protobuf for an item
with many  optional fields where at most one field will be set at the same time. Variant messages
contain an allele and the zygosity of the allele.

Alleles can be listed using HGVS, VCF, SPDI or ISCN notation. The phenopacket schema will
implement the `GA4GH Variation Representation Specification  <https://github.com/ga4gh/vr-spec>`_ once that
is mature. The VR-Spec will be the recommended option in some settings.


- See: https://vr-spec.readthedocs.io/en/1.0rc/
- See: https://github.com/ga4gh-beacon/specification/blob/master/beacon.yaml

The ``Variant`` element itself is an optional element of a ``Phenopacket``  or ``Biosample``. If it is present,
the Phenopacket standard has the following requirements.

Alleles can refer to external sources, for example the ClinGen allele registry, ClinVar, dbSNP, dbVAR etc. using the ``id``
field. It is RECOMMENDED to use a :ref:`rstcurie` identifier and corresponding :ref:`rstresource`.

*n.b.* phase information for alleles are not represented in this model.

**Data model**

.. csv-table::
   :header: Field, Type, Status, Description

    allele, :ref:`Allele`, required, one of the Allele types described below
    zygosity, :ref:`rstontologyclass` , recommended, See :ref:`zygosity` below


**Example**

.. code-block:: json

    {
        "spdiAllele": {
          "id": "clinvar:13294"
          "seqId": "NC_000010.10",
          "position": 123256214,
          "deletedSequence": "T",
          "insertedSequence": "G"
        },
        "zygosity": {
          "id": "GENO:0000135",
          "label": "heterozygous"
        }
    }

.. _zygosity:

zygosity
~~~~~~~~
The zygosity of the variant as determined in all of the samples represented in this Phenopacket is represented using a list of
terms taken from the `Genotype Ontology (GENO) <https://www.ebi.ac.uk/ols/ontologies/geno>`_. For instance, if a variant
affects one of two alleles at a certain locus, we could record the zygosity using the term
`heterozygous (GENO:0000135) <https://www.ebi.ac.uk/ols/ontologies/geno/terms?iri=http%3A%2F%2Fpurl.obolibrary.org%2Fobo%2FGENO_0000135>`_.


.. _allele:

allele
~~~~~~
The allele element is required and can be one and only one of ``HgvsAllele``, ``VcfAlelle``, ``SpdiAllele`` or ``IcsnAllele``.

.. _hgvs:

HgvsAllele
~~~~~~~~~~
This element is used to describe an allele according to the nomenclature of the
`Human Geneome Variation Society (HGVS) <http://www.hgvs.org/>`_. For instance,
``NM_000226.3:c.470T>G`` indicates that a T at position 470 of the sequence represented by version 3 of
NM_000226 (which is the mRNA of the human keratin 9 gene `KRT9 <https://www.ncbi.nlm.nih.gov/nuccore/NM_000226.3>`_).

We recommend using a tool such as `VariantValidator <https://variantvalidator.org/>`_ or
`Mutalyzer <https://mutalyzer.nl/>`_ to validate the HGVS string. See the
`HGVS recommendations <http://varnomen.hgvs.org/recommendations/DNA/variant/alleles/>`_ for details about the
HGVS nomenclature.

**Data model**

.. csv-table::
   :header: Field, Type, Status, Description

    id, string, recommended, An arbitrary identifier
    hgvs, string, required, NM_000226.3:c.470T>G

**Example**

.. code-block:: json

    {
        "id": "",
        "hgvs": "NM_000226.3:c.470T>G"
    }

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
   :header: Field, Type, Status, Description

    genome_assembly, string, required, GRCh38
    id, string, recommended, An arbitrary identifier
    chr, string, required, chr2
    pos, int32, required, 134327882
    re, string, required, A
    alt, string, required, C
    info, string, optional, END=43500;SVTYPE=DUP;CHR2=1;SVLEN=29000;

**Example**

.. code-block:: json

    {
        "genome_assembly": "GRCh38",
        "chr": "2",
        "id": ".",
        "pos": 134327882,
        "ref": "A",
        "alt": "<DUP>",
        "info": "END=43500;SVTYPE=DUP;CHR2=1;SVLEN=29000;",
    }

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
first and second residues, and so on. Either the deleted or the inserted interval can be empty, resulting a pure
insertion or deletion.

Note that the deleted and inserted sequences in SPDI are all written on the positive strand for two-stranded molecules.

**Data model**

.. csv-table::
   :header: Field, Type, Status, Description

    id, string, recommended, An arbitrary identifier
    seq_id, string, required, Seq1
    position, int32, required, 4
    deleted_sequence, string, required, A
    inserted_sequence, string, required, G

**Example**

.. code-block:: json

    {
        "id": 1,
        "seqId": "NC_000001.10",
        "position": 12346,
        "deletedSequence": "",
        "insertedSequence": "T"
    }

.. _iscn:

IscnAllele
~~~~~~~~~~
This element can be used to describe cytogenetic anomalies according to the
`International System for Human Cytogenetic Nomenclature (ISCN) <https://www.ncbi.nlm.nih.gov/pubmed/?term=18428230>`_,
an international standard for human chromosome nomenclature, which includes band names, symbols and
abbreviated terms used in the description of human chromosome and chromosome abnormalities.

For example
del(6)(q23q24) describes a deletion from band q23 to q24 on chromosome 6.

**Data model**

.. csv-table::
   :header: Field, Type, Status, Description

   id, string, recommended, An arbitrary identifier
   iscn, string, required, t(8;9;11)(q12;p24;p12)

**Example**

.. code-block:: json

    {
      "id": "ISCN:12345",
      "iscn": "t(8;9;11)(q12;p24;p12)"
    }