.. _rstcomplexquantity:

================
Complex Quantity
================

This element is intended for complex measurements, such as blood pressure where more than one component quantity is required to describe the
measurement.


Data model
==========

.. csv-table::
   :header: Field, Type, Status, Description

   type, OntologyClass, required, Ontology term describing the measurement.
   quantity, Quantity, required, the result of measurement


Example
=======

The following example shows a ComplexQuantity message for diastolic blood pressure.
The intended use case for a ComplexQuantity message is as a component of a :ref:`rstmeasurement`
message that contains two or more components (e.g., systolic and diastolic blood pressure).
See :ref:`rstmeasurement` for an example.

.. code-block:: yaml

    complexQuantity:
        type:
            id: "NCIT:C25299"
            label: "Diastolic Blood Pressure"
        quantity:
            unitClass:
                id: "NCIT:C49670"
                label: "Millimeter of Mercury"
            value: 70.0