package org.phenopackets.schema.v1.io;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.google.protobuf.util.JsonFormat;
import org.phenopackets.schema.v1.Phenopacket;

import java.io.IOException;

/**
 * Utility class for transforming {@link Phenopacket} objects to/from JSON or YAML and for inter-converting between
 * these representations.
 *
 * @author Jules Jacobsen j.jacobsen@qmul.ac.uk
 */
public class PhenopacketFormat {

    private PhenopacketFormat() {
    }

    public static String toJson(Phenopacket phenoPacket) throws IOException {
        return JsonFormat.printer().includingDefaultValueFields().print(phenoPacket);
    }

    public static Phenopacket fromJson(String jsonString) throws IOException {
        Phenopacket.Builder phenoPacketBuilder = Phenopacket.newBuilder();
        JsonFormat.parser().merge(jsonString, phenoPacketBuilder);
        return phenoPacketBuilder.build();
    }

    public static String toYaml(Phenopacket phenoPacket) throws IOException {
        String jsonString = JsonFormat.printer().print(phenoPacket);
        return jsonToYaml(jsonString);
    }

    public static Phenopacket fromYaml(String yamlString) throws IOException {
        String jsonString = yamlToJson(yamlString);
        return fromJson(jsonString);
    }

    // parse JSON to YAML
    public static String jsonToYaml(String jsonString) throws IOException {
        JsonNode jsonNodeTree = new ObjectMapper().readTree(jsonString);
        return new YAMLMapper().writeValueAsString(jsonNodeTree);
    }

    // parse YAML to JSON
    public static String yamlToJson(String yamlString) throws IOException {
        JsonNode jsonNodeTree = new YAMLMapper().readTree(yamlString);
        return new ObjectMapper().writeValueAsString(jsonNodeTree);
    }

}
