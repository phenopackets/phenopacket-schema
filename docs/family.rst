.. _rstfamily:

======
Family
======

Phenotype, sample and pedigree data required for a genomic diagnosis.
This element is equivalent to the Genomics England
`InterpretationRequestRD <https://github.com/genomicsengland/GelReportModels/blob/master/schemas/IDLs/org.gel.models.report.avro/5.0.0/InterpretationRequestRD.avdl>`_.


In many cases, genetic diagnostics of Mendelian and other disease is performed on a family basis in order
to check for cosegregation of candidate variants with a disease. Usually, one family member is designated
as the ``proband``, for instance, a child affected by a genetic disease might be the proband in a family.
Genomic diagnostics might involve genome sequencing of the child and his or her parents. In this case, the
``Family`` element would include a Phenopacket for the child (``proband`` element). If the parents themselves
display phenotypic findings relevant to the analysis, then Phenopackets are included for them (using the
``relatives`` element). If the parents do not display any relevant phenotypic findings, then it is not
necessary to include Phenopacket elements for them. Instead, their status as unaffected can be recorded
with the :ref:`rstpedigree` element.




.. code:: proto

    message Family {
        string id = 1;
        Phenopacket proband = 2;
        repeated Phenopacket relatives = 4;
        org.phenopackets.schema.v1.core.Pedigree pedigree = 5;
        repeated org.phenopackets.schema.v1.core.HtsFile hts_files = 9;
        org.phenopackets.schema.v1.core.MetaData meta_data = 10;
    }


id
~~
An identifier specific for this family.

proband
~~~~~~~
The individual representing the focus of this packet - e.g. the proband in rare disease cases or cancer patient.
See :ref:`rstindividual` for further information.


relatives
~~~~~~~~~
Individuals related in some way to the patient. For instance, the individuals may be genetically related or may
be members of a cohort. If this field is used, then  it is expected that a pedigree will be included for
genetically related individuals for use cases such as genomic diagnostics. If a phenopacket is being used to
describe one member of a cohort, then in general one phenopacket will be created for each of the individuals in
the cohort. If this field is used, then
it is expected that a pedigree will be included for genetically related individuals
for use cases such as genomic diagnostics. If all that is required
is to record affected/not-affected status in a family, it is possible to use the pedigree element only.


pedigree
~~~~~~~~
The pedigree defining the relations between the proband and their relatives. This element
contains information comaptible with the information in a PED file. Pedigree.individual_id MUST
ap to the PhenoPacket.Individual.id. See :ref:`rstpedigree` for further information.

hts_files
~~~~~~~~~
This element contains a list of pointers to the relevant HTS file(s) for the patient. Each element
describes what type of file is meant (e.g., BAM file), which genome assembly was used for mapping,
as well as a map of samples and individuals represented in that file. It also contains a
File element which optionally refers to a file on a given file system or can be a URI that
refers to a resource on the web. See :ref:`rstfile` for further information.


metaData
~~~~~~~~
This element contains structured definitions of the resources and ontologies used within the phenopacket.
It is expected that every valid Phenopacket contains a metaData element.
See :ref:`rstmetadata` for further information.





 .. list-table:: Phenopacket requirements for the ``Family`` element
   :widths: 25 50 50
   :header-rows: 1

   * - Field
     - Example
     - Status
   * - id
     - arbitrary identifier
     - required
   * - proband
     - See :ref:`rstphenopacket`
     - required
   * - relatives
     - See :ref:`rstphenotype`
     - optional
   * - pedigree
     - See :ref:`rstpedigree`
     - required
   * - hts_files
     - See :ref:`rstfile`
     - optional
   * - meta_data
     - See :ref:`rstmetadata`
     - required
