.. _rstvariantinterpretation:

#####################
VariantInterpretation
#####################

This element represents the interpretation of a variant according to
the American College of Medical Genetics (ACMG) variant interpretation guidelines.



Data model
##########

VariantInterpretation
~~~~~~~~~~~~~~~~~~~~~
.. list-table:: Definition  of the ``VariantInterpretation`` element
   :widths: 25 25 25 75
   :header-rows: 1

   * - Field
     - Type
     - Multiplicity
     - Description
   * - acmg_pathogenicity_classification
     - :ref:`rstacmgPathogenicityClassification`
     - 1..1
     - one of the five ACMG pathogenicity categories, or NOT_PROVIDED. The default is NOT_PROVIDED
   * - therapeutic_actionability
     - :ref:`rsttherapeuticactionability`
     - 1..1
     - The therapeutic actionability of the variant, default is UNKNOWN_ACTIONABILITY
   * - variant
     - :ref:`rstvariant`
     - 1..1
     - a genetic/genomic variant


.. _rstacmgPathogenicityClassification:

AcmgPathogenicityClassification
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
.. csv-table:: Definition  of the ``AcmgPathogenicityClassification`` enumeration
   :header: Name, Ordinal, Description

    NOT_PROVIDED, 0, The variant has not been subject to classification
    BENIGN, 1, This variant does not cause disease
    LIKELY_BENIGN, 2, This variant is not expected to have a major effect on disease. However, the scientific evidence is currently insufficient to prove this conclusively
    UNCERTAIN_SIGNIFICANCE, 3, There is not enough information at this time to support a more definitive classification of this variant
    LIKELY_PATHOGENIC, 4, There is a high likelihood (greater than 90% certainty) that this variant is disease-causing
    PATHOGENIC, 5,  This variant directly contributes to the development of disease


.. _rsttherapeuticactionability:

TherapeuticActionability
~~~~~~~~~~~~~~~~~~~~~~~~
.. csv-table:: Definition  of the ``TherapeuticActionability`` enumeration
   :header: Name, Ordinal, Description

    UNKNOWN_ACTIONABILITY, 0, There is not enough information at this time to support any therapeutic actionability for this variant
    NOT_ACTIONABLE, 1, This variant has no therapeutic actionability.
    ACTIONABLE, 2, This variant is known to be therapeutically actionable.

Example
#######

The following element shows how to denote an interpretation of a variant as pathogenic.

.. code-block:: yaml

    variantInterpretation:
      acmgPathogenicityClassification: "PATHOGENIC"
      variationDescriptor:
        expressions:
        - syntax: "hgvs"
          value: "NM_001848.2:c.877G>A"
        allelicState:
          id: "GENO:0000135"
          label: "heterozygous"


Explanations
############

acmg_pathogenicity_classification
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
The ACMG has recommended a five-tier classification system (`Richards et al., 2015 <https://pubmed.ncbi.nlm.nih.gov/25741868/>`_).

- Benign (BENIGN): This variant does not cause disease.
- Likely benign (LIKELY_BENIGN): This variant is not expected to have a major effect on disease; however, the scientific evidence is currently insufficient to prove this conclusively.
- Uncertain significance (UNCERTAIN_SIGNIFICANCE): There is not enough information at this time to support a more definitive classification of this variant.
- Likely pathogenic (LIKELY_PATHOGENIC): There is a high likelihood (greater than 90% certainty) that this variant is disease-causing.
- Pathogenic (PATHOGENIC): This variant directly contributes to the development of disease.

In the case that the variant has not been subject to classification, the value 'NOT_PROVIDED' MUST be used.

therapeutic_actionability
~~~~~~~~~~~~~~~~~~~~~~~~~
An enumeration flagging the variant as being a candidate for treatment/ clinical intervention of the disorder caused by
this variant, which could improve the clinical outcome.

variation_descriptor
~~~~~~~~~~~~~~~~~~~~
The subject of the variant interpretation. See :ref:`rstvariant` for more information.