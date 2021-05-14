package org.phenopackets.schema.v2.examples;


import org.hl7.fhir.r4.model.Bundle;
import org.phenopackets.schema.v2.Family;
import org.phenopackets.schema.v2.Phenopacket;

/**
 * Utility test class containing pre-canned FHIR and Phenopacket messages. These classes are created from the examples in
 * the src/test/resources directory.
 *
 * This class is the primary entry-point to this data for the rest of the project. Classes exposed here should be
 * package-private to avoid confusion in the rest of the project.
 *
 * @author Jules Jacobsen <j.jacobsen@qmul.ac.uk>
 */
public class TestExamples {

    private TestExamples() {
    }

    /**
     * Utility method for returning a {@link Bundle} rare disease example based on the case defined in
     * src/test/resources/toronto_rare_disease_example.md
     *
     * @return a FHIR {@link Bundle} containing a rare disease patient and relations.
     */
    public static Bundle rareDiseaseBundle() {
        return RareDiseaseFhirExample.rareDiseaseBundle();
    }

    /**
     * Utility method for returning a {@link Phenopacket} rare disease example based on the case defined in
     * src/test/resources/toronto_rare_disease_example.md
     *
     * @return a {@link Phenopacket} containing a rare disease patient and relations.
     */
    public static Family rareDiseaseKindred1aFamily() {
        return RareDiseaseFamilyExample.rareDiseaseKindred1aFamily();
    }

    public static Phenopacket rareDiseasePhenopacket() {
        return RareDiseaseFamilyExample.proband();
    }

    public static Bundle cancerBundle() {
        return CancerFhirExample.cancerBundle();
    }

    public static Family rareDiseaseBethlemMyopathyFamily() {
        return BethlemMyopathyExample.bethlemMyopathyFamily();
    }

    public static Phenopacket cancerPhenopacket() {
        return CancerPhenopacketExample.cancerPhenopacket();
    }

    public static Phenopacket biosamplesPhenopacket() {
        return BiosamplesPhenopacketExample.biosamplePhenopacket();
    }

    public static Phenopacket severeCovidCaseWithCardiacComplications() {
        return CovidExample.severeCovidCaseWithCardiacComplications();
    }
}
