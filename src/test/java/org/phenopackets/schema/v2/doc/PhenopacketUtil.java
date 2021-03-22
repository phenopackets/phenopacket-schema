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

}
