.. _rstrecommendedontologies:

######################
Recommended Ontologies
######################

The phenopacket schema can be used with any ontologies. The phenopacket can be compared to a hierarchical structure
with "slots" for ontology terms and other data. Different use cases may require different ontology terms to cover
the subject matter or to fulfil requirements of a specific research project. The spectrum of requirements is so broad
that we do not think it is appropriate to require a specific set of ontologies for use with phenopackets. Nonetheless,
the value of phenopacket-encoded data will be greatly increased if the community of users converges towards a common
set of ontologies (to the extent possible). Here, we provide general recommendations for ontologies that we have found
to be useful. This list is incomplete and we would welcome feedback from the community about ontologies that should be
added to this page.

We do anticipate that individual research consortia or other groups should agree on a set of allowed ontologies for
specific projects.


Diseases
########

Mondo Disease Ontology provides a comprehensive logically structured ontology of diseases that integrates multiple
other disease ontologies.


.. list-table:: Example terms from Mondo
   :widths: 50 50
   :header-rows: 1

   * - Label
     - Id
   * - incontinentia pigmenti
     - `MONDO:0010631 <https://www.ebi.ac.uk/ols/ontologies/mondo/terms?iri=http%3A%2F%2Fpurl.obolibrary.org%2Fobo%2FMONDO_0010631>`_
   * - dilated cardiomyopathy 3B
     - `MONDO:0010542 <https://www.ebi.ac.uk/ols/ontologies/mondo/terms?iri=http%3A%2F%2Fpurl.obolibrary.org%2Fobo%2FMONDO_0010542>`_

Other disease ontologies of note include
The National Cancer Institute's thesaurus (`NCIT  <https://www.ebi.ac.uk/ols/ontologies/ncit>`_),
Orphanet Rare Disease Ontology (`ORDO <https://www.ebi.ac.uk/ols/ontologies/ordo>`_),
Disease Ontology (`DO <https://www.ebi.ac.uk/ols/ontologies/doid>`_), and the
Online Mendelian Inheritance in Man (`OMIM <https://omim.org/>`_).

Phenotypic features
###################

The `Human Phenotype Ontology (HPO) <https://hpo.jax.org/app/>`_ provides a comprehensive logical standard to describe
and computationally analyze phenotypic abnormalities found
in human disease.



.. list-table:: Example terms from HPO
   :widths: 50 50
   :header-rows: 1

   * - Label
     - Id
   * - Arachnodactyly
     - `HP:0001166 <https://hpo.jax.org/app/browse/term/HP:0001166>`_
   * - Patent ductus arteriosus
     - `HP:0001643 <https://hpo.jax.org/app/browse/term/HP:0001643>`_



Anatomy
#######

`UBERON <https://pubmed.ncbi.nlm.nih.gov/22293552/>`_ is an integrated cross-species ontology with classes
representing a variety of anatomical entities.


.. list-table:: Example terms from UBERON
   :widths: 50 50
   :header-rows: 1

   * - Label
     - Id
   * - heart
     - `UBERON:0000948 <https://www.ebi.ac.uk/ols/ontologies/uberon/terms?iri=http%3A%2F%2Fpurl.obolibrary.org%2Fobo%2FUBERON_0000948>`_
   * - brain
     - `UBERON:0000955 <https://www.ebi.ac.uk/ols/ontologies/uberon/terms?iri=http%3A%2F%2Fpurl.obolibrary.org%2Fobo%2FUBERON_0000955>`_


Genes
#####

The HUGO Gene Nomenclature Committee (HGNC) provides standard names, symbols, and IDs for human genes.


.. list-table:: Example terms from HGNC
   :widths: 50 50
   :header-rows: 1

   * - Label
     - Id
   * - FBN1
     - `HGNC:3603 <https://www.genenames.org/data/gene-symbol-report/#!/hgnc_id/HGNC:3603>`_
   * - NF1
     - `HGNC:7765 <https://www.genenames.org/data/gene-symbol-report/#!/hgnc_id/HGNC:7765>`_

Other sources of gene IDs include `NCBI Gene <https://www.ncbi.nlm.nih.gov/gene/>`_ and
`Ensembl <https://ensembl.org/>`_.

Units of Measurement
####################

The
`Units of measurement ontology <https://pubmed.ncbi.nlm.nih.gov/23060432/>`_
(denoted `UO <https://www.ebi.ac.uk/ols/ontologies/uo>`_) provides terms for units commonly encountered in
medical data. The following table shows some typical examples.


.. list-table:: Example terms from Units of measurement ontology
   :widths: 50 50
   :header-rows: 1

   * - Label
     - Id
   * - millimolar
     - `UO:0000063 <https://www.ebi.ac.uk/ols/ontologies/uo/terms?iri=http%3A%2F%2Fpurl.obolibrary.org%2Fobo%2FUO_0000063>`_
   * - milligram
     - `UO:0000022 <https://www.ebi.ac.uk/ols/ontologies/uo/terms?iri=http%3A%2F%2Fpurl.obolibrary.org%2Fobo%2FUO_0000022>`_
   * - millimetres of mercury
     - `UO:0000272 <https://www.ebi.ac.uk/ols/ontologies/uo/terms?iri=http%3A%2F%2Fpurl.obolibrary.org%2Fobo%2FUO_0000272>`_


