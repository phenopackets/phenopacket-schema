package org.phenopackets.schema.v2.doc;

import com.google.protobuf.Message;
import com.google.protobuf.Timestamp;
import com.google.protobuf.util.Timestamps;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.phenopackets.schema.v2.GeneInterpretation;
import org.phenopackets.schema.v2.GenomicInterpretation;
import org.phenopackets.schema.v2.VariantInterpretation;
import org.phenopackets.schema.v2.core.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.phenopackets.schema.v2.PhenoPacketTestUtil.ontologyClass;
import static org.phenopackets.schema.v2.doc.PhenopacketUtil.*;

/**
 * This class is a convenience class for generating YAML snippets for the documentation. For each snippet,
 * we calculate a Hash value and assert equality. If there is any upstream change, the assertion will fail,
 * which will be a warning to update the documentation.
 */
public class YamlGeneration extends TestBase{

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
            System.out.println(yamlString);
            return sha256(yamlString);
        } catch (IOException e) {
            return e.getMessage();
        }
    }


    @Test
    void ontologyClassHpoNeutropenia() {
        String id = "HP:0001875";
        String label = "Neutropenia";
        OntologyClass neutropenia = ontologyClass(id, label);
        String hash = printAndGetHash(neutropenia, "ontologyClass");
        assertEquals("e93ec31eb81c5923a646deba11d32a2550413cbe96a6d92c22b7d257e031b0b4", hash);
    }

    @Test
    void ontologyClassHpoSevere() {
        String id = "HP:0012828";
        String label = "Severe";
        OntologyClass severe = ontologyClass(id, label);
        String hash = printAndGetHash(severe, "ontologyClass");
        assertEquals("5475f5293308af2149caae789e101dbeb7cf2aaaf5fa9a8d242de588617fc795", hash);
    }


    @Test
    void vitalStatusDeceasedYaml() throws ParseException {
        OntologyClass causeOfDeath = ontologyClass("NCIT:C36263","Metastatic Malignant Neoplasm");
        TimeElement timeOfDeath = timeElementFromDateString("2015-10-01T10:54:20.021Z");
        VitalStatus deceased = vitalStatusDeceased(causeOfDeath, timeOfDeath);
        String hash = printAndGetHash(deceased, "vitalStatus");
        assertEquals("eae16f64c90fbea14c2010b575426b25b59078245904b198a32a2fb9b8470258", hash);
    }

    @Test
    void vitalStatusAliveYaml() {
        VitalStatus alive = vitalStatusAlive();
        String hash = printAndGetHash(alive, "vitalStatus");
        assertEquals("f12616d0a7fa92173179263efef56c22b1b12128deb7057a245d738f2e18ed19", hash);
    }


    @Test
    void gestationalAgeYaml() {
        GestationalAge gestationalAge = gestationalAge(33,2);
        String hash = printAndGetHash(gestationalAge, "gestationalAge");
        assertEquals("2163baf411b84c60284e5bfe86a65a035fbacd6e3f9d23478e6ad900786fc49b", hash);
    }

    @Test
    void testHpoResource() {
        Resource hpoResource = hpoResource("2018-03-08");
        String hash = printAndGetHash(hpoResource, "resource");
        assertEquals("43a5457bd16282effa6d0ce656c4730c3525c7651765c6c253ce58a696c7db18", hash);
    }

    @Test
    void testHgncResource() {
        Resource hgncResource = hgncResource("2019-08-08");
        String hash = printAndGetHash(hgncResource, "resource");
        assertEquals("e554b02a815eb1a92884c10440ff18171e05bbbd896e28ee9346e486d32a92b0", hash);
    }

    @Test
    void testUniprotResource() {
        Resource uniprotResource = uniprotResource("2019_07");
        String hash = printAndGetHash(uniprotResource, "resource");
        assertEquals("10a8a1697ee43a21da4b74da68740735522211c01cd9b56008848b3579304c76", hash);
    }

    @Test
    void testExternalReference() {
        String id = "PMID:30962759";
        String description = "Recurrent Erythema Nodosum in a Child with a SHOC2 Gene Mutation";
        ExternalReference externalReference = externalReference(id, description);
        String hash = printAndGetHash(externalReference, "externalReference");
        assertEquals("399143dfe80ddb492759832328fc60606ce8483c406b3915c0643d237c9e2f25", hash);
    }

    @Test
    void testEvidence() {
        String evidenceId = "PMID:30962759";
        String description = "Recurrent Erythema Nodosum in a Child with a SHOC2 Gene Mutation";
        Evidence evidence = evidenceWithEcoAuthorStatement(evidenceId, description);
        String hash = printAndGetHash(evidence, "evidence");
        assertEquals("e0d8e9f371c77a275afcb6f844d00e42b4e7a84aa9154d666255f69bdca1df54", hash);
    }

    @Test
    void testGene() {
        String id = "HGNC:347";
        String symbol = "ETF1";
        Gene gene = gene(id, symbol);
        String hash = printAndGetHash(gene, "gene");
        assertEquals("7b80f285c6a5d4b6479db01c58b9d6820f47853d7d6607f542e2f9d83a332e51", hash);
    }

    @Test
    void testGeneWithAltIds() {
        String id = "HGNC:347";
        String symbol = "ETF1";
        List<String> alternateIds = List.of("ensembl:ENSRNOG00000019450", "ncbigene:307503");
        Gene gene = gene(id, symbol, alternateIds);
        String hash = printAndGetHash(gene, "gene");
        assertEquals("c8f8dd3459738139cb9e5ba033d0bad4ba23dfbfea0d4df9cc4dac53010223d2", hash);
    }

    @Test
    void testAge() {
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
    void testAgeRange() {
        String bottom = "P45Y";
        String top = "P49Y";
        AgeRange ageRange = ageRange(bottom, top);
        String hash = printAndGetHash(ageRange, "ageRange");
        assertEquals("e55c7fbfbc064be8957295f1a3b0ad7219c79f8a3cbec2411d8ea5f776b6daf3", hash);
    }

    @Test
    void testReferenceRange() {
        OntologyClass cellsPerMicroliter = ontologyClass("UO:0000316","cells per microliter");
        double lower = 150_000;
        double upper = 450_000;
        ReferenceRange referenceRange = referenceRange(cellsPerMicroliter, lower, upper);
        String hash = printAndGetHash(referenceRange, "referenceRange");
        assertEquals("d8f1b33456a2fedf52cbaeddef99bb775770a04186ec82924367f4fc7e930380", hash);
    }

    @Test
    void testPlateletMeasurement() throws ParseException {
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
    void testNitrituriaMeasurement() throws ParseException {
        OntologyClass loinc = ontologyClass("LOINC:5802-4","Nitrite [Presence] in Urine by Test strip");
        Value present = presentValue();
        TimeElement time = timeElementFromDateString("2021-01-01T10:54:20.021Z");
        Measurement measurement = measurement(loinc, present, time);
        String hash = printAndGetHash(measurement, "measurement");
        assertEquals("26a135acd05734f17750feac37544c2cfa82686145b8ecfe0a494e5ddf12b812", hash);
    }

    @Test
    void testBloodPressure() throws ParseException {
        TimeElement time = timeElementFromDateString("2021-01-01T10:54:20.021Z");
        Measurement bloodPressure = bloodPressure(125,75, time);
        String hash = printAndGetHash(bloodPressure, "measurement");
        assertEquals("2e6feccf9cb68bf69d68687c7ceb7e88f6760895b84522e08b8a7d5fb6144d24", hash);
    }


    @Test
    void diastolicBP() {
        OntologyClass diastolicBP = ontologyClass("NCIT:C25299", "Diastolic Blood Pressure");
        OntologyClass mmHg = ontologyClass("NCIT:C49670", "Millimeter of Mercury");
        double diastolic = 70;
        Quantity diastolicQuantity = quantity(diastolic, mmHg);
        TypedQuantity typedQuantity = typedQuantity(diastolicBP, diastolicQuantity);
        String hash = printAndGetHash(typedQuantity, "typedQuantity");
        assertEquals("a1e200d9520c190e936113c168b9cac6747fdab46eb2df937c72aa315bfb14c7", hash);
    }


    @Test
    void htsFileTest() {
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
    void individualTest() throws ParseException {
        String id = "patient:0";
        Timestamp dob = Timestamps.parse("1998-01-01T00:00:00Z");
        Sex male = Sex.MALE;
        Individual individual = individual(id, dob, male);
        String hash = printAndGetHash(individual, "individual");
        assertEquals("638a7d0480c97f8690c1b76525ddb2725270f56e7aec6374fe52b5a30fd73450", hash);
    }

    @Test
    void timeIntervalTest() throws ParseException {
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
    void doseIntervalTest() throws ParseException {
        DoseInterval doseInterval = doseIntervalExample();
        String hash = printAndGetHash(doseInterval, "doseInterval");
        assertEquals("21cfbe01d59fcae44b0012571c6f06fd62ae0d0465bb22081473f82523ac4ae1", hash);
    }

    @Test
    void treatmentTest() throws ParseException  {
        OntologyClass agent = ontologyClass("DrugCentral:1610", "losartan");  // for instance, DrugCentral, RxNorm Drugbank concept
        OntologyClass route_of_administration = ontologyClass("NCIT:C38288","Oral Route of Administration"); // For instance, NCIT subhierarchy: Route of Administration (Code C38114)
        List<DoseInterval> doseIntervalList = List.of(doseIntervalExample());
        DrugType drug_type = DrugType.PRESCRIPTION;
        Treatment treatment = treatment(agent, route_of_administration, doseIntervalList,drug_type);
        String hash = printAndGetHash(treatment, "treatment");
        assertEquals("99126a60a4a9a80024e93d99cd4515a245443d774f431af5dd500928ab7e8656", hash);
    }


    @Test
    void urothelialCarcinomaBiosample() {
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
        assertEquals("c2cc88bd31ceb0427665cdfb5dbf05de1b847ebc0ad2f314fec60745cdcde3b8", hash);
    }

    @Test
    void testVariant() {
        Variant variant = heterozygousHgvsVariant("NM_001848.2:c.877G>A");
        String hash = printAndGetHash(variant, "variant");
        assertEquals("a60dcb71cf83b9072696716c7514c57cc6e33ca933e6bb82172fa38d3c07bf22", hash);
    }

    @Test
    void testVariantInterpretation() {
        Variant variant = heterozygousHgvsVariant("NM_001848.2:c.877G>A");
        VariantInterpretation variantInterpretation = pathogenicVariantInterpretation(variant);
        String hash = printAndGetHash(variantInterpretation, "variantInterpretation");
        assertEquals("2939d8a67b2a3ebafe3d9b2a2fad0c09f00053317a9cb19daa1ebad88ed5e8e7", hash);
    }


    @Test
    void testGeneInterpretation() {
        String id = "HGNC:347";
        String symbol = "ETF1";
        Gene gene = gene(id, symbol);
        GeneInterpretation geneInterpretation = candidateGeneInterpretation(gene);
        String hash = printAndGetHash(geneInterpretation, "geneInterpretation");
        assertEquals("a60dcb71cf83b9072696716c7514c57cc6e33ca933e6bb82172fa38d3c07bf22", hash);
    }

    @Test
    void testGenomicInterpretation() {
       GenomicInterpretation interpretation = pathogenicGenomicInterpretationOfVariant("subject 1","NM_001848.2:c.877G>A");
        String hash = printAndGetHash(interpretation, "genomicInterpretation");
        assertEquals("a60dcb71cf83b9072696716c7514c57cc6e33ca933e6bb82172fa38d3c07bf22", hash);
    }



}

