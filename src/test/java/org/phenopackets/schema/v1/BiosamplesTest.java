package org.phenopackets.schema.v1;

import com.google.protobuf.util.JsonFormat;
import org.junit.Test;
import org.phenopackets.schema.v1.core.*;

import static org.phenopackets.schema.v1.PhenoPacketTestUtil.ontologyClass;
import static org.phenopackets.schema.v1.PhenoPacketTestUtil.parseTimestamp;

/**
 * Test case for assessing suitability of the phenopacket for representing data from the Biosamples database
 * https://www.ebi.ac.uk/biosamples
 *
 * @author Jules Jacobsen <j.jacobsen@qmul.ac.uk>
 */
public class BiosamplesTest {

    /**
     * https://www.ebi.ac.uk/biosamples/samples/SAMN05324082
     */
    @Test
    public void biosampleExample() throws Exception {

        MetaData metaData = MetaData.newBuilder()
                .addResources(Resource.newBuilder()
                        .setId("pato")
                        .setName("Phenotype And Trait Ontology")
                        .setNamespacePrefix("PATO")
                        .setUrl("http://purl.obolibrary.org/obo/pato.owl")
                        .setVersion("2018-03-28")
                        .build())
                .addResources(Resource.newBuilder()
                        .setId("efo")
                        .setName("Experimental Factor Ontology")
                        .setNamespacePrefix("EFO")
                        .setUrl("http://www.ebi.ac.uk/efo/efo.owl")
                        .setVersion("2.97")
                        .build())
                .addResources(Resource.newBuilder()
                        .setId("cl")
                        .setName("Cell Ontology")
                        .setNamespacePrefix("CL")
                        .setUrl("http://purl.obolibrary.org/obo/cl.owl")
                        .setVersion("2017-12-11")
                        .build())
                .addResources(Resource.newBuilder()
                        .setId("bto")
                        .setName("BRENDA tissue / enzyme source")
                        .setNamespacePrefix("BTO")
                        .setUrl("http://purl.obolibrary.org/obo/bto")
                        .setVersion("2016-05-05")
                        .build())
                .addResources(Resource.newBuilder()
                        .setId("uberon")
                        .setName("Uber-anatomy ontology")
                        .setNamespacePrefix("UBERON")
                        .setUrl("http://purl.obolibrary.org/obo/uberon.owl")
                        .setVersion("2018-05-14")
                        .build())
                .addResources(Resource.newBuilder()
                        .setId("ncbitaxon")
                        .setName("NCBI organismal classification")
                        .setNamespacePrefix("NCBITaxon")
                        .setUrl("http://purl.obolibrary.org/obo/ncbitaxon.owl")
                        .setVersion("2018-03-02")
                        .build())
                .build();

        Individual individual = Individual.newBuilder()
                .setId("SAMN05324082-individual")
                .setSex(ontologyClass("PATO:0000384", "male"))
                .setTaxonomy(ontologyClass("NCBITaxon:9606", "Homo sapiens"))
                .build();

        Biosample biosample = Biosample.newBuilder()
                .setId("SAMN05324082")
                .setIndividualId("SAMN05324082-individual")
                .setCreated(parseTimestamp("2016-06-29T12:03:03.240Z"))
                .setUpdated(parseTimestamp("2018-06-10T10:59:06.784Z"))
                .setDescription("THP-1; 6 hours; DMSO; Replicate 1")
                .setIndividualAgeAtCollection(Age.newBuilder().setAge("P1Y").build())
                .setTaxonomy(ontologyClass("NCBITaxon:9606", "Homo sapiens"))
                .addPhenotypes(Phenotype.newBuilder().setType(ontologyClass("EFO:0001253", "THP-1")).build())
                .addPhenotypes(Phenotype.newBuilder().setType(ontologyClass("CL:0000576", "monocyte")).build())
                .addPhenotypes(Phenotype.newBuilder().setType(ontologyClass("BTO:0000214", "cell culture")).build())
                .addPhenotypes(Phenotype.newBuilder().setType(ontologyClass("UBERON:0000178", "peripheral blood")).build())
                .build();

        Disease disease = Disease.newBuilder().setId("EFO:0000222").setLabel("acute myeloid leukemia").build();

        PhenoPacket phenoPacket = PhenoPacket.newBuilder()
                .setMetaData(metaData)
                .addIndividuals(individual)
                .addBiosamples(biosample)
                .addDiseases(disease)
                .build();

        System.out.println(JsonFormat.printer().print(phenoPacket));
    }

}
