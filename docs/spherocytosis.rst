=========================================
Spherocytosis: A complete example in Java
=========================================

We will now demonstrate how to construct a phenopacket in Java to represent a case report about an individual with `type 1 spherocytosis <https://omim.org/entry/182900>`_. We present data from a simulated case report in which the phenotypic features and *ANK1* mutation are used to initialize a phenopacket. The phenopacket can be serialized and deserialized as shown in the section on :ref:`Serialization`. Here, we will show how to export the phenopacket in JSON format.


Spherocytosis: A case report
============================

A 27-year-old  woman presented with mild anemia, jaundice, and splenomegaly first observed in early childhood. Hepatomegaly was ruled out. Blood film showed about 70% spherocytes and reticulocytosis of 6.5%. The heterozygous variant NM_001142446.1:c.5620C>T was found, corresponding to chr8:41661923G>A (on Assembly GRCh38).

Setting up the Java build
=========================
To include the phenopackets-schema package from maven central, add the following to the pom file::

  <dependency>
    <groupId>org.phenopackets</groupId>
    <artifactId>phenopacket-schema</artifactId>
    <version>${phenopackets.version}</version>
  </dependency>

Define the phenopackets.version in the properties section of the pom file (the current version is 0.1.0)::

   <properties>
     <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
     ...
     <phenopackets.version>0.1.0</phenopackets.version>
     <protobuf.version>3.5.1</protobuf.version>
   </properties>
   
The Java code
=============
We show some Java code that demonstrates the basic methodology for building a Phenopacket. We have put the entire code into one function for didactic purposes, but real-life code might be more structured. We do define one auxialliary function::

   /** convenience function to help creating OntologyClass objects. */
    public static OntologyClass ontologyClass(String id, String label) {
        return OntologyClass.newBuilder()
                .setId(id)
                .setLabel(label)
                .build();
    }

With this, we present a function that creates a PhenoPacket that represents the case report described above::

  public PhenoPacket spherocytosisExample() {
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

        return PhenoPacket.newBuilder()
                .setSubject(proband)
                .addAllVariants(ImmutableList.of(ANK1_variant))
                .setMetaData(metaData)
                .build();
    }

JSON export
===========
This phenopacket can be easily serialized in (binary) protobuf format, but in some situations it may be desirable to export the PhenoPacket as `JSON <https://en.wikipedia.org/wiki/JSON>`_. This is easy with the following commands::

   PhenoPacket phenoPacket =new PhenoPacketExample().spherocytosisExample();
   try {
     System.out.println(toJson(phenoPacket));
     } catch (IOException e) {
       e.printStackTrace();
     }

This code will outut the following JSON code::

  {
  "subject": {
    "id": "PROBAND#1",
    "ageAtCollection": {
      "age": "P27Y3M"
    },
    "sex": {
      "id": "PATO:0000383",
      "label": "female"
    },
    "phenotypes": [{
      "type": {
        "id": "HP:0004444",
        "label": "Spherocytosis"
      },
      "classOfOnset": {
        "id": "HP:0011463",
        "label": "Childhood onset"
      }
    }, {
      "type": {
        "id": "HP:0000952",
        "label": "Jaundice"
      },
      "classOfOnset": {
        "id": "HP:0011463",
        "label": "Childhood onset"
      }
    }, {
      "type": {
        "id": "HP:0001744",
        "label": "Splenomegaly"
      },
      "classOfOnset": {
        "id": "HP:0011463",
        "label": "Childhood onset"
      }
    }, {
      "type": {
        "id": "HP:0002240",
        "label": "Hepatomegaly"
      },
      "negated": true
    }, {
      "type": {
        "id": "HP:0001923",
        "label": "Reticulocytosis"
      }
    }]
  },
  "variants": [{
    "sequence": "NM_001142446.1",
    "position": 5620,
    "deletion": "C",
    "insertion": "T",
    "hgvs": "NM_001142446.1:c.5620C>T ",
    "sampleGenotypes": {
      "PROBAND#1": {
        "id": "GENO:0000135",
        "label": "heterozygous"
      }
    }
  }],
  "metaData": {
    "createdBy": "Example clinician",
    "resources": [{
      "id": "hp",
      "name": "human phenotype ontology",
      "namespacePrefix": "HP",
      "url": "http://purl.obolibrary.org/obo/hp.owl",
      "version": "2018-03-08",
      "iriPrefix": "http://purl.obolibrary.org/obo/HP_"
    }, {
      "id": "pato",
      "name": "Phenotype And Trait Ontology",
      "namespacePrefix": "PATO",
      "url": "http://purl.obolibrary.org/obo/pato.owl",
      "version": "2018-03-28",
      "iriPrefix": "http://purl.obolibrary.org/obo/PATO_"
    }, {
      "id": "geno",
      "name": "Genotype Ontology",
      "namespacePrefix": "GENO",
      "url": "http://purl.obolibrary.org/obo/geno.owl",
      "version": "19-03-2018",
      "iriPrefix": "http://purl.obolibrary.org/obo/GENO_"
    }]
  }
  }

The phenopackets-schema offers many more functions to create phenopackets for special situations. We refer interested readers to the protobuf and the example Java code in the phenopackets-schema repository.
