.. _rstbiosample:

#########
Biosample
#########

A Biosample refers to a unit of biological material from which the substrate
molecules (e.g. genomic DNA, RNA, proteins) for molecular analyses (e.g.
sequencing, array hybridisation, mass-spectrometry) are extracted. Examples
would be a tissue biopsy, a single cell from a culture for single cell genome
sequencing or a protein fraction from a gradient centrifugation.
Several instances (e.g. technical replicates) or types of experiments (e.g.
genomic array as well as RNA-seq experiments) may refer to the same Biosample.

Data model
##########

 .. list-table::
   :widths: 25 25 50 50
   :header-rows: 1

   * - Field
     - Type
     - Status
     - Description
   * - id
     - string
     - required
     - arbitrary identifier
   * - individual_id
     - string
     - recommended
     - arbitrary identifier
   * - derived_from_id
     - string
     - optional
     - id of the biosample from which the current biosample was derived (if applicable)
   * - description
     - string
     - optional
     - arbitrary text
   * - sampled_tissue
     - :ref:`rstontologyclass`
     - optional
     - Tissue from which the sample was taken
   * - sample_type
     - :ref:`rstontologyclass`
     - optional
     - type of material, e.g., RNA, DNA, Cultured cells
   * - phenotypic_features
     - :ref:`rstphenotypicfeature` (List)
     - recommended
     - List of phenotypic abnormalities of the sample
   * - measurements
     - :ref:`rstmeasurement` (List)
     - optional
     - List of measurements of the sample
   * - taxonomy
     - :ref:`rstontologyclass`
     - optional
     - Species of the sampled individual
   * - time_of_collection
     - :ref:`rsttimeelement`
     - recommended
     - Age of the proband at the time the sample was taken
   * - histological_diagnosis
     - :ref:`rstontologyclass`
     - recommended
     - Disease diagnosis that was inferred from the histological examination
   * - tumor_progression
     - :ref:`rstontologyclass`
     - recommended
     - Indicates primary, metastatic, recurrent
   * - tumor_grade
     - :ref:`rstontologyclass`
     - recommended
     - List of terms representing the tumor grade
   * - pathological_stage
     - :ref:`rstontologyclass`
     - optional
     - Pathological stage, if applicable
   * - pathological_tnm_finding
     - :ref:`rstontologyclass` (List)
     - optional
     - Pathological TNM findings, if applicable
   * - diagnostic_markers
     - :ref:`rstontologyclass`
     - recommended
     - Clinically relevant biomarkers
   * - procedure
     - :ref:`rstprocedure`
     - required
     - The procedure used to extract the biosample
   * - hts_files
     - :ref:`rstfile`
     - optional
     - list of high-throughput sequencing files derived from the biosample
   * - material_sample
     - :ref:`rstontologyclass`
     - recommended
     - status of specimen (tumor tissue, normal control, etc.)

Example
#######

The staging system most often used for
bladder cancer is the American Joint Committee on Cancer (AJCC) TNM system. The overall
stage is assigned based on the T, N, and M categories (Cancer stage grouping). For instance, stage II (pathological staging)
is defined as T2a or T2b, N0, and M0, meaning the cancer has spread
into the  wall of the bladder.

.. code-block:: yaml

  biosample:
    id: "sample1"
    individualId: "patient1"
    description: "Additional information can go here"
    sampledTissue:
        id: "UBERON_0001256"
        label: "wall of urinary bladder"
    histologicalDiagnosis:
        id: "NCIT:C39853"
        label: "Infiltrating Urothelial Carcinoma"
    tumorProgression:
        id: "NCIT:C84509"
        label: "Primary Malignant Neoplasm"
    tumorGrade:
        id: "NCIT:C36136"
        label: "Grade 2 Lesion"
    procedure:
        code:
            id: "NCIT:C5189"
            label: "Radical Cystoprostatectomy"
    htsFiles:
        - uri: "file:///data/genomes/urothelial_ca_wgs.vcf.gz"
        description: "Urothelial carcinoma sample"
        htsFormat: "VCF"
        genomeAssembly: "GRCh38"
        individualToSampleIdentifiers:
            patient1: "NA12345"
    materialSample:
        id: "EFO:0009655"
        label: "abnormal sample"
    timeOfCollection:
        age:
            iso8601duration: "P52Y2M"
    pathologicalStage:
        id: "NCIT:C28054"
        label: "Stage II"
    pathologicalTnmFinding:
    - id: "NCIT:C48726"
        label: "T2b Stage Finding"
    - id: "NCIT:C48705"
        label: "N0 Stage Finding"
    - id: "NCIT:C48699"
        label: "M0 Stage Finding"


