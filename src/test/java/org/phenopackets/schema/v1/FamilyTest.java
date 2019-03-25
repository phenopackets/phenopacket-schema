package org.phenopackets.schema.v1;

import org.junit.jupiter.api.Test;
import org.phenopackets.schema.v1.examples.TestExamples;
import org.phenopackets.schema.v1.io.FamilyFormat;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Jules Jacobsen <j.jacobsen@qmul.ac.uk>
 */
public class FamilyTest {

    private final FamilyFormat familyFormat = new FamilyFormat();


    @Test
    void printFamilyAsYaml() throws IOException {
        System.out.println(familyFormat.toYaml(TestExamples.rareDiseaseFamily()));
    }

    @Test
    void roundTrippingFamily() throws IOException {
        Family original = TestExamples.rareDiseaseFamily();

        String asYaml = familyFormat.toYaml(original);
        System.out.println(asYaml);

        System.out.println(familyFormat.toJson(original));

        String asJson = familyFormat.yamlToJson(asYaml);
        System.out.println(asJson);

        Family fromJson = familyFormat.fromJson(asJson);

        //Ta-da!
        assertThat(fromJson, equalTo(original));
    }
}
