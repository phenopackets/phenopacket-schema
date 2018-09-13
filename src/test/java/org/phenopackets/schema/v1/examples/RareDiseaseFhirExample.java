package org.phenopackets.schema.v1.examples;

import com.google.protobuf.Timestamp;
import org.hl7.fhir.r4.model.*;
import org.phenopackets.schema.v1.core.Individual;
import org.phenopackets.schema.v1.core.Phenotype;

import java.time.Instant;
import java.util.Collections;
import java.util.Date;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.phenopackets.schema.v1.PhenoPacketTestUtil.FEMALE;

/**
 * FHIR representation of the rare disease example from the Toronto hackathon. See
 * src/test/resources/toronto_rare_disease_example.md.
 *
 * @author Jules Jacobsen <j.jacobsen@qmul.ac.uk>
 */
class RareDiseaseFhirExample {

    /**
     * @return
     */
    static Bundle rareDiseaseBundle() {
//        Patient probandPatient = new Patient();
//        probandPatient.setId(PROBAND_ID);
//        probandPatient.setBirthDate(Date.from(Instant.parse("2018-01-01T00:00:00Z")));
//        probandPatient.setGender(Enumerations.AdministrativeGender.MALE);
//
//        CodeableConcept sexConcept = codeableConcept("http://purl.obolibrary.org/obo/pato.owl", "PATO:0000384", "male");
//        Observation probandSex = new Observation().setCode(sexConcept).setSubject(new Reference(probandPatient));
//
//
//        Condition probandCondition = createPatientCondition(probandPhenotype, probandPatient);
//        assertThat(probandCondition.getCode().getCodingFirstRep().getCode(), equalTo(probandPhenotype.getType().getId()));
//        assertThat(probandCondition.getCode().getCodingFirstRep().getDisplay(), equalTo(probandPhenotype.getType().getLabel()));
//
//        Phenotype motherPhenotype = abnormalPhenotype.toBuilder()
//                .setSeverity(ontologyClass("HP:0012826", "Moderate"))
//                .build();
//
//        Instant motherBirthInstant = Instant.parse("1977-05-25T00:00:00Z");
//        Individual mother = Individual.newBuilder()
//                .setSex(FEMALE)
//                .setId(MOTHER_ID)
//                .setDateOfBirth(Timestamp.newBuilder().setSeconds(motherBirthInstant.getEpochSecond()).build())
//                .addPhenotypes(motherPhenotype)
//                .build();
//
//        Patient motherPatient = createPatient(mother);
//        Observation motherSex = createSexObservation(mother, motherPatient);
//        Condition motherCondition = createPatientCondition(motherPhenotype, motherPatient);
//
//        //translate to Pedigree to FHIR
//        FamilyMemberHistory familyMemberHistory = new FamilyMemberHistory();
//        familyMemberHistory.setStatus(FamilyMemberHistory.FamilyHistoryStatus.COMPLETED);
//        familyMemberHistory.setPatient(new Reference(probandPatient));
//        familyMemberHistory.setRelationship(codeableConcept("http://hl7.org/fhir/ValueSet/v3-FamilyMember", "NMTH", null));//"NFTH" = Natural FaTHer
//        Extension extension = new Extension();
//        extension.setUrl("http://hl7.org/fhir/StructureDefinition/familymemberhistory-patient-record");
//        extension.setValue(new Reference(motherPatient));
//        familyMemberHistory.setExtension(Collections.singletonList(extension));

        Bundle bundle = new Bundle();
        bundle.setType(Bundle.BundleType.COLLECTION);
        bundle.setId("STUDY_ID:0000123");
//        bundle.addEntry().setResource(probandPatient);
//        bundle.addEntry().setResource(probandSex);
//        bundle.addEntry().setResource(probandCondition);
//
//        bundle.addEntry().setResource(motherPatient);
//        bundle.addEntry().setResource(motherSex);
//        bundle.addEntry().setResource(motherCondition);
//
//        bundle.addEntry().setResource(familyMemberHistory);

        return bundle;
    }
}
