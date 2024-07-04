# README

This folder contains the files required for building and deployment of `phenopackets` Python package.

## How to release

Generate the Python sources by running Maven up to `test` or more:

```shell
./mvnw test
```

Maven will run the Protobuf compiler and generate the Python classes.

We copy the generated classes into the package:

```shell
cp -r target/generated-sources/protobuf/python/* python/src/
```

We re-export elements of `v2.0.2` schema from the top-level package to maintain the backward compatibility 
with the previous `phenopackets` versions.

```shell
cd python

cp config/__init__.py src/phenopackets/
```

Now is the time to run tests. Note, the tests can only be run *after* installing the package with `test` dependencies!
Let's install the package and run the tests:

```shell
# Install the package with test dependencies
python3 -m pip install .[test]

# Run the tests
pytest
```

If the tests pass, we can build and deploy the package to PyPi. 
To do so, we will need the `build` and `twine` Python packages in the environment:

```shell
# Install the build libraries
python3 -m pip install build twine

# Build
python3 -m build

# Deploy
python3 -m twine upload dist/*

# Clear the deployed files
rm -rf dist 
```

That's it!