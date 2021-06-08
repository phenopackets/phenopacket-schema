.. _rstpedigree:

########
Pedigree
########


This element is used to represent a pedigree to describe the family relationships of each sample along with their gender
and phenotype (affected status). The information in this element is for use by programs for analysis of a multi-sample
VCF file with exome or genome sequences of members of a family, some of whom are affected by a Mendelian disease.

The phenopacket schema has implemented a PED-compatible data-model to promote interoperability between existing PED files
and PED software, but does not actually store a PED file.

See the detailed description at the `PLINK <http://zzz.bwh.harvard.edu/plink/data.shtml>`_ website for more information
about PED files.


Data model
##########

.. list-table::
   :widths: 25 25 25 75
   :header-rows: 1

   * - Field
     - Type
     - Multiplicity
     - Description
   * - persons
     - list of :ref:`rstperson`
     - 1..*
     - list of family members in this pedigree. REQUIRED.


The pedigree is simply a list of Person objects. These objects reflect the elements of a PED file.

.. _rstperson:

Person
~~~~~~

The `Person` class represents a row from the PED file indicating the biological parents of the individual, their sex and
their :ref:`rstaffectedstatus`.

.. list-table:: Definition of the ``Person`` element
   :widths: 25 25 50 50
   :header-rows: 1

   * - Field
     - Type
     - Multiplicity
     - Description
   * - family_id
     - string
     - 1..1
     - application specific identifier. REQUIRED.
   * - individual_id
     - string
     - 1..1
     - application specific identifier. REQUIRED.
   * - paternal_id
     - string
     - 1..1
     - application specific identifier. REQUIRED.
   * - maternal_id
     - string
     - 1..1
     - application specific identifier. REQUIRED.
   * - sex
     - :ref:`rstsex`
     - 1..1
     - see text. REQUIRED.
   * - affected_status
     - :ref:`rstaffectedstatus`
     - 1..1
     - see text. REQUIRED.


Example
#######

Here we show a pedigree in PED format, this contains two male siblings which share an abnormal (affected) phenotype and
their two normal (unaffected) parents.

.. code-block::

  "family 1"    "kindred 1A"    "FATHER"    "MOTHER"    2   2
  "family 1"    "kindred 1B"    "FATHER"    "MOTHER"    2   2
  "family 1"    "MOTHER"      0   0   1   1
  "family 1"    "FATHER"      0   0   2   1


Below we show the same pedigree as a phenopacket `Pedigree` in YAML format.

.. code-block:: yaml

 pedigree:
  persons:
  - familyId: "family 1"
    individualId: "kindred 1A"
    paternalId: "FATHER"
    maternalId: "MOTHER"
    sex: "MALE"
    affectedStatus: "AFFECTED"
  - familyId: "family 1"
    individualId: "kindred 1B"
    paternalId: "FATHER"
    maternalId: "MOTHER"
    sex: "MALE"
    affectedStatus: "AFFECTED"
  - familyId: "family 1"
    individualId: "MOTHER"
    paternalId: "0"
    maternalId: "0"
    sex: "FEMALE"
    affectedStatus: "UNAFFECTED"
  - familyId: "family 1"
    individualId: "FATHER"
    paternalId: "0"
    maternalId: "0"
    sex: "MALE"
    affectedStatus: "UNAFFECTED"

.. _rstaffectedstatus:

AffectedStatus
~~~~~~~~~~~~~~

This element is an enumeration to

.. csv-table::
   :header: Name, Description

   MISSING, It is unknown if the individual has the affected phenotype
   UNAFFECTED, The individual does not show the affected phenotype of the proband
   AFFECTED, The individual has the affected phenotype of the proband

In a PED file, affected persons are encoded with "2", and unaffecteds by "1"
(a "0" is used if no information is available). Instead, Phenopackets uses an enumeration as shown in the table.

In a PED file, the sex of individuals is encoded as a "1" for females, "2" for males, and "0" for unknown. Phenopackets
uses :ref:`rstsex` instead.

The message is made up of a list of ``Person`` elements (the Person element is defined within the Pedigree element).
Each Person element is equivalent to one row of a PED file.

The family ID and the individual IDs may be made up of letters and digits, and the combination of
family and individual ID should uniquely identify each person represented in the PED file. The
parents of a person in the pedigree are shown with the corresponding individual IDs. Individuals whose parents are not
represented in the PED file are known as founders; their parents are represented by a zero ("0") in the
columns for mother and father. Finally, the sex and the affected (disease) status of the person are shown.

.. _pedigree_identifiers:

If a ``Phenopacket`` is used to represent any of the individuals listed in the ``Pedigree``, then it is essential that
the ``individual_id`` used in the pedigree matches the ``id`` of the ``subject`` of the ``Phenopacket``. It is allowable
for the ``Pedigree`` to have individuals that do not have an associated ``Phenopacket``. This is useful, for instance,
if the ``Pedigree`` is being used to store the affected/not affected status of family members being examined by exome or genome
sequencing. In this case (i.e. where there are no associated phenopackets for the ``Pedigree.individual_id``), it is
expected that the ``individual_id`` elements match the sample identifiers of the exome/genome file.





