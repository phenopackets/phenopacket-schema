.. _rstexternalreference:

#################
ExternalReference
#################


This element encodes information about an external reference. One typical use case for this elements is
to provide a reference to a published article by showing its PubMed identifier as a part of
an :ref:`rstevidence` element.


Data model
##########


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


Example
#######

.. code-block:: yaml

    externalReference:
        id: "PMID:30962759"
        description: "Recurrent Erythema Nodosum in a Child with a SHOC2 Gene Mutation"


Explanations
############

id
~~
The syntax of the identifier is application specific. It is RECOMMENDED that this is a :ref:`rstcurie` that uniquely identifies
the evidence source, e.g. **ISBN:978-3-16-148410-0** or **PMID:123456**. However, it could be a URL/URI, or other
relevant identifier.

It is RECOMMENDED to use a :ref:`rstcurie` identifier. If one is used, it is RECOMMENDED that the corresponding
:ref:`rstresource` be provided in the :ref:`rstmetadata` element. For the above example, one would provide
an :ref:`rstresource` for PubMed (see the :ref:`rstmetadata` for this example).

description
~~~~~~~~~~~
An optional free text description of the evidence. In the example above, the title of a published article is shown.

