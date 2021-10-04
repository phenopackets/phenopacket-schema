# Create Temporary Destination
# Phenopackets folder
TEMP_DIRECTORY=$(mktemp -d)
echo "Building phenopacket distribution files in temporary directory at $TEMP_DIRECTORY"
TEMP_DIRECTORY_PYTHON_MODULE="$TEMP_DIRECTORY/phenopackets"
TEMP_DIRECTORY_TESTS_MODULE="$TEMP_DIRECTORY/tests"
TEMP_DIRECTORY_VIRTUAL_ENV="$TEMP_DIRECTORY/phenopackets-venv"
declare -a pyfiles=("base" "phenopackets" "biosample" "disease" "genome" "individual" "interpretation" "medical_action" "measurement" "meta_data" "pedigree" "phenotypic_feature" "vrsatile")
# Functions
createInitFile(){
  echo "import pkg_resources" >> "$TEMP_DIRECTORY/phenopackets/__init__.py"
  echo "__version__ = pkg_resources.get_distribution('phenopackets').version"   >> "$TEMP_DIRECTORY/phenopackets/__init__.py"
  for i in "${pyfiles[@]}"
  do
     echo "from .${i}_pb2 import *"  >> "$TEMP_DIRECTORY/phenopackets/__init__.py"
  done
}

replaceImports(){
    for i in "${pyfiles[@]}"
  do
      sed -i 's/from phenopackets.schema.v2.core/from . /g' "$TEMP_DIRECTORY_PYTHON_MODULE/${i}_pb2.py"
      sed -i 's/from ga4gh.vrsatile.v1/from . /g' "$TEMP_DIRECTORY_PYTHON_MODULE/${i}_pb2.py"
      sed -i 's/from ga4gh.vrs.v1/from . /g' "$TEMP_DIRECTORY_PYTHON_MODULE/${i}_pb2.py"
  done
}

createVirtualEnvironment(){
  echo "Creating Python virtual environment at ${1}"
  python3 -m venv "${1}" &> /dev/null
  if [ ${?} = 1 ]; then
    echo "Setup of Python virtual environment using 'python3 -m venv' failed. Trying 'virtualenv'"
    virtualenv "${1}" &> /dev/null
  fi
  if [ ${?} = 1 ]; then
    echo "Deployment FAILED. Could not create Python virtual environment"
    exit 1;
  fi
  echo "Virtual environment created successfully";
}

# Create python module
mkdir $TEMP_DIRECTORY_PYTHON_MODULE
createInitFile
cp ./target/generated-sources/protobuf/python/phenopackets/schema/v2/phenopackets_pb2.py $TEMP_DIRECTORY_PYTHON_MODULE
cp ./target/generated-sources/protobuf/python/phenopackets/schema/v2/core/* $TEMP_DIRECTORY_PYTHON_MODULE
cp ./target/generated-sources/protobuf/python/ga4gh/vrsatile/v1/vrsatile_pb2.py $TEMP_DIRECTORY_PYTHON_MODULE
cp ./target/generated-sources/protobuf/python/ga4gh/vrs/v1/vrs_pb2.py $TEMP_DIRECTORY_PYTHON_MODULE
replaceImports
# Create tests module
mkdir $TEMP_DIRECTORY_TESTS_MODULE
cp ./src/test/python/* $TEMP_DIRECTORY_TESTS_MODULE
# Copy Packaging files
cp requirements.txt setup.py pom.xml LICENSE README.rst $TEMP_DIRECTORY

# Create Python venv in virtual directory
createVirtualEnvironment $TEMP_DIRECTORY_VIRTUAL_ENV
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
if [ $1 = "release-prod" ]; then
  python3 -m twine upload dist/*
elif [ $1 = "release-test" ]; then
  python3 -m twine upload --repository testpypi dist/*
else
  echo "Python Release was prepared successfully. No release argument provided, use one of [release-prod, release-test] to make the production/test release."
fi



