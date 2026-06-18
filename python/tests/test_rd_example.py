import typing

import pytest

import phenopackets.schema.v2 as pps2

from google.protobuf.timestamp_pb2 import Timestamp


def test_phenopacket(
    subject: pps2.Individual,
    phenotypic_features: typing.Sequence[pps2.PhenotypicFeature],
    meta_data: pps2.MetaData,
):
    """
    Test if the phenopacket from `docs/rd-example-python.rst`
    can be built.
    """
    proband = pps2.Phenopacket(
        id="14 year-old boy",
        subject=subject,
        phenotypic_features=phenotypic_features,
        meta_data=meta_data,
    )
    assert proband is not None


@pytest.fixture
def subject() -> pps2.Individual:
    return pps2.Individual(
        id="14 year-old boy",
        time_at_last_encounter=pps2.TimeElement(
            age=pps2.Age(
                iso8601duration="P14Y",
            )
        ),
        sex=pps2.MALE,
    )


@pytest.fixture
def evidence() -> pps2.Evidence:
    return pps2.Evidence(
        evidence_code=pps2.OntologyClass(
            id="ECO:0000033",
            label="author statement supported by traceable reference",
        ),
        reference=pps2.ExternalReference(
            id="PMID:30808312",
            description="COL6A1 mutation leading to Bethlem myopathy with recurrent hematuria: a case report.",
        ),
    )


@pytest.fixture
def phenotypic_features(
    evidence: pps2.Evidence,
) -> typing.Sequence[pps2.PhenotypicFeature]:
    return (
        pps2.PhenotypicFeature(
            type=pps2.OntologyClass(id="HP:0001558", label="Decreased fetal movement"),
            onset=pps2.TimeElement(
                ontology_class=pps2.OntologyClass(
                    id="HP:0011461",
                    label="Fetal onset",
                ),
            ),
            evidence=(evidence,),
        ),
        pps2.PhenotypicFeature(
            type=pps2.OntologyClass(
                id="HP:0031910",
                label="Abnormal cranial nerve physiology",
            ),
            excluded=True,
            evidence=(evidence,),
        ),
        pps2.PhenotypicFeature(
            type=pps2.OntologyClass(
                id="HP:0011463",
                label="Macroscopic hematuria",
            ),
            modifiers=(
                pps2.OntologyClass(
                    id="HP:0031796",
                    label="Recurrent",
                ),
            ),
            onset=pps2.TimeElement(
                age=pps2.Age(
                    iso8601duration="P14Y",
                ),
            ),
            evidence=(evidence,),
        ),
        pps2.PhenotypicFeature(
            type=pps2.OntologyClass(
                id="HP:0001270",
                label="Motor delay",
            ),
            severity=pps2.OntologyClass(
                id="HP:0001270",
                label="Mild",
            ),
            onset=pps2.TimeElement(
                ontology_class=pps2.OntologyClass(
                    id="HP:0011463",
                    label="Childhood onset",
                ),
            ),
        ),
    )


@pytest.fixture
def meta_data() -> pps2.MetaData:
    created = Timestamp()
    created.FromJsonString("2021-05-11T14:37:28.328Z")
    return pps2.MetaData(
        created=created,
        created_by="Peter R.",
        resources=(
            pps2.Resource(
                id="hp",
                name="human phenotype ontology",
                url="http://purl.obolibrary.org/obo/hp.owl",
                version="2018-03-08",
                namespace_prefix="HP",
                iri_prefix="http://purl.obolibrary.org/obo/HP_",
            ),
            pps2.Resource(
                id="geno",
                name="Genotype Ontology",
                url="http://purl.obolibrary.org/obo/geno.owl",
                version="19-03-2018",
                namespace_prefix="GENO",
                iri_prefix="http://purl.obolibrary.org/obo/GENO_",
            ),
            pps2.Resource(
                id="pubmed",
                name="PubMed",
                namespace_prefix="PMID",
                iri_prefix="https://www.ncbi.nlm.nih.gov/pubmed/",
            ),
        ),
        phenopacket_schema_version="2.0",
        external_references=(
            pps2.ExternalReference(
                id="PMID:30808312",
                description="Bao M, et al. COL6A1 mutation leading to Bethlem myopathy with recurrent hematuria: a case report. BMC Neurol. 2019;19(1):32.",
            ),
        ),
    )
