package org.phenopackets.schema.v2.examples;

import com.google.protobuf.Timestamp;
import org.junit.jupiter.api.Test;
import org.phenopackets.schema.v2.Phenopacket;
import org.phenopackets.schema.v2.core.*;
import org.phenopackets.schema.v2.doc.PhenopacketUtil;
import org.phenopackets.schema.v2.io.FormatMapper;

import java.io.IOException;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.phenopackets.schema.v2.PhenoPacketTestUtil.ontologyClass;


/**
 * Add three variants to the urothelial carcinoma
 * 1. 	rs1242535815
 * chr5:g.1295228G>A (TERT promoter mutation, -124C>T
 * 2.  	rs730882008 chr17:g.7577093C>A (ClinVar 182938), TP53
 * 3. AKT chr14	105246551	105246551	C	T (hg37)
 */

public class UrothelialCarcinomaExample {

    private final Phenopacket phenopacket;

    private final String patientId = "patient1";
    private final String ageAtBiopsy = "P52Y2M";


    public UrothelialCarcinomaExample() {
        MetaData metaData = buildMetaData();

        PhenotypicFeature hematuria = PhenotypicFeature.newBuilder()
                .setType(ontologyClass("HP:0000790","Hematuria"))
                .build();
        PhenotypicFeature dsyuria = PhenotypicFeature.newBuilder()
                .setType(ontologyClass("HP:0100518","Dysuria"))
                .setSeverity(ontologyClass("HP:0012828","Severe"))
                .build();

        this.phenopacket = Phenopacket.newBuilder()
                .setId("example case")
                .setSubject(subject())
                .addPhenotypicFeatures(hematuria)
                .addPhenotypicFeatures(dsyuria)
                .addBiosamples(bladderBiosample())
                .addBiosamples(prostateBiosample())
                .addBiosamples(leftUreterBiosample())
                .addBiosamples(rightUreterBiosample())
                .addBiosamples(pelvicLymphNodeBiosample())
                .addDiseases(infiltratingUrothelialCarcinoma())
                .addFiles(createNormalGermlineHtsFile())
                .setMetaData(metaData)
                .build();
    }

    public File createNormalGermlineHtsFile() {
        // first create a File
        // We are imagining there is a reference to a VCF file for a normal germline genome seqeunce
        return File.newBuilder()
                .setUri("file://data/genomes/germline_wgs.vcf.gz")
                .putIndividualToFileIdentifiers("example case", "NA12345")
                .putFileAttributes("genomeAssembly", "GRCh38")
                .putFileAttributes("fileFormat", "vcf")
                .putFileAttributes("description", "Matched normal germline sample")
                .build();
    }

    public File createSomaticHtsFile() {
        // first create a File
        // We are imagining there is a reference to a VCF file for a normal germline genome seqeunce
        // Now create a File object
        return File.newBuilder()
                .setUri("file://data/genomes/urothelial_ca_wgs.vcf.gz")
                .putIndividualToFileIdentifiers("sample1", "BS342730")
                .putFileAttributes("genomeAssembly", "GRCh38")
                .putFileAttributes("fileFormat", "vcf")
                .putFileAttributes("description", "Urothelial carcinoma sample")
                .build();
    }

    public File createMetastasisHtsFile() {
        // first create a File
        // We are imagining there is a reference to a VCF file for a normal germline genome seqeunce
        return File.newBuilder()
                .setUri("file://data/genomes/metastasis_wgs.vcf.gz")
                .putIndividualToFileIdentifiers("sample5", "BS730275")
                .putFileAttributes("genomeAssembly", "GRCh38")
                .putFileAttributes("fileFormat", "vcf")
                .putFileAttributes("description", "lymph node metastasis sample")
                .build();
    }

