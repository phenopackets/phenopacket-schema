package org.phenopackets.schema.v1.converters;

import ca.uhn.fhir.context.FhirContext;
import com.google.common.collect.ImmutableList;
import com.google.protobuf.Timestamp;
import com.google.protobuf.util.JsonFormat;
import org.hl7.fhir.r4.model.*;
import org.junit.jupiter.api.Test;
import org.phenopackets.schema.v1.PhenoPacket;
import org.phenopackets.schema.v1.PhenoPacketTestUtil;
import org.phenopackets.schema.v1.core.*;

import java.time.Instant;
import java.util.Collections;
import java.util.Date;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.phenopackets.schema.v1.PhenoPacketTestUtil.FEMALE;
import static org.phenopackets.schema.v1.PhenoPacketTestUtil.MALE;
import static org.phenopackets.schema.v1.converters.ConverterUtil.ontologyClass;

/**
 * Driver tests to enable the alignment between Phenopackets and FHIR for rare disease cases.
 * 
 * @author Jules Jacobsen <j.jacobsen@qmul.ac.uk>
 */
public class PhenoPacketFhirInteropTestRareDisease {

    // the individual ID needs to match that used in the pedigree
    private static final String FAMILY_ID = "FAMILY:1";
    private static final String PROBAND_ID = "PROBAND:1";
    private static final String MOTHER_ID = "MOTHER:1";
    private static final String FATHER_ID = "FATHER:1";
    private static final String SISTER_ID = "SISTER:1";

    private static final Phenotype abnormalPhenotype = Phenotype.newBuilder()
            .setType(ontologyClass("HP:0000118", "Phenotypic abnormality"))
            .build();

    private static final CodeableConcept FHIR_FEMALE = codeableConcept("http://purl.obolibrary.org/obo/pato.owl", FEMALE.getId(), FEMALE.getLabel());
    private static final CodeableConcept FHIR_MALE = codeableConcept("http://purl.obolibrary.org/obo/pato.owl", MALE.getId(), MALE.getLabel());


    private static CodeableConcept codeableConcept(String system, String id, String label){
        return new CodeableConcept().addCoding(new Coding(system, id, label));
    }

    private static CodeableConcept hpoConcept(String id, String label) {
        return codeableConcept("http://purl.obolibrary.org/obo/hp.owl", id, label);
    }

    /**
     * Simple utility method for converting a Phenopacket Phenotype to a FHIR Condition. Only example code, NOT production ready!
     * @param phenotype
     * @param patient
     * @return
     */
    private static Condition createPatientCondition(Phenotype phenotype, Patient patient) {
        Condition condition = new Condition().setCode(hpoConcept(phenotype.getType().getId(), phenotype.getType().getLabel()));
        condition.setSeverity(hpoConcept(phenotype.getSeverity().getId(), phenotype.getSeverity().getLabel()));
        // Fhir has oneof datetime, Age, Period, String - For this example we're going to use a string
        condition.setOnset(new StringType(phenotype.getClassOfOnset().getLabel()));
        condition.setSubject(new Reference(patient));

        if (phenotype.getNegated()){
            condition.setVerificationStatus(Condition.ConditionVerificationStatus.REFUTED);
        }
        return condition;
    }

    private Pedigree createPedigree() {
        Pedigree.Person mother = Pedigree.Person.newBuilder()
                .setFamilyId(FAMILY_ID)
                .setIndividualId(MOTHER_ID)
                .setSex(Pedigree.Person.Sex.FEMALE)
                .setAffectedStatus(Pedigree.Person.AffectedStatus.AFFECTED)
                .build();

        Pedigree.Person father = Pedigree.Person.newBuilder()
                .setFamilyId(FAMILY_ID)
                .setIndividualId(FATHER_ID)
                .setSex(Pedigree.Person.Sex.MALE)
                .setAffectedStatus(Pedigree.Person.AffectedStatus.UNAFFECTED)
                .build();

        Pedigree.Person son = Pedigree.Person.newBuilder()
                .setFamilyId(FAMILY_ID)
                .setIndividualId(PROBAND_ID)
                .setMaternalId(MOTHER_ID)
                .setPaternalId(FATHER_ID)
                .setSex(Pedigree.Person.Sex.MALE)
                .setAffectedStatus(Pedigree.Person.AffectedStatus.AFFECTED)
                .build();

        return Pedigree.newBuilder()
                .addPersons(mother)
                .addPersons(father)
                .addPersons(son)
                .build();
    }

