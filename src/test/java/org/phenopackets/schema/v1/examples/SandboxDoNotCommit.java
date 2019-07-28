package org.phenopackets.schema.v1.examples;

import com.google.protobuf.util.JsonFormat;
import org.junit.jupiter.api.Test;
import org.phenopackets.schema.v1.core.*;
import org.phenopackets.schema.v1.io.PhenopacketFormat;

public class SandboxDoNotCommit {


    @Test
    void range() throws Exception{
        Age a1 = Age.newBuilder().setAge("P11Y1M").build();
        Age a2 = Age.newBuilder().setAge("P79Y2M").build();
        AgeRange ar = AgeRange.newBuilder().setStart(a1).setEnd(a2).build();
        String asJson = JsonFormat.printer().print(ar);
        System.out.println(asJson);

    }



    @Test
    void disease() throws Exception {
        OntologyClass oc = OntologyClass.newBuilder().setId("OMIM:164400").setLabel("Spinocerebellar ataxia 1").build();
        Age a1 = Age.newBuilder().setAge("P38Y7M").build();
        Disease d = Disease.newBuilder().setTerm(oc).setAgeOfOnset(a1).build();
        String asJson = JsonFormat.printer().print(d);
        System.out.println(asJson);

    }


    @Test
    void evidence() throws Exception {
        OntologyClass oc = OntologyClass.newBuilder().setId("ECO:0006017").setLabel("author statement from published clinical study used in manual assertion").build();
        ExternalReference er = ExternalReference.newBuilder().setId("PMID:30962759").setDescription("Recurrent Erythema Nodosum in a Child with a SHOC2 Gene Mutation").build();
        Evidence evi = Evidence.newBuilder().setEvidenceCode(oc).setReference(er).build();
        String asJson = JsonFormat.printer().print(evi);
        System.out.println(asJson);
    }


    @Test
    void gene() throws Exception {
        Gene g = Gene.newBuilder().setId("HGNC:347").setSymbol("ETF1").build();
        String asJson = JsonFormat.printer().print(g);
        System.out.println(asJson);
    }
}
