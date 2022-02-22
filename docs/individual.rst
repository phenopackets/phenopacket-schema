.. _rstindividual:

##########
Individual
##########

The subject of the Phenopacket is represented by an *Individual* element.
This element intends to represent an individual human or other organism. In this documentation,
we explain the element using the example of a human proband in a clinical investigation.

Data model
##########

 .. list-table::
    :widths: 25 25 25 75
    :header-rows: 1

    * - Field
      - Type
      - Multiplicity
      - Description
    * - id
      - string
      - 1..1
      - An arbitrary identifier. REQUIRED.
    * - alternate_ids
      - a list of :ref:`rstcurie`
      - 0..*
      - A list of alternative identifiers for the individual
    * - date_of_birth
      - timestamp
      - 0..1
      - A timestamp either exact or imprecise
    * - time_at_last_encounter
      - :ref:`rsttimeelement`
      - 0..1
      - The age or age range of the individual when last encountered. RECOMMENDED.
    * - vital_status
      - :ref:`rstvitalstatus`
      - 0..1
      - The vital status of the individual e.g. whether they are alive or the time and cause of death. RECOMMENDED.
    * - sex
      - :ref:`rstsex`
      - 0..1
      - Observed apparent sex of the individual
    * - karyotypic_sex
      - :ref:`rstkaryotypicsex`
      - 0..1
      - The karyotypic sex of the individual
    * - gender
      - :ref:`rstontologyclass`
      - 0..1
      -  Self-identified gender
    * - taxonomy
      - :ref:`rstontologyclass`
      - 0..1
      - an :ref:`rstontologyclass` representing the species (e.g., NCBITaxon:9615)


Example
#######

The following example is typical but does not make use of all of the optional fields of this element.

.. code-block:: yaml

  individual:
    id: "patient:0"
    dateOfBirth: "1998-01-01T00:00:00Z"
    sex: "MALE"

Explanations
############

id
~~
This element is the **primary** identifier for the individual and SHOULD be used in other parts of a message when
referring to this individual - for example in a :ref:`rstpedigree` or :ref:`rstbiosample`. The contents of the element
are context dependent, and will be determined by the application. For instance, if the Phenopacket is being used to
represent a case study about an individual with some genetic disease, the individual may be referred to in that study by
their position in the pedigree, e.g., III:2 for the second person in the third generation. In this case, id would be set
to ``III:2``.

If a :ref:`pedigree` element is used, it is essential that the ``individual_id`` of the :ref:`pedigree` element matches
the ``id`` field here.

If a :ref:`rstbiosample` element is used, it is essential that the ``individual_id`` of the :ref:`rstbiosample` element
matches the ``id`` field here.

All identifiers within a phenopacket pertaining to an individual SHOULD use this identifier. It is the responsibility of
the sender to provide the recipient an internally consistent message. This is possible as all messages can be created
dynamically be the sender using identifiers appropriate for the receiving system.

For example, a hospital may want to send a :ref:`rstfamily` to an external lab for analysis. Here the hospital is providing
an obfuscated identifier which is used to identify the individual in the :ref:`rstphenopacket`, the :ref:`rstpedigree` and
mappings to the sample id in the :ref:`rstfile`.

In this case the :ref:`rstpedigree` is created by the sending system from whatever source they use and the identifiers
should be mapped to those `Individual.id` contained in the `Family.proband` and `Family.relatives` phenopackets.

In the case of the VCF file, the sending system likely has no control or ability to change the identifiers used for the
sample id and it is likely they use different identifiers. It is for this reason the :ref:`rstfile` has a *local*
mapping field `HtsFile.individual_to_sample_identifiers` where the `Individual.id` can be mapped to the sample id in that
file.

**example**

In this example we show individual blocks which would be used as part of a singleton 'family' to illustrate the use of
the internally consistent `Individual.id`. As noted above, the data may have been constructed by the sender from different
sources but given they know these relationships, they should provide the receiver with a consistent view of the data both
for ease of use and to limit incorrect mapping.

Thus, we would use the same id various elements.

.. code-block:: yaml

  individual:
    id: "patient23456"
    dateOfBirth: "1998-01-01T00:00:00Z"
    sex: "MALE"

Assuming that this individual was sequenced, we might have the following :ref:`rstfile` element.

.. code-block:: yaml

    htsFile:
        uri: "file://data/genomes/germline_wgs.vcf.gz"
        description: "Matched normal germline sample"
        htsFormat: "VCF"
        genomeAssembly: "GRCh38"
        individualToSampleIdentifiers:
            patient23456: "NA12345"



We would also use ``patient23456`` as the ``individualId`` element within a :ref:`rstpedigree` element.


alternate_ids
~~~~~~~~~~~~~

An optional list of alternative identifiers for this individual. These should be in the form of :ref:`rstcurie`s and hence have a
corresponding :ref:`rstresource` listed in the :ref:`rstmetadata`. These should **not** be used elsewhere in the phenopacket
as this will break the assumptions required for using the ``id`` field as the primary identifier. This field is provided
for the convenience of users who may have multiple mappings to an individual which they need to track.

date_of_birth
~~~~~~~~~~~~~
This element represents the date of birth of the individual as an `ISO8601 UTC timestamp <https://en.wikipedia.org/wiki/ISO_8601>`_ that is rounded down to the closest known year/month/day/hour/minute. For example:

- "2018-03-01T00:00:00Z" for someone born on an unknown day in March 2018
- "2018-01-01T00:00:00Z" for someone born on an unknown day in 2018
- empty if unknown/ not stated.

See :ref:`here<rstjavatimestamp>` for more information about timestamps.

The element is provided for use cases within protected networks, but it many situations the element should not be used
in order to protect the privacy of the individual. Instead, the ``time_at_last_encounter`` element should be preferred.


time_at_last_encounter
~~~~~~~~~~~~~~~~~~~~~~
An object describing when the encounter with the patient happened or the the age of the individual at the time of collection
of biospecimens or phenotypic observations reported in the current Phenopacket. It is specified using either an :ref:`rsttimeelement`,
which can represent an time in several different ways, either precisely or within a range. For example an :ref:`rstage`
or an :ref:`rstagerange` element, which can represent a range of ages such as 10-14 years (age can be represented in this
was to protect privacy of study participants).

vital_status
~~~~~~~~~~~~
The :ref:`rstvitalstatus` can be used to report whether the individual is living or dead at the timepoint when the phenopacket
was created (or if the status is unknown).

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
