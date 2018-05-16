package org.phenopackets.schema.v1;

import com.google.common.collect.ImmutableList;
import com.google.protobuf.Timestamp;
import com.google.protobuf.util.JsonFormat;
import org.junit.Test;
import org.phenopackets.schema.v1.core.*;

import java.time.Instant;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.phenopackets.schema.v1.PhenoPacketTestUtil.FEMALE;
import static org.phenopackets.schema.v1.PhenoPacketTestUtil.ontologyClass;

/**
 * @author Jules Jacobsen <j.jacobsen@qmul.ac.uk>
 */
public class RareDiseaseSampleTest {

    // the individual ID needs to match that used in the pedigree
    public static final String FAMILY_ID = "FAMILY:1";
    private static final String PROBAND_ID = "PROBAND:1";
    private static final String MOTHER_ID = "MOTHER:1";

    private static final Phenotype abnormalPhenotype = Phenotype.newBuilder()
            .setType(ontologyClass("HP:0000118", "Phenotypic abnormality"))
            .build();

    @Test
    public void completeRareDiseaseSample() throws Exception {

        Individual proband = Individual.newBuilder()
                .setSex(FEMALE)
                .setId(PROBAND_ID)
                .setDateOfBirth(Timestamp.newBuilder().setSeconds(Instant.parse("2018-01-01T00:00:00Z").getEpochSecond()).build())
                .addPhenotypes(abnormalPhenotype.toBuilder()
                        .addModifiers(ontologyClass("HP:0012828", "Severe"))
                        .build())
                .build();

        Individual mother = Individual.newBuilder()
                .setSex(FEMALE)
                .setId(MOTHER_ID)
                .setDateOfBirth(Timestamp.newBuilder().setSeconds(Instant.parse("1977-05-25T00:00:00Z").getEpochSecond()).build())
                .addPhenotypes(abnormalPhenotype.toBuilder()
                        .addModifiers(ontologyClass("HP:0012826", "Moderate"))
                        .build())
                .build();

        Pedigree trio = createPedigree();

        File vcf = File.newBuilder().setPath("/path/to/vcf.gz").build();
        RareDisease.RareDiseaseSampleData rareDiseaseSampleData = RareDisease.RareDiseaseSampleData.newBuilder()
                .setId("STUDY_ID:0000123")
                .setDescription("A rare disease case with a pedigree and VCF file")
                .setPedigree(trio)
                .setProbandId(PROBAND_ID)
                .addIndividuals(mother)
                .addIndividuals(proband)
                .setGenomeAssembly(GenomeAssembly.GRCH_37)
                .setVcf(vcf)
                .build();

        System.out.println(JsonFormat.printer().print(rareDiseaseSampleData));
        assertThat(rareDiseaseSampleData.getId(), equalTo("STUDY_ID:0000123"));
        assertThat(rareDiseaseSampleData.getDescription().isEmpty(), is(false));
        assertThat(rareDiseaseSampleData.getPedigree(), equalTo(trio));
        assertThat(rareDiseaseSampleData.getIndividualsList(), equalTo(ImmutableList.of(mother, proband)));
        assertThat(rareDiseaseSampleData.getGenomeAssembly(), equalTo(GenomeAssembly.GRCH_37));
        assertThat(rareDiseaseSampleData.getVcf(), equalTo(vcf));
    }

    private Pedigree createPedigree() {
        Pedigree.Person mother = Pedigree.Person.newBuilder()
                .setFamilyId(FAMILY_ID)
                .setIndividualId(MOTHER_ID)
                .setSex(Pedigree.Person.Sex.FEMALE)
                .setAffectedStatus(Pedigree.Person.AffectedStatus.AFFECTED)
                .build();

        Pedigree.Person father = Pedigree.Person.newBuilder()
                .setFamilyId(FAMILY_ID)
                .setIndividualId("FATHER:1")
                .setSex(Pedigree.Person.Sex.MALE)
                .setAffectedStatus(Pedigree.Person.AffectedStatus.UNAFFECTED)
                .build();

        Pedigree.Person daughter = Pedigree.Person.newBuilder()
                .setFamilyId(FAMILY_ID)
                .setIndividualId(PROBAND_ID)
                .setMaternalId(MOTHER_ID)
                .setPaternalId("FATHER:1")
                .setSex(Pedigree.Person.Sex.FEMALE)
                .setAffectedStatus(Pedigree.Person.AffectedStatus.AFFECTED)
                .build();

        Pedigree.Family trio = Pedigree.Family.newBuilder()
                .setId(FAMILY_ID)
                .addPersons(mother)
                .addPersons(father)
                .addPersons(daughter)
                .build();

        return Pedigree.newBuilder()
                .addFamilies(trio)
                .build();
    }
}
