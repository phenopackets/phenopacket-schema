.. _rstvalue:

#####
Value
#####

The value element is meant to be used as part of the :ref:`rstmeasurement` element, and it
represents the outcome of a measurment.


Data model
##########


.. list-table:: Definition  of the ``Value`` element
   :widths: 25 25 25 75
   :header-rows: 1

   * - Field
     - Type
     - Multiplicity
     - Description
   * - value
     - {:ref:`rstquantity` | :ref:`rstontologyclass`}
     - 1..1
     - the outcome (value) of a measurement. REQUIRED.

Examples
########

The following example shows a Value used for a quantitative measurement.

.. code-block:: yaml


    value:
        quantity:
            unit:
                id: "UO:0000316"
                label: "cells per microliter"
            value: 24000.0
            referenceRange:
                unit:
                    id: "UO:0000316"
                    label: "cells per microliter"
                low: 150000.0
                high: 450000.0


The following example shows a Value used for an ordinal measurement.

.. code-block:: yaml

    value:
        ontologyClass:
            id: "NCIT:C25626"
            label: "Present"


Explanations
############

See :ref:`rstquantity` for explanations of how to contruct that element. For ordinal measurements,
the following terms may be useful.


.. csv-table::
   :header: Term, ID
   :align: left

   Present, `NCIT:C25626 <https://www.ebi.ac.uk/ols/ontologies/ncit/terms?iri=http%3A%2F%2Fpurl.obolibrary.org%2Fobo%2FNCIT_C25626>`_
   Absent, `NCIT:C48190 <https://www.ebi.ac.uk/ols/ontologies/ncit/terms?iri=http%3A%2F%2Fpurl.obolibrary.org%2Fobo%2FNCIT_C48190>`_
   Abnormal, `NCIT:C25401 <https://www.ebi.ac.uk/ols/ontologies/ncit/terms?iri=http%3A%2F%2Fpurl.obolibrary.org%2Fobo%2FNCIT_C25401>`_
   Elevated, `NCIT:C25493 <https://www.ebi.ac.uk/ols/ontologies/ncit/terms?iri=http%3A%2F%2Fpurl.obolibrary.org%2Fobo%2FNCIT_C25493>`_
   Reduced, `NCIT:C25640 <https://www.ebi.ac.uk/ols/ontologies/ncit/terms?iri=http%3A%2F%2Fpurl.obolibrary.org%2Fobo%2FNCIT_C25640>`_


