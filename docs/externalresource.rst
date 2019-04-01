==========================
External Reference element
==========================


ToDo discuss intended use cases.


The protobuf code

.. code-block:: proto

    message ExternalReference {
        // e.g. ISBN, PMID:123456, DOI:...
        string id = 1;
        string description = 2;
    }


FHIR mapping (ExternalReference)
================================
This element is mapped to the FHIR element `Reference <https://www.hl7.org/fhir/references.html>`_.
