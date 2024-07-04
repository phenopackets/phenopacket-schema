.. _rstgene:

##############
GeneDescriptor
##############


This element represents an identifier for a gene, using the Gene Descriptor from the
`VRSATILE Framework <https://vrsatile.readthedocs.io/en/latest/value_object_descriptor/vod_index.html#gene-descriptor>`_.
Gene Descriptors can be used to transmit the information that the gene is thought to play a
causative role in the disease phenotypes being described in cases where the exact variant cannot
be transmitted, either for privacy reasons or because it is unknown.

Gene Descriptors may also be used to contextualize variants described in a :ref:`rstvariant`.

Data model
##########

.. list-table:: Definition  of the ``GeneDescriptor`` element
   :widths: 25 25 25 75
   :header-rows: 1

   * - Field
     - Type
     - Multiplicity
     - Description
   * - value_id
     - string
     - 1..1
     - Official identifier of the gene. REQUIRED.
   * - symbol
     - string
     - 1..1
     - Official gene symbol. REQUIRED.
   * - description
     - string
     - 0..1
     - A free-text description of the gene
   * - alternate_ids
     - list of string
     - 0..*
     - Alternative identifier(s) of the gene
   * - xrefs
     - list of string
     - 0..*
     - Related concept IDs (e.g. gene ortholog IDs) may be placed in xrefs
   * - alternate_symbols
     - list of string
     - 0..*
     - Alternative symbol(s) of the gene

Example
#######

.. code-block:: yaml

    geneDescriptor:
      valueId: "HGNC:3477"
      symbol: "ETF1"

Optionally, with alternative identifiers:

.. code-block:: yaml

    geneDescriptor:
      valueId: "HGNC:3477"
      symbol: "ETF1"
      alternateIds:
      - "ensembl:ENSG00000120705"
      - "ncbigene:2107"
      - "ucsc:uc003ldc.6"
      - "OMIM:600285"

Using the gene descriptor to convey alternate identifiers, symbols and orthologs:

.. code-block:: yaml

    geneDescriptor:
      valueId: "HGNC:3477"
      symbol: "ETF1"
      alternateIds:
      - "ensembl:ENSG00000120705"
      - "ncbigene:2107"
      - "ucsc:uc003ldc.6"
      - "OMIM:600285"
      alternateSymbols:
      - "SUP45L1"
      - "ERF1"
      - "ERF"
      - "eRF1"
      - "TB3-1"
      - "RF1"
      xrefs:
      - "VGNC:97422"
      - "MGI:2385071"
      - "RGD:1305712"
      - "ensembl:ENSRNOG00000019450"
      - "ncbigene:307503"

Explanations
############

value_id
~~~~~~~~
The id represents the accession number of comparable identifier for the gene.

It SHOULD be a :ref:`rstcurie` identifier with a prefix that is used by the official organism gene nomenclature committee. In
the case of Humans, this is the `HGNC <https://www.genenames.org/>`_, e.g. `HGNC:347 <https://www.genenames.org/data/gene-symbol-report/#!/hgnc_id/HGNC:3477>`_

symbol
~~~~~~
This SHOULD use official gene symbol as designated by the organism gene nomenclature committee. In the case of human
this is the `HUGO Gene Nomenclature Committee <https://www.genenames.org>`_ e.g. `ETF1 <https://www.genenames.org/data/gene-symbol-report/#!/hgnc_id/HGNC:3477>`_.

description
~~~~~~~~~~~
A free-text description of the value object. This should be only used to convey information which is otherwise not
possible to encode using the schema.

alternate_ids
~~~~~~~~~~~~~

This field can be used to provide identifiers to alternative resources where this gene is used or catalogued. For example,
the NCBI and Ensemble both provide alternative identifiers for genes where they catalogue the transcripts for a gene e.g.
`ncbigene:2107 <https://www.ncbi.nlm.nih.gov/gene/2107>`_, `ensembl:ENSG00000120705 <http://useast.ensembl.org/Homo_sapiens/Gene/Summary?db=core;g=ENSG00000120705;r=5:138506095-138543236>`_
These identifiers SHOULD be represented in :ref:`rstcurie` form with a corresponding :ref:`rstresource`.

alternate_symbols
~~~~~~~~~~~~~~~~~
This field can be used to list the alternate symbols used to refer to the gene. These include the previously approved
gene symbols and those used in the literature or other databases to refer to the gene.

xrefs
~~~~~
This field can be used to provide identifiers to alternative resources representing related, but not equivalent concepts,
for example gene ortholog ids
