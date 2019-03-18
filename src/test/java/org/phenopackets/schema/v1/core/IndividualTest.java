package org.phenopackets.schema.v1.core;

import com.google.common.collect.ImmutableList;
import com.google.protobuf.Timestamp;
import com.google.protobuf.util.JsonFormat;
import org.junit.jupiter.api.Test;
import org.phenopackets.schema.v1.PhenoPacketTestUtil;

import java.time.Instant;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.phenopackets.schema.v1.PhenoPacketTestUtil.ontologyClass;

/**
 * @author Jules Jacobsen <j.jacobsen@qmul.ac.uk>
 */
public class IndividualTest {

    @Test
    public void testIndividual() throws Exception {

        long dateOfBirth = Instant.parse("2018-03-12T15:15:30.00Z").getEpochSecond();
        Timestamp dobTimestamp = Timestamp.newBuilder().setSeconds(dateOfBirth).build();
        Individual individual = Individual.newBuilder()
                .setId("INDIVIDUAL:1")
                .setDateOfBirth(dobTimestamp)
                .setSex(Sex.FEMALE)
                .setKaryotypicSex(KaryotypicSex.XX)
                .build();

        System.out.println(JsonFormat.printer().print(individual));
        assertThat(individual.getDateOfBirth(), equalTo(dobTimestamp));
    }
}
