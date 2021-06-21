package org.phenopackets.schema.v2.examples;

import com.google.protobuf.Timestamp;
import org.phenopackets.schema.v2.Phenopacket;
import org.phenopackets.schema.v2.core.*;
import org.phenopackets.schema.v2.doc.PhenopacketUtil;

import java.time.LocalDate;
import java.time.ZoneOffset;

import static org.phenopackets.schema.v2.PhenoPacketTestUtil.ontologyClass;

/**
 * @author Jules Jacobsen <j.jacobsen@qmul.ac.uk>
 */
class CovidExample {

    /** This example of a COVID-19 patient case report is taken from:
     * https://casereports.onlinejacc.org/content/early/2020/05/21/j.jaccas.2020.04.001
     *
     */
    public static Phenopacket severeCovidCaseWithCardiacComplications() {

        // Medical History
        //
        // His medical history included ischemic cardiomyopathy, stage 3 chronic kidney disease, and obesity.
        // "MONDO:0004994", "cardiomyopathy"
        Disease cardiomyopathy = Disease.newBuilder().setTerm(ontologyClass("NCIT:C34830", "Cardiomyopathy")).build();

        // "MONDO:0005300", "chronic kidney disease"
        Disease chronicKidneyDisease = Disease.newBuilder().setTerm(ontologyClass("NCIT:C80389", "Chronic Kidney Disease, Stage 3")).build();

        // "MONDO:0011122", "obesity disorder"
        Disease obesity = Disease.newBuilder().setTerm(ontologyClass("NCIT:C3283", "Obesity")).build();

        // His post-LVAD complications include gastrointestinal bleeding, ventricular tachycardia, and right ventricular
        // (RV) dysfunction but no infectious complications.
        // He did not have diabetes or use tobacco.
        // NOT "NCIT:C2985", "Diabetes Mellitus"
        Disease notDiabetes = Disease.newBuilder()
                .setTerm(ontologyClass("NCIT:C2985", "Diabetes Mellitus"))
                .setExcluded(true)
                .build();

        // His blood group was type A positive.
        PhenotypicFeature bloodGroupA = PhenotypicFeature.newBuilder()
                .setType(ontologyClass("NCIT:C76246", "Blood Group A"))
                .build();

        PhenotypicFeature rhesusPositive = PhenotypicFeature.newBuilder()
                .setType(ontologyClass("NCIT:C76251", "Rh Positive Blood Group"))
                .build();

        TimeElement initialSymptomsTime = parseLocalDate("2020-03-17");
        // 70-year-old male with a destination therapy HeartMate 3 (Abbott Laboratory, Lake Bluff, Illinois)
        // left ventricular assist device (LVAD) implanted in 2016 who developed fever, flank pain, and hematuria 3 days
        // after attending a party
        Individual patient = Individual.newBuilder().setId("P123542")
                .setSex(Sex.MALE)
                .setTimeAtLastEncounter(TimeElement.newBuilder().setAge(Age.newBuilder().setIso8601Duration("P70Y")))
                .setVitalStatus(VitalStatus.newBuilder()
                        .setStatus(VitalStatus.Status.DECEASED)
                        .setTimeOfDeath(parseLocalDate("2020-03-28"))
                        .setCauseOfDeath(ontologyClass("MONDO:0100096", "COVID-19")))
                .build();

        MedicalAction lvadImplant = MedicalAction.newBuilder()
                .setProcedure(Procedure.newBuilder()
                        .setCode(ontologyClass("NCIT:C80473", "Left Ventricular Assist Device"))
                        .setPerformed(parseLocalDate("2016-01-01"))
                        .build())
                .build();
        PhenotypicFeature fever = PhenotypicFeature.newBuilder()
                .setType(ontologyClass("NCIT:C3038", "Fever"))
                .setOnset(initialSymptomsTime)
                .build();
        PhenotypicFeature flankPain = PhenotypicFeature.newBuilder()
                .setType(ontologyClass("NCIT:C34615", "Flank Pain"))
                .setOnset(initialSymptomsTime)
                .build();
        PhenotypicFeature hematuria = PhenotypicFeature.newBuilder()
                .setType(ontologyClass("NCIT:C3090", "Hematuria"))
                .setOnset(initialSymptomsTime)
                .build();

        // He was tested for coronavirus disease 2019 (COVID-19), but he left against medical advice.
        // In the ensuing days, he continued to have fever, new onset myalgia, diarrhea, and dyspnea.
        TimeElement preHospitalisationDateRange = parseLocalDateRange("2020-03-18", "2020-03-20");

        PhenotypicFeature myalgia = PhenotypicFeature.newBuilder()
                .setType(ontologyClass("NCIT:C27009", "Myalgia"))
                .setOnset(preHospitalisationDateRange)
                .build();

        PhenotypicFeature diarrhea = PhenotypicFeature.newBuilder()
                .setType(ontologyClass("NCIT:C2987", "Diarrhea"))
                .setOnset(preHospitalisationDateRange)
                .build();

        PhenotypicFeature dyspnea = PhenotypicFeature.newBuilder()
                .setType(ontologyClass("NCIT:C2998", "Dyspnea"))
                .setOnset(preHospitalisationDateRange)
                .build();

        // He returned to the emergency department and was in acute hypoxic respiratory failure requiring
        // supplemental oxygen to maintain peripheral oxygen saturation ≥94%.
        TimeElement returnToHospitalTime = parseLocalDate("2020-03-20");

        Disease covid19 = Disease.newBuilder()
                .setTerm(ontologyClass("MONDO:0100096", "COVID-19"))
                .setOnset(initialSymptomsTime)
                .build();

        PhenotypicFeature acuteRespiratoryFailure = PhenotypicFeature.newBuilder()
                .setType(ontologyClass("NCIT:C27043", "Acute Respiratory Failure"))
                .setOnset(returnToHospitalTime)
                .build();

        MedicalAction nasalOxygenAdministered = MedicalAction.newBuilder()
                .setTreatment(Treatment.newBuilder()
                        .setAgent(ontologyClass("NCIT:C722", "Oxygen"))
                        .setRouteOfAdministration(ontologyClass("NCIT:C38284", "Nasal Route of Administration"))
                        .addDoseIntervals(DoseInterval.newBuilder()
                                .setInterval(parseLocalDateInterval("2020-03-20", "2020-03-22"))
                                .setQuantity(quantityOf(2, ontologyClass("NCIT:C67388", "Liter per Minute")))
                                .build())
                        .addDoseIntervals(DoseInterval.newBuilder()
                                .setInterval(parseLocalDateInterval("2020-03-22", "2020-03-23"))
                                .setQuantity(quantityOf(50, ontologyClass("NCIT:C67388", "Liter per Minute")))
                                .build()))
                .build();

        MedicalAction hydroxychloroquineAdministered = MedicalAction.newBuilder()
                .setTreatment(Treatment.newBuilder()
                        .setAgent(ontologyClass("NCIT:C557", "Hydroxychloroquine"))
                        // This was not specified in the original report, however we used these
                        // https://www.covid19treatmentguidelines.nih.gov/antiviral-therapy/chloroquine-or-hydroxychloroquine-with-or-without-azithromycin/
                        // 450 mg twice daily for 1 day, followed by 450 mg once daily for 4 days
                        .addDoseIntervals(DoseInterval.newBuilder()
                                .setQuantity(quantityOf(450, ontologyClass("NCIT:C28253", "mg")))
                                .setScheduleFrequency(ontologyClass("NCIT:C64496", "Twice Daily"))
                                .setInterval(parseLocalDateInterval("2020-03-20", "2020-03-20"))
                                .build())
                        .addDoseIntervals(DoseInterval.newBuilder()
                                .setQuantity(quantityOf(450, ontologyClass("NCIT:C28253", "mg")))
                                .setScheduleFrequency(ontologyClass("NCIT:C125004", "Once Daily"))
                                .setInterval(parseLocalDateInterval("2020-03-21", "2020-03-22"))
                                .build())
                        .build())
                .build();

        MedicalAction trachealIntubation = MedicalAction.newBuilder()
                .setProcedure(Procedure.newBuilder()
                        .setCode(ontologyClass("NCIT:C116648", "Tracheal Intubation"))
                        .setPerformed(parseLocalDate("2020-03-22"))
                        .build())
                .build();

        MedicalAction peepOxygenAdministered = MedicalAction.newBuilder()
                .setTreatment(Treatment.newBuilder()
                        .setAgent(ontologyClass("NCIT:C722", "Oxygen"))
                        .setRouteOfAdministration(ontologyClass("NCIT:C50254", "Positive end Expiratory Pressure Valve Device"))
                        .addDoseIntervals(DoseInterval.newBuilder()
                                .setInterval(parseLocalDateInterval("2020-03-22", "2020-03-28"))
                                .setQuantity(quantityOf(14, ontologyClass("NCIT:C91060", "Centimeters of Water")))
                                .build()))
                .build();

        MedicalAction.Builder tocilizumabAdministered = MedicalAction.newBuilder()
                .setTreatment(Treatment.newBuilder()
                        .setAgent(ontologyClass("NCIT:C84217", "Tocilizumab"))
                        .addDoseIntervals(DoseInterval.newBuilder()
                                .setInterval(parseLocalDateInterval("2020-03-24", "2020-03-28"))
                                .build())
                        .build());

        Measurement initialBloodLymphocyteCount = Measurement.newBuilder()
                .setAssay(ontologyClass("NCIT:C113237", "Absolute Blood Lymphocyte Count"))
                .setValue(Value.newBuilder()
                        .setQuantity(Quantity.newBuilder()
                                .setUnit(ontologyClass("NCIT:C67245", "Thousand Cells"))
                                .setValue(1.4)
                        .setReferenceRange(ReferenceRange.newBuilder().setUnit(ontologyClass("NCIT:C67245", "Thousand Cells")).setHigh(4.5).setLow(1.0)))
                )
                .setTimeObserved(TimeElement.newBuilder().setInterval(TimeInterval.newBuilder().setStart(parseIsoLocalDate("2019-09-01")).setEnd(parseIsoLocalDate("2020-03-01"))))
                .build();

        Measurement hoD0bloodLymphocyteCount = Measurement.newBuilder()
                .setAssay(ontologyClass("NCIT:C113237", "Absolute Blood Lymphocyte Count"))
                .setValue(Value.newBuilder()
                        .setQuantity(Quantity.newBuilder()
                                .setUnit(ontologyClass("NCIT:C67245", "Thousand Cells"))
                                .setValue(0.7)
                                .setReferenceRange(ReferenceRange.newBuilder().setUnit(ontologyClass("NCIT:C67245", "Thousand Cells")).setHigh(4.5).setLow(1.0)))
                )
                .setTimeObserved(returnToHospitalTime)
                .build();

        Resource ncit = Resource.newBuilder()
                .setId("ncit")
                .setName("NCI Thesaurus OBO Edition")
                .setUrl("http://purl.obolibrary.org/obo/ncit.owl")
                .setVersion("http://purl.obolibrary.org/obo/ncit/releases/2019-11-26/ncit.owl")
                .setNamespacePrefix("NCIT")
                .build();

        Resource mondo = Resource.newBuilder()
                .setId("mondo")
                .setName("Mondo Disease Ontology")
                .setUrl("http://purl.obolibrary.org/obo/mondo.obo")
                .setNamespacePrefix("MONDO")
                .build();

        Resource doi = Resource.newBuilder()
                .setId("doi")
                .setName("Digital Object Identifier")
                .setUrl("http://dx.doi.org")
                .setNamespacePrefix("DOI")
                .build();

        Resource pubmed = Resource.newBuilder()
                .setId("pubmed")
                .setName("PubMed")
                .setUrl("https://pubmed.ncbi.nlm.nih.gov/")
                .setNamespacePrefix("PUBMED")
                .build();

        ExternalReference article = ExternalReference.newBuilder()
                .setId("DOI:10.1016/j.jaccas.2020.04.001")
                .setReference("PMID:32292915")
                .setDescription("The Imperfect Cytokine Storm: Severe COVID-19 With ARDS in a Patient on Durable LVAD Support")
                .build();

        MetaData metaData = MetaData.newBuilder()
                .setPhenopacketSchemaVersion(PhenopacketUtil.SCHEMA_VERSION)
                .addResources(ncit)
                .addResources(mondo)
                .addResources(doi)
                .addResources(pubmed)
                .addExternalReferences(article)
                .build();

        return Phenopacket.newBuilder()
                .setMetaData(metaData)
                .setSubject(patient)
                .addPhenotypicFeatures(bloodGroupA)
                .addPhenotypicFeatures(rhesusPositive)
                .addPhenotypicFeatures(fever)
                .addPhenotypicFeatures(flankPain)
                .addPhenotypicFeatures(hematuria)
                .addPhenotypicFeatures(myalgia)
                .addPhenotypicFeatures(diarrhea)
                .addPhenotypicFeatures(dyspnea)
                .addPhenotypicFeatures(acuteRespiratoryFailure)
                .addMeasurements(initialBloodLymphocyteCount)
                .addMeasurements(hoD0bloodLymphocyteCount)
                .addMedicalActions(lvadImplant)
                .addMedicalActions(nasalOxygenAdministered)
                .addMedicalActions(hydroxychloroquineAdministered)
                .addMedicalActions(trachealIntubation)
                .addMedicalActions(peepOxygenAdministered)
                .addMedicalActions(tocilizumabAdministered)
                .addDiseases(notDiabetes)
                .addDiseases(cardiomyopathy)
                .addDiseases(chronicKidneyDisease)
                .addDiseases(obesity)
                .addDiseases(covid19)
                .build();
    }

