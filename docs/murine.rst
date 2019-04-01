====================
Murine Disease Model
====================

The Phenopacket standard has been designed to be used in some non-medical contexts. As an example, we here show how
a Phenopacket can be used to describe a genetically modified mouse that was created to study a
Mendelian disease.

In `Evidence for a critical contribution of haploinsufficiency in the complex pathogenesis of Marfan syndrome
<https://www.ncbi.nlm.nih.gov/pubmed/15254584>`_, Judge and coauthors describe a mouse model that
harbors a heterozygous c.1039C>G variant in the `Fbn1 gene <http://www.informatics.jax.org/marker/MGI:95489>`_.
The mice displayed impaired microfibrillar deposition, skeletal deformity, and progressive deterioration of
aortic wall architecture, which are features that are comparable `Marfan syndrome <https://omim.org/entry/154700>`_ in
humans.

We generated the phenopacket using Java code that is available in the test files section (``MurineDiseaseModel.java``).
We first show the entire Phenopacket, and further below explain the individual sections.

.. code-block:: json

   {
    "id": "",
    "subject": {
      "id": "MGI:3690326",
      "datasetId": "",
      "sex": "UNKNOWN_SEX",
      "karyotypicSex": "UNKNOWN_KARYOTYPE"
    },
    "phenotypes": [{
      "description": "",
      "type": {
        "id": "MP:0004044",
        "label": "aortic dissection"
      },
    "negated": false,
    "modifiers": [],
    "evidence": []
   }, {
    "description": "",
    "type": {
      "id": "MP:0000150",
      "label": "abnormal rib morphology"
    },
    "negated": false,
    "modifiers": [],
    "evidence": []
   }, {
    "description": "",
    "type": {
      "id": "MP:0000160",
      "label": "kyphosis"
    },
    "negated": false,
    "modifiers": [],
    "evidence": []
   }, {
    "description": "",
    "type": {
      "id": "MP:0001183",
      "label": "overexpanded pulmonary alveoli"
    },
    "negated": false,
    "modifiers": [],
    "evidence": []
   }, {
    "description": "",
    "type": {
      "id": "MP:0003211",
      "label": "abnormal aorta elastic fiber morphology"
    },
    "negated": false,
    "modifiers": [],
    "evidence": []
   }, {
    "description": "",
    "type": {
      "id": "MP:0006120",
      "label": "mitral valve prolapse"
    },
    "negated": false,
    "modifiers": [],
    "evidence": []
   }, {
    "description": "",
    "type": {
      "id": "MP:0003923",
      "label": "abnormal heart left atrium morphology"
    },
    "negated": false,
    "modifiers": [],
    "evidence": []
   }, {
    "description": "",
    "type": {
      "id": "MP:0003921",
      "label": "abnormal heart left ventricle morphology"
    },
    "negated": false,
    "modifiers": [],
    "evidence": []
   }, {
    "description": "",
    "type": {
      "id": "MP:0010996",
      "label": " increased aorta wall thickness"
    },
    "negated": false,
    "modifiers": [],
    "evidence": []
    }],
    "biosamples": [],
    "genes": [],
     "variants": [{
    "genotype": {
      "id": "GENO:0000135",
      "label": "heterozygous"
    },
    "background": "involves: 129S1/Sv * 129X1/SvJ * C57BL/6J",
    "mouseAllele": {
      "id": "MGI:3690325",
      "gene": "Fbn1",
      "alleleSymbol": "tm1Hcd"
    }
   }],
    "diseases": [],
    "htsFiles": [],
    "metaData": {
      "createdBy": "Peter",
      "submittedBy": "",
      "resources": [{
        "id": "mp",
        "name": "mammalian phenotype ontology",
        "namespacePrefix": "MP",
        "url": "http://purl.obolibrary.org/obo/mp.owl",
        "version": "2019-03-08",
        "iriPrefix": "http://purl.obolibrary.org/obo/MP_"
      }, {
        "id": "geno",
        "name": "Genotype Ontology",
        "namespacePrefix": "GENO",
        "url": "http://purl.obolibrary.org/obo/geno.owl",
        "version": "19-03-2018",
        "iriPrefix": "http://purl.obolibrary.org/obo/GENO_"
      }],
      "updated": [],
      "externalReferences": [{
        "id": "PMID:15254584",
        "description": "Heterozygous Fbn1 C1039G mutation. Judge DP, Biery NJ, Keene DR, Geubtner J, Myers L, Huso DL, Sakai LY, Dietz\nHC. Evidence for a critical contribution of haploinsufficiency in the complex\npathogenesis of Marfan syndrome. J Clin Invest. 2004;114(2):172-81."
      }]
    }
   }