Genotypes
#########
GENO is anontology of genotypes their more fundamental sequence components, and links to related biological
and experimental entities. We use GENO terms to denote genotypes.


.. list-table:: Example terms from GENO
   :widths: 50 50
   :header-rows: 1

   * - Label
     - Id
   * - heteroyzgous
     - `GENO:0000135 <https://www.ebi.ac.uk/ols/ontologies/geno/terms?iri=http%3A%2F%2Fpurl.obolibrary.org%2Fobo%2FGENO_0000135>`_
   * - homozygous
     - `GENO:0000136 <https://www.ebi.ac.uk/ols/ontologies/geno/terms?iri=http%3A%2F%2Fpurl.obolibrary.org%2Fobo%2FGENO_0000136>`_


Assays
######

Logical Observation Identifiers Names and Codes (LOINC) is a database and universal standard for identifying medical
laboratory observations. It can be used to denote clinical assays in the :ref:`rstmeasurement` element.


.. list-table:: Example terms from LOINC
   :widths: 50 50
   :header-rows: 1

   * - Label
     - Id
   * - Platelets [#/volume] in Blood
     - `LOINC:26515-7 <https://loinc.org/26515-7/>`_
   * - Calcium [Mass/volume] in Serum or Plasma
     - `LOINC:17861-6 <https://www <https://loinc.org/17861-6/>`_


Medications
###########

`DrugCentral <https://pubmed.ncbi.nlm.nih.gov/33151287/>`_ integrates a broad spectrum of drug resources related to
chemical structures, biological activities, regulatory data, pharmacology and drug formulations


.. list-table:: Example terms from DrugCentral
   :widths: 50 50
   :header-rows: 1

   * - Label
     - Id
   * - losartan
     - `DrugCentral:1610 <https://drugcentral.org/drugcard/1610>`_
   * - selumetinib
     - `DrugCentral:5388 <https://drugcentral.org/drugcard/5388>`_

Other ontologies with coverage of drugs include `ChEBI <https://www.ebi.ac.uk/chebi/>`_,
`RxNorm <https://www.nlm.nih.gov/research/umls/rxnorm/index.html>`_, and `DrugBank <https://go.drugbank.com/>`_.




The National Cancer Institute's Thesaurus
#########################################


The National Cancer Institute's thesaurus (NCIT) provides a wide range of terms that can be useful for phenopackets.
In addition to providing an ontology of cancers, NCIT provides terms for procedures, findings, units or measurement,
scheduling, etc. The following table shows an an example pf
the subhierarchy for `Unit of Measure (NCIT:C25709) <https://www.ebi.ac.uk/ols/ontologies/ncit/terms?iri=http%3A%2F%2Fpurl.obolibrary.org%2Fobo%2FNCIT_C25709>`_.
and for `Schedule Frequency (NCIT:C64493) <https://www.ebi.ac.uk/ols/ontologies/ncit/terms?iri=http%3A%2F%2Fpurl.obolibrary.org%2Fobo%2FNCIT_C64493>`_.


.. list-table:: Example terms from NCIT Unit of Measure and Schedule Frequency subhierarchies
   :widths: 50 50
   :header-rows: 1

   * - Label
     - Id
   * - Milligram per Kilogram per Dose
     - `NCIT:C124458 <https://www.ebi.ac.uk/ols/ontologies/ncit/terms?iri=http%3A%2F%2Fpurl.obolibrary.org%2Fobo%2FNCIT_C124458>`_
   * - Twice Daily
     - `NCIT:C64496 <https://www.ebi.ac.uk/ols/ontologies/ncit/terms?iri=http%3A%2F%2Fpurl.obolibrary.org%2Fobo%2FNCIT_C64496>`_
   * - Cells per Milliliter
     - `NCIT:C74919 <https://www.ebi.ac.uk/ols/ontologies/ncit/terms?iri=http%3A%2F%2Fpurl.obolibrary.org%2Fobo%2FNCIT_C74919>`_


Experimental Factor Ontology
############################

Experimental factor ontology (`EFO <https://www.ebi.ac.uk/ols/ontologies/efo>`_) is an ontology of experimental
variables particularly those used in molecular biology. EFO imports terms from many source ontologies and
provides additional terms needed to provide a systematic description of many experimental variables available in EBI databases.

.. list-table:: Example terms from EFO
   :widths: 50 50
   :header-rows: 1

   * - Label
     - Id
   * - abnormal sample
     - `EFO:0009655 <https://www.ebi.ac.uk/ols/ontologies/efo/terms?iri=http%3A%2F%2Fwww.ebi.ac.uk%2Fefo%2FEFO_0009655>`_
   * - genomic DNA
     - `EFO:0008479 <https://www.ebi.ac.uk/ols/ontologies/efo/terms?iri=http%3A%2F%2Fwww.ebi.ac.uk%2Fefo%2FEFO_0008479>`_
   * - milligram per kilogram
     - `EFO:0002902 <https://www.ebi.ac.uk/ols/ontologies/efo/terms?iri=http%3A%2F%2Fwww.ebi.ac.uk%2Fefo%2FEFO_0002902>`_

