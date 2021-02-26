.. _rstexposure:

========
Exposure
========


This element is intended to be used as a component of the Phenopacket message. ::

    repeated org.phenopackets.schema.v1.core.Exposure exposures = 11;


This represents a list of environmental exposures.
The resources using these fields should define what this represents in their context.


**Data model**


.. list-table:: Definition  of the ``Exposure`` element
   :widths: 25 25 50 50
   :header-rows: 1

   * - Field
     - Type
     - Status
     - Description
   * - description
     - string
     - optional
     - Free-text description of the exposure.
   * - type
     - OntologyClass
     - required
     - The primary ontology class which describes the exposure.
   * - negated
     - bool
     - optional
     - Flag to indicate whether the exposure occurred or not
   * - route
     - OntologyClass
     - optional
     - Mechanism of the exposure
   * - modifiers
     - OntologyClass (repeated)
     - optional
     - Characteristics of the exposure
   * - occurrence
     - TimeElement
     - optional
     - When the exposure occurred
   * - evidence
     - Evidence (repeated)
     - required
     - Evidences for how the exposure was determined.


**Example**

.. code-block:: json

  {
    "description": "accidental arsenic ingestion from a herbal medicine product",
    "type":
        {
            "id": "ECTO:9000032",
            "label": "arsenic atom exposure"
        },
    "route":
        {
            "id": "NCIT:C28229",
            "label": "Ingestion"
        },
    "occurrence":
        {
            "timestamp": "2018-03-01T00:00:00Z"
        },
    "evidence":
         {
            "id": "ECO:0006154",
            "label": "self-reported patient statement evidence"
        }
  }


description
~~~~~~~~~~~
Free-text description of the exposure. Note this is not a acceptable place to document/describe the exposure
the type and onset etc... fields should be used for this purpose.


type
~~~~
The primary ontology class which describes the exposure (See :ref:`ontologyclass`).

Terms from
the `Environment Exposure Ontology (ECTO) <https://www.ebi.ac.uk/ols/ontologies/ecto>`_
can be used.

negated
~~~~~~~
Flag to indicate whether the exposure occurred or not. Default is 'false', in other words the exposure has
occurred. Therefore it is only required in cases to indicate that the exposure was deemed _not_ to have happened.
More formally, this modifier indicates the logical negation of the OntologyClass used in the 'type' field.
*CAUTION* It is imperative to check this field for correct interpretation of the exposure!

route
~~~~~
Mechanism of the exposure. Ontology terms from the
`NCIT <https://www.ebi.ac.uk/ols/ontologies/ncit>`_ can be used for this purpose.

modifiers
~~~~~~~~~
Ontology class describing the characteristics of the exposure.

occurrence
~~~~~~~~~~
When the exposure occurred.

evidence
~~~~~~~~
Evidences for how the exposure was determined.
