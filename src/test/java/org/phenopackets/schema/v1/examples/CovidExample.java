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

        System.out.println(patient.getLiveState());

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

        return Phenopacket.newBuilder()
                .setMetaData(MetaData.newBuilder()
                        .setApiVersion(ApiVersion.v1_1)
                        .build())
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
                .addMedicalActions(MedicalAction.newBuilder()
                        .setPharmaceuticalTreatment(PharmaceuticalTreatment.newBuilder()
                                .setDrug(ontologyClass("NCIT:C722", "Oxygen"))
                                .setRouteOfAdministration(ontologyClass("NCIT:C38284", "Nasal Route of Administration"))
                                .addDoseIntervals(DoseInterval.newBuilder()
                                        .setInterval(Interval.newBuilder().setStart(timestampFrom(LocalDate.parse("2020-03-20"))).setEnd(timestampFrom(LocalDate.parse("2020-03-22"))).build())
                                        .setQuantity(Quantity.newBuilder().setUnit(ontologyClass("NCIT:C67388", "Liter per Minute")).setValue(2).build())
                                        .build())
                                .addDoseIntervals(DoseInterval.newBuilder()
                                        .setInterval(Interval.newBuilder().setStart(timestampFrom(LocalDate.parse("2020-03-22"))).setEnd(timestampFrom(LocalDate.parse("2020-03-23"))).build())
                                        .setQuantity(Quantity.newBuilder().setUnit(ontologyClass("NCIT:C67388", "Liter per Minute")).setValue(50).build())
                                        .build())))
                .addMedicalActions(MedicalAction.newBuilder()
                        .setPharmaceuticalTreatment(PharmaceuticalTreatment.newBuilder()
                                .setDrug(ontologyClass("NCIT:C557", "Hydroxychloroquine"))
                                .addDoseIntervals(DoseInterval.newBuilder()
                                        .setInterval(Interval.newBuilder().setStart(timestampFrom(LocalDate.parse("2020-03-20"))).setEnd(timestampFrom(LocalDate.parse("2020-03-22"))).build())
                                        .build())
                                .setStopReasonId(StopReason.REMOVED)
                                .build())
                        .build())
                .addMedicalActions(MedicalAction.newBuilder()
                        .setProcedure(Procedure.newBuilder()
                                .setCode(ontologyClass("NCIT:C116648", "Tracheal Intubation"))
                                .setPerformed(TimeElement.newBuilder().setTimestamp(timestampFrom(LocalDate.parse("2020-03-22"))).build())
                                .build())
                        .build())
                // TODO: how to detail the amount of Oxygen administered via ventilator?
                .addMedicalActions(MedicalAction.newBuilder()
//                        .setProcedure(Procedure.newBuilder().build())
                        .setPharmaceuticalTreatment(PharmaceuticalTreatment.newBuilder()
                                .setDrug(ontologyClass("NCIT:C722", "Oxygen"))
                                .setRouteOfAdministration(ontologyClass("NCIT:C50254", "Positive end Expiratory Pressure Valve Device"))
                                .addDoseIntervals(DoseInterval.newBuilder()
                                        .setInterval(Interval.newBuilder().setStart(timestampFrom(LocalDate.parse("2020-03-22"))).setEnd(timestampFrom(LocalDate.parse("2020-03-28"))).build())
                                        .setQuantity(Quantity.newBuilder().setUnit(ontologyClass("NCIT:C91060", "Centimeters of Water")).setValue(14).build())
                                        .build()))
                        .build())
                .addMedicalActions(MedicalAction.newBuilder()
                        .setPharmaceuticalTreatment(PharmaceuticalTreatment.newBuilder()
                                .setDrug(ontologyClass("NCIT:C84217", "Tocilizumab"))
                                .addDoseIntervals(DoseInterval.newBuilder()
                                        .setInterval(Interval.newBuilder().setStart(timestampFrom(LocalDate.parse("2020-03-24"))).setEnd(timestampFrom(LocalDate.parse("2020-03-28"))).build())
                                        .build())
                        .build()))
                .addDiseases(cardiomyopathy)
                .addDiseases(chronicKidneyDisease)
                .addDiseases(obesity)
                .addDiseases(covid19)
                .build();
    }

    private static TimeElement parseLocalDate(String isoLocalDate) {
        return TimeElement.newBuilder()
                .setTimestamp(timestampFrom(LocalDate.parse(isoLocalDate)))
                .build();
    }

    private static TimeElement parseLocalDateRange(String isoLocalDateStart, String isoLocalDateEnd) {
        return TimeElement.newBuilder()
                .setInterval(Interval.newBuilder()
                        .setStart(timestampFrom(LocalDate.parse(isoLocalDateStart)))
                        .setEnd(timestampFrom(LocalDate.parse(isoLocalDateEnd)))
                        .build())
                .build();
    }

    private static Timestamp timestampFrom(LocalDate localDateStart) {
        return Timestamp.newBuilder().setSeconds(localDateStart.atStartOfDay().toEpochSecond(ZoneOffset.UTC)).build();
    }
}
