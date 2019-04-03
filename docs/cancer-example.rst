.. _rstcancerexample:

============================
A complete example: Oncology
============================

We will now present a phenopacket that represents a case of an individual with bladder cancer. We present each of the
sections of the Phenopacket in separate subsections for legibility. Recall that JSON data is represented as
as name/value pairs that are separated by commas (we show the trailing comma on all but the last name/value pair of the
Phenopacket).

Infiltrating urothelial carcinoma: A case report
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
We here present a case report about an individual
with infiltrating urothelial carcinoma (NCIT:C39853). Three somatic variants were identified in this sample which
are judged to be related to tumorigenesis. Additionally, a secondary finding of prostate carcinoma is made. The
two distal ureter segments are found to be cancer-free. A pelvic lymph node is found to have a metastasis, but no molecular
investigation was made. The patient was found to have two clinical abnormalities, hematuria (blood in the urine) and
dysuria (painful urination).


In this example, we imagine that whole genome sequencing was performed on the infiltrating urothelial carcinoma as well
as on the metastasis in the pelvic lymph node. A paired normal germline sample was also sequenced. All three files
are located on some local file system, and this Phenopacket is used to organize the information about the diagnosis and
phenotypes of the cancer in a way that would be able to support analysis of the WGS data. In a larger study, one
such Phenopacket could organize the information for each of thousands of patients.



id
~~
The `id` field is an arbitrary but required value.

.. code-block:: json

    "id": "example case",


subject
~~~~~~~
The subject block is an :ref:`rstindividual` element. The `id` and `datasetId` can be arbitrary identifiers (``id`` is required,
and ``datasetId`` is optional).

.. code-block:: json

    "subject": {
        "id": "patient1",
        "datasetId": "urology cohort",
        "dateOfBirth": "1964-03-15T00:00:00Z",
        "sex": "MALE",
        "karyotypicSex": "UNKNOWN_KARYOTYPE"
    },

phenotypes
~~~~~~~~~~
This field can be used to represent clinical manifestations using the :ref:`phenotype` elelemt. Phenotypes directly related to the biopsied or
extirpated tumor specimens should be reported in the `Biosample` element (see below). In this example,
the patient is found to have `Hematuria <https://hpo.jax.org/app/browse/term/HP:0000790>`_
and severe `Dysuria <https://hpo.jax.org/app/browse/term/HP:0100518>`_.

.. code-block:: json

 "phenotypes": [{
    "description": "",
    "type": {
      "id": "HP:0000790",
      "label": "Hematuria"
    },
    "negated": false,
    "modifiers": [],
    "evidence": []
  }, {
    "description": "",
    "type": {
      "id": "HP:0100518",
      "label": "Dysuria"
    },
    "negated": false,
    "severity": {
      "id": "HP:0012828",
      "label": "Severe"
    },
    "modifiers": [],
    "evidence": []
  }],

biosamples
~~~~~~~~~~
This is a list of :ref:`rstbiosample` elements that describe the evaluation of pathological examination of tumor specimens. We will present
each :ref:`rstbiosample` in turn. The entire collection of biosamples is represented as follows.

.. code-block:: json

    "biosamples": [ { ... }, { ... }, {....}],

biosample 1: Infiltrating Urothelial Carcinoma
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
The first biosample is a biopsy taken from the wall of the urinary bladder. The histologuical diagnosis is represented
by a National Cancer Institute's Thesaurus code. This is a primary malignant neoplasm with stage T2bN2. A VCF file
representing a paired normal germline sample is located at ``/data/genomes/urothelial_ca_wgs.vcf.gz`` on the file system.


.. code-block:: json

    {
        "id": "sample1",
        "datasetId": "",
        "individualId": "patient1",
        "description": "",
        "sampledTissue": {
            "id": "UBERON_0001256",
            "label": "wall of urinary bladder"
        },
        "phenotypes": [],
        "ageOfIndividualAtCollection": {
            "age": "P52Y2M"
        },
        "histologicalDiagnosis": {
            "id": "NCIT:C39853",
            "label": "Infiltrating Urothelial Carcinoma"
        },
        "tumorProgression": {
            "id": "NCIT:C84509",
            "label": "Primary Malignant Neoplasm"
        },
        "tumorStage": [{
            "id": "NCIT:C48766",
            "label": "pT2b Stage Finding"
           }, {
            "id": "NCIT:C48750",
            "label": "pN2 Stage Finding"
        }],
        "diagnosticMarkers": [],
        "procedure": {
            "code": {
                "id": "NCIT:C15189",
                "label": "Biopsy"
            }
        },
        "htsFiles": [{
            "htsFormat": "VCF",
            "genomeAssembly": "GRCh38",
            "individualToSampleIdentifiers": {
            },
            "file": {
                "path": "/data/genomes/urothelial_ca_wgs.vcf.gz",
                "uri": "",
                "description": "Urothelial carcinoma sample"
            }
        }],
        "variants": [],
        "isControlSample": false
    }


Biosample 2: Prostate Acinar Adenocarcinoma
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
Prostate adenocarcinoma was discovered as an incidental finding. The tumor was found to have a Gleason score of 7.

.. code-block:: json

    {
        "id": "sample2",
        "datasetId": "",
        "individualId": "patient1",
        "description": "",
        "sampledTissue": {
            "id": "UBERON:0002367",
            "label": "prostate gland"
        },
        "phenotypes": [],
        "ageOfIndividualAtCollection": {
            "age": "P52Y2M"
        },
        "histologicalDiagnosis": {
            "id": "NCIT:C5596",
            "label": "Prostate Acinar Adenocarcinoma"
        },
        "tumorProgression": {
            "id": "NCIT:C95606",
            "label": "Second Primary Malignant Neoplasm"
        },
        "tumorGrade": {
            "id": "NCIT:C28091",
            "label": "Gleason Score 7"
        },
        "tumorStage": [],
        "diagnosticMarkers": [],
        "procedure": {
            "code": {
                "id": "NCIT:C15189",
                "label": "Biopsy"
            }
        },
        "htsFiles": [],
        "variants": [],
        "isControlSample": false
    }

