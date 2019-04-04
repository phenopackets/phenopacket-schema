package org.phenopackets.schema.v1.core;

import com.google.protobuf.util.JsonFormat;
import org.junit.jupiter.api.Test;
import org.phenopackets.schema.v1.Phenopacket;

/**
 * This class illustrates the usage of the Variant message for various genotypes.
 *
 * @author Jules Jacobsen <j.jacobsen@qmul.ac.uk>
 */
public class VariantTest {

    private static final OntologyClass HOM = OntologyClass.newBuilder().setId("GENO:0000136").setLabel("homozygous").build();
    private static final OntologyClass HET = OntologyClass.newBuilder().setId("GENO:0000135").setLabel("heterozygous").build();

    private static final VcfAllele FGFR2_PATH_ALLELE_1 = VcfAllele.newBuilder()
            .setGenomeAssembly("GRCh37")
            .setChr("10")
            .setPos(123256215)
            .setRef("T")
            .setAlt("G")
            .build();

    private static final VcfAllele FGFR2_LIKELY_PATH_ALLELE_2 = VcfAllele.newBuilder()
            .setGenomeAssembly("GRCh37")
            .setChr("10")
            .setPos(123247569)
            .setRef("T")
            .setAlt("C")
            .build();

    private static final Disease PFIEFFER_SYNDROME = Disease.newBuilder()
            .setTerm(OntologyClass.newBuilder().setId("OMIM:101600").setLabel("Pfeiffer syndrome").build())
            .build();

    private static final MetaData META_DATA = MetaData.newBuilder()
            .addResources(Resource.newBuilder()
                    .setId("geno")
                    .setName("Genotype Ontology")
                    .setNamespacePrefix("GENO")
                    .setIriPrefix("http://purl.obolibrary.org/obo/GENO_")
                    .setUrl("http://purl.obolibrary.org/obo/geno.owl")
                    .setVersion("19-03-2018")
                    .build())
            .addResources(Resource.newBuilder()
                    .setId("omim")
                    .setName("Online Mendelian Inheritance in Man")
                    .setNamespacePrefix("OMIM")
                    .setUrl("https://omim.org")
                    .setVersion("01-04-2019")
                    .build())
            .setCreatedBy("Jules J.")
            .build();

    @Test
    void homozygousVariant() throws Exception {
        Variant hom = Variant.newBuilder()
                .setVcfAllele(FGFR2_PATH_ALLELE_1)
                .setZygosity(HOM)
                .build();

        Phenopacket phenopacket = Phenopacket.newBuilder()
                .setId("pfeiffer_homozygous_case")
                .addVariants(hom)
                .addDiseases(PFIEFFER_SYNDROME)
                .setMetaData(META_DATA)
                .build();

        System.out.println(JsonFormat.printer().print(phenopacket));
    }

    @Test
    void heterozygousVariant() throws Exception {

        Variant het = Variant.newBuilder()
                .setVcfAllele(FGFR2_PATH_ALLELE_1)
                .setZygosity(HET)
                .build();

        Phenopacket phenopacket = Phenopacket.newBuilder()
                .setId("pfeiffer_heterozygous_case")
                .addVariants(het)
                .addDiseases(PFIEFFER_SYNDROME)
                .setMetaData(META_DATA)
                .build();

        System.out.println(JsonFormat.printer().print(phenopacket));
    }

    @Test
    void compoundHeterozygousVariant() throws Exception {

        Variant het1 = Variant.newBuilder()
                .setVcfAllele(FGFR2_PATH_ALLELE_1)
                .setZygosity(HET)
                .build();

        Variant het2 = Variant.newBuilder()
                .setVcfAllele(FGFR2_LIKELY_PATH_ALLELE_2)
                .setZygosity(HET)
                .build();

        Phenopacket phenopacket = Phenopacket.newBuilder()
                .setId("pfeiffer_compound_heterozygous_case")
                .addVariants(het1)
                .addVariants(het2)
                .addDiseases(PFIEFFER_SYNDROME)
                .setMetaData(META_DATA)
                .build();

        System.out.println(JsonFormat.printer().print(phenopacket));
    }
}
