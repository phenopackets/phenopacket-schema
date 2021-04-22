.. _rsthtsfile:

#######
HtsFile
#######

Phenopackets can be used to hold phenotypic information that can inform the analysis of
sequencing data in `VCF format <https://www.ncbi.nlm.nih.gov/pubmed/21653522>`_ as well
as other high-throughput sequencing (HTS) or other data types. The HtsFile
message allows a Phenopacket to link HTS files with data.

Given that HtsFile elements are listed in various locations such as the ``Phenopacket``, ``Biosample``, ``Family`` etc.
which can in turn be nested, individual HTS files MUST be contained within their appropriate scope.
For example within a ``Phenopacket`` for germline samples of an individual or within the scope of the ``Phenopacket.Biosample``
in the case of genomic data derived from sequencing that biosample. Aggregate data types such as ``Cohort`` and ``Family``
MUST contain aggregate HTS file data i.e. merged/multi-sample VCF at the level of the Family/Cohort, but each member
Phenopacket can contain its own locally-scope HTS files pertaining to that individual/biosample(s).


HtsFile
~~~~~~~
This message is used for information about a high-throughput file.

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
    * - description
      - string
      - 0..1
      - arbitrary description of the file
    * - hts_format
      - :ref:`rsthtsformat`
      - 1..1
      - The format of the HTS file e.g. VCF. REQUIRED.
    * - genome_assembly
      - string
      - 1..1
      - The genome assembly against which the file was called e.g. GRCh38. REQUIRED.
    * - individual_to_sample_identifiers
      - a map of string key: value
      - 0..1
      - The mapping between the Individual.id or Biosample.id to the sample identifier in the HTS file. RECOMMENDED.


.. _rsthtsformat:

HtsFormat
~~~~~~~~~
This message is used for a file in one of the `HTS formats <https://samtools.github.io/hts-specs>`_.

.. csv-table:: Definition  of the ``HtsFormat`` enumeration
   :header: Name, Ordinal, Description

    UNKNOWN, 0, An HTS file of unknown type
    SAM, 1,  A SAM format file
    BAM, 2, A BAM format file
    CRAM, 3, A CRAM format file
    VCF, 4, A VCF format file
    BCF, 5, A BCF format file
    GVCF, 6, A GVCF format file
    FASTQ, 7, A FASTQ format file


**Example**

.. code-block:: yaml

    htsFile:
        uri: "file://data/genomes/germline_wgs.vcf.gz"
        description: "Matched normal germline sample"
        htsFormat: "VCF"
        genomeAssembly: "GRCh38"
        individualToSampleIdentifiers:
            patient23456: "NA12345"


uri
~~~
URI for the file e.g. `file://data/genomes/file1.vcf.gz` or `https://opensnp.org/data/60.23andme-exome-vcf.231?1341012444`.

description
~~~~~~~~~~~
An arbitrary description of the file contents.

The `HtsFile` message MUST have at least one of `path` and `uri` and usually should just have one of the two (in exceptional
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