    private MetaData buildMetaData() {
        long millis  = System.currentTimeMillis();
        Timestamp timestamp = Timestamp.newBuilder().setSeconds(millis / 1000)
                .setNanos((int) ((millis % 1000) * 1000000)).build();
        return MetaData.newBuilder()
                .setPhenopacketSchemaVersion(PhenopacketUtil.SCHEMA_VERSION)
                .addResources(Resource.newBuilder()
                        .setId("hp")
                        .setName("human phenotype ontology")
                        .setNamespacePrefix("HP")
                        .setIriPrefix("http://purl.obolibrary.org/obo/HP_")
                        .setUrl("http://purl.obolibrary.org/obo/hp.owl")
                        .setVersion("2019-04-08")
                        .build())
                .addResources(Resource.newBuilder()
                        .setId("uberon")
                        .setName("uber anatomy ontology")
                        .setNamespacePrefix("UBERON")
                        .setIriPrefix("http://purl.obolibrary.org/obo/UBERON_")
                        .setUrl("http://purl.obolibrary.org/obo/uberon.owl")
                        .setVersion("2019-03-08")
                        .build())
                .addResources(Resource.newBuilder()
                        .setId("ncit")
                        .setName("NCI Thesaurus OBO Edition")
                        .setNamespacePrefix("NCIT")
                        .setUrl("http://purl.obolibrary.org/obo/ncit.owl")
                        .setVersion("18.05d")
                        .build())
                .setCreatedBy("Peter R")
                .setCreated(timestamp)
                .setSubmittedBy("Peter R")
                .addExternalReferences(ExternalReference.newBuilder()
                        .setId("PMID:29221636")
                        .setDescription("Urothelial neoplasms in pediatric and young adult patients: A large single-center series")
                        .build())
                .build();
    }



    private Disease infiltratingUrothelialCarcinoma() {
        return Disease.newBuilder()
                .setTerm(ontologyClass("NCIT:C39853", "Infiltrating Urothelial Carcinoma"))
                // Disease stage here is calculated based on the TMN findings
                .addDiseaseStage(ontologyClass("NCIT:C27971", "Stage IV"))
                // The tumor was staged as pT2b, meaning infiltration into the outer muscle layer of the bladder wall
                // pT2b Stage Finding (Code C48766)
                .addClinicalTnmFinding(ontologyClass("NCIT:C48766", "pT2b Stage Finding"))
                //pN2 Stage Finding (Code C48750)
                // cancer has spread to 2 or more lymph nodes in the true pelvis (N2)
                .addClinicalTnmFinding(ontologyClass("NCIT:C48750", "pN2 Stage Finding"))
                // M1 Stage Finding
                // the tumour has spread from the original site (Metastatic Neoplasm in lymph node - sample5)
                .addClinicalTnmFinding(ontologyClass("NCIT:C48700", "M1 Stage Finding"))
                .build();
    }

    private Individual subject() {
        return Individual.newBuilder()
                .setId(this.patientId)
                .setSex(Sex.MALE)
                .setDateOfBirth(Timestamp.newBuilder()
                        .setSeconds(Instant.parse("1964-03-15T00:00:00Z").getEpochSecond()))
                .build();
    }

    private Biosample.Builder biosampleBuilder(String patientId, String sampleId, String age, OntologyClass sampleType) {
        return Biosample.newBuilder()
                .setIndividualId(patientId)
                .setId(sampleId)
                .setTimeOfCollection(TimeElement.newBuilder().setAge(Age.newBuilder().setIso8601Duration(age)).build())
                .setSampledTissue(sampleType);
    }

    private Biosample bladderBiosample() {
        String sampleId = "sample1";
        // left wall of urinary bladder
        OntologyClass sampleType = ontologyClass("UBERON_0001256", "wall of urinary bladder");
        Biosample.Builder biosampleBuilder = biosampleBuilder(patientId, sampleId, this.ageAtBiopsy, sampleType);
        // also want to mention the procedure, Prostatocystectomy (NCIT:C94464)
        //Infiltrating Urothelial Carcinoma (Code C39853)
        biosampleBuilder.setHistologicalDiagnosis(ontologyClass("NCIT:C39853", "Infiltrating Urothelial Carcinoma"));
        // A malignant tumor at the original site of growth
        biosampleBuilder.setTumorProgression(ontologyClass("NCIT:C84509", "Primary Malignant Neoplasm"));
        biosampleBuilder.addFiles(createSomaticHtsFile());
        biosampleBuilder.setProcedure(Procedure.newBuilder().setCode(ontologyClass("NCIT:C5189", "Radical Cystoprostatectomy")).build());
        return biosampleBuilder.build();
    }

