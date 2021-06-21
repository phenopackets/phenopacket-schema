.. _rstrarediseaseexamplejava:

============
Rare Disease
============

This page explains the Java code that was used to generate :ref:`rstrdexample`. The
complete code is available in the src/test package of this repository in the class
``BethlemMyopathyExample``.


Builders and Short cuts
~~~~~~~~~~~~~~~~~~~~~~~
The individual elements of a Phenopacket are constructed with functions provided by the protobuf framework.
These functions use the Builder pattern. For instance, to create an OntologyClass object, we use the
following code.

.. code-block:: java

    OntologyClass hematuria = OntologyClass.newBuilder()
                .setId("HP:0000790")
                .setLabel("Hematuria")
                .build();

Developers may find it easier to define convenience functions that wrap the builders. For instance, for the OntologyClass
example, we might define the following function.




.. code-block:: java

    public static OntologyClass ontologyClass(String id, String label) {
        return OntologyClass.newBuilder()
                .setId(id)
                .setLabel(label)
                .build();
    }

We will use the ``ontologyClass`` function in our examples, but otherwise show all steps for clarity.

Family members and variants
~~~~~~~~~~~~~~~~~~~~~~~~~~~

We define the names of the family members.

.. code-block:: java

    private static final String PROBAND_ID = "14 year-old boy";
    private static final String MOTHER_ID = "MOTHER";
    private static final String FATHER_ID = "FATHER";


Proband
~~~~~~~
The following function then creates the Proband object. Note how
we create OntologyClass objects for onset and severity modifiers,
and create an Evidence object that indicates the provenance of the data.

.. code-block:: java

 static Phenopacket proband() {

        OntologyClass mild = OntologyClass.newBuilder().setId("HP:0012825").setLabel("Mild").build();

        OntologyClass evidenceCode = OntologyClass.newBuilder().
                setId("ECO:0000033").
                setLabel("author statement supported by traceable reference").
                build();

        Evidence citation = Evidence.newBuilder().
                setReference(ExternalReference.newBuilder().
                        setId("PMID:30808312").
                        setDescription("COL6A1 mutation leading to Bethlem myopathy with recurrent hematuria: a case report.").
                        build()).
                setEvidenceCode(evidenceCode)
                .build();

        PhenotypicFeature decreasedFetalMovement = PhenotypicFeature.newBuilder()
                .setType(ontologyClass("HP:0001558", "Decreased fetal movement"))
                .setOnset(TimeElement.newBuilder().setOntologyClass(ontologyClass("HP:0011461", "Fetal onset")).build())
                .addEvidence(citation)
                .build();

        PhenotypicFeature absentCranialNerveAbnormality = PhenotypicFeature.newBuilder()
                .setType(ontologyClass("HP:0031910", "Abnormal cranial nerve physiology"))
                .setExcluded(true)
                .addEvidence(citation)
                .build();

        PhenotypicFeature motorDelay = PhenotypicFeature.newBuilder()
                .setType(ontologyClass("HP:0001270","Motor delay"))
                .setOnset(TimeElement.newBuilder().setOntologyClass(ontologyClass("HP:0011463","Childhood onset")))
                .setSeverity(mild)
                .build();

        PhenotypicFeature hematuria = PhenotypicFeature.newBuilder()
                .setType(ontologyClass("HP:0011463", "Macroscopic hematuria"))
                .setOnset(TimeElement.newBuilder().setAge(Age.newBuilder().setIso8601Duration("P14Y")))
                .addModifiers(ontologyClass("HP:0031796","Recurrent"))
                .addEvidence(citation)
                .build();


        Individual proband = Individual.newBuilder()
                .setSex(Sex.MALE)
                .setId(PROBAND_ID)
                .setTimeAtLastEncounter(TimeElement.newBuilder().setAge(Age.newBuilder().setIso8601Duration("P14Y")))
                .build();
        return Phenopacket.newBuilder()
                .setId(PROBAND_ID)
                .setSubject(proband)
                .addPhenotypicFeatures(decreasedFetalMovement)
                .addPhenotypicFeatures(absentCranialNerveAbnormality)
                .addPhenotypicFeatures(hematuria)
                .addPhenotypicFeatures(motorDelay)
                .build();
    }


