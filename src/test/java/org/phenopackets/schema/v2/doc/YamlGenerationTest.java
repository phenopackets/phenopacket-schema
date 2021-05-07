package org.phenopackets.schema.v2.doc;

import com.google.protobuf.Message;
import com.google.protobuf.Timestamp;
import com.google.protobuf.util.Timestamps;
import org.ga4gh.vrsatile.v1.Expression;
import org.ga4gh.vrsatile.v1.GeneDescriptor;
import org.ga4gh.vrsatile.v1.VariationDescriptor;
import org.ga4gh.vrsatile.v1.VcfRecord;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.phenopackets.schema.v2.core.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.phenopackets.schema.v2.doc.PhenopacketUtil.*;


/**
 * This class is a convenience class for generating YAML snippets for the documentation. For each snippet,
 * we calculate a Hash value and assert equality. If there is any upstream change, the assertion will fail,
 * which will be a warning to update the documentation.
 */
public class YamlGenerationTest extends TestBase {

    /**
     * Print out the YAML string we will use for documentation and calculate and return the sha2356 hash
     * Comment out the print statement if documentation is finalized.
     * @param message an element of the Phenopacket
     * @param label the label we will put on this element for generating YAML
     * @return a sha256 hash
     */
    private String printAndGetHash(Message message, String label) {
        try {
            String yamlString = messageToYaml(message, label);
            System.out.println(sha256(yamlString));
            System.out.println(yamlString);
            return sha256(yamlString);
        } catch (IOException e) {
            return e.getMessage();
        }
    }


    @Test
    public void ontologyClassHpoNeutropenia() {
        String id = "HP:0001875";
        String label = "Neutropenia";
        OntologyClass neutropenia = ontologyClass(id, label);
        String hash = printAndGetHash(neutropenia, "ontologyClass");
        assertEquals("e93ec31eb81c5923a646deba11d32a2550413cbe96a6d92c22b7d257e031b0b4", hash);
    }

    @Test
    public void ontologyClassHpoSevere() {
        String id = "HP:0012828";
        String label = "Severe";
        OntologyClass severe = ontologyClass(id, label);
        String hash = printAndGetHash(severe, "ontologyClass");
        assertEquals("5475f5293308af2149caae789e101dbeb7cf2aaaf5fa9a8d242de588617fc795", hash);
    }


    @Test
    public void vitalStatusDeceasedYaml() throws ParseException {
        OntologyClass causeOfDeath = ontologyClass("NCIT:C36263","Metastatic Malignant Neoplasm");
        TimeElement timeOfDeath = timeElementFromDateString("2015-10-01T10:54:20.021Z");
        VitalStatus deceased = vitalStatusDeceased(causeOfDeath, timeOfDeath);
        String hash = printAndGetHash(deceased, "vitalStatus");
        assertEquals("eae16f64c90fbea14c2010b575426b25b59078245904b198a32a2fb9b8470258", hash);
    }

    @Test
    public void vitalStatusAliveYaml() {
        VitalStatus alive = vitalStatusAlive();
        String hash = printAndGetHash(alive, "vitalStatus");
        assertEquals("f12616d0a7fa92173179263efef56c22b1b12128deb7057a245d738f2e18ed19", hash);
    }


    @Test
    public void gestationalAgeYaml() {
        GestationalAge gestationalAge = gestationalAge(33,2);
        String hash = printAndGetHash(gestationalAge, "gestationalAge");
        assertEquals("2163baf411b84c60284e5bfe86a65a035fbacd6e3f9d23478e6ad900786fc49b", hash);
    }

    @Test
    public void testHpoResource() {
        Resource hpoResource = hpoResource("2018-03-08");
        String hash = printAndGetHash(hpoResource, "resource");
        assertEquals("43a5457bd16282effa6d0ce656c4730c3525c7651765c6c253ce58a696c7db18", hash);
    }

    @Test
    public void testHgncResource() {
        Resource hgncResource = hgncResource("2019-08-08");
        String hash = printAndGetHash(hgncResource, "resource");
        assertEquals("e554b02a815eb1a92884c10440ff18171e05bbbd896e28ee9346e486d32a92b0", hash);
    }

    @Test
    public void testUniprotResource() {
        Resource uniprotResource = uniprotResource("2019_07");
        String hash = printAndGetHash(uniprotResource, "resource");
        assertEquals("10a8a1697ee43a21da4b74da68740735522211c01cd9b56008848b3579304c76", hash);
    }

