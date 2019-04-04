package org.phenopackets.schema.v1.examples;

import com.google.common.collect.ImmutableList;
import com.google.protobuf.Timestamp;
import com.google.protobuf.util.JsonFormat;
import org.junit.jupiter.api.Test;
import org.phenopackets.schema.v1.Family;
import org.phenopackets.schema.v1.Phenopacket;
import org.phenopackets.schema.v1.core.*;

import java.time.Instant;

import static org.phenopackets.schema.v1.PhenoPacketTestUtil.ontologyClass;

/**
 * Phenopacket representation of the rare disease example from the Toronto hackathon. See
 * src/test/resources/toronto_rare_disease_example.md.
 *
 * @author Jules Jacobsen <j.jacobsen@qmul.ac.uk>
 */
class RareDiseaseFamilyExample {

    private static final String PROBAND_ID = "PROBAND#1";
    private static final String SISTER_ID = "PROBAND#2";
    private static final String MOTHER_ID = "MOTHER";
    private static final String FATHER_ID = "FATHER";

    // Alleles
    private static final HgvsAllele NM_001361_403_C_T = hgvs("NM_001361.4:c.403C>T");
    private static final HgvsAllele NM_001361_454_G_A = hgvs("NM_001361.4:c.454G>A");
    private static final HgvsAllele NM_001369_12599_dupA = hgvs("NM_001369.2:c.12599dupA");

    private static HgvsAllele hgvs(String value) {
        return HgvsAllele.newBuilder().setHgvs(value).build();
    }

    private static Variant het(HgvsAllele allele) {
        return Variant.newBuilder()
                .setHgvsAllele(allele)
                .setGenotype(ontologyClass("GENO:0000135", "heterozygous"))
                .build();
    }

    private static Variant hom(HgvsAllele allele) {
        return Variant.newBuilder()
                .setHgvsAllele(allele)
                .setGenotype(ontologyClass("GENO:0000136", "homozygous"))
                .build();
    }

    static Phenopacket proband() {
        Phenotype syndactylyCongenitalOnset = Phenotype.newBuilder()
                .setType(ontologyClass("HP:0001159", "Syndactyly"))
                .setClassOfOnset(ontologyClass("HP:0003577", "Congenital onset"))
                .build();

        Phenotype pneumoniaChildhoodOnset = Phenotype.newBuilder()
                .setType(ontologyClass("HP:0002090", "Pneumonia"))
                .setClassOfOnset(ontologyClass("HP:0011463", "Childhood onset"))
                .build();

        Phenotype cryptorchidismCongenitalOnset = Phenotype.newBuilder()
                .setType(ontologyClass("HP:0000028", "Cryptorchidism"))
                .setClassOfOnset(ontologyClass("HP:0003577", "Congenital onset"))
                .build();

        Phenotype chronicSinusitisAdultOnsetSevere = Phenotype.newBuilder()
                .setType(ontologyClass("HP:0011109", "Chronic sinusitis"))
                .setSeverity(ontologyClass("HP:0012828", "Severe"))
                .setClassOfOnset(ontologyClass("HP:0003581", "Adult onset"))
                .build();

        Individual proband = Individual.newBuilder()
                .setSex(Sex.MALE)
                .setId(PROBAND_ID)
                .setDateOfBirth(Timestamp.newBuilder()
                        .setSeconds(Instant.parse("1998-01-01T00:00:00Z").getEpochSecond())
                        .build())
                .build();
        return Phenopacket.newBuilder()
                .setSubject(proband)
                .addPhenotypes(syndactylyCongenitalOnset)
                .addPhenotypes(pneumoniaChildhoodOnset)
                .addPhenotypes(cryptorchidismCongenitalOnset)
                .addPhenotypes(chronicSinusitisAdultOnsetSevere)
                .addVariants(het(NM_001361_403_C_T))
                .addVariants(het(NM_001361_454_G_A))
                .addVariants(hom(NM_001369_12599_dupA))
                .build();
    }

