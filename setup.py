import os

import xmltodict
from setuptools import setup, find_packages


def clean():
    """Cleans the source directory of all non-source files.
    """
    os.system('rm -rf .coverage')
    os.system('find . -name "__pycache__" -print0|xargs -0 rm -rf')
    os.system('find . -name "*.egg-info" -print0|xargs -0 rm -rf')
    os.system('rm -rf dist')


path = os.path.dirname(os.path.abspath(__file__))

with open(os.path.join(path, 'LICENSE')) as f:
    license = f.read()

with open(os.path.join(path, 'README.rst')) as f:
    readme = f.read()

with open(os.path.join(path, 'requirements.txt')) as f:
    requirements = f.read().splitlines()


def version():
    with open(os.path.join(path, 'pom.xml')) as f:
        pom = xmltodict.parse(f.read())
        return pom['project']['version']


setup(name='phenopacket-schema',
      version=version(),
      description='Phenopacket schema',
      long_description=readme,
      url='https://github.com/phenopackets/phenopacket-schema',
      author='Jules Jacobsen',
      author_email='j.jacobsen at qmul.ac.uk',
      license=license,
      # n.b this doesn't work correctly at the moment -
      # if a knowledgeable Pythonista would like to help this would be great
      package_dir={'': 'target/generated-sources/protobuf/python'},
      packages=find_packages(),
      zip_safe=False,
      install_requires=requirements
      )
