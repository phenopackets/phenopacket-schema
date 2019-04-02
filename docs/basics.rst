==================
Phenopacket basics
==================

The phenopacket schema is defined in two files that are maintained in
the `phenopacket-schema GitHub repository <https://github.com/phenopackets/phenopacket-schema>`_. We will introduce
the basic concepts of the Phenopacket here, but refer users to the detailed explanations of individual
elements on the :ref:`rstbuildingblocks` pages.


The elements of the Phenopacket are hierarchical, whereby one element can contain others. Elements of the
Phenopacket are defined as protobuf messages. The Phenopacket schema has two protobuf files; The
file ``phenopackets.proto`` has definitions of four Elements that can be used to transmit phenotype
information for four major use cases.

1. :ref:`rstphenopacket` Information about the phenotypic findings, and optionally genetic variants or a link
to an external file such as a VCF file from an exome or genome sequencing experiment.


messages ``Phenopacket``, ``Family``, ``Cohort`` and
``Biosample``, which are





The header specifies that we are using protobuf version 3 and defines the package name. It imports the ``base.proto`` file mentioned above. The
command ``option java_multiple_files = true;`` puts each top-level message type from the .proto file into an independent .java file. The message then goes on to define the main components of the Phenopacket.
