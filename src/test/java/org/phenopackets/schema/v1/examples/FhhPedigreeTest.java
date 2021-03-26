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
        FhhIndividual proband = FhhIndividual.newBuilder().setId("proband").build();
        FhhIndividual mother = FhhIndividual.newBuilder().setId("mother")
                .setSex(ontologyClass("", "")) // what ontology?
                .build();
        FhhIndividual father = FhhIndividual.newBuilder().setId("father")
                .setSex(ontologyClass("", "")) // what ontology?
                .build();

        FhhRelationship probandMotherRelationship = FhhRelationship.newBuilder().setIndividual("mother")
                .setRelation(ontologyClass("REL:003", "biological parent"))
                .setRelative("proband")
                .build();

        FhhRelationship probandFatherRelationship = FhhRelationship.newBuilder().setIndividual("father")
                .setRelation(ontologyClass("REL:003", "biological parent"))
                .setRelative("proband")
                .build();

        FhhPedgree fhhPedgree = FhhPedgree.newBuilder()
                .setProband("proband")
                .setConsultand("someone")
                .setDate("2021-02-18")
                .setReason(ontologyClass("OMIM:101600", "Apert syndrome"))
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
