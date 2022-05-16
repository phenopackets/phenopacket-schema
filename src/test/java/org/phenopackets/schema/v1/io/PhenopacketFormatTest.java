package org.phenopackets.schema.v1.io;

import org.junit.jupiter.api.Test;
import org.phenopackets.schema.v1.Phenopacket;
import org.phenopackets.schema.v1.examples.TestExamples;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Jules Jacobsen <j.jacobsen@qmul.ac.uk>
 */
public class PhenopacketFormatTest {

    @Test
    public void roundTripping() throws IOException {
        Phenopacket original = TestExamples.rareDiseasePhenopacket();

        String asYaml = PhenopacketFormat.toYaml(original);

        String asJson = FormatMapper.yamlToJson(asYaml);

        Phenopacket fromJson = PhenopacketFormat.fromJson(asJson);

        //Ta-da!
        assertThat(fromJson, equalTo(original));
    }

    @Test
    public void toFromJson() throws Exception {
        Phenopacket original = TestExamples.cancerPhenopacket();

        String asJson = PhenopacketFormat.toJson(original);

        Phenopacket transformed = PhenopacketFormat.fromJson(asJson);
        assertThat(transformed, equalTo(original));
    }

    @Test
    public void toFromYaml() throws Exception {
        Phenopacket original = TestExamples.biosamplesPhenopacket();

        String asYaml = PhenopacketFormat.toYaml(original);

        Phenopacket transformed = PhenopacketFormat.fromYaml(asYaml);
        assertThat(transformed, equalTo(original));
    }
}