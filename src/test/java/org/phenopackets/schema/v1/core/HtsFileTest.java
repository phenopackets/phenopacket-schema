package org.phenopackets.schema.v1.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HtsFileTest {


    public static HtsFile createNormalGermlineHtsFile() {
        // first create a File
        // We are imagining there is a reference to a VCF file for a normal germline genome seqeunce
        String path = "/data/genomes/germline_wgs.vcf.gz";
        String description = "Matched normal germline sample";
        File file = File.newBuilder().setPath(path).setDescription(description).build();
        // Now create an HtsFile object

        return HtsFile.newBuilder().
                setHtsFormat(HtsFile.HtsFormat.VCF).
                setGenomeAssembly("GRCh38").
                setFile(file).
                build();
    }


    public static HtsFile createSomaticHtsFile() {
        // first create a File
        // We are imagining there is a reference to a VCF file for a normal germline genome seqeunce
        String path = "/data/genomes/urothelial_ca_wgs.vcf.gz";
        String description = "Urothelial carcinoma sample";
        File file = File.newBuilder().setPath(path).setDescription(description).build();
        // Now create an HtsFile object
        return HtsFile.newBuilder().
                setHtsFormat(HtsFile.HtsFormat.VCF).
                setGenomeAssembly("GRCh38").
                setFile(file).
                build();
    }

    public static HtsFile createMetastasisHtsFile() {
        // first create a File
        // We are imagining there is a reference to a VCF file for a normal germline genome seqeunce
        String path = "/data/genomes/metastasis_wgs.vcf.gz";
        String description = "lymph node metastasis sample";
        File file = File.newBuilder().setPath(path).setDescription(description).build();
        // Now create an HtsFile object
        return HtsFile.newBuilder().
                setHtsFormat(HtsFile.HtsFormat.VCF).
                setGenomeAssembly("GRCh38").
                setFile(file).
                build();
    }



    @Test
    void testHtsFileType() {
        HtsFile htsfile = createNormalGermlineHtsFile();
        assertEquals(HtsFile.HtsFormat.VCF, htsfile.getHtsFormat());
    }


}
