.. _rstquantity:

########
Quantity
########

This element is meant to denote quantities of items such as medications. The unit of a dose
can be expressed with NCIT terms such as
`Milligram <https://www.ebi.ac.uk/ols/ontologies/ncit/terms?iri=http%3A%2F%2Fpurl.obolibrary.org%2Fobo%2FNCIT_C28253&viewMode=All&siblings=false>`_,
`Microgram <https://www.ebi.ac.uk/ols/ontologies/ncit/terms?iri=http%3A%2F%2Fpurl.obolibrary.org%2Fobo%2FNCIT_C48152&viewMode=All&siblings=false>`_,
or
`Unit <https://www.ebi.ac.uk/ols/ontologies/ncit/terms?iri=http%3A%2F%2Fpurl.obolibrary.org%2Fobo%2FNCIT_C44278&viewMode=All&siblings=false>`_.
The value should be expressed as a number.



Data model
##########


.. list-table:: Definition  of the ``Quantity`` element
   :widths: 25 25 25 75
   :header-rows: 1

   * - Field
     - Type
     - Multiplicity
     - Description
   * - unit
     - :ref:`rstontologyclass`
     - 1..1
     - The kind of unit. REQUIRED.
   * - value
     - double
     - 1..1
     - the value of the quantity in the units  e.g. 2.0 mg. REQUIRED.
   * - reference_range
     - :ref:`rstreferencerange`
     - 0..1
     - the normal range for the `value`



Examples
########

The following message could be used to represent the quantity corresponding to a 15 mg tablet of Meloxicam.

.. code-block:: yaml

  unit:
    id: "NCIT:C28253"
    label: "Milligram"
  value: 15.0

The following message could be used to represent the quantity corresponding to a bolus of 5000 units of Heparin.

.. code-block:: yaml

  unit:
    id: "NCIT:C44278"
    label: "Unit"
  value: 5000

The following example shows a quantity for a platelet count per microliter, with a reference range.

.. code-block:: yaml

  unit:
    id: "UO:0000316"
    label: "cells per microliter"
  value: 300000.0
  referenceRange:
    unit:
      id: "UO:0000316"
      label: "cells per microliter"
    low: 150000.0
    high: 450000.0

Explanations
############

unit
~~~~
Ontology terms for units can be taken from the National Cancer Institute Thesaurus (NCIT),
from the subhierarchy for `Unit of Measure (Code C25709) <https://www.ebi.ac.uk/ols/ontologies/ncit/terms?iri=http%3A%2F%2Fpurl.obolibrary.org%2Fobo%2FNCIT_C25709>`_.


value
~~~~~
The corresponding value of the quantity in the indicated units.