.. _rstrarediseaseexamplejava:

============
Rare Disease
============

TODO -- update to have the same example as in the test class


We will now demonstrate how to construct a phenopacket in Java to represent a case report
about an individual with `type 1 spherocytosis <https://omim.org/entry/182900>`_. We present data from a
simulated case report in which the phenotypic features and *ANK1* mutation are used to initialize a phenopacket.
The phenopacket can be serialized and deserialized as shown in the section on :ref:`Serialization`. Here, we will
show how to export the phenopacket in JSON format.


Spherocytosis: A case report
============================

A 27-year-old  woman presented with mild anemia, jaundice, and splenomegaly first observed in early childhood. Hepatomegaly was ruled out. Blood film showed about 70% spherocytes and reticulocytosis of 6.5%. The heterozygous variant NM_001142446.1:c.5620C>T was found, corresponding to chr8:41661923G>A (on Assembly GRCh38).




This code will outut the following JSON code

.. code-block:: json

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


Reading phenopackets in Java
============================
The following code demonstrates how to use Java to input a Phenopacket
that describes a patient with Human Phenotype Ontology (HPO) terms. We make
use of the open-source `phenol library <https://github.com/monarch-initiative/phenol>`_ to
input and manipulate the HPO file.

  .. code-block:: java	  

    import org.json.simple.JSONObject;
    import org.json.simple.parser.JSONParser;
    import org.phenopackets.schema.v1.Phenopacket;
    import org.phenopackets.schema.v1.core.*;
    JSONParser parser = new JSONParser();
    
    Object obj = parser.parse(new FileReader(pathToJsonPhenopacketFile));
    JSONObject jsonObject = (JSONObject) obj;
    String phenopacketJsonString = jsonObject.toJSONString();
    Phenopacket phenopack = PhenoPacketFormat.fromJson(phenopacketJsonString);
    String samplename = phenopack.getSubject().getId();
    // Get the phenotypic abnormalities that were observed in the affected individual
    Individual subject =phenoPacket.getSubject();
    List<TermId> observedPhenotypes= subject
                .getPhenotypesList()
                .stream()
                .distinct() // this removes any duplicate HPO terms that may be present
                .filter(((Predicate<Phenotype>) Phenotype::getNegated).negate()) // i.e., just take non-negated phenotypes
                .map(Phenotype::getType)
                .map(OntologyClass::getId)
                .map(TermId::of)
                .collect(ImmutableList.toImmutableList());
    // Get the excluded phenotypes (i.e., these were observed to be not present)
    List<TermId> excludedPhenotypes = subject
                .getPhenotypesList()
                .stream()
                .filter(Phenotype::getNegated) // i.e., just take negated phenotypes
                .map(Phenotype::getType)
                .map(OntologyClass::getId)
                .map(TermId::of)
                .collect(ImmutableList.toImmutableList());
    List<HtsFile> htsFileList = phenoPacket.getHtsFilesList();
    // depending on application, we may need to check that there is one (and only one) high-throughput file
    // The following code assumes that the list of HTS files contains one VCF file
    String vcfpath=null;
    String genomeAssembly=null;
    for (HtsFile htsFile : htsFileList) {
      if (htsFile.getHtsFormat().equals(HtsFile.HtsFormat.VCF)) {
        vcfpath=htsFile.getFile().getPath();
        genomeAssembly=htsFile.getGenomeAssembly().name();
      }
    }


The above code block thus extracts the same of the proband, a list of observed and excluded HPO terms, as well
as the path to the corresponding VCF file. We would expect such a VCF file to be used to coordinate the
running of a phenotype-driven genomic diagnostic analysis software that requires both a VCF file as well
as lists of observed (and optionally) excluded phenotypes.
