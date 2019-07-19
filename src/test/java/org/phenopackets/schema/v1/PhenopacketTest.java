package org.phenopackets.schema.v1;

import org.junit.jupiter.api.Test;
import org.phenopackets.schema.v1.core.*;
import org.phenopackets.schema.v1.examples.TestExamples;
import org.phenopackets.schema.v1.io.PhenopacketFormat;

import java.io.IOException;

import static org.phenopackets.schema.v1.PhenoPacketTestUtil.ontologyClass;

/**
 * @author Jules Jacobsen <j.jacobsen@qmul.ac.uk>
 */
public class PhenopacketTest {

    @Test
    public void testRareDiseaseDiagnosis() throws Exception {

        Gene fgfr2Gene = Gene.newBuilder()
                .setId("HGNC:3689")
                .setSymbol("FGFR2")
                .build();

        // These are equivalent:
        // HGVS: NC_000010.10:g.123256215T>G
        // SPDI: NC_000010.10:123256214:T:G
        // VCF:  10-123256215-T-G or NC_000010.10-123256215-T-G
        SpdiAllele pathogenicAllele = SpdiAllele.newBuilder()
                .setSeqId("NC_000010.10")
                .setPosition(123256214)
                .setDeletedSequence("T")
                .setInsertedSequence("G")
                .build();

        Variant heterozygousPathogenicVariant = Variant.newBuilder()
                .setSpdiAllele(pathogenicAllele)
                .setZygosity(ontologyClass("GENO:0000135", "heterozygous"))
                .build();

        PhenotypicFeature coronalCraniosynostosis = PhenotypicFeature.newBuilder()
                .setType(ontologyClass("HP:0004440", "Coronal craniosynostosis"))
                .setClassOfOnset(ontologyClass("HP:0011463", "Childhood onset"))
                .build();

        PhenotypicFeature maxillaryHypoplasia = PhenotypicFeature.newBuilder()
                .setType(ontologyClass("HP:0000327", "Maxillary hypoplasia"))
                .build();

        PhenotypicFeature cloverleafSkullSevere = PhenotypicFeature.newBuilder()
                .setType(ontologyClass("HP:0002676", "Cloverleaf skull"))
                .setSeverity(ontologyClass("HP:0012828", "Severe"))
                .build();

        PhenotypicFeature brachydactyly = PhenotypicFeature.newBuilder()
                .setType(ontologyClass("HP:0001156", "Brachydactyly"))
                .build();

        PhenotypicFeature craniosynostosis = PhenotypicFeature.newBuilder()
                .setType(ontologyClass("HP:0001363", "Craniosynostosis"))
                .build();

        PhenotypicFeature broadThumb = PhenotypicFeature.newBuilder()
                .setType(ontologyClass("HP:0011304", "Broad thumb"))
                .build();

        PhenotypicFeature broadHallux = PhenotypicFeature.newBuilder()
                .setType(ontologyClass("HP:0010055", "Broad hallux"))
                .setClassOfOnset(ontologyClass("HP:0011463", "Childhood onset"))
                .build();

        PhenotypicFeature proptosisCongenitalSevere = PhenotypicFeature.newBuilder()
                .setType(ontologyClass("HP:0000520", "Proptosis"))
                .setSeverity(ontologyClass("HP:0012828", "Severe"))
                .setClassOfOnset(ontologyClass("HP:0003577", "Congenital onset"))
                .build();

        PhenotypicFeature proptosisCongenitalMild = PhenotypicFeature.newBuilder()
                .setType(ontologyClass("HP:0000520", "Proptosis"))
                .setSeverity(ontologyClass("HP:0012825", "Mild"))
                .setClassOfOnset(ontologyClass("HP:0003577", "Congenital onset"))
                .build();

        PhenotypicFeature intellectualDisabilityOccasional = PhenotypicFeature.newBuilder()
                .setType(ontologyClass("HP:0001249", "Intellectual disability"))
                .setSeverity(ontologyClass("HP:0040283", "Occasional"))
                .build();

        Disease pfeifferSyndrome = Disease.newBuilder()
                .setTerm(ontologyClass("OMIM:101600","PFEIFFER SYNDROME"))
                .build();


        Individual proband = Individual.newBuilder()
                .setId("proband")
                .setSex(Sex.MALE)
                .build();

        MetaData metaData = MetaData.newBuilder()
                .addResources(Resource.newBuilder()
                        .setId("hp")
                        .setName("human phenotype ontology")
                        .setNamespacePrefix("HP")
                        .setIriPrefix("http://purl.obolibrary.org/obo/HP_")
                        .setUrl("http://purl.obolibrary.org/obo/hp.owl")
                        .setVersion("2018-03-08")
                        .build())
                .addResources(Resource.newBuilder()
                        .setId("geno")
                        .setName("Genotype Ontology")
                        .setNamespacePrefix("GENO")
                        .setIriPrefix("http://purl.obolibrary.org/obo/GENO_")
                        .setUrl("http://purl.obolibrary.org/obo/geno.owl")
                        .setVersion("19-03-2018")
                        .build())
                .addResources(Resource.newBuilder()
                        .setId("so")
                        .setName("Sequence types and features")
                        .setNamespacePrefix("SO")
                        .setIriPrefix("http://purl.obolibrary.org/obo/SO_")
                        .setUrl("http://purl.obolibrary.org/obo/so.owl")
                        .setVersion("2015-11-24")
                        .build())
                .setCreatedBy("Jules J.")
                .build();

        Phenopacket pfeifferDiagnosisExample = Phenopacket.newBuilder()
                .setSubject(proband)
                .addPhenotypicFeatures(brachydactyly)
                .addPhenotypicFeatures(craniosynostosis)
                .addPhenotypicFeatures(broadThumb)
                .addPhenotypicFeatures(broadHallux)
                .addPhenotypicFeatures(proptosisCongenitalMild)
                .addVariants(heterozygousPathogenicVariant)
                .addGenes(fgfr2Gene)
                .addDiseases(pfeifferSyndrome)
                .addHtsFiles(HtsFile.newBuilder()
                        .setHtsFormat(HtsFile.HtsFormat.VCF)
                        .setGenomeAssembly("GRCh37")
                        // in this case the proband identifier is different to the sample
                        // identifier used in the VCF file
                        .putIndividualToSampleIdentifiers(proband.getId(), "SAMPLE0001")
                        .setFile(File.newBuilder().setPath("/data/vcfs/proband.vcf").build())
                        .build())
                .setMetaData(metaData)
                .build();

        System.out.println(PhenopacketFormat.toJson(pfeifferDiagnosisExample));
    }

    @Test
    void printCancer() throws IOException {
        System.out.println(PhenopacketFormat.toYaml(TestExamples.cancerPhenopacket()));
    }

    @Test
    void printRareDisease() throws IOException {
        // TODO Add a RareDiseaseFamilyFormat class for this case...
        System.out.println(PhenopacketFormat.toJson(TestExamples.rareDiseasePhenopacket()));
    }

    @Test
    void printBiosamples() throws IOException {
        System.out.println(PhenopacketFormat.toYaml(TestExamples.biosamplesPhenopacket()));
    }
}
