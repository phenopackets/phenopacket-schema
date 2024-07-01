class TestImportsV202:
    """
    Test that the imports which we had prior `v2.0.2.post2` work.
    """

    def test_singular_import(self):
        from phenopackets import Phenopacket

        pp = Phenopacket(id='retinoblastoma.id')

        assert isinstance(pp, Phenopacket)

    def test_import_module(self):
        import phenopackets as pp2

        pp = pp2.Phenopacket(id='retinoblastoma.id')

        assert isinstance(pp, pp2.Phenopacket)

    def test_import_everything(self):
        import phenopackets

        pp = phenopackets.Phenopacket(id='retinoblastoma.id')

        assert isinstance(pp, phenopackets.Phenopacket)

    def test_import_all_sorts_of_stuff(self):
        import phenopackets as pps

        payload = (
            pps.OntologyClass, pps.Individual, pps.PhenotypicFeature, pps.Measurement, pps.Biosample,
            pps.Interpretation, pps.Disease, pps.MedicalAction, pps.File, pps.MetaData, pps.Family, pps.Cohort,
        )

        for clz in payload:
            x = clz()
            assert isinstance(x, clz)


class TestImportsPostV202:
    """
    Test that the imports which we have at and after `v2.0.2.post2` work.
    """

    def test_singular_import(self):
        from phenopackets.schema.v2.phenopackets_pb2 import Phenopacket

        pp = Phenopacket(id='retinoblastoma.id')

        assert isinstance(pp, Phenopacket)

    def test_import_module(self):
        import phenopackets.schema.v2.phenopackets_pb2 as pp2

        pp = pp2.Phenopacket(id='retinoblastoma.id')

        assert isinstance(pp, pp2.Phenopacket)

    def test_import_everything(self):
        import phenopackets

        pp = phenopackets.schema.v2.phenopackets_pb2.Phenopacket(id='retinoblastoma.id')

        assert isinstance(pp, phenopackets.schema.v2.phenopackets_pb2.Phenopacket)

    def test_import_v1(self):
        from phenopackets.schema.v1.phenopackets_pb2 import Phenopacket

        p = Phenopacket()
        assert isinstance(p, Phenopacket)

    def test_import_all_v2_building_blocks(self):
        import phenopackets.schema.v2 as pps2

        payload = (
            pps2.OntologyClass, pps2.File,               # `.core.base_pb2.py`
            pps2.Biosample,                              # `.core.biosample_pb2.py`
            pps2.Disease,                                # `.core.disease_pb2.py`
            # Nothing for the `.core.genome_pb2.py` file
            pps2.Individual,                             # `.core.individual_pb2.py`
            pps2.Interpretation,                         # `.core.interpretation_pb2.py`
            pps2.Measurement,                            # `.core.measurement_pb2.py`
            pps2.MedicalAction,                          # `.core.medical_action_pb2.py`
            pps2.MetaData,                               # `.core.meta_data_pb2.py`
            pps2.Pedigree,                               # `.core.pedigree_pb2.py`
            pps2.PhenotypicFeature,                      # `.core.phenotypic_feature_pb2.py`
            pps2.Cohort, pps2.Family, pps2.Phenopacket,  # `.phenopackets_pb2.py`
            pps2.Allele,                                 # `phenopackets.vrs.v1.vrs_pb2.py`
            pps2.VariationDescriptor,                    # `phenopackets.vrsatile.v1.vrsatile_pb2.py`
        )

        for clz in payload:
            x = clz()
            assert isinstance(x, clz)


class TestImportVrsatile:

    def test_singular_import(self):
        from phenopackets.vrsatile.v1.vrsatile_pb2 import Extension

        e = Extension()

        assert isinstance(e, Extension)

    def test_import_vrs(self):
        import phenopackets.vrsatile.v1.vrsatile_pb2 as vrsatile

        e = vrsatile.Extension()

        assert isinstance(e, vrsatile.Extension)


class TestImportVrs:

    def test_singular_import(self):
        from phenopackets.vrs.v1.vrs_pb2 import Number

        number = Number()

        assert isinstance(number, Number)

    def test_import_vrs(self):
        import phenopackets.vrs.v1.vrs_pb2 as vrs

        n = vrs.Number()

        assert isinstance(n, vrs.Number)
