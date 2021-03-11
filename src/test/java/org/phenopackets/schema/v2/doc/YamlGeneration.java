package org.phenopackets.schema.v2.doc;

import org.junit.jupiter.api.Test;
import org.phenopackets.schema.v1.core.OntologyClass;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.phenopackets.schema.v2.doc.Wrapper.buildOntologyClass;

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
}
