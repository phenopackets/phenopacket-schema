.. _rstgeneinterpretation:

##################
GeneInterpretation
##################

This element represents the interpretation of a gene. Users may choose to use
this element instead of a :ref:`rstvariantinterpretation` if they are not able
to reveal the actual variant that was detected for privacy reasons, for instance.




Data model
##########

.. list-table:: Definition  of the ``GeneInterpretation`` element
   :widths: 25 25 50 50
   :header-rows: 1

   * - Field
     - Type
     - Status
     - Description
   * - geneFinding
     - enum GeneFinding
     - required
     - whether the gene is interpreted to possibly (CANDIDATE) or definitely (CONTRIBUTORY) be related to the diagnosis
   * - gene
     - :ref:`rstgene`
     - required
     - A gene interpreted to be related to a diagnosis.



Example
#######

The following element shows how to denote an interpretation of a gene as a candidate for
a disorder. This could be used for genomic MatchMaking (see `GeneMatcher <https://pubmed.ncbi.nlm.nih.gov/26220891/>`_).


.. code-block:: yaml

    geneInterpretation:
        gene:
            id: "HGNC:347"
            symbol: "ETF1"

TODO -- check generation of this code






