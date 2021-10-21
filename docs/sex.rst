.. _rstsex:

###
Sex
###

An enumeration used to represent the sex of an individual.
This element does not represent gender identity or :ref:`rstkaryotypicsex`, but instead represents typical
"phenotypic sex", as would be determined by a midwife or physician at birth.

Data model
##########

Implementation note - this is an enumerated type, therefore the values represented below are the only legal values. The
value of this type SHALL NOT be null, instead it SHALL use the 0 (zero) ordinal element as the default value, should none
be specified.

.. csv-table::
   :header: Name, Ordinal, Description

    UNKNOWN_SEX, 0, Not assessed or not available. Maps to `NCIT:C17998 <https://www.ebi.ac.uk/ols/ontologies/ncit/terms?iri=http%3A%2F%2Fpurl.obolibrary.org%2Fobo%2FNCIT_C17998>`_
    FEMALE, 1, Female sex. Maps to `NCIT:C46113 <https://www.ebi.ac.uk/ols/ontologies/ncit/terms?iri=http%3A%2F%2Fpurl.obolibrary.org%2Fobo%2FNCIT_C46113>`_
    MALE, 2, Male sex. Maps to `NCIT:C46112 <https://www.ebi.ac.uk/ols/ontologies/ncit/terms?iri=http%3A%2F%2Fpurl.obolibrary.org%2Fobo%2FNCIT_C46112>`_
    OTHER_SEX, 3, It is not possible to accurately assess the applicability of MALE/FEMALE. Maps to `NCIT:C45908 <https://www.ebi.ac.uk/ols/ontologies/ncit/terms?iri=http%3A%2F%2Fpurl.obolibrary.org%2Fobo%2FNCIT_C45908>`_

Example
#######

.. code-block:: json

    {
        "sex": "UNKNOWN_SEX"
    }
