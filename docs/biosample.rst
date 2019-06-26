.. _rstbiosample:

=========
Biosample
=========

A Biosample refers to a unit of biological material from which the substrate
molecules (e.g. genomic DNA, RNA, proteins) for molecular analyses (e.g.
sequencing, array hybridisation, mass-spectrometry) are extracted. Examples
would be a tissue biopsy, a single cell from a culture for single cell genome
sequencing or a protein fraction from a gradient centrifugation.
Several instances (e.g. technical replicates) or types of experiments (e.g.
genomic array as well as RNA-seq experiments) may refer to the same Biosample.


.. code:: proto

    message Biosample {
        string id = 1;
        string dataset_id = 2;
        string individual_id = 3;
        string description = 4;
        OntologyClass sampled_tissue = 5;
        repeated PhenotypicFeature phenotypic_features = 6;
        OntologyClass taxonomy = 7;
        oneof individual_age_at_collection {
            Age age_of_individual_at_collection = 8;
            AgeRange age_range_of_individual_at_collection = 9;
        }
        OntologyClass histological_diagnosis = 10;
        OntologyClass tumor_progression = 11;
        OntologyClass tumor_grade = 12;
        repeated OntologyClass tumor_stage = 13;
        repeated OntologyClass diagnostic_markers = 14;
        Procedure procedure = 15;
        repeated HtsFile hts_files = 16;
        repeated Variant variants = 17;
        bool is_control_sample = 18;
    }

id
~~
The Biosample id. This is unique in the context of the server instance.

dataset_id
~~~~~~~~~~
The ID of the dataset this Biosample belongs to.

string individual_id
~~~~~~~~~~~~~~~~~~~~
The id of the individual this biosample was derived from. It is not necessary to
provide this information here if the Biosample is being transmitted as a part of
a :ref:`rstphenopacket`.

description
~~~~~~~~~~~
The biosample's description. This attribute contains human readable text.
The "description" attributes should not contain any structured data.

sampled_tissue
~~~~~~~~~~~~~~
On :ref:`rstontologyclass` describing the tissue from which the specimen was collected.
We recommend the use of `UBERON <https://www.ebi.ac.uk/ols/ontologies/uberon>`_. The
PDX MI mapping is ``Specimen tumor tissue``. The FHIR mapping is ``Specimen.type``.

phenotypic_features
~~~~~~~~~~
The phenotypic characteristics of the BioSample, for example histological findings of a biopsy.
See :ref:`rstphenotypicfeature` for further information.


taxonomy
~~~~~~~~
For resources where there may be more than one organism being studied it is advisable to indicate the taxonomic
identifier of that organism, to its most specific level. We advise using the
codes from the `NCBI Taxonomy <https://www.ncbi.nlm.nih.gov/taxonomy>`_ resource. For instance,
NCBITaxon:9606 is human (homo sapiens sapiens) and  or NCBITaxon:9615 is dog.

individual_age_at_collection
~~~~~~~~~~~~~~~~~~~~~~~~~~~~
An age object describing the age of the individual this biosample was
derived from at the time of collection. See :ref:`rstage` for further information.

histological_diagnosis
~~~~~~~~~~~~~~~~~~~~~~
This is the pathologist’s diagnosis and may often represent a refinement of the clinical diagnosis (which
could be reported in the :ref:`rstphenopacket` that contains this Biosample).
Normal samples would be tagged with the term "NCIT:C38757", "Negative Finding".
See :ref:`rstontologyclass` for further information.

tumor_progression
~~~~~~~~~~~~~~~~~
This field can be used to indicate if a specimen is from  the primary tumor, a metastasis or a recurrence.
There are multiple ways of representing this using ontology terms, and the terms chosen should have
a specific meaning that is application specific.

For example a term from the following NCIT terms from
the `Neoplasm by Special Category <https://www.ebi.ac.uk/ols/ontologies/ncit/terms?iri=http%3A%2F%2Fpurl.obolibrary.org%2Fobo%2FNCIT_C7062>`_
can be chosen.

