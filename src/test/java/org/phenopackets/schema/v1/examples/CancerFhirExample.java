package org.phenopackets.schema.v1.examples;

import org.hl7.fhir.r4.model.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * FHIR representation of the cancer example from the Toronto hackathon.
 *
 * @author Alejandro Metke <alejandro.metke@csiro.au>
 */
class CancerFhirExample {

    static final String SNOMED_CT_SYSTEM = "http://snomed.info/sct";
    static final String LOINC_SYSTEM = "http://loinc.org";
    static final String UCUM_SYSTEM = "http://unitsofmeasure.org";
    static final String SYSTEM_CONDITION_CLINICAL = "http://terminology.hl7.org/CodeSystem/condition-clinical";

    static final CodeableConcept RECURRENCE = getCode(SYSTEM_CONDITION_CLINICAL, "recurrence", "Recurrence");
    static final CodeableConcept ACTIVE = getCode(SYSTEM_CONDITION_CLINICAL, "active", "Active");

    static Bundle cancerBundle() {
        Patient patient = new Patient();
        patient.setId("p-1");

        // male
        addSex(patient, Sex.MALE);

        // date of birth
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        patient.setBirthDate(parseDate("1964-03-15", sdf));

        // patient origin - there is an extension for patient place of birth
        Extension ext = new Extension();
        ext.setUrl("http://hl7.org/fhir/StructureDefinition/birthPlace");
        ext.setValue(new Address().setCountry("Japan").setCity("Beppu"));
        patient.addExtension(ext);

        Encounter initialEncounter = new Encounter();
        initialEncounter.setId("enc-1");
        initialEncounter.setStatus(Encounter.EncounterStatus.FINISHED);
        initialEncounter.setClass_(getCoding("http://hl7.org/fhir/v3/ActCode", "NONAC", "inpatient non-acute"));
        initialEncounter.setSubject(new Reference(patient));

        // HPV-18 positive (cancer tissue)
        // HPV-positive is on cancer tissue so we model this as a body structure
        BodyStructure tumour = new BodyStructure();
        tumour.setId("bs-1");
        tumour.setMorphology(getCode(SNOMED_CT_SYSTEM, "252987004", "Tumour cells"));
        tumour.setPatient(new Reference(patient));

        // We make an observation on the body structure
        Observation hpvObservation = new Observation();
        hpvObservation.setId("obs-1");
        hpvObservation.setCode(getCode(LOINC_SYSTEM, "77377-0", "Human "
                + "papilloma virus 16 and 18 and 31+33+35+39+45+51+52+56+58+59+66+"
                + "68 DNA [Interpretation] in Unspecified specimen"));
        hpvObservation.setValue(getCode(LOINC_SYSTEM, "LA22706-8", "HPV type 18 detected"));
        hpvObservation.setFocus(Arrays.asList(new Reference(tumour)));
        hpvObservation.setEncounter(new Reference(initialEncounter));

        // diagnosis - including stage
        Condition carcinoma = new Condition();
        carcinoma.setId("cond-1");
        carcinoma.setClinicalStatus(ACTIVE);
        carcinoma.setCode(getCode(SNOMED_CT_SYSTEM, "402815007", "Squamous cell carcinoma"));
        carcinoma.setBodySite(Arrays.asList(getCode(SNOMED_CT_SYSTEM, "32849002", "Oesophageal structure")));
        carcinoma.addStage().setSummary(getCode(SNOMED_CT_SYSTEM, "67673008", "T2 category"));
        carcinoma.addStage().setSummary(getCode(SNOMED_CT_SYSTEM, "53623008", "N1 category"));
        carcinoma.addStage().setSummary(getCode(SNOMED_CT_SYSTEM, "30893008", "M0 category"));

        // Represented P48Y3M in UCUM months (i.e. (48 *12) + 3)
        carcinoma.setOnset(new Age().setSystem(UCUM_SYSTEM).setCode("mo").setValue(579));
        carcinoma.setEncounter(new Reference(initialEncounter));

        // diagnostic sample (tumor resection) => variants, SNV (WES) & CNV
        // (array) analysed in Cambridge, MA, U.S. - this cannot be captured at
        // this granularity
        Specimen tumourResection = new Specimen();
        tumourResection.setId("spec-1");
        tumourResection.setType(getCode(SNOMED_CT_SYSTEM, "370612006", "Excision of neoplasm"));
        tumourResection.setSubject(new Reference(patient));

        Observation cnvVariant = new Observation();
        cnvVariant.setId("obs-cnv");
        cnvVariant.getMeta().addProfile("http://hl7.org/fhir/uv/genomics-reporting/StructureDefinition/obs-described-variant");

        // This is TBD in the current implementation guide - following
        // suggestion from MGHA
        cnvVariant.setCode(getCode(LOINC_SYSTEM, "69548-6", " Genetic variant assessment"));
        // reference_name = 8
        cnvVariant.addComponent(getStudiedGene("8"));

        // TODO Ignoring genetic coordinate system because code is still TBD in FHIR

        // start = 116618580
        // end = 145078636
        cnvVariant.addComponent(getGenomicAlleleStartEnd(116618580, 145078636));

        // There is no notion of "cipos" and "ciend" in the current FHIR
        // genomic implementation
        // guide. However, these can be transformed into inner and outer start
        // and end.
        // cipos = [-500, 500]
        // ciend = [-500, 500]

        // In this case outer start = 116618580 - 500 = 116618080
        // outer end = 145078636 + 500 = 145079136
        // inner start = 116618580 + 500 = 116619080
        // inner end = 145078636 - 500 = 145078136
        cnvVariant.addComponent(getStructuralVariantOuterStartEnd(116618080, 145079136));
        cnvVariant.addComponent(getStructuralVariantInnerStartEnd(116619080, 145078136));

        // variant_type = DUP
        cnvVariant.addComponent(getDnaChangeType(DnaChangeType.DUPLICATION));

        // svlen = 28460056
        cnvVariant.addComponent(getStructuralVariantLength(28460056));

        // TODO Couldn't find this attribute in GA4GH schemas documentation:
        // experiment_type

        // Link to specimen
        cnvVariant.setSpecimen(new Reference(tumourResection));

        // Biosample (lymph node biopsy) => variants, SNV (WES) & CNV (array)
        Specimen lymphNodeBiopsy = new Specimen();
        lymphNodeBiopsy.setId("spec-2");
        lymphNodeBiopsy.setType(getCode(SNOMED_CT_SYSTEM, "309079007", "Lymph node biopsy"));
        lymphNodeBiopsy.setSubject(new Reference(patient));

        Observation snvVariant = new Observation();
        snvVariant.setId("obs-snv");
        snvVariant.getMeta().addProfile("http://hl7.org/fhir/uv/genomics-reporting/StructureDefinition/obs-described-variant");

        // reference_name = 1
        snvVariant.addComponent(getStudiedGene("8"));

        // start = 43447122
        // end = 43447122
        snvVariant.addComponent(getGenomicAlleleStartEnd(43447122, 43447122));

        // reference_bases = C
        snvVariant.addComponent(getGenomicRefAllele("C"));

        // alternate_bases = T
        snvVariant.addComponent(getGenomicAltAllele("T"));

        // genotype = [ 0, 1], i.e. CT
        Observation genotype = new Observation();
        genotype.setId("obs-gen");
        genotype.getMeta().addProfile("http://hl7.org/fhir/uv/genomics-reporting/StructureDefinition/obs-genotype");
        genotype.addCategory(getCode("http://hl7.org/fhir/observation-category", "laboratory", "Laboratory"));
        genotype.setValue(new StringType("CT"));
        genotype.addDerivedFrom(new Reference(snvVariant));

        // The example didn't really sepcify which variants were associated
        // with each specimen so we just make a arbitrary decision
        DiagnosticReport tumourReport = new DiagnosticReport();
        tumourReport.setId("diag-1");
        tumourReport.setStatus(DiagnosticReport.DiagnosticReportStatus.FINAL);
        tumourReport.addSpecimen(new Reference(tumourResection));
        tumourReport.addSpecimen(new Reference(lymphNodeBiopsy));
        tumourReport.addResult(new Reference(cnvVariant));
        tumourReport.addResult(new Reference(snvVariant));
        tumourReport.addResult(new Reference(genotype));

        Procedure surgery = new Procedure();
        surgery.setId("proc-1");
        surgery.setStatus(Procedure.ProcedureStatus.COMPLETED);
        surgery.setCode(getCode(SNOMED_CT_SYSTEM, "387713003", "Surgical procedure"));
        surgery.setSubject(new Reference(patient));
        surgery.setEncounter(new Reference(initialEncounter));

        Procedure radiation = new Procedure();
        radiation.setId("proc-2");
        radiation.setStatus(Procedure.ProcedureStatus.COMPLETED);
        radiation.setCode(getCode(SNOMED_CT_SYSTEM, "108290001", "Radiation oncology AND/OR radiotherapy"));
        radiation.setSubject(new Reference(patient));
        radiation.setEncounter(new Reference(initialEncounter));

        // Second encounter
        Encounter secondEncounter = new Encounter();
        secondEncounter.setId("enc-2");
        secondEncounter.setStatus(Encounter.EncounterStatus.FINISHED);
        secondEncounter.setClass_(getCoding("http://hl7.org/fhir/v3/ActCode",
                "NONAC", "inpatient non-acute"));
        secondEncounter.setSubject(new Reference(patient));

        // The recurrence
        Condition carcinomaRecurrence = new Condition();
        // Same id because this will be an update
        carcinomaRecurrence.setId("cond-1");
        carcinoma.setClinicalStatus(RECURRENCE);
        carcinomaRecurrence.setCode(getCode(SNOMED_CT_SYSTEM, "402815007", "Squamous cell carcinoma"));
        carcinomaRecurrence.setBodySite(Arrays.asList(getCode(SNOMED_CT_SYSTEM, "32849002", "Oesophageal structure")));
        carcinomaRecurrence.addStage().setSummary(getCode(SNOMED_CT_SYSTEM, "67673008", "T2 category"));
        carcinomaRecurrence.addStage().setSummary(getCode(SNOMED_CT_SYSTEM, "53623008", "N1 category"));
        carcinomaRecurrence.addStage().setSummary(getCode(SNOMED_CT_SYSTEM, "55440008", "M1 category"));
        carcinomaRecurrence.setOnset(new Age().setSystem(UCUM_SYSTEM).setCode("mo").setValue(590));
        carcinomaRecurrence.setEncounter(new Reference(secondEncounter));

        // Condition to represent the metastasis in the lung
        Condition metastasis = new Condition();
        metastasis.setId("cond-2");
        metastasis.setClinicalStatus(ACTIVE);
        metastasis.setCode(getCode(SNOMED_CT_SYSTEM, "94391008", "Secondary malignant neoplasm of lung"));
        // No need for a body site because it is implicit in the code
        // No need for staging because this is a metastasis of the primary cancer
        metastasis.setOnset(new Age().setSystem(UCUM_SYSTEM)
                .setCode("mo").setValue(590));
        metastasis.setEncounter(new Reference(secondEncounter));

        Procedure palliativeRadiation = new Procedure();
        palliativeRadiation.setId("proc-3");
        palliativeRadiation.setStatus(Procedure.ProcedureStatus.COMPLETED);
        palliativeRadiation.setCode(getCode(SNOMED_CT_SYSTEM, "108290001", "Radiation oncology AND/OR radiotherapy"));
        palliativeRadiation.setReasonCode(Arrays.asList(getCode(SNOMED_CT_SYSTEM, "362964009", "Palliative procedure")));
        palliativeRadiation.setSubject(new Reference(patient));
        palliativeRadiation.setEncounter(new Reference(secondEncounter));

        // Third encounter
        Encounter thirdEncounter = new Encounter();
        thirdEncounter.setId("enc-3");
        thirdEncounter.setStatus(Encounter.EncounterStatus.FINISHED);
        thirdEncounter.setClass_(getCoding("http://hl7.org/fhir/v3/ActCode","NONAC", "inpatient non-acute"));
        thirdEncounter.setSubject(new Reference(patient));

        // The patient death
        Patient patientDeceased = new Patient();
        patientDeceased.setId("p-1");
        addSex(patientDeceased, Sex.MALE);
        patientDeceased.setBirthDate(parseDate("1964-03-15", sdf));
        patientDeceased.setDeceased(new BooleanType(true));
        // TODO We need an extension for death age - only date time is supported in the
        // core spec

        Bundle bundle = new Bundle();
        bundle.setType(Bundle.BundleType.TRANSACTION);
        bundle.addEntry().setResource(patient).getRequest()
                .setMethod(Bundle.HTTPVerb.PUT)
                .setUrl("/Patient/" + patient.getId());
        bundle.addEntry().setResource(initialEncounter).getRequest()
                .setMethod(Bundle.HTTPVerb.PUT)
                .setUrl("/Encounter/" + initialEncounter.getId());
        bundle.addEntry().setResource(tumour).getRequest()
                .setMethod(Bundle.HTTPVerb.PUT)
                .setUrl("/BodyStructure/" + tumour.getId());
        bundle.addEntry().setResource(hpvObservation).getRequest()
                .setMethod(Bundle.HTTPVerb.PUT)
                .setUrl("/Observation/" + hpvObservation.getId());
        bundle.addEntry().setResource(carcinoma).getRequest()
                .setMethod(Bundle.HTTPVerb.PUT)
                .setUrl("/Condition/" + carcinoma.getId());
        bundle.addEntry().setResource(tumourResection).getRequest()
                .setMethod(Bundle.HTTPVerb.PUT)
                .setUrl("/Specimen/" + tumourResection.getId());
        bundle.addEntry().setResource(cnvVariant).getRequest()
                .setMethod(Bundle.HTTPVerb.PUT)
                .setUrl("/Observation/" + cnvVariant.getId());
        bundle.addEntry().setResource(lymphNodeBiopsy).getRequest()
                .setMethod(Bundle.HTTPVerb.PUT)
                .setUrl("/Specimen/" + lymphNodeBiopsy.getId());
        bundle.addEntry().setResource(snvVariant).getRequest()
                .setMethod(Bundle.HTTPVerb.PUT)
                .setUrl("/Observation/" + snvVariant.getId());
        bundle.addEntry().setResource(genotype).getRequest()
                .setMethod(Bundle.HTTPVerb.PUT)
                .setUrl("/Observation/" + genotype.getId());
        bundle.addEntry().setResource(tumourReport).getRequest()
                .setMethod(Bundle.HTTPVerb.PUT)
                .setUrl("/DiagnosticReport/" + tumourReport.getId());
        bundle.addEntry().setResource(surgery).getRequest()
                .setMethod(Bundle.HTTPVerb.PUT)
                .setUrl("/Procedure/" + surgery.getId());
        bundle.addEntry().setResource(radiation).getRequest()
                .setMethod(Bundle.HTTPVerb.PUT)
                .setUrl("/Procedure/" + radiation.getId());
        bundle.addEntry().setResource(secondEncounter).getRequest()
                .setMethod(Bundle.HTTPVerb.PUT)
                .setUrl("/Encounter/" + secondEncounter.getId());
        bundle.addEntry().setResource(carcinomaRecurrence).getRequest()
                .setMethod(Bundle.HTTPVerb.PUT)
                .setUrl("/Condition/" + carcinomaRecurrence.getId());
        bundle.addEntry().setResource(metastasis).getRequest()
                .setMethod(Bundle.HTTPVerb.PUT)
                .setUrl("/Condition/" + metastasis.getId());
        bundle.addEntry().setResource(palliativeRadiation).getRequest()
                .setMethod(Bundle.HTTPVerb.PUT)
                .setUrl("/Procedure/" + palliativeRadiation.getId());
        bundle.addEntry().setResource(thirdEncounter).getRequest()
                .setMethod(Bundle.HTTPVerb.PUT)
                .setUrl("/Encounter/" + thirdEncounter.getId());
        bundle.addEntry().setResource(patientDeceased).getRequest()
                .setMethod(Bundle.HTTPVerb.PUT)
                .setUrl("/Patient/" + patientDeceased.getId());

        return bundle;
    }