    private static Quantity quantityOf(double value, OntologyClass unit) {
        return Quantity.newBuilder().setUnit(unit).setValue(value).build();
    }

    private static TimeInterval parseLocalDateInterval(String isoLocalDateStart, String isoLocalDateEnd) {
        return TimeInterval.newBuilder().setStart(parseIsoLocalDate(isoLocalDateStart)).setEnd(parseIsoLocalDate(isoLocalDateEnd)).build();
    }

    private static TimeElement parseLocalDate(String isoLocalDate) {
        return TimeElement.newBuilder()
                .setTimestamp(parseIsoLocalDate(isoLocalDate))
                .build();
    }

    private static TimeElement parseLocalDateRange(String isoLocalDateStart, String isoLocalDateEnd) {
        return TimeElement.newBuilder()
                .setInterval(parseLocalDateInterval(isoLocalDateStart, isoLocalDateEnd))
                .build();
    }

    private static Timestamp parseIsoLocalDate(String isoLocalDate) {
        return timestampFrom(LocalDate.parse(isoLocalDate));
    }

    private static Timestamp timestampFrom(LocalDate localDateStart) {
        return Timestamp.newBuilder().setSeconds(localDateStart.atStartOfDay().toEpochSecond(ZoneOffset.UTC)).build();
    }

}
