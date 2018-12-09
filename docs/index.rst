=====================================================
Welcome to the documentation for phenopackets-schema!
=====================================================

.. toctree::
   :maxdepth: 2
   :caption: Contents

   protobuf
   basics
   spherocytosis
   individual
   phenotype
   variant
   age
   geolocation
   attributes
   externalresource
   evidence

~~~~~~~~~~~~~~~~~~~~~~
What is a PhenoPacket?
~~~~~~~~~~~~~~~~~~~~~~

Phenopackets represent an open standard for sharing disease and phenotype information will improve our ability to understand, diagnose, and treat both rare and common diseases. A PhenoPacket links detailed phenotype descriptions with disease, patient, and genetic information, enabling clinicians, biologists, and disease and drug researchers to build more complete models of disease. The standard is designed to encourage wide adoption and synergy between the people, organizations and systems that comprise the joint effort to address human disease and biological understanding.

While great strides have been made in exchange formats for sequence and variation data (e.g. `Variant Call Format <https://samtools.github.io/hts-specs/VCFv4.3.pdf>`_), complementary standards for phenotypes and environment are urgently needed. For individuals with rare and undiagnosed diseases, such standards could improve the speed and accuracy of diagnosis. The development of a clinical phenotype data exchange standard is both necessary and timely. It is necessary because study sizes of well over 100,000 patients are thought to be required to effectively assess the role of rare variation in common disease3 or to discover the genomic basis for a substantial portion of Mendelian diseases. It is timely because studies of this power are now becoming financially and technologically tractable.

Phenotypic abnormalities of individuals are currently described in diverse places in diverse formats: publications, databases, health records, and even in social media. We propose that these descriptions a) contain a minimum set of fields and b) get transmitted alongside genomic sequence data, such as in VCF, between clinics, authors, journals, and data repositories. The structure of the data in the exchange standard will be optimized for integration from these distributed contexts. The implementation of such a system will allow the sharing of phenotype data prospectively, as well as retrospectively. Increasing the volume of computable data across a diversity of systems will support large-scale computational disease analysis using the combined genotype and phenotype data.

The terms ‘disease’ and ‘phenotype’ are often conflated. The PhenoPackets standard uses ``phenotype`` to refer to a phenotypic feature, such as `Arachnodactyly <https://hpo.jax.org/app/browse/term/HP:0001166>`_, that is the component of a disease, such as `Marfan syndrome <https://hpo.jax.org/app/browse/disease/OMIM:154700>`_. The PhenoPacket proposed here is designed to support `deep phenotyping <https://www.ncbi.nlm.nih.gov/pubmed/22504886>`_, a process wherein individual components of each phenotype are observed and documented. Phenopackets require the use of a common ontology, a logically defined hierarchy of terms, that allows sophisticated algorithmic analysis over medically relevant abnormalities. The `Human Phenotype Ontology <http://www.human-phenotype-ontology.org>`_ (HPO) was built for this purpose and has been used for genomic diagnostics, translational research, genomic matchmaking, and systems biology applications. The HPO is developed in the context of the `Monarch Initiative <https://monarchinitiative.org/>`_, an international team of computer scientists, clinicians, and biologists in the United States, Europe, and Australia; HPO is being translated into multiple languages to support international interoperability. Due to its extensive phenotypic coverage beyond other terminologies, HPO has recently been integrated into the `Unified Medical Language System <https://www.nlm.nih.gov/research/umls/sourcereleasedocs/current/HPO/>`_ (UMLS) to support deep phenotyping in a variety of mainstream health care IT systems.

For more information, please consult the `Phenopackets Website <http://phenopackets.org/>`_.

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
What is the PhenoPacket Schema?
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

The goal of the `phenopacket-schema <https://github.com/phenopackets/phenopacket-schema>`_ is to define the phenotypic description of a patient/sample in the context of rare disease or cancer genomic diagnosis. It aims to provide sufficient and shareable information of the data outside of the EHR (Electronic Health Record) with the aim of enabling capturing of sufficient structured data at the point of care by a clinician or clinical geneticist for sharing with other labs or computational analysis of the data in clinical or research environments.

This work  has been produced as part of the `GA4GH Clinical Phenotype Data Capture Workstream <https://ga4gh-cp.github.io/>`_ and is designed to
be compatible with  `GA4GH metadata-schemas <https://github.com/ga4gh-metadata/metadata-schemas>`_. 

The phenopacket schema defines a common, limited set of data types which may be composed into more specialised types for data sharing between resources using an agreed upon common schema.

This common schema has been used to define the 'Phenopacket' which is a catch-all collection of data types, specifically focused on representing rare-disease or cancer samples for both initial data capture and analysis. The phenopacket is designed to be both human and machine-readable, and to inter-operate with the `HL7 Fast Healthcare Interoperability Resources Specification (aka FHIR®) <http://hl7.org/fhir/>`_.
