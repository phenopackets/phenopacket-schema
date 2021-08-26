.. _rstcancerexamplejava:

====================================
A complete example in Java: Oncology
====================================

This example shows how to create the Phenopacket that was explained :ref:`here<rstcancerexample>`.


subject
~~~~~~~

We create an object to represent the proband as an :ref:`rstindividual`.

.. code-block:: java

  private Individual subject() {
        return Individual.newBuilder()
                .setId(this.patientId)
                .setSex(Sex.MALE)
                .setDateOfBirth(Timestamp.newBuilder()
                        .setSeconds(Instant.parse("1964-03-15T00:00:00Z").getEpochSecond()))
                .build();
    }



phenotypicFeatures
~~~~~~~~~~~~~~~~~~
There are two categories of phenotypes that can be of interest with cancer data. Firstly, there
are constitutional phenotypes such as weight loss that are related to the disease of cancer. Second,
the tumor, and is applicable metasases, each have their own phenotypes including histology and grade.
The Phenopacket standard represents constitutional Phenotypes using a list of :ref:`rstphenotype`
elements, and represents phenotypes of the tumor and metastases in :ref:`rstbiosample` elements.
In the present case, the patient was found to have hematuria and severe dysuria, which are coded as follows.


.. code-block:: java


        PhenotypicFeature hematuria = PhenotypicFeature.newBuilder()
                .setType(ontologyClass("HP:0000790","Hematuria"))
                .build();
        PhenotypicFeature dsyuria = PhenotypicFeature.newBuilder()
                .setType(ontologyClass("HP:0100518","Dysuria"))
                .setSeverity(ontologyClass("HP:0012828","Severe"))
                .build();


File
~~~~~~~
We use three :ref:`File <rstfile>` objects in this Phenopacket. One represents the pair normal germline
whole-genome sequence (WGS) VCF file, one one each represents somatic WGS data from the bladder carcinoma
specimen and from the metastasis specimen. All three packets are created analogously. Here is the
code for the bladder carcinoma WGS file.

.. code-block:: java

    public File createSomaticHtsFile() {
        // first create a File
        // We are imagining there is a reference to a VCF file for a normal germline genome seqeunce
        String path = "file://data/genomes/urothelial_ca_wgs.vcf.gz";
        String description = "Urothelial carcinoma sample";
        // Now create an HtsFile object
        return HtsFile.newBuilder()
                .setHtsFormat(HtsFile.HtsFormat.VCF)
                .setGenomeAssembly("GRCh38")
                .setUri(path)
                .setDescription(description)
                .putIndividualToSampleIdentifiers("sample1", "BS342730")
                .build();
    }


biosamples
~~~~~~~~~~
This example Phenopacket contains five :ref:`rstbiosample` objects, each of which is constructed
using a function similar to the following code, which represents the bladder carcinoma specimen.


.. code-block:: java

  private Biosample bladderBiosample() {
        String sampleId = "sample1";
        // left wall of urinary bladder
        OntologyClass sampleType = ontologyClass("UBERON_0001256", "wall of urinary bladder");
        Biosample.Builder biosampleBuilder = biosampleBuilder(patientId, sampleId, this.ageAtBiopsy, sampleType);
        // also want to mention the procedure, Prostatocystectomy (NCIT:C94464)
        //Infiltrating Urothelial Carcinoma (Code C39853)
        biosampleBuilder.setHistologicalDiagnosis(ontologyClass("NCIT:C39853", "Infiltrating Urothelial Carcinoma"));
        // A malignant tumor at the original site of growth
        biosampleBuilder.setTumorProgression(ontologyClass("NCIT:C84509", "Primary Malignant Neoplasm"));
        biosampleBuilder.addHtsFiles(createSomaticHtsFile());
        biosampleBuilder.setProcedure(Procedure.newBuilder().setCode(ontologyClass("NCIT:C5189", "Radical Cystoprostatectomy")).build());
        return biosampleBuilder.build();
    }


Normal findings
~~~~~~~~~~~~~~~
In the biosamples for the left and right ureter, normal findings were obtains. This is represented
by an :ref:`rstontologyclass` for normal (negative) findings. We recommend using the following term
from NCIT.

.. code-block:: java

    OntologyClass normalFinding = ontologyClass("NCIT:C38757", "Negative Finding");

This is used to create a "normal" :ref:`rstbiosample` object as follows.

.. code-block:: java

   private Biosample leftUreterBiosample() {
        String sampleId = "sample3";
        OntologyClass sampleType = ontologyClass("UBERON:0001223", "left ureter");
        Biosample.Builder biosampleBuilder = biosampleBuilder(patientId, sampleId, this.ageAtBiopsy, sampleType);
        OntologyClass normalFinding = ontologyClass("NCIT:C38757", "Negative Finding");
        biosampleBuilder.setHistologicalDiagnosis(normalFinding);
        biosampleBuilder.setProcedure(Procedure.newBuilder().setCode(ontologyClass("NCIT:C15189", "Biopsy")).build());
        return biosampleBuilder.build();
    }


