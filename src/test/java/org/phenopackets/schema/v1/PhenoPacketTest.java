package org.phenopackets.schema.v1;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.google.common.collect.ImmutableList;
import com.google.protobuf.Timestamp;
import com.google.protobuf.util.JsonFormat;
import org.junit.Test;
import org.phenopackets.schema.v1.Phenopacket.PhenoPacket;
import org.phenopackets.schema.v1.core.*;

import java.io.IOException;
import java.time.Instant;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Jules Jacobsen <j.jacobsen@qmul.ac.uk>
 */
public class PhenoPacketTest {

    private static final OntologyClass FEMALE = ontologyClass("PATO:0000383", "female");

    private static final OntologyClass MALE = ontologyClass("PATO:0000384", "male");

    private static OntologyClass ontologyClass(String id, String label) {
        return OntologyClass.newBuilder()
                .setId(id)
                .setLabel(label)
                .build();
    }

    //TODO: move these to a utility class
    public String writeAsYaml(PhenoPacket phenoPacket) throws IOException {
        String jsonString = JsonFormat.printer().print(phenoPacket);
        return jsonToYaml(jsonString);
    }

    // parse JSON to YAML
    public String jsonToYaml(String jsonString) throws IOException {
        JsonNode jsonNodeTree = new ObjectMapper().readTree(jsonString);
        return new YAMLMapper().writeValueAsString(jsonNodeTree);
    }

    // parse YAML to JSON
    public String yamlToJson(String yamlString) throws IOException {
        JsonNode jsonNodeTree = new YAMLMapper().readTree(yamlString);
        return new ObjectMapper().writeValueAsString(jsonNodeTree);
    }

    @Test
    public void testyMcTestFace() throws Exception {

        Phenotype phenotype = Phenotype.newBuilder()
                .setOnset(ontologyClass("HP:0003623", "Neonatal onset"))
                .setType(ontologyClass("HP:0001711", "Abnormality of the left ventricle"))
                .build();

        long dateOfBirth = Instant.parse("2018-03-12T15:15:30.00Z").getEpochSecond();
        Timestamp dobTimestamp = Timestamp.newBuilder().setSeconds(dateOfBirth).build();
        Individual individual = Individual.newBuilder()
                .setId("INDIVIDUAL:1")
                .setDateOfBirth(dobTimestamp)
                .setSex(FEMALE)
                .addPhenotypes(phenotype)
                .build();

        System.out.println(JsonFormat.printer().print(individual));
        assertThat(individual.getDateOfBirth(), equalTo(dobTimestamp));
        assertThat(individual.getPhenotypesList(), equalTo(ImmutableList.of(phenotype)));
    }

    @Test
    public void testPedigree() throws Exception {
        Pedigree defaultPedigree = Pedigree.getDefaultInstance();
        assertThat(defaultPedigree.getPersonsCount(), equalTo(0));

        Pedigree.Person person1 = Pedigree.Person.newBuilder()
                .setFamilyId("FAMILY:1")
                .setIndividualId("PERSON:1")
                .setMaternalId("MOTHER:1")
                .setPaternalId("FATHER:1")
                .setSex(Pedigree.Person.Sex.MALE)
                .setAffectedStatus(Pedigree.Person.AffectedStatus.AFFECTED)
                .build();

        Pedigree.Person person2 = Pedigree.Person.newBuilder()
                .setFamilyId("FAMILY:1")
                .setIndividualId("PERSON:2")
                .setMaternalId("MOTHER:1")
                .setPaternalId("FATHER:1")
                .setSex(Pedigree.Person.Sex.FEMALE)
                .setAffectedStatus(Pedigree.Person.AffectedStatus.UNAFFECTED)
                .build();

        Pedigree pedigree = Pedigree.newBuilder()
                .addPersons(person1)
                .addPersons(person2)
                .build();

        System.out.println(JsonFormat.printer().print(pedigree));
    }

