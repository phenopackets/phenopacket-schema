package org.phenopackets.schema.v1;

import ca.uhn.fhir.context.FhirContext;
import com.google.common.collect.ImmutableList;
import com.google.protobuf.Timestamp;
import com.google.protobuf.util.JsonFormat;
import org.hl7.fhir.r4.model.*;
import org.junit.Test;
import org.phenopackets.schema.v1.core.*;
import org.phenopackets.schema.v1.core.Resource;

import java.io.IOException;
import java.time.Instant;
import java.util.Collections;
import java.util.Date;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.phenopackets.schema.v1.PhenoPacketTestUtil.FEMALE;
import static org.phenopackets.schema.v1.PhenoPacketTestUtil.MALE;
import static org.phenopackets.schema.v1.PhenoPacketTestUtil.ontologyClass;

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
        //This is a bit of a mismatch here as the severity is coded specifically in
        condition.setSeverity(hpoConcept(phenotype.getSeverity().getId(), phenotype.getSeverity().getLabel()));
        // Fhir has oneof datetime, Age, Period, String - For this example we're going to use a string
        condition.setOnset(new StringType(phenotype.getClassOfOnset().getLabel()));
        condition.setSubject(new Reference(patient));

        if (phenotype.getNegated()){
            condition.setVerificationStatus(Condition.ConditionVerificationStatus.REFUTED);
        }
        return condition;
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

        Pedigree.Person daughter = Pedigree.Person.newBuilder()
                .setFamilyId(FAMILY_ID)
                .setIndividualId(PROBAND_ID)
                .setMaternalId(MOTHER_ID)
                .setPaternalId(FATHER_ID)
                .setSex(Pedigree.Person.Sex.FEMALE)
                .setAffectedStatus(Pedigree.Person.AffectedStatus.AFFECTED)
                .build();

        return Pedigree.newBuilder()
                .addPersons(mother)
                .addPersons(father)
                .addPersons(daughter)
                .build();
    }

    @Test
    public void toFhirBundle() throws Exception {
        Phenotype probandPhenotype = abnormalPhenotype.toBuilder()
                .setSeverity(ontologyClass("HP:0012828", "Severe"))
                .setClassOfOnset(ontologyClass("HP:0003577", "Congenital onset"))
                .build();

        Individual proband = Individual.newBuilder()
                .setSex(FEMALE)
                .setId(PROBAND_ID)
                .setDateOfBirth(Timestamp.newBuilder().setSeconds(Instant.parse("2018-01-01T00:00:00Z").getEpochSecond()).build())
                .addPhenotypes(probandPhenotype)
                .build();

        //FHIR
        Patient probandPatient = new Patient();
        probandPatient.setId(PROBAND_ID);
        probandPatient.setBirthDate(Date.from(Instant.parse("2018-01-01T00:00:00Z")));
        Observation probandSex = new Observation().setCode(FHIR_FEMALE);
        probandSex.setSubject(new Reference(probandPatient));
        Condition probandCondition = createPatientCondition(probandPhenotype, probandPatient);

        Phenotype motherPhenotype = abnormalPhenotype.toBuilder()
                .setSeverity(ontologyClass("HP:0012826", "Moderate"))
                .build();

        Individual mother = Individual.newBuilder()
                .setSex(FEMALE)
                .setId(MOTHER_ID)
                .setDateOfBirth(Timestamp.newBuilder().setSeconds(Instant.parse("1977-05-25T00:00:00Z").getEpochSecond()).build())
                .addPhenotypes(motherPhenotype)
                .build();

        Patient motherPatient = new Patient();
        motherPatient.setId(MOTHER_ID);
        motherPatient.setBirthDate(Date.from(Instant.parse("1977-05-25T00:00:00Z")));
        Observation motherSex = new Observation().setCode(FHIR_FEMALE);
        motherSex.setSubject(new Reference(motherPatient));
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


    /**
     * Driver project example case - https://docs.google.com/document/d/1_6RwjdJa0qtGeidykZeG_PcPqhdMOIttUirCXGTGpwk
     * Here there are two affected siblings - the proband is affected with two conditions, one caused by a single
     * homozygous allele, the other by a compound heterozygous genotype. The proband's sister is affected with a
     * single condition caused by the compound heterozygous genotype. Neither parent exhibits an abnormal phenotype.
     */
    @Test
    public void probandWithTwoMendelianDiseases() throws IOException {
        Phenotype syndactylyCongenitalOnset = Phenotype.newBuilder()
                .setType(ontologyClass("HP:0001159", "Syndactyly"))
                .setClassOfOnset(ontologyClass("HP:0003577", "Congenital onset"))
                .build();

        Phenotype pneumoniaChildhoodOnset = Phenotype.newBuilder()
                .setType(ontologyClass("HP:0002090", "Pneumonia"))
                .setClassOfOnset(ontologyClass("HP:0011463", "Childhood onset"))
                .build();

        Phenotype cryptorchidismCongenitalOnset = Phenotype.newBuilder()
                .setType(ontologyClass("HP:0000028", "Cryptorchidism"))
                .setClassOfOnset(ontologyClass("HP:0003577", "Congenital onset"))
                .build();

        Phenotype chronicSinusitisAdultOnsetSevere = Phenotype.newBuilder()
                .setType(ontologyClass("HP:0011109", "Chronic sinusitis"))
                .setSeverity(ontologyClass("HP:0012828", "Severe"))
                .setClassOfOnset(ontologyClass("HP:0003581","Adult onset"))
                .build();


        Individual proband = Individual.newBuilder()
                .setSex(MALE)
                .setId(PROBAND_ID)
                .setDateOfBirth(Timestamp.newBuilder().setSeconds(Instant.parse("1998-01-01T00:00:00Z").getEpochSecond()).build())
                .addPhenotypes(syndactylyCongenitalOnset)
                .addPhenotypes(pneumoniaChildhoodOnset)
                .addPhenotypes(cryptorchidismCongenitalOnset)
                .addPhenotypes(chronicSinusitisAdultOnsetSevere)
                .build();

        Pedigree.Person pedProband = Pedigree.Person.newBuilder()
                .setFamilyId(FAMILY_ID)
                .setIndividualId(PROBAND_ID)
                .setSex(Pedigree.Person.Sex.MALE)
                .setMaternalId(MOTHER_ID)
                .setPaternalId(FATHER_ID)
                .setAffectedStatus(Pedigree.Person.AffectedStatus.AFFECTED)
                .build();

        Phenotype notPneumonia = Phenotype.newBuilder()
                .setType(ontologyClass("HP:0002090", "Pneumonia"))
                .setNegated(true)
                .build();

        Phenotype notCryptorchidism = Phenotype.newBuilder()
                .setType(ontologyClass("HP:0000028", "Cryptorchidism"))
                .setNegated(true)
                .build();

        Phenotype notChronicSinusitis = Phenotype.newBuilder()
                .setType(ontologyClass("HP:0011109", "Chronic sinusitis"))
                .setNegated(true)
                .build();

        Individual sisterOfProband = Individual.newBuilder()
                .setSex(FEMALE)
                .setId(SISTER_ID)
                .setDateOfBirth(Timestamp.newBuilder().setSeconds(Instant.parse("2000-03-04T00:00:00Z").getEpochSecond()).build())
                .addPhenotypes(syndactylyCongenitalOnset)
                .addPhenotypes(notPneumonia)
                .addPhenotypes(notChronicSinusitis)
                .build();

        Pedigree.Person pedSister = Pedigree.Person.newBuilder()
                .setFamilyId(FAMILY_ID)
                .setIndividualId(SISTER_ID)
                .setSex(Pedigree.Person.Sex.FEMALE)
                .setMaternalId(MOTHER_ID)
                .setPaternalId(FATHER_ID)
                .setAffectedStatus(Pedigree.Person.AffectedStatus.AFFECTED)
                .build();

        Individual mother = Individual.newBuilder()
                .setSex(FEMALE)
                .setId(MOTHER_ID)
                .build();

        Pedigree.Person pedMother = Pedigree.Person.newBuilder()
                .setFamilyId(FAMILY_ID)
                .setIndividualId(MOTHER_ID)
                .setSex(Pedigree.Person.Sex.FEMALE)
                .setAffectedStatus(Pedigree.Person.AffectedStatus.UNAFFECTED)
                .build();

        Individual father = Individual.newBuilder()
                .setSex(MALE)
                .setId(FATHER_ID)
                .build();

        Pedigree.Person pedFather = Pedigree.Person.newBuilder()
                .setFamilyId(FAMILY_ID)
                .setIndividualId(FATHER_ID)
                .setSex(Pedigree.Person.Sex.MALE)
                .setAffectedStatus(Pedigree.Person.AffectedStatus.UNAFFECTED)
                .build();

        Pedigree pedigree = Pedigree.newBuilder()
                .addPersons(pedProband)
                .addPersons(pedSister)
                .addPersons(pedMother)
                .addPersons(pedFather)
                .build();


        Variant var1 = Variant.newBuilder()
                .setSequence("NM_001361.4")
                .setPosition(423)
                .setDeletion("C")
                .setInsertion("T")
                .setHgvs("NM_001361.4:c.403C>T")
                .putSampleGenotypes(PROBAND_ID, ontologyClass("GENO:0000135", "heterozygous"))
                .putSampleGenotypes(SISTER_ID, ontologyClass("GENO:0000135", "heterozygous"))
                .putSampleGenotypes("FATHER:1", ontologyClass("GENO:0000135", "heterozygous"))
                .build();

        Variant var2 = Variant.newBuilder()
                .setSequence("NM_001361.4")
                .setPosition(474)
                .setDeletion("G")
                .setInsertion("A")
                .setHgvs("NM_001361.4:c.454G>A")
                .putSampleGenotypes(PROBAND_ID, ontologyClass("GENO:0000135", "heterozygous"))
                .putSampleGenotypes(SISTER_ID, ontologyClass("GENO:0000135", "heterozygous"))
                .putSampleGenotypes(MOTHER_ID, ontologyClass("GENO:0000135", "heterozygous"))
                .build();

        Variant var3 = Variant.newBuilder()
                .setSequence("NM_001369.2")
                .setPosition(12639)
                .setDeletion("AA")
                .setInsertion("AAA")
                .setHgvs("NM_001369.2:c.12599dupA")
                .putSampleGenotypes(PROBAND_ID, ontologyClass("GENO:0000136", "homozygous"))
                .putSampleGenotypes(SISTER_ID, ontologyClass("GENO:0000135", "heterozygous"))
                .putSampleGenotypes(MOTHER_ID, ontologyClass("GENO:0000135", "heterozygous"))
                .putSampleGenotypes("FATHER:1", ontologyClass("GENO:0000135", "heterozygous"))
                .build();

        MetaData metaData = MetaData.newBuilder()
                .addResources(Resource.newBuilder()
                        .setId("hp")
                        .setName("human phenotype ontology")
                        .setNamespacePrefix("HP")
                        .setUrl("http://purl.obolibrary.org/obo/hp.owl")
                        .setVersion("2018-03-08")
                        .build())
                .addResources(Resource.newBuilder()
                        .setId("pato")
                        .setName("Phenotype And Trait Ontology")
                        .setNamespacePrefix("PATO")
                        .setUrl("http://purl.obolibrary.org/obo/pato.owl")
                        .setVersion("2018-03-28")
                        .build())
                .addResources(Resource.newBuilder()
                        .setId("geno")
                        .setName("Genotype Ontology")
                        .setNamespacePrefix("GENO")
                        .setUrl("http://purl.obolibrary.org/obo/geno.owl")
                        .setVersion("19-03-2018")
                        .build())
                .setCreatedBy("Jules J.")
                .build();

        PhenoPacket phenoPacket = PhenoPacket.newBuilder()
                .setPatient(proband)
                .addAllIndividuals(ImmutableList.of(sisterOfProband, mother, father))
                .setPedigree(pedigree)
                .addAllVariants(ImmutableList.of(var1, var2, var3))
                .setMetaData(metaData)
                .build();

        System.out.println(PhenoPacketTestUtil.toYaml(phenoPacket));

    }

}
