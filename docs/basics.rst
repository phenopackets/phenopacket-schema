.. _rstbasics:

==================
Phenopacket basics
==================

The phenopacket schema is defined in two files that are maintained in
the `phenopacket-schema GitHub repository <https://github.com/phenopackets/phenopacket-schema>`_. We will introduce
the basic concepts of the Phenopacket here, but refer users to the detailed explanations of individual
elements on the :ref:`rstbuildingblocks` pages.


The elements of the Phenopacket are hierarchical, whereby one element can contain others. Elements of the
Phenopacket are defined as protobuf *messages*. The Phenopacket schema has three protobuf files,
``phenopackets.proto``, ``interpretation.proto``, and ``base.proto``.



phenopackets.proto
~~~~~~~~~~~~~~~~~~
The
file ``phenopackets.proto`` has definitions of four Elements that can be used to transmit phenotype
information for four major use cases.

1. :ref:`rstphenopacket`: Information about the phenotypic findings, and optionally genetic variants or a link
to an external file such as a VCF file from an exome or genome sequencing experiment.

2. :ref:`rstfamily`: Information about the proband (index patient) and relatives of the proband (pedigree) required for a genomic diagnosis.

3. :ref:`rstcohort`: Information about a group of individuals related in some phenotypic or genotypic aspect.

4. :ref:`rstbiosample`: Information biological material such as a tumor biopsy that was used for genomic analysis.


interpretation.proto
~~~~~~~~~~~~~~~~~~~~





base.proto
~~~~~~~~~~
The ``base.proto`` file contains the :ref:`rstbuildingblocks` used to construct the larger elements in
``phenopackets.proto`` and ``interpretation.proto``.


The header specifies that we are using protobuf version 3 and defines the package name. It imports the ``base.proto`` file mentioned above. The
command ``option java_multiple_files = true;`` puts each top-level message type from the .proto file into an independent .java file. The message then goes on to define the main components of the Phenopacket.
