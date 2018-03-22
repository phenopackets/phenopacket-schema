# Phenopacket schema
[![Build Status](https://travis-ci.org/phenopackets/phenopacket-schema.svg?branch=master)](https://travis-ci.org/phenopackets/phenopacket-schema.svg?branch=master)

Repository for the inlined phenopacket schema

This is a re-defined version of the original phenopacket with a more individual-centric approach. This new approach was taken in order to simplify the code required to represent and manipulate the data and also better represent this sort of data as it is in day to day use.

Currently this is very much work in progress and breaking changes will occur. Once we have settled on something stable we will draft a 1.0.0-RELEASE and thereafter stick to semantic versioning semantics.

##Building
The project can be built using the awesome [Takari maven wrapper](https://github.com/takari/maven-wrapper) which requires no local maven installation.

To do this ```cd``` to the project root and run the wrapper scripts:
                                                    
```bash
$ ./mvnw clean install
```
or

```cmd
$ ./mvnw.cmd clean install
```

##Sign artefacts for release
There is a ```release-sign-artifacts``` profile which can be triggered with the command

```bash
$ ./mvnw clean install -DperformRelease=true
```

##Python and C++ artefacts
Building the project will automatically compile Java, Python and C++ artefacts. The Java jar file can be directly used in any Java project. For Python or C++ the build artefacts can be found at

```bash
target/generated-sources/protobuf/python
```
and
```bash
target/generated-sources/protobuf/cpp
```

Other languages will need to compile the files in ```src/main/proto``` to their desired language. The protobuf developer site has examples on how to do this, e.g [GO](https://developers.google.com/protocol-buffers/docs/gotutorial#compiling-your-protocol-buffers) or [C#](https://developers.google.com/protocol-buffers/docs/csharptutorial#compiling-your-protocol-buffers). Protobuf also supports a [host of other languages](https://github.com/google/protobuf/tree/v3.5.1#protobuf-runtime-installation).