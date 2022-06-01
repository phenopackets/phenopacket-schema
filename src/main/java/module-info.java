module org.phenopackets.schema {
    requires transitive com.google.protobuf;

    exports org.phenopackets.schema.v1;
    exports org.phenopackets.schema.v1.core;

    exports org.phenopackets.schema.v2;
    exports org.phenopackets.schema.v2.core;

    exports org.ga4gh.vrs.v1;
    exports org.ga4gh.vrsatile.v1;
}