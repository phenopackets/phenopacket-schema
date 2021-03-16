package org.phenopackets.schema.v2.doc;

import org.junit.jupiter.api.Test;
import org.phenopackets.schema.v1.core.*;

import java.io.IOException;
import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.phenopackets.schema.v2.doc.PhenopacketUtil.*;

/**
 * This class is a convenience class for generating YAML snippets for the documentation. For each snippet,
 * we calculate a Hash value and assert equality. If there is any upstream change, the assertion will fail,
 * which will be a warning to update the documentation.
 */
public class YamlGeneration extends TestBase{


    @Test
    void ontologyClassHpoNeutropenia() throws IOException {
        String id = "HP:0001875";
        String label = "Neutropenia";
        OntologyClass neutropenia = ontologyClass(id, label);
        String yamlString = messageToYaml(neutropenia, "ontologyClass");
        System.out.println(yamlString);
        String hash = sha256(yamlString);
        assertEquals("e93ec31eb81c5923a646deba11d32a2550413cbe96a6d92c22b7d257e031b0b4", hash);
    }

    @Test
    void ontologyClassHpoSevere() throws IOException {
        String id = "HP:0012828";
        String label = "Severe";
        OntologyClass neutropenia = ontologyClass(id, label);
        String yamlString = messageToYaml(neutropenia, "ontologyClass");
        System.out.println(yamlString);
        String hash = sha256(yamlString);
        assertEquals("e93ec31eb81c5923a646deba11d32a2550413cbe96a6d92c22b7d257e031b0b4", hash);
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
        OntologyClass causeOfDeath = ontologyClass("NCIT:C36263","Metastatic Malignant Neoplasm");
        TimeElement timeOfDeath = timeElementFromDateString("2015-10-01T10:54:20.021Z");
        VitalStatus deceased = vitalStatusDeceased(causeOfDeath, timeOfDeath);
        String yamlString = messageToYaml(deceased, "vitalStatus");
        //System.out.println(yamlString);
        String hash = sha256(yamlString);
        assertEquals("eae16f64c90fbea14c2010b575426b25b59078245904b198a32a2fb9b8470258", hash);
    }

    @Test
    void vitalStatusAliveYaml() throws IOException {
        VitalStatus alive = vitalStatusAlive();
        String yamlString = messageToYaml(alive, "vitalStatus");
        //System.out.println(yamlString);
        String hash = sha256(yamlString);
        assertEquals("f12616d0a7fa92173179263efef56c22b1b12128deb7057a245d738f2e18ed19", hash);
    }


    @Test
    void gestationalAgeYaml() throws IOException {
        GestationalAge gestationalAge = gestationalAge(33,2);
        String yamlString = messageToYaml(gestationalAge, "gestationalAge");
        System.out.println(yamlString);
        String hash = sha256(yamlString);
        assertEquals("f12616d0a7fa92173179263efef56c22b1b12128deb7057a245d738f2e18ed19", hash);
    }

    @Test
    void testHpoResource() throws IOException {
        Resource hpoResource = hpoResource("2018-03-08");
        String yamlString = messageToYaml(hpoResource, "resource");
        System.out.println(yamlString);
        String hash = sha256(yamlString);
        assertEquals("43a5457bd16282effa6d0ce656c4730c3525c7651765c6c253ce58a696c7db18", hash);
    }

    @Test
    void testHgncResource() throws IOException {
        Resource hgncResource = hgncResource("2019-08-08");
        String yamlString = messageToYaml(hgncResource, "resource");
        System.out.println(yamlString);
        String hash = sha256(yamlString);
        assertEquals("e554b02a815eb1a92884c10440ff18171e05bbbd896e28ee9346e486d32a92b0", hash);
    }

    @Test
    void testUniprotResource() throws IOException {
        Resource uniprotResource = uniprotResource("2019_07");
        String yamlString = messageToYaml(uniprotResource, "resource");
        System.out.println(yamlString);
        String hash = sha256(yamlString);
        assertEquals("e554b02a815eb1a92884c10440ff18171e05bbbd896e28ee9346e486d32a92b0", hash);
    }
}
