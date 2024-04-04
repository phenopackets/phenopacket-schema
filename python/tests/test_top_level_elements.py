

class TestPhenopacket:

    def test_create(self):
        from phenopackets.schema.v2.phenopackets_pb2 import Phenopacket
        pp = Phenopacket()

        assert isinstance(pp, Phenopacket)
