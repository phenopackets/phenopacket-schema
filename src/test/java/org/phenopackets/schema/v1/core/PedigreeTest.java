package org.phenopackets.schema.v1.core;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Jules Jacobsen <j.jacobsen@qmul.ac.uk>
 */
public class PedigreeTest {

    /**
     * Test for representing a small family
     * @throws Exception
     */
    @Test
    public void testTrioPedigree() throws Exception {
        Pedigree defaultPedigree = Pedigree.getDefaultInstance();
        assertThat(defaultPedigree.getPersonsCount(), equalTo(0));

        Pedigree.Person mother = Pedigree.Person.newBuilder()
                .setFamilyId("FAMILY:1")
                .setIndividualId("MOTHER:1")
                .setSex(Pedigree.Person.Sex.FEMALE)
                .setAffectedStatus(Pedigree.Person.AffectedStatus.UNAFFECTED)
                .build();

        Pedigree.Person father = Pedigree.Person.newBuilder()
                .setFamilyId("FAMILY:1")
                .setIndividualId("FATHER:1")
                .setSex(Pedigree.Person.Sex.MALE)
                .setAffectedStatus(Pedigree.Person.AffectedStatus.UNAFFECTED)
                .build();

        Pedigree.Person daughter = Pedigree.Person.newBuilder()
                .setFamilyId("FAMILY:1")
                .setIndividualId("DAUGHTER:1")
                .setMaternalId("MOTHER:1")
                .setPaternalId("FATHER:1")
                .setSex(Pedigree.Person.Sex.FEMALE)
                .setAffectedStatus(Pedigree.Person.AffectedStatus.AFFECTED)
                .build();

        Pedigree pedigree = Pedigree.newBuilder()
                .addPersons(mother)
                .addPersons(father)
                .addPersons(daughter)
                .build();

        assertThat(pedigree.getPersonsCount(), equalTo(3));
    }

}
