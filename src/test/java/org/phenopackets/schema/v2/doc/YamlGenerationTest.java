package org.phenopackets.schema.v2.doc;

import com.google.protobuf.Message;
import com.google.protobuf.Timestamp;
import com.google.protobuf.util.Timestamps;
import org.ga4gh.vrs.v1.Number;
import org.ga4gh.vrs.v1.*;
import org.ga4gh.vrsatile.v1.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.phenopackets.schema.v2.Family;
import org.phenopackets.schema.v2.Phenopacket;
import org.phenopackets.schema.v2.core.*;
import org.phenopackets.schema.v2.examples.TestExamples;

import java.io.IOException;
import java.text.ParseException;
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
    public void testOntologyClassHpoNeutropenia() {
        String id = "HP:0001875";
        String label = "Neutropenia";
        OntologyClass neutropenia = ontologyClass(id, label);
        String hash = printAndGetHash(neutropenia, "ontologyClass");
        assertEquals("e93ec31eb81c5923a646deba11d32a2550413cbe96a6d92c22b7d257e031b0b4", hash);
    }

    @Test
    public void testOntologyClassHpoSevere() {
        String id = "HP:0012828";
        String label = "Severe";
        OntologyClass severe = ontologyClass(id, label);
        String hash = printAndGetHash(severe, "ontologyClass");
        assertEquals("5475f5293308af2149caae789e101dbeb7cf2aaaf5fa9a8d242de588617fc795", hash);
    }


    @Test
    public void testVitalStatusDeceasedYaml() throws ParseException {
        OntologyClass causeOfDeath = ontologyClass("NCIT:C36263","Metastatic Malignant Neoplasm");
        TimeElement timeOfDeath = timeElementFromDateString("2015-10-01T10:54:20.021Z");
        VitalStatus deceased = vitalStatusDeceased(causeOfDeath, timeOfDeath);
        String hash = printAndGetHash(deceased, "vitalStatus");
        assertEquals("eae16f64c90fbea14c2010b575426b25b59078245904b198a32a2fb9b8470258", hash);
    }

    @Test
    public void testVitalStatusAliveYaml() {
        VitalStatus alive = vitalStatusAlive();
        String hash = printAndGetHash(alive, "vitalStatus");
        assertEquals("f12616d0a7fa92173179263efef56c22b1b12128deb7057a245d738f2e18ed19", hash);
    }


    @Test
    public void testGestationalAgeYaml() {
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
        String id = "HGNC:3477";
        String symbol = "ETF1";
        GeneDescriptor gene = geneDescriptor(id, symbol);
        String hash = printAndGetHash(gene, "geneDescriptor");
        assertEquals("470bc709c0c2b94e4dacf7283c78e05c66e0dc2b80d3eef7bd5706bcce7a3124", hash);
    }

    @Test
    public void testGeneWithAltIds() {
        String id = "HGNC:3477";
        String symbol = "ETF1";
        List<String> alternateIds = List.of("ensembl:ENSG00000120705", "ncbigene:2107", "ucsc:uc003ldc.6", "OMIM:600285");
        GeneDescriptor gene = geneDescriptor(id, symbol, alternateIds);
        String hash = printAndGetHash(gene, "geneDescriptor");
        assertEquals("0cc93a9df29cd1a3c199a5df80dbe53513559516b05e6a308236f1a0a87cd730", hash);
    }

    @Test
    public void testGeneWithAltIdsXrefs() {
        String id = "HGNC:3477";
        String symbol = "ETF1";
        GeneDescriptor gene = GeneDescriptor.newBuilder()
                .setValueId(id)
                .setSymbol(symbol)
                .addAllAlternateIds(List.of("ensembl:ENSG00000120705", "ncbigene:2107", "ucsc:uc003ldc.6", "OMIM:600285"))
                .addAllAlternateSymbols(List.of("SUP45L1", "ERF1", "ERF", "eRF1", "TB3-1", "RF1"))
                .addAllXrefs(List.of("VGNC:97422", "MGI:2385071", "RGD:1305712", "ensembl:ENSRNOG00000019450", "ncbigene:307503"))
                .build();
        String hash = printAndGetHash(gene, "geneDescriptor");
        assertEquals("527fa8315ca5e9c37a26942b9d45d50bdfe43d73e30ee187243ed00350c83990", hash);
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
        assertEquals("778bab574f0d800b496bc15c81203340a8b67b78c2673639858d556d07a23bc9", hash);
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
        assertEquals("9e4f8826a32f65d0a2f908e9bb250ca679027cbc1ae26b55fb4ab107cc45b720", hash);
    }


    @Test
    public void testDiastolicBP() {
        OntologyClass diastolicBP = ontologyClass("NCIT:C25299", "Diastolic Blood Pressure");
        OntologyClass mmHg = ontologyClass("NCIT:C49670", "Millimeter of Mercury");
        double diastolic = 70;
        Quantity diastolicQuantity = quantity(diastolic, mmHg);
        TypedQuantity typedQuantity = typedQuantity(diastolicBP, diastolicQuantity);
        String hash = printAndGetHash(typedQuantity, "typedQuantity");
        assertEquals("4b33a72b945732b6a8b7b274f8491b371774fc11013b26d9fdab6667b4ca9c4b", hash);
    }


    @Test
    public void htsFileTest() {
        String uri = "file://data/genomes/germline_wgs.vcf.gz";
        String description = "Matched normal germline sample";
        String genomeAssembly = "GRCh38";
        Map<String,String> individualToSampleIdentifiers = Map.of("patient23456", "NA12345");
        File vcfFile = vcfFile(uri, description, genomeAssembly, individualToSampleIdentifiers);
        String hash = printAndGetHash(vcfFile, "file");
        assertEquals("c37c8df6f630c47e2e96f931a8662799dd61c14da975720970810db359593c1e", hash);
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
        assertEquals("c7b7e0426e45c78dce3bcbc1af2f76ad23ecd389ff9a776acc925820803685cf", hash);
    }

    @Test
    public void treatmentTest() throws ParseException  {
        OntologyClass agent = ontologyClass("DrugCentral:1610", "losartan");  // for instance, DrugCentral, RxNorm Drugbank concept
        OntologyClass route_of_administration = ontologyClass("NCIT:C38288","Oral Route of Administration"); // For instance, NCIT subhierarchy: Route of Administration (Code C38114)
        List<DoseInterval> doseIntervalList = List.of(doseIntervalExample());
        DrugType drug_type = DrugType.PRESCRIPTION;
        Treatment treatment = treatment(agent, route_of_administration, doseIntervalList,drug_type);
        String hash = printAndGetHash(treatment, "treatment");
        assertEquals("fc8e1afecd319222f1abdc756e9ed74220decc65104e9902e31374f96d3b7285", hash);
    }

    @Test
    public void therapeuticRegimenTest() throws ParseException {
        ExternalReference externalReference = ExternalReference.newBuilder()
                .setId("NCT04576091")
                .setReference("https://clinicaltrials.gov/ct2/show/NCT04576091")
                .setDescription("Testing the Addition of an Anti-cancer Drug, BAY1895344, With Radiation Therapy to the Usual Pembrolizumab Treatment for Recurrent Head and Neck Cancer")
                .build();

        TherapeuticRegimen therapeuticRegimen = TherapeuticRegimen.newBuilder().setExternalReference(externalReference)
                .setStartTime(TimeElement.newBuilder().setTimestamp(Timestamps.parse("2020-03-15T13:00:00Z")))
                .setRegimenStatus(TherapeuticRegimen.RegimenStatus.STARTED)
                .build();
        String hash = printAndGetHash(therapeuticRegimen, "therapeuticRegimen");
        assertEquals("91a2d0520bf63231d9d8fb3efe28f31dff8b73dd76b905489b33e41699abd6c1", hash);
    }

    @Test
    public void testUrothelialCarcinomaBiosample() {
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
        File vcfFile = vcfFile(uri, htsDescription, genomeAssembly, individualToSampleIdentifiers);
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
        assertEquals("963cd06917f43146cf01455c5a7165dbf9401fb4c52c57456ed86c9fb411dc15", hash);
    }

    @Test
    public void testVariant() {
        VariationDescriptor variant = heterozygousHgvsVariant("NM_001848.2:c.877G>A");
        String hash = printAndGetHash(variant, "variationDescriptor");
        assertEquals("c13da8dbf04ba4901222caeb383b55d3563bee1423b50010cb6c0d6aa4103dce", hash);
    }

    @Test
    public void testVariantInterpretation() {
        VariationDescriptor variant = heterozygousHgvsVariant("NM_001848.2:c.877G>A");
        VariantInterpretation variantInterpretation = pathogenicVariantInterpretation(variant);
        String hash = printAndGetHash(variantInterpretation, "variantInterpretation");
        assertEquals("022bb0a7917e593a3c99ee2faf1f3929846586dbc44e54475089ff652c320a9b", hash);
    }



    @Test
    public void testGenomicInterpretation() {
       GenomicInterpretation interpretation = pathogenicGenomicInterpretationOfVariant("subject 1","NM_001848.2:c.877G>A");
        String hash = printAndGetHash(interpretation, "genomicInterpretation");
        assertEquals("d5a0e967b72787cb7e00d9dc45fe4e44b4378eb0930bbde296fa19ca7074612b", hash);
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
        assertEquals("444dce76db441182e065a4e2c09ab59bdaf7421ca56b16ca3021d637098c0b67", hash);
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
                .setId("CONSORTIUM:0000123456")
                .setProgressStatus(Interpretation.ProgressStatus.SOLVED)
                .setDiagnosis(diagnosis)
                .build();
        String hash = printAndGetHash(interpretation, "interpretation");
        assertEquals("9c26eb6490ff530da11b2d7b984ea81ad51d2b7622f11a4606da950ed8cd32f2", hash);
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
        assertEquals("fe0c29d992624b1e0bc2fac5e1e93a0a2867d571fa7c402f3ac07f21344f528b", hash);
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
        assertEquals("f3aff8216d727064107c8c7ec5111504083283d11181741cd10d13766cab0d07", hash);
    }

    @Test
    public void testInterpretationBraf() {
        VariationDescriptor brafVar = heterozygousHgvsVariant("NM_001374258.1(BRAF):c.1919T>A (p.Val640Glu)");
        VariantInterpretation v1 = VariantInterpretation.newBuilder()
                .setAcmgPathogenicityClassification(AcmgPathogenicityClassification.PATHOGENIC)
                .setTherapeuticActionability(TherapeuticActionability.ACTIONABLE)
                .setVariationDescriptor(brafVar).build();
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
        assertEquals("bf735e3ae546260eb35e1baf2867fd9d71e58275358dc6a4dcf1f6d12ada40dd", hash);
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
        Quantity cumulativeQuantity = quantity(200, mgPerKg);
        Treatment chemo = Treatment.newBuilder()
                .setAgent(aclarubicin)
                .setDrugType(DrugType.EHR_MEDICATION_LIST)
                .addDoseIntervals(di)
                .setRouteOfAdministration(intravenous)
                .setCumulativeDose(cumulativeQuantity)
                .build();

        String hash = printAndGetHash(chemo, "treatment");
        assertEquals("9ec2bdf89056d27dbd0b0ec74d26c5d72d8d472cee0de5810968c7baf7411526", hash);
    }

    @Test
    public void tamoxifenHormoneTherapyTest() throws ParseException {
        OntologyClass tamoxifen = ontologyClass("DrugCentral:2561","tamoxifen");
        OntologyClass oral = ontologyClass("NCIT:C38288","Oral Route of Administration");
        OntologyClass milligram = ontologyClass("NCIT:C28253","Milligram");
        Timestamp t1 = Timestamps.parse("2020-03-15T13:00:00Z");
        Timestamp t2 = Timestamps.parse("2020-03-25T09:00:00Z");
        Quantity quantity = quantity(20, milligram);
        OntologyClass onceDaily = ontologyClass("NCIT:C125004", "Once Daily");
        DoseInterval di = DoseInterval.newBuilder()
                .setInterval(TimeInterval.newBuilder().setStart(t1).setEnd(t2))
                .setQuantity(quantity)
                .setScheduleFrequency(onceDaily)
                .build();
        Quantity cumulativeQuantity = quantity(36500, milligram);
        Treatment hormone = Treatment.newBuilder()
                .setAgent(tamoxifen)
                .setDrugType(DrugType.PRESCRIPTION)
                .addDoseIntervals(di)
                .setRouteOfAdministration(oral)
                .setCumulativeDose(cumulativeQuantity)
                .build();
        String hash = printAndGetHash(hormone, "treatment");
        assertEquals("559c77ef71a616d4f0a5b8b8e8466f6139191a94b98ac04637d88330d5b8fb36", hash);
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
                .addAllExpressions(List.of(Expression.newBuilder()
                        .setSyntax("spdi")
                        .setValue("NC_000010.11:121496700:T:G")
                        .build()
                ))
                .setAllelicState(heterozygous)
                .build();
        String hash = printAndGetHash(variant, "variationDescriptor");
        assertEquals("5378f067be48fc0c7848e86b3fe220746675a4a3612346e83aad0724a0ca17c1", hash);
    }

    @Test
    public void hgvsVariantTest() {
        OntologyClass heterozygous = ontologyClass("GENO:0000135", "heterozygous");
        VariationDescriptor variant = VariationDescriptor.newBuilder()
                .setId("clinvar:13294")
                .addExpressions(Expression.newBuilder()
                        .setSyntax("hgvs")
                        .setValue("NM_000226.3:c.470T>G"))
                .setAllelicState(heterozygous)
                .build();
        String hash = printAndGetHash(variant, "variationDescriptor");
        assertEquals("04f2791c3744028efa2957108c88c66d630d39102fb83c23ccdeda5f15465ce6", hash);
    }

    @Test
    public void vcfAlleleTest() {
        OntologyClass heterozygous = ontologyClass("GENO:0000135", "heterozygous");
        VcfRecord vcfRecord = VcfRecord.newBuilder()
                .setGenomeAssembly("GRCh38")
                .setId("rs121918506")
                .setChrom("10")
                .setPos(121496701)
                .setRef("T")
                .setAlt("G")
                .build();
        VariationDescriptor variant = VariationDescriptor.newBuilder()
                .setId("clinvar:13294")
                .setVcfRecord(vcfRecord)
                .setAllelicState(heterozygous)
                .build();
        String hash = printAndGetHash(variant, "variationDescriptor");
        assertEquals("262ff8123368594c2fdc36eec5857287bde62c1d9ffd6ff4e24fce67eb05bc26", hash);
    }

    @Test
    public void vrsAlleleTest() {
        OntologyClass heterozygous = ontologyClass("GENO:0000135", "heterozygous");
        Allele allele = Allele.newBuilder()
                .setSequenceLocation(SequenceLocation.newBuilder()
                        .setSequenceId("NC_000010.11")
                        .setSequenceInterval(SequenceInterval.newBuilder()
                                .setStartNumber(Number.newBuilder().setValue(121496700))
                                .setEndNumber(Number.newBuilder().setValue(121496701))
                        ))
                .setLiteralSequenceExpression(LiteralSequenceExpression.newBuilder().setSequence("G"))
                .build();
        Variation variation = Variation.newBuilder()
                .setAllele(allele)
                .build();
        VariationDescriptor variant = VariationDescriptor.newBuilder()
                .setId("clinvar:13294")
                .setVariation(variation)
                .setVrsRefAlleleSeq("T")
                .setMoleculeContext(MoleculeContext.genomic)
                .setAllelicState(heterozygous)
                .build();
        String hash = printAndGetHash(variant, "variationDescriptor");
        assertEquals("18deba344e498ec22380d98a063822994c7e6f6e9b16840ce79f8f9a4b3a054f", hash);
    }

    @Test
    public void variationDescriptorTest() {
        OntologyClass heterozygous = ontologyClass("GENO:0000135", "heterozygous");
        VcfRecord vcfRecord = VcfRecord.newBuilder()
                .setGenomeAssembly("GRCh38")
                .setId("rs121918506")
                .setChrom("10")
                .setPos(121496701)
                .setRef("T")
                .setAlt("G")
                .build();
        VariationDescriptor variant = VariationDescriptor.newBuilder()
                .setId("clinvar:13294")
                .setGeneContext(geneDescriptor("HGNC:3689", "FGFR2"))
                .setVcfRecord(vcfRecord)
                .addExpressions(Expression.newBuilder()
                        .setSyntax("hgvs")
                        .setValue("NM_000141.5:c.1694A>C"))
                .addExpressions(Expression.newBuilder()
                        .setSyntax("hgvs")
                        .setValue("NP_000132.3:p.Glu565Ala"))
                .setAllelicState(heterozygous)
                .build();
        String hash = printAndGetHash(variant, "variationDescriptor");
        assertEquals("052e319393b9a0a00ff889826984e21ed4f60e389eafc2cb4b16e48542d11313", hash);
    }

    @Test
    public void iscnVariantTest() {
        VariationDescriptor variant = VariationDescriptor.newBuilder()
                .setId("id:A")
                .addAllExpressions(List.of(Expression.newBuilder()
                        .setSyntax("iscn")
                        .setValue("t(8;9;11)(q12;p24;p12)")
                        .build()
                ))
                .build();
        String hash = printAndGetHash(variant, "variationDescriptor");
        assertEquals("5590011c3938ac7cdff15f7859b41bbcfc9b23cc17bebfbcdb726e77c6ff04b1", hash);
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
        assertEquals("0d0e5332689a1b7221b9d307510c81995c395fae3a85ee78e9b9bde35903270e", hash);
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
        Quantity mmHg70 = quantity(70, mmHg);
        Quantity mmHg120 = quantity(120, mmHg);

        ComplexValue complexValue = ComplexValue.newBuilder()
                .addTypedQuantities(TypedQuantity.newBuilder().setType(systolic).setQuantity(mmHg120))
                .addTypedQuantities(TypedQuantity.newBuilder().setType(diastolic).setQuantity(mmHg70))
                .build();
        String hash = printAndGetHash(complexValue, "complexValue");
        assertEquals("06b89f577323d1a9215f422cbae4939b009c9fb961d248fe92e148abe957ad2a", hash);
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

    @Nested
    public class ExamplesTests {

        @Test
        public void rareDiseaseExampleTest() {
            Family bethlemMyopathyFamily = TestExamples.rareDiseaseBethlemMyopathyFamily();
            String hash = printAndGetHash(bethlemMyopathyFamily, "family");
            assertEquals("6371d98b47969ef8026da0e6d17251f8eec036ad95a73f621a02e7555f497644", hash);
        }

        @Test
        public void cancerExampleTest() {
            Phenopacket phenopacket = TestExamples.cancerPhenopacket();
            String hash = printAndGetHash(phenopacket, "phenopacket");
            assertEquals("1fa300fe573c37069344e6089d7e097980da0629837b197e927f7492151df07e", hash);
        }

        @Test
        public void covidExampleTest() {
            Phenopacket phenopacket = TestExamples.severeCovidCaseWithCardiacComplications();
            String hash = printAndGetHash(phenopacket, "phenopacket");
            assertEquals("7a1308bb2e17a9a405f5e6582303385e88ec4ead897f400574cc6ee6a795902e", hash);
        }
    }
}