    static Phenopacket affectedSisterOfProband() {
        Phenotype syndactylyCongenitalOnset = Phenotype.newBuilder()
                .setType(ontologyClass("HP:0001159", "Syndactyly"))
                .setClassOfOnset(ontologyClass("HP:0003577", "Congenital onset"))
                .build();

        Phenotype notPneumonia = Phenotype.newBuilder()
                .setType(ontologyClass("HP:0002090", "Pneumonia"))
                .setAbsent(true)
                .build();

        Phenotype notChronicSinusitis = Phenotype.newBuilder()
                .setType(ontologyClass("HP:0011109", "Chronic sinusitis"))
                .setAbsent(true)
                .build();

        Individual sister = Individual.newBuilder()
                .setSex(Sex.FEMALE)
                .setId(SISTER_ID)
                .setDateOfBirth(Timestamp.newBuilder()
                        .setSeconds(Instant.parse("2000-03-04T00:00:00Z").getEpochSecond())
                        .build())
                .build();

        return Phenopacket.newBuilder()
                .setSubject(sister)
                .addPhenotypes(syndactylyCongenitalOnset)
                .addPhenotypes(notPneumonia)
                .addPhenotypes(notChronicSinusitis)
                .addVariants(het(NM_001361_403_C_T))
                .addVariants(het(NM_001361_454_G_A))
                .addVariants(het(NM_001369_12599_dupA))
                .build();
    }

    static Phenopacket unaffectedMother() {
        Individual mother = Individual.newBuilder()
                .setSex(Sex.FEMALE)
                .setId(MOTHER_ID)
                .build();
        return Phenopacket.newBuilder()
                .setSubject(mother)
                .addVariants(het(NM_001361_454_G_A))
                .addVariants(het(NM_001369_12599_dupA))
                .build();
    }

    static Phenopacket unaffectedFather() {
        Individual father = Individual.newBuilder()
                .setSex(Sex.MALE)
                .setId(FATHER_ID)
                .build();
        return Phenopacket.newBuilder()
                .setSubject(father)
                .addVariants(het(NM_001361_403_C_T))
                .addVariants(het(NM_001369_12599_dupA))
                .build();
    }


    private static Pedigree pedigree() {
        Pedigree.Person pedProband = Pedigree.Person.newBuilder()
                .setIndividualId(PROBAND_ID)
                .setSex(Sex.MALE)
                .setMaternalId(MOTHER_ID)
                .setPaternalId(FATHER_ID)
                .setAffectedStatus(Pedigree.Person.AffectedStatus.AFFECTED)
                .build();

        Pedigree.Person pedSister = Pedigree.Person.newBuilder()
                .setIndividualId(SISTER_ID)
                .setSex(Sex.FEMALE)
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
                .addPersons(pedSister)
                .addPersons(pedMother)
                .addPersons(pedFather)
                .build();
    }

    /**
     * Driver project example case - https://docs.google.com/document/d/1_6RwjdJa0qtGeidykZeG_PcPqhdMOIttUirCXGTGpwk
     * Here there are two affected siblings - the proband is affected with two conditions, one caused by a single
     * homozygous allele, the other by a compound heterozygous genotype. The proband's sister is affected with a
     * single condition caused by the compound heterozygous genotype. Neither parent exhibits an abnormal phenotype.
     */
    static Family rareDiseaseFamily() {

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
                        .setId("pato")
                        .setName("Phenotype And Trait Ontology")
                        .setNamespacePrefix("PATO")
                        .setIriPrefix("http://purl.obolibrary.org/obo/PATO_")
                        .setUrl("http://purl.obolibrary.org/obo/pato.owl")
                        .setVersion("2018-03-28")
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
                        .setId("doi")
                        .setName("Digital Object Identifier")
                        .setNamespacePrefix("DOI")
                        .setIriPrefix("http://dx.doi.org/")
                        .build())
                .setCreatedBy("Jules J.")
                .setCreated(timestamp)
                .addExternalReferences(ExternalReference.newBuilder()
                        .setId("DOI:10.1126/science.1186802")
                        .setDescription("Analysis of genetic inheritance in a family quartet by whole-genome sequencing.")
                        .build())
                .build();

        return Family.newBuilder()
                .setProband(proband())
                .addAllRelatives(ImmutableList.of(affectedSisterOfProband(), unaffectedMother(), unaffectedFather()))
                .setPedigree(pedigree())
                .setMetaData(metaData)
                .build();
    }

    @Test
    void printAsJson() throws Exception{
       String s = JsonFormat.printer().print(rareDiseaseFamily());
       System.out.println(s);
    }

}
