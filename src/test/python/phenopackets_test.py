# n.b. the base_pb2 and phenopackets_pb2 files are located in the target/generated-sources/protobuf/python
# directory once the project has built. This file *should* run when done so via IntelliJ/PyCharm but otherwise
# some file hackery will be required.

import os
import unittest

from google.protobuf.json_format import Parse, MessageToJson
from google.protobuf.timestamp_pb2 import Timestamp

from phenopackets import Individual, Sex, PhenotypicFeature, OntologyClass, Phenopacket


class PhenopacketsTest(unittest.TestCase):
    subject = Individual(id="Zaphod", sex="MALE", date_of_birth=Timestamp(seconds=-123456798))
    phenotypic_features = [PhenotypicFeature(type=OntologyClass(id="HG2G:00001", label="Hoopy")),
                           PhenotypicFeature(type=OntologyClass(id="HG2G:00002", label="Frood"))
                           ]
    phenopacket = Phenopacket(id="PPKT:1", subject=subject, phenotypic_features=phenotypic_features)
    test_json_file = "test.json"

    def test_phenopacket_round_trip(self):
        with open(self.test_json_file, 'w') as jsfile:
            json = MessageToJson(self.phenopacket)
            jsfile.write(json)
        with open(self.test_json_file, 'r') as jsfile:
            phenopacket = Parse(message=Phenopacket(), text=jsfile.read())
            self.assertEqual(phenopacket.subject.id, "Zaphod")
            self.assertEqual(Sex.Name(phenopacket.subject.sex), "MALE")
            for phenotypic_feature, expected_feature in zip(phenopacket.phenotypic_features, self.phenotypic_features):
                term = phenotypic_feature.type
                expected_term = expected_feature.type
                self.assertEqual(term.id, expected_term.id)
                self.assertEqual(term.label, expected_term.label)

    @classmethod
    def tearDownClass(cls) -> None:
        try:
            os.remove(cls.test_json_file)
        except OSError as e:
            print("Error: %s - %s." % (e.filename, e.strerror))


if __name__ == '__main__':
    unittest.main()