    @Test
    public void testExternalReference() {
        String id = "PMID:30962759";
        String description = "Recurrent Erythema Nodosum in a Child with a SHOC2 Gene Mutation";
        ExternalReference externalReference = externalReference(id, description);
        String hash = printAndGetHash(externalReference, "externalReference");
        assertEquals("399143dfe80ddb492759832328fc60606ce8483c406b3915c0643d237c9e2f25", hash);
    }

    @Test
    public void testEvidence() {
        String evidenceId = "PMID:30962759";
        String description = "Recurrent Erythema Nodosum in a Child with a SHOC2 Gene Mutation";
        Evidence evidence = evidenceWithEcoAuthorStatement(evidenceId, description);
        String hash = printAndGetHash(evidence, "evidence");
        assertEquals("e0d8e9f371c77a275afcb6f844d00e42b4e7a84aa9154d666255f69bdca1df54", hash);
    }

    @Test
    public void testGene() {
        String id = "HGNC:347";
        String symbol = "ETF1";
        GeneDescriptor gene = geneDescriptor(id, symbol);
        String hash = printAndGetHash(gene, "gene");
        assertEquals("7b80f285c6a5d4b6479db01c58b9d6820f47853d7d6607f542e2f9d83a332e51", hash);
    }

    @Test
    public void testGeneWithAltIds() {
        String id = "HGNC:347";
        String symbol = "ETF1";
        List<String> alternateIds = List.of("ensembl:ENSRNOG00000019450", "ncbigene:307503");
        GeneDescriptor gene = geneDescriptor(id, symbol, alternateIds);
        String hash = printAndGetHash(gene, "gene");
        assertEquals("c8f8dd3459738139cb9e5ba033d0bad4ba23dfbfea0d4df9cc4dac53010223d2", hash);
    }

    @Test
    public void testAge() {
        String validAge = "P25Y3M2D";
        Age age = age(validAge);
        String hash = printAndGetHash(age, "age");
        assertEquals("3515fa76f5944a3b7b630a10ca76e110f4059ae6f90d82b8d78d18fcea96527b", hash);
        Assertions.assertThrows(RuntimeException.class, () ->{
            String invalidAge = "25Y3M2D";
            Age age2 = age(invalidAge);
        });
    }

    @Test
    public void testAgeRange() {
        String bottom = "P45Y";
        String top = "P49Y";
        AgeRange ageRange = ageRange(bottom, top);
        String hash = printAndGetHash(ageRange, "ageRange");
        assertEquals("e55c7fbfbc064be8957295f1a3b0ad7219c79f8a3cbec2411d8ea5f776b6daf3", hash);
    }

    @Test
    public void testReferenceRange() {
        OntologyClass cellsPerMicroliter = ontologyClass("UO:0000316","cells per microliter");
        double lower = 150_000;
        double upper = 450_000;
        ReferenceRange referenceRange = referenceRange(cellsPerMicroliter, lower, upper);
        String hash = printAndGetHash(referenceRange, "referenceRange");
        assertEquals("d8f1b33456a2fedf52cbaeddef99bb775770a04186ec82924367f4fc7e930380", hash);
    }

    @Test
    public void testPlateletMeasurement() throws ParseException {
        OntologyClass loinc = ontologyClass("LOINC:26515-7","Platelets [#/volume] in Blood");
        OntologyClass cellsPerMicroliter = ontologyClass("UO:0000316","cells per microliter");
        double lower = 150_000;
        double upper = 450_000;
        ReferenceRange referenceRange = referenceRange(cellsPerMicroliter, lower, upper);
        Value value = quantitativeValue(cellsPerMicroliter, 24_000, referenceRange);
        TimeElement time = timeElementFromDateString("2020-10-01T10:54:20.021Z");
        Measurement measurement = measurement(loinc, value, time);
        String hash = printAndGetHash(measurement, "measurement");
        assertEquals("0dc085d5e2a532951b4f4b11401d841de39df27e36f1b7ac4cb60db9643bad62", hash);
    }

