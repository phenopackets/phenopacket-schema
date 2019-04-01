.. _rstsex:

===
Sex
===

An enumeration used to represent the sex of an individual.
This element does not represent gender identity, but instead represents
"Administrative sex", which is used to indicate the sex a person has listed with
their insurance company.

.. code:: proto

  enum Sex {
    UNKNOWN_SEX = 0;
    FEMALE = 1;
    MALE = 2;
    OTHER_SEX = 3;
  }


The meaning of the items is:

1. UNKNOWN_SEX.  Not assessed or not available.
2. FEMALE. female sex.
3. MALE. male sex.
4. OTHER_SEX. It is not possible, to accurately assess the applicability of MALE/FEMALE.


FHIR mapping
~~~~~~~~~~~~
`AdministrativeGender <https://www.hl7.org/fhir/codesystem-administrative-gender.html>`_.
