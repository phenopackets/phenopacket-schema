package org.phenopackets.schema.v1;

import com.google.protobuf.util.JsonFormat;
import org.junit.jupiter.api.Test;
import org.phenopackets.schema.v1.examples.TestExamples;
import org.phenopackets.schema.v1.io.FormatMapper;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Jules Jacobsen <j.jacobsen@qmul.ac.uk>
 */
public class FamilyTest {

    @Test
    public void printFamilyAsYaml() throws IOException {
        Family family = TestExamples.rareDiseaseFamily();
        String json = JsonFormat.printer().print(family);
        System.out.println(FormatMapper.jsonToYaml(json));
    }

    @Test
    public void roundTrippingFamily() throws IOException {
        Family original = TestExamples.rareDiseaseFamily();

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
