package org.phenopackets.schema.v1.io;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.io.IOException;

/**
 * @author Jules Jacobsen <j.jacobsen@qmul.ac.uk>
 */
public class FormatMapper {

    private FormatMapper() {
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
