.. _rstphenopacket:

###########
Phenopacket
###########

A Phenopacket is an anonymous phenotypic description of an individual or biosample with potential genetic findings
and/or diagnoses of interest. It can be used for multiple use cases. For instance, it can be used to describe the
phenotypic findings observed in an individual with a disease that is being studied or for an individual in
whom the diagnosis is being sought. The phenopacket can contain information about
genetic findings that are causative of the disease, or alternatively it can contain a reference to a VCF file if
genome scale sequencing is being performed as a part of the differential diagnostic process. A Phenopacket can also be used to
describe the constitutional phenotypic findings of an individual with cancer (a :ref:`rstbiosample` should be used to
describe the phenotypic abnormalities directly associated with an extirpated or biopsied tumor).


 .. list-table:: Definition of the ``Phenopacket`` element
    :widths: 25 25 25 75
    :header-rows: 1

    * - Field
      - Type
      - Multiplicity
      - Definition
    * - id
      - string
      - 1..1
      - arbitrary identifier. REQUIRED.
    * - subject
      - :ref:`rstindividual`
      - 0..1
      - The proband. RECOMMENDED.
    * - phenotypic_features
      - List of :ref:`phenotypicfeature`
      - 0..*
      - Phenotypic features observed in the proband. RECOMMENDED.
    * - measurements
      - List of :ref:`rstmeasurement`
      - 0..*
      - Measurements performed in the proband
    * - biosamples
      - List of :ref:`rstbiosample`
      - 0..*
      - samples (e.g., biopsies), if any
    * - interpretations
      - List of :ref:`rstinterpretation`
      - 0..*
      - Interpretations related to this phenopacket
    * - diseases
      - List of :ref:`rstdisease`
      - 0..*
      - Disease(s) diagnosed in the proband
    * - medical_actions
      - List of :ref:`rstmedicalaction`
      - 0..*
      - Medical actions performed
    * - files
      - List of :ref:`rstfile`
      - 0..*
      - list of files related to the subject, e.g. VCF or other high-throughput sequencing files
    * - meta_data
      - :ref:`rstmetadata`
      - 1..1
      - Information about ontologies and references used in the phenopacket. REQUIRED.

Examples
########

TODO link to several longer examples.


Explanations
############

id
~~

The id is an identifier specific for this phenopacket. The syntax of the identifier is application specific.


subject
~~~~~~~

This is typically the individual human (or another organism) that the Phenopacket is describing. In many cases, the individual will
be a patient or proband of the study. See :ref:`rstindividual` for further information.


phenotypic_features
~~~~~~~~~~~~~~~~~~~
This is a list of phenotypic findings observed in the subject. See :ref:`phenotypicfeature` for further information.


measurements
~~~~~~~~~~~~

A list of measurements performed in the patient. In contrast to :ref:`phenotypicfeature`, which
relies on an :ref:`rstontologyclass` to specify the observation, the :ref:`rstmeasurement` can
be used to report quanititative as well as ordinal or categorical measurements.



biosamples
~~~~~~~~~~

This field describes samples that have been derived from the patient who is the object of the Phenopacket.
or a collection of biosamples in isolation. See :ref:`rstbiosample` for further information.

interpretations
~~~~~~~~~~~~~~~

An optional list of :ref:`rstinterpretation` related to the phenopacket. These elements
are intended to represent interpretations of disease or phenotypic findings based on
genomic findings and must relate either to a genetic or genomic investigation of organismal
origin (e.g., germline DNA derived from a blood sample) or from a :ref:`rstbiosample`.


diseases
~~~~~~~~
This is a field for disease identifiers and can be used for listing either diagnosed or suspected conditions. The
resources using these fields should define what this represents in their context.
See :ref:`rstdisease` for further information.

medical_actions
~~~~~~~~~~~~~~~

A list of treatments or other medical actions performed for the person represented by this
phenopacket. See :ref:`rstmedicalaction` for details.


files
~~~~~
This element contains a list of pointers to relevant file(s) for the `subject`. For example, the results of a high-throughput
sequencing experiment. See :ref:`rstfile` for further information.


meta_data
~~~~~~~~
This element contains structured definitions of the resources and ontologies used within the phenopacket.
It is expected that every valid Phenopacket contains a metaData element.
See :ref:`rstmetadata` for further information.


