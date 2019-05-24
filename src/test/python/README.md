Phenopackets Python Example
===========================

Assuming this file is located in the directory `~/github/phenopacket-schema/src/test/python`

```bash
$ cd ~/github/phenopacket-schema/
# build the project using maven (requires Java)
$ ./mvnw package
# alternatively read-up on protoc
# now setup a dir for the python virtual environment
$ mkdir ~/python_envs/phenopacket-schema
$ cd ~/python_envs/phenopacket-schema
# copy the auto-generated python files to the env
$ cp -r ~/github/phenopacket-schema/target/target/generated-sources/protobuf/python/* .
# copy the test project files 
$ cp ~/github/phenopacket-schema/src/test/python/* .
# setup the python virtual env
$ python3 -m venv ~/python_envs/phenopacket-schema
$ pip install -r ~/github/phenopacket-schema/requirements.txt
$ python phenopacket_test.py
```

