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


**Data model**


 .. list-table:: Definition of the ``Family`` element
   :widths: 25 25 50 50
   :header-rows: 1

   * - Field
     - Type
     - Status
     - Description
   * - id
     - string
     - required
     - application-specific identifier
   * - proband
     - :ref:`rstphenopacket`
     - required
     - The proband (index patient) in the family
   * - relatives
     - :ref:`rstphenopacket` (list)
     - optional
     - list of Phenopackets for family members other than the proband
   * - pedigree
     - :ref:`rstpedigree`
     - required
     - representation of the pedigree
   * - hts_files
     - :ref:`rstfile` (list)
     - optional
     - High-throughput sequencing files obtained from members of the family 
   * - meta_data
     - :ref:`rstmetadata`
     - required
     - Metadata about ontologies and references used in this message



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
the cohort. If this field is used, then it is expected that a pedigree will be included for genetically related individuals
for use cases such as genomic diagnostics. If all that is required is to record affected/not-affected status in a family,
it is possible to use the pedigree element only.


pedigree
~~~~~~~~
The pedigree defining the relations between the proband and their relatives. This element
contains information compatible with the information in a PED file. Pedigree.individual_id MUST
map to the PhenoPacket.Individual.id. See :ref:`rstpedigree` for further information.

hts_files
~~~~~~~~~
This element contains a list of pointers to the relevant HTS file(s) for the family as a whole. For a ``Family`` these
files MUST be merged/multi-sample files with appropriate genotype information. For a multi-sample file, the sample
identifiers MUST each map to a ``Pedigree.individual_id`` referenced in the ``pedigree`` field, in order that linkage
analysis can be performed on the sample.

See :ref:`rstfile` for further information.


meta_data
~~~~~~~~
This element contains structured definitions of the resources and ontologies used within the phenopacket.
It is expected that every valid Phenopacket contains a metaData element.
See :ref:`rstmetadata` for further information.