    @Test
    public void testNitrituriaMeasurement() throws ParseException {
        OntologyClass loinc = ontologyClass("LOINC:5802-4","Nitrite [Presence] in Urine by Test strip");
        Value present = presentValue();
        TimeElement time = timeElementFromDateString("2021-01-01T10:54:20.021Z");
        Measurement measurement = measurement(loinc, present, time);
        String hash = printAndGetHash(measurement, "measurement");
        assertEquals("26a135acd05734f17750feac37544c2cfa82686145b8ecfe0a494e5ddf12b812", hash);
    }

    @Test
    public void testBloodPressure() throws ParseException {
        TimeElement time = timeElementFromDateString("2021-01-01T10:54:20.021Z");
        Measurement bloodPressure = bloodPressure(125,75, time);
        String hash = printAndGetHash(bloodPressure, "measurement");
        assertEquals("2e6feccf9cb68bf69d68687c7ceb7e88f6760895b84522e08b8a7d5fb6144d24", hash);
    }


    @Test
    public void diastolicBP() {
        OntologyClass diastolicBP = ontologyClass("NCIT:C25299", "Diastolic Blood Pressure");
        OntologyClass mmHg = ontologyClass("NCIT:C49670", "Millimeter of Mercury");
        double diastolic = 70;
        Quantity diastolicQuantity = quantity(diastolic, mmHg);
        TypedQuantity typedQuantity = typedQuantity(diastolicBP, diastolicQuantity);
        String hash = printAndGetHash(typedQuantity, "typedQuantity");
        assertEquals("a1e200d9520c190e936113c168b9cac6747fdab46eb2df937c72aa315bfb14c7", hash);
    }


    @Test
    public void htsFileTest() {
        String uri = "file://data/genomes/germline_wgs.vcf.gz";
        String description = "Matched normal germline sample";
                //"htsFormat": "VCF",
        String genomeAssembly = "GRCh38";
        Map<String,String> individualToSampleIdentifiers = Map.of("patient23456", "NA12345");
        HtsFile vcfFile = vcfFile(uri,description,genomeAssembly,individualToSampleIdentifiers);
        String hash = printAndGetHash(vcfFile, "htsFile");
        assertEquals("ec4ab02f2dc2b0fc46ec62ec5b401ebf97d34cf0178e021d70c71e14cd84e2cd", hash);
    }

    @Test
    public void individualTest() throws ParseException {
        String id = "patient:0";
        Timestamp dob = Timestamps.parse("1998-01-01T00:00:00Z");
        Sex male = Sex.MALE;
        Individual individual = individual(id, dob, male);
        String hash = printAndGetHash(individual, "individual");
        assertEquals("638a7d0480c97f8690c1b76525ddb2725270f56e7aec6374fe52b5a30fd73450", hash);
    }

    @Test
    public void timeIntervalTest() throws ParseException {
        String start = "2020-03-15T13:00:00Z";
        String end = "2020-03-25T09:00:00Z";
        TimeInterval timeInterval = timeInterval(start, end);
        String hash = printAndGetHash(timeInterval, "timeInterval");
        assertEquals("261af4635f6bda84278b76f19382bfca9d23556dbcb62d1feb24db76d12c578b", hash);
    }

    private DoseInterval doseIntervalExample()  throws ParseException {
        String start = "2020-03-15T13:00:00Z";
        String end = "2020-03-25T09:00:00Z";
        TimeInterval timeInterval = timeInterval(start, end);
        OntologyClass unit = ontologyClass("UO:0000022", "milligram");
        Quantity quantity = quantity(30, unit);
        OntologyClass twiceDaily = ontologyClass("NCIT:C64496", "Twice Daily");
        return doseInterval(timeInterval, quantity, twiceDaily);
    }

    @Test
    public void doseIntervalTest() throws ParseException {
        DoseInterval doseInterval = doseIntervalExample();
        String hash = printAndGetHash(doseInterval, "doseInterval");
        assertEquals("21cfbe01d59fcae44b0012571c6f06fd62ae0d0465bb22081473f82523ac4ae1", hash);
    }

    @Test
    public void treatmentTest() throws ParseException  {
        OntologyClass agent = ontologyClass("DrugCentral:1610", "losartan");  // for instance, DrugCentral, RxNorm Drugbank concept
        OntologyClass route_of_administration = ontologyClass("NCIT:C38288","Oral Route of Administration"); // For instance, NCIT subhierarchy: Route of Administration (Code C38114)
        List<DoseInterval> doseIntervalList = List.of(doseIntervalExample());
        DrugType drug_type = DrugType.PRESCRIPTION;
        Treatment treatment = treatment(agent, route_of_administration, doseIntervalList,drug_type);
        String hash = printAndGetHash(treatment, "treatment");
        assertEquals("99126a60a4a9a80024e93d99cd4515a245443d774f431af5dd500928ab7e8656", hash);
    }

