.. _rstprocedure:

#########
Procedure
#########

The Procedure element represents a clinical procedure performed on a subject. For example a surgical or diagnostic
procedure such as a biopsy.

If the Procedure element is used, it must contain a ``code`` element, but only need contain the
body_site element if needed.

Data model
##########

.. csv-table::
   :header: Field, Type, Multiplicity, Description

    code, :ref:`rstontologyclass`, 1..1, clinical procedure performed. REQUIRED.
    body_site, :ref:`rstontologyclass`, 0..*, specific body site where the procedure was performed
    performed, :ref:`rsttimeelement`, 0..*, age/time when the procedure was performed

Example
#######

In this example, a skin biopsy from the skin of the forearm is perfomed on an individual who is
25 years old.

.. code-block:: yaml

    procedure:
        code:
            id: "NCIT:C28743"
            label: "Punch Biopsy"
        bodySite:
            id: "UBERON:0003403"
            label: "skin of forearm"
        performed:
            age:
                iso8601duration: "P25Y"




Explanations
############

.. _procedurecode:

code
~~~~
This element is an :ref:`rstontologyclass` that represents clinical procedure performed on a subject. For instance,
`Biopsy of Soft Palate <https://www.ebi.ac.uk/ols/ontologies/ncit/terms?iri=http%3A%2F%2Fpurl.obolibrary.org%2Fobo%2FNCIT_C51585>`_
would be represented as follows.


.. code-block:: json

    {
        "code": {
            "id": "NCIT:C51585",
            "label": "Biopsy of Soft Palate"
        }
    }

.. _procedurebodysite:

body site
~~~~~~~~~
In cases where it is not possible to represent the procedure adequately with a single
:ref:`rstontologyclass`, the body site should be indicated using a separate
ontology class. For instance, the following indicates a punch biopsy on the
skin of the forearm.

.. code-block:: json

    {
        "code" {
            "id": "NCIT:C28743",
            "label": "Punch Biopsy"
        },
        "bodySite" {
            "id": "UBERON:0003403",
            "label": "skin of forearm"
        }
    }
