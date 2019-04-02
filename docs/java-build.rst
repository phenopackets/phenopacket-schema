.. _rstjavabuild:

==========
Java Build
==========

Most users of phenopackets-schema in Java should use maven central to include the phenopackets-schema package.



Setting up the Java build
~~~~~~~~~~~~~~~~~~~~~~~~~
To include the phenopackets-schema package from maven central, add the following to the pom file

Define the phenopackets.version in the properties section of the pom.xml file (the current version is 0.3.0).

.. code-block:: xml

   <properties>
     <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
     ...
     <phenopackets.version>0.3.0</phenopackets.version>
   </properties>

Then put the following stanza into the ``dependencies`` section of the maven pom.xml file.

.. code-block:: xml

  <dependency>
    <groupId>org.phenopackets</groupId>
    <artifactId>phenopacket-schema</artifactId>
    <version>${phenopackets.version}</version>
  </dependency>


Building phenopackets-schema locally
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Users can also download phenopackets-schema from its
`GitHub repository <https://github.com/phenopackets/phenopacket-schema>`_
and install it locally. ::

    $ git clone https://github.com/phenopackets/phenopacket-schema
    $ cd phenopacket-schema
    $ mvn compile
    $ mvn install

