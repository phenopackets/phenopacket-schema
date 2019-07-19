.. _rstresource:

========
Resource
========


The Resource element is a description of an external resource used for referencing an object. For example the
resource may be an ontology such as the HPO or SNOMED. The :ref:`rstmetadata` element uses one resource
element to describe each ontology that is referenced in the Phenopacket.

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

.. code-block:: json

  {
      "id": "so",
      "name": "Sequence types and features",
      "url": "http://purl.obolibrary.org/obo/so.owl",
      "version": "2015-11-24",
      "namespacePrefix": "SO",
      "iriPrefix": "http://purl.obolibrary.org/obo/SO_"
  }


**FHIR Mapping**
The FHIR mapping for the Phenopacket `Resource` is: `CodeSystem <http://www.hl7.org/fhir/codesystem.html>`_.


id
~~
For OBO Ontologies, the value of this string MUST always be the official
OBO ID, which is always equivalent to the ID prefix in lower case.
Examples: hp, go, mp, mondo
Consult http://obofoundry.org for a complete list
For other ontologies (e.g. SNOMED), use the prefix in `identifiers.org <http://identifiers.org/>`_.

name
~~~~
The name of the ontology refered to by the id element, for example, `The Human Phenotype Ontology`.
For OBO Ontologies, the value of this string SHOULD be the same as the title
field on http://obofoundry.org
however, this field is purely for information purposes and software
should not encode any assumptions.

namespace_prefix
~~~~~~~~~~~~~~~~
The prefix used in the CURIE of an OntologyClass e.g. HP, MP, ECO
or example an HPO term will have a CURIE like this - HP:0012828 which should be used in combination with
the iri_prefix to form a fully-resolvable IRI.

url
~~~
For OBO ontologies, this should always be the PURL, e.g.
http://purl.obolibrary.org/obo/hp.owl, http://purl.obolibrary.org/obo/hp.obo.

version
~~~~~~~
The version of the resource or ontology used to make the annotation.
For OBO ontologies, this should be the versionIRI.


iri_prefix
~~~~~~~~~~

The full IRI prefix which can be used with the namespace_prefix and the OntologyClass::id to resolve to an IRI for a
term. Tools such as the curie-util (https://github.com/prefixcommons/curie-util) can utilise this to produce
fully-resolvable IRIs for an OntologyClass.
For example, using the HPO term encoding the concept of 'Severe' (which corresponds to
`HP:0012828 <https://hpo.jax.org/app/browse/term/HP:0012828>`_), and the iri_prefix
`http://purl.obolibrary.org/obo/HP_`, the term can
be resolved to http://purl.obolibrary.org/obo/HP_0012828.
