package org.phenopackets.schema.v1.examples;


import org.hl7.fhir.r4.model.Bundle;
import org.phenopackets.schema.v1.PhenoPacket;
import org.phenopackets.schema.v1.RareDiseaseFamily;

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
     * Utility method for returning a {@link PhenoPacket} rare disease example based on the case defined in
     * src/test/resources/toronto_rare_disease_example.md
     *
     * @return a {@link PhenoPacket} containing a rare disease patient and relations.
     */
    public static RareDiseaseFamily rareDiseaseFamily() {
        return RareDiseaseFamilyExample.rareDiseaseFamily();
    }

    public static PhenoPacket rareDiseasePhenoPacket() {
        return RareDiseaseFamilyExample.proband();
    }

    public static Bundle cancerBundle() {
        return CancerFhirExample.cancerBundle();
    }

    public static PhenoPacket cancerPhenoPacket() {
        return CancerPhenoPacketExample.cancerPhenopacket();
    }

    public static PhenoPacket biosamplesPhenoPacket() {
        return BiosamplesPhenoPacketExample.biosamplePhenoPacket();
    }

}