    @Test
    public void therapeuticRegimenTreatmentTest() throws ParseException {
        ExternalReference externalReference = ExternalReference.newBuilder()
                .setId("NCT04576091")
                .setReference("https://clinicaltrials.gov/ct2/show/NCT04576091")
                .setDescription("Testing the Addition of an Anti-cancer Drug, BAY1895344, With Radiation Therapy to the Usual Pembrolizumab Treatment for Recurrent Head and Neck Cancer")
                .build();

        TherapeuticRegimenTreatment therapeuticRegimenTreatment = TherapeuticRegimenTreatment.newBuilder().setExternalReference(externalReference)
                .setStartTime(TimeElement.newBuilder().setTimestamp(Timestamps.parse("2020-03-15T13:00:00Z")))
                .setRegimenStatus(TherapeuticRegimenTreatment.RegimenStatus.STARTED)
                .build();
        String hash = printAndGetHash(therapeuticRegimenTreatment, "therapeuticRegimenTreatment");
        assertEquals("152bb717e6d8586928f033407b20f113d8715f7cc07704ef42aaf5d2e79f8796", hash);
    }

    @Test
    public void urothelialCarcinomaBiosample() {
        String id = "sample1";
        String individualId = "patient1";
        String description = "Additional information can go here";
        OntologyClass sampledTissue = ontologyClass("UBERON_0001256","wall of urinary bladder");
        Age age = age("P52Y2M");
        TimeElement timeElement = TimeElement.newBuilder().setAge(age).build();
        OntologyClass histologicalDiagnosis = ontologyClass("NCIT:C39853", "Infiltrating Urothelial Carcinoma");
        OntologyClass tumorProgression = ontologyClass("NCIT:C84509", "Primary Malignant Neoplasm");
        Procedure procedure = Procedure.newBuilder().setCode(ontologyClass("NCIT:C5189", "Radical Cystoprostatectomy")).build();
        String uri = "file:///data/genomes/urothelial_ca_wgs.vcf.gz";
        String htsDescription = "Urothelial carcinoma sample";
        String genomeAssembly = "GRCh38";
        Map<String,String> individualToSampleIdentifiers = Map.of("patient1", "NA12345");
        HtsFile vcfFile = vcfFile(uri,htsDescription,genomeAssembly,individualToSampleIdentifiers);
        OntologyClass stageII = ontologyClass("NCIT:C28054", "Stage II");
        OntologyClass stageT2b = ontologyClass("NCIT:C48726", "T2b Stage Finding");
        OntologyClass stageN0 = ontologyClass("NCIT:C48705", "N0 Stage Finding");
        OntologyClass stageM0 = ontologyClass("NCIT:C48699", "M0 Stage Finding");
        List<OntologyClass> tnm = List.of(stageT2b, stageN0, stageM0);
        OntologyClass grade2 = ontologyClass("NCIT:C36136", "Grade 2 Lesion");

        Biosample biosample = biosample(id,
                individualId,
                description,
                sampledTissue,
                timeElement,
                histologicalDiagnosis,
                tumorProgression,
                stageII,
                tnm,
                grade2,
                procedure,
                vcfFile);
        String hash = printAndGetHash(biosample, "biosample");
        assertEquals("012a36657c4b16663f0d6aca2ca80f549cd9f7b836bf826603080b9579d77295", hash);
    }

    @Test
    public void testVariant() {
        VariationDescriptor variant = heterozygousHgvsVariant("NM_001848.2:c.877G>A");
        String hash = printAndGetHash(variant, "variationDescriptor");
        assertEquals("a60dcb71cf83b9072696716c7514c57cc6e33ca933e6bb82172fa38d3c07bf22", hash);
    }

