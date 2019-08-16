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
     - identifier of the gene
   * - symbol
     - string
     - required
     - Official gene symbol


**Example**

.. code-block:: json

  {
    "id": "HGNC:347",
    "symbol": "ETF1"
  }


id
~~
The id represents an accession number of comparable identifier for the gene. It is often a CURIE with a prefix that
is NCBIGene or HGNC (e.g. `HGNC:347 <https://www.genenames.org/data/gene-symbol-report/#!/hgnc_id/HGNC:3477>`_), but
may also be an identifier of the form `ENSG00000120705 <http://useast.ensembl.org/Homo_sapiens/Gene/Summary?db=core;g=ENSG00000120705;r=5:138506095-138543236>`_
or , `uc003ldc.6 <http://genome.ucsc.edu/cgi-bin/hgTracks?db=hg38&lastVirtModeType=default&lastVirtModeExtraState=&virtModeType=default&virtMode=0&nonVirtPosition=&position=chr5%3A138506099%2D138543300&hgsid=740830709_Y6BD9QmLx9YvUSbMY4BiFV8tAwre>`_.


symbol
~~~~~~
This SHOULD use official gene symbol as designated by the organism gene nomenclature committee. In the case of human
this is the `HUGO Gene Nomenclature Committee <https://www.genenames.org>`_ e.g. `ETF1 <https://www.genenames.org/data/gene-symbol-report/#!/hgnc_id/HGNC:3477>`_.