    @Test
    public void testBoscExample() throws Exception {
        //
//        Phenotype whiteHands = Phenotype.newBuilder()
//                .addTypes(ontologyClass("PATO:0000323", "white"))
//                .addTypes(ontologyClass("PATO:0000586", "increased size"))
//                .addTypes(ontologyClass("HP:0001233", "2-3 finger syndactyly"))
//                .addTypes(ontologyClass("UBERON:0002398", "manus"))
//                .build();
//
//        Phenotype happyDisposition = Phenotype.newBuilder()
//                .addTypes(ontologyClass("HP:0100024", "Conspicuously happy disposition"))
//                .setDescription("welcomes strangers with open arms")
//                .setOnset(ontologyClass("HP:0011463", "Childhood onset"))
//                .build();
//
//        Phenotype absentVibrisae = Phenotype.newBuilder()
//                .addTypes(ontologyClass("MP:0001284", "absent vibrissae"))
//                .build();
//
//        Phenotype circularEars = Phenotype.newBuilder()
//                .addTypes(ontologyClass("PATO:0000411", "circular"))
//                .addTypes(ontologyClass("UBERON:0001690", "ears"))
//                .build();
//
//        Individual mickeyMouse = Individual.newBuilder()
//                .setId("MOUSE:000001")
//                .setDateOfBirth(Timestamp.newBuilder()
//                        .setSeconds(Instant.parse("1928-11-18T00:00:00.00Z").getEpochSecond()))
//                .setSex(MALE)
//                .addPhenotypes(whiteHands)
//                .addPhenotypes(happyDisposition)
//                .addPhenotypes(absentVibrisae)
//                .addPhenotypes(circularEars)
//                .build();

//        String jsonString = JsonFormat.printer().print(mickeyMouse);
//        System.out.println(jsonString);
//
//        String yamlString = jsonToYaml(jsonString);
//        System.out.println(yamlString);
//
//        String backToJsonString = yamlToJson(yamlString);
//        System.out.println(backToJsonString);
//
//        //test round-trip via JSON
//        Individual.Builder mickeyBuilder = Individual.newBuilder();
//        JsonFormat.parser().merge(backToJsonString, mickeyBuilder);
//        Individual roundTrippedMickey = mickeyBuilder.build();
//
//        assertThat(roundTrippedMickey, equalTo(mickeyMouse));

    }

