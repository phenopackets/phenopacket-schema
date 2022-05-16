package org.phenopackets.schema.v1;

import com.google.protobuf.Timestamp;
import com.google.protobuf.util.JsonFormat;
import org.junit.jupiter.api.Test;
import org.phenopackets.schema.v1.core.*;
import org.phenopackets.schema.v1.examples.TestExamples;

/**
 * @author Jules Jacobsen <j.jacobsen@qmul.ac.uk>
 */
public class InterpretationTest {

    @Test
    public void interpretation() throws Exception {
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


        long millis  = System.currentTimeMillis();
        Timestamp timestamp = Timestamp.newBuilder().setSeconds(millis / 1000)
                .setNanos((int) ((millis % 1000) * 1000000)).build();

        MetaData metaData = MetaData.newBuilder()
                .addResources(Resource.newBuilder()
                        .setId("hp")
                        .setName("human phenotype ontology")
                        .setNamespacePrefix("HP")
                        .setIriPrefix("http://purl.obolibrary.org/obo/HP_")
                        .setUrl("http://purl.obolibrary.org/obo/hp.owl")
                        .setVersion("2018-03-08")
                        .build())
                .addResources(Resource.newBuilder()
                        .setId("omim")
                        .setName("Online Mendelian Inheritance in Man")
                        .setNamespacePrefix("OMIM")
                        .setIriPrefix("https://omim.org/entry/")
                        .setUrl("https://omim.org")
                        .build())
                .addResources(Resource.newBuilder()
                        .setId("geno")
                        .setName("Genotype Ontology")
                        .setNamespacePrefix("GENO")
                        .setIriPrefix("http://purl.obolibrary.org/obo/GENO_")
                        .setUrl("http://purl.obolibrary.org/obo/geno.owl")
                        .setVersion("19-03-2018")
                        .build())
                .addResources(Resource.newBuilder()
                        .setId("hgnc")
                        .setName("HUGO Gene Nomenclature Committee")
                        .setNamespacePrefix("HGNC")
                        .setIriPrefix("https://www.genenames.org/data/gene-symbol-report/#!/hgnc_id/HGNC:")
                        .build())
                .setCreatedBy("Jules J.")
                .setCreated(timestamp)
                .build();

        Interpretation interpretation = Interpretation.newBuilder()
                .setId("SOLVERD:0000012456")
                .setPhenopacket(TestExamples.rareDiseasePhenopacket())
                .setResolutionStatus(Interpretation.ResolutionStatus.SOLVED)
                .addDiagnosis(diagnosis)
                .setMetaData(metaData)
                .build();

        System.out.println(JsonFormat.printer().print(interpretation));
    }
}
