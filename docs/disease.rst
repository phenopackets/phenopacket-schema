.. _rstdisease:

#######
Disease
#######

The word *phenotype* is used with many different meanings, including "the observable traits of an organism”. In medicine,
the word can be used with at least two different meanings. It is used to refer to
some **observed** deviation from normal morphology, physiology, or behavior. In contrast, the *disease* is a diagnosis,
i.e., an inference or hypothesis about the  cause underlying the observed phenotypic abnormalities. Occasionally,
physicians use the word phenotype to refer to a disease, but we do not use this meaning here.



Data model
##########

.. csv-table::
   :header: Field, Type, Multiplicity, Description

   term, :ref:`rstontologyclass`, 1..1, An ontology class that represents the disease. REQUIRED.
   excluded, boolean, 0..1, Flag to indicate whether the disease was observed or not.
   onset, :ref:`rsttimeelement`, 0..1, an element representing the age of onset of the disease
   resolution, :ref:`rsttimeelement`, 0..1, an element representing the age of resolution (abatement) of the disease
   disease_stage, :ref:`rstontologyclass` (List), 0..*, List of terms representing the disease stage e.g. AJCC stage group.
   clinical_tnm_finding, :ref:`rstontologyclass` (List), 0..*, List of terms representing the tumor TNM score
   primary_site, :ref:`rstontologyclass`, 0..1, the primary site of disease
   laterality, :ref:`rstontologyclass`, 0..1, laterality (left or right) of the primary site of sites if applicable




Example
#######

.. code-block:: yaml

  disease:
    term:
        id: "OMIM:164400"
        label: "Spinocerebellar ataxia 1"
    onset:
        age:
            iso8601duration: "P38Y7M"


See :ref:`rstcancerexample` for a usage of the Disease element that includes information about tumor staging.

Explanations
############

term
~~~~

In the phenopacket schema, the disease element denotes the diagnosis by means of an ontology class. For rare
diseases, we recommend using a term from  `Online Mendelian Inheritance in Man (OMIM) <https://omim.org/>`_ (e.g.,
OMIM:101600), `Orphanet <https://www.orpha.net/consor/cgi-bin/index.php>`_ (e.g., Orphanet:710), or
`MONDO <https://github.com/monarch-initiative/mondo>`_ (e.g., MONDO:0007043). There are many other
ontologies and terminologies that can be used including `Disease Ontology <http://disease-ontology.org/>`_,
`SNOMED <http://www.snomed.org/>`_, and `ICD <https://www.who.int/classifications/icd/en/>`_.
For cancers, we recommend using terms from domain-specific ontologies, such as
`NCIthesaurus <https://ncit.nci.nih.gov/ncitbrowser/>`_ (e.g., NCIT:C9049).


excluded
~~~~~~~~

Flag to indicate whether the disease was observed or not. Default is 'false', in other words the disease was
observed. Therefore it is only required in cases to indicate that the disease was looked for, but found to be
absent.
More formally, this modifier indicates the logical negation of the OntologyClass used in
the 'term' field. *CAUTION* It is imperative to check this field for correct interpretation of the disease!

onset
~~~~~

The ``onset`` element provides three possibilities of describing the onset of the disease. It is also possible
to denote the onset of individual phenotypic features of disease in the Phenopacket element. If an ontology class
is used to refer to the age of onset of the disease, we recommend using a term from
`the HPO onset hierarchy <https://hpo.jax.org/app/browse/term/HP:0003674>`_.


resolution
~~~~~~~~~~

An element representing the age of resolution (abatement, recovery from) of the disease.


disease_stage
~~~~~~~~~~~~~

This attribute is used to describe the stage of disease. If the disease is a cancer, this attribute describes
the extent of cancer development, typically including an AJCC stage group (i.e., Stage 0, I-IV), though other staging
systems are used for some cancers. See `staging <https://www.cancer.gov/about-cancer/diagnosis-staging/staging>`_.
The list of elements constituting this attribute should be derived from child terms of NCIT:C28108 (Disease Stage
Qualifier) or equivalent hierarchy from another ontology.

clinical_tnm_finding
~~~~~~~~~~~~~~~~~~~~

This attribute can be used if the phenopacket is describing cancer. TNM findings score the progression of cancer
with respect to the originating tumor (T), spread to lymph nodes (N), and presence of metastases (M). These findings
are commonly reported for tumors, and support the stage classifications stored in the `disease_stage`_ attribute.
See `staging <https://www.cancer.gov/about-cancer/diagnosis-staging/staging>`_.
The list of elements constituting this attribute should be derived from child terms of NCIT:C48232 (Cancer TNM Finding)
or equivalent hierarchy from another ontology.

primary_site
~~~~~~~~~~~~

The term used to describe the primary site of disease. Terms from the NCIT or UBERON.

laterality
~~~~~~~~~~
The term used to indicate laterality of diagnosis, if applicable.








