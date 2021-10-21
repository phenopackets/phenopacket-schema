.. _rstmetadata:

########
MetaData
########


This element contains structured definitions of the resources and ontologies used within the phenopacket. It is considered to be a required element of a valid Phenopacket and application Q/C software should check this.

Data model
##########

  .. list-table:: Definition of the ``MetaData`` element
    :widths: 25 25 25 75
    :header-rows: 1

    * - Field
      - Type
      - Multiplicity
      - Description
    * - created
      - A Timestamp
      - 1..1
      - Representation of the time when this object was created, e.g., 2019-04-01T15:10:17.808Z
    * - created_by
      - string
      - 1..1
      - Name of person who created the phenopacket
    * - submitted_by
      - string
      - 0..1
      - Name of person who submitted the phenopacket
    * - resources
      - list of :ref:`rstresource`
      - 1..*
      - Ontologies used to create the phenopacket
    * - updates
      - list of :ref:`rstupdate`
      - 0..*
      - List of updates to the phenopacket
    * - phenopacket_schema_version
      - string
      - 1..1
      - schema version of the current phenopacket
    * - external_references
      - List of :ref:`rstexternalreference`
      - 0..*
      - (See text)

The `MetaData` element MUST have one :ref:`rstresource` element for each ontology or terminology whose
terms are used in the Phenopacket. For instance, if a MONDO term is used to specify the disease and
HPO terms are used to specify the phenotypes of a patient, then the `MetaData` element MUST have
one `Resource` element each for MONDO and HPO.

Example
#######

.. code-block:: yaml

  metadata:
    created: "2019-07-21T00:25:54.662Z"
    createdBy: "Peter R."
    resources:
        - id: "hp"
        name: "human phenotype ontology"
        url: "http://purl.obolibrary.org/obo/hp.owl"
        version: "2018-03-08"
        namespacePrefix: "HP"
        iriPrefix: "hp"
        - id: "geno"
        name: "Genotype Ontology"
        url: "http://purl.obolibrary.org/obo/geno.owl"
        version: "19-03-2018"
        namespacePrefix: "GENO"
        iriPrefix: "geno"
        - id: "pubmed"
        name: "PubMed"
        url: "https://www.ncbi.nlm.nih.gov/pubmed/"
        namespacePrefix: "PMID"
    phenopacketSchemaVersion: "2.0"
    externalReferences:
        - id: "PMID:30808312"
        description: "Bao M, et al. COL6A1 mutation leading to Bethlem myopathy with recurrent hematuria: a case report. BMC Neurol. 2019;19(1):32."



Explanations
############

created
~~~~~~~
This element is a `ISO8601 UTC timestamp <https://en.wikipedia.org/wiki/ISO_8601>`_ for when this phenopacket was
created in ISO, e.g.,  "2018-03-01T00:00:00Z".


created_by
~~~~~~~~~~
This is a string that represents an identifier for the contributor/ program. The expected syntax and semantics are application-dependent.


submitted_by
~~~~~~~~~~~~
This is a string that represents an identifier for the person who submitted the phenopacket (who may not be
the person who created the phenopacket).


resources
~~~~~~~~~
This element contains a listing of the ontologies/resources referenced in the phenopacket.


updates
~~~~~~~
This element contains a list of :ref:`rstupdate` objects which contain information about when, what and who updated
a phenopacket. This is only necessary when a phenopacket is being used as a persistent record and is being continuously
updated. Resources should provide information about how this is being used.

phenopacket_schema_version
~~~~~~~~~~~~~~~~~~~~~~~~~~
A string representing the version of the phenopacket-schema according to which a phenopacket was made. Permitted values
MUST be one of `1.0.0`, `1.0` or `2.0`. Versions `1.0.0` and `1.0` are equivalent and the `1.0` string should be
preferred. This version of the schema is `2.0`.

external_references
~~~~~~~~~~~~~~~~~~~
A list of :ref:`rstexternalreference` (such as the PubMed id of a publication from which a
phenopacket was derived).

