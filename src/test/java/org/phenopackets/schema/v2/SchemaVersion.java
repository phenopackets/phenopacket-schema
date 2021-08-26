package org.phenopackets.schema.v2;

public enum SchemaVersion {
    v1_0("1.0"),
    v2_0("2.0");

    private final String version;

    SchemaVersion(String version) {
        this.version = version;
    }

    public SchemaVersion parseSchemaVersion(String schemaVersion) {
        if (schemaVersion.startsWith("1.0")) {
            return v1_0;
        }
        if (schemaVersion.startsWith("2.0")) {
            return v2_0;
        }
        return v1_0;
    }

    @Override
    public String toString() {
        return version;
    }
}
