.. _rstfile:

####
File
####

The File message allows a Phenopacket to link the structured phenotypic data it contains to external files which can be
used to inform analyses. For example the file could link to sequencing data in `VCF format <https://www.ncbi.nlm.nih.gov/pubmed/21653522>`_ as well
as other data types.

Given that ``File`` elements are listed in various locations such as the ``Phenopacket``, ``Biosample``, ``Family`` etc.
which can in turn be nested, individual files MUST be contained within their appropriate scope.
For example within a ``Phenopacket`` for germline samples of an individual or within the scope of the ``Phenopacket.Biosample``
in the case of genomic data derived from sequencing or other characterisation of that biosample. Aggregate data types
such as ``Cohort`` and ``Family`` MUST contain aggregate file data i.e. merged/multi-sample VCF at the level of the Family/Cohort, but each member
Phenopacket can contain its own locally-scope files pertaining to that individual/biosample(s).


File
~~~~
This message is used for information about a file.

.. list-table::
    :widths: 25 25 25 75
    :header-rows: 1

    * - Field
      - Type
      - Multiplicity
      - Description
    * - uri
      - string
      - 1..1
      - A valid URI e.g. file://data/file1.vcf.gz or https://opensnp.org/data/60.23andme-exome-vcf.231?1341012444. REQUIRED.
    * - individual_to_file_identifiers
      - a map of string key: value
      - 0..1
      - The mapping between the Individual.id or Biosample.id to any identifier in the file. RECOMMENDED.
    * - file_attributes
      - a map of string key: value
      - 0..1
      - A map of attributes pertaining to the file or its contents.

**Example**

The message below shows how a compressed VCF file should be specified. It indicates the sample identifier `NA12345` in
the VCF file is equivalent to the Phenopacket individual identifier `patient23456`. The `fileAttributes` indicate that
the file was called against the GRCh38 genome assembly, indicates the `fileFormat` is VCF and also provides a human-readable
`description`.

.. code-block:: yaml

    file:
      uri: "file://data/genomes/germline_wgs.vcf.gz"
      individualToFileIdentifiers:
        patient23456: "NA12345"
      fileAttributes:
        genomeAssembly: "GRCh38"
        fileFormat: "vcf"
        description: "Matched normal germline sample"


uri
~~~
URI for the file e.g. `file://data/genomes/file1.vcf.gz` or `https://opensnp.org/data/60.23andme-exome-vcf.231?1341012444`.

individual_to_file_identifiers
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
A map of identifiers mapping an individual referred to in the Phenopacket
to an identifier used in the file.
The key values must correspond to the Individual::id for the individuals in the message or Biosample::id for biosamples,
the values must map to identifiers in the file.

file_attributes
~~~~~~~~~~~~~~~
A map of attributes which a resource might want to share about the contents of a file. For example a file containing
genomic coordinates (e.g. VCF, BED) SHOULD contain an entry with the key `genomicAssembly` and a value indicating the
genome assembly the contents of the file was called against. We recommend using the
`Genome Reference Consortium <https://www.ncbi.nlm.nih.gov/grc>`_ nomenclature e.g. `GRCh37`, `GRCh38`.