.. _rstindividual:

==================
Individual element
==================

The subject of the Phenopacket is represented by an *Individual* element.
The Phenopackets schema maps the *Individual* element to the FHIR `Patient element <https://www.hl7.org/fhir/patient.html>`_.

Individual
==========
This element intends to represent an individual human or other organism. In this documentation, we explain the element using the example of a human proband in a clinical investigation.::

  message Individual {
   string id = 1;
   string dataset_id = 2;
   google.protobuf.Timestamp date_of_birth = 3;
   oneof age {
       Age age_at_collection = 4;
       AgeRange age_range_at_collection = 5;
   }
   Sex sex = 6;
   KaryotypicSex karyotypic_sex = 10;
   OntologyClass taxonomy = 8;
  }


id
==
This element is the primary identifier for the individual. The contents of the element are context dependent, and will
be determined by the application. For instance, if the Phenopacket is being used to represent a case study about
an individual with some genetic disease, the individual may be referred to in that study by their position in
the pedigree, e.g., III:2 for the second person in the third generation. In this case, id would be set to ``III:2``.
If a ``Pedigree`` element is used, it is essential that the ``id`` used here matches the individual_id of
the ``Pedigree`` element.

dataset_id
==========
This element represents the ID of the dataset this Individual belongs to.
This field is represented by a string and its syntax and meaning are application-dependent.

date_of_birth
=============
This element represents the date of birth of the individual as an `ISO8601 UTC timestamp <https://en.wikipedia.org/wiki/ISO_8601>`_ that is rounded down to the closest known year/month/day/hour/minute. For example:

- "2018-03-01T00:00:00Z" for someone born on an unknown day in March 2018
- "2018-01-01T00:00:00Z" for someone born on an unknown day in 2018
- empty if unknown/ not stated.

The element is provided for use cases within protected networks, but it many situations the element should not be used
in order to protect the privacy of the individual. Instead, the ``Age`` element should be preferred.


age
===

An age object describing the age of the individual at the time of collection of biospecimens or phenotypic observations
reported in the current Phenopacket. It is specified using either an :ref:`Age element`, which can represent an Age in several different ways,
or an :ref:`AgeRange` element, which can represent a range of ages such as 10-14 years (age can be represented in this
was to protect privacy of study participants).



Specifying the sex of an individual
===================================
Phenopackets make use of ontology terms to denote the sex of an individual. We recommend using
`PATO terms <https://www.ebi.ac.uk/ols/ontologies/pato/terms?iri=http%3A%2F%2Fpurl.obolibrary.org%2Fobo%2FPATO_0000047>`_ for this purpose. There are various ways to define sex including phenotypically and genetically, and Phenopackets allows
any ontology term that represents sex in any way to be used. The PATO terms `female <https://www.ebi.ac.uk/ols/ontologies/pato/terms?iri=http%3A%2F%2Fpurl.obolibrary.org%2Fobo%2FPATO_0000383>`_ and `male <https://www.ebi.ac.uk/ols/ontologies/pato/terms?iri=http%3A%2F%2Fpurl.obolibrary.org%2Fobo%2FPATO_0000384>`_ represent phenotypic sex. There may be situations when it is more appropriate to use a term such as `male genotypic sex <https://www.ebi.ac.uk/ols/ontologies/pato/terms?iri=http%3A%2F%2Fpurl.obolibrary.org%2Fobo%2FPATO_0020001>`_.
   

In Java, the sex can be specified as follows::

   /** convenience function to help creating OntologyClass objects. */
    public static OntologyClass ontologyClass(String id, String label) {
        return OntologyClass.newBuilder()
                .setId(id)
                .setLabel(label)
                .build();
    }

    final OntologyClass FEMALE = ontologyClass("PATO:0000383", "female");
    final OntologyClass MALE = ontologyClass("PATO:0000384", "male");

phenotypes
==========
This is a list of ontology terms that describe the phenotype of the subject of the Phenopacket.
It makes use of the :ref:`Phenotype element`.

taxonomy
========
This element usually is not needed for medical use cases. For resources where there may be more than
one organism being studied it is advisable to indicate the taxonomic
identifier of that organism, to its most specific level using an NCBI taxonomic identifier,
e.g. NCBITaxon:9606 or NCBITaxon:1337 encoded as an OntologyClass.

timestamp
=========
This element represents the `ISO8601 UTC timestamp <https://en.wikipedia.org/wiki/ISO_8601>`_ at
which this Individual record was created, in the format YYYY-MM-DDTHH:MM:SS.SSSZ,
e.g. 2007-12-03T10:15:30.00Z.

updated
=======
This element represents the `ISO8601 UTC timestamp <https://en.wikipedia.org/wiki/ISO_8601>`_ at
which this Individual record was updated, in the format YYYY-MM-DDTHH:MM:SS.SSSZ,
e.g. 2007-12-03T10:15:30.00Z.

location
========
This element represents the address coded as geolocation where this individual originated from.
It is recommended that this reflects the place of birth or main place of living, not necessarily a current address.
It is represented using a :ref:`Geolocation Element`.


attributes
==========
This element can be empty of contain a map of additional information regarding the Individual.
The element can be empty or contain one or more :ref:`Attributes element`.
   

external_identifiers
====================
The element can be empty or contain one or more external identifiers
representing this individual. These are considered
different representation of the same record, not records which are in some
other relation with the record at hand. It is defined using the
:ref:`External Reference element`.
   
