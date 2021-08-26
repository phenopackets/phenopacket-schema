package org.phenopackets.schema.v2;

import com.google.protobuf.Timestamp;
import org.ga4gh.vrsatile.v1.GeneDescriptor;
import org.ga4gh.vrsatile.v1.VariationDescriptor;
import org.junit.jupiter.api.Test;
import org.phenopackets.schema.v2.core.*;
import org.phenopackets.schema.v2.examples.TestExamples;
import org.phenopackets.schema.v2.io.FormatMapper;

/**
 * @author Jules Jacobsen <j.jacobsen@qmul.ac.uk>
 */
public class InterpretationTest {

    @Test
    public void interpretation() throws Exception {

        Diagnosis diagnosis = Diagnosis.newBuilder()
                .setDisease(OntologyClass.newBuilder()
                            .setId("OMIM:263750")
                            .setLabel("Miller syndrome")
                )
                .addGenomicInterpretations(
                        GenomicInterpretation.newBuilder()
                                .setInterpretationStatus(GenomicInterpretation.InterpretationStatus.CONTRIBUTORY)
                                .setGene(GeneDescriptor.newBuilder().setValueId("HGNC:2867").setSymbol("DHODH")))
                .addGenomicInterpretations(
                        GenomicInterpretation.newBuilder()
                                .setInterpretationStatus(GenomicInterpretation.InterpretationStatus.CONTRIBUTORY)
                                .setVariantInterpretation(VariantInterpretation.newBuilder()
                                .setVariationDescriptor(VariationDescriptor.newBuilder().setDescription(""))
                                .setAcmgPathogenicityClassification(AcmgPathogenicityClassification.PATHOGENIC)))
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
                .setProgressStatus(Interpretation.ProgressStatus.SOLVED)
                .setDiagnosis(diagnosis)
                .build();

        Phenopacket phenopacketWithInterpretation = TestExamples.rareDiseasePhenopacket().toBuilder()
                .addInterpretations(interpretation)
                .mergeMetaData(metaData)
                .build();
        System.out.println(FormatMapper.messageToYaml(phenopacketWithInterpretation));
    }
}
