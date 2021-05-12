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
and `SPDI <https://>`_. We recommend the use of VRS Variation objects for representing variants when possible.

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