Explanations
############

id
~~
The Biosample id. This is unique in the context of the server instance.

individual_id
~~~~~~~~~~~~~
The id of the :ref:`rstindividual` this biosample was derived from. It is recommended, but not necessary to
provide this information here if the Biosample is being transmitted as a part of
a :ref:`rstphenopacket`.

derived_from_id
~~~~~~~~~~~~~~~
The id of the parent biosample this biosample was derived from.

description
~~~~~~~~~~~
The biosample's description. This attribute contains human readable text.
The "description" attributes should not contain any structured data.

sampled_tissue
~~~~~~~~~~~~~~
On :ref:`rstontologyclass` describing the tissue from which the specimen was collected.
We recommend the use of `UBERON <https://www.ebi.ac.uk/ols/ontologies/uberon>`_. The
PDX MI mapping is ``Specimen tumor tissue``.

sample_type
~~~~~~~~~~~

RNA, DNA, Cultured cells. We recommend use of EFO term to describe the sample,
for instance, `genomic DNA (EFO:0008479) <https://www.ebi.ac.uk/ols/ontologies/efo/terms?iri=http%3A%2F%2Fwww.ebi.ac.uk%2Fefo%2FEFO_0008479>`_.


phenotypic_features
~~~~~~~~~~~~~~~~~~~
The phenotypic characteristics of the BioSample, for example histological findings of a biopsy.
See :ref:`rstphenotypicfeature` for further information.


measurements
~~~~~~~~~~~~
Measurements (usually quantitative) performed on the sample.
See :ref:`rstmeasurement` for further information.




taxonomy
~~~~~~~~
For resources where there may be more than one organism being studied it is advisable to indicate the taxonomic
identifier of that organism, to its most specific level. We advise using the
codes from the `NCBI Taxonomy <https://www.ncbi.nlm.nih.gov/taxonomy>`_ resource. For instance,
NCBITaxon:9606 is human (homo sapiens sapiens) and  or NCBITaxon:9615 is dog.

individual_age_at_collection
~~~~~~~~~~~~~~~~~~~~~~~~~~~~
An age object describing the age of the individual this biosample was
derived from at the time of collection. The Age object allows the encoding
of the age either as ISO8601 duration or time interval (preferred), or
as ontology term object.
See :ref:`rsttimeelement` for further information.

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
This should be a child term of NCIT:C28076 (Disease Grade Qualifier) or equivalent.
See the `tumor grade fact sheet <https://www.cancer.gov/about-cancer/diagnosis-staging/prognosis/tumor-grade-fact-sheet>`_.


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
URI element which refers to a file on a given file system or a resource on the web.

See :ref:`rstfile` for further information.

material_sample
~~~~~~~~~~~~~~~

This element can be used to specify the status of the sample. For instance,
a status may be used as a normal control, often in combination with
another sample that is thought to contain a pathological finding.
We recommend use of ontology terms such as

- `reference sample (EFO:0009654) <https://www.ebi.ac.uk/ols/ontologies/efo/terms?iri=http%3A%2F%2Fwww.ebi.ac.uk%2Fefo%2FEFO_0009654>`_.
- `abnormal sample (EFO:0009655) <https://www.ebi.ac.uk/ols/ontologies/efo/terms?iri=http%3A%2F%2Fwww.ebi.ac.uk%2Fefo%2FEFO_0009655>`_.