    @Test
    public void uninterpretedRareDiseaseSample() throws Exception {

        Individual proband = Individual.newBuilder()
                .setSex(FEMALE)
                .setId(PROBAND_ID)
                .setDateOfBirth(Timestamp.newBuilder().setSeconds(Instant.parse("2018-01-01T00:00:00Z").getEpochSecond()).build())
                .addPhenotypes(abnormalPhenotype.toBuilder()
                        .addModifiers(ontologyClass("HP:0012828", "Severe"))
                        .build())
                .build();

        Individual mother = Individual.newBuilder()
                .setSex(FEMALE)
                .setId(MOTHER_ID)
                .setDateOfBirth(Timestamp.newBuilder().setSeconds(Instant.parse("1977-05-25T00:00:00Z").getEpochSecond()).build())
                .addPhenotypes(abnormalPhenotype.toBuilder()
                        .addModifiers(ontologyClass("HP:0012826", "Moderate"))
                        .build())
                .build();

        Individual father = Individual.newBuilder()
                .setSex(MALE)
                .setId(FATHER_ID)
                .build();

        Pedigree trio = createPedigree();

        File vcf = File.newBuilder().setPath("/path/to/vcf.gz").build();
        PhenoPacket rareDiseaseSampleData = PhenoPacket.newBuilder()
                .setId("STUDY_ID:0000123")
                .setPedigree(trio)
                .setPatient(proband)
                .addIndividuals(mother)
                .addIndividuals(father)
                .setGenomeAssembly(GenomeAssembly.GRCH_37)
                .setVcf(vcf)
                .build();

        System.out.println(JsonFormat.printer().print(rareDiseaseSampleData));
        assertThat(rareDiseaseSampleData.getId(), equalTo("STUDY_ID:0000123"));
        assertThat(rareDiseaseSampleData.getPedigree(), equalTo(trio));
        assertThat(rareDiseaseSampleData.getPatient(), equalTo(proband));
        assertThat(rareDiseaseSampleData.getIndividualsList(), equalTo(ImmutableList.of(mother, father)));
        assertThat(rareDiseaseSampleData.getGenomeAssembly(), equalTo(GenomeAssembly.GRCH_37));
        assertThat(rareDiseaseSampleData.getVcf(), equalTo(vcf));
    }

