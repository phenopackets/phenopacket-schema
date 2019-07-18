.. _rstpedigree:

========
Pedigree
========



This element is used to represent a pedigree file (PRD file). A PED file ("pedigree file") describes the family relationships of each sample along with their
gender and phenotype (See the detailed description
at the `PLINK <http://zzz.bwh.harvard.edu/plink/data.shtml>`_ website for more information).
PED files are typically used by software for genetic linkage analysis.
PED files have six mandatory columns, and additional optional columns (that are typically not used
in the context of genomic analysis).

This message represets the core Ped file information
that is used by many programs for analysis of a multi-sample VCF file with exome or genome sequences of members
of a family, some of whom are affected by a Mendelian disease.

.. code-block:: proto

  message Pedigree {
    repeated Person persons = 1;
    message Person {
        enum AffectedStatus {
            MISSING = 0;
            UNAFFECTED = 1;
            AFFECTED = 2;
        }
        string family_id = 1;
        string individual_id = 2;
        string paternal_id = 3;
        string maternal_id = 4;
        Sex sex = 5;
        AffectedStatus affected_status = 6;
    }
  }

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



 .. list-table::
 .. list-table:: Phenopacket requirements for the ``Person`` element
   :widths: 25 50 50
   :header-rows: 1

   * - Field
     - Example/Explanation
     - Status
   * - family_id
     - FAM001
     - required
   * - individual_id
     - A unique identifier for the sample
     - required
   * - paternal_id
     - The sample ID for the father of individual_id (or 0 if the father is not represented in the pedigree)
     - required
   * - maternal_id
     -  The sample ID for the mother of individual_id (or 0 if the mother is not represented in the pedigree)
     - required
   * - sex
     - 1 for male, 2 for female, 0 if unknown (the integers associated with the phenopacket Sex enumeration correspond to the conventional PED file codes)
     - required
   * - affected_status
     - 1 for unaffected, 2 for affected, 0 if unknown. (the integers associated with the phenopacket AffectedStatus enumeration correspond to the conventional PED file codes)
     - required




