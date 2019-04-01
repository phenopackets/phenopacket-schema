.. _rstprocedure:

=========
Procedure
=========

The Procedure element represents a clinical procedure performed on a subject in order to extract a biosample.

.. code:: proto

  message Procedure {
    OntologyClass code = 1;
    OntologyClass body_site = 2;
  }


code
~~~~
This element is an :ref:`rstontologyclass` that represents clinical procedure performed on a subject. For instance,
`Biopsy of Soft Palate <https://www.ebi.ac.uk/ols/ontologies/ncit/terms?iri=http%3A%2F%2Fpurl.obolibrary.org%2Fobo%2FNCIT_C51585>`_
would be represented as follows.


.. code-block:: proto

    procedure {
        code {
            id: "NCIT:C51585"
            label: "Biopsy of Soft Palate"
        }
    }


In cases where it is not possible to represent the procedure adequately with a sinlge
:ref:`rstontologyclass`, the body site should be indicated using a separate
ontology class. For instance, the following indicates a punch biopsy on the
skin of the forearm.

.. code-block:: proto

    procedure {
        code {
            id: "NCIT:C28743"
            label: "Punch Biopsy"
        }
        body_site {
            id: "UBERON:0003403"
            label: "skin of forearm"
        }
    }


If the Procedure element is used, it must contain a ``code`` element, but only need contain the
body_site element if needed.


 .. list-table:: Phenopacket requirements for the ``Procedure`` element
    :widths: 25 50 50
    :header-rows: 1

    * - Field
      - Example
      - Status
    * - code
      - see above
      - required
    * - body_site
      - See above
      - optional


FHIR mapping
~~~~~~~~~~~~
`Procedure <https://www.hl7.org/fhir/procedure.html>`_.
