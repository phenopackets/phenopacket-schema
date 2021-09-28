import os
import xmltodict

from setuptools import setup, find_packages

path = os.path.dirname(os.path.abspath(__file__))

with open(os.path.join(path, 'LICENSE')) as f:
    LICENSE = f.read()

with open(os.path.join(path, 'README.rst')) as f:
    READ_ME = f.read()

with open(os.path.join(path, 'requirements.txt')) as f:
    REQUIREMENTS = f.read().splitlines()


def version():
    with open(os.path.join(path, 'pom.xml')) as f:
        pom = xmltodict.parse(f.read())
        return pom['project']['version']


setup(
      name='phenopackets',
      version=version(),  # replace with version method
      packages=find_packages(),
      install_requires=REQUIREMENTS,
      package_data={'':  ['tests/*']},
      data_files=[('', ['requirements.txt', 'LICENSE'])],
      test_suite="tests",
      long_description=READ_ME,
      long_description_content_type='text/x-rst',
      author='Michael Gargano',
      author_email='michael.gargano@jax.com',
      url='https://github.com/phenopackets/phenopacket-schema',
      description='A python implementation of phenopackets protobuf',
      license=LICENSE,
      keywords='phenopackets, clinical'
      )
