package org.phenopackets.schema.v1.examples;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;
import org.ga4gh.pedigree.v1.FhhIndividual;
import org.ga4gh.pedigree.v1.FhhPedgree;
import org.ga4gh.pedigree.v1.FhhRelationship;
import org.junit.jupiter.api.Test;
import org.phenopackets.schema.v1.core.OntologyClass;

public class FhhPedigreeTest {

    @Test
    void trio() throws InvalidProtocolBufferException {
        FhhIndividual proband = FhhIndividual.newBuilder().setID("proband").build();
        FhhIndividual mother = FhhIndividual.newBuilder().setID("mother")
                .setSex(ontologyClass("", "")) // what ontology?
                .build();
        FhhIndividual father = FhhIndividual.newBuilder().setID("father")
                .setSex(ontologyClass("", "")) // what ontology?
                .build();

        FhhRelationship probandMotherRelationship = FhhRelationship.newBuilder().setId("M-P")
                .setIndividual("mother")
                .setRelation(ontologyClass("REL:003", "biological parent"))
                .setRelative("proband")
                .build();

        FhhRelationship probandFatherRelationship = FhhRelationship.newBuilder().setId("F-P")
                .setIndividual("father")
                .setRelation(ontologyClass("REL:003", "biological parent"))
                .setRelative("proband")
                .build();

        FhhPedgree fhhPedgree = FhhPedgree.newBuilder()
                .setProband("proband")
                .setConsultand("someone")
                .setCollectedAt("2021-02-18") // dateCollected ?
                .setReasonCollected(ontologyClass("OMIM:101600", "Apert syndrome")) // this seems odd
                .addIndividuals(proband)
                .addIndividuals(mother)
                .addIndividuals(father)
                .addRelationships(probandFatherRelationship)
                .addRelationships(probandMotherRelationship)
                .build();

        System.out.println(JsonFormat.printer().print(fhhPedgree));
    }

    private OntologyClass ontologyClass(String id, String label) {
        return OntologyClass.newBuilder().setId(id).setLabel(label).build();
    }
}
