package org.phenopackets.api.proto;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.google.common.collect.ImmutableList;
import com.google.protobuf.Timestamp;
import com.google.protobuf.util.JsonFormat;
import org.junit.Test;

import java.io.IOException;
import java.time.Instant;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Jules Jacobsen <j.jacobsen@qmul.ac.uk>
 */
public class PhenoPacketProtobufTest {

    @Test
    public void testyMcTestFace() throws Exception {

        Phenotype phenotype = Phenotype.newBuilder()
                .setOnset(OntologyClass.newBuilder().setId("HP:0003623").setLabel("Neonatal onset").build())
                .addTypes(OntologyClass.newBuilder()
                        .setId("HP:0001711")
                        .setLabel("Abnormality of the left ventricle")
                        .build())
                .build();

        long dateOfBirth = Instant.parse("2018-03-12T15:15:30.00Z").getEpochSecond();
        Timestamp dobTimestamp = Timestamp.newBuilder().setSeconds(dateOfBirth).build();
        Individual individual = Individual.newBuilder()
                .setId("INDIVIDUAL:1")
                .setDateOfBirth(dobTimestamp)
                .setSex(Sex.FEMALE)
                .addPhenotypes(phenotype)
                .build();

        System.out.println(JsonFormat.printer().print(individual));
        assertThat(individual.getDateOfBirth(), equalTo(dobTimestamp));
        assertThat(individual.getPhenotypesList(), equalTo(ImmutableList.of(phenotype)));
    }

    @Test
    public void testPedigree() throws Exception {
        Pedigree defaultPedigree = Pedigree.getDefaultInstance();
        assertThat(defaultPedigree.getPersonsCount(), equalTo(0));

        Pedigree.Person person1 = Pedigree.Person.newBuilder()
                .setFamilyId("FAMILY:1")
                .setIndividualId("PERSON:1")
                .setMaternalId("MOTHER:1")
                .setPaternalId("FATHER:1")
                .setSex(Sex.MALE)
                .setAffectedStatus(Pedigree.Person.AffectedStatus.AFFECTED)
                .build();

        Pedigree.Person person2 = Pedigree.Person.newBuilder()
                .setFamilyId("FAMILY:1")
                .setIndividualId("PERSON:2")
                .setMaternalId("MOTHER:1")
                .setPaternalId("FATHER:1")
                .setSex(Sex.FEMALE)
                .setAffectedStatus(Pedigree.Person.AffectedStatus.UNAFFECTED)
                .build();

        Pedigree pedigree = Pedigree.newBuilder()
                .addPersons(person1)
                .addPersons(person2)
                .build();

        System.out.println(JsonFormat.printer().print(pedigree));
    }

    @Test
    public void testBoscExample() throws Exception {

        Phenotype whiteHands = Phenotype.newBuilder()
                .addTypes(OntologyClass.newBuilder().setId("PATO:0000323").setLabel("white").build())
                .addTypes(OntologyClass.newBuilder().setId("PATO:0000586").setLabel("increased size").build())
                .addTypes(OntologyClass.newBuilder().setId("UBERON:0002398").setLabel("manus").build())
                .build();

        Phenotype happyDisposition = Phenotype.newBuilder()
                .addTypes(OntologyClass.newBuilder()
                        .setId("HP:0100024")
                        .setLabel("Conspicuously happy disposition")
                        .build())
                .setDescription("welcomes strangers with open arms")
                .setOnset(OntologyClass.newBuilder().setId("HP:0011463").setLabel("Childhood onset").build())
                .build();

        Phenotype absentVibrisae = Phenotype.newBuilder()
                .addTypes(OntologyClass.newBuilder().setId("MP:0001284").setLabel("absent vibrissae").build())
                .build();

        Phenotype circularEars = Phenotype.newBuilder()
                .addTypes(OntologyClass.newBuilder().setId("PATO:0000411").setLabel("circular").build())
                .addTypes(OntologyClass.newBuilder().setId("UBERON:0001690").setLabel("ears").build())
                .build();

        Individual mickeyMouse = Individual.newBuilder()
                .setId("MOUSE:000001")
                .setDateOfBirth(Timestamp.newBuilder()
                        .setSeconds(Instant.parse("1928-11-18T00:00:00.00Z").getEpochSecond()))
                .setSex(Sex.MALE)
                .addPhenotypes(whiteHands)
                .addPhenotypes(happyDisposition)
                .addPhenotypes(absentVibrisae)
                .addPhenotypes(circularEars)
                .build();

        String jsonString = JsonFormat.printer().print(mickeyMouse);
        System.out.println(jsonString);

        System.out.println(asYaml(jsonString));

        //test round-trip via JSON
        Individual.Builder mickeyBuilder = Individual.newBuilder();
        JsonFormat.parser().merge(jsonString, mickeyBuilder);
        Individual roundTrippedMickey = mickeyBuilder.build();

        assertThat(roundTrippedMickey, equalTo(mickeyMouse));

    }

    public String asYaml(String jsonString) throws IOException {
        // parse JSON
        JsonNode jsonNodeTree = new ObjectMapper().readTree(jsonString);
        return new YAMLMapper().writeValueAsString(jsonNodeTree);
    }


}
