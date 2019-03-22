package org.phenopackets.schema.v1.io;

import com.google.protobuf.util.JsonFormat;
import org.phenopackets.schema.v1.Family;

import java.io.IOException;

/**
 * @author Jules Jacobsen <j.jacobsen@qmul.ac.uk>
 */
public class FamilyFormat implements FormatMapper<Family> {

    @Override
    public String toJson(Family family) throws IOException {
        return JsonFormat.printer().includingDefaultValueFields().print(family);
    }

    @Override
    public String toYaml(Family family) throws IOException {
        String jsonString = JsonFormat.printer().print(family);
        return jsonToYaml(jsonString);
    }

    @Override
    public Family fromJson(String jsonString) throws IOException {
        Family.Builder familyBuilder = Family.newBuilder();
        JsonFormat.parser().merge(jsonString, familyBuilder);
        return familyBuilder.build();
    }

    @Override
    public Family fromYaml(String yamlString) throws IOException {
        String jsonString = yamlToJson(yamlString);
        return fromJson(jsonString);
    }

}