    @Test
    public void testVariantInterpretation() {
        VariationDescriptor variant = heterozygousHgvsVariant("NM_001848.2:c.877G>A");
        VariantInterpretation variantInterpretation = pathogenicVariantInterpretation(variant);
        String hash = printAndGetHash(variantInterpretation, "variantInterpretation");
        assertEquals("e6e343647c3d015fc4f7131fe5eb920d29b4182024e32ee2f19b9721c35e0a52", hash);
    }



    @Test
    public void testGenomicInterpretation() {
       GenomicInterpretation interpretation = pathogenicGenomicInterpretationOfVariant("subject 1","NM_001848.2:c.877G>A");
        String hash = printAndGetHash(interpretation, "genomicInterpretation");
        assertEquals("8060daad92d638609ca92d9afd5acea93900add3d4b372e1ab1bd41f5e22ce58", hash);
    }



    @Test
    public void testDisease() {
        OntologyClass diseaseTerm = ontologyClass("OMIM:164400","Spinocerebellar ataxia 1");
        Disease disease = diseaseWithOnset(diseaseTerm, "P38Y7M");
        String hash = printAndGetHash(disease, "disease");
        assertEquals("f314e5ca00e0912313dbdc325f4bd4c58cf2c8c8d7ab0bf32fc6f5e19a553267", hash);
    }

    @Test
    public void testMetadata() throws ParseException {
        Resource hp = resource(   "hp",
                "human phenotype ontology",
                "HP",
                "http://purl.obolibrary.org/obo/hp.owl",
                  "2018-03-08",
                "http://purl.obolibrary.org/obo/HP_");
        Resource geno = resource(   "geno",
                  "Genotype Ontology",
                "GENO",
                 "http://purl.obolibrary.org/obo/geno.owl",
                 "19-03-2018",
                "http://purl.obolibrary.org/obo/GENO_");
        Resource pubmedResource = Resource.newBuilder()
                .setId("pubmed")
                .setName("PubMed")
                .setNamespacePrefix("PMID")
                .setUrl("https://www.ncbi.nlm.nih.gov/pubmed/").build();
        String  description = "Bao M, et al. COL6A1 mutation leading to Bethlem myopathy with recurrent hematuria: a case report. BMC Neurol. 2019;19(1):32.";
        ExternalReference ref = externalReference("PMID:30808312", description);
        List<Resource> resourceList = List.of(hp, geno, pubmedResource);
        String created = "2019-07-21T00:25:54.662Z";
        String createdBy = "Peter R.";

        MetaData metadata = metadata(created, createdBy, resourceList, ref);
        String hash = printAndGetHash(metadata, "metadata");
        assertEquals("2d1fb82798db9b0e418573b2150f3adb942eae51e1fb4de55b3945c66d74c4bb", hash);
    }

    @Test
    public void testInterpretationOfPathogenicVar() {
        OntologyClass miller = ontologyClass("OMIM:263750", "Miller syndrome");
        GeneDescriptor dhodh = geneDescriptor("HGNC:2867", "DHODH");
        GenomicInterpretation genomicInterpretation = GenomicInterpretation.newBuilder()
                .setGene(dhodh)
                .setInterpretationStatus(GenomicInterpretation.InterpretationStatus.CONTRIBUTORY)
                .build();
        Diagnosis diagnosis = Diagnosis.newBuilder()
                .setDisease(miller)
                .addGenomicInterpretations(genomicInterpretation)
                .build();
        Interpretation interpretation = Interpretation.newBuilder()
                .setId("SOLVERD:0000123456")
                .setProgressStatus(Interpretation.ProgressStatus.SOLVED)
                .setDiagnosis(diagnosis)
                .build();
        String hash = printAndGetHash(interpretation, "interpretation");
        assertEquals("efe7dadc657af67d286b2e99a7ace745e7c0529f0fca0eab61928a6f1fd65e12", hash);
    }

    @Test
    public void testInterpretationFbn1() {
        GenomicInterpretation variantInterpretation = pathogenicGenomicInterpretationOfVariant("subject 1","NM_000138.4(FBN1):c.6751T>A");
        OntologyClass marfan = ontologyClass("OMIM:154700", "Marfan syndrome");
        Diagnosis diagnosis = Diagnosis.newBuilder()
                .setDisease(marfan)
                .addGenomicInterpretations(variantInterpretation)
                .build();
        Interpretation interpretation = Interpretation.newBuilder()
                .setId("Arbitrary interpretation id")
                .setProgressStatus(Interpretation.ProgressStatus.SOLVED)
                .setDiagnosis(diagnosis)
                .build();
        String hash = printAndGetHash(interpretation, "interpretation");
        assertEquals("42c542aea0919728f578be1119e62924817bc595c77a266e62460ed5269f7c1c", hash);
    }

