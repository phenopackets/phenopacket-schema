===============
Variant element
===============
This element should be used to describe candidate variants or diagnosed causative
variants. The resources using these fields should define what this represents in their context.

The PhenoPackets format expects that variants are described using the
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

The protobuf code is as follows::

  message Variant {
    GenomeAssembly genome_assembly = 1; 
    string sequence_accession = 2; 
    CoordinateSystem coordinate_system = 3;
    int32 position = 4;
    // Deleted bases on the forward strand - equivalent to the VCF REF
    string deletion = 5;
    // Inserted bases on the forward strand - equivalent to the VCF ALT
    string insertion = 6;
    // SPDI can't represent the full set of HGVS expressions, in which case use the HGVS string
    string hgvs = 7;
    map<string, OntologyClass> sample_genotypes = 8;
  }

genome_assembly
===============
The genome assembly (version) on which the coordinates of the variant are based. Currently, hg38 is the assembly for the human genome, but the previous version hg19 is still in use.

It is not strictly necessary to indicate the genome assembly the sequence accession as that will be unique for the assembly/sequence.

sequence
========
 Accession of the sequence e.g., NC_000010.10 (GRCh37 chromosome 10), or ENST00000380152.7 (Ensembl's BRCA2-201 transcript).
 

coordinate_system
=================
Coordinate system of the position. Defaults to ZERO_BASED. Note that SPDI and VMC mandate a ZERO_BASED system,
whereas VCV and HGVS use ONE_BASED systems. This is specified here as a means of providing unambiguous positioning
as opposed to it being implicit within the spec of the system. This element relies on the following enum::

   enum CoordinateSystem {
     ZERO_BASED = 0;
     ONE_BASED = 1;
   }


position (Variant)
==================
Sequence position at which the change is located, in the specified coordinate system.
For instance, ``Seq1:4:A:G`` refers to a single nucleotide variant at the fifth nucleotide (
nucleotide 4 according to zero-based numbering) from an ``A`` to a ``G``. See the
`SPDI webpage <https://www.ncbi.nlm.nih.gov/variation/notation/>`_ for more
examples.

deletion
========
TODO

insertion
=========

TODO document me following discussions.


hgvs
====
The `sequence variant nomenclature of the Human Genome Variation Society (HGVS) <http://varnomen.hgvs.org/>`_ is in wide use
and can represent some variants not currently expressable using SPDI. HGVS has historically concentrated on providing
an unambiguous use of variants with respect to transcripts (which is the typical use case in genetic diagnostics), and is widely used in medical publications. 

sample_genotypes
================
The genotype of the variant as determined in all of the samples represented in this Phenopacket is represented using a list of
terms taken from the `Genotype Ontology (GENO) <https://www.ebi.ac.uk/ols/ontologies/geno>`_. For instance, if a variant
affects one of two alleles at a certain locus, we could record the genotype using the term
`heterozygous (GENO:0000135) <https://www.ebi.ac.uk/ols/ontologies/geno/terms?iri=http%3A%2F%2Fpurl.obolibrary.org%2Fobo%2FGENO_0000135>`_.