    private Biosample prostateBiosample() {
        String sampleId = "sample2";
        //prostate
        OntologyClass sampleType = ontologyClass("UBERON:0002367", "prostate gland");
        Biosample.Builder biosampleBuilder = biosampleBuilder(patientId, sampleId, this.ageAtBiopsy, sampleType);
        OntologyClass prostateAcinarAdenocarcinoma = ontologyClass("NCIT:C5596", "Prostate Acinar Adenocarcinoma");
        biosampleBuilder.setHistologicalDiagnosis(prostateAcinarAdenocarcinoma);

        // A primary malignant neoplasm in a patient who has been already diagnosed with a primary malignant neoplasm in another anatomic site.
        OntologyClass secondary = ontologyClass("NCIT:C95606", "Second Primary Malignant Neoplasm");
        biosampleBuilder.setTumorProgression(secondary);

        // The Gleason scoring system is used to grade prostate cancer (1). The Gleason score is based on biopsy samples taken from the prostate.
        // Gleason 7: The tumor tissue is moderately differentiated
        OntologyClass gleason7 = ontologyClass("NCIT:C28091", "Gleason Score 7");
        biosampleBuilder.setTumorGrade(gleason7);
        biosampleBuilder.setProcedure(Procedure.newBuilder().setCode(ontologyClass("NCIT:C15189", "Biopsy")).build());
        return biosampleBuilder.build();
    }

    private Biosample leftUreterBiosample() {
        String sampleId = "sample3";
        OntologyClass sampleType = ontologyClass("UBERON:0001223", "left ureter");
        Biosample.Builder biosampleBuilder = biosampleBuilder(patientId, sampleId, this.ageAtBiopsy, sampleType);
        OntologyClass normalFinding = ontologyClass("NCIT:C38757", "Negative Finding");
        biosampleBuilder.setHistologicalDiagnosis(normalFinding);
        biosampleBuilder.setProcedure(Procedure.newBuilder().setCode(ontologyClass("NCIT:C15189", "Biopsy")).build());
        return biosampleBuilder.build();
    }

    private Biosample rightUreterBiosample() {
        String sampleId = "sample4";
        OntologyClass sampleType = ontologyClass("UBERON:0001222", "right ureter");
        Biosample.Builder biosampleBuilder = biosampleBuilder(patientId, sampleId, this.ageAtBiopsy, sampleType);
        OntologyClass normalFinding = ontologyClass("NCIT:C38757", "Negative Finding");
        biosampleBuilder.setHistologicalDiagnosis(normalFinding);
        return biosampleBuilder.build();
    }

    private Biosample pelvicLymphNodeBiosample() {
        String sampleId = "sample5";
        OntologyClass sampleType = ontologyClass("UBERON:0015876", "pelvic lymph node");
        Biosample.Builder biosampleBuilder = biosampleBuilder(patientId, sampleId, this.ageAtBiopsy, sampleType);
        OntologyClass metastasis = ontologyClass("NCIT:C3261", "Metastatic Neoplasm");
        biosampleBuilder.setTumorProgression(metastasis);
        biosampleBuilder.addFiles(createMetastasisHtsFile());
        biosampleBuilder.setProcedure(Procedure.newBuilder().setCode(ontologyClass("NCIT:C15189", "Biopsy")).build());
        return biosampleBuilder.build();
    }


    @Test
    public void testPatientName() {
        assertEquals(this.patientId, this.phenopacket.getSubject().getId());
    }

    @Test
    public void testInfiltratingUrothelialCarcinomaExample() throws IOException {
        System.out.println(FormatMapper.messageToYaml(phenopacket));
    }
}
