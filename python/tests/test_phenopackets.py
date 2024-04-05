import os

import pytest

from google.protobuf.json_format import Parse, MessageToJson
from google.protobuf.timestamp_pb2 import Timestamp
from phenopackets import Individual, Sex, PhenotypicFeature, OntologyClass, Phenopacket


# This will break when we deprecate the top-level import.
class TestPhenopackets:

    @pytest.fixture
    def phenopacket(self) -> Phenopacket:
        subject = Individual(id="Zaphod", sex="MALE", date_of_birth=Timestamp(seconds=-123456798))
        phenotypic_features = (
            PhenotypicFeature(type=OntologyClass(id="HG2G:00001", label="Hoopy")),
            PhenotypicFeature(type=OntologyClass(id="HG2G:00002", label="Frood")),
        )

        return Phenopacket(id="PPKT:1", subject=subject, phenotypic_features=phenotypic_features)

    def test_phenopacket_round_trip(
        self,
        phenopacket: Phenopacket,
    ):
        output: str = MessageToJson(phenopacket)

        back = Parse(message=Phenopacket(), text=output)

        assert back.subject.id == "Zaphod"
        assert Sex.Name(back.subject.sex) == "MALE"
        for phenotypic_feature, expected_feature in zip(back.phenotypic_features, phenopacket.phenotypic_features):
            term = phenotypic_feature.type
            expected_term = expected_feature.type
            assert term.id == expected_term.id
            assert term.label == expected_term.label

    def test_phenopacket_covid(
            self,
            fpath_test_dir: str,
    ):
        with open(os.path.join(fpath_test_dir, "covid19.json")) as fh:
            pp = Parse(message=Phenopacket(), text=fh.read())

        assert pp.subject.id == "P123542"
        assert len(pp.phenotypic_features) == 11
        assert len(pp.measurements) == 2
        assert pp.measurements[0].assay.id == "NCIT:C113237"
        assert pp.measurements[1].assay.id == "LOINC:26474-7"
        assert pp.diseases[0].term.id == "MONDO:0005015"
        assert pp.diseases[1].term.label == "cardiomyopathy"

        assert pp.diseases[2].onset.timestamp is not None

    def test_phenopacket_mcahs1(
            self,
            fpath_test_dir: str,
    ):
        with open(os.path.join(fpath_test_dir, "mcahs1.json")) as fh:
            pp = Parse(message=Phenopacket(), text=fh.read())

        assert pp.subject.id == "proband A"
        assert pp.subject.sex == 1
        assert pp.interpretations[0].progress_status == 3
        assert pp.interpretations[0].diagnosis.disease.label == "Bethlem myopathy 1"
        assert pp.interpretations[0].diagnosis.genomic_interpretations[0].variant_interpretation.acmg_pathogenicity_classification == 5
        assert pp.interpretations[0].diagnosis.genomic_interpretations[0].variant_interpretation.variation_descriptor.allelic_state.id == "GENO:0000135"