    @Test
    public void testInterpretationCftr() {
        GenomicInterpretation v1 = pathogenicGenomicInterpretationOfVariant("subject 1","NM_000492.3(CFTR):c.1477C>T (p.Gln493Ter)");
        GenomicInterpretation v2 = pathogenicGenomicInterpretationOfVariant("subject 1","NM_000492.3(CFTR):c.1521_1523delCTT (p.Phe508delPhe)");

        OntologyClass cf = ontologyClass("OMIM: 219700", "Cystic fibrosis");
        Diagnosis diagnosis = Diagnosis.newBuilder()
                .setDisease(cf)
                .addGenomicInterpretations(v1)
                .addGenomicInterpretations(v2)
                .build();
        Interpretation interpretation = Interpretation.newBuilder()
                .setId("Arbitrary interpretation id")
                .setProgressStatus(Interpretation.ProgressStatus.SOLVED)
                .setDiagnosis(diagnosis)
                .build();
        String hash = printAndGetHash(interpretation, "interpretation");
        assertEquals("1746eeeaf427759370dbf62f581d4517561656e315d345bf660af9f33c243446", hash);
    }

    @Test
    public void testInterpretationBraf() {
        VariationDescriptor brafVar = VariationDescriptor.newBuilder()
                .setDescription("NM_001374258.1(BRAF):c.1919T>A (p.Val640Glu)").build();
        VariantInterpretation v1 = VariantInterpretation.newBuilder()
                .setAcmgPathogenicityClassification(AcmgPathogenicityClassification.PATHOGENIC)
                .setTherapeuticActionability(TherapeuticActionability.ACTIONABLE)
                .setVariant(brafVar).build();
        GenomicInterpretation genomicInterpretation = GenomicInterpretation.newBuilder()
                .setVariantInterpretation(v1)
                .setInterpretationStatus(GenomicInterpretation.InterpretationStatus.CONTRIBUTORY)
                .setSubjectOrBiosampleId("biosample id")
                .build();
        OntologyClass melanoma = ontologyClass("NCIT:C3224", "Melanoma");
        Diagnosis diagnosis = Diagnosis.newBuilder()
                .setDisease(melanoma)
                .addGenomicInterpretations(genomicInterpretation)
                .build();
        Interpretation interpretation = Interpretation.newBuilder()
                .setId("Arbitrary interpretation id")
                .setProgressStatus(Interpretation.ProgressStatus.COMPLETED)
                .setDiagnosis(diagnosis)
                .build();
        String hash = printAndGetHash(interpretation, "interpretation");
        assertEquals("72205f6bbb57aae693dc170f0c573026a8c41a3809b0202c68b2ed7f7530185b", hash);
    }

    @Test
    public void aclarubicinTest() throws ParseException {
        OntologyClass aclarubicin = ontologyClass("DrugCentral:80","aclarubicin");
        OntologyClass intravenous = ontologyClass("NCIT:C38276","Intravenous Route of Administration");
        OntologyClass mgPerKgPerDose = ontologyClass("NCIT:C124458","Milligram per Kilogram per Dose");
        Timestamp t1 = Timestamps.parse("2020-07-10T00:00:00Z");
        Timestamp t2 = Timestamps.parse("2020-08-10T00:00:00Z");
        OntologyClass mgPerKg = ontologyClass("EFO:0002902","milligram per kilogram");
        Quantity quantity = quantity(100, mgPerKgPerDose);
        OntologyClass every3weeks = ontologyClass("NCIT:C64535", "Every Three Weeks");
        DoseInterval di = DoseInterval.newBuilder()
                .setInterval(TimeInterval.newBuilder().setStart(t1).setEnd(t2))
                .setQuantity(quantity)
                .setScheduleFrequency(every3weeks)
                .build();
        Treatment treatment = Treatment.newBuilder()
                .setAgent(aclarubicin)
                .setDrugType(DrugType.EHR_MEDICATION_LIST)
                .addDoseIntervals(di)
                .setRouteOfAdministration(intravenous)
                .build();
        Quantity cumulativeQuantity = quantity(200, mgPerKg);
        CumulativeDose cumulativeDose = CumulativeDose.newBuilder()
                .setQuantity(cumulativeQuantity).build();
        ChemoTherapyTreatment chemo = ChemoTherapyTreatment.newBuilder()
                .setTreatment(treatment)
                .setCumulativeDose(cumulativeDose)
                .build();
        String hash = printAndGetHash(chemo, "chemoTherapyTreatment");
        assertEquals("b63559c8dacbaf9665abd46a6ee6ee18b424952ea1270a6d833afbe13eb59676", hash);
    }

