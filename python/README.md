# README

This folder contains the files required for building and deployment of `phenopackets` Python package.

## How to release

Generate the Python sources by running Maven up to `test` or more:

```shell
./mvnw test
```

Maven will run the Protobuf compiler and write the generated Python classes into `src` folder.

We must do some postprocessing to maintain backward compatibility with the previous `phenopackets` versions.
Therefore, we run a script to put all elements of Phenopacket Schema v2.0.2 into the top-level `phenopackets` package.

TODO(iimpulse): implement

Now is the time to run tests. Note, the tests can only be run *after* installing the package with `test` dependencies!
Let's install the package and run the tests:

```shell
# Install the `phenopackets` with test dependencies
python3 -m pip install .[test]

# Run the tests
pytest
```

If the tests pass, we can build and deploy the package to PyPi. 
To do so, we will need the `build` and `twine` Python packages in the environment:

```shell
# Build
python3 -m build

# Deploy
python3 -m twine upload dist/*

# Clear the deployed files
rm -rf dist 
```

That's it!