package org.phenopackets.schema.v1.io;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.google.protobuf.MessageOrBuilder;
import com.google.protobuf.util.JsonFormat;

import java.io.IOException;

/**
 * @author Jules Jacobsen <j.jacobsen@qmul.ac.uk>
 */
public class PhenoPacketFormat {

    public static String toYaml(MessageOrBuilder messageOrBuilder) throws IOException {
        String jsonString = JsonFormat.printer().print(messageOrBuilder);
        return jsonToYaml(jsonString);
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
