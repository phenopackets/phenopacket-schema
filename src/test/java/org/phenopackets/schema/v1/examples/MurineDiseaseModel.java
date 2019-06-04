package org.phenopackets.schema.v1.examples;


import org.junit.jupiter.api.Test;
import org.phenopackets.schema.v1.Phenopacket;
import org.phenopackets.schema.v1.core.*;
import org.phenopackets.schema.v1.io.PhenopacketFormat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.phenopackets.schema.v1.PhenoPacketTestUtil.ontologyClass;


/**
 * Transmitting data about the C1039G Fbn1 knockin mouse from PMID:15254584 representing MGI:3690326
 */
public class MurineDiseaseModel {
    /** The Genotype accession ID for  Fbn1tm1Hcd/Fbn1tm1Hcd */
    private final String MgiId = "MGI:3690326";


    Phenopacket C1039Gmodel() {
        PhenotypicFeature aorticDissection = PhenotypicFeature.newBuilder()
                .setType(ontologyClass("MP:0004044", "aortic dissection"))
                .build();

        PhenotypicFeature abnormalRibMorphology = PhenotypicFeature.newBuilder()
                .setType(ontologyClass("MP:0000150", "abnormal rib morphology"))
                .build();

        PhenotypicFeature kyphosis = PhenotypicFeature.newBuilder()
                .setType(ontologyClass("MP:0000160", "kyphosis"))
                .build();

        PhenotypicFeature  	overexpandedPulmonaryAlveoli = PhenotypicFeature.newBuilder()
                .setType(ontologyClass("MP:0001183", "overexpanded pulmonary alveoli"))
                .build();



        PhenotypicFeature abnormalAortaElasticFiberMorphology =PhenotypicFeature.newBuilder()
                .setType(ontologyClass("MP:0003211", "abnormal aorta elastic fiber morphology"))
                .build();

        PhenotypicFeature mitralValveProlapse=PhenotypicFeature.newBuilder()
                .setType(ontologyClass("MP:0006120", "mitral valve prolapse"))
                .build();

        PhenotypicFeature  abnormalHeartLeftAtriumMorphology=PhenotypicFeature.newBuilder()
                .setType(ontologyClass("MP:0003923", "abnormal heart left atrium morphology"))
                .build();

        PhenotypicFeature  abnormalHeartLeftVentricleMorphology=PhenotypicFeature.newBuilder()
                .setType(ontologyClass("MP:0003921", "abnormal heart left ventricle morphology"))
                .build();


        PhenotypicFeature   increasedAortaWallThickness=PhenotypicFeature.newBuilder()
                .setType(ontologyClass("MP:0010996", " increased aorta wall thickness"))
                .build();



        Individual Fbn1C1039G_Model = Individual.newBuilder()
                .setId(MgiId)
                .build();

        MurineAllele allele = MurineAllele.newBuilder().setId("MGI:3690325").setGene("Fbn1").setAlleleSymbol("tm1Hcd").build();
        Variant Fbn1variant = Variant.newBuilder()
                .setMurineAllele(allele)
                .setBackground("involves: 129S1/Sv * 129X1/SvJ * C57BL/6J")
                .setZygosity(ontologyClass("GENO:0000135", "heterozygous"))
                .build();

        ExternalReference pubmedRef = ExternalReference.newBuilder()
               .setId("PMID:15254584")
                .setDescription("Heterozygous Fbn1 C1039G mutation. Judge DP, et al. \n" +
                        "Evidence for a critical contribution of haploinsufficiency in the complex \n" +
                        "pathogenesis of Marfan syndrome. J Clin Invest. 2004;114(2):172-81.")
                .build();

        MetaData metaData = MetaData.newBuilder()
                .addResources(Resource.newBuilder()
                        .setId("mp")
                        .setName("mammalian phenotype ontology")
                        .setNamespacePrefix("MP")
                        .setIriPrefix("http://purl.obolibrary.org/obo/MP_")
                        .setUrl("http://purl.obolibrary.org/obo/mp.owl")
                        .setVersion("2019-03-08")
                        .build())
                .addResources(Resource.newBuilder()
                        .setId("geno")
                        .setName("Genotype Ontology")
                        .setNamespacePrefix("GENO")
                        .setIriPrefix("http://purl.obolibrary.org/obo/GENO_")
                        .setUrl("http://purl.obolibrary.org/obo/geno.owl")
                        .setVersion("19-03-2018")
                        .build())
                .addExternalReferences(pubmedRef)
                .setCreatedBy("Peter")
                .build();

        return Phenopacket.newBuilder()
                .setSubject(Fbn1C1039G_Model)
                .addPhenotypicFeatures(aorticDissection)
                .addPhenotypicFeatures(abnormalRibMorphology)
                .addPhenotypicFeatures(kyphosis)
                .addPhenotypicFeatures(overexpandedPulmonaryAlveoli)
                .addPhenotypicFeatures(abnormalAortaElasticFiberMorphology)
                .addPhenotypicFeatures(mitralValveProlapse)
                .addPhenotypicFeatures(abnormalHeartLeftAtriumMorphology)
                .addPhenotypicFeatures(abnormalHeartLeftVentricleMorphology)
                .addPhenotypicFeatures(increasedAortaWallThickness)
                .addVariants(Fbn1variant)
                .setMetaData(metaData)
                .build();

    }


    @Test
    void testModelName() {
        Phenopacket ppack = C1039Gmodel();


        assertEquals(this.MgiId, ppack.getSubject().getId());
    }

    @Test
    void printAsJson() throws Exception{
        Phenopacket phenopacket = C1039Gmodel();
        System.out.println(PhenopacketFormat.toJson(phenopacket));
    }
}
