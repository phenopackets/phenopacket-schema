/**
 * Copyright CSIRO Australian e-Health Research Centre (http://aehrc.com). All rights reserved. Use is subject to 
 * license terms and conditions.
 */
package org.phenopackets.schema.v1.converters;

import ca.uhn.fhir.context.FhirContext;
import org.hl7.fhir.r4.model.Bundle;
import org.junit.jupiter.api.Test;
import org.phenopackets.schema.v1.examples.TestExamples;

/**
 * FHIR representation of the cancer example from the Toronto hackathon. See 
 * src/test/resources/toronto_cancer_example.md.
 * 
 * @author Alejandro Metke <alejandro.metke@csiro.au>
 *
 */
public class FhirCancerExampleTest {

    @Test
    public void testCancerExample() {
        Bundle bundle = TestExamples.cancerBundle();
        System.out.println(FhirContext.forR4().newJsonParser().setPrettyPrint(true).encodeResourceToString(bundle));
    }

}
