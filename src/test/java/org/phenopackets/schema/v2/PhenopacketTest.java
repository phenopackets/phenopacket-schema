package org.phenopackets.schema.v2;

import org.junit.jupiter.api.Test;
import org.phenopackets.schema.v2.examples.TestExamples;
import org.phenopackets.schema.v2.io.PhenopacketFormat;

import java.io.IOException;

/**
 * @author Jules Jacobsen <j.jacobsen@qmul.ac.uk>
 */
public class PhenopacketTest {

    @Test
    void printCancer() throws IOException {
        System.out.println(PhenopacketFormat.toYaml(TestExamples.cancerPhenopacket()));
    }

    @Test
    void printRareDisease() throws IOException {
        System.out.println(PhenopacketFormat.toYaml(TestExamples.rareDiseasePhenopacket()));
    }

    @Test
    void printBiosamples() throws IOException {
        System.out.println(PhenopacketFormat.toYaml(TestExamples.biosamplesPhenopacket()));
    }

    @Test
    void printCovidCase() throws IOException {
        System.out.println(PhenopacketFormat.toYaml(TestExamples.severeCovidCaseWithCardiacComplications()));
    }
}
