package org.phenopackets.schema.v2.io;

import com.google.protobuf.util.JsonFormat;
import org.phenopackets.schema.v2.Phenopacket;

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
        return JsonFormat.printer().print(phenoPacket);
    }

    public static Phenopacket fromJson(String jsonString) throws IOException {
        Phenopacket.Builder phenoPacketBuilder = Phenopacket.newBuilder();
        JsonFormat.parser().merge(jsonString, phenoPacketBuilder);
        return phenoPacketBuilder.build();
    }

    public static String toYaml(Phenopacket phenoPacket) throws IOException {
        return FormatMapper.messageToYaml(phenoPacket);
    }

    public static Phenopacket fromYaml(String yamlString) throws IOException {
        String jsonString = FormatMapper.yamlToJson(yamlString);
        return fromJson(jsonString);
    }

}
