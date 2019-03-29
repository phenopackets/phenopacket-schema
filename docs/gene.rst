.. _rstgene:

====
Gene
====


This element represents an indentifier for a gene. It can be used to transmit the information that
the gene is thought to play a causative role in the disease phenotypes being described in cases where
the exact variant cannot be transmitted, either for privacy reasons or because it is unknown. ::

    message Gene {
        string id = 1;
        string symbol = 2;
    }

id
~~
The id represents an accession number of comparable identifier for the gene. It is often a CURIE
with a prefix that is NCBIGene, HGNC, ENSEMBL, UCSC e.g.
`HGNC:347 <https://www.genenames.org/data/gene-symbol-report/#!/hgnc_id/HGNC:3477>`_, but may also be an
identifier of the form ENSG00000120705, uc003ldc.6.


symbol
~~~~~~
The official gene symbol as designated by the organism gene nomenclature committee e.g. ETF1 or Etf1.



 .. list-table:: Phenopacket requirements for the ``Gene`` element
    :widths: 25 50 50
    :header-rows: 1

    * - Field
      - Example
      - Status
    * - id
      - HGNC:347
      - required
    * - symbol
      - ETF1
      - required

