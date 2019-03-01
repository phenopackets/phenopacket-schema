=================
Phenotype element
=================


This element is intended to be used to describe a phenotype that characterizes the subject of the PhenoPacket.
For medical use cases the subject will generally be a patient or a proband of a study, and the phenotypes will
be abnormalities described by an ontology such as the `Human Phenotype Ontology <http://www.human-phenotype-ontology.org>`_.
The word phenotype is used with many different meanings including disease entity, but in this context we mean
An individual phenotypic feature, observed as either present or absent (negated), with possible onset, modifiers and
frequency.

This is the protobuf that we use to define a phenotype::

  
  message Phenotype {
    string description = 1;
    OntologyClass type = 2;
    bool negated = 3;
    OntologyClass severity = 4;
    repeated OntologyClass modifiers = 5;
    oneof onset {
        Age age_of_onset = 6;
        google.protobuf.Timestamp time_of_onset = 7;
        Period period_of_onset = 8;
        OntologyClass class_of_onset = 9;
    }
    repeated Evidence evidence = 10;
  }


description
===========
This element represents a free-text description of the phenotype. It should not be used as the primary
means of describing the phenotype, but can be used to supplement the record if ontology terms are not
sufficiently able to capture all the nuances. In general, the type and onset etc... fields should be used for this purpose, and
this field is a last resort.
    

type (Phenotype)
================
The element represents the primary ontology class which describes the phenotype.
For example `Craniosynostosis (HP:0001363) <https://hpo.jax.org/app/browse/term/HP:0001363>`_.

negated (Phenotype)
===================
This optional element is a flag to indicate whether the phenotype was observed or not.
Default is 'false', in other words the phenotype was observed. Therefore it is only
required in cases to indicate that the phenotype was looked for, but not observed.

severity (Phenotype)
====================
This optional element describes the severity of the condition e.g. subclasses of
`Severity (HP:0012824) <https://hpo.jax.org/app/browse/term/HP:0012824>`_ or `SNOMED:272141005-Severities <https://phinvads.cdc.gov/vads/ViewCodeSystemConcept.action?oid=2.16.840.1.113883.6.96&code=272141005>`_
   
modifiers (Phenotype)
=====================
This element can be empty or contain one or more ontology terms that are intended
to provide  more expressive or precise descriptions of a phenotypic feature, including attributes such as
positionality and external factors that tend to trigger or ameliorate the feature.
Terms can be taken from the hierarchy of `Clinical modifier <https://hpo.jax.org/app/browse/term/HP:0012823>`_ in the HPO (noting that severity should be coded in the severity element). 

onset (Phenotype)
=================
This element can be used to describe the age at which a phenotypic feature was first noticed or diagnosed.
For many medical use cases, either the Age subelement or an ontology term (e.g., from the HPO `Onset (HP:0003674) https://hpo.jax.org/app/browse/term/HP:0003674>`_ terms) will be used.

evidence (Phenotype)
====================
This element can contain one or more :ref:`Evidence element` items that specify how the phenotype was determined.

FHIR Mapping of the Phenotype element
=====================================
This element maps the FHIR `Condition <https://www.hl7.org/fhir/condition.html>`_ or
`Observation <https://www.hl7.org/fhir/observation.html>`_ elements. The FHIR mapping of
the type element of Phenotype is *Condition.identifier*, the mapping of the severity element
is *Condition.severity*, the mapping of onset is *Condition.onset*.

