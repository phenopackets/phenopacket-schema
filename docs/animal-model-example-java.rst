.. _rstanimalmodelexamplejava:

========================================
A complete example in Java: Animal Model
========================================

This page explains how the Phenopacket for the :ref:`rstanimalmodelexample` was created in Java.


individual
~~~~~~~~~~

We use the :ref:`rstindividual` object to represent the phenotypic findings on an animal model.
This element can truly be an individual animal, or can be used to represent a representative
individual based on examination of a cohort of animal models.

.. code-block:: java

  /** The Genotype accession ID for  Fbn1tm1Hcd/Fbn1tm1Hcd */
    private final String MgiId = "MGI:3690326";
    Individual Fbn1C1039G_Model = Individual.newBuilder()
                .setId(MgiId)
                .build();


MouseAllele
~~~~~~~~~~~
This is the code used to represent
`Fbn1\<tm1Hcd\> <http://www.informatics.jax.org/allele/MGI:3690325>`_.
The animals used for the investigation were heterozygous

.. code-block:: java

    MouseAllele allele = MouseAllele.
                            newBuilder().
                            setId("MGI:3690325").
                            setGene("Fbn1").
                            setAlleleSymbol("tm1Hcd").
                            build();
    Variant Fbn1variant = Variant.newBuilder()
        .setMouseAllele(allele)
        .setBackground("involves: 129S1/Sv * 129X1/SvJ * C57BL/6J")
        .setZygosity(ontologyClass("GENO:0000135", "heterozygous"))
        .build();


Phenotypes
~~~~~~~~~~

We create a series of :ref:`rstphenotype` objects to represent the phenotypic observations
made on this model.

.. code-block:: java
    Phenotype increasedAortaWallThickness=Phenotype.newBuilder()
                .setType(ontologyClass("MP:0010996", " increased aorta wall thickness"))
                .build();

The eight additional :ref:`rstphenotype` objects are created analogously.


ExternalReference
~~~~~~~~~~~~~~~~~
We use an :ref:`rstexternalreference` object to represent the
`publication <https://www.ncbi.nlm.nih.gov/pubmed/15254584>`_ which
reported these phenotypes.

.. code-block:: java

    ExternalReference pubmedRef = ExternalReference.newBuilder()
        .setId("PMID:15254584")
        .setDescription("Heterozygous Fbn1 C1039G mutation. Judge DP, et al. \n" +
            "Evidence for a critical contribution of haploinsufficiency in the complex \n" +
            "pathogenesis of Marfan syndrome. J Clin Invest. 2004;114(2):172-81.")
        .build();

MetaData
~~~~~~~~
The :ref:`rstmetadata` section MUST indicate all ontologies used in the phenopacket together with their versions.
This Phenopacket used MP and GENO.

.. code-block:: java

    MetaData metaData = MetaData.newBuilder()
        .addResources(Resource.newBuilder()
            .setId("mp")
            .setName("mammalian phenotype ontology")
            .setNamespacePrefix("MP")
            .setIriPrefix("http://purl.obolibrary.org/obo/MP_")
            .setUrl("http://purl.obolibrary.org/obo/mp.owl")
            .setVersion("2019-03-08")
            .build())
        .addResources(Resource.newBuilder()
            .setId("geno")
            .setName("Genotype Ontology")
            .setNamespacePrefix("GENO")
            .setIriPrefix("http://purl.obolibrary.org/obo/GENO_")
                .setUrl("http://purl.obolibrary.org/obo/geno.owl")
                .setVersion("19-03-2018")
                .build())
        .addExternalReferences(pubmedRef)
        .setCreatedBy("Peter")
        .build();


Putting it all together
~~~~~~~~~~~~~~~~~~~~~~~
Finally, we create the :ref:`rstphenopacket` object.

.. code-block:: java

    Phenopacket.newBuilder()
        .setSubject(Fbn1C1039G_Model)
        .addPhenotypes(aorticDissection)
        .addPhenotypes(abnormalRibMorphology)
        .addPhenotypes(kyphosis)
        .addPhenotypes(overexpandedPulmonaryAlveoli)
        .addPhenotypes(abnormalAortaElasticFiberMorphology)
        .addPhenotypes(mitralValveProlapse)
        .addPhenotypes(abnormalHeartLeftAtriumMorphology)
        .addPhenotypes(abnormalHeartLeftVentricleMorphology)
        .addPhenotypes(increasedAortaWallThickness)
        .addVariants(Fbn1variant)
        .setMetaData(metaData)
        .build();