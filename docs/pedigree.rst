.. _rstpedigree:

========
Pedigree
========



This element is used to represent a pedigree to describe the family relationships of each sample along with their
gender and phenotype. Phenopackets uses a PED-like data structure  (See the detailed description
at the `PLINK <http://zzz.bwh.harvard.edu/plink/data.shtml>`_ website for more information). PED files are typically
used by software for genetic linkage analysis.

This message represents the core pedigree information which can be used by programs for analysis of a multi-sample VCF
file with exome or genome sequences of members of a family, some of whom are affected by a Mendelian disease.

**Data model**

.. list-table::
   :widths: 25 25 50 50
   :header-rows: 1

   * - Field
     - Type
     - Status
     - Description
   * - persons
     - list of :ref:`rstperson`
     - required
     - list of family members in this pedigree


The pedigree is simply a list of Person objects. These objects are meant to reflect the elements of
a PED file.

.. _rstperson:

Person
~~~~~~


.. list-table:: Definition of the ``Person`` element
   :widths: 25 25 50 50
   :header-rows: 1

   * - Field
     - Type
     - Status
     - Description
   * - family_id
     - string
     - required
     - application specific identifier
   * - individual_id
     - string
     - required
     - application specific identifier
   * - paternal_id
     - string
     - required
     - application specific identifier
   * - maternal_id
     - string
     - required
     - application specific identifier
   * - sex
     - :ref:`rstsex`
     - required
     - see text
   * - affected_status
     - :ref:`rstaffectedstatus`
     - required
     - see text


.. _rstaffectedstatus:

AffectedStatus
~~~~~~~~~~~~~~

This element is an enumeration to

.. csv-table::
   :header: Name, Description

   MISSING, 0
   UNAFFECTED, 1
   AFFECTED, 2


The message is made up of a list of ``Person`` elements (the Person element is defined within the Pedigree element).
Each Person element is equivalent to one row of a PED file.

The family ID and the individual IDs may be made up of letters and digits, and the combination of
family and individual ID should uniquely identify each person represented in the PED file. The
parents of a person in the pedigree are shown with the corresponding individual
IDs. Individuals whose parents are not represented in the PED file are known
as founders; their parents are represented by a zero (0) in the
columns for mother and father. Finally, the sex and the phenotype (disease)
status are shown in columns 5 and 6.

If a ``Phenopacket`` is used to represent any of the
individuals listed in the ``Pedigree``, then is essential that the ``individual_id`` used in the
pedigree matches the ``id`` of the ``Phenopacket``. It is allowable for the ``Pedigree`` to
have individuals that do not have an associated ``Phenopacket``. This is useful, for instance, if the ``Pedigree``
is being used to store the affected/not affected status of family members being examined by exome or genome
sequencing. In this case, it is expected that the ``individual_id`` elements match the sample identifiers
of the exome/genome file.

The Pedigree object  does not support reporting multiple phenotypes in one individual.
The phenotype represented by the affectation status is whether the disease is present or not.
If this is desired, then one would have to create full phenopackets for each individual in a family.





