package org.phenopackets.schema.v2.doc;

import org.phenopackets.schema.v1.core.OntologyClass;

public class Wrapper {


    public static OntologyClass buildOntologyClass(String id, String label) {
        return OntologyClass.newBuilder().setId(id).setLabel(label).build();
    }

}
