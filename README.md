Phenopacket schema
==================

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.phenopackets/phenopacket-schema/badge.svg)](https://maven-badges.herokuapp.com/maven-central/org.phenopackets/phenopacket-schema)

[![PyPI version](https://badge.fury.io/py/phenopackets.svg)](https://badge.fury.io/py/phenopackets)

[![Crate](https://img.shields.io/crates/v/phenopacket-schema.svg)](https://crates.io/crates/phenopacket-schema)

[![Documentation](https://readthedocs.org/projects/phenopacket-schema/badge/?version=v2)](https://phenopacket-schema.readthedocs.io/en/v2/?badge=v2)


This has been produced as part of the [GA4GH](https://ga4gh.org) [Clinical Phenotype Data Capture Workstream](https://ga4gh-cp.github.io/) and it merges the existing [GA4GH metadata-schemas](https://github.com/ga4gh-metadata/metadata-schemas) work with a more focused model from the [phenopacket-reference-implementation](https://github.com/phenopackets/phenopacket-reference-implementation).

This is a re-defined version of the original phenopacket with a more individual-centric approach. This new approach was taken in order to simplify the code required to represent and manipulate the data and also better represent this sort of data as it is in day to day use.

Documentation
=============

The core documentation can be found at [Documentation](https://phenopacket-schema.readthedocs.io/en/latest)

The documentation in this README is primarily for the users of the phenopacket-schema java library.


Scope and Purpose
=================
The goal of the phenopacket-schema is to define the phenotypic description of a patient/sample in the context of rare disease or cancer genomic diagnosis. It aims to provide sufficient and shareable information of the data outside of the EHR (Electronic Health Record) with the aim of enabling capturing of sufficient structured data at the point of care by a clinician or clinical geneticist for sharing with other labs or computational analysis of the data in clinical or research environments.

The schema aims to define a common, limited set of data types which may be composed into more specialised types for data sharing between resources using an agreed upon common schema (as defined in base.proto).

This common schema has been used to define the 'Phenopacket' which is a catch-all collection of data types, specifically focused on representing rare-disease or cancer samples for both initial data capture and analysis. The phenopacket is designed to be both human and machine-readable, and to inter-operate with the HL7 Fast Healthcare Interoperability Resources Specification (aka FHIR®).  

Versioning
==========

The library uses semantic versioning. See https://semver.org for details.

Email list
==========
There is a low-volume mailing list for announcements about phenopackets at phenopackets@groups.io. More information
about this list is available at https://groups.io/g/phenopackets.


Usage
=====
The Phenopacket schema is defined using [Protobuf](https://protobuf.dev/) which is `"a language-neutral, platform-neutral extensible mechanism for serializing structured data"`.  There are two ways to use this library, firstly using the `Phenopacket` as an exchange mechanism, secondly as a schema of basic types on which to build more specialist messages, yet allow for easy interoperability with other resources using the phenopackets schema.
The following sections describe how to achieve these two things.


Include phenopackets into your project
--------------------------------------

**Java** people can incorporate phenopackets into their code by importing the jar using maven:

```xml
<dependency>
    <groupId>org.phenopackets</groupId>
    <artifactId>phenopacket-schema</artifactId>
    <version>${phenopacket-schema.version}</version>
</dependency>
```

Using phenopackets in **Python** is also straightforward:

```bash
pip install phenopackets
```

Exchanging Phenopackets directly
--------------------------------
Examples on how these can be used can be found in the test directory. There are no explicit relationships defined between fields in the phenopacket (apart from the Pedigree), so it is vital that resources exchanging phenopackets agree on what is valid and what the fields mean in relation to other fields in the phenopacket. For example the `Phenopacket.genes` field may be agreed upon as representing the genes for a gene panel in one context, or a set of candidate genes or perhaps a diagnosed causative gene.

JSON/YAML formats
-----------------
A Phenopacket can be transformed between the native binary format and JSON using the `JsonFormat` class from the `protobuf-java-util` library. This will also need to be added to your pom.xml

```xml
<dependency>
	<groupId>com.google.protobuf</groupId>
	<artifactId>protobuf-java-util</artifactId>
	<version>${protobuf.version}</version>
</dependency>
```

```bash
pip install protobuf
```

`protobuf-java-util` for java and `protobuf` for python contain simple utility methods to perform these transformations. Usage is shown here:

```java
// Transform a Phenopacket into JSON
Phenopacket original = TestExamples.rareDiseasePhenopacket();

String asJson = JsonFormat.printer().print(original);
System.out.println(asJson);

// Convert the JSON back to a Phenopacket
Phenopacket.Builder phenoPacketBuilder = Phenopacket.newBuilder();
JsonFormat.parser().merge(jsonString, phenoPacketBuilder);
Phenopacket fromJson = phenoPacketBuilder.build();

// Convert the JSON into YAML (using Jackson)
JsonNode jsonNodeTree = new ObjectMapper().readTree(jsonString);
String yamlPhenopacket = new YAMLMapper().writeValueAsString(jsonNodeTree);

// Convert the YAML back into JSON (using Jackson)
JsonNode jsonNodeTree = new YAMLMapper().readTree(yamlString);
String jsonPhenopacket = new ObjectMapper().writeValueAsString(jsonNodeTree);

// And finally back into a Java object
Phenopacket.Builder phenoPacketBuilder2 = Phenopacket.newBuilder();
JsonFormat.parser().merge(jsonPhenopacket, phenoPacketBuilder2);
Phenopacket fromJson2 = phenoPacketBuilder2.build();
```

```python
from google.protobuf.json_format import Parse, MessageToJson
from google.protobuf.timestamp_pb2 import Timestamp
from phenopackets import Phenopacket, Individual, PhenotypicFeature, OntologyClass

# Parsing phenopackets from json
with open('file.json', 'r') as jsfile:
    phenopacket = Parse(Phenopacket(), text=jsfile.read())

# Writing phenopackets to json
with open('file.json', 'w') as jsfile:
    subject = Individual(id="Zaphod", sex="MALE", date_of_birth=Timestamp(seconds=-123456798))
    phenotypic_features = [PhenotypicFeature(type=OntologyClass(id="HG2G:00001", label="Hoopy")),
						   PhenotypicFeature(type=OntologyClass(id="HG2G:00002", label="Frood"))]

    phenopacket = Phenopacket(id="PPKT:1", subject=subject, phenotypic_features=phenotypic_features)

    json = MessageToJson(phenopacket)
    jsfile.write(json)
```

Building new messages from the schema
-------------------------------------
There is an example of how to do this included in the [mme.proto](https://github.com/phenopackets/phenopacket-schema/blob/master/src/test/proto/org/matchmakerexchange/api/v1/mme.proto) file. Here the Matchmaker Exchange (MME) API has been implemented using the phenopackets schema, defining custom messages as required, but re-using messages from [base.proto](https://github.com/phenopackets/phenopacket-schema/blob/master/src/main/proto/org/phenopackets/schema/v1/core/base.proto) where applicable. Using the above example, perhaps the `Phenopacket.genes` is a problem as you wish to record not only the gene panels ordered, but also the candidate genes discovered in two separate fields. In this case, a new bespoke message could be created, using the `Gene` as a building block.


Git Submodules
==============
This repo uses [git submodules](https://git-scm.com/book/en/v2/Git-Tools-Submodules) to import the [VRS protobuf](https://github.com/ga4gh/vrs-protobuf) implementation. You may need to use the following command after cloning/update
for things to build correctly:

```bash
$ git submodule update --init --recursive
```


Building
========
The project can be built using the awesome [Takari maven wrapper](https://github.com/takari/maven-wrapper) which requires no local maven installation. The only requirement for the build is to have a working java installation and network access.

To do this ``cd`` to the project root and run the wrapper scripts:
                                                    
```bash
$ ./mvnw clean install
```

or

```bash
$ ./mvnw.cmd clean install
```


Sign artefacts for release
==========================
There is a `release-sign-artifacts` profile for **Java** which can be triggered with the command

```bash
$ ./mvnw clean install -DperformRelease=true
```

The **Python** artefacts are released by running::

Test

```bash
$ bash deploy-python.sh release-test
```

Production

```bash
$ bash deploy-python.sh release-prod
```

Java, Python and C++ artefacts
==============================
Building the project will automatically compile Java, Python and C++ artefacts. The Java jar file can be directly used in any Java project. For Python or C++ the build artefacts can be found at

```
target/generated-sources/protobuf/python
```

and

```
target/generated-sources/protobuf/cpp
```

Other languages will need to compile the files in `src/main/proto` to
their desired language. The protobuf developer site has examples on how
to do this, e.g [GO](https://protobuf.dev/getting-started/gotutorial/) or [C#](https://protobuf.dev/getting-started/csharptutorial/). Protobuf also supports a [host of other
languages](https://protobuf.dev/reference/other/).

