import os

import pytest


@pytest.fixture(scope='session')
def fpath_test_dir() -> str:
    # When running `pytest` from the top-level Python folder (`phenopacket-schema/python`)
    # this path will evaluate to the `tests` folder.
    return os.path.dirname(__file__)
