====================================
A complete example in Java: Oncology
====================================

We will now demonstrate how to construct a phenopacket in Java to represent a case in which several biosamples are
created to represent biopsies.






Infiltrating urothelial carcinoma: A case report
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
We here present a case report about an individual
with infiltrating urothelial carcinoma (NCIT:C39853). Three somatic variants were identified in this sample which
are judged to be related to tumorigenesis. Additionally, a secondary finding of prostate carcinoma is made. The
two distal ureter segments are found to be cancer-free. A pelvic lymph node is found to have a metastasis, but no molecular
investyigation was made. The phenopacket can be serialized a
nd deserialized as shown in the section on :ref:`Serialization`. Here, we will show how to export the phenopacket in
JSON format.

Representing the disease diagnosis
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

We recommend using the National Cancer Institute's Thesaurus codes to represent cancer diagnoses, but any
relevant ontology term can be used. The following Java code creates a Phenopacket ``Disease`` object.

.. code-block:: java

     private Disease infiltratingUrothelialCarcinoma() {
            return Disease.newBuilder()
                .setId("NCIT:C39853")
                .setLabel("Infiltrating Urothelial Carcinoma")
                .build();
    }


Building a Biosample object
~~~~~~~~~~~~~~~~~~~~~~~~~~~

We use the following function to make it easier to create biosample objects.

.. code-block:: java

  private Biosample biosampleBuilder(String patientId, String sampleId, String age, OntologyClass sampleType, List<Phenotype> phenotypes) {
        return Biosample.newBuilder().
                setIndividualId(patientId).
                setId(sampleId).
                setIndividualAgeAtCollection(Age.newBuilder().
                        setAge(age).
                        build()).
                setType(sampleType).
                addAllPhenotypes(phenotypes).
                build();
    }

We create five biosamples for this example. This is the biosample for the primary biopsy of the bladder cancer specimen.

.. code-block:: java

   private Biosample bladderBiopsy() {
        String sampleId = "sample1";
        // left wall of urinary bladder
        OntologyClass sampleType = ontologyClass("UBERON_0001256", "wall of urinary bladder");
        // also want to mention the procedure, Prostatocystectomy (NCIT:C94464)
        ImmutableList.Builder<Phenotype> builder = new ImmutableList.Builder<>();
        //Infiltrating Urothelial Carcinoma (Code C39853)
        Phenotype infiltratingUrothelialCarcinoma = fromFinding("NCIT:C39853", "Infiltrating Urothelial Carcinoma");
        builder.add(infiltratingUrothelialCarcinoma);
        // The tumor was staged as pT2b, meaning infiltration into the outer muscle layer of the bladder wall
        // pT2b Stage Finding (Code C48766)
        Phenotype pT2b = fromFinding("NCIT:C48766", "pT2b Stage Finding");
        builder.add(pT2b);
        //pN2 Stage Finding (Code C48750)
        // cancer has spread to 2 or more lymph nodes in the true pelvis (N2)
        Phenotype pN2 = fromFinding("NCIT:C48750", "pN2 Stage Finding");
        builder.add(pN2);
        return biosampleBuilder(patientId, sampleId, this.ageAtBiopsy, sampleType, builder.build());
    }

TODO -- We need to add a field to say how the biopsy was generated.

TODO


Add three variants to the urothelial carcinoma
1. 	rs1242535815 chr5:g.1295228G>A (TERT promoter mutation, -124C>T
2.  	rs730882008 chr17:g.7577093C>A (ClinVar 182938), TP53
3. AKT chr14	105246551	105246551	C	T (hg37)



This is the function to create a Biosample object for the prostate biospy

.. code-block:: java

  private Phenotype fromFinding(String id, String label) {
        OntologyClass oc = ontologyClass(id, label);
        return Phenotype.newBuilder().setType(oc).build();
    }

  private Biosample prostateBiospy() {
        String sampleId = "sample2";
        //prostate
        OntologyClass sampleType = ontologyClass("UBERON:0002367", "prostate gland");
        ImmutableList.Builder<Phenotype> builder = new ImmutableList.Builder<>();
        Phenotype prostateAcinarAdenocarcinoma = fromFinding("NCIT:C5596", "Prostate Acinar Adenocarcinoma");
        Phenotype gleason7 = fromFinding("NCIT:C28091","Gleason Score 7");
        builder.add(prostateAcinarAdenocarcinoma);
        builder.add(gleason7);
        return biosampleBuilder(patientId, sampleId, this.ageAtBiopsy, sampleType, builder.build());
    }

We use the NCIT ``Negative finding`` term to represent the fact that the result of biopsy on the ureters was normal.

.. code-block:: java

   private Biosample leftUreterBiospy() {
        String sampleId = "sample3";
        OntologyClass sampleType = ontologyClass("UBERON:0001223", "left ureter");
        ImmutableList.Builder<Phenotype> builder = new ImmutableList.Builder<>();
        Phenotype normalFinding = fromFinding("NCIT:C38757", "Negative Finding");
        builder.add(normalFinding);
        return biosampleBuilder(patientId, sampleId, this.ageAtBiopsy, sampleType, builder.build());
    }

The code for the right ureter is similar.


Metadata
~~~~~~~~
The metadata section must indicate all ontologies used in the phenopacket together with their versions. More details TODO -- link to page

.. code-block:: java

 private MetaData buildMetaData() {
        return MetaData.newBuilder()
                .addResources(Resource.newBuilder()
                        .setId("ncit")
                        .setName("NCI Thesaurus OBO Edition")
                        .setNamespacePrefix("NCIT")
                        .setUrl("http://purl.obolibrary.org/obo/ncit.owl")
                        .setVersion("18.05d")
                        .build())
                .build();
    }


Putting it all together
~~~~~~~~~~~~~~~~~~~~~~~

Finally,

.. code-block:: java


  public class UrothelialCarcinomaExample {

    private final PhenoPacket phenopacket;

    private final String patientId = "patient1";
    private final String ageAtBiopsy = "P52Y2M";


    public UrothelialCarcinomaExample() {
        MetaData metaData = buildMetaData();
        Individual patient = buildPatient();

        this.phenopacket = PhenoPacket.newBuilder()
                .addIndividuals(patient)
              .addBiosamples(bladderBiopsy())
                .addBiosamples(prostateBiospy())
                .addBiosamples(leftUreterBiospy())
                .addBiosamples(rightUreterBiospy())
                .addBiosamples(pelvicLymphNodeBiospy())
                .addDiseases(infiltratingUrothelialCarcinoma())
                .setMetaData(metaData)
                .build();
    }

    private Individual buildPatient() {
        return Individual.newBuilder()
                .setId(this.patientId)
                .setDateOfBirth(Timestamp.newBuilder()
                        .setSeconds(Instant.parse("1964-03-15T00:00:00Z").getEpochSecond()))
                .build();
    }


Setting up the Java build
~~~~~~~~~~~~~~~~~~~~~~~~~
see rare disease example put in separate file