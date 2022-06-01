package org.phenopackets.schema.v2.examples;

import org.junit.jupiter.api.Test;
import org.phenopackets.schema.v2.core.OntologyClass;
import org.phenopackets.schema.v2.core.Resource;

import java.util.Objects;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * This class aims to document the use of the Resource and OntologyClass messages and how they can be used to
 * define a URI for a concept.
 *
 * @author Jules Jacobsen <j.jacobsen@qmul.ac.uk>
 */
public class ResourceOntologyClassExample {

    @Test
    public void resolveUriForHpoClass() {

        Resource hpoResource = Resource.newBuilder()
                // for OBO Ontologies, the value of this string MUST always be the official
                // OBO ID, which is always equivalent to the ID prefix in lower case.
                // Examples: hp, go, mp, mondo
                // Consult http://obofoundry.org for a complete list
                // For other ontologies (e.g. SNOMED), use the prefix in identifiers.org
                .setId("hpo")
                // e.g. The Human PhenotypicFeature Ontology
                // for OBO Ontologies, the value of this string SHOULD be the same as the title
                // field on http://obofoundry.org
                // however, this field is purely for information purposes and software
                // should not encode any assumptions
                .setName("The Human PhenotypicFeature Ontology")
                // For OBO ontologies, this should always be the PURL, e.g.
                // http://purl.obolibrary.org/obo/hp.owl, http://purl.obolibrary.org/obo/hp.obo
                .setUrl("http://purl.obolibrary.org/obo/hp.obo")
                // for OBO ontologies, this should be the versionIRI
                .setVersion("2019-02-12")
                // The prefix used in the CURIE of an OntologyClass e.g. HP, MP, ECO
                // For example an HPO term will have a CURIE like this - HP:0012828 which should be used in combination with
                // the iri_prefix to form a fully-resolvable IRI
                .setNamespacePrefix("HP")
                // Full IRI prefix which can be used with the namespace_prefix and the OntologyClass::id to resolve to an IRI for a
                // term. Tools such as the curie-util (https://github.com/prefixcommons/curie-util) can utilise this to produce
                // fully-resolvable IRIs for an OntologyClass.
                // e.g. Using the HPO term encoding the concept of 'Severe'
                //    OntologyClass:
                //        id: 'HP:0012828'
                //        label: 'Severe'
                //    Resource:
                //        namespace_prefix: 'HP'
                //        iri_prefix: 'http://purl.obolibrary.org/obo/HP_'
                // the term can be resolved to http://purl.obolibrary.org/obo/HP_0012828
                .setIriPrefix("http://purl.obolibrary.org/obo/HP_")
                .build();

        Resource snomedResource = Resource.newBuilder()
                // for OBO Ontologies, the value of this string MUST always be the official
                // OBO ID, which is always equivalent to the ID prefix in lower case.
                // Examples: hp, go, mp, mondo
                // Consult http://obofoundry.org for a complete list
                // For other ontologies (e.g. SNOMED), use the prefix in identifiers.org
                .setId("snomedct")
                .setName("SNOMED CT")
                .setUrl("http://snomed.info/sct")
                .setVersion("v20190131")
                .setNamespacePrefix("SCTID")
                //
                // .setIriPrefix("http://purl.obolibrary.org/obo/HP_")
                .build();

        //http://www.ontobee.org/ontology/HP?iri=http://purl.obolibrary.org/obo/HP_0012828
        OntologyClass severeHpo = OntologyClass.newBuilder().setId("HP:0012828").setLabel("Severe").build();

        // https://browser.ihtsdotools.org/?perspective=full&conceptId1=24484000&edition=en-edition&release=v20190131&server=https://prod-browser-exten.ihtsdotools.org/api/snomed&langRefset=900000000000509007
        OntologyClass severeSnomed = OntologyClass.newBuilder().setId("SCTID:24484000").setLabel("Severe (severity modifier) (qualifier value)").build();

        // For reference https://www.w3.org/TR/curie/#s_syntax formally defines the CURIE
        // There are libraries such as the CurieUtil which will handle expanding and contracting IRI <-> CURIE
        // but essentially the logic should look like this:

        Curie hpoSevereCurie = Curie.of(severeHpo.getId());
        assertThat(hpoSevereCurie.getPrefix(), equalTo("HP"));
        assertThat(hpoSevereCurie.getReference(), equalTo("0012828"));

        assertThat(hpoResource.getIriPrefix() + hpoSevereCurie.getReference(), equalTo("http://purl.obolibrary.org/obo/HP_0012828"));
    }

    // https://www.w3.org/TR/curie/#s_syntax
    public static class Curie {

        private final String prefix;
        private final String reference;

        public static Curie of(String curie) {
            String[] curieParts = Objects.requireNonNull(curie).split(":");
            if (curieParts.length != 2) {
                throw new IllegalArgumentException("Curie must be of the form 'prefix:reference'");
            }
            String prefix = curieParts[0];
            String reference = curieParts[1];
            return new Curie(prefix, reference);
        }

        private Curie(String prefix, String reference) {
            this.prefix = prefix;
            this.reference = reference;
        }

        public String getPrefix() {
            return prefix;
        }

        public String getReference() {
            return reference;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Curie)) return false;
            Curie curie = (Curie) o;
            return prefix.equals(curie.prefix) &&
                    reference.equals(curie.reference);
        }

        @Override
        public int hashCode() {
            return Objects.hash(prefix, reference);
        }

        @Override
        public String toString() {
            return prefix + ':' + reference;
        }
    }
}
