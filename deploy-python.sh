# Create Temporary Destination
# Phenopackets folder
TEMP_DIRECTORY=$(mktemp -d)
echo "$TEMP_DIRECTORY"
TEMP_DIRECTORY_PYTHON_MODULE="$TEMP_DIRECTORY/phenopackets"
TEMP_DIRECTORY_TESTS_MODULE="$TEMP_DIRECTORY/tests"
TEMP_DIRECTORY_VIRTUAL_ENV="$TEMP_DIRECTORY/phenopackets-venv"

# Functions
createInitFile(){
  echo "import pkg_resources" >> "$TEMP_DIRECTORY/phenopackets/__init__.py"
  echo "__version__ = pkg_resources.get_distribution('phenopackets').version"   >> "$TEMP_DIRECTORY/phenopackets/__init__.py"
  echo "from .base_pb2 import *"   >> "$TEMP_DIRECTORY/phenopackets/__init__.py"
  echo "from .phenopackets_pb2 import *"   >> "$TEMP_DIRECTORY/phenopackets/__init__.py"
  echo "from .interpretation_pb2 import *"   >> "$TEMP_DIRECTORY/phenopackets/__init__.py"
}

replaceImports(){
  # Interpretation Replace
  sed -i 's/import base_pb2/from . import base_pb2/g' "$TEMP_DIRECTORY_PYTHON_MODULE/interpretation_pb2.py"
  sed -i 's/import phenopackets_pb2/from . import phenopackets_pb2/g' "$TEMP_DIRECTORY_PYTHON_MODULE/interpretation_pb2.py"

  # Phenopackets Replace
  sed -i 's/import base_pb2/from . import base_pb2/g' "$TEMP_DIRECTORY_PYTHON_MODULE/phenopackets_pb2.py"
}

# Create python module
mkdir $TEMP_DIRECTORY_PYTHON_MODULE
createInitFile
cp ./target/generated-sources/protobuf/python/* $TEMP_DIRECTORY_PYTHON_MODULE
replaceImports
# Create tests module
mkdir $TEMP_DIRECTORY_TESTS_MODULE
cp ./src/test/python/* $TEMP_DIRECTORY_TESTS_MODULE
# Copy Packaging files
cp requirements.txt setup.py pom.xml LICENSE README.rst $TEMP_DIRECTORY

# Create Python venv in virtual directory
python3 -m venv $TEMP_DIRECTORY_VIRTUAL_ENV
cd $TEMP_DIRECTORY || { echo "Deployment FAILED. Couldn't cd to temp directory" ; exit 1; }
# shellcheck disable=SC1090
source "$TEMP_DIRECTORY_VIRTUAL_ENV/bin/activate"
pip install -r "$TEMP_DIRECTORY/requirements.txt"
# Dependencies for building/deploying
python3 -m pip install setuptools wheel twine || { echo "Deployment FAILED. Failed to install python dependencies" ; exit 1; }
# Test
pip install -e .
python3 setup.py test || { echo "Deployment FAILED. Unittest Failure" ; exit 1; }
# Build
python3 setup.py sdist bdist_wheel || { echo "Deployment FAILED. Building python package" ; exit 1; }

# Deploy - Remove --repository testpypi flag for production.
# python3 -m twine upload --repository testpypi dist/*