    @Test
    public void phenotypicFeatureTest() {
        Age onset = age("P6M");
        Age resolution = age("P4Y2M");
        OntologyClass infantileSpasms = ontologyClass("HP:0012469", "Infantile spasms");
        OntologyClass recurrent = ontologyClass("HP:0031796", "Recurrent");
        PhenotypicFeature phenotypicFeature = PhenotypicFeature.newBuilder()
                .setOnset(TimeElement.newBuilder().setAge(onset))
                .setResolution(TimeElement.newBuilder().setAge(resolution))
                .setType(infantileSpasms)
                .addModifiers(recurrent)
                .build();
        String hash = printAndGetHash(phenotypicFeature, "phenotypicFeature");
        assertEquals("02daef889dd1a62229b48b669004f9f3da325bfa952c27e30743eeb2774320fe", hash);
    }

    @Test
    public void testProcedure() {
        OntologyClass code = ontologyClass("NCIT:C28743", "Punch Biopsy");
        OntologyClass bodySite = ontologyClass("UBERON:0003403", "skin of forearm");
        TimeElement timeElement = TimeElement.newBuilder()
                .setAge(Age.newBuilder().setIso8601Duration("P25Y"))
                .build();
       Procedure procedure = Procedure.newBuilder()
               .setCode(code)
               .setBodySite(bodySite)
               .setPerformed(timeElement)
               .build();
        String hash = printAndGetHash(procedure, "procedure");
        assertEquals("81b3970984ee1f7f9a33e3ed0b1f2e4211862aa622c355ab8571513756767e9c", hash);
    }

    @Test
    public void spdiVariantTest() {
        OntologyClass heterozygous = ontologyClass("GENO:0000135", "heterozygous");
        VariationDescriptor variant = VariationDescriptor.newBuilder()
                .setId("clinvar:13294")
                .addAllExpressions( Collections.singletonList(Expression.newBuilder()
                        .setSyntax("spdi")
                        .setValue("NC_000010.10:123256214:T:G")
                        .build()
                ))
                .setAllelicState(heterozygous)
                .build();
        String hash = printAndGetHash(variant, "variationDescriptor");
        assertEquals("4752d835e75e16d4874e30759e0796466e74f1a09616cbbfcf7ca167ea5327e3", hash);
    }

    @Test
    public void hgvsVariantTest() {
        OntologyClass heterozygous = ontologyClass("GENO:0000135", "heterozygous");
        VariationDescriptor variant = VariationDescriptor.newBuilder()
                .setId("clinvar:13294")
                .addAllExpressions(Collections.singletonList( Expression.newBuilder()
                        .setSyntax("hgvsc")
                        .setValue("NM_000226.3:c.470T>G").build()
                ))
                .setAllelicState(heterozygous)
                .build();
        String hash = printAndGetHash(variant, "variant");
        assertEquals("cc8f5d4b797c5c3d872e8b8e1bf8ebaa3a6e73afe1b6f31c2c86502163547f97", hash);
    }

    @Test
    public void vcfAlleleTest() {
        OntologyClass heterozygous = ontologyClass("GENO:0000135", "heterozygous");
        VcfRecord vcfRecord = VcfRecord.newBuilder()
                .setGenomeAssembly("GRCh38")
                .setId(".")
                .setChrom("2")
                .setPos(134327882)
                .setRef("A")
                .setAlt("T")
                .build();
        VariationDescriptor variant = VariationDescriptor.newBuilder()
                .setId("clinvar:13294")
                .addAllVcfRecord(Collections.singletonList(vcfRecord))
                .setAllelicState(heterozygous)
                .build();
        String hash = printAndGetHash(variant, "variationDescriptor");
        assertEquals("2d959f56c61bc18d1543b16ef82e53dd62516ee17c542370aecb98dce8e4500a", hash);
    }

