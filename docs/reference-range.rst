.. _rstreferencerange:

###############
ReferenceRange
###############

This elements is provided to support the :ref:`rstmeasurement` element, which can be used to report a numerical
value such as `LOINC:26515-7 <https://loinc.org/26515-7/>`_, Platelets [#/volume] in Blood. The normal range for
circulating platelets is  150,000 to 450,000 platelets per microliter.


Data model
##########

.. csv-table::
   :header: Field, Type, Multiplicity, Description

   unit, :ref:`rstontologyclass`, 1..1, Ontology term describing the unit. REQUIRED.
   low, double, 1..1, lower range of normal. REQUIRED.
   high, double, 1..1, upper range of normal. REQUIRED.


Example
#######

There are several ontologies  that provide terms for units of measurement, including the
`Units of measurement ontology <https://www.ebi.ac.uk/ols/ontologies/uo>`_ and the
National Cancer Institute Thesaurus (NCIT),
from the subhierarchy for `Unit of Measure (Code C25709) <https://www.ebi.ac.uk/ols/ontologies/ncit/terms?iri=http%3A%2F%2Fpurl.obolibrary.org%2Fobo%2FNCIT_C25709>`_.


The following example shows the reference range for platelet count per microliter.

.. code-block:: yaml

  referenceRange:
    unit:
        id: "UO:0000316"
        label: "cells per microliter"
    low: 150000.0
    high: 450000.0

