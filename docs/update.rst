.. _rstupdate:

######
Update
######

A class for holding data about an update event to a record. This is used to hold brief details about when it occurred
and optionally who or what made the update and any pertinent information regarding the content and/or reason for the
update. The class is used in the :ref:`rstmetadata` element.

Data model
##########


.. list-table:: Definition  of the ``Update`` element
   :widths: 25 25 25 75
   :header-rows: 1

   * - Field
     - Type
     - Multiplicity
     - Description
   * - timestamp
     - `ISO8601 UTC timestamp <https://en.wikipedia.org/wiki/ISO_8601>`_
     - 1..1
     - ISO8601 UTC timestamp at which this record was updated. REQUIRED.
   * - updated_by
     - string
     - 0..1
     - Information about the person/organisation/network that has updated the phenopacket.
   * - comment
     - string
     - 0..1
     - Textual comment about the changes made to the content and/or reason for the update.


Example
#######

.. code-block:: yaml

  update:
    timestamp: "2018-06-10T10:59:06Z"

Optionally, with extra information:

.. code-block:: yaml

  update:
    timestamp: "2018-06-10T10:59:06Z"
    updatedBy: "Julius J."
    comment: "added phenotypic features to individual patient:1"


Explanations
############

timestamp
~~~~~~~~~
An `ISO8601 UTC timestamp <https://en.wikipedia.org/wiki/ISO_8601>`_ for when this phenopacket was updated.

updated_by
~~~~~~~~~~
Information about the person/organisation/network that has updated the phenopacket.

comment
~~~~~~~
Textual comment about the changes made to the content and/or reason for the update. This should be a brief description
only, it is not the actual update.