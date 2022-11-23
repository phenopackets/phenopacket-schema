package org.ga4gh.pedigree.v1;

import org.junit.jupiter.api.Test;
import org.phenopackets.schema.v2.Phenopacket;
import org.phenopackets.schema.v2.core.*;
import org.phenopackets.schema.v2.io.FormatMapper;

import java.io.IOException;

import static org.phenopackets.schema.v2.PhenoPacketTestUtil.ontologyClass;

/**
 * Test cases / examples for the Phenopackets implementation of the GA4GH-Pedigree conceptual model
 */
class Ga4ghPedigreeExamplesTest {

    private static final OntologyClass BIOLOGICAL_MOTHER_OF = ontologyClass("KIN:027", "isBiologicalMother");
    private static final OntologyClass BIOLOGICAL_FATHER_OF = ontologyClass("KIN:028", "isBiologicalFather");
    private static final OntologyClass MONOZYGOTIC_TWIN_OF = ontologyClass("KIN:010", "isMonozygoticMultipleBirthSiblingOf");
    private static final OntologyClass ADOPTIVE_PARENT_OF = ontologyClass("KIN:022", "isAdoptiveParentOf");
    private static final OntologyClass GESTATIONAL_CARRIER_OF = ontologyClass("KIN:005", "isGestationalCarrierOf");
    private static final OntologyClass OVUM_DONOR_OF = ontologyClass("KIN:038", "isOvumDonorOf");

    private static Relationship.Builder relationship(Phenopacket subject, OntologyClass relationship, Phenopacket relative) {
        return Relationship.newBuilder()
                .setIndividualId(subject.getSubject().getId())
                .setRelation(relationship)
                .setRelativeId(relative.getSubject().getId());
    }

    @Test
    void simpleTrio() throws IOException {
        Phenopacket mother = Phenopacket.newBuilder().setId("1").setSubject(Individual.newBuilder().setId("MOTHER").setSex(Sex.FEMALE)).build();
        Phenopacket father = Phenopacket.newBuilder().setId("2").setSubject(Individual.newBuilder().setId("FATHER").setSex(Sex.MALE)).build();
        Phenopacket proband = Phenopacket.newBuilder().setId("3").setSubject(Individual.newBuilder().setId("CHILD").setSex(Sex.UNKNOWN_SEX)).build();

        Pedigree pedigree = Pedigree.newBuilder()
                .setId("FAM1")
                .setNarrative("A Phenopacket GA4GHPedigree of a trio with an affected child")
                .setDate("2022-06-23")
                .addIndexPatients(proband.getSubject().getId())
                .addIndividuals(mother)
                .addIndividuals(father)
                .addIndividuals(proband)
                .addRelationships(relationship(mother, BIOLOGICAL_MOTHER_OF, proband))
                .addRelationships(relationship(father, BIOLOGICAL_FATHER_OF, proband))
                .build();

        System.out.println(FormatMapper.messageToYaml(pedigree));
    }

    @Test
    void twins() throws IOException {
        Phenopacket mother = Phenopacket.newBuilder().setId("1").setSubject(Individual.newBuilder().setId("MOTHER").setSex(Sex.FEMALE)).build();
        Phenopacket father = Phenopacket.newBuilder().setId("2").setSubject(Individual.newBuilder().setId("FATHER").setSex(Sex.MALE)).build();
        Phenopacket twin1 = Phenopacket.newBuilder().setId("3").setSubject(Individual.newBuilder().setId("TWIN1").setSex(Sex.UNKNOWN_SEX)).build();
        Phenopacket twin2 = Phenopacket.newBuilder().setId("4").setSubject(Individual.newBuilder().setId("TWIN2").setSex(Sex.UNKNOWN_SEX)).build();

        Pedigree pedigree = Pedigree.newBuilder()
                .setId("FAM2")
                .setNarrative("A Phenopacket GA4GHPedigree of a couple with identical twins")
                .setDate("2022-06-23")
                .addIndividuals(mother)
                .addIndividuals(father)
                .addIndividuals(twin1)
                .addIndividuals(twin2)
                .addRelationships(relationship(mother, BIOLOGICAL_MOTHER_OF, twin1))
                .addRelationships(relationship(mother, BIOLOGICAL_MOTHER_OF, twin2))
                .addRelationships(relationship(father, BIOLOGICAL_FATHER_OF, twin1))
                .addRelationships(relationship(father, BIOLOGICAL_FATHER_OF, twin2))
                .addRelationships(relationship(twin1, MONOZYGOTIC_TWIN_OF, twin2))
                .addRelationships(relationship(twin2, MONOZYGOTIC_TWIN_OF, twin1))
                .build();

//        System.out.println(FormatMapper.messageToYaml(pedigree));
    }

