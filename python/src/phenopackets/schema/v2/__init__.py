# The __init__ file for placing in `phenopackets.schema.v2` package
# to allow importing all building blocks of the Phenopacket Schema v2,
# including the VRS/VRSATILE elements.

from .core.base_pb2 import *
from .core.biosample_pb2 import *
from .core.disease_pb2 import *
from .core.genome_pb2 import *
from .core.individual_pb2 import *
from .core.interpretation_pb2 import *
from .core.measurement_pb2 import *
from .core.medical_action_pb2 import *
from .core.meta_data_pb2 import *
from .core.pedigree_pb2 import *
from .core.phenotypic_feature_pb2 import *
from .phenopackets_pb2 import *
from ...vrs.v1.vrs_pb2 import *
from ...vrsatile.v1.vrsatile_pb2 import *
