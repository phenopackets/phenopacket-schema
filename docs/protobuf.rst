.. _rstprotobuf:

================================
A short introduction to protobuf
================================

Phenopackets schema uses protobuf, an exchange format developed in 2008 by Google. We refer readers to the
excellent `Wikipedia page on Protobuf <https://en.wikipedia.org/wiki/Protocol_Buffers>`_ and
to `Google's documentation <https://developers.google.com/protocol-buffers/>`_ for details. This page
intends to get curious readers who are unfamiliar with protobuf up to speed with the main aspects of this
technology, but it is not necessary to understand protobuf to use the phenopacket schema.

Google initially developed Protocol Buffers (protobuf) for internal use, but now has provided a code generator for multiple languages under an open source license. In this documentation, we will demonstrate use of phenopacket-schema with Java, but all of the features are available in any of the languages that protobuf works with including C++ and Python.


The major advantages of protobuf are that it is language-neutral, faster than many other schema languages such as XML and JSON, and can be simpler to use because of features such as automatic validation of data objects.


Protobuf forsees that data structures (so-called **messages**) are defined in a definition file (with the suffix .proto) and compiled to generate code that is invoked by the sender or recipient of the data to encode/decode the data.


~~~~~~~~~~~~~~~~~~~
Installing protobuf
~~~~~~~~~~~~~~~~~~~

The following exercise is not necessary to use phenopacket-schema,
but is intended to build intuition for how protobuf works.
We first need to install protobuf (Note that these instructions are for this tutorial only. The maven system will automatically
pull in protobuf for phenopackets schema). We show one simple way of installing protobuf on a linux system in the following.

1. Download the source code from the `protobuf GitHub page <https://github.com/protocolbuffers/protobuf>`_. Most users should download the latest tar.gz archive from the Release page. Extract the code.

2. Install the code as follows (to do so, you will need the packages autoconf, automake, libtool, curl, make, g++, unzip).

.. code-block:: bash

   ./configure
   make
   make check
   sudo make install
   sudo ldconfig # refresh shared library cache.


You now should check if installation was successful

.. code-block:: bash

  $ protoc --version
  libprotoc 3.8.0

~~~~~~~~~~~~~~~~~
An example schema
~~~~~~~~~~~~~~~~~

protobuf represents data as messages whose fields are indicated and aliased with a number and tag. Fields can be required, optional, or repeated.
The following message describes a dog. The name is represented as a string, and the field is indicated with the number 1. The weight of the dog is represented as an integer.
The toys field can store multiple items represented as a string. Note that in protobuf3,
it is not possible to define a field as required.

.. code-block:: proto

    syntax = "proto3";
    
    message dog {
      required string name = 1;
      int32 weight = 2;
      repeated string toys = 4;
      }

We can compile this code with the following command

.. code-block:: bash

  $ protoc -I=. --java_out=. dog.proto 

This will generate a Java file called ``Dog.java`` with code to create, import, and export protobuf data. For example, the weight field is represented as follows.

.. code-block:: java
    
    public static final int WEIGHT_FIELD_NUMBER = 2;
    private int weight_;
    public int getWeight() {
      return weight_;
    }


It is highly recommended to peruse the complete Java file, but we will leave that as an exercise for the reader.

~~~~~~~~~~~~~~~~~~~~~~~~
Using the generated code
~~~~~~~~~~~~~~~~~~~~~~~~

We can now easily use a generated code to create Java instance of the Dog class. We will not provide a complete maven tutorial here, but the
key things that need to be done to get this to work are the following.

1. set up a maven-typical directory structure such as::

     src
     --main
     ----java
     ------org
     --------example
     ----proto


Add the following to the dependencies

.. code-block:: xml

    <dependency>
      <groupId>com.google.protobuf</groupId>
      <artifactId>protobuf-java</artifactId>
      <version>3.5.1</version>
    </dependency>
   
and add the following to the plugin section

.. code-block:: xml

    <plugin>
      <groupId>org.xolstice.maven.plugins</groupId>
      <artifactId>protobuf-maven-plugin</artifactId>
      <version>0.5.1</version>
      <extensions>true</extensions>
      <configuration>
        <protocExecutable>/usr/local/bin/protoc</protocExecutable>
      </configuration>
      <executions>
        <execution>
          <goals>
            <goal>compile</goal>
            <goal>test-compile</goal>
          </goals>
        </execution>
      </executions>
    </plugin>

This is the simplest configuration of the `xolstice plugin <https://www.xolstice.org/protobuf-maven-plugin/usage.html>`_; see the documentation for further information. We have assumed that protoc is installed in /usr/local/bin in the above, and the path may need to be adjusted on your system.


Add the protobuf definition to the proto directory. Add a class such as *Main.java* in the /src/main/java/org/example directory (package: org.example). For simplicity, the following code snippets could be written in the main method

.. code-block:: java

   String name = "Fido";
   int weight = 5;
   String toy1="bone";
   String toy2="ball";
   
   Dog.dog fido = Dog.dog.newBuilder()
                .setName(name).
                setWeight(weight).
                addToys(toy1).
                addToys(toy2).
                build();
		
    System.out.println(fido.getName() + "; weight: " + fido.getWeight() + "kg;  favorite toys: "
        + fido.getToysList().stream().collect(Collectors.joining("; ")));



The code can be compiled with

.. code-block:: bash

  $ mvn clean package

If we run the demo app, it should output the following. ::

    Fido; weight: 5kg;  favorite toys: bone; ball``.


Serialization
=============

The following code snippet serializes the Java object fido and writes the serialized message to disk, then reads the message and displays it.

.. code-block:: java

        try {
            // serialize
            String filePath="fido.pb";
            FileOutputStream fos = new FileOutputStream(filePath);
            fido.writeTo(fos);
            // deserialize
            Dog.dog deserialized
                    = Dog.dog.newBuilder()
                    .mergeFrom(new FileInputStream(filePath)).build();

            System.out.println("deserialized: "+deserialized.getName() + "; weight: " + deserialized.getWeight() + "kg;  favorite toys: "
                    + deserialized.getToysList().stream().collect(Collectors.joining("; ")));

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

The code should output the following. ::

    deserialized: Fido; weight: 5kg;  favorite toys: bone; ball

We hope that this brief introduction was useful and refer to `Google's documentation <https://developers.google.com/protocol-buffers/>`_ for more details. 
