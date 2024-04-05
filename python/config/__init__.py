# This __init__ file should be placed into `phenopackets` package
# for backward compatibility with `[2.0.0, 2.0.2.post1]`,
# where we used to import Phenopacket Schema building blocks as
# ```
# import phenopackets as pp2
# phenopacket = pp2.Phenopacket()
# ```

from .schema.v2.core.base_pb2 import *
from .schema.v2.core.biosample_pb2 import *
from .schema.v2.core.disease_pb2 import *
from .schema.v2.core.genome_pb2 import *
from .schema.v2.core.individual_pb2 import *
from .schema.v2.core.interpretation_pb2 import *
from .schema.v2.core.measurement_pb2 import *
from .schema.v2.core.medical_action_pb2 import *
from .schema.v2.core.meta_data_pb2 import *
from .schema.v2.core.pedigree_pb2 import *
from .schema.v2.core.phenotypic_feature_pb2 import *
from .schema.v2.phenopackets_pb2 import *
from ga4gh.vrs.v1.vrs_pb2 import *
from ga4gh.vrsatile.v1.vrsatile_pb2 import *
