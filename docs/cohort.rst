.. _rstcohort:

======
Cohort
======


This element describes a group of individuals related in some phenotypic or genotypic aspect. It can be used to describe
a group of individuals being investigated in a genome-wide analysis study. We recommend using the :ref:`rstfamily` element
to describe families being investigated for the presence of a Mendelian disease.




.. code:: proto

    message Cohort {
        string id = 1;
        string description = 2;
        repeated Phenopacket members = 3;
        repeated org.phenopackets.schema.v1.core.HtsFile hts_files = 4;
        org.phenopackets.schema.v1.core.MetaData meta_data = 5;
    }

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
This element contains a list of pointers to the relevant HTS file(s) for the cohort. Each element
describes what type of file is meant (e.g., BAM file), which genome assembly was used for mapping,
as well as a map of samples and individuals represented in that file. It also contains a
File element which optionally refers to a file on a given file system or can be a URI that
refers to a resource on the web. See :ref:`rstfile` for further information.


metaData
~~~~~~~~
This element contains structured definitions of the resources and ontologies used within the phenopacket.
It is expected that every valid Phenopacket contains a metaData element.
See :ref:`rstmetadata` for further information.





 .. list-table:: Phenopacket requirements for the ``Cohort`` element
   :widths: 25 50 50
   :header-rows: 1

   * - Field
     - Example
     - Status
   * - id
     - arbitrary identifier
     - required
   * - description
     - arbitrary text
     - optional
   * - members
     - See :ref:`rstphenopacket`
     - required
   * - hts_files
     - See :ref:`rstfile`
     - optional
   * - meta_data
     - See :ref:`rstmetadata`
     - required