    static Date parseDate(String date, SimpleDateFormat simpleDateFormat) {
        try {
            return simpleDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    enum Sex {
        MALE, FEMALE, TRANSEXUAL, INTERSEX, INDETERMINATE_SEX
    }

    enum DnaChangeType {
        WILD_TYPE("LA9658-1", "Wild type"),
        DELETION("LA6692-3", "Deletion"),
        DUPLICATION("LA6686-5", "Duplication"),
        INSERTION("LA6687-3", "Insertion"),
        INSERTION_DELETION("LA6688-1", "Insertion/Deletion"),
        INVERSION("LA6689-9", "Inversion"),
        SUBSTITUTION("LA6690-7", "Substitution"),
        COPY_NUMBER_GAIN("LA14033-7", "Copy number gain"),
        COPY_NUMBER_LOSS("LA14034-5", "Copy number loss"),
        MOBILE_ELEMENT_INSERTION("LA26324-6", "Mobile element insertion"),
        NOVEL_SEQUENCE_INSERTION("LA26325-3", "Mobile element insertion"),
        TANDEM_DUPLICATION("LA26326-1", "Tandem duplication"),
        INTRACHROMOSOMAL_BREAKPOINT("LA26327-9", "Intrachromosomal breakpoint"),
        INTERCHROMOSOMAL_BREAKPOINT("LA26328-7", "Interchromosomal breakpoint"),
        TRANSLOCATION("LA26331-1", "Translocation"),
        COMPLEX("LA26330-3", "Complex"),
        SEQUENCE_ALTERATION("LA26329-5", "Sequence alteration");

        private final String code;
        private final String display;

        DnaChangeType(String code, String display) {
            this.code = code;
            this.display = display;
        }

        public String getCode() {
            return code;
        }

        public String getDisplay() {
            return display;
        }
    }

    private static void addSex(Patient p, Sex sex) {
        Observation ob = new Observation();
        ob.setStatus(Observation.ObservationStatus.FINAL);
        ob.setCode(getCode(SNOMED_CT_SYSTEM, "734000001", "Biological sex"));
        switch (sex) {
            case FEMALE:
                ob.setValue(getCode(SNOMED_CT_SYSTEM, "248152002", "Female"));
                break;
            case INDETERMINATE_SEX:
                ob.setValue(getCode(SNOMED_CT_SYSTEM, "32570681000036106", "Indeterminate sex"));
                break;
            case INTERSEX:
                ob.setValue(getCode(SNOMED_CT_SYSTEM, "32570691000036108", "Intersex"));
                break;
            case MALE:
                ob.setValue(getCode(SNOMED_CT_SYSTEM, "248153007", "Male"));
                break;
            case TRANSEXUAL:
                ob.setValue(getCode(SNOMED_CT_SYSTEM, "407374003", "Transsexual"));
                break;
            default:
                throw new RuntimeException("Unexpected sex value: " + sex);
        }
        ob.setSubject(new Reference(p));
    }

    private static CodeableConcept getCode(String system, String code, String label) {
        return new CodeableConcept().addCoding(getCoding(system, code, label));
    }

    private static Coding getCoding(String system, String code, String label) {
        return new Coding(system, code, label);
    }

    private static Observation.ObservationComponentComponent getStudiedGene(String val) {
        Observation.ObservationComponentComponent res = new Observation.ObservationComponentComponent();
        res.setCode(getCode(LOINC_SYSTEM, "48018-6", "Gene studied [ID]"));
        res.setValue(new StringType(val));
        return res;
    }

    private static Observation.ObservationComponentComponent getGenomicAlleleStartEnd(long start, long end) {
        Observation.ObservationComponentComponent res = new Observation.ObservationComponentComponent();
        res.setCode(getCode(LOINC_SYSTEM, "81254-5", "Genomic allele start-end"));
        Range val = new Range();
        SimpleQuantity low = new SimpleQuantity();
        low.setValue(start);
        SimpleQuantity high = new SimpleQuantity();
        high.setValue(end);
        val.setLow(low);
        val.setHigh(high);
        res.setValue(val);
        return res;
    }

    private static Observation.ObservationComponentComponent getStructuralVariantLength(long val) {
        Observation.ObservationComponentComponent res = new Observation.ObservationComponentComponent();
        res.setCode(getCode(LOINC_SYSTEM, "81300-6", "Structural variant [Length]"));
        res.setValue(new IntegerType(val));
        return res;
    }

    private static Observation.ObservationComponentComponent getStructuralVariantOuterStartEnd(long start, long end) {
        Observation.ObservationComponentComponent res = new Observation.ObservationComponentComponent();
        res.setCode(getCode(LOINC_SYSTEM, "81301-4", "Structural variant outer start and end"));
        Range val = new Range();
        SimpleQuantity low = new SimpleQuantity();
        low.setValue(start);
        SimpleQuantity high = new SimpleQuantity();
        high.setValue(end);
        val.setLow(low);
        val.setHigh(high);
        res.setValue(val);
        return res;
    }

    private static Observation.ObservationComponentComponent getStructuralVariantInnerStartEnd(long start, long end) {
        Observation.ObservationComponentComponent res = new Observation.ObservationComponentComponent();
        res.setCode(getCode(LOINC_SYSTEM, "81302-2", "Structural variant inner start and end"));
        Range val = new Range();
        SimpleQuantity low = new SimpleQuantity();
        low.setValue(start);
        SimpleQuantity high = new SimpleQuantity();
        high.setValue(end);
        val.setLow(low);
        val.setHigh(high);
        res.setValue(val);
        return res;
    }

    private static Observation.ObservationComponentComponent getDnaChangeType(DnaChangeType dnaChangeType) {
        Observation.ObservationComponentComponent res = new Observation.ObservationComponentComponent();
        res.setCode(getCode(LOINC_SYSTEM, "48019-4", "DNA change [Type]"));
        res.setValue(getCode(LOINC_SYSTEM, dnaChangeType.getCode(), dnaChangeType.getDisplay()));
        return res;
    }

    private static Observation.ObservationComponentComponent getGenomicRefAllele(String val) {
        Observation.ObservationComponentComponent res = new Observation.ObservationComponentComponent();
        res.setCode(getCode(LOINC_SYSTEM, "69547-8", "Genomic ref allele [ID]"));
        res.setValue(new StringType(val));
        return res;
    }

    private static Observation.ObservationComponentComponent getGenomicAltAllele(String val) {
        Observation.ObservationComponentComponent res = new Observation.ObservationComponentComponent();
        res.setCode(getCode(LOINC_SYSTEM, "69551-0", "Genomic alt allele [ID]"));
        res.setValue(new StringType(val));
        return res;
    }

}
