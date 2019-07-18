.. _rstphenopacket:

===========
Phenopacket
===========

A Phenopacket is an anonymous phenotypic description of an individual or biosample with potential genes of interest
and/or diagnoses. It can be used for multiple use cases. For instance, it can be used to describe the
phenotypic findings observed in an individual with a disease that is being studied or for an individual in
whom the diagnosis is being sought. The phenopacket can contain information about
genetic findings that are causative of the disease, or alternatively it can contain a reference to a VCF file if
exome sequencing is being performed as a part of the differential diagnostic process. A Phenopacket can also be used to
describe the constitutional
phenotypic findings of an individual with cancer (a :ref:`rstbiosample` should be used to describe the phenotypic
abnormalities directly associated with an extirpated or biospied tumor).


.. code:: proto

    message Phenopacket {
        string id = 1;
        org.phenopackets.schema.v1.core.Individual subject = 2;
        repeated org.phenopackets.schema.v1.core.PhenotypicFeature phenotypic_features = 3;
        repeated org.phenopackets.schema.v1.core.Biosample biosamples = 5;
        repeated org.phenopackets.schema.v1.core.Gene genes = 6;
        repeated org.phenopackets.schema.v1.core.Variant variants = 7;
        repeated org.phenopackets.schema.v1.core.Disease diseases = 8;
        repeated org.phenopackets.schema.v1.core.HtsFile hts_files = 9;
        org.phenopackets.schema.v1.core.MetaData meta_data = 10;
    }




id
~~

The id is an identifier specific for this phenopacket. The syntax of the identifier is application specific.


subject
~~~~~~~

This is typically the individual human (or another organism) that the Phenopacket is describing. In many cases, the individual will
be a patient or proband of the study. See :ref:`rstindividual` for further information.


phenotypic_features
~~~~~~~~~~
This is a list of phenotypic findings observed in the subject. See :ref:`rstphenotype` for further information.


biosamples
~~~~~~~~~~

This field describes samples that have been derived from the patient who is the object of the Phenopacket.
or a collection of biosamples in isolation. See :ref:`rstbiosample` for further information.

genes
~~~~~
This is a field for gene identifiers and can be used for listing either candidate genes or causative genes. The
resources using these fields should define what this represents in their context. This could be used in order to
obfuscate the specific causative/candidate variant to maintain patient privacy. See :ref:`rstgene` for further information.

variants
~~~~~~~~
This is a field for genetic variants and can be used for listing either candidate variants or diagnosed causative
variants. The resources using these fields should define what this represents in their context.
See :ref:`rstvariant` for further information.

diseases
~~~~~~~~
This is a field for disease identifiers and can be used for listing either diagnosed or suspected conditions. The
resources using these fields should define what this represents in their context.
See :ref:`rstdisease` for further information.


hts_files
~~~~~~~~~
This element contains a list of pointers to the relevant HTS file(s) for the patient. Each element
describes what type of file is meant (e.g., BAM file), which genome assembly was used for mapping,
as well as a map of samples and individuals represented in that file. It also contains a
File element which optionally refers to a file on a given file system or can be a URI that
refers to a resource on the web. See :ref:`rstfile` for further information.


metaData
~~~~~~~~
This element contains structured definitions of the resources and ontologies used within the phenopacket.
It is expected that every valid Phenopacket contains a metaData element.
See :ref:`rstmetadata` for further information.



 .. list-table:: Phenopacket requirements for the ``Phenopacket`` element
   :widths: 25 50 50
   :header-rows: 1

   * - Field
     - Example
     - Status
   * - id
     - arbitrary identifier
     - required
   * - subject
     - See :ref:`rstindividual`
     - recommended
   * - phenotypic_features
     - See :ref:`rstphenotype`
     - recommended
   * - biosamples
     - See :ref:`rstbiosample`
     - optional
   * - genes
     - :ref:`rstgene`
     - optional
   * - variants
     - See :ref:`rstvariant`
     - optional
   * - diseases
     - See :ref:`rstdisease`
     - optional
   * - hts_files
     - See :ref:`rstfile`
     - optional
   * - meta_data
     - See :ref:`rstmetadata`
     - required
