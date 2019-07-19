package org.phenopackets.schema.v1.examples;

import com.google.protobuf.util.JsonFormat;
import org.junit.jupiter.api.Test;
import org.phenopackets.schema.v1.core.Age;
import org.phenopackets.schema.v1.core.AgeRange;
import org.phenopackets.schema.v1.io.PhenopacketFormat;

public class SandboxDoNotCommit {


    @Test
    void range() throws Exception{
        Age a1 = Age.newBuilder().setAge("P11Y1M").build();
        Age a2 = Age.newBuilder().setAge("P79Y2M").build();
        AgeRange ar = AgeRange.newBuilder().setStart(a1).setEnd(a2).build();
        String asJson = JsonFormat.printer().print(ar);
        System.out.println(asJson);

    }
}
