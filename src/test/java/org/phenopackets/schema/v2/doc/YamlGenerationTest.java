package org.phenopackets.schema.v2.doc;

import com.google.protobuf.Message;
import com.google.protobuf.Timestamp;
import com.google.protobuf.util.Timestamps;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.phenopackets.schema.v2.core.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;



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
        OntologyClass neutropenia = PhenopacketUtil.ontologyClass(id, label);
        String hash = printAndGetHash(neutropenia, "ontologyClass");
        assertEquals("e93ec31eb81c5923a646deba11d32a2550413cbe96a6d92c22b7d257e031b0b4", hash);
    }

    @Test
    public void ontologyClassHpoSevere() {
        String id = "HP:0012828";
        String label = "Severe";
        OntologyClass severe = PhenopacketUtil.ontologyClass(id, label);
        String hash = printAndGetHash(severe, "ontologyClass");
        assertEquals("5475f5293308af2149caae789e101dbeb7cf2aaaf5fa9a8d242de588617fc795", hash);
    }


    @Test
    public void vitalStatusDeceasedYaml() throws ParseException {
        OntologyClass causeOfDeath = PhenopacketUtil.ontologyClass("NCIT:C36263","Metastatic Malignant Neoplasm");
        TimeElement timeOfDeath = PhenopacketUtil.timeElementFromDateString("2015-10-01T10:54:20.021Z");
        VitalStatus deceased = PhenopacketUtil.vitalStatusDeceased(causeOfDeath, timeOfDeath);
        String hash = printAndGetHash(deceased, "vitalStatus");
        assertEquals("eae16f64c90fbea14c2010b575426b25b59078245904b198a32a2fb9b8470258", hash);
    }

    @Test
    public void vitalStatusAliveYaml() {
        VitalStatus alive = PhenopacketUtil.vitalStatusAlive();
        String hash = printAndGetHash(alive, "vitalStatus");
        assertEquals("f12616d0a7fa92173179263efef56c22b1b12128deb7057a245d738f2e18ed19", hash);
    }


    @Test
    public void gestationalAgeYaml() {
        GestationalAge gestationalAge = PhenopacketUtil.gestationalAge(33,2);
        String hash = printAndGetHash(gestationalAge, "gestationalAge");
        assertEquals("2163baf411b84c60284e5bfe86a65a035fbacd6e3f9d23478e6ad900786fc49b", hash);
    }

    @Test
    public void testHpoResource() {
        Resource hpoResource = PhenopacketUtil.hpoResource("2018-03-08");
        String hash = printAndGetHash(hpoResource, "resource");
        assertEquals("43a5457bd16282effa6d0ce656c4730c3525c7651765c6c253ce58a696c7db18", hash);
    }

    @Test
    public void testHgncResource() {
        Resource hgncResource = PhenopacketUtil.hgncResource("2019-08-08");
        String hash = printAndGetHash(hgncResource, "resource");
        assertEquals("e554b02a815eb1a92884c10440ff18171e05bbbd896e28ee9346e486d32a92b0", hash);
    }

    @Test
    public void testUniprotResource() {
        Resource uniprotResource = PhenopacketUtil.uniprotResource("2019_07");
        String hash = printAndGetHash(uniprotResource, "resource");
        assertEquals("10a8a1697ee43a21da4b74da68740735522211c01cd9b56008848b3579304c76", hash);
    }

    @Test
    public void testExternalReference() {
        String id = "PMID:30962759";
        String description = "Recurrent Erythema Nodosum in a Child with a SHOC2 Gene Mutation";
        ExternalReference externalReference = PhenopacketUtil.externalReference(id, description);
        String hash = printAndGetHash(externalReference, "externalReference");
        assertEquals("399143dfe80ddb492759832328fc60606ce8483c406b3915c0643d237c9e2f25", hash);
    }

    @Test
    public void testEvidence() {
        String evidenceId = "PMID:30962759";
        String description = "Recurrent Erythema Nodosum in a Child with a SHOC2 Gene Mutation";
        Evidence evidence = PhenopacketUtil.evidenceWithEcoAuthorStatement(evidenceId, description);
        String hash = printAndGetHash(evidence, "evidence");
        assertEquals("e0d8e9f371c77a275afcb6f844d00e42b4e7a84aa9154d666255f69bdca1df54", hash);
    }

    @Test
    public void testGene() {
        String id = "HGNC:347";
        String symbol = "ETF1";
        Gene gene = PhenopacketUtil.gene(id, symbol);
        String hash = printAndGetHash(gene, "gene");
        assertEquals("7b80f285c6a5d4b6479db01c58b9d6820f47853d7d6607f542e2f9d83a332e51", hash);
    }

    @Test
    public void testGeneWithAltIds() {
        String id = "HGNC:347";
        String symbol = "ETF1";
        List<String> alternateIds = List.of("ensembl:ENSRNOG00000019450", "ncbigene:307503");
        Gene gene = PhenopacketUtil.gene(id, symbol, alternateIds);
        String hash = printAndGetHash(gene, "gene");
        assertEquals("c8f8dd3459738139cb9e5ba033d0bad4ba23dfbfea0d4df9cc4dac53010223d2", hash);
    }

    @Test
    public void testAge() {
        String validAge = "P25Y3M2D";
        Age age = PhenopacketUtil.age(validAge);
        String hash = printAndGetHash(age, "age");
        assertEquals("3515fa76f5944a3b7b630a10ca76e110f4059ae6f90d82b8d78d18fcea96527b", hash);
        Assertions.assertThrows(RuntimeException.class, () ->{
            String invalidAge = "25Y3M2D";
            Age age2 = PhenopacketUtil.age(invalidAge);
        });
    }

    @Test
    public void testAgeRange() {
        String bottom = "P45Y";
        String top = "P49Y";
        AgeRange ageRange = PhenopacketUtil.ageRange(bottom, top);
        String hash = printAndGetHash(ageRange, "ageRange");
        assertEquals("e55c7fbfbc064be8957295f1a3b0ad7219c79f8a3cbec2411d8ea5f776b6daf3", hash);
    }

    @Test
    public void testReferenceRange() {
        OntologyClass cellsPerMicroliter = PhenopacketUtil.ontologyClass("UO:0000316","cells per microliter");
        double lower = 150_000;
        double upper = 450_000;
        ReferenceRange referenceRange = PhenopacketUtil.referenceRange(cellsPerMicroliter, lower, upper);
        String hash = printAndGetHash(referenceRange, "referenceRange");
        assertEquals("d8f1b33456a2fedf52cbaeddef99bb775770a04186ec82924367f4fc7e930380", hash);
    }

    @Test
    public void testPlateletMeasurement() throws ParseException {
        OntologyClass loinc = PhenopacketUtil.ontologyClass("LOINC:26515-7","Platelets [#/volume] in Blood");
        OntologyClass cellsPerMicroliter = PhenopacketUtil.ontologyClass("UO:0000316","cells per microliter");
        double lower = 150_000;
        double upper = 450_000;
        ReferenceRange referenceRange = PhenopacketUtil.referenceRange(cellsPerMicroliter, lower, upper);
        Value value = PhenopacketUtil.quantitativeValue(cellsPerMicroliter, 24_000, referenceRange);
        TimeElement time = PhenopacketUtil.timeElementFromDateString("2020-10-01T10:54:20.021Z");
        Measurement measurement = PhenopacketUtil.measurement(loinc, value, time);
        String hash = printAndGetHash(measurement, "measurement");
        assertEquals("0dc085d5e2a532951b4f4b11401d841de39df27e36f1b7ac4cb60db9643bad62", hash);
    }

    @Test
    public void testNitrituriaMeasurement() throws ParseException {
        OntologyClass loinc = PhenopacketUtil.ontologyClass("LOINC:5802-4","Nitrite [Presence] in Urine by Test strip");
        Value present = PhenopacketUtil.presentValue();
        TimeElement time = PhenopacketUtil.timeElementFromDateString("2021-01-01T10:54:20.021Z");
        Measurement measurement = PhenopacketUtil.measurement(loinc, present, time);
        String hash = printAndGetHash(measurement, "measurement");
        assertEquals("26a135acd05734f17750feac37544c2cfa82686145b8ecfe0a494e5ddf12b812", hash);
    }

    @Test
    public void testBloodPressure() throws ParseException {
        TimeElement time = PhenopacketUtil.timeElementFromDateString("2021-01-01T10:54:20.021Z");
        Measurement bloodPressure = PhenopacketUtil.bloodPressure(125,75, time);
        String hash = printAndGetHash(bloodPressure, "measurement");
        assertEquals("2e6feccf9cb68bf69d68687c7ceb7e88f6760895b84522e08b8a7d5fb6144d24", hash);
    }


    @Test
    public void diastolicBP() {
        OntologyClass diastolicBP = PhenopacketUtil.ontologyClass("NCIT:C25299", "Diastolic Blood Pressure");
        OntologyClass mmHg = PhenopacketUtil.ontologyClass("NCIT:C49670", "Millimeter of Mercury");
        double diastolic = 70;
        Quantity diastolicQuantity = PhenopacketUtil.quantity(diastolic, mmHg);
        TypedQuantity typedQuantity = PhenopacketUtil.typedQuantity(diastolicBP, diastolicQuantity);
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
        HtsFile vcfFile = PhenopacketUtil.vcfFile(uri,description,genomeAssembly,individualToSampleIdentifiers);
        String hash = printAndGetHash(vcfFile, "htsFile");
        assertEquals("ec4ab02f2dc2b0fc46ec62ec5b401ebf97d34cf0178e021d70c71e14cd84e2cd", hash);
    }

    @Test
    public void individualTest() throws ParseException {
        String id = "patient:0";
        Timestamp dob = Timestamps.parse("1998-01-01T00:00:00Z");
        Sex male = Sex.MALE;
        Individual individual = PhenopacketUtil.individual(id, dob, male);
        String hash = printAndGetHash(individual, "individual");
        assertEquals("638a7d0480c97f8690c1b76525ddb2725270f56e7aec6374fe52b5a30fd73450", hash);
    }

    @Test
    public void timeIntervalTest() throws ParseException {
        String start = "2020-03-15T13:00:00Z";
        String end = "2020-03-25T09:00:00Z";
        TimeInterval timeInterval = PhenopacketUtil.timeInterval(start, end);
        String hash = printAndGetHash(timeInterval, "timeInterval");
        assertEquals("261af4635f6bda84278b76f19382bfca9d23556dbcb62d1feb24db76d12c578b", hash);
    }

    private DoseInterval doseIntervalExample()  throws ParseException {
        String start = "2020-03-15T13:00:00Z";
        String end = "2020-03-25T09:00:00Z";
        TimeInterval timeInterval = PhenopacketUtil.timeInterval(start, end);
        OntologyClass unit = PhenopacketUtil.ontologyClass("UO:0000022", "milligram");
        Quantity quantity = PhenopacketUtil.quantity(30, unit);
        OntologyClass twiceDaily = PhenopacketUtil.ontologyClass("NCIT:C64496", "Twice Daily");
        return PhenopacketUtil.doseInterval(timeInterval, quantity, twiceDaily);
    }

    @Test
    public void doseIntervalTest() throws ParseException {
        DoseInterval doseInterval = doseIntervalExample();
        String hash = printAndGetHash(doseInterval, "doseInterval");
        assertEquals("21cfbe01d59fcae44b0012571c6f06fd62ae0d0465bb22081473f82523ac4ae1", hash);
    }

    @Test
    public void treatmentTest() throws ParseException  {
        OntologyClass agent = PhenopacketUtil.ontologyClass("DrugCentral:1610", "losartan");  // for instance, DrugCentral, RxNorm Drugbank concept
        OntologyClass route_of_administration = PhenopacketUtil.ontologyClass("NCIT:C38288","Oral Route of Administration"); // For instance, NCIT subhierarchy: Route of Administration (Code C38114)
        List<DoseInterval> doseIntervalList = List.of(doseIntervalExample());
        DrugType drug_type = DrugType.PRESCRIPTION;
        Treatment treatment = PhenopacketUtil.treatment(agent, route_of_administration, doseIntervalList,drug_type);
        String hash = printAndGetHash(treatment, "treatment");
        assertEquals("99126a60a4a9a80024e93d99cd4515a245443d774f431af5dd500928ab7e8656", hash);
    }


    @Test
    public void urothelialCarcinomaBiosample() {
        String id = "sample1";
        String individualId = "patient1";
        String description = "Additional information can go here";
        OntologyClass sampledTissue = PhenopacketUtil.ontologyClass("UBERON_0001256","wall of urinary bladder");
        Age age = PhenopacketUtil.age("P52Y2M");
        TimeElement timeElement = TimeElement.newBuilder().setAge(age).build();
        OntologyClass histologicalDiagnosis = PhenopacketUtil.ontologyClass("NCIT:C39853", "Infiltrating Urothelial Carcinoma");
        OntologyClass tumorProgression = PhenopacketUtil.ontologyClass("NCIT:C84509", "Primary Malignant Neoplasm");
        Procedure procedure = Procedure.newBuilder().setCode(PhenopacketUtil.ontologyClass("NCIT:C5189", "Radical Cystoprostatectomy")).build();
        String uri = "file:///data/genomes/urothelial_ca_wgs.vcf.gz";
        String htsDescription = "Urothelial carcinoma sample";
        String genomeAssembly = "GRCh38";
        Map<String,String> individualToSampleIdentifiers = Map.of("patient1", "NA12345");
        HtsFile vcfFile = PhenopacketUtil.vcfFile(uri,htsDescription,genomeAssembly,individualToSampleIdentifiers);
        OntologyClass stageII = PhenopacketUtil.ontologyClass("NCIT:C28054", "Stage II");
        OntologyClass stageT2b = PhenopacketUtil.ontologyClass("NCIT:C48726", "T2b Stage Finding");
        OntologyClass stageN0 = PhenopacketUtil.ontologyClass("NCIT:C48705", "N0 Stage Finding");
        OntologyClass stageM0 = PhenopacketUtil.ontologyClass("NCIT:C48699", "M0 Stage Finding");
        List<OntologyClass> tnm = List.of(stageT2b, stageN0, stageM0);
        OntologyClass grade2 = PhenopacketUtil.ontologyClass("NCIT:C36136", "Grade 2 Lesion");

        Biosample biosample = PhenopacketUtil.biosample(id,
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
        Variant variant = PhenopacketUtil.heterozygousHgvsVariant("NM_001848.2:c.877G>A");
        String hash = printAndGetHash(variant, "variant");
        assertEquals("a60dcb71cf83b9072696716c7514c57cc6e33ca933e6bb82172fa38d3c07bf22", hash);
    }

    @Test
    public void testVariantInterpretation() {
        Variant variant = PhenopacketUtil.heterozygousHgvsVariant("NM_001848.2:c.877G>A");
        VariantInterpretation variantInterpretation = PhenopacketUtil.pathogenicVariantInterpretation(variant);
        String hash = printAndGetHash(variantInterpretation, "variantInterpretation");
        assertEquals("2939d8a67b2a3ebafe3d9b2a2fad0c09f00053317a9cb19daa1ebad88ed5e8e7", hash);
    }



    @Test
    public void testGenomicInterpretation() {
       GenomicInterpretation interpretation = PhenopacketUtil.pathogenicGenomicInterpretationOfVariant("subject 1","NM_001848.2:c.877G>A");
        String hash = printAndGetHash(interpretation, "genomicInterpretation");
        assertEquals("da6dd5623ae93a14034462b599f9345d4c779dec631ddea22d33f6f22fdce9bc", hash);
    }



    @Test
    public void testDisease() {
        OntologyClass diseaseTerm = PhenopacketUtil.ontologyClass("OMIM:164400","Spinocerebellar ataxia 1");
        Disease disease = PhenopacketUtil.diseaseWithOnset(diseaseTerm, "P38Y7M");
        String hash = printAndGetHash(disease, "disease");
        assertEquals("f314e5ca00e0912313dbdc325f4bd4c58cf2c8c8d7ab0bf32fc6f5e19a553267", hash);
    }

    @Test
    public void testMetadata() throws ParseException {
        Resource hp = PhenopacketUtil.resource(   "hp",
                "human phenotype ontology",
                "HP",
                "http://purl.obolibrary.org/obo/hp.owl",
                  "2018-03-08",
                "http://purl.obolibrary.org/obo/HP_");
        Resource geno = PhenopacketUtil.resource(   "geno",
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
        ExternalReference ref = PhenopacketUtil.externalReference("PMID:30808312", description);
        List<Resource> resourceList = List.of(hp, geno, pubmedResource);
        String created = "2019-07-21T00:25:54.662Z";
        String createdBy = "Peter R.";

        MetaData metadata = PhenopacketUtil.metadata(created, createdBy, resourceList, ref);
        String hash = printAndGetHash(metadata, "metadata");
        assertEquals("2d1fb82798db9b0e418573b2150f3adb942eae51e1fb4de55b3945c66d74c4bb", hash);
    }

    @Test
    public void testInterpretationOfPathogenicVar() {
        OntologyClass miller = PhenopacketUtil.ontologyClass("OMIM:263750", "Miller syndrome");
        Gene dhodh = PhenopacketUtil.gene("HGNC:2867", "DHODH");
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
        GenomicInterpretation variantInterpretation = PhenopacketUtil.pathogenicGenomicInterpretationOfVariant("subject 1","NM_000138.4(FBN1):c.6751T>A");
        OntologyClass marfan = PhenopacketUtil.ontologyClass("OMIM:154700", "Marfan syndrome");
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
        assertEquals("1c325f7bc4cc7c642c5ef0170c0d6e9820ffc090dc23782ec251bb2872b8f132", hash);
    }

    @Test
    public void testInterpretationCftr() {
        GenomicInterpretation v1 = PhenopacketUtil.pathogenicGenomicInterpretationOfVariant("subject 1","NM_000492.3(CFTR):c.1477C>T (p.Gln493Ter)");
        GenomicInterpretation v2 = PhenopacketUtil.pathogenicGenomicInterpretationOfVariant("subject 1","NM_000492.3(CFTR):c.1521_1523delCTT (p.Phe508delPhe)");

        OntologyClass cf = PhenopacketUtil.ontologyClass("OMIM: 219700", "Cystic fibrosis");
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
        assertEquals("d36f5f902e9b9d8cfd648d8f61d48eb1e1a5e99a852e0e9fd411097949e81e42", hash);
    }



    @Test
    public void testInterpretationBraf() {
        Variant brafVar = Variant.newBuilder()
                .setHgvsAllele(HgvsAllele.newBuilder()
                        .setHgvs("NM_001374258.1(BRAF):c.1919T>A (p.Val640Glu)")).build();
        VariantInterpretation v1 = VariantInterpretation.newBuilder()
                .setVariantFinding(VariantInterpretation.VariantFinding.PATHOGENIC)
                .setVariant(brafVar).build();
        GenomicInterpretation genomicInterpretation = GenomicInterpretation.newBuilder()
                .setVariantInterpretation(v1)
                .setInterpretationStatus(GenomicInterpretation.InterpretationStatus.ACTIONABLE)
                .setSubjectOrBiosampleId("biosample id")
                .build();
        OntologyClass melanoma = PhenopacketUtil.ontologyClass("NCIT:C3224", "Melanoma");
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
        assertEquals("abcd87e963f7cbed1034b3c8f51df1a83aebe56ca65a3841a46ec0fb93b915e7", hash);

    }
    
}

