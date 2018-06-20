package org.phenopackets.schema.v1;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.google.protobuf.MessageOrBuilder;
import com.google.protobuf.Timestamp;
import com.google.protobuf.util.JsonFormat;
import org.phenopackets.schema.v1.core.OntologyClass;

import java.io.IOException;
import java.time.Instant;

/**
 * @author Jules Jacobsen <j.jacobsen@qmul.ac.uk>
 */
public class PhenoPacketTestUtil {

    public static final OntologyClass FEMALE = ontologyClass("PATO:0000383", "female");
    public static final OntologyClass MALE = ontologyClass("PATO:0000384", "male");


    public static OntologyClass ontologyClass(String id, String label) {
        return OntologyClass.newBuilder()
                .setId(id)
                .setLabel(label)
                .build();
    }

    public static Timestamp parseTimestamp(String string) {
        return Timestamp.newBuilder()
                .setSeconds(Instant.parse(string).getEpochSecond())
                .build();
    }

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
