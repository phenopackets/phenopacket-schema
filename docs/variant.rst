===============
Variant element
===============
This element should be used to describe candidate variants or diagnosed causative
variants. There is currently no standard variant nomenclature that can represent all kinds
of genetic variation that is relevant to human medicine, science, and model organisms. Therefore,
we represent variants using the keyword ``oneof``, which is used in protobuf for an item
with many  optional fields where at most one field will be set at the same time. Variant messages
contain an allele, the genotype of the allele, and, and a background element that can be
used to represent the genetic background of an animal model.

Alleles can be
listed using HGVS, VCF, SPDI, ISCN, or "mouse allele" notation.::

    message Variant {
      oneof allele {
        HgvsAllele hgvs_allele = 2;
        VcfAllele vcf_allele = 3;
        SpdiAllele spdi_allele = 4;
        IscnAllele iscn_allele = 5;
        MouseAllele mouse_allele = 8;
      }
      OntologyClass genotype = 6;
      string background = 7;
    }



The ``variant`` element itself is an optional element of a ``Phenopacket``  or ``Biosample``. If it is present,
the Phenopacket standard has the following requirements.

 .. list-table:: Phenopacket requirements for the ``variant`` element
   :widths: 25 50 50
   :header-rows: 1

   * - Field
     - Example
     - Status
   * - allele
     - see individual elements, below
     - required
   * - genotype
     - See ``genotype``, below
     - recommended
   * - background
     - involves: 129S1/Sv * 129X1/SvJ * C57BL/6J
     - optional




genotype
~~~~~~~~
The genotype of the variant as determined in all of the samples represented in this Phenopacket is represented using a list of
terms taken from the `Genotype Ontology (GENO) <https://www.ebi.ac.uk/ols/ontologies/geno>`_. For instance, if a variant
affects one of two alleles at a certain locus, we could record the genotype using the term
`heterozygous (GENO:0000135) <https://www.ebi.ac.uk/ols/ontologies/geno/terms?iri=http%3A%2F%2Fpurl.obolibrary.org%2Fobo%2FGENO_0000135>`_.

background
~~~~~~~~~~
Phenopackets that describe mouse models of disease should
report the genetic background, using a string such as
``involves: 129S1/Sv * 129X1/SvJ * C57BL/6J``.
We expect that this element will be used mainly for Phenopackets for animal models.


allele
~~~~~~
The allele element is required and can be one and only one of ``HgvsAllele``, ``VcfAlelle``, ``SpdiAllele``, ``IcsnAllele``,
or ``MouseAllele``.

HgvsAllele
~~~~~~~~~~
This element is used to describe an allele according to the nomenclature of the
`Human Geneome Variation Society (HGVS) <http://www.hgvs.org/>`_. For instance,
``NM_000226.3:c.470T>G`` indicates that a T at position 470 of the sequence represented by version 3 of
NM_000226 (which is the mRNA of the human keratin 9 gene `KRT9 <https://www.ncbi.nlm.nih.gov/nuccore/NM_000226.3>`_). ::

    message HgvsAllele {
        string id = 1;
        string hgvs = 2;
    }


We recommend using a tool such as `VariantValidator <https://variantvalidator.org/>`_ or
`Mutalyzer <https://mutalyzer.nl/>`_ to validate the HGVS string. See the
`HGVS recommendations <http://varnomen.hgvs.org/recommendations/DNA/variant/alleles/>`_ for details about the
HGVS nomenclature.


 .. list-table:: Phenopacket requirements for the ``HgvsAllele`` element
   :widths: 25 50 50
   :header-rows: 1

   * - Field
     - Example
     - Status
   * - id
     - An arbitrary identifier
     - recommended
   * - hgvs
     - NM_000226.3:c.470T>G
     - required



VcfAllele
~~~~~~~~~
This element is used to describe variants using the
`Variant Call Format <https://samtools.github.io/hts-specs/VCFv4.3.pdf>`_, which is in near universal use
for exome, genome, and other Next-Generation-Sequencing-based variant calling. It is an appropriate
option to use for variants reported according to their chromosomal location as dervied from a VCF file. ::

    message VcfAllele {
        string id = 1;
        string chr = 2;
        int32 pos = 3;
        string ref = 4;
        string alt = 5;
        string info = 6;
    }

In the Phenopacket format, it is expected that one ``VcfAllele`` message described a single allele (in contrast to
the actial VCF format that allows multiple alleles at the same position to be reported on the same line; to report
these in Phenopacket format, two ``variant`` messages would be required).


