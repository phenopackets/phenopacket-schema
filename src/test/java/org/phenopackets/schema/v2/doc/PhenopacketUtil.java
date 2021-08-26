package org.phenopackets.schema.v2.doc;

import com.google.protobuf.Timestamp;
import com.google.protobuf.util.Timestamps;
import org.ga4gh.vrsatile.v1.Expression;
import org.ga4gh.vrsatile.v1.GeneDescriptor;
import org.ga4gh.vrsatile.v1.VariationDescriptor;
import org.phenopackets.schema.v2.SchemaVersion;
import org.phenopackets.schema.v2.core.*;

import java.text.ParseException;
import java.time.Period;
import java.util.List;
import java.util.Map;

public class PhenopacketUtil {

    public static final String SCHEMA_VERSION = SchemaVersion.v2_0.toString();

    public static OntologyClass ontologyClass(String id, String label) {
        return OntologyClass.newBuilder().setId(id).setLabel(label).build();
    }


    public static TimeElement timeElementFromDateString(String timeStampString) throws ParseException {
        Timestamp t = Timestamps.parse(timeStampString);
        return TimeElement.newBuilder().setTimestamp(t).build();
    }

    public static VitalStatus vitalStatusDeceased(OntologyClass oclass, TimeElement timeElement) {
        return VitalStatus
                .newBuilder()
                .setCauseOfDeath(oclass)
                .setTimeOfDeath(timeElement)
                .setStatus(VitalStatus.Status.DECEASED).build();
    }

    public static VitalStatus vitalStatusAlive() {
        return VitalStatus
                .newBuilder()
                .setStatus(VitalStatus.Status.ALIVE).build();
    }

    public static GestationalAge gestationalAge(int weeks, int days) {
        if (weeks <0) {
            throw new RuntimeException("Gestational age weeks must be a non-negative integer");
        }
        if (weeks > 45) {
            throw new RuntimeException("Unrealistic gestational week (usually less than 43). Use Builder if needed");
        }
        if (days < 0) {
            throw new RuntimeException("Gestational age days must be a non-negative integer");
        }
        if (days > 6) {
            throw new RuntimeException("Gestational age days must less than 7");
        }
        return GestationalAge.newBuilder().setWeeks(weeks).setDays(days).build();
    }



    public static Resource resource(String id,
                                    String name,
                                    String namespace_prefix,
                                    String url,
                                    String version,
                                    String iri_prefix) {
        return Resource.newBuilder()
                .setId(id)
                .setName(name)
                .setNamespacePrefix(namespace_prefix)
                .setUrl(url)
                .setVersion(version)
                .setIriPrefix(id)
                .build();
    }

    public static Resource hpoResource(String version) {
        String id = "hp";
        String name = "Human Phenotype Ontology";
        String namespace_prefix = "HP";
        String url = "http://www.human-phenotype-ontology.org";
        String iri_prefix = "http://purl.obolibrary.org/obo/HP_";
        return resource(id, name, namespace_prefix, url,version, iri_prefix);
    }

    public static Resource hgncResource(String version) {
        String id = "hgnc";
        String name = "HUGO Gene Nomenclature Committee";
        String namespace_prefix = "HGNC";
        String url = "https://www.genenames.org";
        String iri_prefix = "https://www.genenames.org/data/gene-symbol-report/#!/hgnc_id/";
        return resource(id, name, namespace_prefix, url,version, iri_prefix);
    }

    public static Resource uniprotResource(String version) {
        String id ="uniprot";
        String name = "UniProt Knowledgebase";
        String namespace_prefix ="uniprot";
        String url = "https://www.uniprot.org";
        String iri_prefix = "https://purl.uniprot.org/uniprot/";
        return resource(id, name, namespace_prefix, url,version, iri_prefix);
    }

    public static ExternalReference externalReference(String id, String description) {
        return ExternalReference.newBuilder().setId(id).setDescription(description).build();
    }

    public static Evidence evidenceWithEcoAuthorStatement(String id, String description) {
        String ecoId = "ECO:0006017";
        String label = "author statement from published clinical study used in manual assertion";
        OntologyClass evidenceCode = ontologyClass(ecoId, label);
        ExternalReference extRef = externalReference(id, description);
        return Evidence.newBuilder().setEvidenceCode(evidenceCode).setReference(extRef).build();
    }

    public static GeneDescriptor geneDescriptor(String id, String symbol) {
        return GeneDescriptor.newBuilder().setValueId(id).setSymbol(symbol).build();
    }

