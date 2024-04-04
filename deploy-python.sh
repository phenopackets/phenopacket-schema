# Build and Deploy Python Package
DIRECTORY=./python
echo "Building phenopacket distribution files in directory at $DIRECTORY"
TEMP_DIRECTORY_VIRTUAL_ENV="phenopackets-venv"
cd $DIRECTORY || { echo "Deployment FAILED. Couldn't find directory" ; exit 1; }
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

# Create Python venv in virtual directory
createVirtualEnvironment $TEMP_DIRECTORY_VIRTUAL_ENV
# shellcheck disable=SC1090
source "$TEMP_DIRECTORY_VIRTUAL_ENV/bin/activate"
# Dependencies for building/deploying
python3 -m pip install build twine || { echo "Deployment FAILED. Failed to install python dependencies" ; exit 1; }
# Rexport module definition until v3
cp ./config/__init__.py ./src/phenopackets/
# Test
python3 -m pip install .[test]
pytest || { echo "Deployment FAILED. Unittest Failure" ; exit 1; }
# Build
python3 -m build || { echo "Deployment FAILED. Building python package" ; exit 1; }
# Deploy - Remove --repository testpypi flag for production.
if [ "$1" = "release-prod" ]; then
  python3 -m twine upload dist/*
elif [ "$1" = "release-test" ]; then
  python3 -m twine upload --repository testpypi dist/*
else
  echo "Python Release was prepared successfully. No release argument provided, use one of [release-prod, release-test] to make the production/test release."
fi



