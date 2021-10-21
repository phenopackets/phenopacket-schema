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
    public void printCancer() throws IOException {
        System.out.println(PhenopacketFormat.toYaml(TestExamples.cancerPhenopacket()));
    }

    @Test
    public void printRareDisease() throws IOException {
        System.out.println(PhenopacketFormat.toJson(TestExamples.rareDiseasePhenopacket()));
    }

    @Test
    public void printBiosamples() throws IOException {
        System.out.println(PhenopacketFormat.toYaml(TestExamples.biosamplesPhenopacket()));
    }

    @Test
    public void printCovidCase() throws IOException {
        System.out.println(PhenopacketFormat.toYaml(TestExamples.severeCovidCaseWithCardiacComplications()));
    }
}
