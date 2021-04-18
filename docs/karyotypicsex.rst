.. _rstkaryotypicsex:

#############
KaryotypicSex
#############

This enumeration represents the chromosomal sex of an individual.

Data model
##########

Implementation note - this is an enumerated type, therefore the values represented below are the only legal values. The
value of this type SHALL NOT be null, instead it SHALL use the 0 (zero) ordinal element as the default value, should none
be specified.

.. csv-table::
   :header: Name, Ordinal, Description

    UNKNOWN_KARYOTYPE, 0, Untyped or inconclusive karyotyping
    XX, 1, Female
    XY, 2, Male
    XO, 3, Single X chromosome only
    XXY, 4, Two X and one Y chromosome
    XXX, 5, Three X chromosomes
    XXYY, 6, Two X chromosomes and two Y chromosomes
    XXXY, 7, Three X chromosomes and one Y chromosome
    XXXX, 8, Four X chromosomes
    XYY, 9, One X and two Y chromosomes
    OTHER_KARYOTYPE, 10, None of the above types