    @Test
    void adoption() throws IOException {
        Phenopacket mother = Phenopacket.newBuilder().setId("1").setSubject(Individual.newBuilder().setId("MOTHER").setSex(Sex.FEMALE)).build();
        Phenopacket father = Phenopacket.newBuilder().setId("3").setSubject(Individual.newBuilder().setId("FATHER").setSex(Sex.MALE)).build();
        Phenopacket biologicalMother = Phenopacket.newBuilder().setId("2").setSubject(Individual.newBuilder().setId("BIOLOGICAL_MOTHER").setSex(Sex.FEMALE)).build();
        Phenopacket child = Phenopacket.newBuilder().setId("4").setSubject(Individual.newBuilder().setId("CHILD").setSex(Sex.UNKNOWN_SEX)).build();

        Pedigree pedigree = Pedigree.newBuilder()
                .setId("FAM3")
                .setNarrative("A Phenopacket GA4GHPedigree of a child with an adoptive mother")
                .setDate("2022-06-23")
                .addIndividuals(mother)
                .addIndividuals(biologicalMother)
                .addIndividuals(father)
                .addIndividuals(child)
                .addRelationships(relationship(mother, ADOPTIVE_PARENT_OF, child))
                .addRelationships(relationship(biologicalMother, BIOLOGICAL_MOTHER_OF, child))
                .addRelationships(relationship(father, BIOLOGICAL_FATHER_OF, child))
                .build();

//        System.out.println(FormatMapper.messageToYaml(pedigree));
    }

    @Test
    void ivf() throws IOException {
        Phenopacket mother = Phenopacket.newBuilder().setId("1").setSubject(Individual.newBuilder().setId("MOTHER").setSex(Sex.FEMALE)).build();
        Phenopacket father = Phenopacket.newBuilder().setId("3").setSubject(Individual.newBuilder().setId("FATHER").setSex(Sex.MALE)).build();
        Phenopacket surrogate = Phenopacket.newBuilder().setId("2").setSubject(Individual.newBuilder().setId("SURROGATE").setSex(Sex.FEMALE)).build();
        Phenopacket child = Phenopacket.newBuilder().setId("4").setSubject(Individual.newBuilder().setId("CHILD").setSex(Sex.UNKNOWN_SEX)).build();

        Pedigree pedigree = Pedigree.newBuilder()
                .setId("FAM4")
                .setNarrative("A Phenopacket GA4GHPedigree of a child with an egg donor, gestational carrier, and biological father")
                .setDate("2022-06-23")
                .addIndividuals(mother)
                .addIndividuals(surrogate)
                .addIndividuals(father)
                .addIndividuals(child)
                .addRelationships(relationship(mother, OVUM_DONOR_OF, child))
                .addRelationships(relationship(surrogate, GESTATIONAL_CARRIER_OF, child))
                .addRelationships(relationship(father, BIOLOGICAL_FATHER_OF, child))
                .build();

//        System.out.println(FormatMapper.messageToYaml(pedigree));
    }
}


