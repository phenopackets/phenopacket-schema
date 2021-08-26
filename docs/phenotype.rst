.. _rstphenotypicfeature:

#################
PhenotypicFeature
#################


This element is intended to be used to describe a phenotype that characterizes the subject of the Phenopacket.
For medical use cases the subject will generally be a patient or a proband of a study, and the phenotypes will
be abnormalities described by an ontology such as the `Human Phenotype Ontology <http://www.human-phenotype-ontology.org>`_.
The word phenotype is used with many different meanings including disease entity, but in this context we mean
An individual phenotypic feature, observed as either present or absent (excluded), with possible onset, modifiers and
frequency.


Data model
##########

.. csv-table::
   :header: Field, Type, Multiplicity, Description

    description, string, optional, human-readable verbiage **NOT** for structured text
    type, :ref:`rstontologyclass`, 1..1, term denoting the phenotypic feature. REQUIRED.
    excluded, boolean, 0..1, defaults to `false`
    severity, :ref:`rstontologyclass`, 0..1, description of the severity of the feature described in `type`. For instance terms from `HP:0012824  <https://hpo.jax.org/app/browse/term/HP:0012824>`_
    modifiers, list of :ref:`rstontologyclass`, 0..*, For instance one or more terms from `HP:0012823 <https://hpo.jax.org/app/browse/term/HP:0012823>`_
    onset, :ref:`rsttimeelement`, 0..1, Age or time at which the feature was first observed.
    resolution, :ref:`rsttimeelement`, 0..1, Age or time at which the feature resolved or abated.
    evidence, :ref:`Evidence <rstevidence>`, 0..*, the evidence for an assertion of the observation of a `type`. RECOMMENDED.

Example
#######

The following example specifies recurrent
`Infantile spasms <https://hpo.jax.org/app/browse/term/HP:0012469>`_, which had onset
at age 6 months and resolved at age 4 years and 2 months.

.. code-block:: yaml

    phenotypicFeature:
        type:
            id: "HP:0012469"
            label: "Infantile spasms"
        modifiers:
            - id: "HP:0031796"
            label: "Recurrent"
        onset:
            age:
                iso8601duration: "P6M"
        resolution:
            age:
                iso8601duration: "P4Y2M"


Explanations
############


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

excluded
~~~~~~~~
This element is a flag to indicate whether the phenotype was observed or not.
The default is 'false', in other words the phenotype was observed. Therefore it is only
required in cases to indicate that the phenotype was looked for, but found to be absent.

severity
~~~~~~~~
This  element is an :ref:`ontology class <rstontologyclass>` that describes the severity of the condition e.g. subclasses of
`Severity (HP:0012824) <https://hpo.jax.org/app/browse/term/HP:0012824>`_ or
`SNOMED:272141005-Severities <https://phinvads.cdc.gov/vads/ViewCodeSystemConcept.action?oid=2.16.840.1.113883.6.96&code=272141005>`_
   
modifiers
~~~~~~~~~
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


resolution
~~~~~~~~~~
This element can be used to describe the age or time when a phenotypic feature resolved (disappeared, got better).
In the example shown above, infantile spasms no longer occured after the age of 4 years and 2 months.


evidence
~~~~~~~~
This element is recommended and contain one or more :ref:`Evidence <rstevidence>` elements
that specify how the phenotype was determined.


