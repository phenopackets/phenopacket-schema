import os
import xmltodict

from setuptools import setup, find_packages

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


setup(
      name='phenopackets',
      version='0.0.0.dev',  # replace with version method
      packages=find_packages(),
      install_requires=requirements,
      package_data={'':  ['tests/*']},
      data_files=[('', ['requirements.txt', 'LICENSE'])],
      test_suite="tests",
      long_description=readme,
      long_description_content_type='text/x-rst',
      author='Michael Gargano', # replace Jules Jacobsen
      author_email='michael.gargano@jax.com', # replace j.jacobsen at qmul.ac.uk
      url='https://github.com/phenopackets/phenopacket-schema',
      description='A python implementation of phenopackets',
      license=license,
      keywords='phenopackets, clinical'
      )
