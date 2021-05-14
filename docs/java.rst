.. _rstjava:

#################################
Working with Phenopackets in Java
#################################


Here we provide some guidance on how to work with Phenopackets in Java. The sections on
:ref:`rstjavabuild`, :ref:`rstjavaexport` provide general guidance about using Java
to work with phenopackets. The following sections provide a few examples of how to build
various elements of Phenopackets. Finally, we present the full Java code used to build
each of the examples for :ref:`rare disease <rstrarediseaseexamplejava>` and
:ref:`cancer <rstcancerexamplejava>`.


.. toctree::
   :maxdepth: 1

   including phenopackets in Java projects <java-build>
   Phenopacket File I/O in Java <java-export>
   evidence <java-evidence>
   timestamp <java-timestamp>
   duration <java-duration>


The Java code
=============
We show some Java code that demonstrates the basic methodology for building a Phenopacket. We have put the entire code into one function for didactic purposes, but real-life code might be more structured. We do define one auxialliary function

.. code-block:: java

   /** convenience function to help creating OntologyClass objects. */
    public static OntologyClass ontologyClass(String id, String label) {
        return OntologyClass.newBuilder()
                .setId(id)
                .setLabel(label)
                .build();
    }

With this, we present a function that creates a Phenopacket that represents the case report described above

.. code-block:: java

  public Phenopacket spherocytosisExample() {
        final String PROBAND_ID = "PROBAND#1";
        PhenotypicFeature spherocytosis = PhenotypicFeature.newBuilder()
                .setType(ontologyClass("HP:0004444", "Spherocytosis"))
                .setClassOfOnset(ontologyClass("HP:0011463", "Childhood onset"))
                .build();
        PhenotypicFeature jaundice = PhenotypicFeature.newBuilder()
                .setType(ontologyClass("HP:0000952", "Jaundice"))
                .setClassOfOnset(ontologyClass("HP:0011463", "Childhood onset"))
                .build();
        PhenotypicFeature splenomegaly = PhenotypicFeature.newBuilder()
                .setType(ontologyClass("HP:0001744", "Splenomegaly"))
                .setClassOfOnset(ontologyClass("HP:0011463", "Childhood onset"))
                .build();
        PhenotypicFeature notHepatomegaly = PhenotypicFeature.newBuilder()
                .setType(ontologyClass("HP:0002240", "Hepatomegaly"))
                .setExcluded(true)
                .build();
        PhenotypicFeature reticulocytosis = PhenotypicFeature.newBuilder()
                .setType(ontologyClass("HP:0001923", "Reticulocytosis"))
                .build();

        VcfAllele vcfAllele = VcfAllele.newBuilder()
                .setGenomeAssembly("GRCh37")
                .setChr("8")
                .setPos(41519441)
                .setRef("G")
                .setAlt("A")
                .build();

        Variant ANK1_variant = Variant.newBuilder()
                .setVcfAllele(vcfAllele)
                .setZygosity(ontologyClass("GENO:0000135", "heterozygous"))
                .build();

        Individual proband = Individual.newBuilder()
                .setSex(Sex.FEMALE)
                .setId(PROBAND_ID)
                .setAgeAtCollection(Age.newBuilder().setAge("P27Y3M").build())
                .build();

        MetaData metaData = MetaData.newBuilder()
                .addResources(Resource.newBuilder()
                        .setId("hp")
                        .setName("human phenotype ontology")
                        .setNamespacePrefix("HP")
                        .setIriPrefix("http://purl.obolibrary.org/obo/HP_")
                        .setUrl("http://purl.obolibrary.org/obo/hp.owl")
                        .setVersion("2018-03-08")
                        .build())
                .addResources(Resource.newBuilder()
                        .setId("geno")
                        .setName("Genotype Ontology")
                        .setNamespacePrefix("GENO")
                        .setIriPrefix("http://purl.obolibrary.org/obo/GENO_")
                        .setUrl("http://purl.obolibrary.org/obo/geno.owl")
                        .setVersion("19-03-2018")
                        .build())
                .setCreatedBy("Example clinician")
                .build();

        return Phenopacket.newBuilder()
                .setSubject(proband)
                .addPhenotypicFeatures(spherocytosis)
                .addPhenotypicFeatures(jaundice)
                .addPhenotypicFeatures(splenomegaly)
                .addPhenotypicFeatures(notHepatomegaly)
                .addPhenotypicFeatures(reticulocytosis)
                .addAllVariants(ImmutableList.of(ANK1_variant))
                .setMetaData(metaData)
                .build();
    }


Writing a Phenopacket in protobuf format
========================================

Messages can be written in binary format using the native protobuf encoding. While this is useful for machine-to-machine
 communication due to low latency and overhead of serialisation it is not human-readable. We refer the reader to the official
 `documentation <https://developers.google.com/protocol-buffers/docs/javatutorial#parsing-and-serialization>`_ on this topic,
 but will briefly give an example of writing to an ``OutputStream`` here.

.. code-block:: java

    Path path = Paths.get("/path/to/file");
    try (OutputStream outputStream = Files.newOutputStream(path)) {
        Phenopacket phenoPacket = new PhenoPacketExample().spherocytosisExample();
        phenoPacket.writeTo(outputStream);
    } catch (IOException e) {
        e.printStackTrace();
    }

    // read it back again
    try (InputStream inputStream = Files.newInputStream(path)) {
        Phenopacket deserialised = Phenopacket.parseFrom(inputStream);
    } catch (IOException e) {
        e.printStackTrace();
    }


JSON export
===========
In many situations it may be desirable to export the Phenopacket as `JSON <https://en.wikipedia.org/wiki/JSON>`_. This
is easy with the following commands:

First add the protobuf-java-util dependency to your Maven POM.xml

.. code-block:: xml

    <dependency>
        <groupId>com.google.protobuf</groupId>
        <artifactId>protobuf-java-util</artifactId>
        <version>${protobuf.version}</version>
        <scope>test</scope>
    </dependency>


Then you can use it to print out JSON using the `JsonFormat` class.

.. code-block:: java

        Phenopacket p = spherocytosisExample();
        try {
            String jsonString = JsonFormat.printer().includingDefaultValueFields().print(p);
            System.out.println(jsonString);
        } catch (Exception e) {
            e.printStackTrace();
        }
