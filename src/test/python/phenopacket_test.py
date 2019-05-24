# n.b. the base_pb2 and phenopackets_pb2 files are located in the target/generated-sources/protobuf/python
# directory once the project has built. This file *should* run when done so via IntelliJ/PyCharm but otherwise
# some file hackery will be required.

import os

import google.protobuf.timestamp_pb2 as timestamp_pb2
from google.protobuf.json_format import Parse, MessageToJson

import base_pb2 as pp_base
import phenopackets_pb2 as pp

# Main - we're going to create a simple phenopacket, write it out as JSON then read it back in as an object
subject = pp_base.Individual(id="Zaphod", sex=pp_base.MALE, date_of_birth=timestamp_pb2.Timestamp(seconds=-123456798))
phenotypes = [pp_base.Phenotype(type=pp_base.OntologyClass(id="H2G2:00001", label="Hoopy")),
              pp_base.Phenotype(type=pp_base.OntologyClass(id="H2G2:00002", label="Frood"))
              ]
phenopacket = pp.Phenopacket(id="PPKT:1", subject=subject, phenotypes=phenotypes)

test_json_file = "test.json"
with open(test_json_file, 'w') as jsfile:
    json = MessageToJson(phenopacket)
    jsfile.write(json)

with open(test_json_file, 'r') as jsfile:
    round_trip = Parse(message=pp.Phenopacket(), text=jsfile.read())
    print(round_trip)

try:
    os.remove(test_json_file)
except OSError as e:
    print("Error: %s - %s." % (e.filename, e.strerror))