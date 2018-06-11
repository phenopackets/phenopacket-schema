# Phenopacket schema
[![Build Status](https://travis-ci.org/phenopackets/phenopacket-schema.svg?branch=master)](https://travis-ci.org/phenopackets/phenopacket-schema.svg?branch=master)

Repository for the inlined phenopacket schema

This is a re-defined version of the original phenopacket with a more individual-centric approach. This new approach was taken in order to simplify the code required to represent and manipulate the data and also better represent this sort of data as it is in day to day use.

## Scope and Purpose
The goal of the phenopacket-schema is to define the phenotypic description of a patient/sample in the context of rare disease or cancer genomic diagnosis. It aims to provide sufficient and shareable information of the data outside of the EHR (Electronic Health Record) with the aim of enabling capturing of sufficient structured data at the point of care by a clinician or clinical geneticist for sharing with other labs or computational analysis of the data in clinical or research environments. 

The schema aims to define a common, limited set of data types which may be composed into more specialised types for data sharing between resources using an agreed upon common schema (as defined in base.proto).

This common schema has been used to define the 'Phenopacket' which is a catch-all collection of data types, specifically focused on representing rare-disease or cancer samples for both initial data capture and analysis. The phenopacket is designed to be both human and machine-readable, and to inter-operate with the HL7 Fast Healthcare Interoperability Resources Specification (aka FHIR®).  

Currently this is very much work in progress and breaking changes will occur. Once we have settled on something stable we will draft a 1.0.0-RELEASE and thereafter stick to semantic versioning semantics.

## Building
The project can be built using the awesome [Takari maven wrapper](https://github.com/takari/maven-wrapper) which requires no local maven installation. The only requirement for the build is to have a working java installation and network access. 

To do this ```cd``` to the project root and run the wrapper scripts:
                                                    
```bash
$ ./mvnw clean install
```
or

```cmd
$ ./mvnw.cmd clean install
```

## Sign artefacts for release
There is a ```release-sign-artifacts``` profile which can be triggered with the command

```bash
$ ./mvnw clean install -DperformRelease=true
```

## Java, Python and C++ artefacts
Building the project will automatically compile Java, Python and C++ artefacts. The Java jar file can be directly used in any Java project. For Python or C++ the build artefacts can be found at

```bash
target/generated-sources/protobuf/python
```
and
```bash
target/generated-sources/protobuf/cpp
```

Other languages will need to compile the files in ```src/main/proto``` to their desired language. The protobuf developer site has examples on how to do this, e.g [GO](https://developers.google.com/protocol-buffers/docs/gotutorial#compiling-your-protocol-buffers) or [C#](https://developers.google.com/protocol-buffers/docs/csharptutorial#compiling-your-protocol-buffers). Protobuf also supports a [host of other languages](https://github.com/google/protobuf/tree/v3.5.1#protobuf-runtime-installation).