    public static GeneDescriptor geneDescriptor(String id, String symbol, List<String> alternateIds) {
        return GeneDescriptor.newBuilder().setValueId(id).setSymbol(symbol).addAllAlternateIds(alternateIds).build();
    }


    public static Age age(String iso8601) {
        Period.parse(iso8601); // if no error thrown, the String is valid
        return Age.newBuilder().setIso8601Duration(iso8601).build();
    }

    public static AgeRange ageRange(String age1, String age2) {
        return AgeRange.newBuilder().setStart(age(age1)).setEnd(age(age2)).build();
    }

    public static ReferenceRange referenceRange(OntologyClass unit, double lower, double upper) {
        return ReferenceRange.newBuilder().setUnit(unit).setLow(lower).setHigh(upper).build();
    }

    public static Value quantitativeValue(OntologyClass unit, double value, ReferenceRange referenceRange) {
        Quantity quantity = Quantity.newBuilder()
                .setUnit(unit)
                .setValue(value)
                .setReferenceRange(referenceRange)
                .build();
        return Value.newBuilder().setQuantity(quantity).build();
    }

    public static Value presentValue() {
        String id = "NCIT:C25626";
        String label = "Present";
        OntologyClass present = OntologyClass.newBuilder().setId(id).setLabel(label).build();
        return Value.newBuilder().setOntologyClass(present).build();
    }


    public static Measurement measurement(OntologyClass assay, Value value, TimeElement timeElement) {
        return Measurement.newBuilder().setAssay(assay).setValue(value).setTimeObserved(timeElement).build();
    }

    public static TypedQuantity typedQuantity(OntologyClass type, Quantity quantity) {
        return TypedQuantity.newBuilder().setType(type).setQuantity(quantity).build();
    }

    public static Quantity quantity(double value, OntologyClass unit) {
        return Quantity.newBuilder().setValue(value).setUnit(unit).build();
    }

    public static Measurement bloodPressure(double systolic, double diastolic, TimeElement timeElement) {
        OntologyClass mmHg = ontologyClass("NCIT:C49670", "Millimeter of Mercury");

        OntologyClass systolicBP = ontologyClass("NCIT:C25298", "Systolic Blood Pressure");
        Quantity systolicQuantity = quantity(systolic, mmHg);
        TypedQuantity systolicCC = typedQuantity(systolicBP, systolicQuantity);

        OntologyClass diastolicBP = ontologyClass("NCIT:C25299", "Diastolic Blood Pressure");
        Quantity diastolicQuantity = quantity(diastolic, mmHg);
        TypedQuantity diastolicCC = typedQuantity(diastolicBP, diastolicQuantity);

        ComplexValue complexValue = ComplexValue.newBuilder()
                .addTypedQuantities(systolicCC)
                .addTypedQuantities(diastolicCC)
                .build();

        OntologyClass bloodPressureMeasurement = ontologyClass("CMO:0000003", "blood pressure measurement");
        return Measurement.newBuilder()
                .setAssay(bloodPressureMeasurement)
                .setComplexValue(complexValue)
                .setTimeObserved(timeElement)
                .build();
    }

    public static File vcfFile(String uri, String description,String genomeAssembly, Map<String,String> individualToSampleIdentifiers ) {
        return File.newBuilder()
                .setUri(uri)
                .putFileAttributes("genomeAssembly", genomeAssembly)
                .putFileAttributes("fileFormat", "vcf")
                .putFileAttributes("description", description)
                .putAllIndividualToFileIdentifiers(individualToSampleIdentifiers)
                .build();
    }

    public static Individual individual(String id, Timestamp dob, Sex sex) {
        return Individual.newBuilder()
                .setId(id)
                .setDateOfBirth(dob)
                .setSex(sex)
                .build();
    }

    public static TimeInterval timeInterval(String dateString1, String dateString2) throws ParseException {
        Timestamp t1 = Timestamps.parse(dateString1);
        Timestamp t2 = Timestamps.parse(dateString2);
        return TimeInterval.newBuilder()
                .setStart(t1)
                .setEnd(t2)
                .build();
    }

    public static DoseInterval doseInterval(TimeInterval timeInterval, Quantity quantity, OntologyClass schedule_frequency) {
        return DoseInterval.newBuilder()
                .setInterval(timeInterval)
                .setQuantity(quantity)
                .setScheduleFrequency(schedule_frequency)
                .build();
    }


