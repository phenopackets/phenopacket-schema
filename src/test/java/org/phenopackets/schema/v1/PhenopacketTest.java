package org.phenopackets.schema.v1;

import org.junit.jupiter.api.Test;
import org.phenopackets.schema.v1.examples.TestExamples;
import org.phenopackets.schema.v1.io.PhenopacketFormat;

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
}