    @Test
    public void testRareDiseaseDiagnosis() throws Exception {

        Gene fgfr2Gene = Gene.newBuilder()
                .setId("HGNC:3689")
                .setSymbol("FGFR2")
                .setNcbiTaxonId(9606)
                .build();

        VariantAnnotation variantAnnotation = VariantAnnotation.newBuilder()
                .setAcmgClassification(VariantAnnotation.AcmgClassification.PATHOGENIC)
                .setVariantEffect(ontologyClass("SO:0001583", "missense_variant"))
                .addAcmgCode("PS1")
                .addAcmgCode("PS2")
                .addAcmgCode("PM2")
                .build();

        Variant pathogenicVariant = Variant.newBuilder()
                .setSequence("NC_000010.10")
                .setPosition(123256214)
                .setDeletion("T")
                .setInsertion("G")
                .setGenotype(ontologyClass("GENO:0000135", "heterozygous"))
                .setVariantAnnotation(variantAnnotation)
                .build();

        Phenotype coronalCraniosynostosis = Phenotype.newBuilder()
                .setType(ontologyClass("HP:0004440", "Coronal craniosynostosis"))
                .setOnset(ontologyClass("HP:0011463", "Childhood onset"))
                .build();

        Phenotype maxillaryHypoplasia = Phenotype.newBuilder()
                .setType(ontologyClass("HP:0000327", "Maxillary hypoplasia"))
                .build();

        Phenotype cloverleafSkullOccasional = Phenotype.newBuilder()
                .setType(ontologyClass("HP:0002676", "Cloverleaf skull"))
                .setFrequency(ontologyClass("HP:0040283", "Occasional"))
                .build();

        Phenotype brachydactyly = Phenotype.newBuilder()
                .setType(ontologyClass("HP:0001156", "Brachydactyly"))
                .build();

        Phenotype craniosynostosis = Phenotype.newBuilder()
                .setType(ontologyClass("HP:0001363", "Craniosynostosis"))
                .build();

        Phenotype broadThumb = Phenotype.newBuilder()
                .setType(ontologyClass("HP:0011304", "Broad thumb"))
                .build();

        Phenotype broadHallux = Phenotype.newBuilder()
                .setType(ontologyClass("HP:0010055", "Broad hallux"))
                .setOnset(ontologyClass("HP:0011463", "Childhood onset"))
                .build();

        Phenotype proptosisCongenitalSevere = Phenotype.newBuilder()
                .setType(ontologyClass("HP:0000520", "Proptosis"))
                .addModifiers(ontologyClass("HP:0012828", "Severe"))
                .setOnset(ontologyClass("HP:0003577", "Congenital onset"))
                .build();

        Phenotype proptosisCongenitalMild = Phenotype.newBuilder()
                .setType(ontologyClass("HP:0000520", "Proptosis"))
                .addModifiers(ontologyClass("HP:0012825", "Mild"))
                .setOnset(ontologyClass("HP:0003577", "Congenital onset"))
                .build();

        Phenotype intellectualDisabilityOccasional = Phenotype.newBuilder()
                .setType(ontologyClass("HP:0001249", "Intellectual disability"))
                .setFrequency(ontologyClass("HP:0040283", "Occasional"))
                .build();

        Disease pfeifferSyndrome = Disease.newBuilder()
                .setId("OMIM:101600")
                .setLabel("PFEIFFER SYNDROME")
                .setModeOfInheritance(ontologyClass("HP:0000006", "Autosomal dominant inheritance"))
                .addPhenotypes(maxillaryHypoplasia)
                .addPhenotypes(coronalCraniosynostosis)
                .addPhenotypes(cloverleafSkullOccasional)
                .addPhenotypes(proptosisCongenitalSevere)
                .addPhenotypes(broadThumb)
                .addPhenotypes(intellectualDisabilityOccasional)
                .build();


        Individual proband = Individual.newBuilder()
                .setId("proband")
                .addPhenotypes(brachydactyly)
                .addPhenotypes(craniosynostosis)
                .addPhenotypes(broadThumb)
                .addPhenotypes(broadHallux)
                .addPhenotypes(proptosisCongenitalMild)
                .build();

        MetaData metaData = MetaData.newBuilder()
                .addOntologies(Ontology.newBuilder()
                        .setId("hp")
                        .setName("human phenotype ontology")
                        .setNamespacePrefix("HP")
                        .setUrl("http://purl.obolibrary.org/obo/hp.owl")
                        .setVersion("2018-03-08")
                        .build())
                .addOntologies(Ontology.newBuilder()
                        .setId("geno")
                        .setName("Genotype Ontology")
                        .setNamespacePrefix("GENO")
                        .setUrl("http://purl.obolibrary.org/obo/geno.owl")
                        .setVersion("19-03-2018")
                        .build())
                .addOntologies(Ontology.newBuilder()
                        .setId("so")
                        .setName("Sequence types and features")
                        .setNamespacePrefix("SO")
                        .setUrl("http://purl.obolibrary.org/obo/so.owl")
                        .setVersion("2015-11-24")
                        .build())
                .setCreatedBy("Jules J.")
                .build();

        PhenoPacket exomiserPfeifferExample = PhenoPacket.newBuilder()
                .setDescription("Example diagnosed patient with known pathogenic variant in the FGFR2 gene causative of Pfeiffer syndrome.")
                .addDiseases(pfeifferSyndrome)
                .setIndividual(proband)
                .addVariants(pathogenicVariant)
                .setMetaData(metaData)
                .build();

        System.out.println(writeAsYaml(exomiserPfeifferExample));
    }

}
