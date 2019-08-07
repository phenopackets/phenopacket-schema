.. _rstindividual:

==========
Individual
==========

The subject of the Phenopacket is represented by an *Individual* element.
This element intends to represent an individual human or other organism. In this documentation,
we explain the element using the example of a human proband in a clinical investigation.

**Data model**

 .. list-table::
    :widths: 25 50 50 50
    :header-rows: 1

    * - Field
      - Type
      - Status
      - Description
    * - id
      - string
      - required
      - An arbitrary identifier
    * - date_of_birth
      - timestamp
      - optional
      - A timestamp either exact or imprecise
    * - age
      - :ref:`rstage` or :ref:`rstagerange`
      - recommended
      - The age or age range of the individual
    * - sex
      - :ref:`rstsex`
      - recommended
      - Observed apparent sex of the individual
    * - karyotypic_sex
      - :ref:`rstkaryotypicsex`
      - optional
      - The karyotypic sex of the individual
    * - taxonomy
      - :ref:`rstontologyclass`
      - optional
      - an :ref:`rstontologyclass` representing the species (e.g., NCBITaxon:9615)


**Example**

The following example is typical but does not make use of all of the optional fields of this element.

.. code-block:: json

  {
      "id": "patient:0",
      "dateOfBirth": "1998-01-01T00:00:00Z",
      "sex": "MALE"
  }


id
~~
This element is the primary identifier for the individual. The contents of the element are context dependent, and will
be determined by the application. For instance, if the Phenopacket is being used to represent a case study about
an individual with some genetic disease, the individual may be referred to in that study by their position in
the pedigree, e.g., III:2 for the second person in the third generation. In this case, id would be set to ``III:2``.
If a :ref:`pedigree` element is used, it is essential that the ``id`` used here matches the ``individual_id`` of
the :ref:`pedigree` element.

date_of_birth
~~~~~~~~~~~~~
This element represents the date of birth of the individual as an `ISO8601 UTC timestamp <https://en.wikipedia.org/wiki/ISO_8601>`_ that is rounded down to the closest known year/month/day/hour/minute. For example:

- "2018-03-01T00:00:00Z" for someone born on an unknown day in March 2018
- "2018-01-01T00:00:00Z" for someone born on an unknown day in 2018
- empty if unknown/ not stated.

See :ref:`here<rstjavatimestamp>` for more information about timestamps.

The element is provided for use cases within protected networks, but it many situations the element should not be used
in order to protect the privacy of the individual. Instead, the ``Age`` element should be preferred.


age
~~~
An age object describing the age of the individual at the time of collection of biospecimens or phenotypic observations
reported in the current Phenopacket. It is specified using either an :ref:`Age element<rstage>`, which can represent an Age in several different ways,
or an :ref:`AgeRange` element, which can represent a range of ages such as 10-14 years (age can be represented in this
was to protect privacy of study participants).


sex
~~~
Phenopackets make use of an enumeration to denote the phenotypic sex of an individual. See :ref:`rstsex`.


karyotypic_sex
~~~~~~~~~~~~~~
Phenopackets make use of an enumeration to denote the chromosomal sex of an individual. See :ref:`rstkaryotypicsex`.


taxonomy
~~~~~~~~
For resources where there may be more than one organism being studied it is advisable to indicate the taxonomic
identifier of that organism, to its most specific level. We advise using the
codes from the `NCBI Taxonomy <https://www.ncbi.nlm.nih.gov/taxonomy>`_ resource. For instance,
NCBITaxon:9606 is human (homo sapiens sapiens) and  or NCBITaxon:9615 is dog.
