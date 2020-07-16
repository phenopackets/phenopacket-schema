.. _rstquantity:

========
Quantity
========

This element is meant to denote quantities of items such as medications.



**Data model**


.. list-table:: Definition  of the ``Quantity`` element
   :widths: 25 25 50 50
   :header-rows: 1

   * - Field
     - Type
     - Status
     - Description
   * - unit
     - OntologyClass
     - required
     - The kind of unit.
   * - value
     - double
     - required
     - the  value of the quantity in the units  e.g. 2.0 mg



**Example**

.. code-block:: json

  {
    "unit":
        {
            "id": "NCIT:C28253",
            "label": "Milligram"
        },
    "value": 30
  }



unit
~~~~
Ontology terms for units can be taken from the National Cancer Institute Thesaurus (NCIT),
from the subhierarchy for `Unit of Measure (Code C25709) <https://www.ebi.ac.uk/ols/ontologies/ncit/terms?iri=http%3A%2F%2Fpurl.obolibrary.org%2Fobo%2FNCIT_C25709>`_.


value
~~~~~
The corresponding value of the quantity in the units. In the example, a ``Quantity`` element
for 30 mg is shown.