    @Test
    public void iscnVariantTest() {
        VariationDescriptor variant = VariationDescriptor.newBuilder()
                .setId("id:A")
                .addAllExpressions(Collections.singletonList(Expression.newBuilder()
                        .setSyntax("iscn")
                        .setValue("t(8;9;11)(q12;p24;p12)")
                        .build()
                ))
                .build();
        String hash = printAndGetHash(variant, "variationDescriptor");
        assertEquals("37115e75b26bc88e6346a2c9220c88c4767fed79e9760e4fe80c3ed575b0fc14", hash);
    }

    @Test
    public void testValue() {
        OntologyClass loinc = ontologyClass("LOINC:26515-7","Platelets [#/volume] in Blood");
        OntologyClass cellsPerMicroliter = ontologyClass("UO:0000316","cells per microliter");
        double lower = 150_000;
        double upper = 450_000;
        ReferenceRange referenceRange = referenceRange(cellsPerMicroliter, lower, upper);
        Value value = quantitativeValue(cellsPerMicroliter, 24_000, referenceRange);
        String hash = printAndGetHash(value, "value");
        assertEquals("66ac76834270d3459e54e2c1e20fa7a7b16fc5f9457a9bcd5a478334054c44e1", hash);
    }

    @Test
    public void testOrdinalValue() {
        OntologyClass present = ontologyClass("NCIT:C25626","Present");
        Value value = Value.newBuilder()
                .setOntologyClass(present)
                .build();
        String hash = printAndGetHash(value, "value");
        assertEquals("5d3e517e1c4ae82b477cc4993be973a38858a14a91cea21fcf23f4aa509d6343", hash);
    }

    @Test
    public void testComplexValue(){
        OntologyClass diastolic = ontologyClass("NCIT:C25299", "Diastolic Blood Pressure");
        OntologyClass systolic = ontologyClass("NCIT:C25298", "Systolic Blood Pressure");
        OntologyClass mmHg = ontologyClass("NCIT:C49670" ,"Millimeter of Mercury");
        Quantity mmHg70 = Quantity.newBuilder()
        .setValue(70)
        .setUnitClass(mmHg)
        .build();
        Quantity mmHg120 = Quantity.newBuilder()
                .setValue(120)
                .setUnitClass(mmHg)
                .build();

        ComplexValue complexValue = ComplexValue.newBuilder()
                .addTypedQuantities(TypedQuantity.newBuilder().setType(systolic).setQuantity(mmHg120).build())
                .addTypedQuantities(TypedQuantity.newBuilder().setType(diastolic).setQuantity(mmHg70).build())
                .build();
        String hash = printAndGetHash(complexValue, "complexValue");
        assertEquals("ebf63191574582f5cd431bcbf1d6683468409f45b9c5dda083005f7f59af27ac", hash);
    }

    @Test
    public void pedigreeTest() {
        Pedigree.Person person1A =
                affectedPerson("family 1", "kindred 1A","FATHER","MOTHER",Sex.MALE);
        Pedigree.Person person1B =
                affectedPerson("family 1", "kindred 1B","FATHER","MOTHER",Sex.MALE);
        Pedigree.Person mother = unaffectedPerson("family 1", "MOTHER", "0","0",Sex.FEMALE);
        Pedigree.Person father = unaffectedPerson("family 1", "FATHER", "0","0",Sex.MALE);

       Pedigree pedigree = Pedigree.newBuilder()
               .addPersons(person1A)
               .addPersons(person1B)
               .addPersons(mother)
               .addPersons(father)
               .build();
        String hash = printAndGetHash(pedigree, "pedigree");
        assertEquals("1c72e4e36a4619febade1adb01cda5a4caf63eecbd39e743dbc442deb05739fa", hash);
    }

    @Test
    public void testUpdate() throws ParseException {
        Timestamp ts = Timestamps.parse("2018-06-10T10:59:06Z");
        Update update = Update.newBuilder()
                .setTimestamp(ts)
                .setUpdatedBy("Julius J.")
                .setComment("added phenotypic features to individual patient:1")
                .build();
        String hash = printAndGetHash(update, "update");
        assertEquals("1147417812354796ba5dcde1a7a8f23abb9b8c58a0d02c42be85c84028db1ab7", hash);
    }
    
}

