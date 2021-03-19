package org.ga4gh.vrs.v1;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;
import org.ga4gh.vrs.*;
import org.junit.jupiter.api.Test;

/**
 * @author Jules Jacobsen <j.jacobsen@qmul.ac.uk>
 */
public class VrsTest {

    enum Type {
        Text, Allele, SequenceLocation, SimpleInterval;
    }

    @Test
    void allele() throws InvalidProtocolBufferException {

        LiteralSequenceExpression literalSequenceExpression = LiteralSequenceExpression.newBuilder()
                .setType("LiteralSequenceExpression")
                .setSequence("T")
                .build();

        SequenceExpression sequenceExpression = SequenceExpression.newBuilder()
                .setLiteral(literalSequenceExpression)
                .build();

        SimpleInterval simpleInterval = SimpleInterval.newBuilder()
                .setType("SimpleInterval")
                .setStart(44908822)
                .setEnd(44908821)
                .build();

        SequenceInterval sequenceInterval = SequenceInterval.newBuilder()
                .setSimpleInterval(simpleInterval)
                .build();

        SequenceLocation sequenceLocation = SequenceLocation.newBuilder()
                .setType("SequenceLocation")
                .setInterval(sequenceInterval)
                .setSequenceId("ga4gh:SQ.IIB53T8CNeJJdUqzn9V_JnRtQadwWCbl")
                .build();

        Location location = Location.newBuilder()
                .setSequenceLocation(sequenceLocation)
                .build();

        Allele allele = Allele.newBuilder()
                .setType("Allele")
                .setLocation(location)
                .setSequenceExpression(sequenceExpression)
                .build();

        // Output can be validated at https://json-schema-validator.herokuapp.com/
        System.out.println(JsonFormat.printer().preservingProtoFieldNames().print(allele));
    }



    @Test
    void haplotype() throws InvalidProtocolBufferException {
//        Allele allele = Allele.newBuilder()
//                .setType("Allele")
//                .setLocation(SequenceLocation.newBuilder()
//                        .setType("SequenceLocation")
//                        .setInterval(SimpleInterval.newBuilder()
//                                .setEnd(44908822)
//                                .setStart(44908821)
//                                .setType("SimpleInterval")
//                                .build())
//                        .setSequenceId("ga4gh:SQ.IIB53T8CNeJJdUqzn9V_JnRtQadwWCbl")
//                        .build())
//                .setState(SequenceState.newBuilder()
//                        .setType("SequenceState")
//                        .setSequence("T")
//                        .build())
//                .build();
//
//        Haplotype haplotype = Haplotype.newBuilder().setType("Haplotype").addMembers(allele).build();
//
//        System.out.println(JsonFormat.printer().preservingProtoFieldNames().print(haplotype));
    }
}
