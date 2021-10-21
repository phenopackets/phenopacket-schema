package org.phenopackets.schema.v2.examples;

import com.google.protobuf.Timestamp;
import org.phenopackets.schema.v2.Phenopacket;
import org.phenopackets.schema.v2.core.*;
import org.phenopackets.schema.v2.doc.PhenopacketUtil;

import java.time.Instant;

import static org.phenopackets.schema.v2.PhenoPacketTestUtil.ontologyClass;


/**
 * Phenopacket representation of the cancer example from the Toronto hackathon.
 *
 * @author Jules Jacobsen <j.jacobsen@qmul.ac.uk>
 */
class CancerPhenopacketExample {

    static Phenopacket cancerPhenopacket() {
        MetaData metaData = MetaData.newBuilder()
                .setPhenopacketSchemaVersion(PhenopacketUtil.SCHEMA_VERSION)
                .addResources(Resource.newBuilder()
                        .setId("ncit")
                        .setName("NCI Thesaurus OBO Edition")
                        .setNamespacePrefix("NCIT")
                        .setUrl("http://purl.obolibrary.org/obo/ncit.owl")
                        .setVersion("18.05d")
                        .build())
                .build();

        String patientId = "patient1";

        //Diagnosis - should this be under Disease, or is it a phenotype of the patient or the biosample?
        Disease esophagealCarcinoma = Disease.newBuilder()
                .setTerm(ontologyClass("NCIT:C4024","Esophageal Squamous Cell Carcinoma"))
                .addClinicalTnmFinding(ontologyClass("NCIT:C48724", "T2 Stage Finding"))
                .addClinicalTnmFinding(ontologyClass("NCIT:C48706", "N1 Stage Finding"))
                .addClinicalTnmFinding(ontologyClass("NCIT:C48699", "M0 Stage Finding"))
                .build();


        Individual patient = Individual.newBuilder()
                .setId(patientId)
                .setDateOfBirth(Timestamp.newBuilder()
                        .setSeconds(Instant.parse("1964-03-15T00:00:00Z").getEpochSecond()))
                .build();

        Biosample lymphNodeBiopsy = Biosample.newBuilder()
                .setIndividualId(patientId)
                .setId("sample1")
                .setTimeOfCollection(TimeElement.newBuilder().setAge(Age.newBuilder().setIso8601Duration("P48Y3M")))
                //Biosample (lymph node biopsy)
                .setSampledTissue(ontologyClass("NCIT:C139196", "Esophageal Lymph Node"))
                .setTumorProgression(ontologyClass("NCIT:C84509", "Primary Malignant Neoplasm"))
                //diagnosis: Squamous cell carcinoma of the esophagus, T2N1M0
                .setHistologicalDiagnosis(ontologyClass("NCIT:C4024", "Esophageal Squamous Cell Carcinoma"))
                //HPV-18 positive (cancer tissue)
                .addDiagnosticMarkers(ontologyClass("NCIT:C131711", "Human Papillomavirus-18 Positive"))
                .setProcedure(Procedure.newBuilder().setCode(ontologyClass("NCIT:C15189", "Biopsy")).build())
                // diagnostic sample (tumor resection)
                .build();

        Biosample esophagusBiopsy = Biosample.newBuilder()
                .setIndividualId(patientId)
                .setId("sample2")
                .setTimeOfCollection(TimeElement.newBuilder().setAge(Age.newBuilder().setIso8601Duration("P49Y2M")))
                .setSampledTissue((ontologyClass("NCIT:C12389", "Esophagus")))
                //recurrence sample, esophagus (biopsy)
                .setTumorProgression(ontologyClass("NCIT:C4813", "Recurrent Malignant Neoplasm"))
                .setProcedure(Procedure.newBuilder().setCode(ontologyClass("NCIT:C15189", "Biopsy")).build())
                .build();

        Biosample lungAutopsy = Biosample.newBuilder()
                .setIndividualId(patientId)
                .setId("sample3")
                .setTimeOfCollection(TimeElement.newBuilder().setAge(Age.newBuilder().setIso8601Duration("P50Y7M")))
                .setSampledTissue(ontologyClass("NCIT:C12468", "Lung"))
                .setTumorProgression(ontologyClass("NCIT:C3261", "Metastatic Neoplasm"))
                .setProcedure(Procedure.newBuilder().setCode(ontologyClass("NCIT:C15189", "Biopsy")).build())
                .build();

        return Phenopacket.newBuilder()
                .setSubject(patient)
                .addBiosamples(lymphNodeBiopsy)
                .addBiosamples(esophagusBiopsy)
                .addBiosamples(lungAutopsy)
                .addDiseases(esophagealCarcinoma)
                .setMetaData(metaData)
                .build();
    }
}
