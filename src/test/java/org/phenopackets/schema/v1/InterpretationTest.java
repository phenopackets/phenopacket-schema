package org.phenopackets.schema.v1;

import com.google.protobuf.util.JsonFormat;
import org.junit.jupiter.api.Test;
import org.phenopackets.schema.v1.core.Disease;
import org.phenopackets.schema.v1.core.Gene;
import org.phenopackets.schema.v1.core.OntologyClass;
import org.phenopackets.schema.v1.examples.TestExamples;

/**
 * @author Jules Jacobsen <j.jacobsen@qmul.ac.uk>
 */
class InterpretationTest {

    @Test
    void interpretation() throws Exception {
        Diagnosis diagnosis = Diagnosis.newBuilder()
                .setDisease(Disease.newBuilder()
                        .setTerm(
                            OntologyClass.newBuilder()
                            .setId("OMIM:263750")
                            .setLabel("Miller syndrome")
                            .build()
                        )
                    .build())
                .addGenomicInterpretations(
                        GenomicInterpretation.newBuilder()
                                .setStatus(GenomicInterpretation.Status.CAUSATIVE)
                                .setGene(Gene.newBuilder().setId("HGNC:2867").setSymbol("DHODH").build())
                                .build()
                )
                .build();

        Interpretation interpretation = Interpretation.newBuilder()
                .setId("SOLVERD:0000012456")
                .setPhenopacket(TestExamples.rareDiseasePhenopacket())
                .setResolutionStatus(Interpretation.ResolutionStatus.SOLVED)
                .addDiagnosis(diagnosis)
                .build();

        System.out.println(JsonFormat.printer().print(interpretation));
    }
}