    @Test
    public void toFhirBundle() throws Exception {
        Phenotype probandPhenotype = abnormalPhenotype.toBuilder()
                .setSeverity(ontologyClass("HP:0012828", "Severe"))
                .setClassOfOnset(ontologyClass("HP:0003577", "Congenital onset"))
                .build();

        Instant probandBirthInstant = Instant.parse("2018-01-01T00:00:00Z");
        Individual proband = Individual.newBuilder()
                .setSex(MALE)
                .setId(PROBAND_ID)
                .setDateOfBirth(Timestamp.newBuilder().setSeconds(probandBirthInstant.getEpochSecond()).build())
                .addPhenotypes(probandPhenotype)
                .build();

        //FHIR
        Patient probandPatient = createPatient(proband);
        assertThat(probandPatient.getBirthDate(), equalTo(Date.from(probandBirthInstant)));
        assertThat(probandPatient.getId(), equalTo(PROBAND_ID));
        assertThat(probandPatient.getGender(), equalTo(Enumerations.AdministrativeGender.MALE));

        Observation probandSex = createSexObservation(proband, probandPatient);
        assertThat(probandSex.getCode().getText(), equalTo(FHIR_MALE.getText()));
        assertThat(probandSex.getCode().getCodingFirstRep().getCode(), equalTo(MALE.getId()));
        assertThat(probandSex.getCode().getCodingFirstRep().getDisplay(), equalTo(MALE.getLabel()));

//eurgh! This fails - not the same as what went in...
        //        assertThat(probandSex.getSubject(), equalTo(probandPatient.getId()));

        Condition probandCondition = createPatientCondition(probandPhenotype, probandPatient);
        assertThat(probandCondition.getCode().getCodingFirstRep().getCode(), equalTo(probandPhenotype.getType().getId()));
        assertThat(probandCondition.getCode().getCodingFirstRep().getDisplay(), equalTo(probandPhenotype.getType().getLabel()));

        Phenotype motherPhenotype = abnormalPhenotype.toBuilder()
                .setSeverity(ontologyClass("HP:0012826", "Moderate"))
                .build();

        Instant motherBirthInstant = Instant.parse("1977-05-25T00:00:00Z");
        Individual mother = Individual.newBuilder()
                .setSex(FEMALE)
                .setId(MOTHER_ID)
                .setDateOfBirth(Timestamp.newBuilder().setSeconds(motherBirthInstant.getEpochSecond()).build())
                .addPhenotypes(motherPhenotype)
                .build();

        Patient motherPatient = createPatient(mother);
        Observation motherSex = createSexObservation(mother, motherPatient);
        Condition motherCondition = createPatientCondition(motherPhenotype, motherPatient);


        Pedigree trio = createPedigree();

        //translate to Pedigree to FHIR
        FamilyMemberHistory familyMemberHistory = new FamilyMemberHistory();
        familyMemberHistory.setStatus(FamilyMemberHistory.FamilyHistoryStatus.COMPLETED);
        familyMemberHistory.setPatient(new Reference(probandPatient));
        familyMemberHistory.setRelationship(codeableConcept("http://hl7.org/fhir/ValueSet/v3-FamilyMember", "NMTH", null));//"NFTH" = Natural FaTHer
        Extension extension = new Extension();
        extension.setUrl("http://hl7.org/fhir/StructureDefinition/familymemberhistory-patient-record");
        extension.setValue(new Reference(motherPatient));
        familyMemberHistory.setExtension(Collections.singletonList(extension));

        File vcf = File.newBuilder().setPath("/path/to/vcf.gz").build();
        PhenoPacket rareDiseaseSampleData = PhenoPacket.newBuilder()
                .setId("STUDY_ID:0000123")
                .setPedigree(trio)
                .setPatient(proband)
                .addIndividuals(mother)
                .setGenomeAssembly(GenomeAssembly.GRCH_37)
                .setVcf(vcf)
                .build();

        System.out.println(PhenoPacketTestUtil.toYaml(rareDiseaseSampleData));

        Bundle bundle = new Bundle();
        bundle.setType(Bundle.BundleType.COLLECTION);
        bundle.setId("STUDY_ID:0000123");
        bundle.addEntry().setResource(probandPatient);
        bundle.addEntry().setResource(probandSex);
        bundle.addEntry().setResource(probandCondition);

        bundle.addEntry().setResource(motherPatient);
        bundle.addEntry().setResource(motherSex);
        bundle.addEntry().setResource(motherCondition);

        bundle.addEntry().setResource(familyMemberHistory);

        //add the mother and pedigree
        String bundleJson = FhirContext.forR4().newJsonParser().setPrettyPrint(true).encodeResourceToString(bundle);
        System.out.println(bundleJson);

        for (Bundle.BundleEntryComponent bundleEntryComponent : bundle.getEntry()) {
            System.out.println(bundleEntryComponent.getResource().getResourceType());
        }
    }

    private Observation createSexObservation(Individual individual, Patient patient) {
        OntologyClass sexClasss = individual.getSex();
        //TODO: needs to do a look-up against the metadata object to get the system for this. Right now this is hard-coded to use pato.owl.
        CodeableConcept sexConcept = codeableConcept("http://purl.obolibrary.org/obo/pato.owl", sexClasss.getId(), sexClasss.getLabel());
        return new Observation().setCode(sexConcept).setSubject(new Reference(patient));
    }

    private Patient createPatient(Individual individual) {
        Patient patient = new Patient();
        patient.setId(individual.getId());
       //TODO: make generic for handling timestamp or Age wth "P1Y3M" etc.
        patient.setBirthDate(Date.from(Instant.ofEpochSecond(individual.getDateOfBirth().getSeconds())));
        Enumerations.AdministrativeGender administrativeGender = convertPatoToAdministrativeGender(individual.getSex());
        patient.setGender(administrativeGender);
        return patient;
    }

    // This is horribly brittle - might be safest to bin this and just use the observation or directly model sex as an Enum
    private Enumerations.AdministrativeGender convertPatoToAdministrativeGender(OntologyClass sex) {
        if (!sex.getId().startsWith("PATO") || sex.getId().isEmpty()) {
            return Enumerations.AdministrativeGender.NULL;
        }
        switch (sex.getId()) {
            case "PATO_0000383":
            case "PATO:0000383":
                return Enumerations.AdministrativeGender.FEMALE;
            case "PATO_0000384":
            case "PATO:0000384":
                return Enumerations.AdministrativeGender.MALE;
            default:
                return Enumerations.AdministrativeGender.UNKNOWN;
        }
    }

}