Biosample 3: Left ureter
~~~~~~~~~~~~~~~~~~~~~~~~
A biopsy of the left ureter reveal normal findings.

.. code-block:: json

    {
        "id": "sample3",
        "datasetId": "",
        "individualId": "patient1",
        "description": "",
        "sampledTissue": {
            "id": "UBERON:0001223",
            "label": "left ureter"
        },
        "phenotypes": [],
        "ageOfIndividualAtCollection": {
            "age": "P52Y2M"
        },
        "histologicalDiagnosis": {
            "id": "NCIT:C38757",
            "label": "Negative Finding"
        },
        "tumorStage": [],
        "diagnosticMarkers": [],
        "procedure": {
            "code": {
                "id": "NCIT:C15189",
                "label": "Biopsy"
            }
        },
        "htsFiles": [],
        "variants": [],
        "isControlSample": false
    }


Biosample 4: Right ureter
~~~~~~~~~~~~~~~~~~~~~~~~~
A biopsy of the right ureter reveal normal findings.

.. code-block:: json

    {
        "id": "sample4",
        "datasetId": "",
        "individualId": "patient1",
        "description": "",
        "sampledTissue": {
            "id": "UBERON:0001222",
            "label": "right ureter"
        },
        "phenotypes": [],
        "ageOfIndividualAtCollection": {
            "age": "P52Y2M"
        },
        "histologicalDiagnosis": {
            "id": "NCIT:C38757",
            "label": "Negative Finding"
        },
        "tumorStage": [],
        "diagnosticMarkers": [],
        "procedure": {
            "code": {
                "id": "NCIT:C15189",
                "label": "Biopsy"
            }
        },
        "htsFiles": [],
        "variants": [],
        "isControlSample": false
    }


Biosample 4: Pelvic lymph node
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
A biopsy of a pelvic lymph node revealed a metastasis. A reference to a somatic genome sequence file is provided.

.. code-block:: json

     {
        "id": "sample5",
        "datasetId": "",
        "individualId": "patient1",
        "description": "",
        "sampledTissue": {
            "id": "UBERON:0015876",
            "label": "pelvic lymph node"
        },
        "phenotypes": [],
        "ageOfIndividualAtCollection": {
            "age": "P52Y2M"
        },
        "tumorProgression": {
            "id": "NCIT:C3261",
            "label": "Metastatic Neoplasm"
        },
        "tumorStage": [],
        "diagnosticMarkers": [],
        "procedure": {
            "code": {
                "id": "NCIT:C15189",
                "label": "Biopsy"
            }
        },
        "htsFiles": [{
            "htsFormat": "VCF",
            "genomeAssembly": "GRCh38",
            "individualToSampleIdentifiers": {
            },
            "file": {
                "path": "/data/genomes/metastasis_wgs.vcf.gz",
                "uri": "",
                "description": "lymph node metastasis sample"
            }
        }],
        "variants": [],
        "isControlSample": false
     }


genes and variants
~~~~~~~~~~~~~~~~~~
These elements of the Phenopacket are empty. One could have used them to specify that a certain
gene or variant was identified that was inferred to be related to the tumor specimen (for instance,
a germline mutation in a cancer susceptibility gene).

diseases
~~~~~~~~

We recommend using the National Cancer Institute's Thesaurus codes to represent cancer diagnoses, but any
relevant ontology term can be used.

.. code-block:: json

    "diseases": [{
        "term": {
            "id": "NCIT:C39853",
            "label": "Infiltrating Urothelial Carcinoma"
            }
    }],


htsFiles
~~~~~~~~
This is a reference to the paired normal germline sample.

.. code-block:: json

    "htsFiles": [{
        "htsFormat": "VCF",
        "genomeAssembly": "GRCh38",
        "individualToSampleIdentifiers": {
        },
        "file": {
            "path": "/data/genomes/germline_wgs.vcf.gz",
            "uri": "",
            "description": "Matched normal germline sample"
        }
    }],


metaData
~~~~~~~~


.. code-block:: json

   "metaData": {
        "created": "2019-04-03T15:31:40.765Z",
        "createdBy": "Peter R",
        "submittedBy": "Peter R",
        "resources": [{
            "id": "hp",
            "name": "human phenotype ontology",
            "namespacePrefix": "HP",
            "url": "http://purl.obolibrary.org/obo/hp.owl",
            "version": "2019-04-08",
            "iriPrefix": "http://purl.obolibrary.org/obo/HP_"
            }, {
            "id": "uberon",
            "name": "uber anatomy ontology",
            "namespacePrefix": "UBERON",
            "url": "http://purl.obolibrary.org/obo/uberon.owl",
            "version": "2019-03-08",
            "iriPrefix": "http://purl.obolibrary.org/obo/UBERON_"
            }, {
            "id": "ncit",
            "name": "NCI Thesaurus OBO Edition",
            "namespacePrefix": "NCIT",
            "url": "http://purl.obolibrary.org/obo/ncit.owl",
            "version": "18.05d",
            "iriPrefix": ""
            }],
        "updated": [],
        "externalReferences": [{
            "id": "PMID:29221636",
            "description": "Urothelial neoplasms in pediatric and young adult patients: A large single-center series"
        }]
   }


The Java code that was used to create this example is explained  :ref:`here<rstcancerexamplejava>`.

