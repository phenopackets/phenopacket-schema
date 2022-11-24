.. _rsga4ghpedigree:

##############
GA4GH Pedigree
##############


The Phenopacket-schema includes an implementation of the `GA4GH Pedigree Standard <https://pedigree.readthedocs.io/en/latest/index.html>`_.
GA4GH Pedigree defines a conceptual model of individuals and their relationships to allow other standards, such as
Phenopackets to be able to create an implementation of this model using native concepts. For example, the Phenopacket
schema representation of an individual in the GA4GH Pedigree is a :ref:`rstphenopacket`.

The GA4GH Pedigree Standard enables much greater expressivity of the relationships between individuals compared to the
PED model used in the :ref:`rstfamily` message due to defining the relationships using the `Kinship ontology (KIN) <http://purl.org/ga4gh/kin.owl>`_


Data model
##########

The data model described here is transcribed from the `documentation <https://pedigree.readthedocs.io/en/latest/pedigree-model.html#pedigree>`_

Pedigree
========

.. csv-table::
   :header: Field, Type, Multiplicity, Description

   id, string, 1..1, External identifier for the family being investigated
   narrative, string, 0..1, Summary of the pedigree resource for human interpretation
   date, string, 0..1, The date the pedigree was collected or last updated as ISO full or partial date i.e. YYYY or YYYY-MM or YYYY-MM-DD
   status, string, 0..1, Status of the pedigree resource collection
   index_patients, string, 0..*, Identified Individual(s) in the family of a health condition of focus being investigated.
   individuals, :ref:`rstphenopacket`, 0..*, Collection of Individual who are the members of this pedigree
   relationships, :ref:`rstga4ghrelationship`, 0..*, Collection of Relationship between the individuals who are the members of this pedigree


index_patients
~~~~~~~~~~~~~~

A list of identifiers for the individual(s) in the family affected with the health condition being investigated. These
individuals(s) are often termed the Proband, Consultand or First Person Tested Positive.


.. _rstga4ghrelationship:

Relationship
============

.. csv-table::
   :header: Field, Type, Multiplicity, Description

   individual, string, 1..1, Identifier of the subject Individual; equivalent to the Biolink "subject"
   relation, :ref:`rstontologyclass`, 1..1, The relationship the individual has to the relative.
   relative, string, 1..1, Identifier of the relative Individual; equivalent to the Biolink "object"


individual and relative
~~~~~~~~~~~~~~~~~~~~~~~

The identifier values used in the ``individual`` and ``relative`` fields **must** match a value in the **id** element
used in the :ref:`rstindividual` ``subject`` of the phenopackets listed in the ``pedigree`` ``individuals`` field.

relation
~~~~~~~~

An :ref:`rstontologyclass` from the `KIN Ontology <http://purl.org/ga4gh/kin.owl>`_ indicating the relationship the
``individual`` has to the ``relative`` e.g. if the individual is the relative's biological mother, then relation should
be isBiologicalMotherOf [KIN:027].


Example
#######

In this example we have simplified the phenopackets to include only the minimum required information to make a pedigree.
Ordinarily there should be more fields (e.g. the :ref:`rstmetadata`) within the phenopacket.

.. code-block:: yaml

    ---
    id: "FAM1"
    narrative: "A Phenopacket GA4GH Pedigree of a trio with an affected child"
    date: "2022-06-23"
    indexPatients:
    - "CHILD"
    individuals:
    - id: "1"
      subject:
        id: "MOTHER"
        sex: "FEMALE"
    - id: "2"
      subject:
        id: "FATHER"
        sex: "MALE"
    - id: "3"
      subject:
        id: "CHILD"
    relationships:
    - individualId: "MOTHER"
      relation:
        id: "KIN:027"
        label: "isBiologicalMother"
      relativeId: "CHILD"
    - individualId: "FATHER"
      relation:
        id: "KIN:028"
        label: "isBiologicalFather"
      relativeId: "CHILD"

