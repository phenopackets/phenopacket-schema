

class TestPhenopacket:

    def test_create(self):
        from phenopackets.schema.v2.phenopackets_pb2 import Phenopacket
        from phenopackets import Variation
        from ga4gh.vrs.v1.vrs_pb2 import Variation
        pp = Phenopacket()

        assert isinstance(pp, Phenopacket)
