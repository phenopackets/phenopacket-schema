package org.phenopackets.schema.v1.examples;

import com.google.common.collect.ImmutableList;
import com.google.protobuf.Timestamp;
import org.phenopackets.schema.v1.PhenoPacket;
import org.phenopackets.schema.v1.core.*;

import java.time.Instant;

import static org.phenopackets.schema.v1.PhenoPacketTestUtil.*;

/**
 * Phenopacket representation of the rare disease example from the Toronto hackathon. See
 * src/test/resources/toronto_rare_disease_example.md.
 *
 * @author Jules Jacobsen <j.jacobsen@qmul.ac.uk>
 */
class RareDiseasePhenoPacketExample {

    public static final String PROBAND_ID = "PROBAND#1";
    public static final String SISTER_ID = "PROBAND#2";
    public static final String MOTHER_ID = "MOTHER";
    public static final String FATHER_ID = "FATHER";

    /**
     * Driver project example case - https://docs.google.com/document/d/1_6RwjdJa0qtGeidykZeG_PcPqhdMOIttUirCXGTGpwk
     * Here there are two affected siblings - the proband is affected with two conditions, one caused by a single
     * homozygous allele, the other by a compound heterozygous genotype. The proband's sister is affected with a
     * single condition caused by the compound heterozygous genotype. Neither parent exhibits an abnormal phenotype.
     */
    static PhenoPacket rareDiseasePhenoPacket() {
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
                .setClassOfOnset(ontologyClass("HP:0003581","Adult onset"))
                .build();


        Individual proband = Individual.newBuilder()
                .setSex(MALE)
                .setId(PROBAND_ID)
                .setDateOfBirth(Timestamp.newBuilder().setSeconds(Instant.parse("1998-01-01T00:00:00Z").getEpochSecond()).build())
                .addPhenotypes(syndactylyCongenitalOnset)
                .addPhenotypes(pneumoniaChildhoodOnset)
                .addPhenotypes(cryptorchidismCongenitalOnset)
                .addPhenotypes(chronicSinusitisAdultOnsetSevere)
                .build();

        Pedigree.Person pedProband = Pedigree.Person.newBuilder()
                .setIndividualId(PROBAND_ID)
                .setSex(Pedigree.Person.Sex.MALE)
                .setMaternalId(MOTHER_ID)
                .setPaternalId(FATHER_ID)
                .setAffectedStatus(Pedigree.Person.AffectedStatus.AFFECTED)
                .build();

        Phenotype notPneumonia = Phenotype.newBuilder()
                .setType(ontologyClass("HP:0002090", "Pneumonia"))
                .setNegated(true)
                .build();

        Phenotype notCryptorchidism = Phenotype.newBuilder()
                .setType(ontologyClass("HP:0000028", "Cryptorchidism"))
                .setNegated(true)
                .build();

        Phenotype notChronicSinusitis = Phenotype.newBuilder()
                .setType(ontologyClass("HP:0011109", "Chronic sinusitis"))
                .setNegated(true)
                .build();

        Individual sisterOfProband = Individual.newBuilder()
                .setSex(FEMALE)
                .setId(SISTER_ID)
                .setDateOfBirth(Timestamp.newBuilder().setSeconds(Instant.parse("2000-03-04T00:00:00Z").getEpochSecond()).build())
                .addPhenotypes(syndactylyCongenitalOnset)
                .addPhenotypes(notPneumonia)
                .addPhenotypes(notChronicSinusitis)
                .build();

        Pedigree.Person pedSister = Pedigree.Person.newBuilder()
                .setIndividualId(SISTER_ID)
                .setSex(Pedigree.Person.Sex.FEMALE)
                .setMaternalId(MOTHER_ID)
                .setPaternalId(FATHER_ID)
                .setAffectedStatus(Pedigree.Person.AffectedStatus.AFFECTED)
                .build();

        Individual mother = Individual.newBuilder()
                .setSex(FEMALE)
                .setId(MOTHER_ID)
                .build();

        Pedigree.Person pedMother = Pedigree.Person.newBuilder()
                .setIndividualId(MOTHER_ID)
                .setSex(Pedigree.Person.Sex.FEMALE)
                .setAffectedStatus(Pedigree.Person.AffectedStatus.UNAFFECTED)
                .build();

        Individual father = Individual.newBuilder()
                .setSex(MALE)
                .setId(FATHER_ID)
                .build();

        Pedigree.Person pedFather = Pedigree.Person.newBuilder()
                .setIndividualId(FATHER_ID)
                .setSex(Pedigree.Person.Sex.MALE)
                .setAffectedStatus(Pedigree.Person.AffectedStatus.UNAFFECTED)
                .build();

        Pedigree pedigree = Pedigree.newBuilder()
                .addPersons(pedProband)
                .addPersons(pedSister)
                .addPersons(pedMother)
                .addPersons(pedFather)
                .build();


        Variant var1 = Variant.newBuilder()
                .setSequence("NM_001361.4")
                .setPosition(423)
                .setDeletion("C")
                .setInsertion("T")
                .setHgvs("NM_001361.4:c.403C>T")
                .putSampleGenotypes(PROBAND_ID, ontologyClass("GENO:0000135", "heterozygous"))
                .putSampleGenotypes(SISTER_ID, ontologyClass("GENO:0000135", "heterozygous"))
                .putSampleGenotypes("FATHER:1", ontologyClass("GENO:0000135", "heterozygous"))
                .build();

        Variant var2 = Variant.newBuilder()
                .setSequence("NM_001361.4")
                .setPosition(474)
                .setDeletion("G")
                .setInsertion("A")
                .setHgvs("NM_001361.4:c.454G>A")
                .putSampleGenotypes(PROBAND_ID, ontologyClass("GENO:0000135", "heterozygous"))
                .putSampleGenotypes(SISTER_ID, ontologyClass("GENO:0000135", "heterozygous"))
                .putSampleGenotypes(MOTHER_ID, ontologyClass("GENO:0000135", "heterozygous"))
                .build();

        Variant var3 = Variant.newBuilder()
                .setSequence("NM_001369.2")
                .setPosition(12639)
                .setDeletion("AA")
                .setInsertion("AAA")
                .setHgvs("NM_001369.2:c.12599dupA")
                .putSampleGenotypes(PROBAND_ID, ontologyClass("GENO:0000136", "homozygous"))
                .putSampleGenotypes(SISTER_ID, ontologyClass("GENO:0000135", "heterozygous"))
                .putSampleGenotypes(MOTHER_ID, ontologyClass("GENO:0000135", "heterozygous"))
                .putSampleGenotypes("FATHER:1", ontologyClass("GENO:0000135", "heterozygous"))
                .build();

        MetaData metaData = MetaData.newBuilder()
                .addResources(Resource.newBuilder()
                        .setId("hp")
                        .setName("human phenotype ontology")
                        .setNamespacePrefix("HP")
                        .setUrl("http://purl.obolibrary.org/obo/hp.owl")
                        .setVersion("2018-03-08")
                        .build())
                .addResources(Resource.newBuilder()
                        .setId("pato")
                        .setName("Phenotype And Trait Ontology")
                        .setNamespacePrefix("PATO")
                        .setUrl("http://purl.obolibrary.org/obo/pato.owl")
                        .setVersion("2018-03-28")
                        .build())
                .addResources(Resource.newBuilder()
                        .setId("geno")
                        .setName("Genotype Ontology")
                        .setNamespacePrefix("GENO")
                        .setUrl("http://purl.obolibrary.org/obo/geno.owl")
                        .setVersion("19-03-2018")
                        .build())
                .setCreatedBy("Jules J.")
                .build();

        return PhenoPacket.newBuilder()
                .setSubject(proband)
                .addAllIndividuals(ImmutableList.of(sisterOfProband, mother, father))
                .setPedigree(pedigree)
                .addAllVariants(ImmutableList.of(var1, var2, var3))
                .setMetaData(metaData)
                .build();
    }

}
