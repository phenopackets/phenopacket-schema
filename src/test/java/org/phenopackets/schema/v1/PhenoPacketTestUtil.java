package org.phenopackets.schema.v1;

import com.google.protobuf.Timestamp;
import org.hl7.fhir.r4.model.CodeableConcept;
import org.hl7.fhir.r4.model.Coding;
import org.phenopackets.schema.v1.core.OntologyClass;

import java.time.Instant;

/**
 * @author Jules Jacobsen <j.jacobsen@qmul.ac.uk>
 */
public class PhenoPacketTestUtil {

    public static OntologyClass ontologyClass(String id, String label) {
        return OntologyClass.newBuilder()
                .setId(id)
                .setLabel(label)
                .build();
    }

    public static CodeableConcept codeableConcept(String system, String id, String label){
        return new CodeableConcept().addCoding(new Coding(system, id, label));
    }

    public static Timestamp parseTimestamp(String string) {
        return Timestamp.newBuilder()
                .setSeconds(Instant.parse(string).getEpochSecond())
                .build();
    }

}
