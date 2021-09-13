import os
import unittest

from google.protobuf.json_format import Parse, MessageToJson
from google.protobuf.timestamp_pb2 import Timestamp

from phenopackets import Individual, Sex, PhenotypicFeature, OntologyClass, Phenopacket


class PhenopacketsTest(unittest.TestCase):
    path = os.path.dirname(os.path.abspath(__file__))
    subject = Individual(id="Zaphod", sex="MALE", date_of_birth=Timestamp(seconds=-123456798))
    phenotypic_features = [PhenotypicFeature(type=OntologyClass(id="HG2G:00001", label="Hoopy")),
                           PhenotypicFeature(type=OntologyClass(id="HG2G:00002", label="Frood"))
                           ]
    phenopacket = Phenopacket(id="PPKT:1", subject=subject, phenotypic_features=phenotypic_features)
    test_json_file = "test.json"
    def test_phenopacket_round_trip(self):
        with open(self.test_json_file, 'w') as jsfile:
            jsfile.write(MessageToJson(self.phenopacket))
        with open(self.test_json_file, 'r') as jsfile:
            phenopacket = Parse(message=Phenopacket(), text=jsfile.read())
            self.assertEqual(phenopacket.subject.id, "Zaphod")
            self.assertEqual(Sex.Name(phenopacket.subject.sex), "MALE")
            for phenotypic_feature, expected_feature in zip(phenopacket.phenotypic_features, self.phenotypic_features):
                term = phenotypic_feature.type
                expected_term = expected_feature.type
                self.assertEqual(term.id, expected_term.id)
                self.assertEqual(term.label, expected_term.label)

    def test_phenopacket_covid(self):
        with(open(os.path.join(self.path,"covid19.json"), 'r')) as covid19:
            phenopacket = Parse(message=Phenopacket(), text=covid19.read())
            self.assertEqual("P123542", phenopacket.subject.id)
            self.assertEqual(11, len(phenopacket.phenotypic_features))
            self.assertEqual(2, len(phenopacket.measurements))
            self.assertEqual("NCIT:C113237", phenopacket.measurements[0].assay.id)
            self.assertEqual("LOINC:26474-7", phenopacket.measurements[1].assay.id)
            self.assertEqual("MONDO:0005015", phenopacket.diseases[0].term.id)
            self.assertEqual("cardiomyopathy", phenopacket.diseases[1].term.label)
            self.assertNotEqual(None, phenopacket.diseases[2].onset.timestamp)

    def test_phenopacket_mcahs1(self):
        with(open(os.path.join(self.path,"mcahs1.json"), 'r')) as covid19:
            phenopacket = Parse(message=Phenopacket(), text=covid19.read())
            self.assertEqual("proband A", phenopacket.subject.id)
            self.assertEqual(1, phenopacket.subject.sex)
            self.assertEqual(3, phenopacket.interpretations[0].progress_status)
            self.assertEqual("Bethlem myopathy 1", phenopacket.interpretations[0].diagnosis.disease.label)
            self.assertEqual(5, phenopacket.interpretations[0].diagnosis.genomic_interpretations[0].variant_interpretation.acmg_pathogenicity_classification)
            self.assertEqual("GENO:0000135", phenopacket.interpretations[0].diagnosis.genomic_interpretations[0].variant_interpretation.variation_descriptor.allelic_state.id)



    @classmethod
    def tearDownClass(cls) -> None:
        try:
            os.remove(cls.test_json_file)
        except OSError as e:
            print("Error: %s - %s." % (e.filename, e.strerror))


if __name__ == '__main__':
    unittest.main()