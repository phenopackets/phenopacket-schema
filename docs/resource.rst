.. _rstresource:

########
Resource
########


The ``Resource`` element is a description of an external resource used for referencing an object. For example the resource
may be an ontology such as the HPO or SNOMED or another data resource such as the HGNC or ClinVar.

A ``Resource`` is used to contain data used to expand :ref:`rstcurie` identifiers when used in an ``id`` field. This is
known as :ref:`rstidentifierresolution`.

The :ref:`rstmetadata` element uses one resource element to describe each resource that is referenced in the Phenopacket.

Data model
##########

.. csv-table::
   :header: Field, Type, Multiplicity, Description

   id, string, 1..1, Resource identifier. e.g. `hp`. REQUIRED.
   name, string, 1..1, Formal name of the resource or ontology e.g. human phenotype ontology. REQUIRED.
   namespace_prefix, 1..1, required, Namespace prefix of the resource e.g `HP`. REQUIRED.
   url, string, 1..1, Uniform Resource Locator of the resource. REQUIRED.
   version, string, 1..1, The version string for the ontology or resource 2018-03-08. REQUIRED.
   iri_prefix, string, 1..1, Internationalized Resource Identifier such as ``http://purl.obolibrary.org/obo/HP_``. REQUIRED.

Example
#######

For an ontology, the url SHALL point to the obo or owl file, e.g. This information can also be found at the EBI
`Ontology Lookup Service <https://www.ebi.ac.uk/ols/ontologies>`_

.. code-block:: yaml

  resource:
    id: "hp"
    name: "Human Phenotype Ontology"
    url: "http://www.human-phenotype-ontology.org"
    version: "2018-03-08"
    namespacePrefix: "HP"
    iriPrefix: "http://purl.obolibrary.org/obo/HP_"

Non-ontology resources which use CURIEs as their native identifiers should be treated in a similarly resolvable manner.

.. code-block:: yaml

  resource:
    id: "hgnc"
    name: "HUGO Gene Nomenclature Committee"
    url: "https://www.genenames.org"
    version: "2019-08-08"
    namespacePrefix: "HGNC"
    iriPrefix: "https://www.genenames.org/data/gene-symbol-report/#!/hgnc_id/"

Using this :ref:`rstresource` definition it is possible for software to resolve the identifier `HGNC:12805` to
https://www.genenames.org/data/gene-symbol-report/#!/hgnc_id/12805

Non-ontology resources which do *not* use CURIEs as their native identifiers MUST use the namespace from identifiers.org,
when present. For example the UniProt Knowledgebase (https://registry.identifiers.org/registry/uniprot)

.. code-block:: yaml

  resource:
    id: "uniprot"
    name: "UniProt Knowledgebase"
    url: "https://www.uniprot.org"
    version: "2019_07"
    namespacePrefix: "uniprot"
    iriPrefix: "https://purl.uniprot.org/uniprot/"


Using this :ref:`rstresource` definition it is possible for software to resolve the identifier `uniprot:Q8H0D3` to
https://purl.uniprot.org/uniprot/Q8H0D3

Explanations
############

id
~~
For OBO ontologies, the value of this string MUST always be the official OBO ID, which is always equivalent to the ID
prefix in lower case.
Examples: hp, go, mp, mondo
Consult http://obofoundry.org for a complete list.

For other resources which do not use native CURIE identifiers (e.g. SNOMED, UniProt, ClinVar), use the prefix in
`identifiers.org <http://identifiers.org/>`_.

name
~~~~
The name of the ontology referred to by the id element, for example, `The Human Phenotype Ontology`. For OBO Ontologies,
the value of this string SHOULD be the same as the title field on http://obofoundry.org

Other resources should use the official title for that resource. Note that this field is purely for information purposes
and software should not encode any assumptions.

url
~~~
For OBO ontologies, this MUST be the PURL, e.g. http://purl.obolibrary.org/obo/hp.owl or http://purl.obolibrary.org/obo/hp.obo

Other resources should link to the official or top-level url e.g. https://www.uniprot.org or https://www.genenames.org

version
~~~~~~~
The version of the resource or ontology used to make the annotation. For OBO ontologies, this SHALL be the versionIRI.
For other resources this should be the native version of the resource, e.g UniProt - "2019_08", DbSNP - "153" for
resources without release versions, this field should be left blank.

namespace_prefix
~~~~~~~~~~~~~~~~
The prefix used in the CURIE of an OntologyClass e.g. HP, MP, ECO for example an HPO term will have a CURIE like this
- HP:0012828 which should be used in combination with the iri_prefix to form a fully-resolvable IRI.

iri_prefix
~~~~~~~~~~

The full IRI prefix which can be used with the namespace_prefix and the OntologyClass::id to resolve to an IRI for a
term. Tools such as the curie-util (https://github.com/prefixcommons/curie-util) can utilise this to produce
fully-resolvable IRIs for an OntologyClass.


.. _rstcurie:

CURIE
~~~~~
The `CURIE <https://www.w3.org/TR/curie/>`_ is defined by the `W3C <https://www.w3.org/>`_ as a means of encoding a
"Compact URI". It is a simple string taking the form of colon (``:``) separated `prefix` and `reference` elements -
`prefix:reference` e.g. HP:0012828 or HGNC:12805.

It is RECOMMENDED to use CURIE identifiers where possible.

Not all resources use CURIEs as identifiers (e.g. SNOMED, UniProt, ClinVar, PubMed). In these cases it is often possible
to create a CURIE form of an identifier by using the prefix for that resource from `identifiers.org <http://identifiers.org/>`_.

Where no CURIE prefix is present in `identifiers.org <http://identifiers.org/>`_ it is possible for a Resource to define
a locally-scoped namespace, although if a Phenopacket is being shared publicly this is NOT recommended if the resource is
not publicly resolvable.

When using a CURIE identifier a corresponding :ref:`rstresource` SHALL also be included in the :ref:`rstmetadata` section.


.. _rstidentifierresolution:

Identifier resolution
~~~~~~~~~~~~~~~~~~~~~

A CURIE identifier can be resolved to an external resource using the :ref:`rstresource` element by looking-up the CURIE
`prefix` against the Resource::namespacePrefix and then appending the CURIE `reference` to the Resource::iriPrefix.

For example, software can use the HPO Resource shown above to resolve the following HPO term encoding the concept of
``Severe``:

.. code-block:: yaml

  ontologyClass:
    id: "HP:0012828"
    label: "Severe"



The id HP:0012828 can be split into the `prefix` - 'HP' and `reference` - '0012828'. The 'HP' prefix matches the
Resource::namespacePrefix so we can append the reference '0012828' to the Resource::iriPrefix: which produces the URI

  http://purl.obolibrary.org/obo/HP_0012828

the term can be resolved to http://purl.obolibrary.org/obo/HP_0012828