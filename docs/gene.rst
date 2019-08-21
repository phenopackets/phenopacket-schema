.. _rstgene:

====
Gene
====


This element represents an identifier for a gene. It can be used to transmit the information that
the gene is thought to play a causative role in the disease phenotypes being described in cases where
the exact variant cannot be transmitted, either for privacy reasons or because it is unknown.

**Data model**


.. list-table:: Definition  of the ``Gene`` element
   :widths: 25 25 50 50
   :header-rows: 1

   * - Field
     - Type
     - Status
     - Description
   * - id
     - string
     - required
     - Official identifier of the gene
   * - alternate_ids
     - repeated string
     - optional
     - Alternative identifier(s) of the gene
   * - symbol
     - string
     - required
     - Official gene symbol


**Example**

.. code-block:: json

  {
    "id": "HGNC:347"
    "symbol": "ETF1"
  }

Optionally, with alternative identifiers:

.. code-block:: json

  {
    "id": "HGNC:347",
    "alternate_ids": ["ensembl:ENSRNOG00000019450", "ncbigene:307503"],
    "symbol": "ETF1"
  }


id
~~
The id represents the accession number of comparable identifier for the gene.

It SHOULD be a :ref:`rstcurie` identifier with a prefix that is used by the official organism gene nomenclature committee. In
the case of Humans, this is the HGNC e.g. `HGNC:347 <https://www.genenames.org/data/gene-symbol-report/#!/hgnc_id/HGNC:3477>`_

alternate_ids
~~~~~~~~~~~~~

This field can be used to provide identifiers to alternative resources where this gene is used or catalogued. For example,
the NCBI and Ensemble both provide alternative identifiers for genes where they catalogue the transcripts for a gene e.g.
`ncbigene:2107 <https://www.ncbi.nlm.nih.gov/gene/2107>`_, `ensembl:ENSG00000120705 <http://useast.ensembl.org/Homo_sapiens/Gene/Summary?db=core;g=ENSG00000120705;r=5:138506095-138543236>`_
These identifiers SHOULD be represented in :ref:`rstcurie` form with a corresponding :ref:`rstresource`.

symbol
~~~~~~
This SHOULD use official gene symbol as designated by the organism gene nomenclature committee. In the case of human
this is the `HUGO Gene Nomenclature Committee <https://www.genenames.org>`_ e.g. `ETF1 <https://www.genenames.org/data/gene-symbol-report/#!/hgnc_id/HGNC:3477>`_.

Model Organisms
===============
Model organisms represented by the `Alliance of Genome Resources <https://www.alliancegenome.org>`_ should use the
primary identifier and symbol provided. e.g. for Mus musculus gene `eukaryotic translation termination factor 1 <https://www.alliancegenome.org/gene/MGI:2385071>`_

.. code-block:: json

  {
    "id": "MGI:2385071",
    "alternate_ids": ["ensembl:ENSMUSG00000024360", "ncbigene:225363"],
    "symbol": "Etf1"
  }


