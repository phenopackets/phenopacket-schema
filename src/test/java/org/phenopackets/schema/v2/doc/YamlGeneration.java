package org.phenopackets.schema.v2.doc;

import com.google.protobuf.Timestamp;
import org.junit.jupiter.api.Test;
import org.phenopackets.schema.v1.core.OntologyClass;
import org.phenopackets.schema.v1.core.TimeElement;
import org.phenopackets.schema.v1.core.VitalStatus;

import java.io.IOException;
import java.text.ParseException;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.phenopackets.schema.v2.doc.Wrapper.*;

public class YamlGeneration extends TestBase{


    @Test
    void ontologyClassYaml() throws IOException {
        String id = "HP:0001875";
        String label = "Neutropenia";
        OntologyClass neutropenia = buildOntologyClass(id, label);
        String yamlString = messageToYaml(neutropenia, "OntologyClass");
        System.out.println(yamlString);
        String hash = sha256(yamlString);
        assertEquals("8460c503cf10e17284c2f6947a248eafb89cc2b79e24b460c1d6aef10476391f", hash);
    }


    /**
     *
     message VitalStatus {
     // Default = false i.e. the individual is alive. MUST be true if
     enum Status {
     UNKNOWN_STATUS = 0;
     ALIVE = 1;
     DECEASED = 2;
     }
     Status status = 1;

     TimeElement time_of_death = 2;

     OntologyClass cause_of_death = 3;
     }
     * @throws IOException
     */
    @Test
    void vitalStatusDeceasedYaml() throws IOException, ParseException {
        OntologyClass causeOfDeath = buildOntologyClass("NCIT:C36263","Metastatic Malignant Neoplasm");
        TimeElement timeOfDeath = buildFromDateString("2015-10-01T10:54:20.021Z");
        VitalStatus deceased = buildDeceasedStatus(causeOfDeath, timeOfDeath);
        String yamlString = messageToYaml(deceased, "VitalStatus");
        System.out.println(yamlString);
        String hash = sha256(yamlString);
        assertEquals("40d20db7d503328a49d287444ced816643366ede8f123dac5521daa7db1ec737", hash);
    }

    @Test
    void vitalStatusAliveYaml() throws IOException, ParseException {
        TimeElement now = getNowTimestampElement(); // ?? Do we want to add time of lasat onservation
        VitalStatus alive = buildAliveStatus();
        String yamlString = messageToYaml(alive, "VitalStatus");
        System.out.println(yamlString);
        String hash = sha256(yamlString);
        assertEquals("a3ee336af69925199fbb65326a2d9f8cefa8f4ba30d01ed4b7e31b7dd181ad33", hash);
    }
}