id
~~
This is the protobuf code

.. code-block:: proto

    "id": "",


To do -- is there a useful identifier here that goes beyond the identifier for the subject?


subject
~~~~~~~
In this example, we use the MGI genotype id `MGI:3690326 <http://www.informatics.jax.org/allele/genoview/MGI:3690326>`_.
We use the Phenopacket to refer to a typical individual with this genotype (if the information is available, one
Phenopacket can be used per individual mouse). The two elements ``sex`` and ``karyotypicSex`` are defined as
enumerations in the protobuf code and therefore appear here with their default values. If the sex of the mouse
is important, it should be indicated specifically.

.. code-block:: json

    "subject": {
      "id": "MGI:3690326",
      "datasetId": "",
      "sex": "UNKNOWN_SEX",
      "karyotypicSex": "UNKNOWN_KARYOTYPE"
    }


To do -- is there a useful ``datasetId`` here ?


disease and htsFiles
====================
These elements are not required for transmitting data about a mouse model and are left empty.



phenotypes
~~~~~~~~~~

The ``phenotypes`` element is a list of  :ref:`phenotype` elements, here specifying the Mammalian Phenotype Ontology
terms that were used to describe the phenotypic abnormalities observed in the mouse model.

.. code-block:: json

   "phenotypes": [{
      "description": "",
      "type": {
        "id": "MP:0004044",
        "label": "aortic dissection"
      },
    "negated": false,
    "modifiers": [],
    "evidence": []
   }, {
    "description": "",
    "type": {
      "id": "MP:0000150",
      "label": "abnormal rib morphology"
    },
    "negated": false,
    "modifiers": [],
    "evidence": []
   },
  (... etc. ...)


biosamples and genes
~~~~~~~~~~~~~~~~~~~~
These elements are not required for transmitting data about a mouse model and are left empty.


variants
~~~~~~~~
The variants section can use one of several ways of expressing the variant. For mice, the
Phenopacket standard includes an element called ``mouseAllele``.
Please refer to the page on the :ref:`variant` message for further deails.
Our example refers to the
allele `Fbn1\<tm1Hcd\> <http://www.informatics.jax.org/allele/MGI:3690325>`_.

.. code-block:: json

    "variants": [{
    "genotype": {
      "id": "GENO:0000135",
      "label": "heterozygous"
    },
    "background": "involves: 129S1/Sv * 129X1/SvJ * C57BL/6J",
    "mouseAllele": {
      "id": "MGI:3690325",
      "gene": "Fbn1",
      "alleleSymbol": "tm1Hcd"
    }
  }],


The ``variant`` element includes a reference to ``background``.
This field is intended to be used to denote the genetic background of an experimental animal model.

.. code-block:: json

     "background": "involves: 129S1/Sv * 129X1/SvJ * C57BL/6J"


For mice, we recommend following the guidelines of the
`Mouse Genome Informatics Database <http://www.informatics.jax.org/mgihome/nomen/index.shtml>`_.



metadata
~~~~~~~~

The metadata element contains references to the Mammalian Phenotype Ontology and the Sequence Ontology, as well as
a link to the original publication (as indicated by its PubMed id, complemented by an optional description).

.. code-block:: json

    "metaData": {
      "createdBy": "Peter",
      "submittedBy": "",
      "resources": [{
        "id": "mp",
        "name": "mammalian phenotype ontology",
        "namespacePrefix": "MP",
        "url": "http://purl.obolibrary.org/obo/mp.owl",
        "version": "2019-03-08",
        "iriPrefix": "http://purl.obolibrary.org/obo/MP_"
      }, {
        "id": "geno",
        "name": "Genotype Ontology",
        "namespacePrefix": "GENO",
        "url": "http://purl.obolibrary.org/obo/geno.owl",
        "version": "19-03-2018",
        "iriPrefix": "http://purl.obolibrary.org/obo/GENO_"
      }],
      "updated": [],
      "externalReferences": [{
        "id": "PMID:15254584",
        "description": "Heterozygous Fbn1 C1039G mutation. Judge DP, Biery NJ, Keene DR, Geubtner J, Myers L, Huso DL, Sakai LY, Dietz\nHC. Evidence for a critical contribution of haploinsufficiency in the complex\npathogenesis of Marfan syndrome. J Clin Invest. 2004;114(2):172-81."
      }]
    }

