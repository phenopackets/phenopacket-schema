package org.phenopackets.schema.v2;

import com.google.protobuf.util.JsonFormat;
import org.junit.jupiter.api.Test;
import org.phenopackets.schema.v2.examples.TestExamples;
import org.phenopackets.schema.v2.io.FormatMapper;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Jules Jacobsen <j.jacobsen@qmul.ac.uk>
 */
public class FamilyTest {

    @Test
    public void printFamilyAsYaml() throws IOException {
        Family family = TestExamples.rareDiseaseKindred1aFamily();
        System.out.println(FormatMapper.messageToYaml(family));
    }

    @Test
    public void roundTrippingFamily() throws IOException {
        Family original = TestExamples.rareDiseaseKindred1aFamily();

        String json = JsonFormat.printer().print(original);
        String asYaml = FormatMapper.jsonToYaml(json);
        String asJson = FormatMapper.yamlToJson(asYaml);

        Family.Builder familyBuilder = Family.newBuilder();
        JsonFormat.parser().merge(asJson, familyBuilder);
        Family fromJson = familyBuilder.build();

        //Ta-da!
        assertThat(fromJson, equalTo(original));
    }
}