For structural variation the INFO field should contain the relevant information .
In general, the ``info`` field should only be used to report structural variants and it is not expected that the
Phenopacket will report the contents of the info field for single nucleotide and other small variants.


 .. list-table:: Phenopacket requirements for the ``VcfAllele`` element
   :widths: 25 50 50
   :header-rows: 1

   * - Field
     - Example
     - Status
   * - id
     - An arbitrary identifier
     - recommended
   * - chr
     - chr2
     - required
   * - pos
     - 134327882
     - required
   * - ref
     - A
     - required
   * - alt
     - C
     - required
   * - info
     - END=43500;SVTYPE=DUP;CHR2=1;SVLEN=29000;
     - optional


SpdiAllele
~~~~~~~~~~
This option can be used as an alternative to the VcfAllele, and describes variants using the
`Sequence Position Deletion Insertion (SPDI) notation <https://www.ncbi.nlm.nih.gov/variation/notation/>`_. We
recommend that users familiarize themselves with this relatively new standard, which
differs in important ways from other standards such as VCF and HGVS. SPDI has become the
`ClinVar <https://www.ncbi.nlm.nih.gov/clinvar/>`_, `dbSNP <https://www.ncbi.nlm.nih.gov/projects/SNP/>`_,
and and soon the `EVA <https://www.ebi.ac.uk/eva/>`_.

Tools for interconversion between SPDI, HGVS and VCF exist at the `NCBI <https://api.ncbi.nlm.nih.gov/variation/v0/>`_.

Effort of the  GA4GH Variant Representation are ongoing, and this may change in future version of
PhenoPackets.

- See: https://docs.google.com/document/d/1Sulg3kECnorTEAbutINOsK-lFkKAcKpl6IHgPaPQEgA
- See: https://github.com/ga4gh-beacon/specification/blob/master/beacon.yaml

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




The definition of the ``SpdiAllele`` element is as follows. ::

    message SpdiAllele {
        string id = 1;
        string seq_id = 2;
        int32 position = 3;
        string deleted_sequence = 4;
        string inserted_sequence = 5;
    }

Note that the deleted and inserted sequences in SPDI are all written on the positive strand for two-stranded molecules.

 .. list-table:: Phenopacket requirements for the ``SpdiAllele`` element
   :widths: 25 50 50
   :header-rows: 1

   * - Field
     - Example
     - Status
   * - id
     - An arbitrary identifier
     - recommended
   * - seq_id
     - Seq1
     - required
   * - position
     - 4
     - required
   * - deleted_sequence
     - A
     - required
   * - inserted_sequence
     - G
     - required



IscnAllele
~~~~~~~~~~
This element can be used to describe cytogenetic anomalies according to the
`International System for Human Cytogenetic Nomenclature (ISCN) <https://www.ncbi.nlm.nih.gov/pubmed/?term=18428230>`_,
an international standard for human
chromosome nomenclature, which includes band names,
symbols and abbreviated terms used in the description of human chromosome and chromosome abnormalities. ::

    message IscnAllele {
        string id = 1;
        string var = 2;
    }


For example
del(6)(q23q24) describes a deletion from band q23 to q24 on chromosome 6.

 .. list-table:: Phenopacket requirements for the ``IscnAllele`` element
   :widths: 25 50 50
   :header-rows: 1

   * - Field
     - Example
     - Status
   * - id
     - An arbitrary identifier
     - recommended
   * - var
     - t(8;9;11)(q12;p24;p12)
     - required


MouseAllele
~~~~~~~~~~~
This sessage is intended specifically for encoding mouse alleles in accordance with the
`International Committee on Standardized Genetic Nomenclature for Mice <http://informatics.jax.org/mgihome/nomen/>`_.::

    message MouseAllele {
        string id = 1;
        string gene = 2;
        string allele_code = 3;
    }

The example given here encodes the allele `Fbn1\<tm1Hcd\> <http://www.informatics.jax.org/allele/key/51149>`_.
The allele_code should be used for the allele name or lab code, which is written
in superscript according to the International Committee on Standardized Genetic
Nomenclature for Mice.

 .. list-table:: Phenopacket requirements for the ``MouseAllele`` element
   :widths: 25 50 50
   :header-rows: 1

   * - Field
     - Example
     - Status
   * - id
     - An arbitrary identifier
     - recommended
   * - gene
     - Fbn1
     - required
   * - allele_code
     - tm1Hcd
     - required