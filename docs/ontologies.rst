
.. _rstontologies:

##########
Ontologies
##########


A terminology is a set of prefered or official terms in a domain. One of the  most important terminologies for information
retrieval in the medical domain is the Medical Subject Headings (MeSH), which is used for indexing and
searching Medline.

Ontologies differ from terminologies in that ontologies define relationships between concepts
in a way that allows computational logical reasoning. A short introduction is available
in this recent `review <https://www.ncbi.nlm.nih.gov/pubmed/30304648>`_.

While both terminologies and ontologies can be used, the phenopacket schema recommends the use of specific ontologies in order to maximize utility of performing algorithmic analysis over medically relevant abnormalities.

The `Human Phenotype Ontology (HPO) <http://www.human-phenotype-ontology.org>`_  describes patient phenotypic features and was originally designed for genomic diagnostics, translational research, genomic matchmaking, and systems biology applications in the field of rare disease and other fields of medicine. It is increasinly used within clinical applications today for precision medicine.

The HPO is developed in the context of the `Monarch Initiative <https://monarchinitiative.org/>`_, an international team of
computer scientists, clinicians, and biologists in the United States, Europe, and Australia;
HPO is being translated into multiple languages to support international interoperability.
Due to its extensive phenotypic coverage beyond other terminologies, HPO has recently been integrated
into the `Unified Medical Language System <https://www.nlm.nih.gov/research/umls/sourcereleasedocs/current/HPO/>`_ (UMLS) to support deep phenotyping in a variety of mainstream health care IT systems.

The `National Cancer institute's Thesaurus (NCIt) <http://www.obofoundry.org/ontology/ncit.html>`_ is used for
cancer biosamples, and is the de facto standard for cancer knowledge representation and regulatory submission. 

The `Mondo Disease Ontology (Mondo) <https://mondo.monarchinitiative.org/>`_ is an ontology that harmonizes disease definitions and identifiers in a systematic and computational manner across many resources, and is therefore the recommneded disease ontology for use in Phenopackets to maximize interoperability. 

Other terminologies and ontologies may be used in the Phenopackets Schema, such as the International Classification of Diseases (ICD) or the Systematized Nomenclature of Medicine (SNOMED-CT), or numerous ontologies from the `Open Biomedical Ontologies Library (OBO) <http://www.obofoundry.org/>`_.


