package org.phenopackets.schema.v1.examples;

import com.google.protobuf.Timestamp;
import org.phenopackets.schema.v1.Phenopacket;
import org.phenopackets.schema.v1.core.*;

import java.time.LocalDate;
import java.time.ZoneOffset;

import static org.phenopackets.schema.v1.PhenoPacketTestUtil.ontologyClass;

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
//       NOT "NCIT:C2985", "Diabetes Mellitus"
        // TODO: Hmm unable to negate a disease... "MONDO:0005015", "diabetes mellitus (disease)"
        Disease notDiabetes = Disease.newBuilder().setTerm(ontologyClass("NCIT:C2985", "Diabetes Mellitus")).build();

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
                .setAgeAtCollection(Age.newBuilder().setAge("P70Y").build())
                .setLiveState(LiveState.newBuilder().setDeceased(true).setTimeOfDeath(parseLocalDate("2020-03-28")).build())
                .build();

        MedicalAction lvadImplant = MedicalAction.newBuilder()
                .setProcedure(Procedure.newBuilder()
                        .setCode(ontologyClass("NCIT:C80473", "Left Ventricular Assist Device"))
                        .setPerformed(parseLocalDate("2016-01-01"))
                        .build())
                .build();
        PhenotypicFeature fever = PhenotypicFeature.newBuilder()
                .setType(ontologyClass("NCIT:C3038", "Fever"))
                .setOnsetTime(initialSymptomsTime)
                .build();
        PhenotypicFeature flankPain = PhenotypicFeature.newBuilder()
                .setType(ontologyClass("NCIT:C34615", "Flank Pain"))
                .setOnsetTime(initialSymptomsTime)
                .build();
        PhenotypicFeature hematuria = PhenotypicFeature.newBuilder()
                .setType(ontologyClass("NCIT:C3090", "Hematuria"))
                .setOnsetTime(initialSymptomsTime)
                .build();

        // He was tested for coronavirus disease 2019 (COVID-19), but he left against medical advice.
        // In the ensuing days, he continued to have fever, new onset myalgia, diarrhea, and dyspnea.
        TimeElement preHospitalisationDateRange = parseLocalDateRange("2020-03-18", "2020-03-20");

        PhenotypicFeature myalgia = PhenotypicFeature.newBuilder()
                .setType(ontologyClass("NCIT:C27009", "Myalgia"))
                .setOnsetTime(preHospitalisationDateRange)
                .build();

        PhenotypicFeature diarrhea = PhenotypicFeature.newBuilder()
                .setType(ontologyClass("NCIT:C2987", "Diarrhea"))
                .setOnsetTime(preHospitalisationDateRange)
                .build();

        PhenotypicFeature dyspnea = PhenotypicFeature.newBuilder()
                .setType(ontologyClass("NCIT:C2998", "Dyspnea"))
                .setOnsetTime(preHospitalisationDateRange)
                .build();

        // He returned to the emergency department and was in acute hypoxic respiratory failure requiring
        // supplemental oxygen to maintain peripheral oxygen saturation ≥94%.
        TimeElement returnToHospitalTime = parseLocalDate("2020-03-20");

        Disease covid19 = Disease.newBuilder()
                .setTerm(ontologyClass("MONDO:0100096", "COVID-19"))
                .setOnsetTime(initialSymptomsTime)
                .build();

        PhenotypicFeature acuteRespiratoryFailure = PhenotypicFeature.newBuilder()
                .setType(ontologyClass("NCIT:C27043", "Acute Respiratory Failure"))
                .setOnsetTime(returnToHospitalTime)
                .build();

        MedicalAction nasalOxygenAdministered = MedicalAction.newBuilder()
                .setPharmaceuticalTreatment(PharmaceuticalTreatment.newBuilder()
                        .setDrug(ontologyClass("NCIT:C722", "Oxygen"))
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
                .setPharmaceuticalTreatment(PharmaceuticalTreatment.newBuilder()
                        .setDrug(ontologyClass("NCIT:C557", "Hydroxychloroquine"))
                        .addDoseIntervals(DoseInterval.newBuilder()
                                .setInterval(parseLocalDateInterval("2020-03-20", "2020-03-22"))
                                .build())
                        .setStopReasonId(StopReason.REMOVED)
                        .build())
                .build();

        MedicalAction trachealIntubation = MedicalAction.newBuilder()
                .setProcedure(Procedure.newBuilder()
                        .setCode(ontologyClass("NCIT:C116648", "Tracheal Intubation"))
                        .setPerformed(parseLocalDate("2020-03-22"))
                        .build())
                .build();

        MedicalAction peepOxygenAdministered = MedicalAction.newBuilder()
                // TODO: how to detail the amount of Oxygen administered via ventilator? Wouldn't this be better
                //  described as a Procedure?
//                        .setProcedure(Procedure.newBuilder().build())
                .setPharmaceuticalTreatment(PharmaceuticalTreatment.newBuilder()
                        .setDrug(ontologyClass("NCIT:C722", "Oxygen"))
                        .setRouteOfAdministration(ontologyClass("NCIT:C50254", "Positive end Expiratory Pressure Valve Device"))
                        .addDoseIntervals(DoseInterval.newBuilder()
                                .setInterval(parseLocalDateInterval("2020-03-22", "2020-03-28"))
                                .setQuantity(quantityOf(14, ontologyClass("NCIT:C91060", "Centimeters of Water")))
                                .build()))
                .build();

        MedicalAction.Builder tocilizumabAdministered = MedicalAction.newBuilder()
                .setPharmaceuticalTreatment(PharmaceuticalTreatment.newBuilder()
                        .setDrug(ontologyClass("NCIT:C84217", "Tocilizumab"))
                        .addDoseIntervals(DoseInterval.newBuilder()
                                .setInterval(parseLocalDateInterval("2020-03-24", "2020-03-28"))
                                .build())
                        .build());

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

        MetaData metaData = MetaData.newBuilder()
                .setApiVersion(ApiVersion.v1_1)
                .addResources(ncit)
                .addResources(mondo)
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
                .addMedicalActions(lvadImplant)
                .addMedicalActions(nasalOxygenAdministered)
                .addMedicalActions(hydroxychloroquineAdministered)
                .addMedicalActions(trachealIntubation)
                .addMedicalActions(peepOxygenAdministered)
                .addMedicalActions(tocilizumabAdministered)
                .addDiseases(cardiomyopathy)
                .addDiseases(chronicKidneyDisease)
                .addDiseases(obesity)
                .addDiseases(covid19)
                .build();
    }

    private static Quantity quantityOf(double value, OntologyClass unit) {
        return Quantity.newBuilder().setUnit(unit).setValue(value).build();
    }

    private static Interval parseLocalDateInterval(String isoLocalDateStart, String isoLocalDateEnd) {
        return Interval.newBuilder().setStart(parseIsoLocalDate(isoLocalDateStart)).setEnd(parseIsoLocalDate(isoLocalDateEnd)).build();
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
