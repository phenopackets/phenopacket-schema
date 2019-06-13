package org.phenopackets.schema.v1.examples;

import org.hl7.fhir.r4.model.*;

import java.time.Instant;
import java.util.Collections;
import java.util.Date;

import static org.phenopackets.schema.v1.PhenoPacketTestUtil.codeableConcept;

/**
 * FHIR representation of the rare disease example from the Toronto hackathon.
 *
 * @author Jules Jacobsen <j.jacobsen@qmul.ac.uk>
 */
class RareDiseaseFhirExample {

    /**
     * @return
     */
    static Bundle rareDiseaseBundle() {
        Patient probandPatient = new Patient();
        probandPatient.setId("PROBAND#1");
        probandPatient.setBirthDate(Date.from(Instant.parse("1998-01-01T00:00:00Z")));
        probandPatient.setGender(Enumerations.AdministrativeGender.MALE);

        CodeableConcept sexConcept = codeableConcept("http://purl.obolibrary.org/obo/pato.owl", "PATO:0000384", "male");
        Observation probandSex = new Observation().setCode(sexConcept).setSubject(new Reference(probandPatient));

        Condition syndactylyCondition = new Condition();
        syndactylyCondition.setCode(hpoConcept("HP:0001159", "Syndactyly"));
        // Fhir has oneof datetime, Age, Period, String - For this example we're going to use a string
        syndactylyCondition.setOnset(new StringType("Congenital onset"));
        syndactylyCondition.setSubject(new Reference(probandPatient));

        Condition pneumoniaCondition = new Condition();
        pneumoniaCondition.setCode(hpoConcept("HP:0002090", "Pneumonia"));
        // Fhir has oneof datetime, Age, Period, String - For this example we're going to use a string
        pneumoniaCondition.setOnset(new StringType("Childhood onset"));
        pneumoniaCondition.setSubject(new Reference(probandPatient));

        Condition cryptorchidismCondition = new Condition();
        cryptorchidismCondition.setCode(hpoConcept("HP:0000028", "Cryptorchidism"));
        // Fhir has oneof datetime, Age, Period, String - For this example we're going to use a string
        cryptorchidismCondition.setOnset(new StringType("Congenital onset"));
        cryptorchidismCondition.setSubject(new Reference(probandPatient));

        Condition chronicSinusitisCondition = new Condition();
        chronicSinusitisCondition.setCode(hpoConcept("HP:0011109", "Chronic sinusitis"));
        // Fhir has oneof datetime, Age, Period, String - For this example we're going to use a string
        chronicSinusitisCondition.setOnset(new StringType("Adult onset"));
        chronicSinusitisCondition.setSeverity(hpoConcept("HP:0012828", "Severe"));
        chronicSinusitisCondition.setSubject(new Reference(probandPatient));

//
//        PhenotypicFeature motherPhenotype = abnormalPhenotype.toBuilder()
//                .setSeverity(ontologyClass("HP:0012826", "Moderate"))
//                .build();
//
//        Instant motherBirthInstant = Instant.parse("1977-05-25T00:00:00Z");
//        Individual mother = Individual.newBuilder()
//                .setSex(FEMALE)
//                .setId(MOTHER_ID)
//                .setDateOfBirth(Timestamp.newBuilder().setSeconds(motherBirthInstant.getEpochSecond()).build())
//                .addPhenotypicFeatures(motherPhenotype)
//                .build();
//
//        Patient motherPatient = createPatient(mother);
//        Observation motherSex = createSexObservation(mother, motherPatient);
//        Condition motherCondition = createPatientCondition(motherPhenotype, motherPatient);
//
//        //translate to Pedigree to FHIR
        FamilyMemberHistory familyMemberHistory = new FamilyMemberHistory();
        familyMemberHistory.setStatus(FamilyMemberHistory.FamilyHistoryStatus.COMPLETED);
        familyMemberHistory.setPatient(new Reference(probandPatient));
        familyMemberHistory.setRelationship(codeableConcept("http://hl7.org/fhir/ValueSet/v3-FamilyMember", "NMTH", null));//"NFTH" = Natural FaTHer
        Extension extension = new Extension();
        extension.setUrl("http://hl7.org/fhir/StructureDefinition/familymemberhistory-patient-record");
//        extension.setValue(new Reference(motherPatient));
        familyMemberHistory.setExtension(Collections.singletonList(extension));

        Bundle bundle = new Bundle();
        bundle.setType(Bundle.BundleType.COLLECTION);
        bundle.setId("STUDY_ID:0000123");
        bundle.addEntry().setResource(probandPatient);
        bundle.addEntry().setResource(probandSex);
        bundle.addEntry().setResource(syndactylyCondition);
        bundle.addEntry().setResource(pneumoniaCondition);
        bundle.addEntry().setResource(cryptorchidismCondition);
        bundle.addEntry().setResource(chronicSinusitisCondition);
//
//        bundle.addEntry().setResource(motherPatient);
//        bundle.addEntry().setResource(motherSex);
//        bundle.addEntry().setResource(motherCondition);
//
        bundle.addEntry().setResource(familyMemberHistory);

        return bundle;
    }

    private static CodeableConcept hpoConcept(String id, String label){
        return codeableConcept("http://pul.obolibrary.org/obo/hpo.owl", id, label);
    }
}
