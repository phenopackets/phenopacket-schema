.. _rstresource:

========
Resource
========


The Resource element is a description of an external resource used for referencing an object. For example the
resource may be an ontology such as the HPO or SNOMED or another data resource such as the HGNC or ClinVar.
The :ref:`rstmetadata` element uses one resource element to describe each resource that is referenced in the Phenopacket.

**Data model**

.. csv-table::
   :header: Field, Type, Status, Description

   id, string, required, hp
   name, string, required, human phenotype ontology
   namespace_prefix, string, required, HP
   url, string, required, http://purl.obolibrary.org/obo/hp.owl
   version, string, required, 2018-03-08
   iri_prefix, string, required, http://purl.obolibrary.org/obo/HP_

**Example**

For an ontology, the url should point to the obo or owl file, e.g.

.. code-block:: json

  {
      "id": "so",
      "name": "Sequence types and features",
      "url": "http://purl.obolibrary.org/obo/so.owl",
      "version": "2015-11-24",
      "namespacePrefix": "SO",
      "iriPrefix": "http://purl.obolibrary.org/obo/SO_"
  }

Non-ontology resources which use CURIEs as their native identifiers should be treated in a similarly resolvable manner.

.. code-block:: json

  {
    "id": "hgnc",
    "name": "HUGO Gene Nomenclature Committee",
    "url": "https://www.genenames.org",
    "version": "2019-08-08",
    "namespacePrefix": "HGNC",
    "iriPrefix": "https://www.genenames.org/data/gene-symbol-report/#!/hgnc_id/"
  }

Using this :ref:`rstresource` definition it is possible for software to resolve the identifier `HGNC:12805` to
https://www.genenames.org/data/gene-symbol-report/#!/hgnc_id/12805

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
For OBO ontologies, this should always be the PURL, e.g. http://purl.obolibrary.org/obo/hp.owl or http://purl.obolibrary.org/obo/hp.obo

Other resources should link to the official or top-level url e.g. https://www.uniprot.org or https://www.genenames.org

version
~~~~~~~
The version of the resource or ontology used to make the annotation. For OBO ontologies, this should be the versionIRI.
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

Identifiers and Identifier resolution
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
see
https://vr-spec.readthedocs.io/en/1.0rc/terms_and_model.html#id

All :ref:`rstontologyclass` instances SHALL be resolvable

For example, using the HPO term encoding the concept of 'Severe' (which corresponds to
`HP:0012828 <https://hpo.jax.org/app/browse/term/HP:0012828>`_), and the iri_prefix
`http://purl.obolibrary.org/obo/HP_`, the term can
be resolved to http://purl.obolibrary.org/obo/HP_0012828.