diseases
~~~~~~~~

We recommend using the National Cancer Institute's Thesaurus codes to represent cancer diagnoses, but any
relevant ontology term can be used. The following Java code creates a  :ref:`rstdisease` object.

.. code-block:: java

     private Disease infiltratingUrothelialCarcinoma() {
        return Disease.newBuilder()
                .setTerm(ontologyClass("NCIT:C39853", "Infiltrating Urothelial Carcinoma"))
                // Disease stage here is calculated based on the TMN findings
                .addDiseaseStage(ontologyClass("NCIT:C27971", "Stage IV"))
                // The tumor was staged as pT2b, meaning infiltration into the outer muscle layer of the bladder wall
                // pT2b Stage Finding (Code C48766)
                .addClinicalTnmFinding(ontologyClass("NCIT:C48766", "pT2b Stage Finding"))
                //pN2 Stage Finding (Code C48750)
                // cancer has spread to 2 or more lymph nodes in the true pelvis (N2)
                .addClinicalTnmFinding(ontologyClass("NCIT:C48750", "pN2 Stage Finding"))
                // M1 Stage Finding
                // the tumour has spread from the original site (Metastatic Neoplasm in lymph node - sample5)
                .addClinicalTnmFinding(ontologyClass("NCIT:C48700", "M1 Stage Finding"))
                .build();
    }



Metadata
~~~~~~~~
The :ref:`rstmetadata` section MUST indicate all ontologies used in the phenopacket together with their versions.
This Phenopacket used HPO, UBERON, and NCIT. We additionally use a :ref:`rstjavatimestamp` object to
indicate the current time (at which we are creating this Phenopacket).

.. code-block:: java

    private MetaData buildMetaData() {
        long millis  = System.currentTimeMillis();
        Timestamp timestamp = Timestamp.newBuilder().setSeconds(millis / 1000)
                .setNanos((int) ((millis % 1000) * 1000000)).build();
        return MetaData.newBuilder()
                .addResources(Resource.newBuilder()
                        .setId("hp")
                        .setName("human phenotype ontology")
                        .setNamespacePrefix("HP")
                        .setIriPrefix("http://purl.obolibrary.org/obo/HP_")
                        .setUrl("http://purl.obolibrary.org/obo/hp.owl")
                        .setVersion("2019-04-08")
                        .build())
                .addResources(Resource.newBuilder()
                        .setId("uberon")
                        .setName("uber anatomy ontology")
                        .setNamespacePrefix("UBERON")
                        .setIriPrefix("http://purl.obolibrary.org/obo/UBERON_")
                        .setUrl("http://purl.obolibrary.org/obo/uberon.owl")
                        .setVersion("2019-03-08")
                        .build())
                .addResources(Resource.newBuilder()
                        .setId("ncit")
                        .setName("NCI Thesaurus OBO Edition")
                        .setNamespacePrefix("NCIT")
                        .setUrl("http://purl.obolibrary.org/obo/ncit.owl")
                        .setVersion("18.05d")
                        .build())
                .setCreatedBy("Peter R")
                .setCreated(timestamp)
                .setSubmittedBy("Peter R")
                .setPhenopacketSchemaVersion("2.0")
                .addExternalReferences(ExternalReference.newBuilder()
                        .setId("PMID:29221636")
                        .setDescription("Urothelial neoplasms in pediatric and young adult patients: A large single-center series")
                        .build())
                .build();
    }


Putting it all together
~~~~~~~~~~~~~~~~~~~~~~~

Finally, we utilize a Phenopacket builder to generate the complete Phenopacket object.

.. code-block:: java

    Phenopacket phenopacket = Phenopacket.newBuilder()
                .setId("example case")
                .setSubject(subject())
                .addPhenotypicFeatures(hematuria)
                .addPhenotypicFeatures(dsyuria)
                .addBiosamples(bladderBiosample())
                .addBiosamples(prostateBiosample())
                .addBiosamples(leftUreterBiosample())
                .addBiosamples(rightUreterBiosample())
                .addBiosamples(pelvicLymphNodeBiosample())
                .addDiseases(infiltratingUrothelialCarcinoma())
                .addHtsFiles(createNormalGermlineHtsFile())
                .setMetaData(metaData)
                .build();


Output of data
~~~~~~~~~~~~~~
There are many ways of outputting the Phenopacket in JSON format. See :ref:`rstjavaexport` for details.
The following line will output the entire Phenopacket to STDOUT as YAML, using the Jackson library.

.. code-block:: java

    String json = JsonFormat.printer().print(phenopacket);
    JsonNode jsonNodeTree = new ObjectMapper().readTree(json);
    String yaml = new YAMLMapper().writeValueAsString(jsonNodeTree);
    System.out.println(yaml);
