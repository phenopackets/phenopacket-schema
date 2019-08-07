.. _rstsex:

===
Sex
===

An enumeration used to represent the sex of an individual.
This element does not represent gender identity or :ref:`rstkaryotypicsex`, but instead represents typical
"phenotypic sex", as would be determined by a midwife or physician at birth.

**Data model**

.. csv-table::
   :header: Name, Description

    UNKNOWN_SEX,  Not assessed or not available
    FEMALE, female sex
    MALE, male sex
    OTHER_SEX, It is not possible to accurately assess the applicability of MALE/FEMALE

**Example**

.. code-block:: json

    {
        "sex": "UNKNOWN_SEX"
    }
