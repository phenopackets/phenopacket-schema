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
   :widths: 25 25 50 50
   :header-rows: 1

   * - Field
     - Type
     - Status
     - Description
   * - variantFinding
     - enum :ref:`rstvariantfinding`
     - required
     - one of the five ACMG pathogenicity categories, default is UNCERTAIN_SIGNIFICANCE
   * - variant
     - :ref:`rstvariant`
     - required
     - a genetic/genomic variant


.. _rstvariantfinding:
VariantFinding
~~~~~~~~~~~~~~
.. csv-table:: Definition  of the ``VariantFinding`` enumeration
   :header: Name, Ordinal, Description

    UNCERTAIN_SIGNIFICANCE, 0, There is not enough information at this time to support a more definitive classification of this variant
    PATHOGENIC, 1,  This variant directly contributes to the development of disease
    LIKELY_PATHOGENIC, 2, There is a high likelihood (greater than 90% certainty) that this variant is disease-causing
    LIKELY_BENIGN, 3, This variant is not expected to have a major effect on disease; however, the scientific evidence is currently insufficient to prove this conclusively
    BENIGN, 4, This variant does not cause disease


Example
#######

The following element shows how to denote an interpretation of a variant as pathogenic.

.. code-block:: yaml

  variantInterpretation:
    variantFinding: "PATHOGENIC"
    variant:
        hgvsAllele:
            hgvs: "NM_001848.2:c.877G>A"
        zygosity:
            id: "GENO:0000135"
            label: "heterozygous"

Explanations
############

variantFinding
~~~~~~~~~~~~~~
The ACMG has recommended a five-tier classification system (`Richards et al., 2015 <https://pubmed.ncbi.nlm.nih.gov/25741868/>`_).


- Pathogenic (PATHOGENIC): This variant directly contributes to the development of disease.
- Likely pathogenic (LIKELY_PATHOGENIC): There is a high likelihood (greater than 90% certainty) that this variant is disease-causing.
- Uncertain significance (UNCERTAIN_SIGNIFICANCE): There is not enough information at this time to support a more definitive classification of this variant.
- Likely benign (LIKELY_BENIGN): This variant is not expected to have a major effect on disease; however, the scientific evidence is currently insufficient to prove this conclusively.
- Benign (BENIGN): This variant does not cause disease.

variant
~~~~~~~
The subject of the variant interpretation. See :ref:`rstvariant` for more information.