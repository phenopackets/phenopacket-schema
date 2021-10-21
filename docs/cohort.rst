.. _rstcohort:

######
Cohort
######


This element describes a group of individuals related in some phenotypic or genotypic aspect. For instance, a cohort
can consist of individuals all of whom have been diagnosed with a certain disease or have been found to have a certain
phenotypic feature.

We recommend using the :ref:`rstfamily` element to describe families being investigated for the presence of a Mendelian
disease.

The GA4GH is in the process of creating a Computable Cohort Working Group to define how a cohort is generated. It is the
intention that the results of this definition should be collected into a `Cohort` message.

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
   * - files
     - :ref:`rstfile`
     - 0..*
     - list of files related to the whole cohort, e.g. multi-sample high-throughput sequencing files
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

files
~~~~~
This element contains a list of pointers to relevant file(s) for the cohort. The file(s) MUST refer to the entire cohort. Otherwise
individual files MUST be contained within their appropriate scope. e.g. within a ``Phenopacket`` for germline samples of
an individual or within the scope of the ``Phenopacket.Biosample`` in the case of data derived from that biosample.

See :ref:`rstfile` for further information.

meta_data
~~~~~~~~
This element contains structured definitions of the resources and ontologies used within the phenopacket.
It is expected that every valid Phenopacket contains a metaData element.
See :ref:`rstmetadata` for further information.



