package org.phenopackets.schema.v2.examples;

import org.phenopackets.schema.v2.Phenopacket;
import org.phenopackets.schema.v2.core.*;
import org.phenopackets.schema.v2.doc.PhenopacketUtil;

import static org.phenopackets.schema.v2.PhenoPacketTestUtil.ontologyClass;
import static org.phenopackets.schema.v2.PhenoPacketTestUtil.parseTimestamp;

/**
 * Test case for assessing suitability of the phenopacket for representing data from the Biosamples database
 * https://www.ebi.ac.uk/biosamples.
 *
 * JSON sample for this can be found in src/test/resources/biosamples-SAMN05324082.json
 *
 *
 * @author Jules Jacobsen <j.jacobsen@qmul.ac.uk>
 */
class BiosamplesPhenopacketExample {

    /**
     * https://www.ebi.ac.uk/biosamples/samples/SAMN05324082
     */
    static Phenopacket biosamplePhenopacket() {

        MetaData metaData = MetaData.newBuilder()
                .setPhenopacketSchemaVersion(PhenopacketUtil.SCHEMA_VERSION)
                .setCreated(parseTimestamp("2016-06-29T12:03:03.240Z"))
                .addUpdates(Update.newBuilder().setTimestamp(parseTimestamp("2018-06-10T10:59:06.784Z")))
                .addResources(Resource.newBuilder()
                        .setId("pato")
                        .setName("PhenotypicFeature And Trait Ontology")
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
                .setSex(Sex.MALE)
                .setTaxonomy(ontologyClass("NCBITaxon:9606", "Homo sapiens"))
                .build();

        Biosample biosample = Biosample.newBuilder()
                .setId("SAMN05324082")
                .setIndividualId("SAMN05324082-individual")
                .setDescription("THP-1; 6 hours; DMSO; Replicate 1")
                .setTimeOfCollection(TimeElement.newBuilder().setAge(Age.newBuilder().setIso8601Duration("P1Y")))
                .setTaxonomy(ontologyClass("NCBITaxon:9606", "Homo sapiens"))
                .setSampledTissue(ontologyClass("UBERON:0000178", "peripheral blood"))
                .setHistologicalDiagnosis(ontologyClass("EFO:0000221", "Acute Monocytic Leukemia"))
                .addPhenotypicFeatures(PhenotypicFeature.newBuilder().setType(ontologyClass("EFO:0001253", "THP-1")).build())
                .addPhenotypicFeatures(PhenotypicFeature.newBuilder().setType(ontologyClass("BTO:0000214", "cell culture")).build())
                .addPhenotypicFeatures(PhenotypicFeature.newBuilder().setType(ontologyClass("CL:0000576", "monocyte")).build())
                .build();

        Disease disease = Disease.newBuilder().setTerm(ontologyClass("EFO:0000222", "acute myeloid leukemia")).build();

        return Phenopacket.newBuilder()
                .setMetaData(metaData)
                .setSubject(individual)
                .addBiosamples(biosample)
                .addDiseases(disease)
                .build();

    }

}
