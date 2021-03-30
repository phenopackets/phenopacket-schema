package org.phenopackets.schema.v2;

import ca.uhn.fhir.context.FhirContext;
import org.hl7.fhir.r4.model.Bundle;
import org.junit.jupiter.api.Test;
import org.phenopackets.schema.v2.examples.TestExamples;

/**
 * 'Tests' of the FHIR representations of the example data from Toronto hackathon.
 *
 * @author Jules Jacobsen <j.jacobsen@qmul.ac.uk>
 */
public class FhirTest {

    @Test
    public void testCancerExample() {
        Bundle bundle = TestExamples.cancerBundle();
    }

    @Test
    public void testRareDiseaseExample() {
        Bundle bundle = TestExamples.rareDiseaseBundle();
    }

    private String toR4String(Bundle bundle) {
        return FhirContext.forR4().newJsonParser().setPrettyPrint(true).encodeResourceToString(bundle);
    }
}
