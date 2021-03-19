package org.phenopackets.schema.v2.doc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.phenopackets.schema.v1.core.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

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
        assertEquals("5475f5293308af2149caae789e101dbeb7cf2aaaf5fa9a8d242de588617fc795", hash);
    }


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
        assertEquals("2163baf411b84c60284e5bfe86a65a035fbacd6e3f9d23478e6ad900786fc49b", hash);
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
        assertEquals("10a8a1697ee43a21da4b74da68740735522211c01cd9b56008848b3579304c76", hash);
    }

    @Test
    void testExternalReference() throws IOException {
        String id = "PMID:30962759";
        String description = "Recurrent Erythema Nodosum in a Child with a SHOC2 Gene Mutation";
        ExternalReference externalReference = externalReference(id, description);
        String yamlString = messageToYaml(externalReference, "externalReference");
        //System.out.println(yamlString);
        String hash = sha256(yamlString);
        assertEquals("399143dfe80ddb492759832328fc60606ce8483c406b3915c0643d237c9e2f25", hash);
    }

    @Test
    void testEvidence() throws IOException {
        String evidenceId = "PMID:30962759";
        String description = "Recurrent Erythema Nodosum in a Child with a SHOC2 Gene Mutation";
        Evidence evidence = evidenceWithEcoAuthorStatement(evidenceId, description);
        String yamlString = messageToYaml(evidence, "evidence");
        System.out.println(yamlString);
        String hash = sha256(yamlString);
        assertEquals("e0d8e9f371c77a275afcb6f844d00e42b4e7a84aa9154d666255f69bdca1df54", hash);
    }

    @Test
    void testGene() throws IOException {
        String id = "HGNC:347";
        String symbol = "ETF1";
        Gene gene = gene(id, symbol);
        String yamlString = messageToYaml(gene, "gene");
        System.out.println(yamlString);
        String hash = sha256(yamlString);
        assertEquals("7b80f285c6a5d4b6479db01c58b9d6820f47853d7d6607f542e2f9d83a332e51", hash);
    }

    @Test
    void testGeneWithAltIds() throws IOException {
        String id = "HGNC:347";
        String symbol = "ETF1";
        List<String> alternateIds = List.of("ensembl:ENSRNOG00000019450", "ncbigene:307503");
        Gene gene = gene(id, symbol, alternateIds);
        String yamlString = messageToYaml(gene, "gene");
        System.out.println(yamlString);
        String hash = sha256(yamlString);
        assertEquals("c8f8dd3459738139cb9e5ba033d0bad4ba23dfbfea0d4df9cc4dac53010223d2", hash);
    }

    @Test
    void testAge() throws IOException {
        String validAge = "P25Y3M2D";
        Age age = age(validAge);
        String yamlString = messageToYaml(age, "age");
        //System.out.println(yamlString);
        String hash = sha256(yamlString);
        assertEquals("3515fa76f5944a3b7b630a10ca76e110f4059ae6f90d82b8d78d18fcea96527b", hash);
        Assertions.assertThrows(RuntimeException.class, () ->{
            String invalidAge = "25Y3M2D";
            Age age2 = age(invalidAge);
        });
    }

    @Test
    void testAgeRange() throws IOException {
        String bottom = "P45Y";
        String top = "P49Y";
        AgeRange ageRange = ageRange(bottom, top);
        String yamlString = messageToYaml(ageRange, "ageRange");
        System.out.println(yamlString);
        String hash = sha256(yamlString);
        assertEquals("e55c7fbfbc064be8957295f1a3b0ad7219c79f8a3cbec2411d8ea5f776b6daf3", hash);
    }
}

