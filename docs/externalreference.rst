.. _rstexternalreference:

==================
External Reference
==================


This element encodes information about an external reference.


**Data model**


 .. list-table:: Definition of the ``ExternalReference`` element
    :widths: 25 25 50 50
    :header-rows: 1

    * - Field
      - Type
      - Status
      - Description
    * - id
      - string
      - required
      - An application specific identifier
    * - description
      - string
      - optional
      - An application specific description


**Example**

.. code-block:: json

   {
     "id": "PMID:30962759",
     "description": "Recurrent Erythema Nodosum in a Child with a SHOC2 Gene Mutation"
   }


id
~~
The syntax of the identifier is application specific. In many contexts, it is a CURIE \
that uniquely identifies the evidence source, e.g.,
**ISBN:978-3-16-148410-0** or **PMID:123456**.


description
~~~~~~~~~~~
An optional free text description of the evidence.





FHIR mapping (ExternalReference)
================================
This element is mapped to the FHIR element `Reference <https://www.hl7.org/fhir/references.html>`_.
