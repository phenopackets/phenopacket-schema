.. _rstcohort:

######
Cohort
######


This element describes a group of individuals related in some phenotypic or genotypic aspect. For instance, a cohort
can consist of individuals all of whom have been diagnosed with a certain disease or have been found to have a certain
phenotypic feature.

We recommend using the :ref:`rstfamily` element to describe families being investigated for the presence of a Mendelian
disease.


Data model
##########

.. list-table:: Definition of the ``Cohort`` element
   :widths: 25 25 50 50
   :header-rows: 1

   * - Field
     - Type
     - Multiplicity
     - Description
   * - id
     - string
     - 1..1
     - An arbitrary identifier for the cohort. REQUIRED
   * - description
     - string
     - 0..1
     - text description of the cohort
   * - members
     - :ref:`rstphenopacket`
     - 1..*
     - Phenopackets that represent members of the cohort. REQUIRED
   * - hts_files
     - :ref:`rstfile`
     - 0..*
     - High-throughput sequencing files obtained from members of the cohort
   * - meta_data
     - :ref:`rstmetadata`
     - 1..1
     - Metadata related to the ontologies and references used in this message. REQUIRED

Explanations
############

id
~~
The id is an identifier specific for this cohort. The syntax of the identifier is application specific.

description
~~~~~~~~~~~
Any information relevant to the study can be added here as free text.

members
~~~~~~~
One :ref:`phenopacket` is included for each member of the cohort.

hts_files
~~~~~~~~~
This element contains a list of pointers to the relevant HTS file(s) for the cohort. The HTS file MUST be a
multi-sample file referring to the entire cohort, if appropriate. Individual HTS files MUST otherwise be contained
within their appropriate scope. e.g. within a ``Phenopacket`` for germline samples of an individual or within the scope
of the ``Phenopacket.Biosample`` in the case of genomic data derived from sequencing that biosample.
Each element describes what type of file is meant (e.g., BAM file), which genome assembly was used for mapping,
as well as a map of samples and individuals represented in that file. It also contains a
URI element which refers to a file on a given file system or a resource on the web.

See :ref:`rstfile` for further information.

meta_data
~~~~~~~~
This element contains structured definitions of the resources and ontologies used within the phenopacket.
It is expected that every valid Phenopacket contains a metaData element.
See :ref:`rstmetadata` for further information.