* `Primary Neoplasm <https://www.ebi.ac.uk/ols/ontologies/ncit/terms?iri=http%3A%2F%2Fpurl.obolibrary.org%2Fobo%2FNCIT_C8509>`_
* `Metastatic Neoplasm <https://www.ebi.ac.uk/ols/ontologies/ncit/terms?iri=http%3A%2F%2Fpurl.obolibrary.org%2Fobo%2FNCIT_C3261>`_
* `Recurrent Neoplasm <https://www.ebi.ac.uk/ols/ontologies/ncit/terms?iri=http%3A%2F%2Fpurl.obolibrary.org%2Fobo%2FNCIT_C4798>`_

tumor_grade
~~~~~~~~~~~
This should be a child term of  NCIT:C28076 (Disease Grade Qualifier) or equivalent.
See the `tumor grade fact sheet <https://www.cancer.gov/about-cancer/diagnosis-staging/prognosis/tumor-grade-fact-sheet>`_.

tumor_stage
~~~~~~~~~~~
Cancer findings in the TNM system that is relevant to the diagnosis of cancer.
See `staging <https://www.cancer.gov/about-cancer/diagnosis-staging/staging>`_.
This element should be derived from child terms of NCIT:C48232 (Cancer TNM Finding) or equivalent.

diagnostic_markers
~~~~~~~~~~~~~~~~~~
Clinically relevant bio markers. Most of the assays such as immunohistochemistry (IHC) are covered by the NCIT under the sub-hierarchy
NCIT:C25294 (Laboratory Procedure), e.g. NCIT:C68748 (HER2/Neu Positive), NCIT:C131711 (Human Papillomavirus-18 Positive).

procedure
~~~~~~~~~
The clinical procedure performed on the subject in order to extract the biosample.
See :ref:`rstprocedure` for further information.


hts_files
~~~~~~~~~
This element contains a list of pointers to the relevant HTS file(s) for the biosample. Each element
describes what type of file is meant (e.g., BAM file), which genome assembly was used for mapping,
as well as a map of samples and individuals represented in that file. It also contains a
File element which optionally refers to a file on a given file system or can be a URI that
refers to a resource on the web. See :ref:`rstfile` for further information.

variants
~~~~~~~~
This is a field for genetic variants and can be used for listing either candidate variants or diagnosed causative
variants. If this biosample represents a cancer specimen, the variants might refer to somatic variants identified
in the biosample. The resources using these fields should define what this represents in their context.
See :ref:`rstvariant` for further information.

is_control_sample
~~~~~~~~~~~~~~~~~
A boolean (true/false) value.
If true, this sample is being use as a normal control, often in combination with another sample that is thought to contain a pathological finding
the default value is false.






 .. list-table:: Phenopacket requirements for the ``Biosample`` element
   :widths: 25 50 50
   :header-rows: 1

   * - Field
     - Example
     - Status
   * - id
     - arbitrary identifier
     - required
   * - dataset_id
     - arbitrary identifier
     - optional
   * - individual_id
     - arbitrary identifier
     - optional
   * - description
     - arbitrary text
     - optional
   * - sampled_tissue
     - See :ref:`rstontologyclass`
     - required
   * - phenotypic_features
     - See :ref:`rstphenotype`
     - recommend (may be empty if this element is used merely to report the genotype in a sample)
   * - taxonomy
     - See :ref:`rstontologyclass`
     - optional
   * - individual_age_at_collection
     - see text
     - recommended
   * - histological_diagnosis
     - See :ref:`rstontologyclass`
     - recommended
   * - tumor_progression
     - See :ref:`rstontologyclass`
     - recommended
   * - tumor_grade
     - See :ref:`rstontologyclass`
     - recommended
   * - tumor_stage
     - See :ref:`rstontologyclass`
     - recommended
   * - histological_diagnosis
     - See :ref:`rstontologyclass`
     - recommended
   * - diagnostic_markers
     - See :ref:`rstontologyclass`
     - recommended
   * - diseases
     - See :ref:`rstdisease`
     - optional
   * - hts_files
     - See :ref:`rstfile`
     - optional
   * - variants
     - See :ref:`rstvariant`
     - recommended
   * - bool is_control_sample
     - true or false
     - optional (default: false)




FHIR mapping
~~~~~~~~~~~~
`Specimen <http://www.hl7.org/fhir/specimen.html>`_.