.. _rstsex:

===
Sex
===

An enumeration used to represent the sex of an individual.
This element does not represent gender identity, but instead represents
"Administrative sex", which is used to indicate the sex a person has listed with
their insurance company.

**Data model**

.. csv-table::
   :header: Name, Description

    UNKNOWN_SEX,  Not assessed or not available.
    FEMALE, female sex.
    MALE, male sex.
    OTHER_SEX, It is not possible to accurately assess the applicability of MALE/FEMALE.

**Example**

.. code-block:: json

    {
        "sex": "UNKNOWN_SEX"
    }

FHIR mapping
~~~~~~~~~~~~
`AdministrativeGender <https://www.hl7.org/fhir/codesystem-administrative-gender.html>`_.
