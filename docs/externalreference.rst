.. _rstexternalreference:

==========================
External Reference element
==========================


This element encodes the evidence type using, for example
the `Evidence & Conclusion Ontology (ECO) `http://purl.obolibrary.org/obo/eco.owl`_.


.. code-block:: proto

    message ExternalReference {
        string id = 1;
        string description = 2;
    }


id
~~
The syntax of the identifier is application specific. In many contexts, it is a CURIE \
that uniquely identifies the evidence source, e.g.,
**ISBN:978-3-16-148410-0** or **PMID:123456**.


description
~~~~~~~~~~~
An optional free text description of the evidence.



 .. list-table:: Phenopacket requirements for the ``Age`` element
   :widths: 25 50 50
   :header-rows: 1

    * - Field
      - Example
      - Status
    * - id
      - An arbitrary identifier.
      - required
    * - description
      - free text
      - optional



FHIR mapping (ExternalReference)
================================
This element is mapped to the FHIR element `Reference <https://www.hl7.org/fhir/references.html>`_.
