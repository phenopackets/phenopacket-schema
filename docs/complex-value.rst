.. _rstcomplexquantity:

#############
Complex Value
#############

This element is intended for complex measurements, such as blood pressure where more than one component quantity is required to describe the
measurement.




Data model
##########

.. csv-table::
   :header: Field, Type, Status, Description

   typed_quantities, TypedQuantity, required, list of quantities required to fully describe the complex value.

.. _rsttypedquantity:

##############
Typed Quantity
##############

The Complex Value element consists of a list of ``TypedQuantity`` elements.

Data model
##########

.. csv-table::
   :header: Field, Type, Status, Description

   type, OntologyClass, required, :ref:`rstontologyclass` to describe the type of the measurement.
   quantity, Quantity, required, :ref:`rstquantity` denoting the outcome of the measurement




Example
#######

The following example shows a ComplexQuantity message for diastolic blood pressure.
The intended use case for a ComplexQuantity message is as a component of a :ref:`rstmeasurement`
message that contains two or more components (e.g., systolic and diastolic blood pressure).


.. code-block:: yaml

    complexValue:
        typedQuantities:
        - type:
            id: "NCIT:C25298"
            label: "Systolic Blood Pressure"
          quantity:
            unitClass:
                id: "NCIT:C49670"
                label: "Millimeter of Mercury"
          value: 120.0
        - type:
            id: "NCIT:C25299"
            label: "Diastolic Blood Pressure"
          quantity:
            unitClass:
                id: "NCIT:C49670"
                label: "Millimeter of Mercury"
            value: 70.0

