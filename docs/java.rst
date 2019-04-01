=================================
Working with Phenopackets in Java
=================================


Here we provide some guidance on how to work with Phenopackets in Java. The code produces the :ref:`spherocytosis`
Phenopacket that is explained in the Examples section.



.. toctree::
   :maxdepth: 1

   evidence <java-evidence>
   timestamp <java-timestamp>


Setting up the Java build
=========================
To include the phenopackets-schema package from maven central, add the following to the pom file

.. code-block:: xml

  <dependency>
    <groupId>org.phenopackets</groupId>
    <artifactId>phenopacket-schema</artifactId>
    <version>${phenopackets.version}</version>
  </dependency>

Define the phenopackets.version in the properties section of the pom file (the current version is 0.1.0)

.. code-block:: xml

   <properties>
     <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
     ...
     <phenopackets.version>0.1.0</phenopackets.version>
     <protobuf.version>3.5.1</protobuf.version>
   </properties>

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
        final OntologyClass FEMALE = ontologyClass("PATO:0000383", "female");
        Phenotype spherocytosis = Phenotype.newBuilder()
                .setType(ontologyClass("HP:0004444", "Spherocytosis"))
                .setClassOfOnset(ontologyClass("HP:0011463", "Childhood onset"))
                .build();
        Phenotype jaundice = Phenotype.newBuilder()
                .setType(ontologyClass("HP:0000952", "Jaundice"))
                .setClassOfOnset(ontologyClass("HP:0011463", "Childhood onset"))
                .build();
        Phenotype splenomegaly = Phenotype.newBuilder()
                .setType(ontologyClass("HP:0001744", "Splenomegaly"))
                .setClassOfOnset(ontologyClass("HP:0011463", "Childhood onset"))
                .build();
        Phenotype notHepatomegaly = Phenotype.newBuilder()
                .setType(ontologyClass("HP:0002240", "Hepatomegaly"))
                .setNegated(true)
                .build();
        Phenotype reticulocytosis = Phenotype.newBuilder()
                .setType(ontologyClass("HP:0001923", "Reticulocytosis"))
                .build();

        Variant ANK1_variant = Variant.newBuilder()
                .setSequence("NM_001142446.1")
                .setPosition(5620)
                .setDeletion("C")
                .setInsertion("T")
                .setHgvs("NM_001142446.1:c.5620C>T ")
                .putSampleGenotypes(PROBAND_ID, ontologyClass("GENO:0000135", "heterozygous"))
                .build();

        Individual proband = Individual.newBuilder()
                .setSex(FEMALE)
                .setId(PROBAND_ID)
                .setAgeAtCollection(Age.newBuilder().setAge("P27Y3M").build())
                .addPhenotypes(spherocytosis)
                .addPhenotypes(jaundice)
                .addPhenotypes(splenomegaly)
                .addPhenotypes(notHepatomegaly)
                .addPhenotypes(reticulocytosis)
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
                        .setId("pato")
                        .setName("Phenotype And Trait Ontology")
                        .setNamespacePrefix("PATO")
                        .setIriPrefix("http://purl.obolibrary.org/obo/PATO_")
                        .setUrl("http://purl.obolibrary.org/obo/pato.owl")
                        .setVersion("2018-03-28")
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
In many situations it
may be desirable to export the Phenopacket as `JSON <https://en.wikipedia.org/wiki/JSON>`_. This is easy with
the following commands

.. code-block:: java

   Phenopacket phenoPacket = new PhenoPacketExample().spherocytosisExample();
   try {
     System.out.println(toJson(phenoPacket));
     } catch (IOException e) {
       e.printStackTrace();
     }