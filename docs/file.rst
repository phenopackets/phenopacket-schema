.. _rstfile:

================
File and HtsFile
================

Phenopackets can be used to hold phenotypic information that can inform the analysis of
sequencing data in `VCF format <https://www.ncbi.nlm.nih.gov/pubmed/21653522>`_ as well
as other high-throughput sequencing (HTS) or other data types. The Phenopacket provides to
messages that allow phenopackets to link to files with data.



File
~~~~


.. code-block:: proto

    message File {
        string path = 1;
        string uri = 2;
        string description = 3;
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


 .. list-table:: Phenopacket requirements for the ``File`` element
    :widths: 25 50 50
    :header-rows: 1

    * - Field
      - Example
      - Status
    * - path
      - /data/genomes/file1.vcf.gz
      - see text
    * - uri
      - https://opensnp.org/data/60.23andme-exome-vcf.231?1341012444
      - see text
    * - description
      - arbitrary description
      - optional



HtsFile
~~~~~~~
This message is ued for a file in one of the `HTS formats <https://samtools.github.io/hts-specs>`_.


.. code-block:: proto

    message HtsFile {

        enum HtsFormat {
            UNKNOWN = 0;
            SAM = 1;
            BAM = 2;
            CRAM = 3;
            VCF = 4;
            BCF = 5;
            GVCF = 6;
        }
        HtsFormat hts_format = 1;
        string genome_assembly = 2;
        map<string, string> individual_to_sample_identifiers = 3;
        File file = 4;
    }

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
The key values must correspond to the Individual::id for the individuals in the message, the values must map to the
samples in the file.

file
~~~~
A reference to the file.



 .. list-table:: Phenopacket requirements for the ``File`` element
    :widths: 25 50 50
    :header-rows: 1

    * - Field
      - Example
      - Status
    * - hts_format
      - VCF
      - required
    * - genome_assembly
      - GRCh38
      - required
    * - individual_to_sample_identifiers
      - see text
      - recommended
    * - file
      - reference to a `File` message
      - required
