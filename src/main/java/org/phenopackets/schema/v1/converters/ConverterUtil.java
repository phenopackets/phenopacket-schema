package org.phenopackets.schema.v1.converters;

import org.hl7.fhir.r4.model.CodeableConcept;
import org.hl7.fhir.r4.model.Coding;
import org.phenopackets.schema.v1.core.OntologyClass;

/**
 * Super-simple utility class for saving a lot of verbose typing for common operations.
 *
 * @author Jules Jacobsen <j.jacobsen@qmul.ac.uk>
 */
public class ConverterUtil {

    private ConverterUtil() {

    }

    public static CodeableConcept codeableConcept(String system, String id, String label){
        return new CodeableConcept().addCoding(new Coding(system, id, label));
    }

    public static OntologyClass ontologyClass(String id, String label) {
        return OntologyClass.newBuilder()
                .setId(id)
                .setLabel(label)
                .build();
    }
}
