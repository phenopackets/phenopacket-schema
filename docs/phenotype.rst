.. _rstphenotypicfeature:

=================
PhenotypicFeature
=================


This element is intended to be used to describe a phenotype that characterizes the subject of the Phenopacket.
For medical use cases the subject will generally be a patient or a proband of a study, and the phenotypes will
be abnormalities described by an ontology such as the `Human Phenotype Ontology <http://www.human-phenotype-ontology.org>`_.
The word phenotype is used with many different meanings including disease entity, but in this context we mean
An individual phenotypic feature, observed as either present or absent (negated), with possible onset, modifiers and
frequency.

This is the protobuf that we use to define a phenotypic feature

.. code-block:: proto
  
  message PhenotypicFeature {
    string description = 1;
    OntologyClass type = 2;
    bool negated = 3;
    OntologyClass severity = 4;
    repeated OntologyClass modifiers = 5;
    oneof onset {
        Age age_of_onset = 6;
        AgeRange age_range_of_onset = 7;
        OntologyClass class_of_onset = 9;
    }
    repeated Evidence evidence = 10;
  }


description
~~~~~~~~~~~
This element represents a free-text description of the phenotype. It should not be used as the primary
means of describing the phenotype, but can be used to supplement the record if ontology terms are not
sufficiently able to capture all the nuances. In general, the type and onset etc... fields should be used for this purpose, and
this field is a last resort.
    

type
~~~~
The element represents the primary :ref:`ontology class <rstontologyclass>` which describes the phenotype.
For example `Craniosynostosis (HP:0001363) <https://hpo.jax.org/app/browse/term/HP:0001363>`_.

negated
~~~~~~~
This element is a flag to indicate whether the phenotype was observed or not.
The default is 'false', in other words the phenotype was observed. Therefore it is only
required in cases to indicate that the phenotype was looked for, but found to be absent.

severity
~~~~~~~~
This  element is an :ref:`ontology class <rstontologyclass>` that describes the severity of the condition e.g. subclasses of
`Severity (HP:0012824) <https://hpo.jax.org/app/browse/term/HP:0012824>`_ or
`SNOMED:272141005-Severities <https://phinvads.cdc.gov/vads/ViewCodeSystemConcept.action?oid=2.16.840.1.113883.6.96&code=272141005>`_
   
modifier
~~~~~~~~
This element is a list of :ref:`ontology class <rstontologyclass>` elements that can be empty or contain one or more
ontology terms that are intended
to provide  more expressive or precise descriptions of a phenotypic feature, including attributes such as
positionality and external factors that tend to trigger or ameliorate the feature.
Terms can be taken from the hierarchy of `Clinical modifier <https://hpo.jax.org/app/browse/term/HP:0012823>`_ in the HPO
(noting that severity should be coded in the severity element).

onset
~~~~~
This element can be used to describe the age at which a phenotypic feature was first noticed or diagnosed.
For many medical use cases, either the Age sub-element or an :ref:`ontology class <rstontologyclass>` (e.g., from the HPO `Onset (HP:0003674) <https://hpo.jax.org/app/browse/term/HP:0003674>`_ terms) will be used.

evidence
~~~~~~~~
This element is required and must contain one or more :ref:`Evidence <rstevidence>` elements
that specify how the phenotype was determined.





  .. list-table:: Phenopacket requirements for the ``Evidence`` element
    :widths: 25 50 50
    :header-rows: 1

    * - Field
      - Example
      - Status
    * - description
      - free text
      - optional
    * - type
      - :ref:`rstontologyclass` representing HP:0012823
      - required
    * - negated
      - false
      - optional
    * - severity
      - :ref:`rstontologyclass` representing `HP:0012825  <https://hpo.jax.org/app/browse/term/HP:0012825>`_
      - optional
    * - modifier
      - list of :ref:`rstontologyclass` representing one or more terms from `HP:0012823 <https://hpo.jax.org/app/browse/term/HP:0012823>`_
      - optional
    * - onset
      - :ref:`rstontologyclass` representing `HP:0011462  <https://hpo.jax.org/app/browse/term/HP:0011462>`_
      - optional
    * - evidence
      - An :ref:`Evidence <rstevidence>`
      - required





FHIR Mapping of the PhenotypicFeature element
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
This element maps the FHIR `Condition <https://www.hl7.org/fhir/condition.html>`_ or
`Observation <https://www.hl7.org/fhir/observation.html>`_ elements. The FHIR mapping of
the type element of PhenotypicFeature is *Condition.identifier*, the mapping of the severity element
is *Condition.severity*, the mapping of onset is *Condition.onset*.

