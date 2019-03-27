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
        Phenotype aorticDissection = Phenotype.newBuilder()
                .setType(ontologyClass("MP:0004044", "aortic dissection"))
                .build();

        Phenotype abnormalRibMorphology = Phenotype.newBuilder()
                .setType(ontologyClass("MP:0000150", "abnormal rib morphology"))
                .build();

        Phenotype kyphosis = Phenotype.newBuilder()
                .setType(ontologyClass("MP:0000160", "kyphosis"))
                .build();

        Phenotype  	overexpandedPulmonaryAlveoli = Phenotype.newBuilder()
                .setType(ontologyClass("MP:0001183", "overexpanded pulmonary alveoli"))
                .build();



        Phenotype abnormalAortaElasticFiberMorphology =Phenotype.newBuilder()
                .setType(ontologyClass("MP:0003211", "abnormal aorta elastic fiber morphology"))
                .build();

        Phenotype mitralValveProlapse=Phenotype.newBuilder()
                .setType(ontologyClass("MP:0006120", "mitral valve prolapse"))
                .build();

        Phenotype  abnormalHeartLeftAtriumMorphology=Phenotype.newBuilder()
                .setType(ontologyClass("MP:0003923", "abnormal heart left atrium morphology"))
                .build();

        Phenotype  abnormalHeartLeftVentricleMorphology=Phenotype.newBuilder()
                .setType(ontologyClass("MP:0003921", "abnormal heart left ventricle morphology"))
                .build();


        Phenotype   increasedAortaWallThickness=Phenotype.newBuilder()
                .setType(ontologyClass("MP:0010996", " increased aorta wall thickness"))
                .build();



        Individual Fbn1C1039G_Model = Individual.newBuilder()
                .setId(MgiId)
                .build();

        MouseAllele allele = MouseAllele.newBuilder().setId("MGI:3690325").setGene("Fbn1").setAlleleCode("tm1Hcd").build();
        Variant Fbn1variant = Variant.newBuilder()
                .setMouseAllele(allele)
                .setBackground("involves: 129S1/Sv * 129X1/SvJ * C57BL/6J")
                .setGenotype(ontologyClass("GENO:0000135", "heterozygous"))
                .build();

        ExternalReference pubmedRef = ExternalReference.newBuilder()
               .setId("PMID:15254584")
                .setDescription("Heterozygous Fbn1 C1039G mutation. Judge DP, Biery NJ, Keene DR, Geubtner J, Myers L, Huso DL, Sakai LY, Dietz\n" +
                        "HC. Evidence for a critical contribution of haploinsufficiency in the complex\n" +
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
                .addPhenotypes(aorticDissection)
                .addPhenotypes(abnormalRibMorphology)
                .addPhenotypes(kyphosis)
                .addPhenotypes(overexpandedPulmonaryAlveoli)
                .addPhenotypes(abnormalAortaElasticFiberMorphology)
                .addPhenotypes(mitralValveProlapse)
                .addPhenotypes(abnormalHeartLeftAtriumMorphology)
                .addPhenotypes(abnormalHeartLeftVentricleMorphology)
                .addPhenotypes(increasedAortaWallThickness)
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
