package org.phenopackets.schema.v2.doc;

import com.google.protobuf.Timestamp;
import com.google.protobuf.util.Timestamps;
import org.phenopackets.schema.v1.core.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

public class PhenopacketUtil {


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

    public static Gene gene(String id, String symbol) {
        return Gene.newBuilder().setId(id).setSymbol(symbol).build();
    }

    public static Gene gene(String id, String symbol, List<String> alternateIds) {
        return Gene.newBuilder().setId(id).setSymbol(symbol).addAllAlternateIds(alternateIds).build();
    }


    public static Age age(String iso8601) {
        Set<Character> allowableCharacters = Set.of('Y', 'M', 'D', 'H','S');
        if (! iso8601.startsWith("P")) {
            throw new RuntimeException("ISO8601 Age string must start with P, but we got '" + iso8601 +"'");
        }
        if (iso8601.length() < 2) {
            throw new RuntimeException("ISO8601 Age string too short. We got '" + iso8601 +"'");
        }
        String a = iso8601.substring(1);

        for (int i=0; i<a.length(); i++) {
            char c = a.charAt(i);
            if (Character.isLetter(c)) {
                if (! allowableCharacters.contains(c)) {
                    throw new RuntimeException("Use of invalid character. We got '" + iso8601 +"'");
                }
            } else if (! Character.isDigit (c)) {
                throw new RuntimeException("Use of invalid character (must be digit or character). We got '" + iso8601 +"'");
            }
        }
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
                .setValue(value)
                .setUnitClass(unit)
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

    public static ComplexQuantity complexQuantity(OntologyClass type, Quantity quantity) {
        return ComplexQuantity.newBuilder().setType(type).setQuantity(quantity).build();
    }

    public static Quantity quantity(double value, OntologyClass unit) {
        return Quantity.newBuilder().setValue(value).setUnitClass(unit).build();
    }

    public static Measurement bloodPressure(double systolic, double diastolic, TimeElement timeElement) {
        OntologyClass systolicBP = ontologyClass("NCIT:C25298", "Systolic Blood Pressure");
        OntologyClass diastolicBP = ontologyClass("NCIT:C25299", "Diastolic Blood Pressure");
        OntologyClass mmHg = ontologyClass("NCIT:C49670", "Millimeter of Mercury");
        Quantity systolicQuantity = quantity(systolic, mmHg);
        Quantity diastolicQuantity = quantity(diastolic, mmHg);
        ComplexQuantity systolicCC = complexQuantity(systolicBP, systolicQuantity);
        ComplexQuantity diastolicCC = complexQuantity(diastolicBP, diastolicQuantity);
        OntologyClass bloodPressureMeasurement = ontologyClass("CMO:0000003", "blood pressure measurement");
        return Measurement.newBuilder()
                .setAssay(bloodPressureMeasurement)
                .addComplexQuantity(systolicCC)
                .addComplexQuantity(diastolicCC)
                .setTimeObserved(timeElement)
                .build();
    }

    public static HtsFile vcfFile(String uri, String description,String genomeAssembly,Map<String,String> individualToSampleIdentifiers ) {
        return HtsFile.newBuilder()
                .setUri(uri)
                .setDescription(description)
                .setGenomeAssembly(genomeAssembly)
                .setHtsFormat(HtsFile.HtsFormat.VCF)
                .putAllIndividualToSampleIdentifiers(individualToSampleIdentifiers)
                .build();
    }


}
