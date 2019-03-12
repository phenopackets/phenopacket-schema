package org.phenopackets.schema.v1.examples;

import com.google.protobuf.Timestamp;
import org.junit.jupiter.api.Test;
import org.phenopackets.schema.v1.PhenoPacket;
import org.phenopackets.schema.v1.core.*;
import org.phenopackets.schema.v1.io.PhenoPacketFormat;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.phenopackets.schema.v1.PhenoPacketTestUtil.ontologyClass;


/**
 * Add three variants to the urothelial carcinoma
 * 1. 	rs1242535815
 * chr5:g.1295228G>A (TERT promoter mutation, -124C>T
 * 2.  	rs730882008 chr17:g.7577093C>A (ClinVar 182938), TP53
 * 3. AKT chr14	105246551	105246551	C	T (hg37)
 */

public class UrothelialCarcinomaExample {

    private final PhenoPacket phenopacket;

    private final String patientId = "patient1";
    private final String ageAtBiopsy = "P52Y2M";


    public UrothelialCarcinomaExample() {
        MetaData metaData = buildMetaData();

        this.phenopacket = PhenoPacket.newBuilder()
                .setSubject(subject())
                .addBiosamples(bladderBiopsy())
                .addBiosamples(prostateBiospy())
                .addBiosamples(leftUreterBiospy())
                .addBiosamples(rightUreterBiospy())
                .addBiosamples(pelvicLymphNodeBiospy())
                .addDiseases(infiltratingUrothelialCarcinoma())
                .setMetaData(metaData)
                .build();
    }

    private MetaData buildMetaData() {
        return MetaData.newBuilder()
                .addResources(Resource.newBuilder()
                        .setId("ncit")
                        .setName("NCI Thesaurus OBO Edition")
                        .setNamespacePrefix("NCIT")
                        .setUrl("http://purl.obolibrary.org/obo/ncit.owl")
                        .setVersion("18.05d")
                        .build())
                .build();
    }


    private Disease infiltratingUrothelialCarcinoma() {
        return Disease.newBuilder()
                .setId("NCIT:C39853")
                .setLabel("Infiltrating Urothelial Carcinoma")
                .build();
    }

    private Individual subject() {
        return Individual.newBuilder()
                .setId(this.patientId)
                .setDateOfBirth(Timestamp.newBuilder()
                        .setSeconds(Instant.parse("1964-03-15T00:00:00Z").getEpochSecond()))
                .build();
    }

    private Biosample.Builder biosampleBuilder(String patientId, String sampleId, String age, OntologyClass sampleType) {
        return Biosample.newBuilder().
                setIndividualId(patientId).
                setId(sampleId).
                setIndividualAgeAtCollection(Age.newBuilder().
                        setAge(age).
                        build()).
                setType(sampleType);
    }

    private Biosample bladderBiopsy() {
        String sampleId = "sample1";
        // left wall of urinary bladder
        OntologyClass sampleType = ontologyClass("UBERON_0001256", "wall of urinary bladder");
        Biosample.Builder biosampleBuilder = biosampleBuilder(patientId, sampleId, this.ageAtBiopsy, sampleType);
        // also want to mention the procedure, Prostatocystectomy (NCIT:C94464)
        //Infiltrating Urothelial Carcinoma (Code C39853)
        OntologyClass infiltratingUrothelialCarcinoma = ontologyClass("NCIT:C39853", "Infiltrating Urothelial Carcinoma");
        biosampleBuilder.setHistologicalDiagnosis(infiltratingUrothelialCarcinoma);
        // A malignant tumor at the original site of growth
        OntologyClass primary = ontologyClass("NCIT:C84509", "Primary Malignant Neoplasm");
        biosampleBuilder.setTumorProgression(primary);
        // The tumor was staged as pT2b, meaning infiltration into the outer muscle layer of the bladder wall
        // pT2b Stage Finding (Code C48766)
        OntologyClass pT2b = ontologyClass("NCIT:C48766", "pT2b Stage Finding");
        biosampleBuilder.addTumorStage(pT2b);
        //pN2 Stage Finding (Code C48750)
        // cancer has spread to 2 or more lymph nodes in the true pelvis (N2)
        OntologyClass pN2 = ontologyClass("NCIT:C48750", "pN2 Stage Finding");
        biosampleBuilder.addTumorStage(pN2);
        return biosampleBuilder.build();
    }

    private Biosample prostateBiospy() {
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
        return biosampleBuilder.build();
    }

    private Biosample leftUreterBiospy() {
        String sampleId = "sample3";
        OntologyClass sampleType = ontologyClass("UBERON:0001223", "left ureter");
        Biosample.Builder biosampleBuilder = biosampleBuilder(patientId, sampleId, this.ageAtBiopsy, sampleType);
        OntologyClass normalFinding = ontologyClass("NCIT:C38757", "Negative Finding");
        biosampleBuilder.setHistologicalDiagnosis(normalFinding);
        return biosampleBuilder.build();
    }

    private Biosample rightUreterBiospy() {
        String sampleId = "sample4";
        OntologyClass sampleType = ontologyClass("UBERON:0001222", "right ureter");
        Biosample.Builder biosampleBuilder = biosampleBuilder(patientId, sampleId, this.ageAtBiopsy, sampleType);
        OntologyClass normalFinding = ontologyClass("NCIT:C38757", "Negative Finding");
        biosampleBuilder.setHistologicalDiagnosis(normalFinding);
        return biosampleBuilder.build();
    }

    private Biosample pelvicLymphNodeBiospy() {
        String sampleId = "sample5";
        OntologyClass sampleType = ontologyClass("UBERON:0015876", "pelvic lymph node");
        Biosample.Builder biosampleBuilder = biosampleBuilder(patientId, sampleId, this.ageAtBiopsy, sampleType);
        OntologyClass metastasis = ontologyClass("NCIT:C3261", "Metastatic Neoplasm");
        biosampleBuilder.setTumorProgression(metastasis);
        return biosampleBuilder.build();
    }


    @Test
    void testPatientName() {
        assertEquals(this.patientId, this.phenopacket.getSubject().getId());
    }

    @Test
    void printAsJson() throws Exception{
        System.out.println(PhenoPacketFormat.toJson(phenopacket));
    }
}
