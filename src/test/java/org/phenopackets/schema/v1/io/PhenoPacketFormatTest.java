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

        PhenoPacket fromJson = PhenoPacketFormat.fromJson(asJson);

        //Ta-da!
        assertThat(fromJson, equalTo(original));
    }

    @Test
    void toFromJson() throws Exception {
        PhenoPacket original = TestExamples.rareDiseasePhenoPacket();

        String asJson = PhenoPacketFormat.toJson(original);
        System.out.println(asJson);

        PhenoPacket transformed = PhenoPacketFormat.fromJson(asJson);
        assertThat(transformed, equalTo(original));
    }

    @Test
    void toFromYaml() throws Exception {
        PhenoPacket original = TestExamples.rareDiseasePhenoPacket();

        String asYaml = PhenoPacketFormat.toYaml(original);
        System.out.println(asYaml);

        PhenoPacket transformed = PhenoPacketFormat.fromYaml(asYaml);
        assertThat(transformed, equalTo(original));
    }
}