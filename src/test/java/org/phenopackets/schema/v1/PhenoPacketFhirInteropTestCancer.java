package org.phenopackets.schema.v1;

import com.google.protobuf.Timestamp;
import com.google.protobuf.util.JsonFormat;
import org.junit.jupiter.api.Test;
import org.phenopackets.schema.v1.core.*;

import java.time.Instant;

import static org.phenopackets.schema.v1.PhenoPacketTestUtil.ontologyClass;

/**
 * @author Jules Jacobsen <j.jacobsen@qmul.ac.uk>
 */
public class PhenoPacketFhirInteropTestCancer {

    @Test
    public void cancerPhenopacket() throws Exception {

        MetaData metaData = MetaData.newBuilder()
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
                .setId("NCIT:C4024")
                .setLabel("Esophageal Squamous Cell Carcinoma")
                .build();


        Individual patient = Individual.newBuilder()
                .setId(patientId)
                .setDateOfBirth(Timestamp.newBuilder()
                        .setSeconds(Instant.parse("1964-03-15T00:00:00Z").getEpochSecond()))
                .build();

        Biosample lymphNodeBiopsy = Biosample.newBuilder()
                .setIndividualId(patientId)
                .setId("sample1")
                .setIndividualAgeAtCollection(Age.newBuilder().setAge("P48Y3M").build())
                .addPhenotypes(Phenotype.newBuilder().setType(ontologyClass("NCIT:C12745", "Lymph Node")).build())
                .addPhenotypes(Phenotype.newBuilder().setType(ontologyClass("NCIT:C15189", "Biopsy")).build())
                .build();

        Biosample esophagusBiopsy = Biosample.newBuilder()
                .setIndividualId(patientId)
                .setId("sample2")
                .setIndividualAgeAtCollection(Age.newBuilder().setAge("P49Y2M").build())
                .addPhenotypes(Phenotype.newBuilder().setType(ontologyClass("NCIT:C12389", "Esophagus")).build())
                .addPhenotypes(Phenotype.newBuilder().setType(ontologyClass("NCIT:C15189", "Biopsy")).build())
                .setAttributes(Attributes.newBuilder()
                        .putAttributes("Analysis", AttributeValues.newBuilder()
                                .addValues(AttributeValue.newBuilder()
                                .setExperiment(Experiment.newBuilder()
                                        .setDescription("variants, SNV (WES) & CNV (array)")
                                        .build())
                                .build())
                        .build())
                    .build())
                .build();

        Biosample lungAutopsy = Biosample.newBuilder()
                .setIndividualId(patientId)
                .setId("sample3")
                .setIndividualAgeAtCollection(Age.newBuilder().setAge("P50Y7M").build())
                .addPhenotypes(Phenotype.newBuilder().setType(ontologyClass("NCIT:C12468", "Lung")).build())
                .addPhenotypes(Phenotype.newBuilder().setType(ontologyClass("NCIT:C19151", "Metastasis")).build())
                .addPhenotypes(Phenotype.newBuilder().setType(ontologyClass("NCIT:C25153", "Autopsy")).build())
                .build();

        PhenoPacket cancerExample = PhenoPacket.newBuilder()
                .addIndividuals(patient)
                .addBiosamples(lymphNodeBiopsy)
                .addBiosamples(esophagusBiopsy)
                .addBiosamples(lungAutopsy)
                .addDiseases(esophagealCarcinoma)
                .setMetaData(metaData)
                .build();

        System.out.println(JsonFormat.printer().print(cancerExample));

    }
}
