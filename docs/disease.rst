===============
Disease element
===============

The word *phenotype* is used with many different meanings, including "the observable traits of an organism”. In medicine,
the word can be used with at least two different meanings. It is used to refer to
some **observed** deviation from normal morphology, physiology, or behavior. In contrast, the *disease* is a diagnosis,
i.e., and inference or hypothesis about the  cause underlying the observed phenotypic abnormalities. Occasionally,
physicians use the word phenotype to refer to a disease, but we do not use this meaning here. The Disease element
has two components.

.. code-block:: proto

 message Disease {
    OntologyClass term = 1;
    oneof onset {
        Age age_of_onset = 3;
        AgeRange age_range_of_onset = 4;
        OntologyClass class_of_onset = 6;
    }
   }

In the phenopacket schema, the disease element denotes the diagnosis by means of an ontology class. For rare
diseases, we recommend using a term from  `Online Mendelian Inheritance in Man (OMIM) <https://omim.org/>`_ (e.g.,
OMIM:101600), `Orphanet <https://www.orpha.net/consor/cgi-bin/index.php>`_ (e.g., Orphanet:710), or
`MONDO <https://github.com/monarch-initiative/mondo>`_ (e.g., MONDO:0007043). There are many other
ontologies and terminologiues that can be used including `Disease Ontology <http://disease-ontology.org/>`_,
`SNOMED <http://www.snomed.org/>`_, and `ICD <https://www.who.int/classifications/icd/en/>`_.

The ``onset`` element provides three possibilities of describing the onset of the disease. It is also possible
to denote the onset of individual phenotypic features of disease in the Phenopacket element. If an ontology class
is used to refer to the age of onset of the disease, we recommend using a term from
`the HPO onset hierarchy <https://hpo.jax.org/app/browse/term/HP:0003674>`_. The FHIR mapping of the onset element
is ``Condition.onset``.