Unaffected parents
~~~~~~~~~~~~~~~~~~

The unaffected father is coded as follows:

.. code-block:: java

   static Phenopacket unaffectedFather() {
        Individual father = Individual.newBuilder()
                .setSex(Sex.MALE)
                .setId(FATHER_ID)
                .build();
        return Phenopacket.newBuilder()
                .setSubject(father)
                .build();
    }

The mother is coded analogously. Note that in both cases, on two of the elements of the :ref:`rstphenopacket`
are actually used.

Pedigree
~~~~~~~~
The following code builds the :ref:`rstpedigree` object.

.. code-block:: java

    private static Pedigree pedigree() {
        Pedigree.Person pedProband = Pedigree.Person.newBuilder()
                .setIndividualId(PROBAND_ID)
                .setSex(Sex.MALE)
                .setMaternalId(MOTHER_ID)
                .setPaternalId(FATHER_ID)
                .setAffectedStatus(Pedigree.Person.AffectedStatus.AFFECTED)
                .build();

        Pedigree.Person pedMother = Pedigree.Person.newBuilder()
                .setIndividualId(MOTHER_ID)
                .setSex(Sex.FEMALE)
                .setAffectedStatus(Pedigree.Person.AffectedStatus.UNAFFECTED)
                .build();

        Pedigree.Person pedFather = Pedigree.Person.newBuilder()
                .setIndividualId(FATHER_ID)
                .setSex(Sex.MALE)
                .setAffectedStatus(Pedigree.Person.AffectedStatus.UNAFFECTED)
                .build();

        return Pedigree.newBuilder()
                .addPersons(pedProband)
                .addPersons(pedMother)
                .addPersons(pedFather)
                .build();
    }


Family
~~~~~~

Finally, the following code pulls everything together to build the Family object.

.. code-block:: java

    /**
     * Example taken from PMID:30808312
     */
    static Family bethlemMyopathyFamily() {

        long millis  = System.currentTimeMillis();
        Timestamp timestamp = Timestamp.newBuilder().setSeconds(millis / 1000)
                .setNanos((int) ((millis % 1000) * 1000000)).build();

        MetaData metaData = MetaData.newBuilder()
                .addResources(Resource.newBuilder()
                        .setId("hp")
                        .setName("human phenotype ontology")
                        .setNamespacePrefix("HP")
                        .setIriPrefix("http://purl.obolibrary.org/obo/HP_")
                        .setUrl("http://purl.obolibrary.org/obo/hp.owl")
                        .setVersion("2018-03-08")
                        .build())
                .addResources(Resource.newBuilder()
                        .setId("geno")
                        .setName("Genotype Ontology")
                        .setNamespacePrefix("GENO")
                        .setIriPrefix("http://purl.obolibrary.org/obo/GENO_")
                        .setUrl("http://purl.obolibrary.org/obo/geno.owl")
                        .setVersion("19-03-2018")
                        .build())
                .addResources(Resource.newBuilder()
                        .setId("pubmed")
                        .setName("PubMed")
                        .setNamespacePrefix("PMID")
                        .setIriPrefix("https://www.ncbi.nlm.nih.gov/pubmed/")
                        .build())
                .setCreatedBy("Peter R.")
                .setCreated(timestamp)
                .setPhenopacketSchemaVersion("2.0")
                .addExternalReferences(ExternalReference.newBuilder()
                        .setId("PMID:30808312")
                        .setDescription("Bao M, et al. COL6A1 mutation leading to Bethlem myopathy with recurrent hematuria: " +
                                "a case report. BMC Neurol. 2019;19(1):32.")
                        .build())
                .build();

        return Family.newBuilder()
                .setId("family")
                .setProband(proband())
                .addAllRelatives(ImmutableList.of(unaffectedMother(), unaffectedFather()))
                .setPedigree(pedigree())
                .setMetaData(metaData)
                .build();
    }


Note that we use ``System.currentTimeMillis()`` to get the current time (when we are creating and
submitting this Phenopacket).