    public static Treatment treatment(OntologyClass agent,
                                      OntologyClass route_of_administration,
                                      List<DoseInterval> doseIntervalList,
                                      DrugType drug_type) {
       return Treatment.newBuilder()
               .setAgent(agent)
               .setRouteOfAdministration(route_of_administration)
               .addAllDoseIntervals(doseIntervalList)
               .setDrugType(drug_type).build();
    }


    public static Biosample biosample(String id,
                               String individualId,
                               String description,
                               OntologyClass sampledTissue ,
                               TimeElement age,
                               OntologyClass histologicalDiagnosis,
                               OntologyClass tumorProgression,
                               OntologyClass pathologicalStage,
                                      List<OntologyClass> tnm,
                               OntologyClass tumorGrade,
                               Procedure procedure,
                               File vcfFile) {
    // assume abnormal sample
        OntologyClass abnormalSample = ontologyClass("EFO:0009655", "abnormal sample");
        return Biosample.newBuilder()
                .setId(id)
                .setIndividualId(individualId)
                .setDescription(description)
                .setSampledTissue(sampledTissue)
                .setTimeOfCollection(age)
                .setHistologicalDiagnosis(histologicalDiagnosis)
                .setTumorProgression(tumorProgression)
                .setPathologicalStage(pathologicalStage)
                .addAllPathologicalTnmFinding(tnm)
                .setTumorGrade(tumorGrade)
                .setProcedure(procedure)
                .addFiles(vcfFile)
                .setMaterialSample(abnormalSample)
                .build() ;
    }

    public static VariationDescriptor heterozygousHgvsVariant(String hgvs) {
        OntologyClass heterozygous = ontologyClass("GENO:0000135", "heterozygous");
        return VariationDescriptor.newBuilder()
                .addExpressions(Expression.newBuilder().setSyntax("hgvs").setValue(hgvs))
                .setAllelicState(heterozygous)
                .build();
    }

    public static VariantInterpretation pathogenicVariantInterpretation(VariationDescriptor variationDescriptor) {
        return VariantInterpretation.newBuilder()
                .setAcmgPathogenicityClassification(AcmgPathogenicityClassification.PATHOGENIC)
                .setVariationDescriptor(variationDescriptor).build();
    }

    public static GenomicInterpretation pathogenicGenomicInterpretationOfVariant(String id, String hgvs) {
        VariationDescriptor variationDescriptor = heterozygousHgvsVariant(hgvs);
        VariantInterpretation variantInterpretation = pathogenicVariantInterpretation(variationDescriptor);
        return GenomicInterpretation.newBuilder()
                .setSubjectOrBiosampleId(id)
                .setVariantInterpretation(variantInterpretation)
                .setInterpretationStatus(GenomicInterpretation.InterpretationStatus.CONTRIBUTORY)
                .build();
    }


    public static Disease diseaseWithOnset(OntologyClass diseaseTerm, String onset) {
        TimeElement onsetTimeElement = TimeElement.newBuilder()
                .setAge(Age.newBuilder().setIso8601Duration(onset).build()).build();
        return Disease.newBuilder()
                .setOnset(onsetTimeElement)
                .setTerm(diseaseTerm).build();
    }

    public static MetaData metadata(String created, String createdBy,List<Resource> resourceList,ExternalReference ref ) throws ParseException {
        Timestamp t = Timestamps.parse(created);
        return MetaData.newBuilder()
                .setPhenopacketSchemaVersion(SCHEMA_VERSION)
                .setCreated(t)
                .setCreatedBy(createdBy)
                .addAllResources(resourceList)
                .addExternalReferences(ref).build();
    }

    public static Pedigree.Person affectedPerson(String familyId, String individualId, String paternalId, String maternalId, Sex sex) {
        return Pedigree.Person.newBuilder()
                .setFamilyId(familyId)
                .setIndividualId(individualId)
                .setPaternalId(paternalId)
                .setMaternalId(maternalId)
                .setAffectedStatus(Pedigree.Person.AffectedStatus.AFFECTED)
                .setSex(sex).build();
    }
    public static Pedigree.Person unaffectedPerson(String familyId, String individualId, String paternalId, String maternalId, Sex sex) {
        return Pedigree.Person.newBuilder()
                .setFamilyId(familyId)
                .setIndividualId(individualId)
                .setPaternalId(paternalId)
                .setMaternalId(maternalId)
                .setAffectedStatus(Pedigree.Person.AffectedStatus.UNAFFECTED)
                .setSex(sex).build();
    }


}
