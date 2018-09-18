package org.phenopackets.schema.v1.io;

import com.google.protobuf.util.JsonFormat;
import org.junit.jupiter.api.Test;
import org.phenopackets.schema.v1.PhenoPacket;
import org.phenopackets.schema.v1.examples.TestExamples;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Jules Jacobsen <j.jacobsen@qmul.ac.uk>
 */
class PhenoPacketFormatTest {

    @Test
    void roundTripping() throws IOException {
        PhenoPacket original = TestExamples.rareDiseasePhenoPacket();

        String asYaml = PhenoPacketFormat.toYaml(original);
        System.out.println(asYaml);

        String asJson = PhenoPacketFormat.yamlToJson(asYaml);
        System.out.println(asJson);

        PhenoPacket.Builder builder = PhenoPacket.newBuilder();
        JsonFormat.parser().merge(asJson, builder);
        PhenoPacket fromJson = builder.build();

        //Ta-da!
        assertThat(fromJson, equalTo(original));
    }
}