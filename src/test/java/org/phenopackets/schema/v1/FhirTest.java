package org.phenopackets.schema.v1;

import org.hl7.fhir.r4.model.Bundle;
import org.junit.jupiter.api.Test;
import org.phenopackets.schema.v1.examples.TestExamples;

import java.io.IOException;

/**
 * 'Tests' of the FHIR representations of the example data from Toronto hackathon.
 *
 * @author Jules Jacobsen <j.jacobsen@qmul.ac.uk>
 */
public class FhirTest {

    @Test
    public void testCancerExample() throws IOException {
        Bundle bundle = TestExamples.cancerBundle();
//        System.out.println(FhirContext.forR4().newJsonParser().setPrettyPrint(true).encodeResourceToString(bundle));
    }

    @Test
    public void testRareDiseaseExample() {
        Bundle bundle = TestExamples.rareDiseaseBundle();
//        System.out.println(FhirContext.forR4().newJsonParser().setPrettyPrint(true).encodeResourceToString(bundle));
    }
}
