package org.phenopackets.schema.v2.doc;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.google.common.hash.Hashing;
import com.google.protobuf.Message;
import com.google.protobuf.Timestamp;
import com.google.protobuf.util.JsonFormat;
import org.phenopackets.schema.v2.core.TimeElement;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;


public class TestBase {

    protected static String sha256(String originalString) {
        return Hashing.sha256()
                .hashString(originalString, StandardCharsets.UTF_8)
                .toString();
    }


    // parse JSON to YAML
    protected static String jsonToYaml(String jsonString) throws IOException {
        JsonNode jsonNodeTree = new ObjectMapper().readTree(jsonString);
        return new YAMLMapper().writeValueAsString(jsonNodeTree);
    }


    protected static String messageToYaml(Message message, String label) throws IOException {
        String jsonString = JsonFormat.printer().print(message);
        JsonNode jsonNodeTree = new ObjectMapper().readTree(jsonString);
        JsonNode node = JsonNodeFactory.instance.objectNode().set(label, jsonNodeTree);
        return new YAMLMapper().writeValueAsString(node);
    }

    protected static TimeElement getNowTimestampElement() {
        Instant time = Instant.now();
        Timestamp timestamp = Timestamp.newBuilder().setSeconds(time.getEpochSecond())
                .setNanos(time.getNano()).build();
        return TimeElement.newBuilder().setTimestamp(timestamp).build();
    }
}
