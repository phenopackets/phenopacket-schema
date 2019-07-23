.. _rstfile:

================
HtsFile
================

Phenopackets can be used to hold phenotypic information that can inform the analysis of
sequencing data in `VCF format <https://www.ncbi.nlm.nih.gov/pubmed/21653522>`_ as well
as other high-throughput sequencing (HTS) or other data types. The HtsFile
message allows a Phenopacket to link HTS files with data.


HtsFile
~~~~~~~

.. list-table::
    :widths: 25 50 50 50
    :header-rows: 1

    * - Field
      - Type
      - Status
      - Description
    * - uri
      - string
      - required
      - A valid URI e.g. file://data/file1.vcf.gz or https://opensnp.org/data/60.23andme-exome-vcf.231?1341012444
    * - description
      - string
      - optional
      - arbitrary description of the file
    * - hts_format
      - :ref:`rsthtsformat`
      - required
      - VCF
    * - genome_assembly
      - string
      - required
      - e.g. GRCh38
    * - individual_to_sample_identifiers
      - a map of string key: value
      - recommended
      - The mapping between the Individual.id or Biosample.id to the sample identifier in the HTS file


.. _rsthtsformat:

HtsFormat
~~~~~~~~~
This message is used for a file in one of the `HTS formats <https://samtools.github.io/hts-specs>`_.


.. list-table::
    :widths: 25 100
    :header-rows: 1

    * - Field
      - Description
    * - UNKNOWN
      - An HTS file of unknown type.
    * - SAM
      - A SAM format file
    * - BAM
      - A BAM format file
    * - CRAM
      - A CRAM format file
    * - VCF
      - A VCF format file
    * - BCF
      - A BCF format file
    * - GVCF
      - A GVCF format file


**Example**

.. code-block:: json

    {
        "uri": "file://data/genomes/germline_wgs.vcf.gz",
        "description": "Matched normal germline sample",
        "htsFormat": "VCF",
        "genomeAssembly": "GRCh38",
        "individualToSampleIdentifiers": {
          "patient23456": "NA12345"
        }
    }


path
~~~~
Full system path to the file. e.g. ``/data/genomes/file1.vcf.gz``.

uri
~~~
URI for the file e.g. `file://data/genomes/file1.vcf.gz` or `https://opensnp.org/data/60.23andme-exome-vcf.231?1341012444`.

description
~~~~~~~~~~~
An arbitrary description of the file contents.

The `File` message MUST have at least one of `path` and `uri` and usually should just have one of the two (in exceptional
cases the same file might be referenced on a local file system and on the network).

hts_format
~~~~~~~~~~
This indicates which format the file has.

genome_assembly
~~~~~~~~~~~~~~~
The genome assembly the contents of this file was called against. We recommend using the
`Genome Reference Consortium <https://www.ncbi.nlm.nih.gov/grc>`_ nomenclature e.g. GRCh37, GRCh38.

individual_to_sample_identifiers
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
A map of identifiers mapping an individual refered to in the Phenopacket
to a sample in the file.
The key values must correspond to the Individual::id for the individuals in the message or Biosample::id for biosamples, the values must map to the
samples in the HTS file.
